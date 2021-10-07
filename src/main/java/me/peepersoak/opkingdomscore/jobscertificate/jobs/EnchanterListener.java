package me.peepersoak.opkingdomscore.jobscertificate.jobs;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.EnchanterData;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class EnchanterListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        String name = ChatColor.stripColor(e.getView().getTitle());
        if (!name.equalsIgnoreCase("Server Enchanter")) return;

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;
        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();
        Material mat = item.getType();

        if (!JobsUtil.isJobCorrect(player, JobsString.ENCHANTER_PATH)) {
            player.sendMessage(ChatColor.RED + "You don't have the right certificate to do this!");
            e.setCancelled(true);
            return;
        }

        int level = JobsUtil.getPlayerJobLevel(player);

        switch (mat) {
            case LIME_STAINED_GLASS_PANE:
                if (level < 1) {
                    player.sendMessage(ChatColor.RED + "Your level is not high enough to do this!");
                    player.sendMessage(ChatColor.RED + "" + 1);
                    e.setCancelled(true);
                    return;
                }
            case LIGHT_BLUE_STAINED_GLASS_PANE:
                if (level < 2) {
                    player.sendMessage(ChatColor.RED + "Your level is not high enough to do this!");
                    player.sendMessage(ChatColor.RED + "" + 2);
                    e.setCancelled(true);
                    return;
                }
            case YELLOW_STAINED_GLASS_PANE:
                if (level < 3) {
                    player.sendMessage(ChatColor.RED + "Your level is not high enough to do this!");
                    player.sendMessage(ChatColor.RED + "" + 3);
                    e.setCancelled(true);
                    return;
                }
            case ORANGE_STAINED_GLASS_PANE:
                if (level < 4) {
                    player.sendMessage(ChatColor.RED + "Your level is not high enough to do this!");
                    player.sendMessage(ChatColor.RED + "" + 4);
                    e.setCancelled(true);
                    return;
                }
            case PINK_STAINED_GLASS_PANE:
                if (level < 5) {
                    player.sendMessage(ChatColor.RED + "Your level is not high enough to do this!");
                    player.sendMessage(ChatColor.RED + "" + 5);
                    e.setCancelled(true);
                    break;
                }
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent e) {
        Player player = e.getEnchanter();
        ItemStack item = e.getItem();
        if (!JobsUtil.isJobCorrect(player, JobsString.ENCHANTER_PATH)) {
            if (e.whichButton() == 2) {
                player.sendMessage(ChatColor.RED + "You don't have the right certificate to do this!!");
                restoreOriginalItem(item, e.getInventory());
                e.setCancelled(true);
            }
            return;
        }
        EnchanterData data = new EnchanterData();
        String mat = item.getType().toString().toLowerCase();
        ConfigurationSection section = data.getConfig().getConfigurationSection("Enchant");
        assert section != null;
        if (!JobsUtil.isBlock(mat, section)) return;
        JobsUtil.addXPandIncome(player, section, mat);
    }

    public void restoreOriginalItem(ItemStack item, Inventory inventory) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack newItem = new ItemStack(item.getType());
                inventory.setItem(0, newItem);
            }
        }.runTaskLater(OPKingdomsCore.getInstance(), 5);
    }

    @EventHandler
    public void onTrade(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.MERCHANT) return;
        if (e.getCurrentItem() == null) return;
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        if (slot == 2) {
            if (!JobsUtil.isJobCorrect(player, JobsString.ENCHANTER_PATH)) {
                player.sendMessage(ChatColor.RED + "You don't have the right certificate to trade!");
                e.setCancelled(true);
            } else {
                int level = JobsUtil.getPlayerJobLevel(player);
                if (level < 1) {
                    player.sendMessage(ChatColor.RED + "Your level is not high enough to trade!");
                    player.sendMessage(ChatColor.RED + "Level Requirement: " + 1);
                    e.setCancelled(true);
                }
            }
        }
    }
}
