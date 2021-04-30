//package com.itech4kids.skyblock.Npcs;
//
//import com.itech4kids.skyblock.CustomMobs.PlayerEntities.SkyblockYeti;
//import com.itech4kids.skyblock.Main;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToMessageDecoder;
//import net.minecraft.server.v1_8_R3.EntityPlayer;
//import net.minecraft.server.v1_8_R3.Packet;
//import org.bukkit.Bukkit;
//import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
//import org.bukkit.entity.Player;
//import org.bukkit.event.entity.EntityDamageByEntityEvent;
//import org.bukkit.event.entity.EntityDamageEvent;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//public class PacketReader {
//
//    Channel channel;
//    public static Map<UUID, Channel> channels = new HashMap<UUID, Channel>();
//
//    public void inject(Player player) {
//        CraftPlayer craftPlayer = (CraftPlayer) player;
//        channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
//        channels.put(player.getUniqueId(), channel);
//
//        if (channel.pipeline().get("PacketInjector") != null)
//            return;
//
//        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {
//
//            @Override
//            protected void decode(ChannelHandlerContext channel, Packet<?> packet, List<Object> list) throws Exception {
//                list.add(packet);
//                readPacket(player, packet);
//            }
//        });
//    }
//
//    public void unInject(Player player){
//        channel = channels.get(player.getUniqueId());
//        if (channel.pipeline().get("PacketInjector") != null){
//            channel.pipeline().remove("PacketInjector");
//        }
//    }
//
//    public void readPacket(Player player, Packet packet){
//
//        if (packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")){
//
//            int id = (int) getValue(packet, "a");
//
//            if (getValue(packet, "action").toString().equalsIgnoreCase("ATTACK"))
////                for (EntityPlayer npc : SkyblockYeti.getNpcs()){
////                    if (npc.getId() == id){
////                        new BukkitRunnable() {
////                            @Override
////                            public void run () {
////                                Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(player, npc.getBukkitEntity(), EntityDamageEvent.DamageCause.ENTITY_ATTACK, 1));
////                            }
////                        }.runTaskLater(Main.getMain(), 5);
////                    }
////                }
//            if (getValue(packet, "d").toString().equalsIgnoreCase("OFF_HAND"))
//                return;
//            if (getValue(packet, "action").toString().equalsIgnoreCase("INTERACT_AT"))
//                return;
//            if (getValue(packet, "action").toString().equalsIgnoreCase("INTERACT"))
//                for (EntityPlayer npc : SkyblockYeti.getNpcs()){
////                    if (npc.getId() == id){
////                        new BukkitRunnable() {
////                            @Override
////                            public void run () {
////                                Bukkit.getPluginManager().callEvent(new NpcClickEvent(player, npc, false));
////                            }
////                        }.runTaskLater(Main.getMain(), 5);
////                    }
//                }
//        }
//    }
//
//    private Object getValue(Object instance, String name){
//        Object result = null;
//
//        try {
//
//            Field field = instance.getClass().getDeclaredField(name);
//            field.setAccessible(true);
//            result = field.get(instance);
//
//            field.setAccessible(false);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//}
