package com.itech4kids.skyblock.Commands.Items;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class RecombobulateCommand implements CommandExecutor {

    public boolean b = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.getItemInHand() != null){
                if (player.getItemInHand().getItemMeta().getLore() != null){
                    ItemMeta itemMeta = player.getItemInHand().getItemMeta();
                    List<String> lore = player.getItemInHand().getItemMeta().getLore();
                    for (int i = 0; i < player.getItemInHand().getItemMeta().getLore().size(); ++i){
                        if (lore.get(i).startsWith(ChatColor.MAGIC + "a ")){
                            b = true;
                            break;
                        }
                        if (ChatColor.stripColor(lore.get(i)).startsWith("VERY SPECIAL")){
                            if (!b) {
                                player.sendMessage(ChatColor.RED + "This item is at the max rarity!");
                                break;
                            }
                        }else if (ChatColor.stripColor(lore.get(i)).startsWith("SPECIAL")){
                            if (!b) {
                                itemMeta.setDisplayName(ChatColor.RED + ChatColor.stripColor(itemMeta.getDisplayName()));
                                lore.set(i, ChatColor.RED + "" + ChatColor.MAGIC + "a " + ChatColor.RED + " " + ChatColor.BOLD + ChatColor.stripColor(lore.get(i)).replaceAll("SPECIAL", "VERY SPECIAL") + " " + ChatColor.MAGIC + "a");
                                player.updateInventory();
                                break;
                            }
                        }else if (ChatColor.stripColor(lore.get(i)).startsWith("MYTHIC")){
                            if (!b) {
                                itemMeta.setDisplayName(ChatColor.DARK_RED + ChatColor.stripColor(itemMeta.getDisplayName()));
                                lore.set(i, ChatColor.DARK_RED + "" + ChatColor.MAGIC + "a " + ChatColor.DARK_RED + " " + ChatColor.BOLD + ChatColor.stripColor(lore.get(i)).replaceAll("MYTHIC", "SUPREME") + " " + ChatColor.MAGIC + "a");
                                player.updateInventory();
                                break;
                            }
                        }else if (ChatColor.stripColor(lore.get(i)).startsWith("LEGENDARY")) {
                            if (!b) {
                                itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + ChatColor.stripColor(itemMeta.getDisplayName()));
                                lore.set(i, ChatColor.LIGHT_PURPLE + "" + ChatColor.MAGIC + "a " + ChatColor.LIGHT_PURPLE + " " + ChatColor.BOLD + ChatColor.stripColor(lore.get(i)).replaceAll("LEGENDARY", "MYTHIC") + " " + ChatColor.MAGIC + "a");
                                player.updateInventory();
                                break;
                            }
                        }else if (ChatColor.stripColor(lore.get(i)).startsWith("EPIC")){
                            if (!b) {
                                itemMeta.setDisplayName(ChatColor.GOLD + ChatColor.stripColor(itemMeta.getDisplayName()));
                                lore.set(i, ChatColor.GOLD + "" + ChatColor.MAGIC + "a " + ChatColor.GOLD + " " + ChatColor.BOLD + ChatColor.stripColor(lore.get(i)).replaceAll("EPIC", "LEGENDARY") + " " + ChatColor.MAGIC + "a");
                                player.updateInventory();
                                break;
                            }
                        }else if (ChatColor.stripColor(lore.get(i)).startsWith("RARE")){
                            if (!b) {
                                itemMeta.setDisplayName(ChatColor.DARK_PURPLE + ChatColor.stripColor(ChatColor.stripColor(itemMeta.getDisplayName())));
                                lore.set(i, ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "a " + ChatColor.DARK_PURPLE + " " + ChatColor.BOLD + ChatColor.stripColor(lore.get(i)).replaceAll("RARE", "EPIC") + " " + ChatColor.MAGIC + "a");
                                player.updateInventory();

                                break;
                            }
                        }else if (ChatColor.stripColor(lore.get(i)).startsWith("UNCOMMON")){
                            if (!b) {
                                itemMeta.setDisplayName(ChatColor.BLUE + ChatColor.stripColor(itemMeta.getDisplayName()));
                                lore.set(i, ChatColor.BLUE + "" + ChatColor.MAGIC + "a " + ChatColor.BLUE + " " + ChatColor.BOLD + ChatColor.stripColor(lore.get(i)).replaceAll("UNCOMMON", "RARE") + " " + ChatColor.MAGIC + "a");
                                player.updateInventory();
                                break;
                            }
                        }else if (ChatColor.stripColor(lore.get(i)).startsWith("COMMON")){
                            if (!b) {
                                itemMeta.setDisplayName(ChatColor.GREEN + ChatColor.stripColor(itemMeta.getDisplayName()));
                                lore.set(i, ChatColor.GREEN + "" + ChatColor.MAGIC + "a " + ChatColor.GREEN + " " + ChatColor.BOLD + ChatColor.stripColor(lore.get(i)).replaceAll("COMMON", "UNCOMMON") + " " + ChatColor.MAGIC + "a");
                                player.updateInventory();
                                break;
                            }
                        }
                    }
                    itemMeta.setLore(lore);
                    player.getItemInHand().setItemMeta(itemMeta);
                }
            }
        }

        return false;
    }
}
