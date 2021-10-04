package me.peepersoak.opkingdomscore.dragon_event.GUI.main;

import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import me.peepersoak.opkingdomscore.dragon_event.GUI.GUIButton;
import me.peepersoak.opkingdomscore.dragon_event.GUI.GUICreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class MainGUI {

    public Inventory openGUI() {
        GUICreator gui = new GUICreator(DragonStringpath.DRAGON_GUI_NAME, 54);
        Inventory inv = gui.getInv();

        GUIButton button = new GUIButton();

        DragonEventData data = new DragonEventData();

        for (int i = 0; i < 54; i++) {
            switch (i) {
                case 4:
                    inv.setItem(i, button.createButton(Material.DRAGON_HEAD, " ", null, false));
                    break;
                case 10:
                    String worldName = ChatColor.GOLD + "Set The Dragon Event World";
                    List<String> WorldNameLore = new ArrayList<>();
                    WorldNameLore.add("");
                    WorldNameLore.add(ChatColor.AQUA + "Set the dragon event world");
                    WorldNameLore.add(ChatColor.AQUA + "to the world you are currently in");
                    WorldNameLore.add("");
                    WorldNameLore.add(ChatColor.AQUA + "Currently Set to:");
                    WorldNameLore.add(ChatColor.LIGHT_PURPLE + data.getConfig().getString(DragonStringpath.DRAGON_WORLD_NAME).replace("_", " "));
                    inv.setItem(i, button.createButton(Material.END_STONE, worldName, WorldNameLore, true));
                    break;
                case 11:
                    String location = data.getConfig().getString(DragonStringpath.DRAGON_SPAWN_LOCATION);
                    String spawnLocation = ChatColor.GOLD + "Set Dragon Spawn Location";
                    List<String> spawnLocationLore = new ArrayList<>();
                    spawnLocationLore.add("");
                    spawnLocationLore.add(ChatColor.AQUA + "Set the dragon Spawn");
                    spawnLocationLore.add(ChatColor.AQUA + "on the block you will break.");
                    spawnLocationLore.add("");
                    spawnLocationLore.add(ChatColor.AQUA + "Currently set to:");
                    spawnLocationLore.add(ChatColor.LIGHT_PURPLE + location.replace(";", " "));
                    inv.setItem(i, button.createButton(Material.END_PORTAL_FRAME, spawnLocation, spawnLocationLore, true));
                    break;
                case 15:
                    String endCrystal = ChatColor.GOLD + "Set End Crystal";
                    List<String> endCrystalLore = new ArrayList<>();
                    endCrystalLore.add("");
                    endCrystalLore.add(ChatColor.AQUA + "Set the end crystal");
                    endCrystalLore.add(ChatColor.AQUA + "location.");
                    endCrystalLore.add("");
                    endCrystalLore.add(ChatColor.AQUA + "End Crystal Count:");
                    endCrystalLore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getStringList(DragonStringpath.DRAGON_END_CRYSTAL_LOCATION).size());
                    inv.setItem(i, button.createButton(Material.END_CRYSTAL, endCrystal, endCrystalLore, true));
                    break;
                case 16:
                    String removeEndCrystal = ChatColor.RED + "Clear End Crystal Location";
                    List<String> removeEndCrystalLore = new ArrayList<>();
                    removeEndCrystalLore.add("");
                    removeEndCrystalLore.add(ChatColor.AQUA + "Remove all end");
                    removeEndCrystalLore.add(ChatColor.AQUA + "crystal location");
                    removeEndCrystalLore.add("");
                    removeEndCrystalLore.add(ChatColor.AQUA + "End Crystal Count:");
                    removeEndCrystalLore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getStringList(DragonStringpath.DRAGON_END_CRYSTAL_LOCATION).size());
                    inv.setItem(i, button.createButton(Material.RED_WOOL, removeEndCrystal, removeEndCrystalLore, false));
                    break;
                case 28:
                    String dragname = ChatColor.GOLD + "Change Dragon Name";
                    List<String> dragNameLore = new ArrayList<>();
                    dragNameLore.add("");
                    dragNameLore.add(ChatColor.AQUA + "Currently Set to:");
                    dragNameLore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getString(DragonStringpath.DRAGON_NAME));
                    inv.setItem(i, button.createButton(Material.PAPER, dragname, dragNameLore, true));
                    break;
                case 29:
                    String dragHealth = ChatColor.GOLD + "Change Dragon Health";
                    List<String> dragHealthLore = new ArrayList<>();
                    dragHealthLore.add("");
                    dragHealthLore.add(ChatColor.AQUA + "Currently Set to:");
                    dragHealthLore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getInt(DragonStringpath.DRAGON_HEALTH));
                    inv.setItem(i, button.createButton(Material.POTION, dragHealth, dragHealthLore, true));
                    break;
                case 30:
                    String allowSkill = ChatColor.GOLD + "Allow Dragon Skill";
                    List<String> allowSkillLore = new ArrayList<>();
                    allowSkillLore.add("");
                    allowSkillLore.add(ChatColor.AQUA + "Currently Set to:");
                    allowSkillLore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_ALLOW_SKILL));
                    inv.setItem(i, button.createButton(Material.TOTEM_OF_UNDYING, allowSkill, allowSkillLore, true));
                    break;
                case 31:
                    String dragSkillThreshold = ChatColor.GOLD + "Health Threshold";
                    List<String> dragSkillThresholdLore = new ArrayList<>();
                    dragSkillThresholdLore.add("");
                    dragSkillThresholdLore.add(ChatColor.AQUA + "If the dragons health");
                    dragSkillThresholdLore.add(ChatColor.AQUA + "falls below this threshold");
                    dragSkillThresholdLore.add(ChatColor.AQUA + "it will start to cast skill");
                    dragSkillThresholdLore.add("");
                    dragSkillThresholdLore.add(ChatColor.AQUA + "Currently Set to:");
                    dragSkillThresholdLore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getInt(DragonStringpath.DRAGON_HEALTH_THRESHOLD));
                    inv.setItem(i, button.createButton(Material.ENCHANTED_GOLDEN_APPLE, dragSkillThreshold, dragSkillThresholdLore, true));
                    break;
                case 32:
                    String dragSkillDistance = ChatColor.GOLD + "Skill Distance";
                    List<String> dragSkillDistanceLore = new ArrayList<>();
                    dragSkillDistanceLore.add("");
                    dragSkillDistanceLore.add(ChatColor.AQUA + "The maximum distance");
                    dragSkillDistanceLore.add(ChatColor.AQUA + "which a skill can be casted");
                    dragSkillDistanceLore.add("");
                    dragSkillDistanceLore.add(ChatColor.AQUA + "Currently Set to:");
                    dragSkillDistanceLore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_DISTANCE));
                    inv.setItem(i, button.createButton(Material.ENDER_PEARL, dragSkillDistance, dragSkillDistanceLore, true));
                    break;
                case 33:
                    String cooldown = ChatColor.GOLD + "Skill Cooldown";
                    List<String> cooldownLore = new ArrayList<>();
                    cooldownLore.add("");
                    cooldownLore.add(ChatColor.AQUA + "Dragon Skill cooldown");
                    cooldownLore.add("");
                    cooldownLore.add(ChatColor.AQUA + "Currently Set to:");
                    cooldownLore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_COOLDOWN));
                    inv.setItem(i, button.createButton(Material.CLOCK, cooldown, cooldownLore, true));
                    break;
                case 34:
                    String percentage = ChatColor.GOLD + "Player Percentage";
                    List<String> percentageLore = new ArrayList<>();
                    percentageLore.add("");
                    percentageLore.add(ChatColor.AQUA + "The percentage of player");
                    percentageLore.add(ChatColor.AQUA + "that the dragon skill");
                    percentageLore.add(ChatColor.AQUA + "will hit");
                    percentageLore.add("");
                    percentageLore.add(ChatColor.AQUA + "Currently Set to:");
                    percentageLore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getInt(DragonStringpath.DRAGON_SKILL_PLAYER_PERCENTAGE));
                    inv.setItem(i, button.createButton(Material.PLAYER_HEAD, percentage, percentageLore, true));
                    break;
                case 46:
                    String skill = ChatColor.GOLD + "Dragon Skill List";
                    List<String> skillLore = new ArrayList<>();
                    skillLore.add(ChatColor.AQUA + "You can set the dragon");
                    skillLore.add(ChatColor.AQUA + "skill here");
                    inv.setItem(i, button.createButton(Material.NETHERITE_SWORD, skill, skillLore, true));
                    break;
                case 51:
                    boolean isALive = data.getConfig().getBoolean(DragonStringpath.DRAGON_EVENT_STATUS);
                    Material mat;
                    if (isALive) {
                        mat = Material.JACK_O_LANTERN;
                    } else {
                        mat = Material.CARVED_PUMPKIN;
                    }
                    String eventStatus = ChatColor.GOLD + "Dragon Event Status";
                    List<String> eventStatusLore = new ArrayList<>();
                    eventStatusLore.add("");
                    eventStatusLore.add(ChatColor.AQUA + "Override the dragon");
                    eventStatusLore.add(ChatColor.AQUA + "Event status");
                    eventStatusLore.add("");
                    eventStatusLore.add(ChatColor.AQUA + "Currently Set to:");
                    eventStatusLore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getString(DragonStringpath.DRAGON_EVENT_STATUS));
                    inv.setItem(i, button.createButton(mat, eventStatus, eventStatusLore, true));
                    break;
                case 52:
                    String egg = ChatColor.GOLD + "Dragon Egg";
                    List<String> eggLore = new ArrayList<>();
                    eggLore.add("");
                    eggLore.add(ChatColor.AQUA + "Enable or Disable");
                    eggLore.add(ChatColor.AQUA + "Dragons Egg animation");
                    eggLore.add("");
                    eggLore.add(ChatColor.AQUA + "Currently Set to:");
                    eggLore.add(ChatColor.LIGHT_PURPLE + "" + data.getConfig().getBoolean(DragonStringpath.DRAGON_PARTICLE));
                    inv.setItem(i, button.createButton(Material.DRAGON_HEAD, egg, eggLore, true));
                    break;
                default:
                    inv.setItem(i, button.createButton(Material.BLACK_STAINED_GLASS_PANE, " ", null, false));
                    break;
            }
        }
        return inv;
    }
}
