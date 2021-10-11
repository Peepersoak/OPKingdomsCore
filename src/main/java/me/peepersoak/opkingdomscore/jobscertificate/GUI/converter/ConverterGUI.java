package me.peepersoak.opkingdomscore.jobscertificate.GUI.converter;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.utilities.GUIButton;
import me.peepersoak.opkingdomscore.utilities.GUICreator;
import me.peepersoak.opkingdomscore.utilities.GUIUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ConverterGUI {

    public Inventory openGUI(Player player) {
        GUICreator creator = new GUICreator(GUIUtils.getGUITItle(JobsString.JOB_TOKEN_CONVERT_GUI + "." + JobsString.TITLE), 27);
        GUIButton button = new GUIButton();

        Inventory inv = creator.getInv();

        String jobButton = JobsString.JOB_TOKEN_CONVERT_GUI + "." + JobsString.BUTTON;

        String name;
        Material material;
        boolean glow;
        List<String> lore;

        for (int i = 0; i < 27; i++) {
            switch (i) {
                case 11:
                    name = GUIUtils.getButtonTitle(jobButton, 0);
                    material = GUIUtils.getButtonMaterial(jobButton, 0);
                    glow = GUIUtils.getButtonIsGlowing(jobButton, 0);
                    lore = GUIUtils.getButtonLores(jobButton, 0, player);
                    inv.setItem(i, button.createButton(material, name, lore, glow));
                    break;
                case 12:
                    name = GUIUtils.getButtonTitle(jobButton, 1);
                    material = GUIUtils.getButtonMaterial(jobButton, 1);
                    glow = GUIUtils.getButtonIsGlowing(jobButton, 1);
                    lore = GUIUtils.getButtonLores(jobButton, 1, player);
                    inv.setItem(i, button.createButton(material, name, lore, glow));
                    break;
                case 13:
                    name = GUIUtils.getButtonTitle(jobButton, 2);
                    material = GUIUtils.getButtonMaterial(jobButton, 2);
                    glow = GUIUtils.getButtonIsGlowing(jobButton, 2);
                    lore = GUIUtils.getButtonLores(jobButton, 2, player);
                    inv.setItem(i, button.createButton(material, name, lore, glow));
                    break;
                case 14:
                    name = GUIUtils.getButtonTitle(jobButton, 3);
                    material = GUIUtils.getButtonMaterial(jobButton, 3);
                    glow = GUIUtils.getButtonIsGlowing(jobButton, 3);
                    lore = GUIUtils.getButtonLores(jobButton, 3, player);
                    inv.setItem(i, button.createButton(material, name, lore, glow));
                    break;
                case 15:
                    name = GUIUtils.getButtonTitle(jobButton, 4);
                    material = GUIUtils.getButtonMaterial(jobButton, 4);
                    glow = GUIUtils.getButtonIsGlowing(jobButton, 4);
                    lore = GUIUtils.getButtonLores(jobButton, 4, player);
                    inv.setItem(i, button.createButton(material, name, lore, glow));
                    break;
                default:
                    inv.setItem(i, button.createButton(Material.ORANGE_STAINED_GLASS_PANE, " ", null, false));
                    break;
            }
        }
        return inv;
    }
}
