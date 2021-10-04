package me.peepersoak.opkingdomscore.dragon_event.GUI.aoe_lightning;

import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AOELightningSettingsGUI {

    public Inventory createInventory() {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.RED + "" + ChatColor.BOLD + DragonStringpath.DRAGON_SKILL_AOE_SETTINGS_GUI);

        AOELightningButtonFactory btn = new AOELightningButtonFactory();

        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        item.setItemMeta(meta);

        for (int i = 0; i < 9; i++) {
            switch (i) {
                case 0:
                    inv.setItem(i, btn.createButton("Allow"));
                    break;
                case 1:
                    inv.setItem(i, btn.createButton("Interval"));
                    break;
                case 2:
                    inv.setItem(i,  btn.createButton("Wither Amplifier"));
                    break;
                case 3:
                    inv.setItem(i,  btn.createButton("Target"));
                    break;
                case 4:
                    inv.setItem(i,  btn.createButton("Distance"));
                    break;
                case 5:
                    inv.setItem(i, btn.createButton("Threshold"));
                    break;
                case 8:
                    inv.setItem(i, btn.createButton("Back"));
                    break;
                default:
                    inv.setItem(i, item);
            }
        }
        return inv;
    }
}
