package com.itech4kids.skyblock;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.itech4kids.skyblock.Commands.ItemBrowserCommand;
import com.itech4kids.skyblock.Commands.PlayerStatCommand;
import com.itech4kids.skyblock.Commands.SkyblockMenuCommand;
import com.itech4kids.skyblock.Commands.SpawnCustomMobCommand;
import com.itech4kids.skyblock.CustomMobs.Dragon.SkyblockDragon;
import com.itech4kids.skyblock.CustomMobs.Enderman.SkyblockEnderman;
import com.itech4kids.skyblock.CustomMobs.Zombie.SkyblockZombie;
import com.itech4kids.skyblock.Listeners.Eventlistener;
import com.itech4kids.skyblock.Listeners.SkyblockMenuListener;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import com.itech4kids.skyblock.Objects.SkyblockStats;
import com.itech4kids.skyblock.Util.Config;
import com.itech4kids.skyblock.Util.Inventories;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;
import sun.awt.image.IntegerComponentRaster;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class Main extends JavaPlugin {

    public HashMap<String, SkyblockPlayer> players;
    private static Main main;
    public static ArrayList<ArmorStand> damage_indicator;

    @Override
    public void onEnable(){
        main = this;
        players = new HashMap<String, SkyblockPlayer>();
        damage_indicator = new ArrayList<ArmorStand>();
        Bukkit.getPluginManager().registerEvents(new Eventlistener(), this);
        Bukkit.getPluginManager().registerEvents(new SkyblockMenuListener(this), this);
        getCommand("sbmenu").setExecutor(new SkyblockMenuCommand());
        getCommand("setPlayerStat").setExecutor(new PlayerStatCommand());
        getCommand("spawncm").setExecutor(new SpawnCustomMobCommand());
        getCommand("itembrowser").setExecutor(new ItemBrowserCommand());
        registerEntity("Enderman", 58, SkyblockEnderman.class);
        registerEntity("Dragon", 63, SkyblockDragon.class);
        registerEntity("Zombie", 54, SkyblockZombie.class);
        new Inventories();
        new Config(this);
    }

    public void registerCustomMobs(){
    }

    public static void registerEntity(String name, int id, Class<? extends EntityInsentient> customClass) {
        try {
            List<Map<?, ?>> dataMaps = new ArrayList<>();
            for (Field f : EntityTypes.class.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
                    f.setAccessible(true);
                    dataMaps.add((Map<?, ?>) f.get(null));
                }
            }
            ((Map<Class<? extends EntityInsentient>, String>) dataMaps.get(1)).put(customClass, name);
            ((Map<Class<? extends EntityInsentient>, Integer>) dataMaps.get(3)).put(customClass, id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Main getMain(){return main;}

    public SkyblockPlayer getPlayer(String s){return players.get(s);}

    private int displayScoreBoard(int scoreNum, Objective objective, Player player) {
        int hours=java.util.Calendar.getInstance().getTime().getHours();
        int minutes=java.util.Calendar.getInstance().getTime().getMinutes();
        SkyblockPlayer activePlayer = getPlayer(player.getName());
        LocalDate l_date = LocalDate.now();
        String dateString = l_date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));

        Score score = objective.getScore(org.bukkit.ChatColor.GRAY + "" + dateString + " " + "Skyblock");
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.GRAY + "   ");
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.WHITE + " Spring 10th");
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.GRAY + " " + hours + ":" + minutes + "pm " + ChatColor.YELLOW + "☀");
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.WHITE + " ⏣ " + ChatColor.GREEN + "Hub Island");
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.WHITE + " ");
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.WHITE + "Purse: " + ChatColor.GOLD + Config.getPurseCoins(activePlayer.getBukkitPlayer()));
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.WHITE + "Bits: " + ChatColor.AQUA + Config.getBits(player));
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.WHITE + "  ");
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.YELLOW + "www.hypixel.net");
        score.setScore(scoreNum--);
        return scoreNum;
    }

    public void updateScoreBoard() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();
            Objective objective = board.registerNewObjective("Skyblock", "Display ");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(org.bukkit.ChatColor.YELLOW + "" + ChatColor.BOLD + "SKYBLOCK");
            Score score;
            int scoreNum = 10;

            scoreNum = displayScoreBoard(scoreNum, objective, p);
            p.setScoreboard(board);
            break;
        }
    }


    public void onJoin(final Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel(); // this cancels it when they leave
                }else if (player.isOnline()){
                    SkyblockPlayer skyblockPlayer = getPlayer(player.getName());
                    //player.sendMessage(skyblockPlayer.getStats() + " stats");
                    IChatBaseComponent icbc;
                    if (skyblockPlayer.getStat(SkyblockStats.DEFENSE) <= 0) {
                        icbc = IChatBaseComponent.ChatSerializer.a(
                                "{\"text\":\"" + "§c" + skyblockPlayer.getStat(SkyblockStats.HEALTH) + "/" + skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH) + "❤" +
                                        "    §b" + skyblockPlayer.getStat(SkyblockStats.MANA) + "/" +
                                        skyblockPlayer.getStat(SkyblockStats.MAX_MANA) + "✎ Mana" + "\"}");
                    }else{
                        icbc = IChatBaseComponent.ChatSerializer.a(
                                "{\"text\":\"" + "§c" + skyblockPlayer.getStat(SkyblockStats.HEALTH) + "/" + skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH) + "❤   §a" +
                                        skyblockPlayer.getStat(SkyblockStats.DEFENSE) + "❈ Defense    §b" + skyblockPlayer.getStat(SkyblockStats.MANA) + "/" +
                                        skyblockPlayer.getStat(SkyblockStats.MAX_MANA) + "✎ Mana" + "\"}");
                    }
                    //player.sendMessage("test3  icbc" + icbc);

                    //((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutChat);

                    ActionBarAPI.sendActionBar(player, icbc.getText());

                    skyblockPlayer.hp = skyblockPlayer.getStat(SkyblockStats.HEALTH);
                    skyblockPlayer.mhp = skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH);
                    skyblockPlayer.m = skyblockPlayer.getStat(SkyblockStats.MANA);
                    skyblockPlayer.mm = skyblockPlayer.getStat(SkyblockStats.MAX_MANA);
                    skyblockPlayer.d = skyblockPlayer.getStat(SkyblockStats.DEFENSE);

                    updateScoreBoard();
                }
            }
        }.runTaskTimer(this, 5L, 5L);
    }


}
