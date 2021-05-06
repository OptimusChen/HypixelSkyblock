package com.itech4kids.skyblock.Listeners;

import com.itech4kids.skyblock.Util.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.io.IOException;

public class CollectionsListener implements Listener {

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e){
        Player player = e.getPlayer();
        if(e.getItem().equals(null)) { return; }
        if(e.getItem().getItemStack().getTypeId() == 296){
            try {
                Config.setCollectionCollected(player, "farming", "wheat", Config.getCollectionCollected(player, "farming", "wheat") + 1);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            if(Config.getCollectionCollected(player, "farming", "wheat") == 1){
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "COLLECTION UNLOCKED " + ChatColor.YELLOW + " Wheat");
            }
        }
        if(e.getItem().getItemStack().getTypeId() == 391){
            try {
                Config.setCollectionCollected(player, "farming", "carrot", Config.getCollectionCollected(player, "farming", "carrot") + 1);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            if(Config.getCollectionCollected(player, "farming", "carrot") == 1){
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "COLLECTION UNLOCKED " + ChatColor.YELLOW + " Carrot");
            }
        }
        if(e.getItem().getItemStack().getTypeId() == 392){
            try {
                Config.setCollectionCollected(player, "farming", "potato", Config.getCollectionCollected(player, "farming", "potato") + 1);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            if(Config.getCollectionCollected(player, "farming", "potato") == 1){
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "COLLECTION UNLOCKED " + ChatColor.YELLOW + " Potato");
            }
        }
    }
}
