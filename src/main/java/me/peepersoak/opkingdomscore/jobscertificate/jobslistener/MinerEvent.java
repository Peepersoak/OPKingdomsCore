package me.peepersoak.opkingdomscore.jobscertificate.jobslistener;

import me.peepersoak.opkingdomscore.jobscertificate.data.MinerData;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MinerEvent implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!e.isDropItems()) return;
        MinerData data = new MinerData();

        Block block = e.getBlock();
        String material = block.getType().toString().toLowerCase();

        if (data.getConfig().getString("Break." + material) == null) return;

        int income = data.getConfig().getInt("Break." + material + ".income");
        int xp = data.getConfig().getInt("Break." + material + ".experience");




    }
}
