package com.itech4kids.skyblock.Commands.Items;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RarityCommand implements CommandExecutor {

    ChatColor color;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0){
            sender.sendMessage(ChatColor.RED + "Please specify a rarity!");
        }else if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.getItemInHand() != null){
                if (player.getInventory().getItemInHand().getItemMeta().getLore() != null) {
                    ItemStack itemStack = player.getItemInHand();
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    List<String> lore = itemMeta.getLore();

                    if (args[0].equalsIgnoreCase("very_special")){
                        color = ChatColor.RED;
                    }else if (args[0].equalsIgnoreCase("special")){
                        color = ChatColor.RED;
                    }else if (args[0].equalsIgnoreCase("supreme")){
                        color = ChatColor.DARK_RED;
                    }else if (args[0].equalsIgnoreCase("mythic")){
                        color = ChatColor.LIGHT_PURPLE;
                    }else if (args[0].equalsIgnoreCase("legendary")){
                        color = ChatColor.GOLD;
                    }else if (args[0].equalsIgnoreCase("epic")){
                        color = ChatColor.DARK_PURPLE;
                    }else if (args[0].equalsIgnoreCase("rare")){
                        color = ChatColor.BLUE;
                    }else if (args[0].equalsIgnoreCase("uncommon")){
                        color = ChatColor.GREEN;
                    }else if (args[0].equalsIgnoreCase("common")){
                        color = ChatColor.WHITE;
                    }

                    for (int i = 0; i < player.getItemInHand().getItemMeta().getLore().size(); ++i){
                        if (ChatColor.stripColor(lore.get(i)).toLowerCase().startsWith("very special")){
                            rarity(itemMeta, lore, args, i, 2);
                        }else if (ChatColor.stripColor(lore.get(i)).toLowerCase().startsWith("special")){
                            rarity(itemMeta, lore, args, i, 1);
                        }else if (ChatColor.stripColor(lore.get(i)).toLowerCase().startsWith("supreme")){
                            rarity(itemMeta, lore, args, i, 1);
                        }else if (ChatColor.stripColor(lore.get(i)).toLowerCase().startsWith("mythic")){
                            rarity(itemMeta, lore, args, i, 1);
                        }else if (ChatColor.stripColor(lore.get(i)).toLowerCase().startsWith("legendary")){
                            rarity(itemMeta, lore, args, i, 1);
                        }else if (ChatColor.stripColor(lore.get(i)).toLowerCase().startsWith("epic")){
                            rarity(itemMeta, lore, args, i, 1);
                        }else if (ChatColor.stripColor(lore.get(i)).toLowerCase().startsWith("rare")){
                            rarity(itemMeta, lore, args, i, 1);
                        }else if (ChatColor.stripColor(lore.get(i)).toLowerCase().startsWith("uncommon")){
                            rarity(itemMeta, lore, args, i, 1);
                        }else if (ChatColor.stripColor(lore.get(i)).toLowerCase().startsWith("common")){
                            rarity(itemMeta, lore, args, i, 1);
                        }
                    }
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                }
            }
        }

        return false;
    }

    public void rarity(ItemMeta itemMeta, List<String> lore, String[] args, int i, int i2){
        itemMeta.setDisplayName(color + ChatColor.stripColor(itemMeta.getDisplayName()));
        if (lore.get(i).contains(" ")) {
            lore.set(i, color + "" + ChatColor.BOLD + args[0].toUpperCase().replaceAll("_", " ") + " " + ChatColor.stripColor(lore.get(i).split(" ")[i2]));
        }else{
            lore.set(i, color + "" + ChatColor.BOLD + args[0].toUpperCase().replaceAll("_", " "));
        }
    }

}
