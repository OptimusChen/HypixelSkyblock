package com.itech4kids.skyblock.Listeners;

import com.itech4kids.skyblock.CustomMobs.PlayerEntities.CustomAI;
import com.itech4kids.skyblock.CustomMobs.Zombie.SkyblockZombie;
import com.itech4kids.skyblock.CustomMobs.Zombie.SkyblockZombieType;
import com.itech4kids.skyblock.Events.SkyblockSkillExpGainEvent;
import com.itech4kids.skyblock.Main;
import com.itech4kids.skyblock.Objects.SkillType;
import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import com.itech4kids.skyblock.Objects.SkyblockStats;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SkillGainListeners implements Listener {

    public Main main;

    public SkillGainListeners(Main main){
        this.main = main;
    }

    @EventHandler
    public void onFish(PlayerFishEvent e) throws IOException {
        int i = new Random().nextInt(50);
        int r = i + 7;
        int c = new Random().nextInt(100);
        int r2 = new Random().nextInt(2);
        Player player = e.getPlayer();
        SkyblockPlayer skyblockPlayer = main.getPlayer(player.getName());
        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
        double x = e.getHook().getLocation().getX();
        double y = e.getHook().getLocation().getY();
        double z = e.getHook().getLocation().getZ();
        if (e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            if (c <= skyblockPlayer.getStat(SkyblockStats.SEA_CREATURE_CHANCE)) {
                if (r2 == 0) {
                    SkyblockZombie skyblockZombie = new SkyblockZombie(SkyblockZombieType.SEA_WALKER, ((CraftWorld) player.getWorld()).getHandle());
                    world.addEntity(skyblockZombie);
                    skyblockZombie.enderTeleportTo(x, y, z);
                } else if (r2 == 1) {
                    NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ChatColor.RED + "Yeti " + ChatColor.GREEN + Main.format(1 * 2000000) + ChatColor.RED + "❤");
                    npc.spawn(new Location(world.getWorld(), x, y, z));
                    npc.data().set(NPC.NAMEPLATE_VISIBLE_METADATA, false);
                    ArmorStand healthDisplay = npc.getEntity().getWorld().spawn(new Location(npc.getEntity().getLocation().getWorld(), npc.getEntity().getLocation().getX(), npc.getEntity().getLocation().getY() + 0.75, npc.getEntity().getLocation().getZ()), ArmorStand.class);
                    healthDisplay.setCustomNameVisible(true);
                    healthDisplay.setGravity(false);
                    healthDisplay.setVisible(false);
                    healthDisplay.setSmall(true);
                    healthDisplay.setCustomName(ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "Lvl" + 175 + ChatColor.DARK_GRAY + "] " + npc.getName());
                    npc.setProtected(false);
                    CustomAI.yetiAI(npc, healthDisplay);
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) throws IOException {
        e.setCancelled(true);
        Block block = e.getBlock();
        Block oldBlock;
        byte id = 0;
        int type = 0;
        SkyblockPlayer skyblockPlayer = main.getPlayer(e.getPlayer().getName());
        switch (block.getType()){
            case STONE:
                if (block.getData() == 0) {
                    Bukkit.getPluginManager().callEvent(new SkyblockSkillExpGainEvent(skyblockPlayer, SkillType.MINING, 1));
                    for (ItemStack itemStack : block.getDrops()) {
                        block.getWorld().dropItemNaturally(block.getLocation(), itemStack);
                    }
                    block.setType(Material.COBBLESTONE);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            block.setType(Material.STONE);
                        }
                    }.runTaskLater(main, 160);
                }
                break;
            case COBBLESTONE:
                Bukkit.getPluginManager().callEvent(new SkyblockSkillExpGainEvent(skyblockPlayer, SkillType.MINING, 1));
                for (ItemStack itemStack : block.getDrops()){
                    block.getWorld().dropItemNaturally(block.getLocation(), itemStack);
                }
                block.setType(Material.BEDROCK);
                new BukkitRunnable() {
                    @Override
                    public void run () {
                        block.setType(Material.STONE);
                    }
                }.runTaskLater(main, 160);
                break;
        }
    }
}
