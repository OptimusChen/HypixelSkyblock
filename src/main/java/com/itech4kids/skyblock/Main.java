package com.itech4kids.skyblock;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itech4kids.skyblock.Commands.ItemBrowser.Boots.BootsCategoryCommand;
import com.itech4kids.skyblock.Commands.ItemBrowser.Chestplate.ChestplateCategoryCommand;
import com.itech4kids.skyblock.Commands.ItemBrowser.Helmet.HelmetCategoryCommand;
import com.itech4kids.skyblock.Commands.ItemBrowser.Leggings.LeggingsCategoryCommand;
import com.itech4kids.skyblock.Commands.ItemBrowser.Sword.SwordCategoryCommand;
import com.itech4kids.skyblock.Commands.ItemBrowser.Sword.SwordCategoryCommandPage2;
import com.itech4kids.skyblock.Commands.Items.*;
import com.itech4kids.skyblock.Commands.*;
import com.itech4kids.skyblock.CustomMobs.Dragon.SkyblockDragon;
import com.itech4kids.skyblock.CustomMobs.Enderman.SkyblockEnderman;
import com.itech4kids.skyblock.CustomMobs.Zombie.SkyblockZombie;
import com.itech4kids.skyblock.Listeners.*;
import com.itech4kids.skyblock.Objects.Items.ItemHandler;
import com.itech4kids.skyblock.Objects.Items.SkyblockUsableItem;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import com.itech4kids.skyblock.Objects.SkyblockStats;
import com.itech4kids.skyblock.Util.Config;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
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
        players = new HashMap<>();
        damage_indicator = new ArrayList<>();
        registerListeners();
        registerCustomMobs();
        registerCommands();
        new Config(this);
        ItemHandler.init();
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new Eventlistener(), this);
        Bukkit.getPluginManager().registerEvents(new SkyblockMenuListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SkillGainListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new AbilityListener(), this);
        Bukkit.getPluginManager().registerEvents(new StatListener(), this);
    }

    private void registerCommands(){
        getCommand("sbmenu").setExecutor(new SkyblockMenuCommand());
        getCommand("setPlayerStat").setExecutor(new PlayerStatCommand());
        getCommand("spawncm").setExecutor(new SpawnCustomMobCommand());
        getCommand("itembrowser").setExecutor(new ItemBrowserCommand());
        getCommand("swordcategory").setExecutor(new SwordCategoryCommand());
        getCommand("swordcategory2").setExecutor(new SwordCategoryCommandPage2());
        getCommand("todolist").setExecutor(new TodoListCommand());
        getCommand("trade").setExecutor(new TradeCommand());
        getCommand("skill").setExecutor(new SkillCommand());
        getCommand("sreforge").setExecutor(new ReforgeCommand());
        getCommand("pets").setExecutor(new PetsCommand());
        getCommand("recombobulate").setExecutor(new RecombobulateCommand());
        getCommand("rarity").setExecutor(new RarityCommand());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("collections").setExecutor(new CollectionsCommand());
        getCommand("item").setExecutor(new ItemCommand());
        getCommand("helmetcategory").setExecutor(new HelmetCategoryCommand());
        getCommand("chestplatecategory").setExecutor(new ChestplateCategoryCommand());
        getCommand("leggingscategory").setExecutor(new LeggingsCategoryCommand());
        getCommand("bootscategory").setExecutor(new BootsCategoryCommand());
    }

    private void registerCustomMobs(){
        registerEntity("Enderman", 58, SkyblockEnderman.class);
        registerEntity("Dragon", 63, SkyblockDragon.class);
        registerEntity("Zombie", 54, SkyblockZombie.class);

    }

    public void updateMaxHealth(SkyblockPlayer skyblockPlayer) {
        Player player = skyblockPlayer.getBukkitPlayer();

        int hp = skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH);

        if (hp > 0 && hp <= 100){
            player.setMaxHealth(20);
        }else if (hp > 101 && hp <= 200){
            player.setMaxHealth(22);
        }else if (hp > 201 && hp <= 300){
            player.setMaxHealth(24);
        }else if (hp > 301 && hp <= 400){
            player.setMaxHealth(26);
        }else if (hp > 401 && hp <= 500){
            player.setMaxHealth(28);
        }else if (hp > 501 && hp <= 600){
            player.setMaxHealth(30);
        }else if (hp > 601 && hp <= 700){
            player.setMaxHealth(32);
        }else if (hp > 701 && hp <= 800){
            player.setMaxHealth(34);
        }else if (hp > 801 && hp <= 900){
            player.setMaxHealth(36);
        }else if (hp > 901 && hp <= 1000){
            player.setMaxHealth(38);
        }else if (hp > 1001){
            player.setMaxHealth(40);
        }

        if (skyblockPlayer.getStat(SkyblockStats.HEALTH) <= 0){
            player.setHealth(0);
        }
    }

    public int sbHealthtoVanilla(int hp) {

        int i = 0;

        if (hp > 0 && hp <= 100){
            i = 20;
        }else if (hp > 101 && hp <= 200){
            i = 22;
        }else if (hp > 201 && hp <= 300){
            i = 24;
        }else if (hp > 301 && hp <= 400){
            i = 26;
        }else if (hp > 401 && hp <= 500){
            i = 28;
        }else if (hp > 501 && hp <= 600){
            i = 30;
        }else if (hp > 601 && hp <= 700){
            i = 32;
        }else if (hp > 701 && hp <= 800){
            i = 34;
        }else if (hp > 801 && hp <= 900){
            i = 36;
        }else if (hp > 901 && hp <= 1000){
            i = 38;
        }else if (hp > 1001){
            i = 40;
        }
        return i;
    }

    public void updateHealth(SkyblockPlayer skyblockPlayer, int i){
        Player player = skyblockPlayer.getBukkitPlayer();

        int hp = skyblockPlayer.getStat(SkyblockStats.HEALTH);
        int mhp = skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH);

        if (player.getHealth() <= player.getMaxHealth()){
            player.setHealth(player.getHealth() + i/(mhp/player.getMaxHealth()));
        }
            skyblockPlayer.setStat(SkyblockStats.HEALTH, hp + i);
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

    public ItemStack IDtoSkull(ItemStack head, String id) {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(new String(org.apache.commons.codec.binary.Base64.decodeBase64(id))).getAsJsonObject();
        String skinUrl = o.get("textures").getAsJsonObject().get("SKIN").getAsJsonObject().get("url").getAsString();
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = org.apache.commons.codec.binary.Base64.encodeBase64(("{textures:{SKIN:{url:\"" + skinUrl + "\"}}}").getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public double getSkillAverage(Player player){
        int i = Config.getStatLvl(player, "combat") + Config.getStatLvl(player, "farming") + Config.getStatLvl(player, "mining") + Config.getStatLvl(player, "foraging") + Config.getStatLvl(player, "fishing") + Config.getStatLvl(player, "enchanting") + Config.getStatLvl(player, "alchemy") + Config.getStatLvl(player, "taming");
        double d = i/8;
        return d;
    }

    private int displayScoreBoard(int scoreNum, Objective objective, Player player) {
        int hours=java.util.Calendar.getInstance().getTime().getHours();
        int minutes=java.util.Calendar.getInstance().getTime().getMinutes();
        SkyblockPlayer activePlayer = getPlayer(player.getName());
        LocalDate l_date = LocalDate.now();
        String dateString = l_date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        DecimalFormat formatter = new DecimalFormat("#,###");
        formatter.setGroupingUsed(true);

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
        score = objective.getScore(org.bukkit.ChatColor.WHITE + "Purse: " + ChatColor.GOLD +  formatter.format(Config.getPurseCoins(activePlayer.getBukkitPlayer())));
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.WHITE + "Bits: " + ChatColor.AQUA + formatter.format(Config.getBits(player)));
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.WHITE + "  ");
        score.setScore(scoreNum--);
        score = objective.getScore(org.bukkit.ChatColor.YELLOW + "www.hypixel.net");
        score.setScore(scoreNum--);
        return scoreNum;
    }

    public void updateStats(SkyblockPlayer skyblockPlayer, ItemStack itemStack, ItemStack oldItem){
        if (itemStack != null && oldItem != null) {
            SkyblockUsableItem item = new SkyblockUsableItem(itemStack);
            SkyblockUsableItem olditem = new SkyblockUsableItem(oldItem);

            for (Map.Entry<SkyblockStats, Integer> entry : item.getProperties().entrySet()) {
                skyblockPlayer.setStat(entry.getKey(), skyblockPlayer.getStat(entry.getKey()) + entry.getValue());
            }
            for (Map.Entry<SkyblockStats, Integer> entry : olditem.getProperties().entrySet()) {
                skyblockPlayer.setStat(entry.getKey(), skyblockPlayer.getStat(entry.getKey()) - entry.getValue());
            }
        }else if (itemStack != null && oldItem == null){
            SkyblockUsableItem item = new SkyblockUsableItem(itemStack);

            for (Map.Entry<SkyblockStats, Integer> entry : item.getProperties().entrySet()) {
                skyblockPlayer.setStat(entry.getKey(), skyblockPlayer.getStat(entry.getKey()) + entry.getValue());
            }
        }else if (oldItem != null && itemStack == null){
            SkyblockUsableItem item = new SkyblockUsableItem(oldItem);

            for (Map.Entry<SkyblockStats, Integer> entry : item.getProperties().entrySet()) {
                skyblockPlayer.setStat(entry.getKey(), skyblockPlayer.getStat(entry.getKey()) - entry.getValue());
            }
        }
    }

    public void updateScoreBoard() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();
            Objective objective = board.registerNewObjective("Skyblock", "Display ");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(org.bukkit.ChatColor.YELLOW + "" + ChatColor.BOLD + "SKYBLOCK");
            int scoreNum = 10;

            scoreNum = displayScoreBoard(scoreNum, objective, p);
            p.setScoreboard(board);
        }
    }

    private static final NavigableMap<Long, String> suffixes = new TreeMap<> ();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
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
                    }else if (skyblockPlayer.getStat(SkyblockStats.MANA) <= 0){
                        icbc = IChatBaseComponent.ChatSerializer.a(
                                "{\"text\":\"" + "§c" + skyblockPlayer.getStat(SkyblockStats.HEALTH) + "/" + skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH) + "❤   §a" +
                                        skyblockPlayer.getStat(SkyblockStats.DEFENSE) + "❈ Defense    §b" + 0 + "/" +
                                        skyblockPlayer.getStat(SkyblockStats.MAX_MANA) + "✎ Mana" + "\"}");
                    } else{
                        icbc = IChatBaseComponent.ChatSerializer.a(
                                "{\"text\":\"" + "§c" + skyblockPlayer.getStat(SkyblockStats.HEALTH) + "/" + skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH) + "❤   §a" +
                                        skyblockPlayer.getStat(SkyblockStats.DEFENSE) + "❈ Defense    §b" + skyblockPlayer.getStat(SkyblockStats.MANA) + "/" +
                                        skyblockPlayer.getStat(SkyblockStats.MAX_MANA) + "✎ Mana" + "\"}");
                    }
                    //player.sendMessage("test3  icbc" + icbc);

                    //((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutChat);

                    ActionBarAPI.sendActionBar(player, icbc.getText());

                    updateMaxHealth(skyblockPlayer);
                    //player.setWalkSpeed(skyblockPlayer.getStat(SkyblockStats.SPEED)/1000);

                }
            }
        }.runTaskTimer(this, 5L, 40);
    }

    public void updateActionbar(SkyblockPlayer skyblockPlayer){
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

        ActionBarAPI.sendActionBar(skyblockPlayer.getBukkitPlayer(), icbc.getText());
    }

    public void updateBoard(final Player player) {
        SkyblockPlayer skyblockPlayer = getPlayer(player.getName());
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel(); // this cancels it when they leave
                }else if (player.isOnline()){
                    updateScoreBoard();
                    if (skyblockPlayer.getStat(SkyblockStats.MANA) <= skyblockPlayer.getStat(SkyblockStats.MAX_MANA) - 6) {
                        skyblockPlayer.setStat(SkyblockStats.MANA, skyblockPlayer.getStat(SkyblockStats.MANA) + 6);
                    }else{
                        skyblockPlayer.setStat(SkyblockStats.MANA, skyblockPlayer.getStat(SkyblockStats.MAX_MANA));
                    }

                    if (skyblockPlayer.getStat(SkyblockStats.HEALTH) <= skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH) - (int) (1.5 + skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH)/100)) {
                        updateHealth(skyblockPlayer, (int) (1.5 + skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH)/100));
                    }else{
                        skyblockPlayer.setStat(SkyblockStats.HEALTH, skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH));
                        skyblockPlayer.getBukkitPlayer().setHealth(skyblockPlayer.getBukkitPlayer().getMaxHealth());
                    }
                }
            }
        }.runTaskTimer(this, 5L, 60);
    }
//
//    public void loadNpc(){
//        FileConfiguration file = getConfig();
//        getConfig().getConfigurationSection("data").getKeys(false).forEach(npc ->{
//            Location location = new Location(Bukkit.getWorld(file.getString("data." + npc + ".world"))
//                    , file.getDouble("data." + npc + ".x")
//                    , file.getDouble("data." + npc + ".y")
//                    , file.getDouble("data." + npc + ".z"));
//            location.setPitch((float) file.getDouble("data." + npc + ".p"));
//            location.setYaw((float) file.getDouble("data." + npc + ".yaw"));
//
//            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), org.bukkit.ChatColor.AQUA + "Yeti");
//
//            gameProfile.getProperties().removeAll("textures");
//
//            PropertyMap map = gameProfile.getProperties();
//
//            map.put("textures", new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTYxOTI5ODY0OTg2NSwKICAicHJvZmlsZUlkIiA6ICJkZGVkNTZlMWVmOGI0MGZlOGFkMTYyOTIwZjdhZWNkYSIsCiAgInByb2ZpbGVOYW1lIiA6ICJEaXNjb3JkQXBwIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2VmOWY2YTU3ZDhmNjg2ZmVkZDUxODVkMTc4NTUwOTEzZjQ4ZjkxYjU3YzkxNjJkNzBkMzY4YjFmZDlmZTJiNTIiCiAgICB9CiAgfQp9", "http://textures.minecraft.net/texture/ef9f6a57d8f686fedd5185d178550913f48f91b57c9162d70d368b1fd9fe2b52"));
//
//            SkyblockYeti.loadNpc(location, gameProfile);
//        });
//    }

}
