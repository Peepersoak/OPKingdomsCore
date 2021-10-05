package me.peepersoak.opkingdomscore.dragon_event.GUI.drag_skill;

import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import me.peepersoak.opkingdomscore.utilities.GUIButton;
import me.peepersoak.opkingdomscore.utilities.GUICreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class DragonSkillGUI {

    public Inventory openGUI() {
        GUICreator gui = new GUICreator(DragonStringpath.DRAGON_SKILL_GUI_NAME, 9);

        Inventory inv = gui.getInv();

        GUIButton button = new GUIButton();

        DragonEventData data = new DragonEventData();

        List<String> lore;

        String name;

        for (int i = 0; i < 9; i++) {
            switch (i) {
                case 0:
                    name = ChatColor.GOLD + "Lightning Strike";
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Strike lightning on players");
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Currently set to:");
                    lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_LIGHTNINGSTRIKE));
                    inv.setItem(i, button.createButton(Material.LIGHTNING_ROD, name, lore, false));
                    break;
                case 1:
                    name = ChatColor.GOLD + "Explosive Skill";
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Make the player explode");
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Currently set to:");
                    lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_EXPLOSION));
                    inv.setItem(i, button.createButton(Material.FIRE_CHARGE, name, lore, false));
                    break;
                case 2:
                    name = ChatColor.GOLD + "Wither Skill";
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Add wither 2 Effect to player");
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Currently set to:");
                    lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_WITHER));
                    inv.setItem(i, button.createButton(Material.WITHER_ROSE, name, lore, false));
                    break;
                case 3:
                    name = ChatColor.GOLD + "Dragon Guardian";
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Click to open Guardian GUI");
                    inv.setItem(i, button.createButton(Material.WITHER_SKELETON_SKULL, name, lore, false));
                    break;
                case 4:
                    name = ChatColor.GOLD + "AOE Lightning Strike";
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Click to open AOE Lightning GUI");
                    inv.setItem(i, button.createButton(Material.TRIDENT, name, lore, true));
                    break;
            }
        }
        return inv;
    }
}
