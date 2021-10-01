package me.peepersoak.opkingdomscore.schedule;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.dragon_event.DragonEggData;
import me.peepersoak.opkingdomscore.dragon_event.DragonEvent;
import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.*;

public class TaskRunnable extends BukkitRunnable {

    private final Random rand = new Random();
    private int announceHour = -1;
    private int announceMinute = -1;
    private DragonEventData data;

    public Calendar getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
        sdf.setTimeZone(tz);

        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(tz);

        return calendar;
    }

    public int getDayInConfig() {
        String str  = OPKingdomsCore.getInstance().getConfig().getString("Day");
        int day = 0;

        switch (str) {
            case "Sunday":
                day = 1;
                break;
            case "Monday":
                day = 2;
                break;
            case "Tuesday":
                day = 3;
                break;
            case "Wednesday":
                day = 4;
                break;
            case "Thursday":
                day = 5;
                break;
            case "Friday":
                day = 6;
                break;
            case "Saturday":
                day = 7;
                break;
        }
        return day;
    }

    public boolean runTheEvent(Calendar calendar) {
        String data = OPKingdomsCore.getInstance().getConfig().getString("Time");
        int hour = Integer.parseInt(data);

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour == currentHour);
    }

    public void eggUpdater() {
        DragonEventData eventData = new DragonEventData();
        String[] spl = Objects.requireNonNull(eventData.getConfig().getString(DragonStringpath.DRAGON_SPAWN_LOCATION)).split(";");
        Location location = new Location(Bukkit.getWorld(spl[0]), Integer.parseInt(spl[1]), Integer.parseInt(spl[2]), Integer.parseInt(spl[3]));

        int random = rand.nextInt(4) + 1;
        List<String> blockLocation = DragonEggData.getEggData();
        for (int i = 0; i < random; i++) {
            Collections.shuffle(blockLocation);
            String data = blockLocation.get(0);
            String[] firstSplit = data.split(" ");
            String[] main = firstSplit[1].split(";");
            int offX = Integer.parseInt(main[0]);
            int offY = Integer.parseInt(main[1]);
            int offZ = Integer.parseInt(main[2]);
            BlockData blockData = Bukkit.createBlockData(main[3]);
            Block block = location.getBlock().getRelative(offX, offY, offZ);
            if (block.getType() == Material.PURPLE_CONCRETE) return;
            block.setType(Material.PURPLE_CONCRETE);
        }
    }

    public void sendMessage(Calendar calendar, int eventHour) {
        String msg = OPKingdomsCore.getInstance().getConfig().getString(DragonStringpath.DRAGON_EVENT_REMAINING_MESSAGE);
        assert msg != null;
        String fullMsg = ChatColor.translateAlternateColorCodes('&', msg);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        int remainingHour = eventHour - (hour + 1);
        int remainingMinute = 60 - minute;
        int remainingSecond = 60 - second;

        String updateHour = fullMsg.replace("%hour%", remainingHour + "");
        String updateMinute = updateHour.replace("%minutes%", remainingMinute + "");

        if (remainingHour >= 0) {
            System.out.println(remainingHour + " " + remainingMinute + " " + remainingSecond);

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (remainingHour == announceHour) return;
                if (remainingHour == 3) {
                    announceHour = 3;
                    player.sendMessage(updateMinute);
                } else if (remainingHour == 2) {
                    announceHour = 2;
                    player.sendMessage(updateMinute);
                } else if (remainingHour == 1) {
                    announceHour = 1;
                    player.sendMessage(updateMinute);
                } else if (remainingHour == 0) {
                    if (remainingMinute == announceMinute) return;
                    if (remainingMinute == 30) {
                        announceMinute = 30;
                        player.sendMessage(updateMinute);
                    } else if (remainingMinute == 10 && remainingSecond == 1) {
                        announceMinute = 10;
                        player.sendMessage(updateMinute);
                    } else if (remainingMinute == 5) {
                        announceMinute = 5;
                        player.sendMessage(updateMinute);
                    } else if (remainingMinute == 1) {
                        if (remainingSecond == 30) {
                            player.sendMessage(ChatColor.GOLD + "" + remainingSecond + " seconds remaining");
                        }
                        else if (remainingSecond == 10) {
                            if (data.getConfig().getString(DragonStringpath.DRAGON_WORLD_NAME).equalsIgnoreCase(player.getWorld().getName())) {
                                player.sendTitle(ChatColor.GOLD + "Event Starting in: " + remainingSecond, "", 10, 20, 0);
                            } else {
                                player.sendMessage(ChatColor.GOLD + "" + remainingSecond + " seconds remaining");
                            }
                        }
                        else if (remainingSecond < 10) {
                            if (data.getConfig().getString(DragonStringpath.DRAGON_WORLD_NAME).equalsIgnoreCase(player.getWorld().getName())) {
                                player.sendTitle(ChatColor.GOLD + "" + remainingSecond, "", 0, 20, 0);
                            } else {
                                player.sendMessage(ChatColor.GOLD + "" + remainingSecond);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        data = new DragonEventData();
        Calendar calendar = getTime();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

        if (currentDay != getDayInConfig()) {
            data.writeBoolean(DragonStringpath.DRAGON_EVENT, false);
            return;
        }

        sendMessage(calendar, 13);

        if (!runTheEvent(calendar)) return;
        if (data.getConfig().getBoolean(DragonStringpath.DRAGON_EVENT)) return;

        DragonEvent event = new DragonEvent();
        event.startEvent();
        data.writeBoolean(DragonStringpath.DRAGON_EVENT, true);
    }
}
