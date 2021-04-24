package com.itech4kids.skyblock.Listeners;

import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AbilityListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        SkyblockPlayer skyblockPlayer = Main.getMain().getPlayer(player.getName());
        Action action = e.getAction();
        if (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)){
            if (player.getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.DARK_PURPLE + "Ember Rod")){
                
            }
        }
    }
}
