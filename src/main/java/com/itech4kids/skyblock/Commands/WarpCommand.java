package com.itech4kids.skyblock.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0){
            sender.sendMessage(ChatColor.RED + "Please specify a warp!");
        }else if (sender instanceof Player){
            Player player = (Player) sender;
            if (args[0].equals("hub")){
                player.teleport(new Location(Bukkit.getWorld("world"), -2.5, 70, -69.5, -180, 0));
            }else if (args[0].equals("home")){

            }
        }
        return false;
    }
}
