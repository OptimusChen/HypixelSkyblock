package com.itech4kids.skyblock.Commands;

import com.itech4kids.skyblock.CustomMobs.Dragon.SkyblockDragon;
import com.itech4kids.skyblock.CustomMobs.Dragon.SkyblockDragonType;
import com.itech4kids.skyblock.CustomMobs.Enderman.SkyblockEnderman;
import com.itech4kids.skyblock.CustomMobs.Enderman.SkyblockEndermanType;
import com.itech4kids.skyblock.CustomMobs.Zombie.SkyblockZombie;
import com.itech4kids.skyblock.CustomMobs.Zombie.SkyblockZombieType;
import com.itech4kids.skyblock.Main;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SpawnCustomMobCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            World world = ((CraftWorld) player.getWorld()).getHandle();
            double x = player.getLocation().getX();
            double y = player.getLocation().getY();
            double z = player.getLocation().getZ();
            Main.registerEntity("Enderman", 58, SkyblockEnderman.class);
            Main.registerEntity("Dragon", 63, SkyblockDragon.class);
            Main.registerEntity("Zombie", 54, SkyblockZombie.class);
            if (args.length == 0){
                player.sendMessage(ChatColor.RED + "Please enter a mob type!");
                player.sendMessage(ChatColor.RED + "/spawncm <mob type> <mob name>");
            }else if (args.length == 1){
                player.sendMessage(ChatColor.RED + "Please enter a mob name!");
                player.sendMessage(ChatColor.RED + "/spawncm <mob type> <mob name>");
            }else{
                switch (args[0].toLowerCase()){
                    case "zombie":
                        SkyblockZombie skyblockZombie = new SkyblockZombie(SkyblockZombieType.valueOf(args[1].toUpperCase()), world);
                        skyblockZombie.enderTeleportTo(x, y, z);
                        world.addEntity(skyblockZombie);
                        break;
                    case "dragon":
                        SkyblockDragon skyblockDragon = new SkyblockDragon(SkyblockDragonType.valueOf(args[1].toUpperCase()), world);
                        skyblockDragon.enderTeleportTo(x, y, z);
                        world.addEntity(skyblockDragon);
                        break;
                    case "enderman":
                        SkyblockEnderman skyblockEnderman = new SkyblockEnderman(SkyblockEndermanType.valueOf(args[1].toUpperCase()), world);
                        skyblockEnderman.enderTeleportTo(x, y, z);
                        world.addEntity(skyblockEnderman);
                        break;
                }
            }
        }
        return false;
    }
}
