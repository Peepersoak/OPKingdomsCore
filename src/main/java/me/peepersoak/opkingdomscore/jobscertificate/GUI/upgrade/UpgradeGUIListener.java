package me.peepersoak.opkingdomscore.jobscertificate.GUI.upgrade;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class UpgradeGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        String invName = ChatColor.stripColor(e.getView().getTitle());
        if (!invName.equalsIgnoreCase(JobsString.UPGRADE_GUI_NAME)) return;
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();
        if (item.getType() == Material.BLACK_STAINED_GLASS_PANE) return;

        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();

        Material mat = item.getType();

        if (mat == Material.LIME_STAINED_GLASS_PANE) {
            String title = JobsUtil.getPlayerJobTitle(player);
            double playerXP = JobsUtil.getPlayerJobXP(player);
            int level = JobsUtil.getPlayerJobLevel(player);
            int playerToken = JobsUtil.getPlayerToken(player);
            int xpTarget = JobsUtil.getXPNeeded(title, level);
            int tokenNeeded = JobsUtil.getTokenNeeded(player);

            if (playerXP < xpTarget) {
                player.sendMessage(ChatColor.RED + "You did not meet the xp requirement to upgrade your job!");
                player.sendMessage(ChatColor.RED + "Current XP: " + ChatColor.AQUA + playerXP);
                player.sendMessage(ChatColor.RED + "XP Needed: " + ChatColor.AQUA + xpTarget);
                return;
            }

            if (playerToken < tokenNeeded) {
                player.sendMessage(ChatColor.RED + "You don't have enough token to upgrade your job!");
                return;
            }
            JobsUtil.changeLevel(player, level, JobsUtil.getXPNeeded(title, level));
            String rawMsg = JobsUtil.getLevelUPRawMessage(title);
            assert rawMsg != null;
            assert title != null;
            String msg = ChatColor.translateAlternateColorCodes('&', rawMsg)
                    .replace("%job_level%", "" + level + 1)
                    .replace("%job_title%", title);
            sendLevelUPMessage(player, msg);
        }
    }

    public void sendLevelUPMessage(Player player, String msg) {
        String separator = ChatColor.AQUA + "=======================";
        player.sendMessage(separator);
        player.sendMessage("");
        player.sendMessage(msg);
        player.sendMessage("");
        player.sendMessage(separator);
    }
}
