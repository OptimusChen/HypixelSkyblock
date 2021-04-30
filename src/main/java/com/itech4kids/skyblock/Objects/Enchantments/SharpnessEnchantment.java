package com.itech4kids.skyblock.Objects.Enchantments;

import com.itech4kids.skyblock.Main;
import net.minecraft.server.v1_8_R3.ItemSword;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SharpnessEnchantment extends Enchantment implements Listener {

    public SharpnessEnchantment() {
        super(0);
        Bukkit.getPluginManager().registerEvents(this, Main.getMain());
    }

    @Override
    public String getName() {
        return "Sharpness";
    }

    @Override
    public int getMaxLevel() {
        return 7;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        boolean b = false;
        if (enchantment.equals(Enchantment.DAMAGE_UNDEAD)){
            b = true;
        }
        return b;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        boolean b = false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemSword){
            b = true;
        }
        return b;
    }

    
}
