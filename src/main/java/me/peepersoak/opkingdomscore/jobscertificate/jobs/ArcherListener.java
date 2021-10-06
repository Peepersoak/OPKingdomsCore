package me.peepersoak.opkingdomscore.jobscertificate.jobs;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.ArcherData;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

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
        damageEntity(e, player, data.getConfig());

        double damage = e.getFinalDamage();
        double health = entity.getHealth();

        if (damage >= health) {
            JobsUtil.addXPandIncome(player, Objects.requireNonNull(data.getConfig().getConfigurationSection(JobsString.ARCHER_MOBS_SECTION)), entity.getType().toString());
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        if (!JobsUtil.isJobCorrect(player, JobsString.ARCHER_PATH)) return;
        int level = JobsUtil.getPlayerJobLevel(player);
        if (level < 4) return;
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

    public void damageEntity(EntityDamageByEntityEvent event, Player player, FileConfiguration config) {
        int level = JobsUtil.getPlayerJobLevel(player);
        double bonusDamage = 0;
        if (level >= 1) {
            bonusDamage = config.getInt(JobsString.ARCHER_BONUS_DMG_1);
        }
        if (level >= 3) {
            bonusDamage = config.getInt(JobsString.ARCHER_BONUS_DMG_3);
        }
        if (level >= 5) {
            bonusDamage = config.getInt(JobsString.ARCHER_BONUS_DMG_5);
        }
        event.setDamage(event.getDamage() + bonusDamage);

        if (JobsUtil.announce()) {
            player.sendMessage(ChatColor.GREEN + "Bonus damage of " + bonusDamage +
                    " was added as a level " + level + " Archer");
        }
    }
}
