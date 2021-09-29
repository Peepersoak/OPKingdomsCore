package me.peepersoak.opkingdomscore.jobscertificate;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.plugin.PluginManager;

public class JobsEventHandler {

    public void registerJobsEvent(OPKingdomsCore instance, PluginManager pm) {
        pm.registerEvents(new JobsCertificateListener(), instance);
    }
}
