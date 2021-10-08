package me.peepersoak.opkingdomscore.jobscertificate.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class JobsAutoComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return null;
        Player player = (Player) sender;

        if (!player.isOp() && !player.hasPermission("OPK.admin")) return null;

        if (args.length == 2) {
            String cmd = args[0];
            if (cmd.equalsIgnoreCase("set")) {
                return getSetCommands();
            }
            return null;
        }

        if (args.length == 3) {
            String cmd = args[0];
            String second = args[1];
            if (cmd.equalsIgnoreCase("set")) {
                if (getSetCommands().contains(second)) {
                    return getPlayerName();
                }
            }
        }

        if (args.length == 4) {
            String cmd = args[0];
            String second = args[1];
            if (cmd.equalsIgnoreCase("set")) {
                if (getSetCommands().contains(second)) {
                    List<String> str = new ArrayList<>();
                    str.add(ChatColor.GRAY + "[Ammount]");
                    return str;
                }
            }
        }
        return generalCommands();
    }

    public List<String> getPlayerName() {
        List<String> name = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            name.add(player.getName());
        }
        return name;
    }

    public List<String> getSetCommands() {
        List<String> str = new ArrayList<>();
        str.add("level");
        str.add("xp");
        str.add("token");
        return str;
    }

    public List<String> generalCommands() {
        List<String> str = new ArrayList<>();
        str.add("apply");
        str.add("upgrade");
        str.add("convert");
        str.add("stats");
        str.add("set");
        return str;
    }
}
