package me.peepersoak.opkingdomscore;

import me.peepersoak.opkingdomscore.dragon_event.*;
import me.peepersoak.opkingdomscore.commands.GeneralCommands;
import me.peepersoak.opkingdomscore.commands.TabCompletions;
import me.peepersoak.opkingdomscore.jobscertificate.JobsEventHandler;
import me.peepersoak.opkingdomscore.jobscertificate.commands.JobsCommand;
import me.peepersoak.opkingdomscore.jobscertificate.data.*;
import me.peepersoak.opkingdomscore.schedule.ScheduleData;
import me.peepersoak.opkingdomscore.schedule.Scheduler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class OPKingdomsCore extends JavaPlugin implements Listener {

    private static OPKingdomsCore instance;

    private final DragonEventHandler dragonEventHandler = new DragonEventHandler();
    private final JobsEventHandler jobsEventHandler = new JobsEventHandler();
    private final DragonEggData eggData = new DragonEggData();

    private static Economy econ = null;


    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        initializedYMLSettings();

        PluginManager pm = Bukkit.getPluginManager();
        dragonEventHandler.registerDragonEvent(this, pm);
        jobsEventHandler.registerJobsEvent(this, pm);

        Objects.requireNonNull(getCommand("opkingdoms")).setExecutor(new GeneralCommands());
        Objects.requireNonNull(getCommand("opkingdoms")).setTabCompleter(new TabCompletions());

        Objects.requireNonNull(getCommand("jobscert")).setExecutor(new JobsCommand());

        Scheduler scheduler = new Scheduler();

        if (!setupEconomy() ) {
            System.out.println(ChatColor.RED + "No Economy found, disabling vault");
            Bukkit.shutdown();
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
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
        ScheduleData scheduleData = new ScheduleData();
        JobMessage jobMessage = new JobMessage();

        eggData.getYMLData();
    }

    public static OPKingdomsCore getInstance() {
        return instance;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
