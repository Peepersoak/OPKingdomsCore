package me.peepersoak.opkingdomscore.jobscertificate.data;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JobsData {

    public JobsData() {
        init();
    }

    private File file;
    private YamlConfiguration config;

    public void init() {
        file = new File(OPKingdomsCore.getInstance().getDataFolder(), JobsString.JOBS_CERTIFICATE_FILE_NAME);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(ChatColor.RED + "Jobs Certificate Files was not created!");
            }
        }
        config = YamlConfiguration.loadConfiguration(file);

        if (config.getInt(JobsString.LEVEL_UP_TOKEN) == 0) {
            config.addDefault(JobsString.LEVEL_UP_TOKEN, 20);
        }
        if (config.getDouble(JobsString.LEVEL_UP_TOKEN_MULTIPLIER) == 0) {
            config.addDefault(JobsString.LEVEL_UP_TOKEN_MULTIPLIER, 1.5);
        }
        if (config.getInt(JobsString.CONVERTER_DOLLAR) == 0) {
            config.addDefault(JobsString.CONVERTER_DOLLAR, 10000);
        }
        if (config.getConfigurationSection(JobsString.MINER_PATH) == null) {
            addDefault(JobsString.MINER_PATH);
        }
        if (config.getConfigurationSection(JobsString.LOGGER_PATH) == null) {
            addDefault(JobsString.LOGGER_PATH);
        }
        if (config.getConfigurationSection(JobsString.BREWER_PATH) == null) {
            addDefault(JobsString.BREWER_PATH);
        }
        if (config.getConfigurationSection(JobsString.ENCHANTER_PATH) == null) {
            addDefault(JobsString.ENCHANTER_PATH);
        }
        if (config.getConfigurationSection(JobsString.WARRIOR_PATH) == null) {
            addDefault(JobsString.WARRIOR_PATH);
        }
        if (config.getConfigurationSection(JobsString.ARCHER_PATH) == null) {
            addDefault(JobsString.ARCHER_PATH);
        }
        if (config.getConfigurationSection(JobsString.SMITH_PATH) == null) {
            addDefault(JobsString.SMITH_PATH);
        }

        config.options().copyDefaults(true);
        saveFileConfig();
    }

    public void addDefault(String jobTitle) {
        config.addDefault(jobTitle + "." + JobsString.JOBS_TITLE_NAME, jobTitle);
        config.addDefault(jobTitle + "." + JobsString.XP_REQUIREMENT, 1000);
        config.addDefault(jobTitle + "." + JobsString.XP_MULTIPLIER, 1.0);
        List<String> lore = new ArrayList<>();
        lore.add("Lore 1");
        lore.add("Lore 2");
        lore.add("Lore 3");
        config.addDefault(jobTitle + "." + JobsString.JOBS_LORE, lore);
        config.addDefault(jobTitle + "." + JobsString.JOBS_JOIN_MESSAGE, "Message Here");
        config.addDefault(jobTitle + "." + JobsString.JOBS_LEAVE_MESSAGE, "Message Here");
        config.addDefault(jobTitle + "." + JobsString.JOBS_LEVEL_UP_MESSAGE, "You are now a level %job_level% %job_title%");
    }

    public void saveFileConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            System.out.println("Can't save the Custom Advancement file");
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
