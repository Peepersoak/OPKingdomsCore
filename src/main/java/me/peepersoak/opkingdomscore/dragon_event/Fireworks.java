package me.peepersoak.opkingdomscore.dragon_event;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.*;

public class Fireworks {

    public Fireworks(Location location) {
        createFireworks(location);
    }

    public void createFireworks(Location location) {
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
