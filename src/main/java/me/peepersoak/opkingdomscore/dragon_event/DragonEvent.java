package me.peepersoak.opkingdomscore.dragon_event;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class DragonEvent {

    private DragonEventData data;
    private final Random rand = new Random();
    private static BossBar dragonEventBossBar;

    public void startEvent() {
        data = new DragonEventData();
        String worldName = data.getConfig().getString(DragonStringpath.DRAGON_WORLD_NAME);
        String spawnLocation = data.getConfig().getString(DragonStringpath.DRAGON_SPAWN_LOCATION);
        List<String> endCrystalLocation = data.getConfig().getStringList(DragonStringpath.DRAGON_END_CRYSTAL_LOCATION);

        if (worldName == null) {
            System.out.println(ChatColor.RED + "Unable to launch event!! World name is missing!");
            return;
        } else if (spawnLocation == null) {
            System.out.println(ChatColor.RED + "Unable to launch event!! Dragon Spawn Location is missing!");
            return;
        }
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            System.out.println(ChatColor.RED + "Error, dragon world is missing!");
            return;
        }
        if (!Bukkit.getServer().getWorlds().contains(world)) {
            Bukkit.getServer().getWorlds().add(world);
        }

        if (data.getConfig().getString(DragonStringpath.DRAGON_EVENT_STATUS).equalsIgnoreCase("Alive")) {
            System.out.println(ChatColor.RED + "The event has already begun");
            return;
        }
        spawnDragon(spawnLocation, endCrystalLocation);
        data.write(DragonStringpath.DRAGON_EVENT_STATUS, "Alive");
    }

    public void spawnDragon(String spawnLocation, List<String> endCrystalLocation) {
        String message = OPKingdomsCore.getInstance().getConfig().getString("Dragon_Event_Message");
        assert message != null;
        String coloredMessage = ChatColor.translateAlternateColorCodes('&', message);
        Bukkit.broadcastMessage(coloredMessage);

        Location dragonSpawn = getLocation(spawnLocation);
        spawnEndCrystal(endCrystalLocation, dragonSpawn);
    }

    public boolean isEnderDragonAlive(World world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof EnderDragon) {
                return true;
            }
        }
        return false;
    }

    public Location getLocation(String location) {
        String[] split = location.split(";");
        String name = split[0];
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int z = Integer.parseInt(split[3]);
        return new Location(Bukkit.getWorld(name), x , y, z);
    }

    public void spawnEndCrystal(List<String> location, Location spawnLocation) {
        if (location.isEmpty()) {
            startEggAnimation(spawnLocation);
            return;
        }

        int count = location.size();

        new BukkitRunnable() {
            int c = 0;
            @Override
            public void run() {
                if (c >= count) {
                    startEggAnimation(spawnLocation);
                    this.cancel();
                    return;
                }
                String str = location.get(c);

                String[] split = str.split(";");
                World world = Bukkit.getWorld(split[0]);
                int x = Integer.parseInt(split[1]);
                int y = Integer.parseInt(split[2]);
                int z = Integer.parseInt(split[3]);

                if (world == null) {
                    System.out.println(ChatColor.RED + "Spawning End Crystal failed!! world is not valid");
                    return;
                }

                Location loc = new Location(world, x + 0.5 , y + 1, z + 0.5);

                loc.getWorld().strikeLightningEffect(loc);
                Objects.requireNonNull(loc.getWorld()).spawnEntity(loc, EntityType.ENDER_CRYSTAL);
                loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 25F, 1F);
                loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 5);
                c++;
            }
        }.runTaskTimer(OPKingdomsCore.getInstance(), 0, 10);
    }

    public void startEggAnimation(Location location) {
        if (!data.getConfig().getBoolean(DragonStringpath.DRAGON_PARTICLE)) {
            spawnEnderDragon(location);
            return;
        }
        int random = rand.nextInt(4) + 5;
        List<String> blockLocation = DragonEggData.getEggData();
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                location.getWorld().strikeLightning(location);
                count++;
                if (count > 4) {
                    this.cancel();
                    for (String str : blockLocation) {
                        Block block = getblockFromData(str, location);
                        FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData());
                        block.setType(Material.AIR);
                        float x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
                        float y = (float) -0.5 + (float)(Math.random() * ((1 - -1) + 1));
                        float z = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
                        fallingBlock.setVelocity(new Vector(x,y,z));
                        fallingBlock.setDropItem(false);
                        PersistentDataContainer dataContainer = fallingBlock.getPersistentDataContainer();
                        dataContainer.set(new NamespacedKey(OPKingdomsCore.getInstance(), "Egg_Falling_Block"), PersistentDataType.STRING, "FallingBlock");
                    }
                    location.getWorld().createExplosion(location.clone().add(0,3,0), 4.0F, false, false);
                    spawnEnderDragon(location);
                    return;
                }
                for (int i = 0; i < random; i++) {
                    Collections.shuffle(blockLocation);
                    Block block = getblockFromData(blockLocation.get(0), location);
                    if (block.getType() == Material.PURPLE_CONCRETE) return;
                    block.setType(Material.PURPLE_CONCRETE);
                    location.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, block.getLocation(), 2);
                    location.getWorld().playSound(block.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10F, 1F);
                }
            }
        }.runTaskTimer(OPKingdomsCore.getInstance(), 0, 10);
    }

    public Block getblockFromData(String data, Location location) {
        String[] firstSplit = data.split(" ");
        String[] main = firstSplit[1].split(";");
        int offX = Integer.parseInt(main[0]);
        int offY = Integer.parseInt(main[1]);
        int offZ = Integer.parseInt(main[2]);
        return location.getBlock().getRelative(offX, offY, offZ);
    }

    public void spawnEnderDragon(Location location) {
        Objects.requireNonNull(location.getWorld()).playSound(location, Sound.ENTITY_ENDER_DRAGON_GROWL, 25F, 1.0F);
       new BukkitRunnable() {
           @Override
           public void run() {
               EnderDragon dragon = (EnderDragon) location.getWorld().spawnEntity(location, EntityType.ENDER_DRAGON);
               double health = data.getConfig().getInt(DragonStringpath.DRAGON_HEALTH);
               dragon.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
               dragon.setHealth(health);
               if (data.getConfig().getBoolean(DragonStringpath.DRAGON_ALLOW_SKILL)) {
                   int cooldown = data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_COOLDOWN) * 20;
                   DragonSkill skill = new DragonSkill(dragon);
                   skill.runTaskTimerAsynchronously(OPKingdomsCore.getInstance(), 0,cooldown);
               }
               PersistentDataContainer data = dragon.getPersistentDataContainer();
               data.set(DragonStringpath.DRAGON_NAMESPACEDKEY, PersistentDataType.STRING, "DragonEvent");
               if (location.getWorld().getEnvironment() == World.Environment.THE_END) {
                   Objects.requireNonNull(dragon.getDragonBattle()).generateEndPortal(false);
               }
               dragonBossBar(dragon);
               dragon.setPhase(EnderDragon.Phase.CIRCLING);
           }
       }.runTask(OPKingdomsCore.getInstance());
    }

    public void dragonBossBar(EnderDragon dragon) {
        String dragonName = data.getConfig().getString(DragonStringpath.DRAGON_NAME);
        assert dragonName != null;
        String name = ChatColor.translateAlternateColorCodes('&', dragonName);

        dragonEventBossBar= Bukkit.createBossBar(name, BarColor.RED, BarStyle.SEGMENTED_12);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    World world = dragon.getWorld();
                    World pWorld = player.getWorld();

                    if (world != pWorld) {
                        dragonEventBossBar.removePlayer(player);
                        continue;
                    }
                    dragonEventBossBar.addPlayer(player);
                }
                if (dragon.isDead()) {
                    dragonEventBossBar.removeAll();
                    this.cancel();
                }
            }
        }.runTaskTimer(OPKingdomsCore.getInstance(), 0, 1);
    }

    public static BossBar getDragonEventBossBar() {
        return dragonEventBossBar;
    }
}
