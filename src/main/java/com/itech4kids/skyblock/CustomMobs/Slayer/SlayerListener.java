package com.itech4kids.skyblock.CustomMobs.Slayer;

import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class SlayerListener implements Listener {

    @EventHandler
    public void onSlayerKill(EntityDeathEvent e){
        if (e.getEntity().getKiller().getType().equals(EntityType.PLAYER)) {
            Player player = e.getEntity().getKiller();
            SkyblockPlayer skyblockPlayer = Main.getMain().getPlayer(player.getName());
            //WIP
        }
    }

}
