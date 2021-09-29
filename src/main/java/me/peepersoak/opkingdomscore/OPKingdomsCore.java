package me.peepersoak.opkingdomscore;

import me.peepersoak.opkingdomscore.dragon_event.*;
import me.peepersoak.opkingdomscore.commands.GeneralCommands;
import me.peepersoak.opkingdomscore.commands.TabCompletions;
import me.peepersoak.opkingdomscore.jobscertificate.JobsEventHandler;
import me.peepersoak.opkingdomscore.jobscertificate.data.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class OPKingdomsCore extends JavaPlugin implements Listener {

    private final TaskRunnable specialEvents = new TaskRunnable();
    private static OPKingdomsCore instance;

    private final DragonEventHandler dragonEventHandler = new DragonEventHandler();
    private final JobsEventHandler jobsEventHandler = new JobsEventHandler();
    private final DragonEggData eggData = new DragonEggData();


    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        initializedYMLSettings();

        PluginManager pm = Bukkit.getPluginManager();
        dragonEventHandler.registerDragonEvent(this, pm);
//        jobsEventHandler.registerJobsEvent(this, pm);

        getCommand("opkingdoms").setExecutor(new GeneralCommands());
        getCommand("opkingdoms").setTabCompleter(new TabCompletions());

//        specialEvents.runTaskTimer(this, 0, 20);
    }

    public void initializedYMLSettings() {
        JobsData data = new JobsData();
        MinerData minerData = new MinerData();
        LoggerData loggerData = new LoggerData();
        BrewerData brewerData = new BrewerData();
        EnchanterData enchanterData = new EnchanterData();
        WarriorData warriorData = new WarriorData();
        ArcherData archerData = new ArcherData();
        SmithingData smithingData = new SmithingData();

        eggData.getYMLData();
    }

    public static OPKingdomsCore getInstance() {
        return instance;
    }
}
