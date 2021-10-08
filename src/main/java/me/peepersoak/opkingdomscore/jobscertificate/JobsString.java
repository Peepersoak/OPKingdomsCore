package me.peepersoak.opkingdomscore.jobscertificate;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.NamespacedKey;

import javax.naming.NameParser;
import javax.xml.stream.events.Namespace;
import java.io.File;

public class JobsString {

    // Namespaced keys
    public static final NamespacedKey JOB_TITLE = new NamespacedKey(OPKingdomsCore.getInstance(), "JobTitle");
    public static final NamespacedKey JOB_LEVEL = new NamespacedKey(OPKingdomsCore.getInstance(), "JobLevel");
    public static final NamespacedKey JOB_XP = new NamespacedKey(OPKingdomsCore.getInstance(), "JobXP");
    public static final NamespacedKey JOB_XP_TARGET = new NamespacedKey(OPKingdomsCore.getInstance(), "JobXPTarget");
    public static final NamespacedKey JOB_TOKEN = new NamespacedKey(OPKingdomsCore.getInstance(), "JobToken");
    public static final NamespacedKey BREWER_POTION = new NamespacedKey(OPKingdomsCore.getInstance(), "BrewerPotion");

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
    public static final String ARCHER_BONUS_DMG = "Damage_Bonus." + "Bow_Bonus";
    public static final String ARCHER_CRIT_CHANCE = "Damage_Bonus." + "Crit_Chance";
    public static final String ARCHER_CRIT_MIN = ARCHER_CRIT_CHANCE + ".Min";
    public static final String ARCHER_CRIT_MAX = ARCHER_CRIT_CHANCE + ".Max";

    // Warrior Certificate
    public static final String WARRIOR_MOBS_SECTION = "Kill";
    public static final String WARRIOR_JOB_BONUSES = "Job_Level_Bonus";
    public static final String WARRIOR_SWORD_BONUS = WARRIOR_JOB_BONUSES + ".Sword_Bonus";
    public static final String WARRIOR_DEFENSE_BONUS = WARRIOR_JOB_BONUSES + ".Defense";
    public static final String WARRIOR_CRIT_CHANCE = WARRIOR_JOB_BONUSES + ".Crit_Chance";
    public static final String WARRIOR_CRIT_MIN = WARRIOR_CRIT_CHANCE + ".Min";
    public static final String WARRIOR_CRIT_MAX = WARRIOR_CRIT_CHANCE + ".Max";

    // Smithing Certificate
    public static final String SMITHING_CRAFT_XP = "Craft";
    public static final String SMITHING_UPGRADE_XP = "Upgrade";
    public static final String SMITHING_REPAIR_XP = "Repair";
    public static final String SMITHING_SMELT_XP = "Smelt";
    public static final String SMITHING_SPECIFIC_ITEM = "Job_Craft_Item";

    // Brewer Certificate
    public static final String BREWER_DOUBLE_THROW_CHANCE = "Double_Potion_Chance";
    public static final String BREWER_POTION_RECOVERY_CHANCE = "Potion_Recovery_Chance";
    public static final String BREWER_CRAFT_XP = "Crafter";
    public static final String BREWER_GATHER_XP = "Gather";

    // For datas
    public static final String JOBS_CERTIFICATE_FILE_NAME = "Jobs Certificate Settings.yml";
    public static final String MINER_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Miner.yml";
    public static final String LOGGER_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Logger.yml";
    public static final String BREWER_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Brewer.yml";
    public static final String ENCHANTER_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Enchanter.yml";
    public static final String WARRIOR_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Warrior.yml";
    public static final String ARCHER_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Archer.yml";
    public static final String SMITHING_SETTINGS_FILE_NAME = "JobsSettings" + File.separator + "Smithing.yml";
    public static final String MESSAGE_FILE_NAME = "JobsSettings" + File.separator + "messages.yml";

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

    // Messages
    public static final String WRONG_CERT = "Wrong_Certificate";
    public static final String WRONG_LEVEL = "Not_Enought_Level";

    public static final String JOBS_GUI_NAME = "Select your job";
    public static final String NEW_JOB_GUI_NAME = "Select your new job";

    public static final String XP = "experience";
    public static final String INCOME = "income";

    public static final String LEVEL_UP_TOKEN = "Token_Needed";
    public static final String LEVEL_UP_TOKEN_MULTIPLIER = "Token_Multiplier";
    public static final String ANNOUNCE_EFFECT = "Announce_Effect_FOR_TESTING_ONLY";
}
