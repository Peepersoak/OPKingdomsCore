package me.peepersoak.opkingdomscore.jobscertificate.data;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class WarriorData {

    public WarriorData() {
        init();
    }

    private File file;
    private YamlConfiguration config;

    public void init() {
        file = new File(OPKingdomsCore.getInstance().getDataFolder(), JobsString.WARRIOR_SETTINGS_FILE_NAME);

        if (!file.exists()) {
            OPKingdomsCore.getInstance().saveResource(JobsString.WARRIOR_SETTINGS_FILE_NAME, false);
            file = new File(OPKingdomsCore.getInstance().getDataFolder(), JobsString.WARRIOR_SETTINGS_FILE_NAME);
        }
        config = YamlConfiguration.loadConfiguration(file);
        config.options().copyDefaults(true);
        saveFileConfig();
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
