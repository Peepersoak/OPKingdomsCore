package me.peepersoak.opkingdomscore.dragon_event.GUI.drag_phase;

import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import me.peepersoak.opkingdomscore.utilities.GUIButton;
import me.peepersoak.opkingdomscore.utilities.GUICreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class DragPhaseGUI {

    public Inventory openGUI() {
        GUICreator gui = new GUICreator(DragonStringpath.DRAGON_PHASE_CONTROL_GUI, 9);
        Inventory inv = gui.getInv();

        String name;
        List<String> lore;

        GUIButton button = new GUIButton();

        for (int i = 0; i < 9; i++) {
            switch (i) {
                case 0:
                    name = ChatColor.GOLD + "Circle";
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Make dragon fly and");
                    lore.add(ChatColor.AQUA + "Circle Around the pillars");
                    inv.setItem(i, button.createButton(Material.ELYTRA, name, lore, true));
                    break;
                case 1:
                    name = ChatColor.GOLD + "Charge";
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Make dragon charge");
                    lore.add(ChatColor.AQUA + "on player");
                    inv.setItem(i, button.createButton(Material.NETHERITE_SWORD, name, lore, true));
                    break;
                case 2:
                    name = ChatColor.GOLD + "Fly to Portal";
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Make dragon fly");
                    lore.add(ChatColor.AQUA + "and land to portal");
                    inv.setItem(i, button.createButton(Material.BEDROCK, name, lore, true));
                    break;
                case 3:
                    name = ChatColor.GOLD + "Hover";
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Make dragon hover");
                    lore.add(ChatColor.AQUA + "at its location");
                    inv.setItem(i, button.createButton(Material.SLIME_BALL, name, lore, true));
                    break;
                case 8:
                    name = ChatColor.RED + "Die";
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Make dragon fly");
                    lore.add(ChatColor.AQUA + "to vicinity and die");
                    inv.setItem(i, button.createButton(Material.DRAGON_EGG, name, lore, true));
                    break;
                default:
                    inv.setItem(i, button.createButton(Material.BLACK_STAINED_GLASS_PANE, " ", null, false));
            }
        }
        return inv;
    }
}
