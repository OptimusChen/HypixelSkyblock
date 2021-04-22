package com.itech4kids.skyblock.Listeners;

import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import com.itech4kids.skyblock.Objects.SkyblockStats;
import com.itech4kids.skyblock.Util.Config;
import com.itech4kids.skyblock.Util.ItemUtil;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import javax.swing.text.NumberFormatter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Random;

import static org.bukkit.Material.EGG;

public class Eventlistener implements Listener {

    public Main main;

    public Eventlistener(){
        main = Main.getMain();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        final Player player = e.getPlayer();

        ItemStack item1 = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = item1.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Skyblock Menu (Right Click)");
        item1.setItemMeta(itemMeta);
        player.getInventory().setItem(8, item1);

        final SkyblockPlayer skyblockPlayer = new SkyblockPlayer(player);
        main.players.put(player.getName(), skyblockPlayer);

        new BukkitRunnable() {
            @Override
            public void run () {
                main.onJoin(player);
                IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "\n" +
                        "§bYou are playing on: "  + "§e§lHYPIXEL.NET" + "\n" + " " + "\"}");
                IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "\n" + "§aOnline:" + "\n" + "§7There are currently " + Bukkit.getOnlinePlayers().size() + " online!" + "\n " + "\n" + "§aRanks, Booster & More!" + "§l§c STORE.HYPIXEL.NET" + "\"}");
                PacketPlayOutPlayerListHeaderFooter info = new PacketPlayOutPlayerListHeaderFooter(header);

                try {
                    Field field = info.getClass().getDeclaredField("b");
                    field.setAccessible(true);
                    field.set(info, footer);
                } catch (Exception x) {
                    x.printStackTrace();
                }

                player.getWorld().setGameRuleValue("mobGriefing", "false");
                player.getWorld().setGameRuleValue("randomTickSpeed", "0");
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(info);
                try {
                    Config.createPlayer(player.getName());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }.runTaskLater(main, 20);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            SkyblockPlayer skyblockPlayer = main.getPlayer(e.getDamager().getName());
            int i = new Random().nextInt(100);
            double damage = 5 + skyblockPlayer.getStat((SkyblockStats.ATTACK_DAMAGE)) + (skyblockPlayer.getStat(SkyblockStats.STRENGTH) / 5) * (1 + skyblockPlayer.getStat(SkyblockStats.STRENGTH) / 100);
            if (i <= skyblockPlayer.getStat(SkyblockStats.CRIT_CHANCE)) {
                e.setDamage(damage * (1 + skyblockPlayer.getStat(SkyblockStats.CRIT_DAMAGE))/100);
                ItemUtil.setDamageIndicator(e.getEntity().getLocation(), ItemUtil.addCritTexture(String.valueOf(Math.round(e.getDamage()))));
            }else{
                e.setDamage(damage);
                ItemUtil.setDamageIndicator(e.getEntity().getLocation(), org.bukkit.ChatColor.GRAY + "" + Math.round(e.getDamage()));
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if (e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Skyblock Menu (Right Click)")){
            player.performCommand("sbmenu");
        }
    }
}
