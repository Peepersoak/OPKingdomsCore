package me.peepersoak.opkingdomscore.jobscertificate.jobs.miner;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import me.peepersoak.opkingdomscore.jobscertificate.data.MinerData;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;

public class MinerListener implements Listener {

    private final HashMap<Location, Long> blockLocation = new HashMap<>();

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

        boolean isOnCD = isOnCooldown(block.getLocation());
        JobsUtil.checkBlockBreakJob(material, player, JobsString.MINER_PATH, e, jobBlock, jobDefaultBlock, isOnCD);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        MinerData minerData = new MinerData();
        String material = block.getType().toString().toLowerCase();
        Player player = e.getPlayer();
        ConfigurationSection jobBlock = minerData.getConfig().getConfigurationSection(JobsString.MINER_JOBS_BLOCKS);
        ConfigurationSection jobDefaultBlock = minerData.getConfig().getConfigurationSection("Break");
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
