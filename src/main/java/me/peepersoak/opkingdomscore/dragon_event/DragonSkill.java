package me.peepersoak.opkingdomscore.dragon_event;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class DragonSkill extends BukkitRunnable {

    public DragonSkill(EnderDragon dragon) {
        this.dragon = dragon;
        setDefaults();
    }

    private final EnderDragon dragon;

    private int playerPercentageStrike;
    private int skillCastDistance;
    private double limit;

    private final Random rand = new Random();

    private List<String> skill;

    public void setDefaults() {
        DragonEventData data = new DragonEventData();
        playerPercentageStrike = data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_PLAYER_PERCENTAGE);
        skillCastDistance = data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_DISTANCE);
        int healthThreshold = data.getConfig().getInt(DragonStringpath.DRAGON_HEALTH_THRESHOLD);

        double maxHealth = dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        limit = (healthThreshold /100) * maxHealth;

        skill = new ArrayList<>();
        if (data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_LIGHTNINGSTRIKE)) {
            skill.add("Lightning_Strike");
        }
        if (data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_EXPLOSION)) {
            skill.add("Explosion");
        }
        if (data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_WITHER)) {
            skill.add("Wither");
        }
        if (data.getConfig().getBoolean(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_ALLOW)) {
            skill.add("Guardian");
        }
    }

    public void castSkill(Player player, String randomSkill) {
        if (dragon.getHealth() <= 0) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                switch (randomSkill) {
                    case "Lightning_Strike":
                        player.getWorld().strikeLightning(player.getLocation());
                        break;
                    case "Explosion":
                        float damage = rand.nextInt(8) + 4;
                        player.getWorld().createExplosion(player.getLocation(), damage, false, false);
                        break;
                    case "Wither":
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 400, 3));
                        break;
                    case "Guardian":
                        DragonEventData data = new DragonEventData();
                        int health = data.getConfig().getInt(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_HEALTH);
                        int dmg = data.getConfig().getInt(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_DAMAGE);
                        int duration = data.getConfig().getInt(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_DURATION);
                        int chance = data.getConfig().getInt(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_CHANCE);
                        String name = data.getConfig().getString(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_NAME);

                        if (rand.nextInt(100) > chance) return;

                        WitherSkeleton skel = (WitherSkeleton) player.getWorld().spawnEntity(player.getLocation(), EntityType.WITHER_SKELETON);

                        Objects.requireNonNull(skel.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
                        Objects.requireNonNull(skel.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(dmg);
                        skel.setCustomNameVisible(true);
                        skel.setCustomName(ChatColor.RED + "" + ChatColor.BOLD + name);
                        skel.setRemoveWhenFarAway(true);
                        skel.setHealth(health);
                        skel.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1));
                        removeGuardian(skel, duration);
                        break;
                }
            }
        }.runTask(OPKingdomsCore.getInstance());
    }

    public void removeGuardian(WitherSkeleton skel, int duration) {
        int ticks = duration * 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                skel.remove();
            }
        }.runTaskLater(OPKingdomsCore.getInstance(), ticks);
    }

    public boolean shouldCast() {
        double health = dragon.getHealth();
        return health <= limit;
    }

    @Override
    public void run() {
        if (dragon.isDead()) {
            this.cancel();
            return;
        }
        if (!shouldCast()) {
            return;
        }
        int numberOfPlayer = dragon.getWorld().getPlayers().size();
        int numberOfStrikes = (int) Math.ceil((playerPercentageStrike/100) * numberOfPlayer);
        List<Player> playerList = dragon.getWorld().getPlayers();

        if (playerList.size() == 0) return;

        if (skill.size() <= 0) return;
        Collections.shuffle(skill);
        String randomSkill = skill.get(0);

        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                if (count >= numberOfStrikes) {
                    this.cancel();
                    return;
                }
                Collections.shuffle(playerList);
                Player player = playerList.get(0);
                double distance = dragon.getLocation().distance(player.getLocation());
                if (distance <= skillCastDistance) {
                    castSkill(player, randomSkill);
                }
                count++;
            }
        }.runTaskTimerAsynchronously(OPKingdomsCore.getInstance(),0,5);
    }
}
