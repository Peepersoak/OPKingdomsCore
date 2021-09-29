package me.peepersoak.opkingdomscore.dragon_event;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DragonEventData {

    public DragonEventData() {
        init();
    }

    private File file;
    private YamlConfiguration config;

    public void init() {
        file = new File(OPKingdomsCore.getInstance().getDataFolder(), DragonStringpath.DRAGON_EVENT_SETTING_FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(ChatColor.RED + "Dragon Event Setting file not created!!");
            }
        }

        config = YamlConfiguration.loadConfiguration(file);

        if (config.getString(DragonStringpath.DRAGON_WORLD_NAME) == null) {
            config.addDefault(DragonStringpath.DRAGON_WORLD_NAME, "None");
        }

        if (config.getString(DragonStringpath.DRAGON_NAME) == null) {
            config.addDefault(DragonStringpath.DRAGON_NAME, "Ancient Black Dragon");
        }

        if (config.getString(DragonStringpath.DRAGON_SPAWN_LOCATION) == null) {
            config.addDefault(DragonStringpath.DRAGON_SPAWN_LOCATION, "None");
        }

        if (config.getStringList(DragonStringpath.DRAGON_END_CRYSTAL_LOCATION).size() == 0) {
            List<String> str = new ArrayList<>();
            config.addDefault(DragonStringpath.DRAGON_END_CRYSTAL_LOCATION, str);
        }

        if (config.getInt(DragonStringpath.DRAGON_HEALTH) == 0) {
            config.addDefault(DragonStringpath.DRAGON_HEALTH, 200);
        }

        if (!config.getBoolean(DragonStringpath.DRAGON_ALLOW_SKILL)) {
            config.addDefault(DragonStringpath.DRAGON_ALLOW_SKILL, false);
        }

        if (config.getInt(DragonStringpath.DRAGON_SKILL_COOLDOWN) == 0) {
            config.addDefault(DragonStringpath.DRAGON_SKILL_COOLDOWN, 5);
        }

        if (!config.getBoolean(DragonStringpath.DRAGON_SKILL_LIGHTNINGSTRIKE)) {
            config.addDefault(DragonStringpath.DRAGON_SKILL_LIGHTNINGSTRIKE, false);
        }

        if (!config.getBoolean(DragonStringpath.DRAGON_SKILL_EXPLOSION)) {
            config.addDefault(DragonStringpath.DRAGON_SKILL_EXPLOSION, false);
        }

        if (!config.getBoolean(DragonStringpath.DRAGON_SKILL_WITHER)) {
            config.addDefault(DragonStringpath.DRAGON_SKILL_WITHER, false);
        }

        if (!config.getBoolean(DragonStringpath.DRAGON_SKILL_GUARDIAN)) {
            config.addDefault(DragonStringpath.DRAGON_SKILL_GUARDIAN, false);
        }

        if (config.getInt(DragonStringpath.DRAGON_HEALTH_THRESHOLD) == 0) {
            config.addDefault(DragonStringpath.DRAGON_HEALTH_THRESHOLD, 50);
        }

        if (config.getInt(DragonStringpath.DRAGON_SKILL_PLAYER_PERCENTAGE) == 0) {
            config.addDefault(DragonStringpath.DRAGON_SKILL_PLAYER_PERCENTAGE, 25);
        }

        if (config.getInt(DragonStringpath.DRAGON_SKILL_DISTANCE) == 0) {
            config.addDefault(DragonStringpath.DRAGON_SKILL_DISTANCE, 20);
        }

        if (config.getString(DragonStringpath.DRAGON_EVENT_STATUS) == null) {
            config.addDefault(DragonStringpath.DRAGON_EVENT_STATUS, "Dead");
        }

        if (!config.getBoolean(DragonStringpath.DRAGON_PARTICLE)) {
            config.addDefault(DragonStringpath.DRAGON_PARTICLE, false);
        }

        if (!config.getBoolean(DragonStringpath.DRAGON_EVENT)) {
            config.addDefault(DragonStringpath.DRAGON_EVENT, false);
        }

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

    public void write(String path, String value) {
        config.set(path, value);
        saveFileConfig();
        init();
    }

    public void writeList(String path, List<String> value) {
        config.set(path, value);
        saveFileConfig();
        init();
    }

    public void writeBoolean(String path, Boolean value) {
        config.set(path, value);
        saveFileConfig();
        init();
    }

    public void writeInteger(String path, Integer value) {
        config.set(path, value);
        saveFileConfig();
        init();
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
