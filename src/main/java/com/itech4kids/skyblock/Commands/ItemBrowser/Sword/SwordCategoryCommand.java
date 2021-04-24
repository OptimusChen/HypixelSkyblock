package com.itech4kids.skyblock.Commands.ItemBrowser.Sword;

import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.Items.Item;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SwordCategoryCommand implements CommandExecutor {

    private Item item;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        this.item = item;
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "[Skyblock] Only players can use this command!");
            return true;
        }
        Player player = (Player) sender;
        SkyblockPlayer skyblockPlayer = Main.getMain().getPlayer(player.getName());
        skyblockPlayer.setInventory("Swords", Bukkit.createInventory(null, 54, "Swords"));
        Inventory menu = skyblockPlayer.getInventory("Swords");

        ItemStack emptySpace = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData());

        for (int index = 0; index < 54; index++) {
            menu.setItem(index, emptySpace);
        }
        List<String> emptyDesc = new ArrayList<>();

        // Descriptions for Swords and Sword Abilities
        List<String> fancySwordDescription = new ArrayList<>();
        fancySwordDescription.add(ChatColor.BLUE + "Scavenger I");
        fancySwordDescription.add(ChatColor.GRAY + "Scavenge" + ChatColor.GOLD + " 0.5 Coins " + ChatColor.GRAY + "per");
        fancySwordDescription.add(ChatColor.GRAY + "monster level on kill.");
        fancySwordDescription.add(ChatColor.BLUE + "Sharpness II");
        fancySwordDescription.add(ChatColor.GRAY + "Increases melee damage dealt by");
        fancySwordDescription.add(ChatColor.GREEN + "10%");
        fancySwordDescription.add(ChatColor.BLUE + "First Strike I");
        fancySwordDescription.add(ChatColor.GRAY + "Increases melee damage dealt by");
        fancySwordDescription.add(ChatColor.GREEN + "25% " + ChatColor.GRAY + "for the first hit on a");
        fancySwordDescription.add(ChatColor.GRAY + "mob.");
        fancySwordDescription.add(ChatColor.BLUE + "Vampirism I");
        fancySwordDescription.add(ChatColor.GRAY + "Heals for " + ChatColor.GREEN + "1% " + ChatColor.GRAY + "of your missing");
        fancySwordDescription.add(ChatColor.GRAY + "health whenever you kill an");
        fancySwordDescription.add(ChatColor.GRAY + "enemy.");
        List<String> rogueSwordAbilityDesc = new ArrayList<>();
        rogueSwordAbilityDesc.add(ChatColor.GRAY + "Increases your movement " + ChatColor.WHITE + "✦");
        rogueSwordAbilityDesc.add(ChatColor.WHITE + "Speed " + ChatColor.GRAY + "by" + ChatColor.GREEN + " +20 " + ChatColor.GRAY + "for" + ChatColor.GREEN + " 30");
        rogueSwordAbilityDesc.add(ChatColor.GRAY + "seconds.");
        List<String> spiderSwordDesc = new ArrayList<>();
        spiderSwordDesc.add(ChatColor.GRAY + "Deals " + ChatColor.GREEN + "+100% " + ChatColor.GRAY + "damage to Cave");
        spiderSwordDesc.add(ChatColor.GRAY + "Spiders, Silverfish, and");
        spiderSwordDesc.add(ChatColor.GRAY + "Spiders.");
        List<String> undeadSwordDesc = new ArrayList<>();
        undeadSwordDesc.add(ChatColor.GRAY + "Deals " + ChatColor.GREEN + "+100% " + ChatColor.GRAY + "damage to");
        undeadSwordDesc.add(ChatColor.GRAY + "Skeletons, Withers,");
        undeadSwordDesc.add(ChatColor.GRAY + "Zombie Pigmen, and");
        undeadSwordDesc.add(ChatColor.GRAY + "Zombies.");
        List<String> endSwordDesc = new ArrayList<>();
        endSwordDesc.add(ChatColor.GRAY + "Deals " + ChatColor.GREEN + "+100% " + ChatColor.GRAY + "damage to");
        endSwordDesc.add(ChatColor.GRAY + "Endermites, Ender");
        endSwordDesc.add(ChatColor.GRAY + "Dragons, and Endermen.");
        List<String> cleaverAbilityDesc = new ArrayList<>();
        cleaverAbilityDesc.add(ChatColor.GRAY + "When hitting an entity, monsters");
        cleaverAbilityDesc.add(ChatColor.GRAY + "in a " + ChatColor.GREEN + "3 " + ChatColor.GRAY + "block range will be");
        cleaverAbilityDesc.add(ChatColor.GRAY + "hit for a portion of that damage");
        cleaverAbilityDesc.add(ChatColor.GRAY + "too.");
        List<String> taticianSwordDesc = new ArrayList<>();
        taticianSwordDesc.add(ChatColor.GRAY + "Gains " + ChatColor.RED + "+15 Damage " + ChatColor.GRAY + "for");
        taticianSwordDesc.add(ChatColor.GRAY + "each Combat collection of");
        taticianSwordDesc.add(ChatColor.GRAY + "Tier VII and over of its");
        taticianSwordDesc.add(ChatColor.GRAY + "wearer.");
        taticianSwordDesc.add("");
        taticianSwordDesc.add(ChatColor.GRAY + "Your collections: " + ChatColor.YELLOW + "0" + ChatColor.GRAY + "/" + ChatColor.YELLOW + "10");
        List<String> emberRodAbilityDesc = new ArrayList<>();
        emberRodAbilityDesc.add(ChatColor.GRAY + "Shoot 3 Fireballs which deal");
        emberRodAbilityDesc.add(ChatColor.RED + "30" + ChatColor.GRAY + "damage in rapid");
        emberRodAbilityDesc.add(ChatColor.GRAY + "succession in front of you!");
        List<String> frozenScytheAbilityDesc = new ArrayList<>();
        frozenScytheAbilityDesc.add(ChatColor.GRAY + "Shoots " + ChatColor.GREEN + "1 " + ChatColor.GRAY + "Ice Bolt that deals");
        frozenScytheAbilityDesc.add(ChatColor.RED + "1,000" + ChatColor.GRAY + " damage and slows");
        frozenScytheAbilityDesc.add(ChatColor.GRAY + "enemies hit for " + ChatColor.GREEN + "5 " + ChatColor.GRAY + "seconds!");
        List<String> golemSwordAbilityDesc = new ArrayList<>();
        golemSwordAbilityDesc.add(ChatColor.GRAY + "Punch the ground, damaging");
        golemSwordAbilityDesc.add(ChatColor.GRAY + "enemies in a hexagon around you");
        golemSwordAbilityDesc.add(ChatColor.GRAY + "for " + ChatColor.GREEN + "255 " + ChatColor.GRAY + "base Magic Damage.");


        // Initialize items
        ItemStack aspect_of_the_jerry = item.createInGameItem(Material.WOOD_SWORD, ChatColor.WHITE + "Aspect of the Jerry", "", 1, emptyDesc, false, true, "Parley", Collections.singletonList(ChatColor.GRAY + "Channel your inner Jerry."), "RIGHT CLICK", 0, "5s", "COMMON SWORD", 1, 0, 0, 0, 0, 0, 0,0,true);
        ItemStack fancy_sword = item.createInGameItem(Material.GOLD_SWORD, ChatColor.WHITE + "Fancy Sword", "", 1, fancySwordDescription, true, false, "", Collections.singletonList(""), "", 0, "", "COMMON SWORD", 20, 0, 0, 0, 0, 0, 0,0,true);
        ItemStack rogue_sword = item.createInGameItem(Material.GOLD_SWORD, ChatColor.WHITE + "Rogue Sword", "", 1, emptyDesc, false, true, "Speed Boost", rogueSwordAbilityDesc, "RIGHT CLICK", 50, "0s", "COMMON SWORD", 20, 0,0,0,0,0, 0,0,true);
        ItemStack spider_sword = item.createInGameItem(Material.IRON_SWORD, ChatColor.WHITE + "Spider Sword", "", 1, spiderSwordDesc, false, false, "", Collections.singletonList(""), "", 0, "", "COMMON SWORD", 30,0,0,0,0,0, 0,0,true);
        ItemStack undead_sword = item.createInGameItem(Material.IRON_SWORD, ChatColor.WHITE + "Undead Sword", "", 1, undeadSwordDesc, false, false, "", Collections.singletonList(""), "", 0, "", "COMMON SWORD", 30,0,0,0,0,0, 0,0,true);
        ItemStack end_sword = item.createInGameItem(Material.DIAMOND_SWORD, ChatColor.GREEN + "End Sword", "", 1, endSwordDesc, false, false, "", Collections.singletonList(""), "", 0, "", "UNCOMMON SWORD", 35, 0,0,0,0,0, 0,0,true);
        ItemStack cleaver = item.createInGameItem(Material.GOLD_SWORD, ChatColor.GREEN + "Cleaver", "", 1, emptyDesc, false, true, "Cleave", cleaverAbilityDesc, "RIGHT CLICK", 0, "", "UNCOMMON SWORD", 60, 10, 0,0,0,0, 0,0,true);
        ItemStack flaming_sword = item.createInGameItem(Material.IRON_SWORD, ChatColor.GREEN + "Flaming Sword", "", 1, Collections.singletonList(ChatColor.GRAY + "Ignites enemies for " + ChatColor.GREEN + "3s" + ChatColor.GRAY + "."), false, false, "", Collections.singletonList(""),"", 0, "", "UNCOMMON SWORD", 50, 20, 0,0,0,0,0,0,true);
        ItemStack prismarine_blade = item.createInGameItem(Material.PRISMARINE_SHARD, ChatColor.GREEN + "Prismarine Blade", "", 1, Collections.singletonList(ChatColor.GRAY + "Deals " + ChatColor.GREEN + "+200% " + ChatColor.GRAY + "damage while in water."), false, false, "", Collections.singletonList(""), "", 0, "", "UNCOMMON", 50,25, 0,0,0,0,0,0,true);
        ItemStack hunter_knife = item.createInGameItem(Material.IRON_SWORD, ChatColor.GREEN + "Hunter Knife", "", 1, Collections.singletonList(""), false, false, "", Collections.singletonList(""), "", 0,"", "UNCOMMON SWORD", 50, 0,0,0,0,0,40, 0,true);
        ItemStack tatician_sword = item.createInGameItem(Material.WOOD_SWORD, ChatColor.BLUE + "Tatician's Sword", "", 1, taticianSwordDesc,false, false, "", Collections.singletonList(""), "", 0, "", "RARE SWORD", 50, 0, 20, 0,0,0,0, 0,true);
        ItemStack thick_tatician_sword = item.createInGameItem(Material.WOOD_SWORD, ChatColor.DARK_PURPLE + "Thick Tatician's Sword", "", 1, taticianSwordDesc,true, false, "", Collections.singletonList(""), "", 0, "", "EPIC SWORD", 50, 100, 20, 0,0,0,0, 0,true);
        ItemStack ember_rod = item.createInGameItem(Material.BLAZE_ROD, ChatColor.DARK_PURPLE + "Ember Rod", "", 1, Collections.singletonList(""), true, true, "Fire Blast", emberRodAbilityDesc, "RIGHT CLICK", 150, "30s", "EPIC SWORD", 80, 35, 0 ,0 ,0,50,0, 0,true);
        ItemStack frozen_scythe = item.createInGameItem(Material.IRON_HOE, ChatColor.BLUE + "Frozen Scythe", "", 1, Collections.singletonList(""), false, true, "Ice Bolt", frozenScytheAbilityDesc, "RIGHT CLICK", 50, "", "RARE SWORD", 80,0,0,0,0,0,0, 0,true);
        ItemStack golem_sword = item.createInGameItem(Material.IRON_SWORD, ChatColor.BLUE + "Golem Sword", "", 1, Collections.singletonList(""), false, true, "Iron Punch", golemSwordAbilityDesc, "RIGHT CLICK", 70, "3s", "RARE SWORD", 80, 125, 0 ,0 ,0 ,0 ,0, 25, true);

        // Navigation items
        ItemStack go_back = item.createPageBackArrow("Item Browser");
        ItemStack close = item.createExitBarrier();
        ItemStack next_page = item.createNavigationArrow("next", 2);

        // Add items to menu
        menu.setItem(10, aspect_of_the_jerry);
        menu.setItem(11, fancy_sword);
        menu.setItem(12, rogue_sword);
        menu.setItem(13, spider_sword);
        menu.setItem(14, undead_sword);
        menu.setItem(15, end_sword);
        menu.setItem(16, cleaver);
        menu.setItem(19, flaming_sword);
        menu.setItem(20, prismarine_blade);
        menu.setItem(21, hunter_knife);
        menu.setItem(22, tatician_sword);
        menu.setItem(23, thick_tatician_sword);
        menu.setItem(24, ember_rod);
        menu.setItem(25, frozen_scythe);
        menu.setItem(28, golem_sword);

        // Add navigation items to the menu
        menu.setItem(48, go_back);
        menu.setItem(49, close);
        menu.setItem(53, next_page);

        player.openInventory(skyblockPlayer.getInventory("Swords"));
        return false;
    }
}