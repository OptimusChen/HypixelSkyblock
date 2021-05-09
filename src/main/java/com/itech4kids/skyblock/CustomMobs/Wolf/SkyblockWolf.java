package com.itech4kids.skyblock.CustomMobs.Wolf;

import com.itech4kids.skyblock.CustomMobs.SEntity;
import com.itech4kids.skyblock.CustomMobs.SEntityAI;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SkyblockWolf extends SEntity {

    public SkyblockWolf(SkyblockWolfType type, Location spawnLocation) {
        super(EntityType.WOLF);

        switch (type){
            case WOLF_LVL_15:
                loadStats(250, 90, false, new HashMap<String, ItemStack>(), "Wolf", 15);
                break;
            case OLD_WOLF:
                loadStats(15000, 800, false, new HashMap<>(), "Old Wolf", 55);
                break;
            case PACK_SPIRIT:
                loadStats(6000, 240, false, new HashMap<>(), "Pack Spirit", 30);
                break;
            case HOWLING_SPIRIT:
                loadStats(7000, 400, false, new HashMap<>(), "Howling Spirit", 35);
                break;
            case SOUL_OF_THE_ALPHA:
                loadStats(31150, 1140, false, new HashMap<>(), "Soul of the Alpha", 55);
                break;
        }

        spawn(spawnLocation);
        registerEntity();
        SEntityAI.runWolfAI(this);
    }
}
