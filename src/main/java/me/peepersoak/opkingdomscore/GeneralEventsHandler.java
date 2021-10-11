package me.peepersoak.opkingdomscore;

import me.peepersoak.opkingdomscore.deathspawn.DeathSpawn;
import org.bukkit.plugin.PluginManager;

public class GeneralEventsHandler {

    public void registerGeneralEvents(OPKingdomsCore instance, PluginManager pm) {
        pm.registerEvents(new DeathSpawn(), instance);
    }
}
