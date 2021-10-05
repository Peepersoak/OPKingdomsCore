package me.peepersoak.opkingdomscore.jobscertificate.jobs.miner;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.JobsUtil;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobsData;
import me.peepersoak.opkingdomscore.jobscertificate.data.LoggerData;
import me.peepersoak.opkingdomscore.jobscertificate.data.MinerData;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MinerListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!e.isDropItems()) return;

        LoggerData loggerData = new LoggerData();
        MinerData minerData = new MinerData();

        Block block = e.getBlock();

        String material = block.getType().toString().toLowerCase();

        Player player = e.getPlayer();

        checkMinerJobs(material, minerData.getConfig(), player, e);
    }

    public void checkMinerJobs(String material, FileConfiguration config, Player player, BlockBreakEvent event) {
        ConfigurationSection miner = config.getConfigurationSection(JobsString.MINER_JOBS_BLOCKS);
        assert miner != null;
        for (String level : miner.getKeys(false)) {
            if (miner.getString(level + "." + material) == null) continue;
            PersistentDataContainer data = player.getPersistentDataContainer();
            if (!data.has(JobsString.MINER, PersistentDataType.STRING)) {
                player.sendMessage(ChatColor.RED + "You don't have the proper certificate to mine this!!");
                event.setCancelled(true);
                return;
            }

            int levelNeeded = data.get(JobsString.JOB_LEVEL, PersistentDataType.INTEGER);

            if (levelNeeded < JobsUtil.getLevelNeeded(level)) {
                player.sendMessage(ChatColor.RED + "You are not powerful enough to mine this block!!");
                player.sendMessage(ChatColor.RED + "Level requirement: " + levelNeeded);
                event.setCancelled(true);
                return;
            }

            int xp = miner.getInt(level + "." + material + JobsString.XP);
            int income = miner.getInt(level + "." + material + JobsString.income);

            int playerNewXp = data.get(JobsString.JOB_XP, PersistentDataType.INTEGER) + xp;
            int playerXpToTarget = data.get(JobsString.JOB_XP_TARGET, PersistentDataType.INTEGER);

            if (playerNewXp >= playerXpToTarget) {
                JobsData jobsData = new JobsData();
                int newTarget = jobsData.getConfig().getInt(JobsString.MINER_PATH + "." + JobsString.XP_REQUIREMENT);;
                int amplifier = jobsData.getConfig().getInt(JobsString.MINER_PATH + "." + JobsString.XP_MULTIPLIER);
                for (int i = 1; i <= levelNeeded + 1; i++) {
                    newTarget = newTarget * amplifier;
                }
                data.set(JobsString.JOB_LEVEL, PersistentDataType.INTEGER, levelNeeded + 1);
                data.set(JobsString.JOB_XP, PersistentDataType.INTEGER, 0);
                data.set(JobsString.JOB_XP_TARGET, PersistentDataType.INTEGER, newTarget);
            } else {
                data.set(JobsString.JOB_XP, PersistentDataType.INTEGER, playerNewXp);
            }
            Economy economy = OPKingdomsCore.getEconomy();
            economy.depositPlayer(player, income);
        }
    }
}
