package me.peepersoak.opkingdomscore.jobscertificate.jobs;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.WarriorData;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.*;

public class WarriorListener implements Listener {

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getDamager();
        if (!JobsUtil.isJobCorrect(player, JobsString.WARRIOR_PATH)) return;

        if (!(e.getEntity() instanceof LivingEntity)) return;
        LivingEntity entity = (LivingEntity) e.getEntity();

        WarriorData data = new WarriorData();

        int level = JobsUtil.getPlayerJobLevel(player);

        if (JobsUtil.isWieldingSword(player)) {
            double bonus = 0;
            if (level >= 2) {
                bonus = data.getConfig().getDouble(JobsString.WARRIOR_SWORD_BONUS);
                if (JobsUtil.announce()) {
                    player.sendMessage(ChatColor.GREEN + "Bonus damage of " + bonus +
                            " was added as a level " + level + " Warrior");
                }
            }
            if (JobsUtil.getPlayerJobLevel(player) >= 5) {
                double rawDamage = e.getDamage() + bonus;
                Random rand = new Random();
                int min = data.getConfig().getInt(JobsString.WARRIOR_CRIT_MIN);
                int max = data.getConfig().getInt(JobsString.WARRIOR_CRIT_MAX);
                int chance = rand.nextInt(max - min) + min;
                int random = rand.nextInt(100) + 1;
                double health = entity.getHealth();
                double newHealth = health - rawDamage;
                if (random <= chance) {
                    if (rawDamage >= health) newHealth = 1;
                    e.setDamage(0);
                    entity.setHealth(newHealth);
                    player.getWorld().playSound(entity.getLocation(), Sound.ITEM_TRIDENT_RIPTIDE_1, 5, 1);
                    if (JobsUtil.announce()) {
                        player.sendMessage(ChatColor.GOLD + "Critical Damage!! entity health from " + health +
                                " to " + newHealth + " as it ignores protections as a " + level + " Warrior");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Monster)) return;
        Monster monster = (Monster) e.getEntity();
        if (monster.getKiller() != null) {
            Player player = monster.getKiller();
            if (JobsUtil.isJobCorrect(player, JobsString.WARRIOR_PATH)) {
                WarriorData data = new WarriorData();
                JobsUtil.addXPandIncome(player,
                        Objects.requireNonNull(data.getConfig().getConfigurationSection(JobsString.WARRIOR_MOBS_SECTION)),
                        e.getEntity().getType().toString());
            }
            if (JobsUtil.shoudlDrop(player)) {
                JobsUtil.addMobDrop(player, e.getDrops());
            }
        }
        e.getDrops().clear();
    }

    @EventHandler
    public void onDefense(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if (!JobsUtil.isJobCorrect(player, JobsString.WARRIOR_PATH)) return;
        int level = JobsUtil.getPlayerJobLevel(player);
        WarriorData data = new WarriorData();
        if (level >= 3) {
            double damage = e.getFinalDamage();
            double reductionPercentage = data.getConfig().getInt(JobsString.WARRIOR_DEFENSE_BONUS);
            double reduction = damage * reductionPercentage/100;
            double newDamage = damage - reduction;
            e.setDamage(newDamage);
            if (JobsUtil.announce()) {
                player.sendMessage(ChatColor.GOLD + "" + "Damage is reduced from " +
                        Math.floor(damage) + " to " + Math.floor(newDamage) + ", as a level " + level + " Warrior");
            }
        }
    }
}
