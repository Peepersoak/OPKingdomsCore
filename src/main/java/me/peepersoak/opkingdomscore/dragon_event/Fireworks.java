package me.peepersoak.opkingdomscore.dragon_event;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Fireworks {

    public Fireworks() {
        createFireworks();
    }

    public void createFireworks() {
        DragonEventData data = new DragonEventData();
        String locData = data.getConfig().getString(DragonStringpath.DRAGON_SPAWN_LOCATION);
        assert locData != null;
        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                if (count == 30) this.cancel();
                Location location = getLocation(locData);
                Firework fw = (Firework) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.FIREWORK);
                FireworkMeta meta = fw.getFireworkMeta();
                Random rand = new Random();
                boolean hasFlicker = rand.nextBoolean();
                if (hasFlicker) {
                    meta.addEffect(FireworkEffect.builder().withColor(getColor()).withColor(getColor()).with(FireworkEffect.Type.BALL_LARGE).withFlicker().build());
                } else {
                    meta.addEffect(FireworkEffect.builder().withColor(getColor()).withColor(getColor()).with(FireworkEffect.Type.BALL_LARGE).build());
                }
                meta.setPower(1);
                fw.setFireworkMeta(meta);
                count++;
            }
        }.runTaskTimer(OPKingdomsCore.getInstance(), 0, 20);
    }

    public Location getLocation(String location) {
        String[] split = location.split(";");
        String name = split[0];
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int z = Integer.parseInt(split[3]);
        Location loc = new Location(Bukkit.getWorld(name), x , y, z);

        List<Integer> range = new ArrayList<>(
                Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5));

        Collections.shuffle(range);
        int offX = range.get(0);
        Collections.shuffle(range);
        int offZ = range.get(0);

        return  loc.getBlock().getRelative(offX, 0, offZ).getLocation();
    }

    public Color getColor() {
        List<Color> color = new ArrayList<>();
        color.add(Color.RED);
        color.add(Color.GREEN);
        color.add(Color.AQUA);
        color.add(Color.BLUE);
        color.add(Color.LIME);
        color.add(Color.OLIVE);
        color.add(Color.ORANGE);
        color.add(Color.PURPLE);
        color.add(Color.WHITE);
        Collections.shuffle(color);
        return color.get(0);
    }
}
