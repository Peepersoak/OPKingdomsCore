package me.peepersoak.opkingdomscore.market.sql;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SQLData {

    public SQLData() {
        init();
    }

    private File file;
    private YamlConfiguration config;

    public void init() {
        file = new File(OPKingdomsCore.getInstance().getDataFolder(), "SQL Settings.yml");
        if (!file.exists()) {
            OPKingdomsCore.getInstance().saveResource("SQL Settings.yml", false);
            file = new File(OPKingdomsCore.getInstance().getDataFolder(), "SQL Settings.yml");
        }
        config = YamlConfiguration.loadConfiguration(file);
        config.options().copyDefaults(true);
        saveFileConfig();
    }
    public void saveFileConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            System.out.println("Can't save the SQL Settings file");
        }
    }

    public String getHost() {
        return config.getString("Host");
    }
    public int getPort() {
        return config.getInt("Port");
    }
    public String getDBName() {
        return config.getString("Database_Name");
    }
    public String getDBUsername() {
        return config.getString("Username");
    }
    public String getDBPassword() {
        return config.getString("Password");
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
