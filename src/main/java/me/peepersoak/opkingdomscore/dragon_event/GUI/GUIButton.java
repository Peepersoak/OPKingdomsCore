package me.peepersoak.opkingdomscore.dragon_event.GUI;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GUIButton {
    public ItemStack createButton(Material mat, String name, List<String> lore, boolean glowing) {
        ItemStack item = new ItemStack(mat);
        if (glowing) {
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 0);
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
}
