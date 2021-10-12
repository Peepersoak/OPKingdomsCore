package me.peepersoak.opkingdomscore.market;

import me.peepersoak.opkingdomscore.utilities.SQLUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class MarketListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!player.isOp()) return;
        SQLUtils.createPlayerTable(player.getUniqueId().toString());
    }
}
