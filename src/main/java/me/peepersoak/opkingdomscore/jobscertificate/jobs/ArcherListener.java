package me.peepersoak.opkingdomscore.jobscertificate.jobs;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.ArcherData;
import me.peepersoak.opkingdomscore.jobscertificate.data.WarriorData;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ArcherListener implements Listener {

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Projectile)) return;
        Projectile projectile = (Projectile) e.getDamager();
        if (!(projectile.getShooter() instanceof Player)) return;
        Player player = (Player) projectile.getShooter();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() != Material.BOW) return;
        if (!(e.getEntity() instanceof LivingEntity)) return;
        LivingEntity entity = (LivingEntity) e.getEntity();

        ArcherData data = new ArcherData();
        if (!JobsUtil.isJobCorrect(player, JobsString.ARCHER_PATH)) return;

        int level = JobsUtil.getPlayerJobLevel(player);

        if (JobsUtil.isWieldingBow(player)) {
            double bonus = 0;
            if (level >= 2) {
                if (level < 5 && player.getInventory().getItemInMainHand().getType() == Material.TRIDENT) return;
                bonus = data.getConfig().getDouble(JobsString.ARCHER_BONUS_DMG);
                if (JobsUtil.announce()) {
                    player.sendMessage(ChatColor.GREEN + "Bonus damage of " + bonus +
                            " was added as a level " + level + " Archer");
                }
            }
            if (JobsUtil.getPlayerJobLevel(player) >= 5) {
                double rawDamage = e.getDamage() + bonus;
                Random rand = new Random();
                int min = data.getConfig().getInt(JobsString.ARCHER_CRIT_MIN);
                int max = data.getConfig().getInt(JobsString.ARCHER_CRIT_MAX);
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
                                " to " + newHealth + " as it ignores protections as a " + level + " Archer");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Monster)) return;
        Monster monster = (Monster) e.getEntity();
        if (monster.getKiller() != null) {
            Player player = monster.getKiller();
            if (JobsUtil.isJobCorrect(player, JobsString.ARCHER_PATH)) {
                WarriorData data = new WarriorData();
                JobsUtil.addXPandIncome(player,
                        Objects.requireNonNull(data.getConfig().getConfigurationSection(JobsString.ARCHER_MOBS_SECTION)),
                        e.getEntity().getType().toString());
            }
            if (JobsUtil.shoudlDrop(player)) {
                JobsUtil.addMobDrop(player, e.getDrops());
            }
        }
        e.getDrops().clear();
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        if (!JobsUtil.hasJob(player)) return;
        if (!JobsUtil.isJobCorrect(player, JobsString.ARCHER_PATH)) return;
        int level = JobsUtil.getPlayerJobLevel(player);
        if (level < 3) return;
        if (e.isSneaking()) {
            archerSneak(player);
        }
    }

    public void archerSneak(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isSneaking()) {
                    this.cancel();
                    return;
                }
                player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, player.getLocation().add(0,0.2,0), 1, 0,0,0, 0);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 30, 0, true, true, false));
            }
        }.runTaskTimer(OPKingdomsCore.getInstance(), 0, 20);
    }
}
