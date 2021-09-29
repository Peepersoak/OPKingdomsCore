package me.peepersoak.opkingdomscore.dragon_event;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class Rewards {

    public Rewards(HashMap<Player, Double> top, Player dragonKiller) {
        this.top = top;
        this.dragonKiller = dragonKiller;
        first = null;
        second = null;
        third = null;
        withRewards = new ArrayList<>();
    }

    private final HashMap<Player, Double> top;
    private final List<Player> withRewards;

    private final Player dragonKiller;

    private Player first;
    private Player second;
    private Player third;

    public void setTop() {
        List<Double> allDamage = new ArrayList<>();
        for (Player p : top.keySet()) {
            allDamage.add(top.get(p));
        }
        Collections.sort(allDamage);
        Collections.reverse(allDamage);
        for (Double damage : allDamage) {
            for (Map.Entry<Player, Double> entry : top.entrySet()) {
                if (entry.getValue().equals(damage)) {
                    Player p = entry.getKey();
                    if (withRewards.contains(p)) continue;
                    if (first == null) {
                        first = p;
                    } else if (second == null) {
                        second = p;
                    } else {
                        third = p;
                        return;
                    }
                }
            }
        }
    }

    public void sendEndEventMessage() {
        String eventEndMessage = OPKingdomsCore.getInstance().getConfig().getString(DragonStringpath.DRAGON_END_EVENT_MESSAGE);
        assert eventEndMessage != null;
        String msg = ChatColor.translateAlternateColorCodes('&', eventEndMessage);

        Bukkit.broadcastMessage(ChatColor.AQUA + "==========================");
        Bukkit.broadcastMessage(ChatColor.GREEN + "");
        Bukkit.broadcastMessage(msg);
        Bukkit.broadcastMessage(ChatColor.GREEN + "");
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Dragon Slayer:");
        Bukkit.broadcastMessage(ChatColor.GOLD + dragonKiller.getName());
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.GREEN + "Most Damage Dealt:");
        Bukkit.broadcastMessage(ChatColor.GOLD + getPlaceMessage(first));
        Bukkit.broadcastMessage(ChatColor.GOLD + getPlaceMessage(second));
        Bukkit.broadcastMessage(ChatColor.GOLD + getPlaceMessage(third));
        Bukkit.broadcastMessage(ChatColor.GREEN + "");
        Bukkit.broadcastMessage(ChatColor.YELLOW + "Your Rewards:");
        giveRewards();
        giveLevel();
        Bukkit.broadcastMessage(ChatColor.GREEN + "");
        Bukkit.broadcastMessage(ChatColor.AQUA + "==========================");
    }

    public String getPlaceMessage(Player player) {
        String msg = "-";
        if (first != null && player == first) {
            msg = ChatColor.GOLD + "First Place: " +
                    ChatColor.AQUA + player.getName() +
                    ChatColor.GOLD + ": " +
                    ChatColor.RED + Math.floor(top.get(player));
        } else if (second != null && player == second) {
            msg = ChatColor.GOLD + "Second Place: " +
                    ChatColor.AQUA + player.getName() +
                    ChatColor.GOLD + ": " +
                    ChatColor.RED + Math.floor(top.get(player));
        } else if (third != null && player == third) {
            msg = ChatColor.GOLD + "Third Place: " +
                    ChatColor.AQUA + player.getName() +
                    ChatColor.GOLD + ": " +
                    ChatColor.RED + Math.floor(top.get(player));
        }
        return msg;
    }

    public void giveRewards() {
        FileConfiguration config = OPKingdomsCore.getInstance().getConfig();

        ConfigurationSection firstPlace = config.getConfigurationSection("First_Place_Rewards");
        ConfigurationSection secondPlace = config.getConfigurationSection("Second_Place_Rewards");
        ConfigurationSection thirdPlace = config.getConfigurationSection("Third_Place_Rewards");
        ConfigurationSection dragKiller = config.getConfigurationSection("Dragon_Killer");

        if (dragonKiller != null && dragKiller != null) {
            giveCommand(dragonKiller, dragKiller);
        }

        if (first != null && firstPlace != null) {
            if (!withRewards.contains(first)) {
                giveCommand(first, firstPlace);
                withRewards.add(first);
            }
        }
        if (second != null && secondPlace != null) {
            if (!withRewards.contains(second)) {
                giveCommand(second, secondPlace);
                withRewards.add(second);
            }
        }
        if (third != null && thirdPlace != null) {
            if (!withRewards.contains(third)) {
                giveCommand(third, thirdPlace);
                withRewards.add(third);
            }
        }
    }

    public void giveLevel() {
        for (Player player : top.keySet()) {
            int dmg = (int) Math.floor(top.get(player));
            int lvl = dmg / top.keySet().size();
            int newLvl = player.getLevel() + lvl;
            player.setLevel(newLvl);
            String msg = OPKingdomsCore.getInstance().getConfig().getString("Damage_To_Level_Message");
            assert msg != null;
            String newMsg = ChatColor.translateAlternateColorCodes('&', msg.replace("%level%", "" + lvl));
            player.sendMessage(newMsg);
        }
    }

    public void giveCommand(Player player, ConfigurationSection section) {
        for (String key : section.getKeys(false)) {
            String cmd = section.getString(key + ".Reward");
            String msg = section.getString(key + ".Message");
            assert cmd != null;
            dispatchCommand(cmd, player.getName());
            assert msg != null;
            String newMsg = ChatColor.translateAlternateColorCodes('&', msg);
            player.sendMessage(newMsg);
        }
    }

    public void dispatchCommand(String command, String name) {
        String cmd = command.replace("%player_name%", name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }
}
