package com.itech4kids.skyblock.Listeners;

import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SkyblockMenuListener implements Listener {

    public Main main;

    public SkyblockMenuListener(Main main){
        this.main = main;
    }

    public void onSkyblockMenuClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        SkyblockPlayer skyblockPlayer = main.getPlayer(player.getName());
        if (e.getInventory().equals(skyblockPlayer.getInventory("Skyblock Menu"))){
            e.setCancelled(true);
            switch (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase())){
                case "your skyblock stats":
                    skyblockPlayer.setInventory("Your Skyblock Profile", Bukkit.createInventory(null, 54, "Your Skyblock Profile"));
                    Inventory menu = skyblockPlayer.getInventory("Your Skyblolck Profile");

                    break;
                case "your skills":
                    skyblockPlayer.setInventory("Your Skills", Bukkit.createInventory(null, 54, "Your Skills"));
                    break;
                case "collections":
                    skyblockPlayer.setInventory("Collection", Bukkit.createInventory(null, 54, "Collection"));
                    break;
                case "recipe book":
                    skyblockPlayer.setInventory("Recipe Book", Bukkit.createInventory(null, 54, "Recipe Book"));
                    break;
                case "trades":
                    skyblockPlayer.setInventory("Trades", Bukkit.createInventory(null, 54, "Trades"));
                    break;
                case "quest log":
                    skyblockPlayer.setInventory("Quest Log", Bukkit.createInventory(null, 54, "Quest Log"));
                    break;
                case "calendar and events":
                    skyblockPlayer.setInventory("Calendar and Events", Bukkit.createInventory(null, 54, "Calendar and Events"));
                    break;
                case "storage":
                    player.openInventory(player.getEnderChest());
                    player.playSound(player.getLocation(), Sound.CHEST_OPEN, 10, 1);
                    break;
                case "active effects":
                    skyblockPlayer.setInventory("Active Effects", Bukkit.createInventory(null, 54, "Active Effects"));
                    break;
                case "pets":
                    skyblockPlayer.setInventory("Pets", Bukkit.createInventory(null, 54, "Pets"));
                    break;
                case "crafting table":
                    player.performCommand("workbench");
                    break;
                case "wardrobe":
                    skyblockPlayer.setInventory("Wardrobe", Bukkit.createInventory(null, 54, "Wardrobe"));
                    break;
                case "fast travel":
                    skyblockPlayer.setInventory("Fast Travel", Bukkit.createInventory(null, 54, "Fast Travel"));
                    break;
                case "profile management":
                    skyblockPlayer.setInventory("Profiles Management", Bukkit.createInventory(null, 54, "Profiles Management"));
                    break;
                case "close":
                    player.closeInventory();
                    break;
                case "settings":
                    skyblockPlayer.setInventory("Settings", Bukkit.createInventory(null, 54, "Settings"));
                    break;
            }
        }
    }

}
