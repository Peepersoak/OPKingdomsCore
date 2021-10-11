package me.peepersoak.opkingdomscore.jobscertificate.jobs.logger;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.LoggerData;
import me.peepersoak.opkingdomscore.jobscertificate.data.MinerData;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class LoggerListener implements Listener {

    private final HashMap<Location, Long> blockLocation = new HashMap<>();

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!e.isDropItems()) return;
        LoggerData data = new LoggerData();
        Block block = e.getBlock();
        String material = block.getType().toString().toLowerCase();
        Player player = e.getPlayer();
        ConfigurationSection jobBlock = data.getConfig().getConfigurationSection(JobsString.LOGGER_JOBS_BLOCKS);
        ConfigurationSection jobDefaultBlock = data.getConfig().getConfigurationSection("Break");
        assert jobBlock != null;

        if (!JobsUtil.isJobCorrect(player, JobsString.LOGGER_PATH)) {
            if (block.getType() == Material.OAK_LOG) return;
            assert jobDefaultBlock != null;
            if (jobDefaultBlock.getString(material) != null) {
                JobsUtil.sendWrongCertificate(player, JobsString.LOGGER_PATH);
                e.setCancelled(true);
                return;
            }
        }

        boolean isOnCD = isOnCooldown(block.getLocation());
        JobsUtil.checkBlockBreakJob(material, player, JobsString.LOGGER_PATH, e, jobBlock, jobDefaultBlock, isOnCD);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getDamager();

        if (!JobsUtil.isJobCorrect(player, JobsString.LOGGER_PATH)) return;
        if (!JobsUtil.isWieldingAxe(player)) return;

        int level = JobsUtil.getPlayerJobLevel(player);
        if (level < 3) return;
        LoggerData data = new LoggerData();
        double bonus = data.getConfig().getDouble(JobsString.LOGGER_JOBS_DAMAGE_BONUS);
        double newDamage = e.getDamage() + bonus;
        if (JobsUtil.announce()) {
            player.sendMessage(ChatColor.GOLD + "Added " + bonus + " damage to your attack");
        }
        if (level >= 5) {
            newDamage = newDamage * 2;
            if (JobsUtil.announce()) {
                player.sendMessage(ChatColor.GOLD + "Damage has been double as a level " + level + " Logger" +
                        " from " + (e.getDamage() + bonus) + " to " + newDamage);
            }
        }
        e.setDamage(newDamage);

    }

    @EventHandler
    public void onCraft(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.WORKBENCH) return;
        if (e.getCurrentItem() == null) return;
        ItemStack item = e.getCurrentItem();
        Player player = (Player) e.getWhoClicked();
        String mat = item.getType().toString().toLowerCase();
        LoggerData data = new LoggerData();
        ConfigurationSection section = data.getConfig().getConfigurationSection("Craft");
        assert section != null;
        if (!JobsUtil.isBlock(mat, section)) return;
        if (!JobsUtil.isJobCorrect(player, JobsString.LOGGER_PATH)) {
            JobsUtil.sendWrongCertificate(player, JobsString.LOGGER_PATH);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        LoggerData loggerData = new LoggerData();
        String material = block.getType().toString().toLowerCase();
        Player player = e.getPlayer();
        ConfigurationSection jobBlock = loggerData.getConfig().getConfigurationSection(JobsString.LOGGER_JOBS_BLOCKS);
        ConfigurationSection jobDefaultBlock = loggerData.getConfig().getConfigurationSection("Break");
        assert jobBlock != null;
        if (JobsUtil.isBlockSpecific(jobBlock, jobDefaultBlock, material, player, JobsString.MINER_PATH)) {
            blockLocation.put(block.getLocation(), System.currentTimeMillis() + (1000 * 300));
        }
    }

    public boolean isOnCooldown(Location location) {
        long time = System.currentTimeMillis();
        boolean isCD = false;
        if (blockLocation.containsKey(location)) {
            if (blockLocation.get(location) > time)  {
                isCD = true;
            }
            blockLocation.remove(location);
        }
        return isCD;
    }
}
