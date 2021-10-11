package me.peepersoak.opkingdomscore.jobscertificate.commands;

import me.peepersoak.opkingdomscore.jobscertificate.GUI.change.ChangeGUI;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.converter.ConverterGUI;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.upgrade.UpgradeGUI;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobMessage;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import me.peepersoak.opkingdomscore.jobscertificate.GUI.jobsGUI.JobsGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class JobsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        JobMessage message = new JobMessage();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                String cmd = args[0];
                if (!player.hasPermission("OPK.jobs")) {
                    String raw = message.getConfig().getString("Job_Apply_No_Permission");
                    assert raw != null;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', raw));
                    return false;
                }
                if (cmd.equalsIgnoreCase("apply")) {
                    if (JobsUtil.hasJob(player) && !player.isOp()) {
                        String raw = message.getConfig().getString("Job_Apply_Other_Job");
                        assert raw != null;
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', raw));
                        return false;
                    }
                    JobsGUI jobsGUI = new JobsGUI();
                    player.openInventory(jobsGUI.openMainGUI(player));
                    return false;
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
                if (cmd.equalsIgnoreCase("change")) {
                    ChangeGUI gui = new ChangeGUI();
                    player.openInventory(gui.openGUI(player));
                }
            }
            if (args.length == 2) {
                String cmd = args[0];
                String name = args[1];
                if (cmd.equalsIgnoreCase("remove")) {
                    if (!player.isOp()) {
                        player.sendMessage(ChatColor.RED + "You don't have permission to do this!");
                        return false;
                    }
                    Player target = Bukkit.getServer().getPlayer(name);
                    if (target == null) {
                        player.sendMessage(ChatColor.RED + "Player was not found!");
                        return false;
                    }
                    JobsUtil.removeCertificate(target);
                    player.sendMessage(ChatColor.GREEN + name + " certificate was remove!");
                    return false;
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
                    if (!JobsUtil.hasJob(player)) {
                        player.sendMessage(ChatColor.RED + name + " does not have any job!");
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
        JobMessage message = new JobMessage();
        List<String> msg = message.getConfig().getStringList("Job_Status.Messages");
        for (String str : msg) {
            String raw = str.replace("%player_name%", player.getName())
                    .replace("%player_job%", "" + JobsUtil.getPlayerJobTitle(player))
                    .replace("%player_job_level%", "" + JobsUtil.getPlayerJobLevel(player))
                    .replace("%player_job_xp%", "" + JobsUtil.getPlayerJobXP(player))
                    .replace("%xp_requirements%", "" + JobsUtil.getPlayerJobXPTarget(player))
                    .replace("%player_token%", "" + JobsUtil.getPlayerToken(player))
                    .replace("%token_requirements%", "" + JobsUtil.getTokenNeeded(player));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', raw));
        }
    }
}
