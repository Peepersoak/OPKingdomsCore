package me.peepersoak.opkingdomscore.jobscertificate.GUI.upgrade;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.utilities.GUIButton;
import me.peepersoak.opkingdomscore.utilities.GUICreator;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class UpgradeGUI {

    public Inventory openGUI(Player player) {
        GUICreator gui = new GUICreator(JobsString.UPGRADE_GUI_NAME, 27);
        Inventory inv = gui.getInv();

        GUIButton button = new GUIButton();
        String name;
        List<String> lore;

        for (int i = 0; i < 27; i++) {
            if (i == 13) {
                name = ChatColor.GREEN + "Upgrade Requirements:";
                lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.GOLD + "" + JobsUtil.getTokenNeeded(player) + " Tokens");
                lore.add(ChatColor.GOLD + "" + JobsUtil.getPlayerJobXPTarget(player) + " Experience");
                lore.add("");
                lore.add(ChatColor.AQUA + "You have:");
                lore.add(ChatColor.LIGHT_PURPLE + "" + JobsUtil.getPlayerToken(player) + " Tokens");
                lore.add(ChatColor.LIGHT_PURPLE + "" + JobsUtil.getPlayerJobXP(player) + " Experience");
                lore.add("");
                lore.add(ChatColor.YELLOW + "Click this if you want to proceed");
                inv.setItem(i, button.createButton(Material.LIME_STAINED_GLASS_PANE, name, lore, false));
            } else {
                inv.setItem(i, button.createButton(Material.BLACK_STAINED_GLASS_PANE, " ", null, false));
            }
        }
        return inv;
    }
}
