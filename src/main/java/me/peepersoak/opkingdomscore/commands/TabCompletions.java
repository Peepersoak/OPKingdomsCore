package me.peepersoak.opkingdomscore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompletions implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return tabs();
    }

    public List<String> tabs() {
        List<String> str = new ArrayList<>();
        str.add("Dragon_Event");
        str.add("Reload");
        str.add("Events");
        return str;
    }
}
