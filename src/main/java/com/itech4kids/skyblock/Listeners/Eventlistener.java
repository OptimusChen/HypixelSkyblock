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
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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

        try {
            Config.createPlayer(player.getName());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ItemStack item1 = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = item1.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Skyblock Menu (Right Click)");
        item1.setItemMeta(itemMeta);
        player.getInventory().setItem(8, item1);

        SkyblockPlayer skyblockPlayer = new SkyblockPlayer(player);
        main.players.put(player.getName(), skyblockPlayer);

        new BukkitRunnable() {
            @Override
            public void run () {
                main.onJoin(player);
                main.updateBoard(player);
                IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "\n" +
                        "§bYou are playing on: "  + "§e§lHYPIXEL.NET" + "\n" + " " + "\"}");
                IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "\n" + "§aDiscord:" + "\n" + "§7Discord not set up yet!" + "\n " + "\n" + "§aRanks, Booster & More!" + "§l§c STORE.HYPIXEL.NET" + "\"}");
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

            }
        }.runTaskLater(main, 5);
    }

    @EventHandler
    public void onTeleport(EntityTeleportEvent e){
        if (e.getEntity().getType().equals(EntityType.ENDERMAN)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCombust(EntityCombustEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) throws IOException {
        SkyblockPlayer skyblockPlayer = main.getPlayer(e.getPlayer().getName());
        skyblockPlayer.saveStats();
        main.players.remove(skyblockPlayer);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) throws IOException {
        Player player = e.getEntity();
        SkyblockPlayer skyblockPlayer = main.getPlayer(player.getName());
        Config.setPurseCoins(player, (int) (Config.getPurseCoins(player)/2));
        player.sendMessage(ChatColor.RED + "You died and lost " + Config.getPurseCoins(player) + " coins!");
        player.playSound(player.getLocation(), Sound.ITEM_BREAK, 100, 1);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player){
            SkyblockPlayer skyblockPlayer = main.getPlayer(e.getDamager().getName());
            int i = new Random().nextInt(100);
            int r = new Random().nextInt(100);
            double damage = 5 + skyblockPlayer.getStat((SkyblockStats.ATTACK_DAMAGE)) + (skyblockPlayer.getStat(SkyblockStats.STRENGTH) / 5) * (1 + skyblockPlayer.getStat(SkyblockStats.STRENGTH) / 100);
            if (!e.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
                if (i <= skyblockPlayer.getStat(SkyblockStats.CRIT_CHANCE)) {
                    e.setDamage(damage * ((100 + skyblockPlayer.getStat(SkyblockStats.CRIT_DAMAGE))) / 100);
                    ItemUtil.setDamageIndicator(e.getEntity().getLocation(), ItemUtil.addCritTexture(String.valueOf(Math.round(e.getDamage()))));
                } else {
                    e.setDamage(damage);
                    ItemUtil.setDamageIndicator(e.getEntity().getLocation(), org.bukkit.ChatColor.GRAY + "" + Math.round(e.getDamage()));
                }
                if (r <= skyblockPlayer.getStat(SkyblockStats.FEROCITY)){
                    if (!skyblockPlayer.ferocityCooldown) {
                        skyblockPlayer.getBukkitPlayer().playSound(skyblockPlayer.getBukkitPlayer().getLocation(), Sound.FIRE_IGNITE, 100, 0);
                        skyblockPlayer.ferocityCooldown = true;
                        Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(skyblockPlayer.getBukkitPlayer(), e.getEntity(), EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage));
                        new BukkitRunnable() {
                            @Override
                            public void run () {
                                skyblockPlayer.ferocityCooldown = false;
                            }
                        }.runTaskLater(main, 5);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent e){
        if (e.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY) || e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH) || e.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT) || e.getState().equals(PlayerFishEvent.State.IN_GROUND)){
            Player player = e.getPlayer();
            Vector vector = player.getLocation().getDirection().multiply(6);
            vector.setY(1);
            player.setVelocity(vector);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        SkyblockPlayer skyblockPlayer = main.getPlayer(player.getName());
        Vector vector = player.getVelocity();
        double blocksPerSecond = vector.length();
        //player.sendMessage(blocksPerSecond + " b/s");
        player.setWalkSpeed(skyblockPlayer.getStat(SkyblockStats.SPEED)/1000F + 0.1F);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if (e.getPlayer().getItemInHand() != null) {
            if (e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Skyblock Menu (Right Click)")) {
                player.performCommand("sbmenu");
            }
        }
    }
}
