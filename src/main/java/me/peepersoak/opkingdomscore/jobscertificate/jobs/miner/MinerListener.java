package me.peepersoak.opkingdomscore.jobscertificate.jobs.miner;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import me.peepersoak.opkingdomscore.jobscertificate.data.MinerData;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
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

import java.util.Objects;

public class MinerListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!e.isDropItems()) return;

        MinerData minerData = new MinerData();

        Block block = e.getBlock();

        String material = block.getType().toString().toLowerCase();

        Player player = e.getPlayer();

        checkMinerJobs(material, minerData.getConfig(), player, e);
    }

    public void checkMinerJobs(String material, FileConfiguration config, Player player, BlockBreakEvent event) {
        ConfigurationSection miner = config.getConfigurationSection(JobsString.MINER_JOBS_BLOCKS);
        ConfigurationSection defaultBlock = config.getConfigurationSection("Break");

        assert miner != null;
        assert defaultBlock != null;

        double xp = 0;
        double income = 0;

        boolean addIncomeAndXP = false;

        for (String level : miner.getKeys(false)) {
            if (!JobsUtil.isBlock(material, Objects.requireNonNull(miner.getConfigurationSection(level)))) continue;
            if (!isMiner(player)) {
                player.sendMessage(ChatColor.RED + "You don't have the proper certificate to mine this!!");
                event.setCancelled(true);
                return;
            }
            if (JobsUtil.getPlayerJobLevel(player) < JobsUtil.getLevelNeeded(level)) {
                player.sendMessage(ChatColor.RED + "You are not powerful enough to mine this block!!");
                player.sendMessage(ChatColor.RED + "Level requirement: Miner Level " + JobsUtil.getLevelNeeded(level));
                event.setCancelled(true);
                return;
            }
            addIncomeAndXP = true;
            xp = JobsUtil.getEarnXP(Objects.requireNonNull(miner.getConfigurationSection(level)), material);
            income = JobsUtil.getEarnIncome(Objects.requireNonNull(miner.getConfigurationSection(level)), material);
        }

        if (JobsUtil.isBlock(material, defaultBlock)) {
            addIncomeAndXP = true;
            xp = JobsUtil.getEarnXP(defaultBlock, material);
            income = JobsUtil.getEarnIncome(defaultBlock, material);
        }

        if (addIncomeAndXP) {
            double playerNewXP = JobsUtil.getPlayerJobXP(player) + xp;

            JobsUtil.addXPToPlayer(player, playerNewXP);

            Economy economy = OPKingdomsCore.getEconomy();
            EconomyResponse response = economy.depositPlayer(player, income);
            if (response.transactionSuccess()) {
                player.sendMessage(ChatColor.GREEN + "" + income + " has beed added to your account");
            } else {
                player.sendMessage(ChatColor.RED + "Failed to add balance");
            }
        }
    }

    public boolean isMiner(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        return Objects.requireNonNull(data.get(JobsString.JOB_TITLE, PersistentDataType.STRING)).equalsIgnoreCase(JobsString.MINER_PATH);
    }
}
