package com.itech4kids.skyblock.CustomMobs.Slayer.Bosses;

import com.itech4kids.skyblock.CustomMobs.Slayer.SlayerAI;
import com.itech4kids.skyblock.CustomMobs.Slayer.SlayerBoss;
import com.itech4kids.skyblock.Main;
import org.bukkit.Location;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class TarantulaBoss extends SlayerBoss {

    public TarantulaBoss(Player spawner, Location spawnLocation, int bossLevel) {
        super(EntityType.SPIDER, bossLevel);
        spawnSlayerBoss(spawnLocation, -0.75);
        registerEntity();
        //Main.getMain().slayerManger.registerBoss(this);
        SlayerAI.runTarantulaAI(this, bossLevel, spawner, spawnLocation.getWorld().spawn(spawnLocation, CaveSpider.class));
    }
}
