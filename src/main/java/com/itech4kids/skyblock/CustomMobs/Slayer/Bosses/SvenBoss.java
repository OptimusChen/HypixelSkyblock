package com.itech4kids.skyblock.CustomMobs.Slayer.Bosses;

import com.itech4kids.skyblock.CustomMobs.Slayer.SlayerAI;
import com.itech4kids.skyblock.CustomMobs.Slayer.SlayerBoss;
import com.itech4kids.skyblock.Main;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SvenBoss extends SlayerBoss {

    private int trueDPS;
    private boolean hasCalledPups;

    private Player spawner;
    private int bossLevel;

    public SvenBoss(Player spawner, int bossLevel) {
        super(EntityType.WOLF, bossLevel);
        hasCalledPups = false;

        this.spawner = spawner;
        this.bossLevel = bossLevel;

        switch (bossLevel){
            case 1:
                trueDPS = 0;
                break;
            case 2:
                trueDPS = 10;
                break;
            case 3:
                trueDPS = 50;
                break;
            case 4:
                trueDPS = 200;
                break;
        }
    }

    public void summon(Location spawnLocation){
        spawnSlayerBoss(spawnLocation, -1);
        registerEntity();
        SlayerAI.runSvenAI(this, bossLevel, spawner);
        Main.getMain().slayerManger.registerBoss(this);
    }

    public void callPups(){
        hasCalledPups = true;
    }

    public boolean hasCalledPups(){
        return hasCalledPups;
    }

    public int getTrueDPS(){
        return trueDPS;
    }

}
