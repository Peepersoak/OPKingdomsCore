package me.peepersoak.opkingdomscore.schedule;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Calendar;
import java.util.List;

public class RunSchedule extends BukkitRunnable {

    public RunSchedule(String day, int eventTime, int hourLeft, List<String> reminderMessage, List<String> eventCommands, String eventMessage, String eventName, Calendar calendar) {
        this.eventTime = eventTime;
        this.eventCommands = eventCommands;
        this.reminderMessage = reminderMessage;
        this.reminderhourLeft = hourLeft;
        this.eventMessage = eventMessage;
        this.calendar = calendar;
        this.eventName = eventName;
        this.day = day;
        setCalendar();
    }

    private final int eventTime;
    private final int reminderhourLeft;
    private final List<String> reminderMessage;
    private final List<String> eventCommands;
    private final String eventMessage;
    private final String eventName;
    private final Calendar calendar;
    private Calendar eventDate;
    private final String day;

    private int varHour = -1;
    private int varMin = -1;

    public void setCalendar() {
        eventDate = Calendar.getInstance();
        eventDate.setTimeZone(calendar.getTimeZone());
        eventDate.set(Calendar.DAY_OF_WEEK, convertDayToNum());
        eventDate.set(Calendar.HOUR_OF_DAY, eventTime);
        eventDate.set(Calendar.MINUTE, 0);
        eventDate.set(Calendar.SECOND, 0);
        eventDate.set(Calendar.MILLISECOND, 0);
        System.out.println(ChatColor.GREEN + eventName + " has been scheduled on " + eventDate.getTime());
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

    public int getHour(Calendar calendar) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public int getMinLeft(Calendar calendar) {
        int min = calendar.get(Calendar.MINUTE);
        return min;
    }

    public int getSeconLeft(Calendar calendar) {
        int sec = calendar.get(Calendar.SECOND);
        return sec;
    }

    public void sendHourlyMessage(Calendar current) {
        int hourLeft =  getHour(eventDate) - getHour(current);
        if (hourLeft > reminderhourLeft) return;
        if (varHour == hourLeft) return;
        varHour = hourLeft;
        if (current.get(Calendar.MINUTE) != 0) return;
        if (varHour <= 1) return;
        sendMessageSync(hourLeft, "hour/'s");
    }

    public void sendMinuteMessage(Calendar current) {
        if (varHour != 1) return;
        if (varMin == current.get(Calendar.MINUTE)) return;
        varMin = current.get(Calendar.MINUTE);
        int minLeft = 60 - varMin;
        switch (varMin) {
            case 0:
            case 30:
            case 45:
            case 50:
            case 55:
                sendMessageSync(minLeft, "minute/'s");
                break;
            case 59:
                varMin = 59;
                break;
        }
    }

    public void sendSecondMessage(Calendar current) {
        if (varHour != 1) return;
        if (varMin != 59) return;
        int secLeft = 60 - current.get(Calendar.SECOND);
        switch (current.get(Calendar.SECOND)) {
            case 0:
            case 30:
                sendMessageSync(secLeft, "second/'s");
                break;
            case 54:
                String msg = ChatColor.translateAlternateColorCodes('&', eventName);
                sendCustMessage(msg + " is starting in:");
                break;
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                sendCustMessage(ChatColor.GREEN + "" + secLeft);
                break;
        }
    }

    public void sendEventNameTile() {
        new BukkitRunnable() {
            @Override
            public void run() {
                String msg = ChatColor.translateAlternateColorCodes('&', eventMessage);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle(msg, "", 20, 60, 20);
                }
            }
        }.runTask(OPKingdomsCore.getInstance());
    }

    public void sendCustMessage(String msg) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(msg);
            }
        }.runTask(OPKingdomsCore.getInstance());
    }

    public void sendMessageSync(int timeLeft, String format) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (String str : reminderMessage) {
                    String msg = ChatColor.translateAlternateColorCodes('&', str);
                    Bukkit.broadcastMessage(msg.replace("%time_left%", "" + timeLeft + " " + format));
                }
            }
        }.runTask(OPKingdomsCore.getInstance());
    }

    public void runCommands() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (String str : eventCommands) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), str);
                }
            }
        }.runTask(OPKingdomsCore.getInstance());
    }

    @Override
    public void run() {
        Calendar current = Calendar.getInstance();
        current.setTimeZone(calendar.getTimeZone());
        if (current.after(eventDate)) this.cancel();
        sendHourlyMessage(current);
        sendMinuteMessage(current);
        sendSecondMessage(current);
        if (current.get(Calendar.HOUR_OF_DAY) == eventDate.get(Calendar.HOUR_OF_DAY) &&
        current.get(Calendar.MINUTE) == eventDate.get(Calendar.MINUTE)) {
            sendEventNameTile();
            runCommands();
            this.cancel();
        }
        System.out.println(current.getTime());
    }
}
