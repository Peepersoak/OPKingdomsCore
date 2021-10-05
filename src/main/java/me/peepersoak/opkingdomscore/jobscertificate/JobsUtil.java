package me.peepersoak.opkingdomscore.jobscertificate;

import org.bukkit.persistence.PersistentDataContainer;

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
}
