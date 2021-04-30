//package com.itech4kids.skyblock.Npcs;
//
//import net.minecraft.server.v1_8_R3.EntityPlayer;
//import org.bukkit.entity.Player;
//import org.bukkit.event.Cancellable;
//import org.bukkit.event.Event;
//import org.bukkit.event.HandlerList;
//
//public class NpcClickEvent extends Event implements Cancellable {
//
//    private final Player player;
//    private final EntityPlayer npc;
//    private boolean isCancelled;
//    private static final HandlerList HANDLERS = new HandlerList();
//    private boolean isLeftClick;
//
//    public NpcClickEvent(Player player, EntityPlayer npc, boolean b){
//        this.player = player;
//        this.npc = npc;
//        this.isLeftClick = b;
//    }
//
//    public Player getPlayer(){
//        return player;
//    }
//
//    public boolean isLeftClick(){return isLeftClick;}
//
//    public EntityPlayer getNpc(){
//        return npc;
//    }
//
//    public static HandlerList getHandlersList(){
//        return HANDLERS;
//    }
//
//    public static HandlerList getHandlerList(){
//        return HANDLERS;
//    }
//
//    public static HandlerList getHandler(){
//        return HANDLERS;
//    }
//
//    @Override
//    public HandlerList getHandlers() {
//        return HANDLERS;
//    }
//
//    @Override
//    public boolean isCancelled() {
//        return isCancelled;
//    }
//
//    @Override
//    public void setCancelled(boolean b) {
//        isCancelled = b;
//    }
//
//}
