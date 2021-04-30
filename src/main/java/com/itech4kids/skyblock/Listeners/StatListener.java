package com.itech4kids.skyblock.Listeners;

import com.inkzzz.spigot.armorevent.PlayerArmorEquipEvent;
import com.inkzzz.spigot.armorevent.PlayerArmorUnequipEvent;
import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.Pets.SkyblockPet;
import com.itech4kids.skyblock.Util.Config;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ItemArmor;
import net.minecraft.server.v1_8_R3.ItemSkull;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.Arrays;

public class StatListener implements Listener {

    @EventHandler
    public void onSwitch(PlayerItemHeldEvent e){
        Player player = e.getPlayer();
        if (player.getInventory().getItem(e.getNewSlot()) != null) {
            Main.getMain().getPlayer(player.getName()).itemInHand = player.getInventory().getItem(e.getNewSlot());
            if (CraftItemStack.asNMSCopy(player.getInventory().getItem(e.getNewSlot())).getItem() instanceof ItemArmor) {
                Main.getMain().updateStats(Main.getMain().getPlayer(e.getPlayer().getName()), null, e.getPlayer().getInventory().getItem(e.getPreviousSlot()));
                Main.getMain().updateActionbar(Main.getMain().getPlayer(e.getPlayer().getName()));
            }else{
                Main.getMain().updateStats(Main.getMain().getPlayer(e.getPlayer().getName()), e.getPlayer().getInventory().getItem(e.getNewSlot()), e.getPlayer().getInventory().getItem(e.getPreviousSlot()));
                Main.getMain().updateActionbar(Main.getMain().getPlayer(e.getPlayer().getName()));
            }
        }else{
            Main.getMain().updateStats(Main.getMain().getPlayer(e.getPlayer().getName()), e.getPlayer().getInventory().getItem(e.getNewSlot()), e.getPlayer().getInventory().getItem(e.getPreviousSlot()));
            Main.getMain().updateActionbar(Main.getMain().getPlayer(e.getPlayer().getName()));
        }
    }

    @EventHandler
    public void onArmorEquip(PlayerArmorEquipEvent e){
        Main.getMain().updateStats(Main.getMain().getPlayer(e.getPlayer().getName()), e.getItemStack(), null);
        Main.getMain().updateActionbar(Main.getMain().getPlayer(e.getPlayer().getName()));
    }

    @EventHandler
    public void onArmorUnEquip(PlayerArmorUnequipEvent e){
        if (!Arrays.asList(e.getPlayer().getInventory().getArmorContents()).contains(e.getItemStack())) {
            Main.getMain().updateStats(Main.getMain().getPlayer(e.getPlayer().getName()), null, e.getItemStack());
            Main.getMain().updateActionbar(Main.getMain().getPlayer(e.getPlayer().getName()));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        for (ItemStack itemStack : e.getPlayer().getInventory().getArmorContents()){
            Bukkit.getPluginManager().callEvent(new PlayerArmorUnequipEvent(e.getPlayer(), itemStack));
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e){
        if (CraftItemStack.asNMSCopy(Main.getMain().getPlayer(e.getPlayer().getName()).itemInHand).getItem() instanceof ItemArmor || CraftItemStack.asNMSCopy(Main.getMain().getPlayer(e.getPlayer().getName()).itemInHand).getItem() instanceof ItemSkull) {
        }else {
            if (Main.getMain().getPlayer(e.getPlayer().getName()).itemInHand.equals(e.getItemDrop().getItemStack())) {
                Main.getMain().updateStats(Main.getMain().getPlayer(e.getPlayer().getName()), null, e.getItemDrop().getItemStack());
                Main.getMain().updateActionbar(Main.getMain().getPlayer(e.getPlayer().getName()));
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCursor();
        ItemStack itemStack1 = e.getCurrentItem();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getItemInHand().getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())) {

                    Main.getMain().updateStats(Main.getMain().getPlayer(player.getName()), player.getItemInHand(), itemStack1);
                    Main.getMain().updateActionbar(Main.getMain().getPlayer(player.getName()));
                }
            }
        }.runTaskLater(Main.getMain(), 1);
    }

    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent e) {
        Main.getMain().getPlayer(e.getPlayer().getName()).lastPickedUp = e.getItem().getItemStack();

        new BukkitRunnable() {
            @Override
            public void run() {

                if (CraftItemStack.asNMSCopy(e.getPlayer().getItemInHand()).getItem() instanceof ItemArmor || CraftItemStack.asNMSCopy(e.getPlayer().getItemInHand()).getItem() instanceof ItemSkull) {
                }else{
                    if (e.getPlayer().getItemInHand().getItemMeta().equals(Main.getMain().getPlayer(e.getPlayer().getName()).lastPickedUp.getItemMeta())) {
                        Main.getMain().updateStats(Main.getMain().getPlayer(e.getPlayer().getName()), Main.getMain().getPlayer(e.getPlayer().getName()).lastPickedUp, null);
                        Main.getMain().updateActionbar(Main.getMain().getPlayer(e.getPlayer().getName()));
                    }
                }
            }
        }.runTaskLater(Main.getMain(), 1);
    }
}
