package com.itech4kids.skyblock.Listeners;

import com.itech4kids.skyblock.Commands.TodoListCommand;
import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.Items.Item;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {

    private Item item;
    private TodoListCommand todoList;

    @EventHandler
    public void onClick(InventoryClickEvent event){
        this.item = item;
        Player player = (Player) event.getWhoClicked();
        SkyblockPlayer skyblockPlayer = Main.getMain().getPlayer(player.getName());

        if(event.getInventory() == null) { return; }
        if(event.getInventory().getName().equals("Item Browser")){
            event.setCancelled(true);
            if(event.getCurrentItem() == null) { return; }
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "swords")){
                player.sendMessage(ChatColor.YELLOW + "Opening sword category...");
                player.performCommand("swordcategory");
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "helmets")){
                player.sendMessage(ChatColor.YELLOW + "Opening helmet category...");
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "chestplates")){
                player.sendMessage(ChatColor.YELLOW + "Opening chestplate category...");
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "leggings")){
                player.sendMessage(ChatColor.YELLOW + "Opening leggings category...");
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "boots")){
                player.sendMessage(ChatColor.YELLOW + "Opening boots category...");
            }
        } else if(event.getInventory().getName().equals("Swords")){
            event.setCancelled(true);
            if(event.getCurrentItem() == null) { return; }
            if(!(event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) && !(event.getCurrentItem().getType().equals(Material.ARROW)) && !(event.getCurrentItem().getType().equals(Material.BARRIER))){
                String old_item_name = event.getCurrentItem().getItemMeta().getDisplayName();
                String new_item_name=old_item_name.replace(' ','_');
                player.sendMessage(ChatColor.GREEN + "Giving one of " + ChatColor.YELLOW + new_item_name.toUpperCase() + ChatColor.GREEN + " to " + ChatColor.WHITE + player.getName());
                player.getInventory().addItem(event.getCurrentItem());
            } else if(event.getCurrentItem().getType().equals(Material.BARRIER)){
                player.closeInventory();
            } else if(event.getCurrentItem().getType().equals(Material.ARROW) && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "go back")){
                player.performCommand("itembrowser");
            } else if(event.getCurrentItem().getType().equals(Material.ARROW) && event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "next page")){
                player.performCommand("swordcategory2");
            }
        } else if(event.getInventory().getName().equalsIgnoreCase("Todo List")){
            event.setCancelled(true);
            if(event.getCurrentItem() == null) { return; }
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "swords - not finished")){
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE + "TodoList" + ChatColor.DARK_PURPLE + "] " + player.getName() + " Completed the swords task!");
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 100, 2);
                todoList.setSwordtask(true);
                System.out.println("TodoList Swords task value: " + todoList.getSwordTask());
            }
        }
    }
}