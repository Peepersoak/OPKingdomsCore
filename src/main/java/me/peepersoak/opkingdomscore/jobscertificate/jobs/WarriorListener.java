package me.peepersoak.opkingdomscore.jobscertificate.jobs;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.WarriorData;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.w3c.dom.Attr;

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
        double bonus = 0;

        int level = JobsUtil.getPlayerJobLevel(player);

        boolean addBonus = false;

        if (JobsUtil.isWieldingSword(player)) {
            if (level >= 1) {
                bonus = data.getConfig().getDouble(JobsString.WARRIOR_SWORD_BONUS);
                addBonus = true;
            }
        }

        if (addBonus) {
            double damage = e.getDamage() + bonus;
            if (JobsUtil.getPlayerJobLevel(player) >= 5) {
                damage = damage * 2;
                // Add Track Here
                addMobTrack(player.getInventory().getItemInMainHand());
            }
            if (JobsUtil.announce()) {
                player.sendMessage(ChatColor.GOLD + "Damage increase from " + Math.floor(e.getDamage()) +
                        " to " + Math.floor(damage) + " as a level " + level + " Warrior");
            }
            e.setDamage(e.getDamage() + bonus);
        }

        double damage = e.getFinalDamage();
        double health = entity.getHealth();

        if (damage >= health) {
            JobsUtil.addXPandIncome(player,
                    Objects.requireNonNull(data.getConfig().getConfigurationSection(JobsString.WARRIOR_MOBS_SECTION)),
                    entity.getType().toString());
        }
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

    public void addMobTrack(ItemStack item) {
        WarriorData warriorData = new WarriorData();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        PersistentDataContainer data = meta.getPersistentDataContainer();

        int killCount = 1;

        if (data.get(JobsString.WARRIOR_KILL_COUNT, PersistentDataType.INTEGER) != null) {
            killCount = data.get(JobsString.WARRIOR_KILL_COUNT, PersistentDataType.INTEGER) + 1;
        }

        data.set(JobsString.WARRIOR_KILL_COUNT, PersistentDataType.INTEGER, killCount);

        System.out.println(killCount);

        int ratio = warriorData.getConfig().getInt(JobsString.WARRIOR_CRIT_CHANCE_RATIO);
        int ammount = killCount/ratio;

        AttributeModifier modifier;

        if (meta.hasAttributeModifiers()) {
            Collection<AttributeModifier> attributeModifierList = meta.getAttributeModifiers(Attribute.GENERIC_ATTACK_SPEED);
            if (attributeModifierList == null) return;
            for (AttributeModifier am : attributeModifierList) {
                if (am.getName().equalsIgnoreCase("Warrior.Knockback")) {
                    modifier = am;
                    meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier);
                    break;
                }
            }
        }

        modifier = new AttributeModifier(UUID.randomUUID(), "Warrior.Knockback", killCount, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier);

        item.setItemMeta(meta);
    }
}
