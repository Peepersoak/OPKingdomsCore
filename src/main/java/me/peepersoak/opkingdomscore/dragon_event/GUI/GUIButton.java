package me.peepersoak.opkingdomscore.dragon_event.GUI;

import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUIButton {

    public GUIButton() {
        data = new DragonEventData();
    }

    private ItemStack wool;
    private final DragonEventData data;

    public ItemStack worldButton() {
        wool = new ItemStack(Material.END_STONE);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Set the Dragon Event World");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Set the dragon event world");
        lore.add(ChatColor.AQUA + "to the world you are currently in");
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + data.getConfig().getString(DragonStringpath.DRAGON_WORLD_NAME).replace("_", " "));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack spawnButton() {
        wool = new ItemStack(Material.END_PORTAL_FRAME);
        String location = data.getConfig().getString(DragonStringpath.DRAGON_SPAWN_LOCATION);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Set Dragon Spawn Location");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Set the dragon Spawn");
        lore.add(ChatColor.AQUA + "on the block you will break.");
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently set to:");
        lore.add(ChatColor.LIGHT_PURPLE + location.replace(";", " "));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack setEndCrystal() {
        wool = new ItemStack(Material.END_CRYSTAL);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Set End Crystal");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Set the end crystal");
        lore.add(ChatColor.AQUA + "location.");
        lore.add("");
        lore.add(ChatColor.AQUA + "End Crystal Count:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getStringList(DragonStringpath.DRAGON_END_CRYSTAL_LOCATION).size());
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack clearEndCrystal() {
        wool = new ItemStack(Material.RED_WOOL);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.RED + "Clear End Crystal Location");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Remove all end");
        lore.add(ChatColor.AQUA + "crystal location");
        lore.add("");
        lore.add(ChatColor.AQUA + "End Crystal Count:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getStringList(DragonStringpath.DRAGON_END_CRYSTAL_LOCATION).size());
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack dragonName() {
        wool = new ItemStack(Material.PAPER);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Change Dragon Name");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getString(DragonStringpath.DRAGON_NAME));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack dragonHealth() {
        wool = new ItemStack(Material.POTION);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Change Dragon Health");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getInt(DragonStringpath.DRAGON_HEALTH));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack allowSkill() {
        wool = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Allow Dragon Skill");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_ALLOW_SKILL));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack dragonHealthThreshold() {
        wool = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Health Threshold");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "If the dragons health");
        lore.add(ChatColor.AQUA + "falls below this threshold");
        lore.add(ChatColor.AQUA + "it will start to cast skill");
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getInt(DragonStringpath.DRAGON_HEALTH_THRESHOLD));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack dragonSkillDistance() {
        wool = new ItemStack(Material.ENDER_PEARL);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Skill Distance");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "The maximum distance");
        lore.add(ChatColor.AQUA + "which a skill can be casted");
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_DISTANCE));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack dragonSkillCooldown() {
        wool = new ItemStack(Material.CLOCK);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Skill Cooldown");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Dragon Skill cooldown");
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_COOLDOWN));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack playerPercentage() {
        wool = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Player Percentage");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "The percentage of player");
        lore.add(ChatColor.AQUA + "that the dragon skill");
        lore.add(ChatColor.AQUA + "will hit");
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_PLAYER_PERCENTAGE));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack lightningStrike() {
        wool = new ItemStack(Material.LIGHTNING_ROD);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Lightning Strike");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Strike a lightning");
        lore.add(ChatColor.AQUA + "on player location");
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_LIGHTNINGSTRIKE));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack explosion() {
        wool = new ItemStack(Material.FIRE_CHARGE);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Explosion");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "create an Explosion");
        lore.add(ChatColor.AQUA + "on player location");
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_EXPLOSION));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack wither() {
        wool = new ItemStack(Material.WITHER_ROSE);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Wither Effect");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Add a wither effect");
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_WITHER));
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack guardian() {
        wool = new ItemStack(Material.WITHER_SKELETON_SKULL);
        ItemMeta greenMeta = wool.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GOLD + "Dragon Guardian");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Click to open the Guardian Setting");
        greenMeta.setLore(lore);
        wool.setItemMeta(greenMeta);
        return wool;
    }

    public ItemStack statusOverride() {
        wool = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = wool.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Dragon Event Status");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Override the dragon");
        lore.add(ChatColor.AQUA + "Event status");
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getString(DragonStringpath.DRAGON_EVENT_STATUS));
        meta.setLore(lore);
        wool.setItemMeta(meta);
        return wool;
    }

    public ItemStack particle() {
        wool = new ItemStack(Material.DRAGON_EGG);
        ItemMeta meta = wool.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Dragon Egg");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.AQUA + "Enable or Disable");
        lore.add(ChatColor.AQUA + "Dragons Egg animation");
        lore.add("");
        lore.add(ChatColor.AQUA + "Currently Set to:");
        lore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_PARTICLE));
        meta.setLore(lore);
        wool.setItemMeta(meta);
        return wool;
    }
}
