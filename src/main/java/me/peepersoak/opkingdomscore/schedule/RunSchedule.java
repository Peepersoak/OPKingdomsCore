package me.peepersoak.opkingdomscore.schedule;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;
import java.util.List;

public class RunSchedule extends BukkitRunnable {

    public RunSchedule(String day, int eventTime, int hourLeft, List<String> reminderMessage, List<String> eventCommands, String eventMessage) {
        this.eventTime = eventTime;
        this.eventCommands = eventCommands;
        this.reminderMessage = reminderMessage;
        this.reminderhourLeft = hourLeft;
        this.eventMessage = eventMessage;
        this.day = day;
        setCalendar();
    }

    private int eventTime;
    private int reminderhourLeft;
    private List<String> reminderMessage;
    private List<String> eventCommands;
    private String eventMessage;
    private Calendar calendar;
    private final String day;

    public void setCalendar() {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, convertDayToNum());
        calendar.set(Calendar.HOUR, 11);
        calendar.set(Calendar.MINUTE, 38);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println(ChatColor.GREEN + eventMessage + " has been scheduled on " + calendar.getTime());
    }

    public int convertDayToNum() {
        int count = 0;
        switch (day) {
            case "Sunday":
                count = 1;
                break;
            case "Monday":
                count = 2;
                break;
            case "Tuesday":
                count = 3;
                break;
            case "Wednesday":
                count = 4;
                break;
            case "Thursday":
                count = 5;
                break;
            case "Friday":
                count = 6;
                break;
            case "Saturday":
                count = 7;
                break;
        }
        return count;
    }

    public int getHourLeft() {
        int hour = calendar.get(Calendar.HOUR);
        return eventTime - hour;
    }

    public int getMinLeft() {
        int min = calendar.get(Calendar.MINUTE);
        return 59 - min;
    }

    public int getSeconLeft() {
        int sec = calendar.get(Calendar.SECOND);
        return 60 - sec;
    }

    @Override
    public void run() {
        Calendar current = Calendar.getInstance();
        System.out.println("--");
        System.out.println(ChatColor.RED + "Trying to run event");
        System.out.println(ChatColor.GREEN + "" + current.getTime());
        System.out.println(ChatColor.GOLD + "" + calendar.getTime());
        System.out.println("--");
        if (current == calendar) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "opkingdoms launch");
            this.cancel();
        }
    }
}
