package me.peepersoak.opkingdomscore.dragon_event;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

public class DragonStringpath {

    // Dragon Skill GUI
    public static final String DRAGON_SKILL_GUI_NAME = "Dragon Skill Settings";

    // Dragon Controll
    public static final String DRAGON_PHASE_CONTROL_GUI = "Dragon Controller";

    // Dragon Guardian String Path
    public static final String DRAGON_GUARDIAN_SETTINGS_GUI = "Dragon Guardian Settings";
    public static final String DRAGON_GUARDIAN_SETTINGS_PATH = "Dragon_Skill.Guardian";
    public static final String DRAGON_GUARDIAN_SETTINGS_ALLOW = DRAGON_GUARDIAN_SETTINGS_PATH + ".Allow";
    public static final String DRAGON_GUARDIAN_SETTINGS_NAME = DRAGON_GUARDIAN_SETTINGS_PATH + ".Name";
    public static final String DRAGON_GUARDIAN_SETTINGS_HEALTH = DRAGON_GUARDIAN_SETTINGS_PATH + ".Health";
    public static final String DRAGON_GUARDIAN_SETTINGS_DAMAGE = DRAGON_GUARDIAN_SETTINGS_PATH + ".Damage";
    public static final String DRAGON_GUARDIAN_SETTINGS_DURATION  = DRAGON_GUARDIAN_SETTINGS_PATH + ".Duration";
    public static final String DRAGON_GUARDIAN_SETTINGS_CHANCE = DRAGON_GUARDIAN_SETTINGS_PATH  + ".Chance";

    // Dragon AOE Skill
    public static final String DRAGON_SKILL_AOE_SETTINGS_GUI = "AOE Lightning Settings";
    public static final String DRAGON_SKILL_LIGHTNING_AOE = "Dragon_Skill.Lightning_AOE";
    public static final String DRAGON_SKILL_AOE_ALLOW = DRAGON_SKILL_LIGHTNING_AOE + ".Allow";
    public static final String DRAGON_SKILL_AOE_INTERVAL = DRAGON_SKILL_LIGHTNING_AOE + ".Interval";
    public static final String DRAGON_SKILL_AOE_WITHER_AMPLIFIER = DRAGON_SKILL_LIGHTNING_AOE + ".Wither_Amplifier";
    public static final String DRAGON_SKILL_AOE_TARGET_ALL = DRAGON_SKILL_LIGHTNING_AOE + ".Target_All";
    public static final String DRAGON_SKILL_AOE_DISTANCE = DRAGON_SKILL_LIGHTNING_AOE + ".Distance";
    public static final String DRAGON_SKILL_AOE_THRESHOLD = DRAGON_SKILL_LIGHTNING_AOE + ".Threshold";

    // Regular Lightning Strike
    public static final String DRAGON_SKILL_LIGHTNINGSTRIKE = "Dragon_Skill.Lightning_Strike";
    public static final String DRAGON_SKILL_EXPLOSION = "Dragon_Skill.Explosion";
    public static final String DRAGON_SKILL_WITHER = "Dragon_Skill.Wither_Effect";

    public static final String DRAGON_EVENT_SETTING_FILE_NAME = "Dragon_Event_Settings.yml";
    public static final String DRAGON_HEALTH_THRESHOLD = "Dragon_Health_Threshold";
    public static final String DRAGON_SKILL_PLAYER_PERCENTAGE = "Player_Percentage";
    public static final String DRAGON_SKILL_DISTANCE = "Dragon_Skill_Distance";
    public static final String DRAGON_WORLD_NAME = "Dragon_World";
    public static final String DRAGON_SPAWN_LOCATION = "Dragon_Spawn_Location";
    public static final String DRAGON_END_CRYSTAL_LOCATION = "End_Crystal_Location";
    public static final String DRAGON_EVENT = "Event_Is_Running";

    public static final String DRAGON_FIRST_PLACE_REWARDS = "Frist_Place_Rewards";
    public static final String DRAGON_SECOND_PLACE_REWARDS = "Second_Place_Rewards";
    public static final String DRAGON_THIRD_PLACE_REWARDS = "Third_Place_Rewards";
    public static final String DRAGON_EVENT_REMAINING_MESSAGE = "Dragon_Event_Reminder";
    public static final String DRAGON_END_EVENT_TP_COMMAND = "Dragon_Teleport_Command";
    public static final String DRAGON_END_EVENT_TP_DELAY = "Dragon_Teleport_Delay";

    public static final String DRAGON_NAME = "Dragon_Name";
    public static final String DRAGON_HEALTH = "Dragon_Health";
    public static final String DRAGON_ALLOW_SKILL = "Allow_Dragon_Skill";
    public static final String DRAGON_SKILL_COOLDOWN = "Dragon_Skill_Cooldown";

    public static final String DRAGON_PARTICLE = "Dragon_Particle";

    public static final String DRAGON_EVENT_STATUS = "Dragon_Event_Status";

    public static final String DRAGON_EVENT_SETTING_NAME = "Dragon Event Settings";

    public static final String DRAGON_END_EVENT_MESSAGE = "Dragon_End_Event_Message";

    public static final String DRAGON_GUI_NAME = ChatColor.GOLD +  "Dragon Event Settings";

    public static final NamespacedKey DRAGON_NAMESPACEDKEY = new NamespacedKey(OPKingdomsCore.getInstance(), "Dragon_Event");
    public static final NamespacedKey DRAGON_BOSSBAR = new NamespacedKey(OPKingdomsCore.getInstance(), "DragonEventBossBar");

}
