package me.peepersoak.opkingdomscore.dragon_event.GUI;

import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUICreator {

    private Inventory inv;

    public Inventory getInv() {
        return inv;
    }

    public void createInventory() {
        GUIButton button = new GUIButton();
        DragonEventData data = new DragonEventData();
        inv = Bukkit.createInventory(null, 54, DragonStringpath.DRAGON_GUI_NAME);

        ItemStack pane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        pane.setItemMeta(meta);

        ItemStack dragonHead = new ItemStack(Material.DRAGON_HEAD);
        ItemMeta dragonHeadMeta = dragonHead.getItemMeta();
        assert dragonHeadMeta != null;
        dragonHeadMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + DragonStringpath.DRAGON_EVENT_SETTING_NAME);
        dragonHead.setItemMeta(dragonHeadMeta);

        for (int i = 0; i < 54; i++) {
            switch (i) {
                case 4:
                    inv.setItem(i, dragonHead);
                    break;
                case 10:
                    inv.setItem(i, button.worldButton());
                    break;
                case 11:
                    inv.setItem(i, button.spawnButton());
                    break;
                case 15:
                    inv.setItem(i, button.setEndCrystal());
                    break;
                case 16:
                    inv.setItem(i, button.clearEndCrystal());
                    break;
                case 28:
                    inv.setItem(i, button.dragonName());
                    break;
                case 29:
                    inv.setItem(i, button.dragonHealth());
                    break;
                case 30:
                    inv.setItem(i, button.allowSkill());
                    break;
                case 31:
                    inv.setItem(i, button.dragonHealthThreshold());
                    break;
                case 32:
                    inv.setItem(i, button.dragonSkillDistance());
                    break;
                case 33:
                    inv.setItem(i, button.dragonSkillCooldown());
                    break;
                case 34:
                    inv.setItem(i, button.playerPercentage());
                    break;
                case 46:
                    inv.setItem(i, button.lightningStrike());
                    break;
                case 47:
                    inv.setItem(i, button.explosion());
                    break;
                case 48:
                    inv.setItem(i, button.wither());
                    break;
                case 49:
                    inv.setItem(i, button.guardian());
                    break;
                case 51:
                    inv.setItem(i, button.statusOverride());
                    break;
                case 52:
                    inv.setItem(i, button.particle());
                    break;
                default:
                    inv.setItem(i, pane);
                    break;
            }
        }
    }
}
