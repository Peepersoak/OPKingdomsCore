package me.peepersoak.opkingdomscore.jobscertificate.GUI.jobsGUI;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobMessage;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobsData;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.jobsGUI.JobsGUI;
import me.peepersoak.opkingdomscore.utilities.GUIUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class JobsGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        String invName = e.getView().getTitle();
        if (!invName.equalsIgnoreCase(GUIUtils.getGUITItle(JobsString.JOB_APPLY_GUI + "." + JobsString.TITLE)) &&
        !invName.equalsIgnoreCase(GUIUtils.getGUITItle(JobsString.JOB_APPLY_GUI + "." + JobsString.CHANGE))) return;
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();
        Material mat = item.getType();

        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();

        String jobPath = JobsString.JOB_APPLY_GUI + "." + JobsString.BUTTON;

        for (int i = 0; i < GUIUtils.getButtonSection(jobPath).getKeys(false).size(); i++) {
            if (GUIUtils.getButtonMaterial(jobPath, i) == mat) {
                String key = GUIUtils.getButtonPath(GUIUtils.getButtonSection(jobPath), i);
                setCertificate(player, key);
                break;
            }
        }
        player.closeInventory();
    }

    public void setCertificate(Player player, String title) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(JobsString.JOB_TITLE, PersistentDataType.STRING, title);
        data.set(JobsString.JOB_LEVEL, PersistentDataType.INTEGER, 0);
        data.set(JobsString.JOB_XP, PersistentDataType.DOUBLE, 0.0);
        if (!data.has(JobsString.JOB_TOKEN, PersistentDataType.INTEGER)) {
            data.set(JobsString.JOB_TOKEN, PersistentDataType.INTEGER, 0);
        }
        JobMessage message = new JobMessage();
        String joinRaw = Objects.requireNonNull(message.getConfig().getString("Job_Apply_Join_Message")).replace("%job%", "" + title);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', joinRaw));
    }
}
