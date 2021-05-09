package com.itech4kids.skyblock.Util;

import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.SkyblockLocation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationsManager {

    public static Main main;

    public LocationsManager(){
        main = Main.getMain();
    }

    public static SkyblockLocation getLocation(String name){
        File file = new File(Main.getMain().getDataFolder()+File.separator+"locations.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return new SkyblockLocation((Location) config.get(name + ".pos1"), (Location) config.get(name + ".pos2"),
                config.getString(name + ".name"), ChatColor.valueOf(config.getString(name + ".color")), config.getInt(name + ".weight"));
    }

    public static SkyblockLocation getLocation(Location location){
        File file = new File(Main.getMain().getDataFolder()+File.separator+"locations.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        SkyblockLocation loc = null;
        /*
        for (String string : getStoredLocations()) {
            loc = new SkyblockLocation((Location) config.get(string + ".pos1"), (Location) config.get(string + ".pos2"),
                    config.getString(string + ".name"), ChatColor.valueOf(config.getString(string + ".color")), config.getInt(string + ".weight"));

            double x1 = loc.pos1.getX();
            double y1 = loc.pos1.getY();
            double z1 = loc.pos1.getZ();

            double x2 = loc.pos2.getX();
            double y2 = loc.pos2.getY();
            double z2 = loc.pos2.getZ();

            if((location.getX() > x1) && (location.getY() > y1) && (location.getZ() > z1) && (location.getX() < x2) && (location.getY() < y2) && (location.getZ() < z2)) {
                return loc;
            } else {
                loc = null;
            }
        }
         */
        return null;
    }

    public static ArrayList<String> getStoredLocations(){
        File file = new File(Main.getMain().getDataFolder()+File.separator+"locations.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ArrayList<String> strings = new ArrayList<>();
        for(String key : config.getKeys(false)){
            strings.add(key);
        }
        return strings;
    }

    public static void createNewLocation(Location pos1, Location pos2, String name, ChatColor color, int weight) throws IOException {
        File file = new File(Main.getMain().getDataFolder()+File.separator+"locations.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(name + ".pos1", pos1);
        config.set(name + ".pos2", pos2);
        config.set(name + ".name", name);
        config.set(name + ".color", color.name());
        config.set(name + ".weight", weight);
        config.save(file);
    }

    public static void createFile() throws IOException {
        File file = new File(Main.getMain().getDataFolder()+File.separator+"locations.yml");
        if (!file.exists()){
            file.createNewFile();
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.save(file);
        }
    }
}
