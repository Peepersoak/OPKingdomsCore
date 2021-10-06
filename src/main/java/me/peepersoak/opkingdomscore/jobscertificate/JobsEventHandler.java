package me.peepersoak.opkingdomscore.jobscertificate;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.converter.ConverterGUIListener;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.upgrade.UpgradeGUIListener;
import me.peepersoak.opkingdomscore.jobscertificate.jobs.miner.MinerListener;
import org.bukkit.plugin.PluginManager;

public class JobsEventHandler {

    public void registerJobsEvent(OPKingdomsCore instance, PluginManager pm) {
        pm.registerEvents(new JobsCertificateListener(), instance);
        pm.registerEvents(new MinerListener(), instance);
        pm.registerEvents(new ConverterGUIListener(), instance);
        pm.registerEvents(new UpgradeGUIListener(), instance);
    }
}
