package com.itech4kids.skyblock.Commands;

import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.Items.ItemHandler;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import com.itech4kids.skyblock.Util.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollectionsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command!");
            return true;
        }
        Player player = (Player) sender;
        List<String> rankingsLore = new ArrayList<>();
        rankingsLore.add(ChatColor.GRAY + "View all of the items avaliable");
        rankingsLore.add(ChatColor.GRAY + "in SkyBlock. collect more of an");
        rankingsLore.add(ChatColor.GRAY + "item to unlock rewards on your");
        rankingsLore.add(ChatColor.GRAY + "way to becoming a master of");
        rankingsLore.add(ChatColor.GRAY + "SkyBlock!");
        rankingsLore.add("");
        rankingsLore.add(ChatColor.GRAY + "Collection Unlocked: " + ChatColor.YELLOW + "0.0" + ChatColor.GOLD + "%");
        rankingsLore.add(ChatColor.WHITE + "-------------------- " + ChatColor.YELLOW + "0" + ChatColor.GOLD + "/" + ChatColor.YELLOW + "0");
        rankingsLore.add("");
        rankingsLore.add(ChatColor.YELLOW + "Click to show rankings!");
        List<String> farmingCollectionLore = new ArrayList<>();
        farmingCollectionLore.add(ChatColor.GRAY + "View your Farming Collection!");
        farmingCollectionLore.add("");
        farmingCollectionLore.add(ChatColor.GRAY + "Collection Unlocked: " + ChatColor.YELLOW + "0.0" + ChatColor.GOLD + "%");
        farmingCollectionLore.add(ChatColor.WHITE + "-------------------- " + ChatColor.YELLOW + "0" + ChatColor.GOLD + "/" + ChatColor.YELLOW + "0");
        farmingCollectionLore.add("");
        farmingCollectionLore.add(ChatColor.YELLOW + "Click to view!");
        List<String> miningCollectionLore = new ArrayList<>();
        miningCollectionLore.add(ChatColor.GRAY + "View your Mining Collection!");
        miningCollectionLore.add("");
        miningCollectionLore.add(ChatColor.GRAY + "Collection Unlocked: " + ChatColor.YELLOW + "0.0" + ChatColor.GOLD + "%");
        miningCollectionLore.add(ChatColor.WHITE + "-------------------- " + ChatColor.YELLOW + "0" + ChatColor.GOLD + "/" + ChatColor.YELLOW + "0");
        miningCollectionLore.add("");
        miningCollectionLore.add(ChatColor.YELLOW + "Click to view!");
        List<String> combatCollectionLore = new ArrayList<>();
        combatCollectionLore.add(ChatColor.GRAY + "View your Combat Collection!");
        combatCollectionLore.add("");
        combatCollectionLore.add(ChatColor.GRAY + "Collection Unlocked: " + ChatColor.YELLOW + "0.0" + ChatColor.GOLD + "%");
        combatCollectionLore.add(ChatColor.WHITE + "-------------------- " + ChatColor.YELLOW + "0" + ChatColor.GOLD + "/" + ChatColor.YELLOW + "0");
        combatCollectionLore.add("");
        combatCollectionLore.add(ChatColor.YELLOW + "Click to view!");
        List<String> foragingCollectionLore = new ArrayList<>();
        foragingCollectionLore.add(ChatColor.GRAY + "View your Foraging Collection!");
        foragingCollectionLore.add("");
        foragingCollectionLore.add(ChatColor.GRAY + "Collection Unlocked: " + ChatColor.YELLOW + "0.0" + ChatColor.GOLD + "%");
        foragingCollectionLore.add(ChatColor.WHITE + "-------------------- " + ChatColor.YELLOW + "0" + ChatColor.GOLD + "/" + ChatColor.YELLOW + "0");
        foragingCollectionLore.add("");
        foragingCollectionLore.add(ChatColor.YELLOW + "Click to view!");
        List<String> fishingCollectionLore = new ArrayList<>();
        fishingCollectionLore.add(ChatColor.GRAY + "View your Fishing Collection!");
        fishingCollectionLore.add("");
        fishingCollectionLore.add(ChatColor.GRAY + "Collection Unlocked: " + ChatColor.YELLOW + "0.0" + ChatColor.GOLD + "%");
        fishingCollectionLore.add(ChatColor.WHITE + "-------------------- " + ChatColor.YELLOW + "0" + ChatColor.GOLD + "/" + ChatColor.YELLOW + "0");
        fishingCollectionLore.add("");
        fishingCollectionLore.add(ChatColor.YELLOW + "Click to view!");
        List<String> bossCollectionLore = new ArrayList<>();
        bossCollectionLore.add(ChatColor.GRAY + "View your progress and claim");
        bossCollectionLore.add(ChatColor.GRAY + "rewards you have obtained from");
        bossCollectionLore.add(ChatColor.GRAY + "defeating SkyBlock bosses!");
        bossCollectionLore.add("");
        bossCollectionLore.add(ChatColor.GRAY + "Boss Collection Unlocked: " + ChatColor.YELLOW + "0.0" + ChatColor.GOLD + "%");
        bossCollectionLore.add(ChatColor.WHITE + "-------------------- " + ChatColor.YELLOW + "0" + ChatColor.GOLD + "/" + ChatColor.YELLOW + "0");
        bossCollectionLore.add("");
        bossCollectionLore.add(ChatColor.YELLOW + "Click to view!");
        if(args.length == 1){
            if(args[0].toLowerCase().equals("main")){
                SkyblockPlayer skyblockPlayer = Main.getMain().getPlayer(player.getName());
                skyblockPlayer.setInventory("Collection", Bukkit.createInventory(null, 54, "Collection"));
                Inventory menu = skyblockPlayer.getInventory("Collection");

                ItemStack emptySpace = ItemHandler.createEmptySpace();

                ItemStack rankings = ItemHandler.createBasicItem(Material.PAINTING, ChatColor.GREEN + "Collection", rankingsLore, (short) 0,false, 1);
                ItemStack farming_collection = ItemHandler.createBasicItem(Material.GOLD_HOE, ChatColor.GREEN + "Farming Collection", farmingCollectionLore, (short) 0, false, 1);
                ItemStack mining_collection = ItemHandler.createBasicItem(Material.STONE_PICKAXE, ChatColor.GREEN + "Mining Collection", farmingCollectionLore, (short) 0, false, 1);
                ItemStack combat_collection = ItemHandler.createBasicItem(Material.STONE_SWORD, ChatColor.GREEN + "Combat Collection", farmingCollectionLore, (short) 0, false, 1);
                ItemStack foraging_collection = ItemHandler.createBasicItem(Material.SAPLING, ChatColor.GREEN + "Foraging Collection", farmingCollectionLore, (short) 3, false, 1);
                ItemStack fishing_collection = ItemHandler.createBasicItem(Material.FISHING_ROD, ChatColor.GREEN + "Fishing Collection", farmingCollectionLore, (short) 0, false, 1);
                ItemStack boss_collection = ItemHandler.createBasicHead(ChatColor.DARK_PURPLE + "Boss Collection", bossCollectionLore, 1, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk1M2I2YzY4NDQ4ZTdlNmI2YmY4ZmIyNzNkNzIwM2FjZDhlMWJlMTllODE0ODFlYWQ1MWY0NWRlNTlhOCJ9fX0=", 1);

                for(int i = 0; i < 54; i++) {
                    menu.setItem(menu.firstEmpty(), emptySpace);
                }

                player.sendMessage(ChatColor.GREEN + "Cobble level: " + Config.getCollectionLevel(player, "cobblestone"));
                player.sendMessage(ChatColor.GREEN + "Cobble collected: " + Config.getCollectionCollected(player, "cobblestone"));

                menu.setItem(4, rankings);
                menu.setItem(20, farming_collection);
                menu.setItem(21, mining_collection);
                menu.setItem(22, combat_collection);
                menu.setItem(23, foraging_collection);
                menu.setItem(24, fishing_collection);
                menu.setItem(31, boss_collection);
                menu.setItem(48, ItemHandler.createPageBackArrow("SkyBlock Menu"));
                menu.setItem(49, ItemHandler.createExitBarrier());

                player.openInventory(skyblockPlayer.getInventory("Collection"));
                return false;
            } else if(args[0].toLowerCase().equals("farming")){
                player.sendMessage("farming");
            } else if(args[0].toLowerCase().equals("mining")){
                player.sendMessage("mining");
            } else if(args[0].toLowerCase().equals("combat")){
                player.sendMessage("combat");
            } else if(args[0].toLowerCase().equals("foraging")){
                player.sendMessage("foraging");
            } else if(args[0].toLowerCase().equals("fishing")){
                player.sendMessage("fishing");
            } else if(args[0].toLowerCase().equals("boss")){
                player.sendMessage("boss");
            } else if(args[0].toLowerCase().equals("help")){

            }
            else{
                player.sendMessage(ChatColor.RED + "Incorrect Usage!\n/collection <menu_name | set to help for list of menus>");
                return true;
            }
        } else{
            player.sendMessage(ChatColor.RED + "Incorrect Usage!\n/collection <menu_name | set to help for list of menus>");
            return true;
        }
        return false;
    }
}