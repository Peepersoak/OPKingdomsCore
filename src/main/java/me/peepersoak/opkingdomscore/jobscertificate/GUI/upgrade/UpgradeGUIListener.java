package me.peepersoak.opkingdomscore.jobscertificate.GUI.upgrade;

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

import java.util.List;

public class UpgradeGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        String invName = e.getView().getTitle();
        if (!invName.equalsIgnoreCase(GUIUtils.getGUITItle(JobsString.JOB_UPGRADE_GUI + "." + JobsString.TITLE))) return;
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();

        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();

        Material mat = item.getType();
        String jobPath = JobsString.JOB_UPGRADE_GUI + "." + JobsString.BUTTON;

        JobMessage message = new JobMessage();

        if (mat == GUIUtils.getButtonMaterial(jobPath, 0)) {
            String title = JobsUtil.getPlayerJobTitle(player);
            double playerXP = JobsUtil.getPlayerJobXP(player);
            int level = JobsUtil.getPlayerJobLevel(player);
            int playerToken = JobsUtil.getPlayerToken(player);
            int xpTarget = JobsUtil.getPlayerJobXPTarget(player);
            int tokenNeeded = JobsUtil.getTokenNeeded(player);

            if (playerXP < xpTarget) {
                String raw = message.getConfig().getString("Job_Upgrade.Not_Enough_XP");
                assert raw != null;
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', raw));
                return;
            }

            if (playerToken < tokenNeeded) {
                String raw = message.getConfig().getString("Job_Upgrade.Not_Enough_Token");
                assert raw != null;
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', raw));
                return;
            }
            JobsUtil.upgradeLevel(player, level);
            int newLevel = level + 1;
            List<String> rawMsg = message.getConfig().getStringList("Job_Upgrade.Success");
            assert title != null;
            for (String msg : rawMsg) {
                String finalMsg = ChatColor.translateAlternateColorCodes('&', msg)
                        .replace("%job_level%", "" + newLevel)
                        .replace("%job_title%", title);
                player.sendMessage(finalMsg);
            }
            player.closeInventory();
        }
    }
}
