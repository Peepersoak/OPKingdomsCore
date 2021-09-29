package me.peepersoak.opkingdomscore.jobscertificate;

import me.peepersoak.opkingdomscore.jobscertificate.data.JobsData;
import me.peepersoak.opkingdomscore.jobscertificate.jobsGUI.JobsGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class JobsCertificateListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (e.getPlayer().hasPlayedBefore()) return;
        Player player = e.getPlayer();
        JobsGUI gui = new JobsGUI();
        // Open the GUI settings for the races
        player.openInventory(gui.openMainGUI());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.hasItem()) return;
        ItemStack item = e.getItem();
        Player player = e.getPlayer();
        if (item.getType() == Material.DIAMOND_BLOCK) {
            JobsGUI gui = new JobsGUI();
            player.openInventory(gui.openMainGUI());
        }
        if (item.getType() == Material.EMERALD_BLOCK) {
            PersistentDataContainer data = player.getPersistentDataContainer();
            player.sendMessage("" + data.get(JobsString.JOB_TITLE, PersistentDataType.STRING));
            player.sendMessage("" + data.get(JobsString.JOB_LEVEL, PersistentDataType.INTEGER));
            player.sendMessage("" + data.get(JobsString.JOB_XP, PersistentDataType.INTEGER));
            player.sendMessage("" + data.get(JobsString.JOB_XP_TARGET, PersistentDataType.INTEGER));
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        String invName = ChatColor.stripColor(e.getView().getTitle());
        if (!invName.equalsIgnoreCase(JobsString.JOBS_GUI_NAME)) return;
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();
        if (item.getType() == Material.BLACK_STAINED_GLASS_PANE) return;

        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();

        JobsData jobsData = new JobsData();

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        String jobTitle = itemData.get(JobsString.JOB_TITLE, PersistentDataType.STRING);

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        playerData.set(JobsString.JOB_TITLE, PersistentDataType.STRING, jobTitle);
        playerData.set(JobsString.JOB_LEVEL, PersistentDataType.INTEGER, 0);
        playerData.set(JobsString.JOB_XP, PersistentDataType.INTEGER, 0);
        playerData.set(JobsString.JOB_XP_TARGET, PersistentDataType.INTEGER, jobsData.getConfig().getInt(jobTitle + "." + JobsString.JOB_XP_TARGET));

        player.closeInventory();

        String joinRaw = jobsData.getConfig().getString(jobTitle + "." + JobsString.JOBS_JOIN_MESSAGE);
        String message = ChatColor.translateAlternateColorCodes('&', joinRaw);

        player.sendMessage(message);
    }
}
