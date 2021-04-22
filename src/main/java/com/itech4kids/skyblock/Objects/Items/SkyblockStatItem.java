package com.itech4kids.skyblock.Objects.Items;

import com.itech4kids.skyblock.Objects.SkyblockPlayer;
import com.itech4kids.skyblock.Objects.SkyblockStats;
import com.itech4kids.skyblock.Util.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SkyblockStatItem extends ItemStack {

    public List<String> lore;
    public ItemMeta itemMeta;

    public SkyblockStatItem(SkyblockStats stat, SkyblockPlayer skyblockPlayer, String name, String lore1, String lore1p2, String lore2, String lore2p2, int id){
        this.setTypeId(id);
        itemMeta = this.getItemMeta();
        lore = new ArrayList<>();

        itemMeta.setDisplayName(name + " " + ChatColor.WHITE + skyblockPlayer.getStat(stat));

        ItemUtil.addLoreMessage(lore1, this);
        ItemUtil.addItalicLore(lore1p2, this);
        ItemUtil.addLoreMessage(lore2, this);
        ItemUtil.addItalicLore(lore2p2, this);

        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
    }
}
