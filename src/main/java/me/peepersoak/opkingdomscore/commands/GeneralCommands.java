package me.peepersoak.opkingdomscore.commands;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.dragon_event.DragonEvent;
import me.peepersoak.opkingdomscore.dragon_event.GUI.GUICreator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                    GUICreator gui = new GUICreator();
                    gui.createInventory();
                    player.openInventory(gui.getInv());
                }
                if (cmd.equalsIgnoreCase("launch")) {
                    event.startEvent();
                }
                if (cmd.equalsIgnoreCase("reload")) {
                    OPKingdomsCore.getInstance().reloadConfig();
                    player.sendMessage(ChatColor.GREEN + "Config has been reloaded");
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
}
