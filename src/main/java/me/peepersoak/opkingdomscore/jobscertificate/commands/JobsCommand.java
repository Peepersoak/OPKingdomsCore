package me.peepersoak.opkingdomscore.jobscertificate.commands;

import me.peepersoak.opkingdomscore.jobscertificate.converter.ConverterGUI;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import me.peepersoak.opkingdomscore.jobscertificate.jobsGUI.JobsGUI;
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
                if (cmd.equalsIgnoreCase("apply")) {
                    if (!player.hasPermission("OPK.jobs")) {
                        player.sendMessage(ChatColor.RED + "Requirement not met!");
                        return false;
                    }
                    JobsGUI jobsGUI = new JobsGUI();
                    player.openInventory(jobsGUI.openMainGUI());
                }
                if (cmd.equalsIgnoreCase("upgrade")) {
                    String title = JobsUtil.getPlayerJobTitle(player);
                    double playerXP = JobsUtil.getPlayerJobXP(player);
                    int level = JobsUtil.getPlayerJobLevel(player);
                    int playerToken = JobsUtil.getPlayerToken(player);
                    int xpTarget = JobsUtil.getXPNeeded(title, level);
                    int tokenNeeded = JobsUtil.getTokenNeeded(player);

                    if (playerXP < xpTarget) {
                        player.sendMessage(ChatColor.RED + "You did not meet the xp requirement to upgrade your job!");
                        player.sendMessage(ChatColor.RED + "Current XP: " + ChatColor.AQUA + playerXP);
                        player.sendMessage(ChatColor.RED + "XP Needed: " + ChatColor.AQUA + xpTarget);
                        return false;
                    }

                    if (playerToken < tokenNeeded) {
                        player.sendMessage(ChatColor.RED + "You don't have enough token to upgrade your job!");
                        return false;
                    }
                    JobsUtil.changeLevel(player, level, JobsUtil.getXPNeeded(title, level));
                    String rawMsg = JobsUtil.getLevelUPRawMessage(title);
                    assert rawMsg != null;
                    assert title != null;
                    String msg = ChatColor.translateAlternateColorCodes('&', rawMsg)
                            .replace("%job_level%", "" + level + 1)
                            .replace("%job_title%", title);
                    sendLevelUPMessage(player, msg);
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

    public void sendLevelUPMessage(Player player, String msg) {
        String separator = ChatColor.AQUA + "=======================";
        player.sendMessage(separator);
        player.sendMessage("");
        player.sendMessage(msg);
        player.sendMessage("");
        player.sendMessage(separator);
    }
}
