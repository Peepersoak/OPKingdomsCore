package me.peepersoak.opkingdomscore.jobscertificate.GUI.change;

import me.peepersoak.opkingdomscore.jobscertificate.GUI.jobsGUI.JobsGUI;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobMessage;
import me.peepersoak.opkingdomscore.utilities.GUIUtils;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ChangeGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        String invName = e.getView().getTitle();
        if (!invName.equalsIgnoreCase(GUIUtils.getGUITItle(JobsString.JOB_CHANGE_GUI + "." + JobsString.TITLE))) return;
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();
        if (item.getType() == Material.ORANGE_STAINED_GLASS_PANE) return;

        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();

        Material mat = item.getType();

        JobMessage message = new JobMessage();
        String jobPath = JobsString.JOB_CHANGE_GUI + "." + JobsString.BUTTON;
        if (mat == GUIUtils.getButtonMaterial(jobPath, 0)) {
            if (JobsUtil.getPlayerToken(player) >= JobsUtil.getTokenNeeded(player)) {
                JobsUtil.setPlayerToken(player, JobsUtil.getPlayerToken(player) - JobsUtil.getTokenNeeded(player));
                JobsGUI gui = new JobsGUI();
                player.openInventory(gui.openJobChangeGUI(player));
                String raw = Objects.requireNonNull(message.getConfig().getString(JobsString.JOB_CHANGE_SUCESS)).replace("%token%", "" + JobsUtil.getTokenNeeded(player));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', raw));
            } else {
                player.closeInventory();
                String raw = message.getConfig().getString(JobsString.JOB_CHANGE_FAILED);
                assert raw != null;
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', raw));
            }
        }
    }
}
