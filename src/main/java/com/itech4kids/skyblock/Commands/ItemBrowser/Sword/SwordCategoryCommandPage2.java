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
import java.util.List;

public class SwordCategoryCommandPage2 implements CommandExecutor {

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
        skyblockPlayer.setInventory("Swords - Page 2", Bukkit.createInventory(null, 54, "Swords - Page 2"));
        Inventory menu = skyblockPlayer.getInventory("Swords - Page 2");

        ItemStack emptySpace = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData());

        for (int index = 0; index < 54; index++) {
            menu.setItem(index, emptySpace);
        }
        List<String> emptyDesc = new ArrayList<>();

        // Descriptions for Swords and Sword Abilities



        // Initialize items
        ItemStack test = new ItemStack(Material.NETHER_STAR, 1);



        // Add items to menu
        menu.setItem(10, test);



        player.openInventory(skyblockPlayer.getInventory("Swords - Page 2"));
        return false;
    }
}