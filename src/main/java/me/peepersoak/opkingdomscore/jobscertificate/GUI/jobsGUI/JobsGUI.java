package me.peepersoak.opkingdomscore.jobscertificate.GUI.jobsGUI;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.utilities.GUIButton;
import me.peepersoak.opkingdomscore.utilities.GUICreator;
import me.peepersoak.opkingdomscore.utilities.GUIUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;


public class JobsGUI {

    public Inventory openJobChangeGUI(Player player) {
        GUICreator creator = new GUICreator(GUIUtils.getGUITItle(JobsString.JOB_APPLY_GUI + "." + JobsString.CHANGE), 36);
        Inventory inv = creator.getInv();
        return addButtons(inv, player);
    }

    public Inventory openMainGUI(Player player) {
        GUICreator creator = new GUICreator(GUIUtils.getGUITItle(JobsString.JOB_APPLY_GUI + "." + JobsString.TITLE), 36);
        Inventory inv = creator.getInv();
        return addButtons(inv, player);
    }

    public Inventory addButtons(Inventory inv, Player player) {
        GUIButton btn = new GUIButton();

        String jobPath = JobsString.JOB_APPLY_GUI + "." + JobsString.BUTTON;

        String name;
        Material material;
        boolean glow;
        List<String> lore;

        for (int i = 0; i < 36; i++) {
            switch (i) {
                case 10:
                    name = GUIUtils.getButtonTitle(jobPath, 0);
                    material = GUIUtils.getButtonMaterial(jobPath, 0);
                    glow = GUIUtils.getButtonIsGlowing(jobPath, 0);
                    lore = GUIUtils.getButtonLores(jobPath, 0, player);
                    inv.setItem(i, btn.createButton(material, name, lore, glow));
                    break;
                case 12:
                    name = GUIUtils.getButtonTitle(jobPath, 1);
                    material = GUIUtils.getButtonMaterial(jobPath, 1);
                    glow = GUIUtils.getButtonIsGlowing(jobPath, 1);
                    lore = GUIUtils.getButtonLores(jobPath, 1, player);
                    inv.setItem(i, btn.createButton(material, name, lore, glow));
                    break;
                case 14:
                    name = GUIUtils.getButtonTitle(jobPath, 2);
                    material = GUIUtils.getButtonMaterial(jobPath, 2);
                    glow = GUIUtils.getButtonIsGlowing(jobPath, 2);
                    lore = GUIUtils.getButtonLores(jobPath, 2, player);
                    inv.setItem(i, btn.createButton(material, name, lore, glow));
                    break;
                case 16:
                    name = GUIUtils.getButtonTitle(jobPath, 3);
                    material = GUIUtils.getButtonMaterial(jobPath, 3);
                    glow = GUIUtils.getButtonIsGlowing(jobPath, 3);
                    lore = GUIUtils.getButtonLores(jobPath, 3, player);
                    inv.setItem(i, btn.createButton(material, name, lore, glow));
                    break;
                case 20:
                    name = GUIUtils.getButtonTitle(jobPath, 4);
                    material = GUIUtils.getButtonMaterial(jobPath, 4);
                    glow = GUIUtils.getButtonIsGlowing(jobPath, 4);
                    lore = GUIUtils.getButtonLores(jobPath, 4, player);
                    inv.setItem(i, btn.createButton(material, name, lore, glow));
                    break;
                case 22:
                    name = GUIUtils.getButtonTitle(jobPath, 5);
                    material = GUIUtils.getButtonMaterial(jobPath, 5);
                    glow = GUIUtils.getButtonIsGlowing(jobPath, 5);
                    lore = GUIUtils.getButtonLores(jobPath, 5, player);
                    inv.setItem(i, btn.createButton(material, name, lore, glow));
                    break;
                case 24:
                    name = GUIUtils.getButtonTitle(jobPath, 6);
                    material = GUIUtils.getButtonMaterial(jobPath, 6);
                    glow = GUIUtils.getButtonIsGlowing(jobPath, 6);
                    lore = GUIUtils.getButtonLores(jobPath, 6, player);
                    inv.setItem(i, btn.createButton(material, name, lore, glow));
                    break;
                default:
                    inv.setItem(i, btn.createButton(Material.BLACK_STAINED_GLASS_PANE, " ", null, false));
            }
        }
        return inv;
    }
}
