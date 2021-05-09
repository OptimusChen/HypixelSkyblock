package com.itech4kids.skyblock.CustomMobs.Slayer;

import com.itech4kids.skyblock.CustomMobs.SEntity;
import com.itech4kids.skyblock.Main;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;

public class SlayerManager {

    private HashMap<Integer, SlayerBoss> slayers;
    private int nextId;

    public SlayerManager(){
        slayers = new HashMap<>();
        nextId = -1;
    }

    public void registerBoss(SlayerBoss boss){
        nextId++;
        slayers.put(nextId, boss);
        boss.getVanillaEntity().setMetadata("slayerId", new FixedMetadataValue(Main.getMain(), nextId));
    }

    public void unRegisterBoss(Entity entity){
        HashMap<Integer, SlayerBoss> bosses_2 = new HashMap<>(slayers);
        for (Map.Entry<Integer, SlayerBoss> entry : slayers.entrySet()){
            if (entry.getValue().getVanillaEntity().getMetadata("slayerId").equals(entity.getMetadata("slayerId"))){
                bosses_2.remove(entry.getKey(), entry.getValue());
            }
        }

        slayers = bosses_2;
    }

    public SlayerBoss getEntity(Entity entity){
        SlayerBoss boss = null;
        for (Map.Entry<Integer, SlayerBoss> entry : slayers.entrySet()){
            if (entry.getValue().getVanillaEntity().getMetadata("slayerId").equals(entity.getMetadata("slayerId"))){
                boss = entry.getValue();
            }
        }
        return boss;
    }

}
