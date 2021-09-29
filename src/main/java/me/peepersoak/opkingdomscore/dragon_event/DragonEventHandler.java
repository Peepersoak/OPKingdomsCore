package me.peepersoak.opkingdomscore.dragon_event;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.dragon_event.GUI.GUIListener;
import org.bukkit.plugin.PluginManager;

public class DragonEventHandler {

    public void registerDragonEvent(OPKingdomsCore plugin, PluginManager pm) {
        pm.registerEvents(new TopDamager(), plugin);
        pm.registerEvents(new GUIListener(), plugin);
    }
}
