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

    public String getDayOfWeek() {
        String day;
        int dayCount = getCalendar().get(Calendar.DAY_OF_WEEK);
        return convertCountToDay(dayCount);
    }
    
    public String convertCountToDay(int count) {
        String day = null;
        switch (count) {
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
        }
        return day;
    }

    public void checkWeekly() {
        ScheduleData data = new ScheduleData();
        ConfigurationSection weekly = data.getConfig().getConfigurationSection(ScheduleStringPath.WEEKLY_EVENT_SECTION);
        for (String day : weekly.getKeys(false)) {
            if (!weekly.getBoolean(ScheduleStringPath.EVENT_RUN)) continue;
            if (!getDayOfWeek().equalsIgnoreCase(day)) return;

            int eventTime = weekly.getInt(ScheduleStringPath.EVENT_TIME);
            String eventMessage = weekly.getString(ScheduleStringPath.EVENT_START_MESSAGE);

            int hourLeftReminder = weekly.getInt(ScheduleStringPath.EVENT_REMINDER_HOUR_LEFT);
            List<String> reminderMessage = weekly.getStringList(ScheduleStringPath.EVENT_REMINDER_MESSAGE);

            List<String> eventCommand = weekly.getStringList(ScheduleStringPath.EVENT_COMMANDS);

            RunSchedule schedule = new RunSchedule(day, eventTime, hourLeftReminder, reminderMessage, eventCommand, eventMessage);
            schedule.runTaskTimerAsynchronously(OPKingdomsCore.getInstance(), 0, 20);
        }
    }
}
