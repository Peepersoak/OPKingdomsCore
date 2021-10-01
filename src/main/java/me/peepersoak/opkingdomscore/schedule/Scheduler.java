package me.peepersoak.opkingdomscore.schedule;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Scheduler {

    public Scheduler() {
        checkWeekly();
    }

    public Calendar getCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
        sdf.setTimeZone(tz);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(tz);
        return calendar;
    }

    public void checkWeekly() {
        ScheduleData data = new ScheduleData();
        ConfigurationSection weekly = data.getConfig().getConfigurationSection(ScheduleStringPath.WEEKLY_EVENT_SECTION);
        assert weekly != null;
        for (String day : weekly.getKeys(false)) {
            String[] strsplit = day.split("_");
            String str = strsplit[0];

            if (!weekly.getBoolean(day + "." + ScheduleStringPath.EVENT_RUN)) continue;

            int eventTime = weekly.getInt(day + "." +ScheduleStringPath.EVENT_TIME);
            String eventMessage = weekly.getString(day + "." +ScheduleStringPath.EVENT_START_MESSAGE);
            String eventName = weekly.getString(day + "." + ScheduleStringPath.EVENT_NAME);

            int hourLeftReminder = weekly.getInt(day + "." +ScheduleStringPath.EVENT_REMINDER_HOUR_LEFT);
            List<String> reminderMessage = weekly.getStringList(day + "." +ScheduleStringPath.EVENT_REMINDER_MESSAGE);

            List<String> eventCommand = weekly.getStringList(day + "." +ScheduleStringPath.EVENT_COMMANDS);

            RunSchedule schedule = new RunSchedule(str, eventTime, hourLeftReminder, reminderMessage, eventCommand, eventMessage, eventName, getCalendar());
            schedule.runTaskTimerAsynchronously(OPKingdomsCore.getInstance(), 0, 20);
        }
    }
}
