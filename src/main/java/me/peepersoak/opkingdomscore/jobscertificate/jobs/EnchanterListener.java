package me.peepersoak.opkingdomscore.jobscertificate.jobs;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.EnchanterData;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EnchanterListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Block block = e.getClickedBlock();
        if (block.getType() != Material.ENCHANTING_TABLE) return;
        Player player = e.getPlayer();
        if (!JobsUtil.isJobCorrect(player, JobsString.ENCHANTER_PATH)) {
            JobsUtil.sendWrongCertificate(player, JobsString.ENCHANTER_PATH);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTrade(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Villager)) return;
        Villager villager = (Villager) e.getRightClicked();
        if (villager.getProfession() == Villager.Profession.NONE) return;
        if (villager.getProfession() == Villager.Profession.NITWIT) return;
        Player player = e.getPlayer();
        if (!JobsUtil.isJobCorrect(player, JobsString.ENCHANTER_PATH)) {
            JobsUtil.sendWrongCertificate(player, JobsString.ENCHANTER_PATH);
            e.setCancelled(true);
        }
    }

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
            JobsUtil.sendWrongCertificate(player, JobsString.ENCHANTER_PATH);
            e.setCancelled(true);
            return;
        }

        int level = JobsUtil.getPlayerJobLevel(player);

        switch (mat) {
            case LIME_STAINED_GLASS_PANE:
                if (level < 1) {
                    JobsUtil.sendNotEnoughLevelMessage(player, JobsString.ENCHANTER_PATH);
                    e.setCancelled(true);
                }
                break;
            case LIGHT_BLUE_STAINED_GLASS_PANE:
                if (level < 2) {
                    JobsUtil.sendNotEnoughLevelMessage(player, JobsString.ENCHANTER_PATH);
                    e.setCancelled(true);
                }
                break;
            case YELLOW_STAINED_GLASS_PANE:
                if (level < 3) {
                    JobsUtil.sendNotEnoughLevelMessage(player, JobsString.ENCHANTER_PATH);
                    e.setCancelled(true);
                }
                break;
            case ORANGE_STAINED_GLASS_PANE:
                if (level < 4) {
                    JobsUtil.sendNotEnoughLevelMessage(player, JobsString.ENCHANTER_PATH);
                    e.setCancelled(true);
                }
                break;
            case PINK_STAINED_GLASS_PANE:
                if (level < 5) {
                    JobsUtil.sendNotEnoughLevelMessage(player, JobsString.ENCHANTER_PATH);
                    e.setCancelled(true);
                }
                break;
        }
    }

    @EventHandler
    public void onEnchant(EnchantItemEvent e) {
        Player player = e.getEnchanter();
        ItemStack item = e.getItem();
        if (!JobsUtil.isJobCorrect(player, JobsString.ENCHANTER_PATH)) return;
        EnchanterData data = new EnchanterData();
        String mat = item.getType().toString().toLowerCase();
        ConfigurationSection section = data.getConfig().getConfigurationSection("Enchant");
        assert section != null;
        if (!JobsUtil.isBlock(mat, section)) return;
        JobsUtil.addXPandIncome(player, section, mat);
    }
}
