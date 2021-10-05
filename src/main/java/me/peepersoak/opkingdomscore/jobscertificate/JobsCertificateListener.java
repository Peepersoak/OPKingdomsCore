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
    public void onInteract(PlayerInteractEvent e) {
        if (!e.hasItem()) return;
        ItemStack item = e.getItem();
        Player player = e.getPlayer();
        if (!player.isOp()) return;
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

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        PersistentDataContainer itemData = meta.getPersistentDataContainer();
        String jobTitle = itemData.get(JobsString.JOB_TITLE, PersistentDataType.STRING);
        assert jobTitle != null;

        setCertificate(player, jobTitle);
        player.closeInventory();
    }

    public void setCertificate(Player player, String title) {
        JobsData jobsData = new JobsData();
        PersistentDataContainer data = player.getPersistentDataContainer();
        switch (title) {
            case JobsString.MINER_PATH:
                data.set(JobsString.MINER, PersistentDataType.STRING, title);
                break;
            case JobsString.LOGGER_PATH:
                data.set(JobsString.LOGGER, PersistentDataType.STRING, title);
                break;
            case JobsString.BREWER_PATH:
                data.set(JobsString.BREWER, PersistentDataType.STRING, title);
                break;
            case JobsString.ENCHANTER_PATH:
                data.set(JobsString.ENCHANTER, PersistentDataType.STRING, title);
                break;
            case JobsString.WARRIOR_PATH:
                data.set(JobsString.WARRIOR, PersistentDataType.STRING, title);
                break;
            case JobsString.ARCHER_PATH:
                data.set(JobsString.ARCHER, PersistentDataType.STRING, title);
                break;
            case JobsString.SMITH_PATH:
                data.set(JobsString.SMITH, PersistentDataType.STRING, title);
                break;
        }

        data.set(JobsString.JOB_TITLE, PersistentDataType.STRING, title);
        data.set(JobsString.JOB_LEVEL, PersistentDataType.INTEGER, 0);
        data.set(JobsString.JOB_XP, PersistentDataType.INTEGER, 0);
        data.set(JobsString.JOB_XP_TARGET, PersistentDataType.INTEGER, jobsData.getConfig().getInt(title + "." + JobsString.JOB_XP_TARGET));

        String joinRaw = jobsData.getConfig().getString(title + "." + JobsString.JOBS_JOIN_MESSAGE);
        assert joinRaw != null;
        String message = ChatColor.translateAlternateColorCodes('&', joinRaw);
        player.sendMessage(message);
    }
}
