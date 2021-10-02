package me.peepersoak.opkingdomscore.schedule;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class RunSchedule extends BukkitRunnable {

    public RunSchedule(String day, int eventTime, int hourLeft, List<String> reminderMessage, List<String> eventCommands, String eventMessage, String eventName) {
        this.eventTime = eventTime;
        this.eventCommands = eventCommands;
        this.reminderMessage = reminderMessage;
        this.reminderhourLeft = hourLeft;
        this.eventMessage = eventMessage;
        this.eventName = eventName;
        this.day = day;
        now = LocalDateTime.now(ZoneId.of("Asia/Singapore"));
        setCalendar();
    }

    private LocalDateTime now;

    private final int eventTime;
    private final int reminderhourLeft;
    private final List<String> reminderMessage;
    private final List<String> eventCommands;
    private final String eventMessage;
    private final String eventName;
    private final String day;

    private int varHour = -1;
    private int varMin = -1;

    private String eventDetails;

    public void setCalendar() {
        LocalDateTime time = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), eventTime, 0);
        eventDetails = eventName + " has been scheduled on " + time;
        Events.addEvents(eventDetails);
    }

    public boolean runEvent() {
        return day.equalsIgnoreCase(now.getDayOfWeek().toString()) &&
                eventTime == now.getHour() &&
                now.getMinute() == 0;
    }

    public void sendHourlyMessage() {
        int hourLeft =  eventTime - now.getHour();
        if (hourLeft > reminderhourLeft) return;
        if (varHour == hourLeft) return;
        varHour = hourLeft;
        if (now.getMinute() != 0) return;
        if (varHour <= 1) return;
        sendMessageSync(hourLeft, "hour/'s");
    }

    public void sendMinuteMessage() {
        if (varHour != 1) return;
        if (varMin == now.getMinute()) return;
        varMin = now.getMinute();
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

    public void sendSecondMessage() {
        if (varHour != 1) return;
        if (varMin != 59) return;
        int secLeft = 60 - now.getSecond();
        switch (now.getSecond()) {
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
                Bukkit.broadcastMessage(ChatColor.GOLD + "==========================");
                Bukkit.broadcastMessage("");
                String eventMsg = ChatColor.translateAlternateColorCodes('&', eventName);
                Bukkit.broadcastMessage(eventMsg);
                Bukkit.broadcastMessage("");
                for (String str : reminderMessage) {
                    String msg = ChatColor.translateAlternateColorCodes('&', str);
                    Bukkit.broadcastMessage(msg.replace("%time_left%", "" + timeLeft + " " + format));
                }
                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage(ChatColor.GOLD + "==========================");
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
        Events.removeEvents(eventDetails);
    }

    @Override
    public void run() {
        now = LocalDateTime.now(ZoneId.of("Asia/Singapore"));
        sendHourlyMessage();
        sendMinuteMessage();
        sendSecondMessage();
        if (runEvent()) {
            sendEventNameTile();
            runCommands();
            this.cancel();
        }
    }
}
