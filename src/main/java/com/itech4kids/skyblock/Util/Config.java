package com.itech4kids.skyblock.Util;

import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.SkillType;
import com.itech4kids.skyblock.Objects.SkyblockStats;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Config {

    public FileConfiguration config;
    public static Main main;

    public Config(Main main){
        this.config = main.getConfig();
        this.main = main;
        main.getConfig().options().copyDefaults();
        main.getConfig().options().copyDefaults();
        main.saveDefaultConfig();
    }

    public static double getPurseCoins(Player player){
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getDouble("purse-coins");
    }

    public static void setPurseCoins(Player player, int i) throws IOException {
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("purse-coins", i);
        config.save(file);
    }

    public static void setBits(Player player, int i) throws IOException {
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("bits", i);
        config.save(file);
    }

    public static int getBits(Player player) {
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getInt("bits");
    }

    public static int getStatExp(Player player, String statName){
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getInt(statName + "-exp");
    }

    public static void setStatExp(Player player, String statName, int i) throws IOException {
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(statName + "-exp", i);
        config.save(file);
    }

    public static int getStatLvl(Player player, String statName){
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getInt(statName + "-lvl");
    }

    public static void setStatLvl(Player player, String statName, int i) throws IOException {
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(statName + "-lvl", i);
        config.save(file);
    }

    public static void setStat(Player player, SkyblockStats statType, Integer integer) throws IOException {
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("stats." + statType.name().toLowerCase(), integer);
        config.save(file);
    }

    public static int getPlayerStatLvl(Player player, SkyblockStats statType){
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getInt("stats." + statType.name().toLowerCase());
    }

    public static void createPlayer(String name) throws IOException {
        File folder = new File(main.getDataFolder() + File.separator + "Players");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File playerFile = new File(main.getDataFolder()+File.separator+"Players"+File.separator+ Bukkit.getPlayer(name).getUniqueId()+".yml");
        if (!playerFile.exists()) {
            playerFile.createNewFile();
            FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

            config.set("bank-coins", 0);
            config.set("purse-coins", 0);
            config.set("bits", 0);

            config.set("combat-exp", 1);
            config.set("foraging-exp", 1);
            config.set("mining-exp", 1);
            config.set("fishing-exp", 1);
            config.set("farming-exp", 1);
            config.set("enchanting-exp", 1);
            config.set("alchemy-exp", 1);
            config.set("social-exp", 1);
            config.set("taming-exp", 1);
            config.set("carpentry-exp", 0);
            config.set("catacombs-exp", 0);
            config.set("runecrafting-exp", 0);

            config.set("combat-lvl", 0);
            config.set("foraging-lvl", 0);
            config.set("mining-lvl", 0);
            config.set("fishing-lvl", 0);
            config.set("farming-lvl", 0);
            config.set("enchanting-lvl", 0);
            config.set("alchemy-lvl", 0);
            config.set("social-lvl", 0);
            config.set("taming-lvl", 0);
            config.set("carpentry-lvl", 0);
            config.set("catacombs-lvl", 0);
            config.set("runecrafting-lvl", 0);

            config.set("stats.health", 100);
            config.set("stats.max_health", 100);
            config.set("stats.mana", 100);
            config.set("stats.max_mana", 100);
            config.set("stats.defense", 0);
            config.set("stats.strength", 0);
            config.set("stats.speed", 100);
            config.set("stats.crit_chance", 30);
            config.set("stats.crit_damage", 50);
            config.set("stats.mining_speed", 0);
            config.set("stats.attack_speed", 0);
            config.set("stats.sea_creature_chance", 0);
            config.set("stats.magic_find", 0);
            config.set("stats.pet_luck", 0);
            config.set("stats.true_defense", 0);
            config.set("stats.ferocity", 0);
            config.set("stats.ability_damage", 0);
            config.set("stats.mining_fortune", 0);
            config.set("stats.farming_fortune", 0);
            config.set("stats.foraging_fortune", 0);

            config.save(playerFile);
        }
    }

}
