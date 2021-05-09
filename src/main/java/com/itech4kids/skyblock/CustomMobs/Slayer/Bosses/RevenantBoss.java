package com.itech4kids.skyblock.CustomMobs.Slayer.Bosses;

import com.itech4kids.skyblock.CustomMobs.Slayer.SlayerAI;
import com.itech4kids.skyblock.CustomMobs.Slayer.SlayerBoss;
import com.itech4kids.skyblock.Main;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class RevenantBoss extends SlayerBoss {

    public RevenantBoss(Player spawner, Location spawnLocation, int bossLevel) {
        super(EntityType.ZOMBIE, bossLevel);
        spawnSlayerBoss(spawnLocation, 0.2);
        registerEntity();
        SlayerAI.runRevenantAI(this, bossLevel, spawner);
        //Main.getMain().slayerManger.registerBoss(this);
    }
}
