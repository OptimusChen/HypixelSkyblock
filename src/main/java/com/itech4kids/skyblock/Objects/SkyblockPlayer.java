package com.itech4kids.skyblock.Objects;

import com.itech4kids.skyblock.Objects.Items.SkyblockItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class SkyblockPlayer {

    private Player player;
    private HashMap<SkyblockStats, Integer> stats;
    private HashMap<String, Inventory> inventories;
    public int hp;
    public int mhp;
    public int mm;
    public int m;
    public int d;

    public SkyblockPlayer(Player player){
        this.player = player;
        stats = new HashMap<SkyblockStats, Integer>();
        inventories = new HashMap<String, Inventory>();
        loadDefaultStats();

        setStat(SkyblockStats.HEALTH, 100);
        setStat(SkyblockStats.MAX_HEALTH, 100);
        setStat(SkyblockStats.DEFENSE, 0);
        setStat(SkyblockStats.MANA, 100);
        setStat(SkyblockStats.MAX_MANA, 100);
        /*
        hp = getStat(SkyblockStats.HEALTH);
        mhp = getStat(SkyblockStats.MAX_HEALTH);
        mm = getStat(SkyblockStats.MAX_MANA);
        m = getStat(SkyblockStats.MANA);
        d = getStat(SkyblockStats.DEFENSE);
         */
    }

    private void loadDefaultStats(){
        setStat(SkyblockStats.HEALTH, 100);
        setStat(SkyblockStats.MAX_HEALTH, 100);
        setStat(SkyblockStats.DEFENSE, 0);
        setStat(SkyblockStats.SPEED, 100);
        setStat(SkyblockStats.STRENGTH, 0);
        setStat(SkyblockStats.MANA, 100);
        setStat(SkyblockStats.MAX_MANA, 100);
        setStat(SkyblockStats.CRIT_CHANCE, 30);
        setStat(SkyblockStats.CRIT_DAMAGE, 50);
        setStat(SkyblockStats.ABILITY_DAMAGE, 0);
        setStat(SkyblockStats.MAGIC_FIND, 0);
        setStat(SkyblockStats.SEA_CREATURE_CHANCE, 20);
        setStat(SkyblockStats.MINING_FORTUNE, 0);
        setStat(SkyblockStats.PET_LUCK, 0);
        setStat(SkyblockStats.TRUE_DEFENSE, 0);
        setStat(SkyblockStats.FEROCITY, 0);
        setStat(SkyblockStats.MINING_FORTUNE, 0);
        setStat(SkyblockStats.FARMING_FORTUNE, 0);
        setStat(SkyblockStats.FORAGING_FORTUNE, 0);
        setStat(SkyblockStats.ATTACK_DAMAGE, 0);
        setStat(SkyblockStats.ATTACK_SPEED, 0);
        setStat(SkyblockStats.MINING_SPEED, 0);
    }

    public void setStat(SkyblockStats s, Integer i){
        stats.put(s, i);
    }

    public HashMap<SkyblockStats, Integer> getStats(){
        return stats;
    }

    public Integer getStat(SkyblockStats statName){return stats.get(statName);}

    public void setInventory(String s, Inventory i){
        if (inventories.containsKey(s)){
            inventories.remove(s);
        }
        inventories.put(s, i);
    }

    public HashMap<String, Inventory> getInventories(){
        return inventories;
    }

    public Inventory getInventory(String inventoryName){ return inventories.get(inventoryName); }

    public Player getBukkitPlayer(){
        return player;
    }
}
