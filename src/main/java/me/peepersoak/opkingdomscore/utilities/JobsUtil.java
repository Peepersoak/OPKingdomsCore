package me.peepersoak.opkingdomscore.utilities;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobsData;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class JobsUtil {
    
    public static Integer getLevelNeeded(String str) {
        String[] split = str.split("_");
        int level = -1;
        try {
            level = Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            //
        }
        return level;
    }

    public static boolean isBlock(String material, ConfigurationSection section) {
        return section.getString(material) != null;
    }

    public static String getPlayerJobTitle(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(JobsString.JOB_TITLE, PersistentDataType.STRING) == null) return null;
        return data.get(JobsString.JOB_TITLE, PersistentDataType.STRING);
    }

    public static int getPlayerJobLevel(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(JobsString.JOB_LEVEL, PersistentDataType.INTEGER) == null) return -1;
        return data.get(JobsString.JOB_LEVEL, PersistentDataType.INTEGER);
    }

    public static Double getPlayerJobXP(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(JobsString.JOB_XP, PersistentDataType.DOUBLE) == null) return -1.0;
        return data.get(JobsString.JOB_XP, PersistentDataType.DOUBLE);
    }

    public static int getPlayerJobXPTarget(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(JobsString.JOB_XP_TARGET, PersistentDataType.INTEGER) == null) return -1;
        return data.get(JobsString.JOB_XP_TARGET, PersistentDataType.INTEGER);
    }

    public static int getPlayerToken(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(JobsString.JOB_TOKEN, PersistentDataType.INTEGER) == null) return -1;
        return data.get(JobsString.JOB_TOKEN, PersistentDataType.INTEGER);
    }

    public static void addTokenToPlayer(Player player, int token) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(JobsString.JOB_TOKEN, PersistentDataType.INTEGER) == null) {
            data.set(JobsString.JOB_TOKEN, PersistentDataType.INTEGER, 0);
        }
        int newToken = data.get(JobsString.JOB_TOKEN, PersistentDataType.INTEGER) + token;
        data.set(JobsString.JOB_TOKEN, PersistentDataType.INTEGER, newToken);
    }

    public static void changeLevel(Player player, int level, int target) {
        JobsData jobsData = new JobsData();
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10F, 1F);
        int tokenCost = jobsData.getConfig().getInt(JobsString.LEVEL_UP_TOKEN);
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(JobsString.JOB_LEVEL, PersistentDataType.INTEGER, level + 1);
        data.set(JobsString.JOB_XP, PersistentDataType.DOUBLE, 0.0);
        data.set(JobsString.JOB_XP_TARGET, PersistentDataType.INTEGER, target);
        data.set(JobsString.JOB_TOKEN, PersistentDataType.INTEGER, data.get(JobsString.JOB_TOKEN, PersistentDataType.INTEGER) - tokenCost);
    }

    public static void addXPToPlayer(Player player, double xp) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        int xpRequirement = getXPNeeded(JobsUtil.getPlayerJobTitle(player), JobsUtil.getPlayerJobLevel(player));
        double newXP = xp;
        if (xp >= xpRequirement) {
            newXP = xpRequirement;
        }
        data.set(JobsString.JOB_XP, PersistentDataType.DOUBLE, newXP);
    }

    public static boolean hasJob(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        return data.has(JobsString.JOB_TITLE, PersistentDataType.STRING);
    }

    public static int getEarnXP(ConfigurationSection section, String material) {
        return section.getInt(material + "." + JobsString.XP);
    }

    public static int getEarnIncome(ConfigurationSection section, String material) {
        return section.getInt(material + "." + JobsString.INCOME);
    }

    public static int getTokenNeeded(Player player) {
        JobsData jobsData = new JobsData();
        int level = JobsUtil.getPlayerJobLevel(player);
        int newToken = jobsData.getConfig().getInt(JobsString.LEVEL_UP_TOKEN);
        double ampl = jobsData.getConfig().getDouble(JobsString.LEVEL_UP_TOKEN_MULTIPLIER);
        for (int i = 0; i <= level; i++) {
            if (i == 0) continue;
            newToken = (int) (newToken * ampl);
        }
        return newToken;
    }

    public static String getLevelUPRawMessage(String title) {
        JobsData jobsData = new JobsData();
        return jobsData.getConfig().getString(title + "." + JobsString.JOBS_LEVEL_UP_MESSAGE);
    }

    public static int getXPNeeded(String title, int level) {
        JobsData jobsData = new JobsData();
        int newXPTarget = jobsData.getConfig().getInt(title + "." + JobsString.XP_REQUIREMENT);
        double amp = jobsData.getConfig().getDouble(title + "." + JobsString.XP_MULTIPLIER);
        for (int i = 0; i <= level; i++) {
            if (i == 0) continue;
            newXPTarget = (int) (newXPTarget * amp);
        }
        return newXPTarget;
    }
}
