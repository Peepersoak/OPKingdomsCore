package me.peepersoak.opkingdomscore.jobscertificate.commands;

import me.peepersoak.opkingdomscore.jobscertificate.GUI.converter.ConverterGUI;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.upgrade.UpgradeGUI;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.jobsGUI.JobsGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                String cmd = args[0];
                if (!player.hasPermission("OPK.jobs")) {
                    player.sendMessage(ChatColor.RED + "Requirement not met!");
                    return false;
                }
                if (cmd.equalsIgnoreCase("apply")) {
                    if (JobsUtil.hasJob(player) && !player.isOp()) {
                        player.sendMessage(ChatColor.RED + "You already have a job!! Go away and work!!");
                        return false;
                    }
                    JobsGUI jobsGUI = new JobsGUI();
                    player.openInventory(jobsGUI.openMainGUI());
                }
                if (!JobsUtil.hasJob(player)) {
                    player.sendMessage(ChatColor.RED + "You don't have any jobs yet!!");
                    return false;
                }
                if (cmd.equalsIgnoreCase("upgrade")) {
                    UpgradeGUI gui = new UpgradeGUI();
                    player.openInventory(gui.openGUI(player));
                }
                if (cmd.equalsIgnoreCase("convert")) {
                    ConverterGUI gui = new ConverterGUI();
                    player.openInventory(gui.openGUI(player));
                }
                if (cmd.equalsIgnoreCase("stats")) {
                    sendPlayerJobsStatus(player);
                }
            }
            if (args.length == 4) {
                String cmd = args[0];
                String stat = args[1];
                if (cmd.equalsIgnoreCase("set")) {
                    String name = args[2];
                    Player target = Bukkit.getPlayer(name);
                    if (target == null || !target.isOnline()) {
                        player.sendMessage(ChatColor.RED + "Player not found!");
                        return false;
                    }
                    if (stat.equalsIgnoreCase("xp")) {
                        double ammount = 0;
                        try {
                            ammount = Double.parseDouble(args[3]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "Please enter a valid number");
                        }
                        JobsUtil.addXPToPlayer(target, ammount);
                        player.sendMessage(ChatColor.GREEN + "You set the Job XP of " + ChatColor.GOLD + target.getName() +
                                ChatColor.GREEN + " to " + ChatColor.AQUA + "" + ammount);
                    }
                    if (stat.equalsIgnoreCase("level")) {
                        int ammount = 0;
                        try {
                            ammount = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "Please enter a valid number");
                        }
                        JobsUtil.changeJobLevel(player, ammount);
                        player.sendMessage(ChatColor.GREEN + "You set the Job Level of " + ChatColor.GOLD + target.getName() +
                                ChatColor.GREEN + " to " + ChatColor.AQUA + "" + ammount);
                    }
                    if (stat.equalsIgnoreCase("token")) {
                        int ammount = 0;
                        try {
                            ammount = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatColor.RED + "Please enter a valid number");
                        }
                        JobsUtil.setPlayerToken(player, ammount);
                        player.sendMessage(ChatColor.GREEN + "You set the Job Token of " + ChatColor.GOLD + target.getName() +
                                ChatColor.GREEN + " to " + ChatColor.AQUA + "" + ammount);
                    }
                }
            }
        }
        return false;
    }

    public void sendPlayerJobsStatus(Player player) {
        String title = JobsUtil.getPlayerJobTitle(player);
        String separator = ChatColor.GREEN + "=======================";
        player.sendMessage(separator);
        player.sendMessage("");
        player.sendMessage(ChatColor.GOLD + "Name: " + ChatColor.AQUA + player.getName());
        player.sendMessage(ChatColor.GOLD + "Job Certificate: " + ChatColor.AQUA + title);
        player.sendMessage(ChatColor.GOLD + "Job Level: " + ChatColor.AQUA + JobsUtil.getPlayerJobLevel(player));
        player.sendMessage(ChatColor.GOLD + "Job Experience: " + ChatColor.AQUA + JobsUtil.getPlayerJobXP(player));
        player.sendMessage(ChatColor.GOLD + "Next Level XP Needed: " + ChatColor.AQUA + JobsUtil.getPlayerJobXPTarget(player));
        player.sendMessage(ChatColor.GOLD + "Token: " + ChatColor.AQUA + JobsUtil.getPlayerToken(player));
        player.sendMessage(ChatColor.GOLD + "Next Level Token Needed: " + ChatColor.AQUA + JobsUtil.getTokenNeeded(player));
        player.sendMessage("");
        player.sendMessage(separator);
    }
}
