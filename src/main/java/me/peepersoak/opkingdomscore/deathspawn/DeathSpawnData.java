package me.peepersoak.opkingdomscore.deathspawn;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DeathSpawnData {

    public DeathSpawnData() {
        init();
    }

    private File file;
    private YamlConfiguration config;

    public void init() {
        file = new File(OPKingdomsCore.getInstance().getDataFolder(), "Death Spawn Data.yml");
        if (!file.exists()) {
            OPKingdomsCore.getInstance().saveResource("Death Spawn Data.yml", false);
            file = new File(OPKingdomsCore.getInstance().getDataFolder(), "Death Spawn Data.yml");
        }
        config = YamlConfiguration.loadConfiguration(file);
        config.options().copyDefaults(true);
        saveFileConfig();
    }

    public void saveFileConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            System.out.println("Can't save the Death Spawn file");
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
