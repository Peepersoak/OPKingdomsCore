package me.peepersoak.opkingdomscore.jobscertificate;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.NamespacedKey;

import javax.xml.stream.events.Namespace;
import java.io.File;

public class JobsString {

    // Namespaced keys
    public static final NamespacedKey JOB_TITLE = new NamespacedKey(OPKingdomsCore.getInstance(), "JobTitle");
    public static final NamespacedKey JOB_LEVEL = new NamespacedKey(OPKingdomsCore.getInstance(), "JobLevel");
    public static final NamespacedKey JOB_XP = new NamespacedKey(OPKingdomsCore.getInstance(), "JobXP");
    public static final NamespacedKey JOB_XP_TARGET = new NamespacedKey(OPKingdomsCore.getInstance(), "JobXPTarget");
    public static final NamespacedKey JOB_TOKEN = new NamespacedKey(OPKingdomsCore.getInstance(), "JobToken");

    public static final NamespacedKey MINER = new NamespacedKey(OPKingdomsCore.getInstance(), "miner_certificate");
    public static final NamespacedKey LOGGER = new NamespacedKey(OPKingdomsCore.getInstance(), "logger_certificate");
    public static final NamespacedKey BREWER = new NamespacedKey(OPKingdomsCore.getInstance(), "brewer_certificate");
    public static final NamespacedKey ENCHANTER = new NamespacedKey(OPKingdomsCore.getInstance(), "enchanter_certificate");
    public static final NamespacedKey WARRIOR = new NamespacedKey(OPKingdomsCore.getInstance(), "warrior_certificate");
    public static final NamespacedKey ARCHER = new NamespacedKey(OPKingdomsCore.getInstance(), "archer_certificate");
    public static final NamespacedKey SMITH = new NamespacedKey(OPKingdomsCore.getInstance(), "smith_certificate");

    public static final NamespacedKey WARRIOR_KILL_COUNT = new NamespacedKey(OPKingdomsCore.getInstance(), "KillCount");

    // Token Converter
    public static final String CONVERTER_GUI_NAME = "Dollar --> Token";
    public static final String CONVERTER_DOLLAR = "Dollar_To_Token";

    // Upgrade GUI
    public static final String UPGRADE_GUI_NAME = "Upgrade your Job";

    // Miner Certificate
    public static final String MINER_JOBS_BLOCKS = "Miner_Jobs";

    // Logger Certificate
    public static final String LOGGER_JOBS_BLOCKS = "Logger_Jobs";
    public static final String LOGGER_JOBS_DAMAGE_BONUS = "Damage_Increase";

    // Archer Certificate
    public static final String ARCHER_MOBS_SECTION = "Kill";
    public static final String ARCHER_BONUS_DMG_1 = "Damage_Bonus." + "Level_1";
    public static final String ARCHER_BONUS_DMG_3 = "Damage_Bonus." + "Level_3";
    public static final String ARCHER_BONUS_DMG_5 = "Damage_Bonus." + "Level_5";

    // Warrior Certificate
    public static final String WARRIOR_MOBS_SECTION = "Kill";
    public static final String WARRIOR_JOB_BONUSES = "Job_Level_Bonus";
    public static final String WARRIOR_SWORD_BONUS = WARRIOR_JOB_BONUSES + ".Sword_Bonus";
    public static final String WARRIOR_KNOCKBACK_BONUS = WARRIOR_JOB_BONUSES + ".Knockback";
    public static final String WARRIOR_DEFENSE_BONUS = WARRIOR_JOB_BONUSES + ".Defense";
    public static final String WARRIOR_AXE_BONUS = WARRIOR_JOB_BONUSES + ".Axe_Bonus";
    public static final String WARRIOR_CRIT_CHANCE_RATIO = WARRIOR_JOB_BONUSES + ".Crit_Chance_Ratio";

    // For datas
    public static final String JOBS_CERTIFICATE_FILE_NAME = "Jobs Certificate Settings.yml";
    public static final String MINER_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Miner.yml";
    public static final String LOGGER_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Logger.yml";
    public static final String BREWER_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Brewer.yml";
    public static final String ENCHANTER_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Enchanter.yml";
    public static final String WARRIOR_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Warrior.yml";
    public static final String ARCHER_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Archer.yml";
    public static final String SMITHING_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Smithing.yml";

    // Data path name
    public static final String XP_REQUIREMENT = "XP_Requirement";
    public static final String XP_MULTIPLIER = "XP_Multiplier";
    public static final String JOBS_TITLE_NAME = "Title";
    public static final String JOBS_JOIN_MESSAGE = "Jobs_Join_Message";
    public static final String JOBS_LEAVE_MESSAGE = "Jobs_Leave_Message";
    public static final String JOBS_LEVEL_UP_MESSAGE = "Jobs_Level_UP_Message";
    public static final String JOBS_LORE = "Lore";

    // Path and Name
    public static final String MINER_PATH = "Miner";
    public static final String LOGGER_PATH = "Logger";
    public static final String BREWER_PATH = "Brewer";
    public static final String ENCHANTER_PATH = "Enchanter";
    public static final String WARRIOR_PATH = "Warrior";
    public static final String ARCHER_PATH = "Archer";
    public static final String SMITH_PATH = "Smith";

    public static final String JOBS_GUI_NAME = "Select your job";
    public static final String NEW_JOB_GUI_NAME = "Select your new job";

    public static final String XP = "experience";
    public static final String INCOME = "income";

    public static final String LEVEL_UP_TOKEN = "Token_Needed";
    public static final String LEVEL_UP_TOKEN_MULTIPLIER = "Token_Multiplier";
    public static final String ANNOUNCE_EFFECT = "Announce_Effect_FOR_TESTING_ONLY";
}
