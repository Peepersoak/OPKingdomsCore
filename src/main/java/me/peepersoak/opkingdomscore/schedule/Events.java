package me.peepersoak.opkingdomscore.schedule;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Events {

    private static final List<String> registerEventName = new ArrayList<>();

    public static List<String> getRegisterEventName() {
        return registerEventName;
    }

    public static void addEvents(String str) {
        registerEventName.add(str);
    }

    public static void removeEvents(String str) {
        registerEventName.remove(str);
    }

    public static void getEvents(Player player) {
        if (Events.getRegisterEventName().isEmpty()) {
            player.sendMessage(ChatColor.RED + "No scheduled Events for today");
            return;
        }
        player.sendMessage(ChatColor.GOLD + "=====================");
        player.sendMessage("");
        player.sendMessage(ChatColor.GREEN + "Events for today:");
        player.sendMessage("");
        int count = 1;
        for (String str : Events.getRegisterEventName()) {
            player.sendMessage(ChatColor.LIGHT_PURPLE + "Event "+ count + ":");
            String msg = ChatColor.translateAlternateColorCodes('&', str);
            player.sendMessage(msg);
            count++;
        }
        player.sendMessage("");
        player.sendMessage(ChatColor.GREEN + "Upcoming Events:");
        player.sendMessage("");
        getUpcomingEvents(player);
        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + "=====================");
    }

    public static void getUpcomingEvents(Player player) {
        ScheduleData data = new ScheduleData();
        ConfigurationSection section = data.getConfig().getConfigurationSection(ScheduleStringPath.WEEKLY_EVENT_SECTION);
        assert section != null;
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Singapore"));
        for (String key : section.getKeys(false)) {
            String[] split = key.split("_");
            String day = split[0];
            if (day.equalsIgnoreCase(now.getDayOfWeek().toString())) continue;
            int time = section.getInt(key + "." + ScheduleStringPath.EVENT_TIME);
            String name = section.getString(key + "." + ScheduleStringPath.EVENT_NAME);
            assert name != null;
            String msg = ChatColor.translateAlternateColorCodes('&', name);
            player.sendMessage(msg + ", scheduled on " + day + " " + time + ":00");
        }
    }
}
