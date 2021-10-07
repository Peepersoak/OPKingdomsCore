package me.peepersoak.opkingdomscore.jobscertificate.jobs;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.BrewerData;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class BrewerListener implements Listener {

    @EventHandler
    public void onCraft(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CRAFTING &&
        e.getClickedInventory().getType() != InventoryType.WORKBENCH) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();
        String mat = item.getType().toString().toLowerCase();

        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();

        if (!JobsUtil.isJobCorrect(player, JobsString.BREWER_PATH)) return;
        if (e.getSlot() != 0) return;
        BrewerData data = new BrewerData();
        ConfigurationSection section = data.getConfig().getConfigurationSection(JobsString.BREWER_CRAFT_XP);
        assert section != null;
        if (!JobsUtil.isBlock(mat, section)) return;
        JobsUtil.addXPandIncome(player, section, mat);
    }

    @EventHandler
    public void onGather(BlockBreakEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();

        BlockData data = block.getBlockData();
        if (!(data instanceof Ageable)) return;
        Ageable age = (Ageable) data;
        if(age.getAge() != age.getMaximumAge()) return;

        BrewerData brewerData = new BrewerData();
        ConfigurationSection section = brewerData.getConfig().getConfigurationSection(JobsString.BREWER_GATHER_XP);
        assert section != null;

        String mat = block.getType().toString().toLowerCase();

        if (!JobsUtil.isBlock(mat, section)) return;
        if (!JobsUtil.isJobCorrect(player, JobsString.BREWER_PATH)) return;
        JobsUtil.addXPandIncome(player, section, mat);
    }

    @EventHandler
    public void onThrow(ProjectileLaunchEvent e) {
        if (!(e.getEntity() instanceof ThrownPotion)) return;
        ThrownPotion potion = (ThrownPotion) e.getEntity();
        if (!(e.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) e.getEntity().getShooter();

        int level = JobsUtil.getPlayerJobLevel(player);
        ItemStack item = potion.getItem();

        if (!JobsUtil.isJobCorrect(player, JobsString.BREWER_PATH)) {
            player.sendMessage(ChatColor.RED + "You don't have the proper certificate to throw this!");
            e.setCancelled(true);
        }

        if (item.getType() == Material.LINGERING_POTION) {
            if (level < 3) {
                player.sendMessage(ChatColor.RED + "Your level is not high enough to throw this!!");
                player.sendMessage(ChatColor.RED + "Level Requirement: 3");
                e.setCancelled(true);
            }
        }

        if (item.getType() == Material.SPLASH_POTION) {
            if (level < 2) {
                player.sendMessage(ChatColor.RED + "Your level is not high enough to throw this!!");
                player.sendMessage(ChatColor.RED + "Level Requirement: 2");
                e.setCancelled(true);
            }
        }

        BrewerData data = new BrewerData();
        Random rand = new Random();

        if (level >= 4) {
            int chance = data.getConfig().getInt(JobsString.BREWER_DOUBLE_THROW_CHANCE);
            int random = rand.nextInt(100) + 1;
            if (random < chance) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ThrownPotion thrownPotion = player.launchProjectile(ThrownPotion.class);
                        thrownPotion.setItem(item);
                    }
                }.runTaskLater(OPKingdomsCore.getInstance(), 5);
                if (JobsUtil.announce()) {
                    player.sendMessage(ChatColor.GOLD + "Thrown another potion as a level " +
                            level + " Brewer");
                }
            }
        }

        if (level >= 5) {
            int chance = data.getConfig().getInt(JobsString.BREWER_POTION_RECOVERY_CHANCE);
            int random = rand.nextInt(100) + 1;
            if (random < chance) {
                e.setCancelled(true);
                ThrownPotion thrownPotion = player.launchProjectile(ThrownPotion.class);
                thrownPotion.setItem(item);
                if (JobsUtil.announce()) {
                    player.sendMessage(ChatColor.GOLD + "Potion was not consumed as a level " +
                            level + " Brewer");
                }
            }
        }
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() != Material.POTION) return;
        Player player =  e.getPlayer();
        int level = JobsUtil.getPlayerJobLevel(player);
        if (!JobsUtil.isJobCorrect(player, JobsString.BREWER_PATH)) {
            player.sendMessage(ChatColor.RED + "You don't have the proper certificate to drink this!");
            e.setCancelled(true);
            return;
        }
        if (level < 1) {
            player.sendMessage(ChatColor.RED + "Your level is not high enough to drink this!");
            player.sendMessage(ChatColor.RED + "Level Requirement: 1");
            e.setCancelled(true);
        }
    }
}
