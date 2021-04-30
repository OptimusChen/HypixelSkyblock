package com.itech4kids.skyblock.Listeners;

import com.itech4kids.skyblock.CustomMobs.PlayerEntities.CustomAI;
import com.itech4kids.skyblock.Events.SkyblockSkillExpGainEvent;
import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.Items.GuiItems.ClickGuiItem;
import com.itech4kids.skyblock.Objects.Items.Item;
import com.itech4kids.skyblock.Objects.Pets.SkyblockPet;
import com.itech4kids.skyblock.Objects.Pets.SkyblockPetsItem;
import com.itech4kids.skyblock.Objects.SkillType;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import com.itech4kids.skyblock.Objects.SkyblockStats;
import com.itech4kids.skyblock.Util.Config;
import com.itech4kids.skyblock.Util.ItemUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.*;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.npc.ai.speech.Chat;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.swing.text.NumberFormatter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.bukkit.Material.EGG;

public class Eventlistener implements Listener {

    public Main main;

    public Eventlistener(){
        main = Main.getMain();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        final Player player = e.getPlayer();
        if (!player.hasMetadata("NPC")) {

            try {
                Config.createPlayer(player.getName());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            ItemStack item1 = new ItemStack(Material.NETHER_STAR);
            ItemMeta itemMeta = item1.getItemMeta();
            List<String> lore = new ArrayList<>();
            itemMeta.setDisplayName(ChatColor.GREEN + "Skyblock Menu (Right Click)");
            ItemUtil.addLoreMessage("View all of your Skyblock progress, including your Skills, Collections, Recipes, and more!", lore);
            itemMeta.setLore(lore);
            item1.setItemMeta(itemMeta);
            player.getInventory().setItem(8, item1);

            SkyblockPlayer skyblockPlayer = new SkyblockPlayer(player);
            main.players.put(player.getName(), skyblockPlayer);

            new BukkitRunnable() {
                @Override
                public void run() {
                    main.onJoin(player);
                    main.updateBoard(player);
                    IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "\n" +
                            "§bYou are playing on: " + "§e§lHYPIXEL.NET" + "\n" + " " + "\"}");
                    IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "\n" + "§aDiscord:" + "\n" + "§7Discord not set up yet!" + "\n " + "\n" + "§aRanks, Booster & More!" + "§l§c STORE.HYPIXEL.NET" + "\"}");
                    PacketPlayOutPlayerListHeaderFooter info = new PacketPlayOutPlayerListHeaderFooter(header);

                    try {
                        Field field = info.getClass().getDeclaredField("b");
                        field.setAccessible(true);
                        field.set(info, footer);
                    } catch (Exception x) {
                        x.printStackTrace();
                    }

                    player.getWorld().setGameRuleValue("mobGriefing", "false");
                    player.getWorld().setGameRuleValue("randomTickSpeed", "0");
                    player.getWorld().setGameRuleValue("naturalRegeneration", "false");
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(info);

                    skyblockPlayer.activePet = SkyblockPet.spawnArmorStand(player, Config.getActivePet(player));;
                }
            }.runTaskLater(main, 1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    Main.getMain().updateMaxHealth(skyblockPlayer);
                    player.setHealth(player.getMaxHealth());
                }
            }.runTaskLater(main, 5);

//        PacketReader reader = new PacketReader();
//        reader.inject(player);
//        if (SkyblockYeti.getNpcs() == null){
//            return;
//        }else if (SkyblockYeti.getNpcs().isEmpty()){
//            return;
//        }else{
//            new BukkitRunnable() {
//                @Override
//                public void run() {
//                    SkyblockYeti.addJoinPacket(player.getPlayer());
//                }
//            }.runTaskLater(Main.getMain(), 1);
//        }
        }
    }

    @EventHandler
    public void onTeleport(EntityTeleportEvent e){
        if (e.getEntity().getType().equals(EntityType.ENDERMAN)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCombust(EntityCombustEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) throws IOException {
        SkyblockPlayer skyblockPlayer = main.getPlayer(e.getPlayer().getName());
        skyblockPlayer.saveStats();
        skyblockPlayer.activePet.remove();
        main.players.remove(skyblockPlayer);
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e){
        if (e.getItem().getItemStack().getItemMeta().getLore() == null){
            List<String> lore = new ArrayList<>();
            ItemMeta itemMeta = e.getItem().getItemStack().getItemMeta();
            lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "COMMON");
            itemMeta.setLore(lore);
            e.getItem().getItemStack().setItemMeta(itemMeta);
        }
    }

    @EventHandler
    public void addLore(InventoryClickEvent e){
        if (e.getCurrentItem().getItemMeta().getLore() == null){
            List<String> lore = new ArrayList<>();
            ItemMeta itemMeta = e.getCurrentItem().getItemMeta();
            if (e.getClickedInventory().equals(e.getWhoClicked().getInventory())) {
                lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "COMMON");
                itemMeta.setLore(lore);
                e.getCurrentItem().setItemMeta(itemMeta);
            }
        }
    }

    @EventHandler
    public void addLore(InventoryCreativeEvent e){
        if (e.getCurrentItem().getItemMeta().getLore() == null){
            List<String> lore = new ArrayList<>();
            ItemMeta itemMeta = e.getCurrentItem().getItemMeta();
            lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "COMMON");
            itemMeta.setLore(lore);
            e.getCurrentItem().setItemMeta(itemMeta);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        DecimalFormat formatter = new DecimalFormat("#,###");
        formatter.setGroupingUsed(true);

        if (!e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
            if (!e.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
                ItemUtil.setDamageIndicator(e.getEntity().getLocation(), ChatColor.GRAY + "" + formatter.format(Math.round(e.getFinalDamage())));
            }
        }

        if (e.getEntity() instanceof Player){
            if (!e.getEntity().hasMetadata("NPC")) {
                SkyblockPlayer skyblockPlayer = main.getPlayer(e.getEntity().getName());
                skyblockPlayer.setStat(SkyblockStats.HEALTH, (int) (skyblockPlayer.getStat(SkyblockStats.HEALTH) - e.getFinalDamage()));
                e.setDamage((e.getFinalDamage() * (skyblockPlayer.getBukkitPlayer().getMaxHealth() / skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH))));
                //skyblockPlayer.getBukkitPlayer().sendMessage((e.getFinalDamage() * (skyblockPlayer.getStat(SkyblockStats.DEFENSE)/(skyblockPlayer.getStat(SkyblockStats.DEFENSE) + 100))) + " damage");
            }
        }

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) throws IOException {
        e.setDeathMessage(null);
        if (!e.getEntity().hasMetadata("NPC")) {
            Player player = e.getEntity();
            SkyblockPlayer skyblockPlayer = main.getPlayer(player.getName());
            skyblockPlayer.setStat(SkyblockStats.HEALTH, skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH));
            ItemStack item1 = new ItemStack(Material.NETHER_STAR);
            ItemMeta itemMeta = item1.getItemMeta();
            List<String> lore = new ArrayList<>();
            itemMeta.setDisplayName(ChatColor.GREEN + "Skyblock Menu (Right Click)");
            ItemUtil.addLoreMessage("View all of your Skyblock progress, including your Skills, Collections, Recipes, and more!", lore);
            itemMeta.setLore(lore);
            item1.setItemMeta(itemMeta);
            player.getInventory().setItem(8, item1);
            Config.setPurseCoins(player, (int) (Config.getPurseCoins(player) / 2));

            DecimalFormat formatter = new DecimalFormat("#,###");
            formatter.setGroupingUsed(true);

            if (Config.getPurseCoins(player) == 0){
                player.sendMessage(ChatColor.RED + "You died and lost 0 coins!");
            }else{
                player.sendMessage(ChatColor.RED + "You died and lost " + formatter.format(Config.getPurseCoins(player)) + " coins!");
            }

            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 100, 1);
        }
    }

    @EventHandler
    public void onNpcDamage(NPCDamageByEntityEvent e) throws IOException {
        int divider = 0;
        LivingEntity livingEntity = (LivingEntity) e.getNPC().getEntity();

        NPC npc = e.getNPC();

        e.setDamage(0);
        if (e.getNPC().getName().contains(ChatColor.RED + "Yeti")){
            divider = 2000000;
            loadDamage(e.getNPC(), divider, e.getDamager(), SkillType.FISHING, 3000);
            //e.getNPC().setName(ChatColor.RED + "Yeti " + ChatColor.GREEN + Main.format(Math.round(livingEntity.getHealth() * 2000000)) + ChatColor.RED + "❤");
            //CustomAI.yetiAI(npc);

        }else if (e.getNPC().getName().contains(ChatColor.RED + "Frozen Steve")){
            divider = 700;
            loadDamage(e.getNPC(), divider, e.getDamager(), SkillType.FISHING, 0);
            //e.getNPC().setName(ChatColor.RED + "Frozen Steve " + ChatColor.GREEN + Math.round(livingEntity.getHealth() * 700) + ChatColor.RED + "❤");
            //CustomAI.frozenSteveAI(e.getNPC());
        }
        ((LivingEntity) e.getNPC().getEntity()).setHealth(livingEntity.getHealth());
        ((LivingEntity) e.getNPC().getEntity()).setMaxHealth(livingEntity.getMaxHealth());
    }

    public void loadDamage(NPC npc, int divider, Entity damager, SkillType skillType, int xp) throws IOException {
        LivingEntity livingEntity = (LivingEntity) npc.getEntity();
        SkyblockPlayer skyblockPlayer = main.getPlayer(damager.getName());
        int i = new Random().nextInt(100);
        int r = new Random().nextInt(100);

        DecimalFormat formatter = new DecimalFormat("#,###");
        formatter.setGroupingUsed(true);
        double damage = 5 + skyblockPlayer.getStat((SkyblockStats.DAMAGE)) + (skyblockPlayer.getStat(SkyblockStats.STRENGTH) / 5) * (1 + skyblockPlayer.getStat(SkyblockStats.STRENGTH) / 100);
        if (!npc.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
            if (i <= skyblockPlayer.getStat(SkyblockStats.CRIT_CHANCE)) {
                ItemUtil.setDamageIndicator(npc.getEntity().getLocation(), ItemUtil.addCritTexture(String.valueOf(formatter.format(Math.round((damage * ((100 + skyblockPlayer.getStat(SkyblockStats.CRIT_DAMAGE))) / 100))))));
                if (((damage * ((100 + skyblockPlayer.getStat(SkyblockStats.CRIT_DAMAGE))) / 100) / divider) >= livingEntity.getHealth()) {
                    new NPCDeathEvent(npc, new EntityDeathEvent((LivingEntity) npc.getEntity(), new ArrayList<>()));
                    Bukkit.getPluginManager().callEvent(new SkyblockSkillExpGainEvent(skyblockPlayer, skillType, xp));
                    livingEntity.setHealth(0);
                    livingEntity.remove();
                    npc.destroy();
                } else {
                    livingEntity.setHealth(livingEntity.getHealth() - ((damage * ((100 + skyblockPlayer.getStat(SkyblockStats.CRIT_DAMAGE))) / 100) / divider));
                }
            } else {
                ItemUtil.setDamageIndicator(npc.getEntity().getLocation(), org.bukkit.ChatColor.GRAY + "" + formatter.format(Math.round(damage)));
                if (damage / divider >= livingEntity.getHealth()) {
                    new NPCDeathEvent(npc, new EntityDeathEvent((LivingEntity) npc.getEntity(), new ArrayList<>()));
                    Bukkit.getPluginManager().callEvent(new SkyblockSkillExpGainEvent(skyblockPlayer, skillType, xp));
                    livingEntity.setHealth(0);
                    livingEntity.remove();
                    npc.destroy();
                } else {
                    livingEntity.setHealth(livingEntity.getHealth() - damage / divider);
                }
            }
            if (r <= skyblockPlayer.getStat(SkyblockStats.FEROCITY)) {
                if (!skyblockPlayer.ferocityCooldown) {
                    for (int f = 0; f < Integer.valueOf(skyblockPlayer.getStat(SkyblockStats.FEROCITY)/100 + 1); ++f) {
                        skyblockPlayer.getBukkitPlayer().playSound(skyblockPlayer.getBukkitPlayer().getLocation(), Sound.FIRE_IGNITE, 100, 0);
                        skyblockPlayer.ferocityCooldown = true;
                        Bukkit.getPluginManager().callEvent(new NPCDamageByEntityEvent(npc, new EntityDamageByEntityEvent(damager, livingEntity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage)));
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            skyblockPlayer.ferocityCooldown = false;
                        }
                    }.runTaskLater(main, 5);
                }
            }
        }
    }

    @EventHandler
    public void onNPCDeath(NPCDeathEvent e) throws IOException {
        if (e.getNPC().getEntity() instanceof Player){
            e.getNPC().spawn(e.getEvent().getEntity().getLocation());
            SkyblockPlayer skyblockPlayer = Main.getMain().getPlayer(((CraftPlayer) e.getNPC()).getKiller().getName());
            e.getNPC().despawn();
            if (e.getNPC().getName().contains(ChatColor.RED + "Yeti")) {
                Bukkit.getPluginManager().callEvent(new SkyblockSkillExpGainEvent(skyblockPlayer, SkillType.FISHING, 3000));
            }
        }
        e.getNPC().destroy();
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        if (e.getRightClicked() instanceof Player){
            if (!e.getRightClicked().hasMetadata("NPC") && player.getItemInHand().getType().equals(Material.AIR) && !player.isSneaking()) {
                    Player righClicked = (Player) e.getRightClicked();
                    SkyblockPlayer skyblockPlayer = main.getPlayer(player.getName());
                    SkyblockPlayer rightClickedPlayer = main.getPlayer(righClicked.getName());

                    skyblockPlayer.setInventory(righClicked.getName() + "'s Profile", Bukkit.createInventory(null, 54, righClicked.getName() + "'s Profile"));

                    Inventory menu = skyblockPlayer.getInventory(righClicked.getName() + "'s Profile");

                    ItemStack close = new ItemStack(Material.BARRIER);
                    ItemMeta closeMeta = close.getItemMeta();
                    closeMeta.setDisplayName(ChatColor.RED + "Close");

                    ItemStack space1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.BLACK.getData());
                    ItemMeta itemMeta = space1.getItemMeta();
                    itemMeta.setDisplayName(" ");
                    space1.setItemMeta(itemMeta);
                    close.setItemMeta(closeMeta);

                    ItemStack noItem = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData());
                    ItemMeta noItemMeta = noItem.getItemMeta();
                    List<String> lore2 = new ArrayList<>();
                    noItemMeta.setDisplayName(ChatColor.RED + "Empty Slot!");
                    lore2.add(ChatColor.RED + "This slot is currently");
                    lore2.add(ChatColor.RED + "empty!");
                    noItemMeta.setLore(lore2);
                    noItem.setItemMeta(noItemMeta);

                    ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
                    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                    skullMeta.setOwner(rightClickedPlayer.getBukkitPlayer().getName());
                    skull.setItemMeta(skullMeta);
                    ArrayList<String> lore = new ArrayList<String>();
                    skullMeta.setDisplayName(ChatColor.GREEN + righClicked.getName() + "'s Skyblock Stats");
                    lore.add(ChatColor.RED + "❤ Health " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.HEALTH) + " HP");
                    lore.add(ChatColor.GREEN + "❈ Defence " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.DEFENSE));
                    lore.add(ChatColor.RED + "❁ Strength " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.STRENGTH));
                    lore.add(ChatColor.WHITE + "✦ Speed " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.SPEED));
                    lore.add(ChatColor.BLUE + "☣ Crit Chance " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.CRIT_CHANCE) + "%");
                    lore.add(ChatColor.BLUE + "☠ Crit Damage " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.CRIT_DAMAGE) + "%");
                    lore.add(ChatColor.AQUA + "✎ Intelligence " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.MANA));
                    lore.add(ChatColor.YELLOW + "⚔ Attack Speed " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.ATTACK_SPEED) + "%");
                    lore.add(ChatColor.DARK_AQUA + "∞ Sea Creature Chance " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.SEA_CREATURE_CHANCE) + "%");
                    lore.add(ChatColor.AQUA + "✯ Magic Find " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.MAGIC_FIND));
                    lore.add(ChatColor.LIGHT_PURPLE + "♣ Pet Luck " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.PET_LUCK));
                    lore.add(ChatColor.WHITE + "❂ True Defense " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.TRUE_DEFENSE));
                    lore.add(ChatColor.RED + "⫽ Ferocity " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.FEROCITY));
                    lore.add(ChatColor.RED + "✹ Ability Damage " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.ABILITY_DAMAGE) + "%");
                    lore.add(ChatColor.GOLD + "☘ Mining Fortune " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.MINING_FORTUNE));
                    lore.add(ChatColor.GOLD + "⸕ Mining Speed " + ChatColor.WHITE + rightClickedPlayer.getStat(SkyblockStats.MINING_SPEED));
                    skullMeta.setLore(lore);
                    skull.setItemMeta(skullMeta);

                    for (int i = 0; i < 54; ++i) {
                        menu.setItem(i, space1);
                    }

                    if (!righClicked.getItemInHand().getType().equals(Material.AIR)) {
                        menu.setItem(1, righClicked.getItemInHand());
                    } else {
                        menu.setItem(1, noItem);
                    }
                    if (righClicked.getInventory().getHelmet() != null) {
                        menu.setItem(10, righClicked.getInventory().getHelmet());
                    } else {
                        menu.setItem(10, noItem);
                    }
                    if (righClicked.getInventory().getChestplate() != null) {
                        menu.setItem(19, righClicked.getInventory().getChestplate());
                    } else {
                        menu.setItem(19, noItem);
                    }
                    if (righClicked.getInventory().getLeggings() != null) {
                        menu.setItem(28, righClicked.getInventory().getLeggings());
                    } else {
                        menu.setItem(28, noItem);
                    }
                    if (righClicked.getInventory().getBoots() != null) {
                        menu.setItem(37, righClicked.getInventory().getBoots());
                    } else {
                        menu.setItem(37, noItem);
                    }
                    if (rightClickedPlayer.activePet != null) {
                        menu.setItem(46, rightClickedPlayer.activePet.getHelmet());
                    } else {
                        menu.setItem(46, noItem);
                    }


                    menu.setItem(15, new ClickGuiItem("Visit Island", "Click to visit!", 288, 0));
                    menu.setItem(24, new ClickGuiItem("Invite to Island", "Click to invite", 38, 0));
                    menu.setItem(33, new ClickGuiItem("Personal Vault", "Click to view!", 130, 0));
                    menu.setItem(16, new ClickGuiItem("Trade Request", "Send a trade request", 388, 0));
                    menu.setItem(25, new ClickGuiItem("Co-op Request", "Send a co-op request!", 264, 0));
                    menu.setItem(22, skull);
                    menu.setItem(49, close);

                    player.openInventory(menu);
            }else if (!e.getRightClicked().hasMetadata("NPC") && player.isSneaking()){
                player.performCommand("trade " + e.getRightClicked().getName());
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        double NPCdamage;
        if (e.getDamager() instanceof Player) {
            if (!e.getDamager().hasMetadata("NPC") && !e.getEntity().hasMetadata("NPC")) {
                SkyblockPlayer skyblockPlayer = main.getPlayer(e.getDamager().getName());
                int i = new Random().nextInt(100);
                int r = new Random().nextInt(100);
                DecimalFormat formatter = new DecimalFormat("#,###");
                formatter.setGroupingUsed(true);
                double damage = 5 + skyblockPlayer.getStat((SkyblockStats.DAMAGE)) + (skyblockPlayer.getStat(SkyblockStats.STRENGTH) / 5) * (1 + skyblockPlayer.getStat(SkyblockStats.STRENGTH) / 100);
                if (!e.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
                    if (i <= skyblockPlayer.getStat(SkyblockStats.CRIT_CHANCE)) {
                        e.setDamage(damage * ((100 + skyblockPlayer.getStat(SkyblockStats.CRIT_DAMAGE))) / 100);
                        ItemUtil.setDamageIndicator(e.getEntity().getLocation(), ItemUtil.addCritTexture(String.valueOf(formatter.format(Math.round(e.getDamage())))));
                    } else {
                        e.setDamage(damage);
                        ItemUtil.setDamageIndicator(e.getEntity().getLocation(), org.bukkit.ChatColor.GRAY + "" + formatter.format(Math.round(e.getDamage())));
                    }
                    if (r <= skyblockPlayer.getStat(SkyblockStats.FEROCITY)) {
                        if (!skyblockPlayer.ferocityCooldown) {
                            skyblockPlayer.getBukkitPlayer().playSound(skyblockPlayer.getBukkitPlayer().getLocation(), Sound.FIRE_IGNITE, 100, 0);
                            skyblockPlayer.ferocityCooldown = true;
                            Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(skyblockPlayer.getBukkitPlayer(), e.getEntity(), EntityDamageEvent.DamageCause.ENTITY_ATTACK, damage));
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    skyblockPlayer.ferocityCooldown = false;
                                }
                            }.runTaskLater(main, 5);
                        }
                    }
                }
            }else if (e.getDamager().hasMetadata("NPC") && !e.getEntity().hasMetadata("NPC")){
                NPC npc = CitizensAPI.getNPCRegistry().getNPC(e.getDamager());
                SkyblockPlayer skyblockPlayer = main.getPlayer(e.getEntity().getName());
                if (npc.getName().contains(ChatColor.RED + "Yeti")) {
                    e.setDamage((300 * (skyblockPlayer.getBukkitPlayer().getMaxHealth() / skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH))));
                    skyblockPlayer.setStat(SkyblockStats.HEALTH, (skyblockPlayer.getStat(SkyblockStats.HEALTH) - 300));
                }else if (npc.getName().contains(ChatColor.RED + "Frozen Steve")){
                    e.setDamage((20 * (skyblockPlayer.getBukkitPlayer().getMaxHealth() / skyblockPlayer.getStat(SkyblockStats.MAX_HEALTH))));
                    skyblockPlayer.setStat(SkyblockStats.HEALTH, (skyblockPlayer.getStat(SkyblockStats.HEALTH) - 50));
                }
            }

        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        SkyblockPlayer skyblockPlayer = main.getPlayer(player.getName());
        Vector vector = player.getVelocity();
        double blocksPerSecond = vector.length();
        //player.sendMessage(blocksPerSecond + " b/s");
        if (skyblockPlayer.getStat(SkyblockStats.SPEED) > 900){
            skyblockPlayer.setStat(SkyblockStats.SPEED, 900);
        }
        player.setWalkSpeed(skyblockPlayer.getStat(SkyblockStats.SPEED)/1000F + 0.1F);
        if (player.getLocation().getY() <= -11){
            player.setHealth(0);
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) throws IOException {
        Player player = e.getPlayer();
        if (e.getPlayer().getItemInHand() != null) {
            if (e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "Skyblock Menu (Right Click)")) {
                player.performCommand("sbmenu");
            }else if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().startsWith(ChatColor.GRAY + "[Lvl ")){
                if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (player.getItemInHand().getItemMeta().getDisplayName().split(" ").length == 3) {
                        player.sendMessage(ChatColor.GREEN + "Successfully added " + ChatColor.getLastColors(player.getItemInHand().getItemMeta().getDisplayName()) + player.getItemInHand().getItemMeta().getDisplayName().split(" ")[2] + ChatColor.GREEN + " to your pets menu!");
                    }else{
                        player.sendMessage(ChatColor.GREEN + "Successfully added " + ChatColor.getLastColors(player.getItemInHand().getItemMeta().getDisplayName()) + player.getItemInHand().getItemMeta().getDisplayName().split(" ")[2] + ChatColor.getLastColors(player.getItemInHand().getItemMeta().getDisplayName()) + " " + player.getItemInHand().getItemMeta().getDisplayName().split(" ")[3] + ChatColor.GREEN + " to your pets menu!");
                    }
                    Config.addPet(player, player.getItemInHand());
                    player.setItemInHand(null);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10, 1);
                }
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if (e.getMessage().equals("[item]")){
            e.setMessage("LOOK AT MY INSANE " + e.getPlayer().getItemInHand().getItemMeta().getDisplayName());
        }
    }

}
