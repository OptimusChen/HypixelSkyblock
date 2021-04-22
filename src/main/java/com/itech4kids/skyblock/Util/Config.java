package com.itech4kids.skyblock.Util;

import com.itech4kids.skyblock.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Config {

    public FileConfiguration config;
    public static Main main;

    public Config(Main main){
        this.config = main.getConfig();
        this.main = main;
        main.getConfig().options().copyDefaults();
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
            config.save(playerFile);
        }
    }

}
