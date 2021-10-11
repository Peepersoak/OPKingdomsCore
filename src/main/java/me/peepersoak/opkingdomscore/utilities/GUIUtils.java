package me.peepersoak.opkingdomscore.utilities;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobMessage;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobsData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GUIUtils {

    public static String getGUITItle(String path) {
        JobMessage message = new JobMessage();
        String raw = message.getConfig().getString(path);
        assert raw != null;
        return ChatColor.translateAlternateColorCodes('&', raw);
    }

    public static ConfigurationSection getButtonSection(String path) {
        JobMessage message = new JobMessage();
        return message.getConfig().getConfigurationSection(path);
    }

    public static String getButtonPath(ConfigurationSection buttons, int slot) {
        int count = 0;
        for (String key : buttons.getKeys(false)) {
            if (count == slot) return key;
            count++;
        }
        return null;
    }

    public static String getButtonTitle(String path, int slot) {
        String key = getButtonPath(getButtonSection(path), slot);
        String raw = getButtonSection(path).getString(key + "." + JobsString.TITLE);
        assert raw != null;
        return ChatColor.translateAlternateColorCodes('&', raw);
    }

    public static Material getButtonMaterial(String path, int slot) {
        String key = getButtonPath(getButtonSection(path), slot);
        return Material.valueOf(Objects.requireNonNull(getButtonSection(path).getString(key + "." + JobsString.MATERIAL)).toUpperCase());
    }

    public static boolean getButtonIsGlowing(String path, int slot) {
        String key = getButtonPath(getButtonSection(path), slot);
        return getButtonSection(path).getBoolean(key + "." + JobsString.GLOWING);
    }

    public static List<String> getButtonLores(String path, int slot, Player player) {
        String key = getButtonPath(getButtonSection(path), slot);
        List<String> lores = new ArrayList<>();
        for (String lore : getButtonSection(path).getStringList(key + "." + JobsString.LORE)) {
            lores.add(ChatColor.translateAlternateColorCodes('&', replacePlaceHolders(lore, player)));
        }
        return lores;
    }

    public static String replacePlaceHolders(String str, Player player) {
        JobsData data = new JobsData();
        return str
                .replace("%xp_requirements%", "" + JobsUtil.getPlayerJobXPTarget(player))
                .replace("%token%", "" + JobsUtil.getTokenNeeded(player))
                .replace("%player_name%", player.getName())
                .replace("%player_token%", "" + JobsUtil.getPlayerToken(player))
                .replace("%player_xp%","" + JobsUtil.getPlayerJobXP(player))
                .replace("%player_bal%", "" + OPKingdomsCore.getEconomy().getBalance(player))
                .replace("%dollar_ratio%", "" + data.getConfig().getInt(JobsString.CONVERTER_DOLLAR));
    }
}
