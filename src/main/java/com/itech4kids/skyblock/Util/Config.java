package com.itech4kids.skyblock.Util;

import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.Pets.SkyblockPetsItem;
import com.itech4kids.skyblock.Objects.SkillType;
import com.itech4kids.skyblock.Objects.SkyblockStats;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
        return config.getInt("skills." + statName + "-lvl");
    }

    public static void setStatLvl(Player player, String statName, int i) throws IOException {
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("skills." + statName + "-lvl", i);
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

    public static ArrayList<ItemStack> getPets(Player player){
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return (ArrayList<ItemStack>) config.getList("pets.stored-pets");
    }

    public static void addPet(Player player, ItemStack item) throws IOException {
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.getList("pets.stored-pets") != null) {
            ArrayList<ItemStack> pets = (ArrayList<ItemStack>) config.getList("pets.stored-pets");
            pets.add(item);
            config.set("pets.stored-pets", pets);
            config.save(file);
        }else {
            ArrayList<ItemStack> pets = new ArrayList<>();
            pets.add(item);
            config.set("pets.stored-pets", pets);
            config.save(file);
        }
    }

    public static void removePet(Player player, ItemStack item) throws IOException {
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        ArrayList<ItemStack> pets = (ArrayList<ItemStack>) config.get("pets.stored-pets");
        pets.remove(item);
        config.set("pets.stored-pets", pets);
        config.save(file);
    }

    public static ItemStack getActivePet(Player player){
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getItemStack("pets.active-pet");
    }

    public static void setActivePet(Player player, ItemStack item) throws IOException {
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("pets.active-pet", item);
        config.save(file);
    }

    public static int getCollectionLevel(Player player, String collection){
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getInt("collections." + collection.toLowerCase() + "_level");
    }

    public static int getCollectionCollected(Player player, String collection){
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getInt("collections." + collection.toLowerCase() + "_collected");
    }

    public static void setCollectionLevel(Player player, String collection, int newValue) throws IOException{
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("collections." + collection.toLowerCase() + "_level", newValue);
    }

    public static void setCollectionCollected(Player player, String collection, int newValue) throws IOException{
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("collections." + collection.toLowerCase() + "_collected", newValue);
    }

    public static boolean getBanned(Player player){
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getBoolean("moderation.banned");
    }

    public static void setBanned(Player player, boolean val) throws IOException{
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("moderation.banned", val);
        config.save(file);
    }

    public static String getBanReason(Player player){
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getString("moderation.ban_reason");
    }

    public static void setBanReason(Player player, String val) throws IOException{
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("moderation.ban_reason", val);
        config.save(file);
    }

    public static int getTimesKicked(Player player){
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getInt("moderation.times_kicked");
    }

    public static void setTimesKicked(Player player, int val) throws IOException{
        File file = new File(main.getDataFolder()+File.separator+"Players"+File.separator+player.getUniqueId()+".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("moderation.times_kicked", val);
        config.save(file);
    }

    public static void createPlayer(String name) throws IOException {
        File folder = new File(main.getDataFolder() + File.separator + "Players");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File playerFile = new File(main.getDataFolder() + File.separator + "Players" + File.separator + Bukkit.getPlayer(name).getUniqueId() + ".yml");
        if (!playerFile.exists()) {
            playerFile.createNewFile();
            FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

            config.set("bank-coins", 0.0);
            config.set("purse-coins", 0.0);
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

            config.set("skills.combat-lvl", 0);
            config.set("skills.foraging-lvl", 0);
            config.set("skills.mining-lvl", 0);
            config.set("skills.fishing-lvl", 0);
            config.set("skills.farming-lvl", 0);
            config.set("skills.enchanting-lvl", 0);
            config.set("skills.alchemy-lvl", 0);
            config.set("skills.social-lvl", 0);
            config.set("skills.taming-lvl", 0);
            config.set("skills.carpentry-lvl", 0);
            config.set("skills.catacombs-lvl", 0);
            config.set("skills.runecrafting-lvl", 0);

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
            config.set("stats.damage", 0);
            config.set("stats.ability_damage", 0);
            config.set("stats.mining_fortune", 0);
            config.set("stats.farming_fortune", 0);
            config.set("stats.foraging_fortune", 0);

            config.set("collections.cobblestone_level", 0);
            config.set("collections.cobblestone_collected", 0);

            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(ChatColor.DARK_GRAY + "[Lvl 0] None");
            item.setItemMeta(itemMeta);

            ArrayList<ItemStack> pets = new ArrayList<>();

            config.set("pets.active-pet", item);
            config.set("pets.stored-pets", pets);

            config.set("moderation.banned", false);
            config.set("moderation.ban_reason", "");
            config.set("moderation.times_kicked", 0);

            config.save(playerFile);
        }

    }

}
