package com.itech4kids.skyblock.CustomMobs.Slayer;

import org.bukkit.entity.Player;

public class SlayerQuest {

    private int exp;
    private int neededExp;

    public SlayerQuest(SlayerType type, Player player){
        exp = 0;
        neededExp = 100;
    }


}
