package me.peepersoak.opkingdomscore.jobscertificate;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.change.ChangeGUIListener;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.converter.ConverterGUIListener;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.jobsGUI.JobsGUIListener;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.upgrade.UpgradeGUIListener;
import me.peepersoak.opkingdomscore.jobscertificate.jobs.*;
import me.peepersoak.opkingdomscore.jobscertificate.jobs.logger.LoggerListener;
import me.peepersoak.opkingdomscore.jobscertificate.jobs.miner.MinerListener;
import org.bukkit.plugin.PluginManager;

public class JobsEventHandler {

    public void registerJobsEvent(OPKingdomsCore instance, PluginManager pm) {
        pm.registerEvents(new JobsGUIListener(), instance);
        pm.registerEvents(new MinerListener(), instance);
        pm.registerEvents(new ConverterGUIListener(), instance);
        pm.registerEvents(new UpgradeGUIListener(), instance);
        pm.registerEvents(new LoggerListener(), instance);
        pm.registerEvents(new ArcherListener(), instance);
        pm.registerEvents(new WarriorListener(), instance);
        pm.registerEvents(new SmithingListener(), instance);
        pm.registerEvents(new BrewerListener(), instance);
        pm.registerEvents(new EnchanterListener(), instance);
        pm.registerEvents(new ChangeGUIListener(), instance);
    }
}
