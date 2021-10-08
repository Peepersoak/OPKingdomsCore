package me.peepersoak.opkingdomscore.jobscertificate.jobs;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.SmithingData;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class SmithingListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock() == null) return;
        Block block = e.getClickedBlock();
        Material mat = block.getType();
        Player player = e.getPlayer();
        if (mat != Material.ANVIL && mat != Material.SMITHING_TABLE) return;
        if (!JobsUtil.isJobCorrect(player, JobsString.SMITH_PATH)) {
            JobsUtil.sendWrongCertificate(player, JobsString.SMITH_PATH);
            e.setCancelled(true);
            return;
        }
        int level = JobsUtil.getPlayerJobLevel(player);
        if (mat == Material.ANVIL) {
            if (level < 2) {
                JobsUtil.sendNotEnoughLevelMessage(player, JobsString.SMITH_PATH);
                e.setCancelled(true);
                return;
            }
        }
        if (mat == Material.SMITHING_TABLE) {
            if (level < 3) {
                JobsUtil.sendNotEnoughLevelMessage(player, JobsString.SMITH_PATH);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.WORKBENCH &&
                e.getClickedInventory().getType() != InventoryType.SMITHING) return;
        if (e.getCurrentItem() == null) return;
        if (!(e.getWhoClicked() instanceof Player)) return;

        int slot = e.getSlot();

        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        String mat = item.getType().toString().toLowerCase();

        SmithingData data = new SmithingData();
        ConfigurationSection section = data.getConfig().getConfigurationSection(JobsString.SMITHING_SPECIFIC_ITEM);
        ConfigurationSection craft = data.getConfig().getConfigurationSection(JobsString.SMITHING_CRAFT_XP);
        ConfigurationSection upgrade = data.getConfig().getConfigurationSection(JobsString.SMITHING_UPGRADE_XP);

        assert craft != null;
        assert section != null;
        assert upgrade != null;

        if (onCraft(section, player, e, mat, slot)) return;

        if (slot != 0) return;
        if (JobsUtil.isBlock(mat, craft)) {
            if (JobsUtil.isJobCorrect(player, JobsString.SMITH_PATH)) {
                JobsUtil.addXPandIncome(player, craft, mat);
            }
        }
    }

    public boolean onCraft(ConfigurationSection section, Player player, InventoryClickEvent e, String mat, int slot) {
        if (slot != 0) return false;
        for (String level : section.getKeys(false)) {
            if (!JobsUtil.isBlock(mat, Objects.requireNonNull(section.getConfigurationSection(level)))) continue;
            if (!JobsUtil.isJobCorrect(player, JobsString.SMITH_PATH)) {
                JobsUtil.sendWrongCertificate(player, JobsString.SMITH_PATH);
                e.setCancelled(true);
                return false;
            }
            if (JobsUtil.getPlayerJobLevel(player) < JobsUtil.getLevelFromString(level)) {
                JobsUtil.sendNotEnoughLevelMessage(player, JobsString.SMITH_PATH);
                e.setCancelled(true);
                return false;
            }
            JobsUtil.addXPandIncome(player, Objects.requireNonNull(section.getConfigurationSection(level)), mat);
            return true;
        }
        return false;
    }
}
