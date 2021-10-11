package me.peepersoak.opkingdomscore.jobscertificate.GUI.change;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobMessage;
import me.peepersoak.opkingdomscore.utilities.GUIButton;
import me.peepersoak.opkingdomscore.utilities.GUICreator;
import me.peepersoak.opkingdomscore.utilities.GUIUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ChangeGUI {
    public Inventory openGUI(Player player) {

        GUICreator gui = new GUICreator(GUIUtils.getGUITItle(JobsString.JOB_CHANGE_GUI + "." + JobsString.TITLE), 27);
        GUIButton button = new GUIButton();
        Inventory inv = gui.getInv();

        String buttonPath = JobsString.JOB_CHANGE_GUI + "." + JobsString.BUTTON;

        String name;
        Material mat;
        boolean glow;
        List<String> lore;
        for (int i = 0 ; i < 27; i++) {
            if (i == 13) {
                name = GUIUtils.getButtonTitle(buttonPath, 0);
                mat = GUIUtils.getButtonMaterial(buttonPath, 0);
                glow = GUIUtils.getButtonIsGlowing(buttonPath, 0);
                lore = GUIUtils.getButtonLores(buttonPath, 0, player);
                inv.setItem(i, button.createButton(mat, name, lore, glow));
            } else {
                inv.setItem(i, button.createButton(Material.BLACK_STAINED_GLASS_PANE, " ", null, false));
            }
        }
        return inv;
    }
}
