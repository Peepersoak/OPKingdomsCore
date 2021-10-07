package me.peepersoak.opkingdomscore.jobscertificate.jobs.logger;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.LoggerData;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class LoggerListener implements Listener {

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
                player.sendMessage(ChatColor.RED + "You don't have the proper certificate to do this!!");
                e.setCancelled(true);
                return;
            }
        }
        JobsUtil.checkBlockBreakJob(material, player, JobsString.LOGGER_PATH, e, jobBlock, jobDefaultBlock);
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
}
