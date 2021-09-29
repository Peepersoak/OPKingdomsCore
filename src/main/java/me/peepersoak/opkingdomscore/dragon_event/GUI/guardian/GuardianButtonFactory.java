package me.peepersoak.opkingdomscore.dragon_event.GUI.guardian;

import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuardianButtonFactory {

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
                item = new ItemStack(Material.WITHER_SKELETON_SKULL);
                buttonName = "Use Guardian Skill";
                lore.add("");
                lore.add(ChatColor.RED + "Allow dragon to use this skill");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_ALLOW));
                break;
            case "Health":
                item = new ItemStack(Material.POTION);
                buttonName = "Change Guardian Health";
                lore.add("");
                lore.add(ChatColor.RED + "Change the health of the Guardian");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getInt(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_HEALTH));;
                break;
            case "Damage":
                item = new ItemStack(Material.NETHERITE_SWORD);
                buttonName = "Change Guardian Damage";
                lore.add("");
                lore.add(ChatColor.RED + "Change the Guardian Damage");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getInt(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_DAMAGE));
                break;
            case "Name":
                item = new ItemStack(Material.PAPER);
                buttonName = "Change Guardian Name";
                lore.add("");
                lore.add(ChatColor.RED + "Change the name of the Guardian");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getString(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_NAME));
                break;
            case "Duration":
                item = new ItemStack(Material.CLOCK);
                buttonName = "Change Guardian Duration";
                lore.add("");
                lore.add(ChatColor.RED + "Set how long the guardian");
                lore.add(ChatColor.RED + "will last in seconds");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getInt(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_DURATION));
                break;
            case "Chance":
                item = new ItemStack(Material.REDSTONE);
                buttonName = "Change Guardian Chance";
                lore.add("");
                lore.add(ChatColor.RED + "Set the chance of the guardian ");
                lore.add(ChatColor.RED + "getting summon, between 1-100");
                lore.add("");
                lore.add(ChatColor.LIGHT_PURPLE + "Currently Set to:");
                lore.add(ChatColor.GOLD + "" + data.getConfig().getInt(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_CHANCE));
                break;
            case "Back":
                item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                buttonName = "Back to Dragon GUI";
                break;
        }
    }



}
