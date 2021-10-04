package me.peepersoak.opkingdomscore.dragon_event.GUI.aoe_lightning;

import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AOELightningButtonFactory {

    private ItemStack item;
    private String buttonType;
    private List<String> lore;
    private String buttonName;

    public ItemStack createButton(String buttonType) {
        this.buttonType = buttonType;
        setItem();
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + buttonName);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public void setItem() {
        DragonEventData data = new DragonEventData();
        lore = new ArrayList<>();
        switch (buttonType) {
            case "Allow":
                item = new ItemStack(Material.LIGHTNING_ROD);
                buttonName = "Use Lightning Skill";
                lore.add("");
                lore.add(ChatColor.RED + "Allow dragon to use this skill");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_AOE_ALLOW));
                break;
            case "Interval":
                item = new ItemStack(Material.CLOCK);
                buttonName = "Change AOE Interval";
                lore.add("");
                lore.add(ChatColor.RED + "Change the skill interval");
                lore.add(ChatColor.RED + "How frequent the skill will be in Ticks");
                lore.add(ChatColor.RED + "20 ticks = 1 second");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_AOE_INTERVAL));
                break;
            case "Wither Amplifier":
                item = new ItemStack(Material.WITHER_SKELETON_SKULL);
                buttonName = "Change Wither Amplifier";
                lore.add("");
                lore.add(ChatColor.RED + "Change the Wither Amplifier");
                lore.add(ChatColor.RED + "Starts with 0.");
                lore.add(ChatColor.RED + "0 = Wither 1");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_AOE_WITHER_AMPLIFIER));
                break;
            case "Target":
                item = new ItemStack(Material.PLAYER_HEAD);
                buttonName = "Change the target type";
                lore.add("");
                lore.add(ChatColor.RED + "Change the target");
                lore.add(ChatColor.RED + "from single to all");
                lore.add(ChatColor.RED + "True = Target all");
                lore.add(ChatColor.RED + "False = Single Target");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_AOE_TARGET_ALL));
                break;
            case "Distance":
                item = new ItemStack(Material.ENDER_PEARL);
                buttonName = "Change Skill Distance";
                lore.add("");
                lore.add(ChatColor.RED + "Set how far the skill");
                lore.add(ChatColor.RED + "can be casted");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_AOE_DISTANCE));
                break;
            case "Threshold":
                item = new ItemStack(Material.POTION);
                buttonName = "Change AOE Cast Threshold";
                lore.add("");
                lore.add(ChatColor.RED + "Set the threshold before");
                lore.add(ChatColor.RED + "the dragon will cast the aoe skill");
                lore.add(ChatColor.RED + "Between 1-100");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_AOE_THRESHOLD));
                break;
            case "Back":
                item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                buttonName = "Back to Dragon GUI";
                break;
        }
    }
}
