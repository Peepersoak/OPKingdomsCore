package me.peepersoak.opkingdomscore.dragon_event;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TopDamager implements Listener {

    private HashMap<Player, Double> dragonEventTopDamager = new HashMap<>();
    private final DragonEventData data = new DragonEventData();
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

        if (player == null) return;

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

        Rewards rewards = new Rewards(dragonEventTopDamager, dragonKiller);
        rewards.setTop();
        rewards.sendEndEventMessage();

        dragonEventTopDamager = new HashMap<>();
        DragonEventData eventData = new DragonEventData();
        eventData.write(DragonStringpath.DRAGON_EVENT_STATUS, "Dead");
        resetDragonEgg(dragon, eventData);
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
        double health = dragon.getHealth();
        double maxHealth = dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        double points = health/maxHealth;

        if (dammage >= health) {
            bar.setProgress(0);
        } else {
            bar.setProgress(points);
        }
    }
}
