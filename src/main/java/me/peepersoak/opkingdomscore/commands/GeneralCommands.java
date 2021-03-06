package me.peepersoak.opkingdomscore.commands;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.deathspawn.DeathSpawnData;
import me.peepersoak.opkingdomscore.dragon_event.DragonEvent;
import me.peepersoak.opkingdomscore.dragon_event.GUI.main.MainGUI;
import me.peepersoak.opkingdomscore.schedule.Events;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GeneralCommands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;

        if (sender instanceof Player) {
            player = (Player) sender;
            if (!player.hasPermission("OPK.admin")) {
                player.sendMessage(ChatColor.RED + "You are not allowed to use this!!");
                return false;
            }
        }

        if (args.length < 1) {
            if (player != null) {
                player.sendMessage(ChatColor.AQUA + "============================");
                player.sendMessage(ChatColor.GREEN + "Usage:");
                player.sendMessage(ChatColor.GOLD + "/OPkingdoms dragon_event" + ChatColor.GREEN + " : Opens a GUI Settings");
                player.sendMessage(ChatColor.GOLD + "/OPKingdoms launch" + ChatColor.GREEN + " : Launch the dragon event");
                player.sendMessage(ChatColor.GOLD + "/OPKingdoms reload" + ChatColor.GREEN + " : Reloads the config");
                player.sendMessage(ChatColor.AQUA + "============================");
            }
        }

        if (args.length == 1) {
            String cmd = args[0];
            DragonEvent event = new DragonEvent();
            if (player != null) {
                if (cmd.equalsIgnoreCase("dragon_event")) {
                    MainGUI gui = new MainGUI();
                    player.openInventory(gui.openGUI());
                }
                if (cmd.equalsIgnoreCase("launch")) {
                    event.startEvent();
                }
                if (cmd.equalsIgnoreCase("reload")) {
                    OPKingdomsCore.getInstance().reloadConfig();
                    player.sendMessage(ChatColor.GREEN + "Config has been reloaded");
                }
                if (cmd.equalsIgnoreCase("events")) {
                    Events.getEvents(player);
                }
                if (cmd.equalsIgnoreCase("set_death_location")) {
                    DeathSpawnData data = new DeathSpawnData();
                    data.getConfig().set("Death_Spawn.Location", getLocation(player.getLocation()));
                    data.saveFileConfig();
                    player.sendMessage(ChatColor.GREEN + "Death Location has been set");
                }
            } else {
                if (cmd.equalsIgnoreCase("launch")) {
                    event.startEvent();
                }
                if (cmd.equalsIgnoreCase("reload")) {
                    OPKingdomsCore.getInstance().reloadConfig();
                    System.out.println(ChatColor.GREEN + "Config has been reloaded");
                }
            }
        }
        return false;
    }

    public String getLocation(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        String world = Objects.requireNonNull(location.getWorld()).getName();
        return world + ";" + x + ";" + y + ";" + z;
    }
}
