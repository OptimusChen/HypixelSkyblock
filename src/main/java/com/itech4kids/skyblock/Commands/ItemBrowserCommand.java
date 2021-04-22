package com.itech4kids.skyblock.Commands;

import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.Items.SkyblockGuiItem;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ItemBrowserCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            SkyblockPlayer skyblockPlayer = Main.getMain().getPlayer(sender.getName());
            skyblockPlayer.setInventory("Item Browser - Page 1", Bukkit.createInventory(null, 54, "Skyblock Menu"));
            Inventory menu = skyblockPlayer.getInventory("Item Browser");

        }
        return false;
    }
}
