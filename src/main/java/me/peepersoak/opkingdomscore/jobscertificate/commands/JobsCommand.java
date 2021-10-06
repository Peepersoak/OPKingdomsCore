package me.peepersoak.opkingdomscore.jobscertificate.commands;

import me.peepersoak.opkingdomscore.jobscertificate.GUI.converter.ConverterGUI;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.upgrade.UpgradeGUI;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.jobsGUI.JobsGUI;
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
                    if (JobsUtil.hasJob(player)) {
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
        player.sendMessage(ChatColor.GOLD + "Next Level XP Needed: " + ChatColor.AQUA + JobsUtil.getXPNeeded(title, JobsUtil.getPlayerJobLevel(player)));
        player.sendMessage(ChatColor.GOLD + "Token: " + ChatColor.AQUA + JobsUtil.getPlayerToken(player));
        player.sendMessage(ChatColor.GOLD + "Next Level Token Needed: " + ChatColor.AQUA + JobsUtil.getTokenNeeded(player));
        player.sendMessage("");
        player.sendMessage(separator);
    }
}
