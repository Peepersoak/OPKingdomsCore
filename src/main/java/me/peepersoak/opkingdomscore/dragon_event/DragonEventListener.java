package me.peepersoak.opkingdomscore.dragon_event;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class DragonEventListener implements Listener {

    private HashMap<Player, Double> dragonEventTopDamager = new HashMap<>();
    private Player dragonKiller;

    @EventHandler
    public void onBlockChange(EntityChangeBlockEvent e){
        if (!(e.getEntity() instanceof FallingBlock)) return;
        FallingBlock block = (FallingBlock) e.getEntity();
        PersistentDataContainer dataContainer = block.getPersistentDataContainer();
        if (!dataContainer.has(new NamespacedKey(OPKingdomsCore.getInstance(), "Egg_Falling_Block"), PersistentDataType.STRING)) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onDragonDestroy(EntityExplodeEvent e) {
        if (!(e.getEntity() instanceof EnderDragon)) return;
        EnderDragon dragon = (EnderDragon) e.getEntity();
        PersistentDataContainer data = dragon.getPersistentDataContainer();
        if (!data.has(DragonStringpath.DRAGON_NAMESPACEDKEY, PersistentDataType.STRING)) return;
        e.blockList().clear();
    }

    @EventHandler
    public void onDragonDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof EnderDragon)) return;
        EnderDragon dragon = (EnderDragon) e.getEntity();
        PersistentDataContainer data = dragon.getPersistentDataContainer();
        if (!data.has(DragonStringpath.DRAGON_NAMESPACEDKEY, PersistentDataType.STRING)) return;

        if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING ||
        e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION ||
        e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            e.setCancelled(true);
        }
        updateBossBar(dragon, e.getFinalDamage());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof EnderDragon)) return;
        EnderDragon dragon = (EnderDragon) e.getEntity();
        PersistentDataContainer data = dragon.getPersistentDataContainer();
        if (!data.has(DragonStringpath.DRAGON_NAMESPACEDKEY, PersistentDataType.STRING)) return;

        Player player = null;

        if (e.getDamager() instanceof Player) {
            player = (Player) e.getDamager();
        } else if (e.getDamager() instanceof Projectile) {
            if (((Projectile)e.getDamager()).getShooter() instanceof Player) {
                player = (Player) ((Projectile)e.getDamager()).getShooter();
            }
        }

        if (player == null) {
            e.setCancelled(true);
            return;
        }

        dragonKiller = player;

        double damage = e.getFinalDamage();

        if (dragonEventTopDamager.containsKey(player)) {
            double newDamage = damage + dragonEventTopDamager.get(player);
            dragonEventTopDamager.put(player, newDamage);
        } else {
            dragonEventTopDamager.put(player, damage);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof EnderDragon)) return;
        EnderDragon dragon = (EnderDragon) e.getEntity();
        PersistentDataContainer data = dragon.getPersistentDataContainer();
        if (!data.has(DragonStringpath.DRAGON_NAMESPACEDKEY, PersistentDataType.STRING)) return;

        Fireworks fireworks = new Fireworks();
        Rewards rewards = new Rewards(dragonEventTopDamager, dragonKiller);
        rewards.setTop();
        rewards.sendEndEventMessage();

        dragonEventTopDamager = new HashMap<>();
        DragonEventData eventData = new DragonEventData();
        eventData.write(DragonStringpath.DRAGON_EVENT_STATUS, "Dead");
        resetDragonEgg(dragon, eventData);

        teleportAllPlayer(dragon.getWorld());
    }

    @EventHandler
    public void onDragonPhaseChange(EnderDragonChangePhaseEvent e) {
        EnderDragon dragon = e.getEntity();
        PersistentDataContainer data = dragon.getPersistentDataContainer();
        if (!data.has(DragonStringpath.DRAGON_NAMESPACEDKEY, PersistentDataType.STRING)) return;
        if (e.getCurrentPhase() == EnderDragon.Phase.LAND_ON_PORTAL) {
            castMassLightningStrike(dragon);
        }
    }

    public void castMassLightningStrike(EnderDragon dragon) {
        DragonEventData data = new DragonEventData();

        int distance = data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_AOE_DISTANCE);
        int interval = data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_AOE_INTERVAL);
        int witherAmp = data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_AOE_WITHER_AMPLIFIER);
        int threshold = data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_AOE_THRESHOLD);
        boolean allow = data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_AOE_ALLOW);
        boolean targetAll = data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_AOE_TARGET_ALL);

        double maxHealth = Objects.requireNonNull(dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue();
        double health = dragon.getHealth();

        if (health > (maxHealth*(threshold/100))) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                if (dragon.getPhase() != EnderDragon.Phase.LAND_ON_PORTAL &&
                dragon.getPhase() != EnderDragon.Phase.SEARCH_FOR_BREATH_ATTACK_TARGET &&
                dragon.getPhase() != EnderDragon.Phase.BREATH_ATTACK &&
                dragon.getPhase() != EnderDragon.Phase.ROAR_BEFORE_ATTACK) this.cancel();
                if (dragon.isDead()) this.cancel();

                if (!allow) return;
                List<Entity> entityList = dragon.getNearbyEntities(distance, distance, distance);
                if (entityList.isEmpty()) this.cancel();
                if (entityList.size() == 0) this.cancel();

                if (targetAll) {
                    for (Entity entity :entityList) {
                        entity.getWorld().strikeLightning(entity.getLocation());
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, witherAmp));
                        }
                    }
                } else {
                    Collections.shuffle(entityList);
                    Entity entity = entityList.get(0);
                    entity.getWorld().strikeLightning(entity.getLocation());
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, witherAmp));
                    }
                }
            }
        }.runTaskTimer(OPKingdomsCore.getInstance(), 0, interval);
    }

    public void teleportAllPlayer(World world) {
        int delay = OPKingdomsCore.getInstance().getConfig().getInt(DragonStringpath.DRAGON_END_EVENT_TP_DELAY) * 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                List<String> cmd = OPKingdomsCore.getInstance().getConfig().getStringList(DragonStringpath.DRAGON_END_EVENT_TP_COMMAND);
                for (Player player : world.getPlayers()) {
                    for (String str : cmd) {
                        dispatchCommand(str, player.getName());
                    }
                }
            }
        }.runTaskLater(OPKingdomsCore.getInstance(), delay);
    }

    public void dispatchCommand(String command, String name) {
        String cmd = command.replace("%player_name%", name);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }

    public void resetDragonEgg(EnderDragon dragon, DragonEventData data) {
        if (data.getConfig().getBoolean(DragonStringpath.DRAGON_PARTICLE)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (dragon.isDead()) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                for (String eggData : DragonEggData.getEggData()) {
                                    getBlockFromData(eggData, "Place");
                                }
                            }
                        }.runTaskLater(OPKingdomsCore.getInstance(), 10 * 20);
                        this.cancel();
                    }
                }
            }.runTaskTimer(OPKingdomsCore.getInstance(),0,20);
        }
    }

    public void getBlockFromData(String data, String status) {
        String[] firstSplit = data.split(" ");
        String[] main = firstSplit[1].split(";");
        int offX = Integer.parseInt(main[0]);
        int offY = Integer.parseInt(main[1]);
        int offZ = Integer.parseInt(main[2]);
        BlockData blockData = Bukkit.createBlockData(main[3]);
        Block block = getLocation().getBlock().getRelative(offX, offY, offZ);

        if (status.equalsIgnoreCase("Place")) {
            block.setBlockData(blockData);
        } else if (status.equalsIgnoreCase("Remove")) {
            block.setType(Material.AIR);
        }
    }

    public Location getLocation() {
        DragonEventData data = new DragonEventData();
        if (data.getConfig().getString(DragonStringpath.DRAGON_SPAWN_LOCATION) == null) {
            System.out.println(ChatColor.RED + "Error, dragon spawn location is missing");
        }
        String[] split = data.getConfig().getString(DragonStringpath.DRAGON_SPAWN_LOCATION).split(";");
        String name = split[0];
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int z = Integer.parseInt(split[3]);
        return new Location(Bukkit.getWorld(name), x , y, z);
    }

    public void updateBossBar(EnderDragon dragon, double dammage) {
        BossBar bar = DragonEvent.getDragonEventBossBar();
        if (bar == null) return;
        double health = dragon.getHealth() - dammage;
        double maxHealth = dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double points = health/maxHealth;

        if (health < 0) {
            bar.setProgress(0);
        } else {
            bar.setProgress(points);
        }
    }

    public void healDragon(EnderDragon dragon) {
        BossBar bar = DragonEvent.getDragonEventBossBar();
        if (bar == null) return;

        double maxHealth = dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double heal = maxHealth * 0.03;
        double health = dragon.getHealth() + heal;

        if (health >= maxHealth) {
            health = maxHealth;
        }

        double points = health/maxHealth;
        dragon.setHealth(health);

        bar.setProgress(points);
    }
}
