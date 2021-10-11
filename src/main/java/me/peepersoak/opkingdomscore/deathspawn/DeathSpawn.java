package me.peepersoak.opkingdomscore.deathspawn;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathSpawn implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        DeathSpawnData data = new DeathSpawnData();
        String rawlOc = data.getConfig().getString("Death_Spawn.Location");
        String[] split = rawlOc.split(";");
        World world = Bukkit.getWorld(split[0].trim());
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int z = Integer.parseInt(split[3]);
        Location spawn = new Location(world, x, y, z);
        player.setBedSpawnLocation(spawn, true);
        for (String str : data.getConfig().getStringList("Death_Spawn.Message")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
        }
    }
}
