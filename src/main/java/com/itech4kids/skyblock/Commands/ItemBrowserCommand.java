package com.itech4kids.skyblock.Commands;

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
import java.util.List;

public class ItemBrowserCommand implements CommandExecutor {

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
        skyblockPlayer.setInventory("Item Browser", Bukkit.createInventory(null, 54, "Item Browser"));
        Inventory menu = skyblockPlayer.getInventory("Item Browser");

        ItemStack emptySpace = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData());

        for (int index = 0; index < 54; index++) {
            menu.setItem(index, emptySpace);
        }
        // Sword category
        List<String> swordCategoryLore = new ArrayList<>();
        swordCategoryLore.add(ChatColor.GRAY + "Click to view the");
        swordCategoryLore.add(ChatColor.GRAY + "Sword Category!");
        swordCategoryLore.add("");
        swordCategoryLore.add(ChatColor.YELLOW + "Click to view!");
        ItemStack swordCategoryItem = item.createBasicItem(Material.DIAMOND_SWORD, ChatColor.GREEN + "Swords", swordCategoryLore, (short) 0,false);

        // Helmet category
        List<String> helmetCategoryLore = new ArrayList<>();
        helmetCategoryLore.add(ChatColor.GRAY + "Click to view the");
        helmetCategoryLore.add(ChatColor.GRAY + "Helmet Category!");
        helmetCategoryLore.add("");
        helmetCategoryLore.add(ChatColor.YELLOW + "Click to view!");
        ItemStack helmetCategoryItem = item.createBasicItem(Material.LEATHER_HELMET, ChatColor.GREEN + "Helmets", helmetCategoryLore, (short) 15, false);

        // Chestplate category
        List<String> chestplateCategoryLore = new ArrayList<>();
        chestplateCategoryLore.add(ChatColor.GRAY + "Click to view the");
        chestplateCategoryLore.add(ChatColor.GRAY + "Chestplate Category!");
        chestplateCategoryLore.add("");
        chestplateCategoryLore.add(ChatColor.YELLOW + "Click to view!");
        ItemStack chestplateCategoryItem = item.createBasicItem(Material.LEATHER_CHESTPLATE, ChatColor.GREEN + "Chestplates", chestplateCategoryLore,  (short) 15,false);

        // Leggings category
        List<String> leggingsCategoryLore = new ArrayList<>();
        leggingsCategoryLore.add(ChatColor.GRAY + "Click to view the");
        leggingsCategoryLore.add(ChatColor.GRAY + "Leggings Category!");
        leggingsCategoryLore.add("");
        leggingsCategoryLore.add(ChatColor.YELLOW + "Click to view!");
        ItemStack leggingsCategoryItem = item.createBasicItem(Material.LEATHER_LEGGINGS, ChatColor.GREEN + "Leggings", leggingsCategoryLore,  (short) 15,false);

        // Boots category
        List<String> bootsCategoryLore = new ArrayList<>();
        bootsCategoryLore.add(ChatColor.GRAY + "Click to view the");
        bootsCategoryLore.add(ChatColor.GRAY + "Boots Category!");
        bootsCategoryLore.add("");
        bootsCategoryLore.add(ChatColor.YELLOW + "Click to view!");
        ItemStack bootsCategoryItem = item.createBasicItem(Material.LEATHER_BOOTS, ChatColor.GREEN + "Boots", bootsCategoryLore,  (short) 15,false);

        // Add categories to the item browser
        menu.setItem(10, swordCategoryItem);
        menu.setItem(11, helmetCategoryItem);
        menu.setItem(12, chestplateCategoryItem);
        menu.setItem(13,leggingsCategoryItem);
        menu.setItem(14,bootsCategoryItem);

        player.openInventory(skyblockPlayer.getInventory("Item Browser"));
        return false;
    }

}
