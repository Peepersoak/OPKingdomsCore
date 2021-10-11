package me.peepersoak.opkingdomscore.jobscertificate.GUI.upgrade;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.utilities.GUIButton;
import me.peepersoak.opkingdomscore.utilities.GUICreator;
import me.peepersoak.opkingdomscore.utilities.GUIUtils;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class UpgradeGUI {

    public Inventory openGUI(Player player) {
        GUICreator gui = new GUICreator(GUIUtils.getGUITItle(JobsString.JOB_UPGRADE_GUI + "." + JobsString.TITLE), 27);
        Inventory inv = gui.getInv();

        GUIButton button = new GUIButton();

        String jobPath = JobsString.JOB_UPGRADE_GUI + "." + JobsString.BUTTON;

        String name;
        Material material;
        boolean glow;
        List<String> lore;

        for (int i = 0; i < 27; i++) {
            if (i == 13) {
                name = GUIUtils.getButtonTitle(jobPath, 0);
                material = GUIUtils.getButtonMaterial(jobPath, 0);
                glow = GUIUtils.getButtonIsGlowing(jobPath, 0);
                lore = GUIUtils.getButtonLores(jobPath, 0, player);
                inv.setItem(i, button.createButton(material, name, lore, glow));
            } else {
                inv.setItem(i, button.createButton(Material.BLACK_STAINED_GLASS_PANE, " ", null, false));
            }
        }
        return inv;
    }
}
