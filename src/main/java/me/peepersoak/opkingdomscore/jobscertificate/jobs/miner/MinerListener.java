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

        ConfigurationSection jobBlock = minerData.getConfig().getConfigurationSection(JobsString.MINER_JOBS_BLOCKS);
        ConfigurationSection jobDefaultBlock = minerData.getConfig().getConfigurationSection("Break");

        assert jobBlock != null;

        JobsUtil.checkBlockBreakJob(material, player, JobsString.MINER_PATH, e, jobBlock, jobDefaultBlock);
    }
}
