package me.peepersoak.opkingdomscore.utilities;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobMessage;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobsData;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class JobsUtil {
    
    public static Integer getLevelFromString(String str) {
        String[] split = str.split("_");
        int level = -1;
        try {
            level = Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            //
        }
        return level;
    }

    public static boolean isBlock(String material, ConfigurationSection section) {
        return section.getString(material) != null;
    }

    public static String getPlayerJobTitle(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(JobsString.JOB_TITLE, PersistentDataType.STRING) == null) return null;
        return data.get(JobsString.JOB_TITLE, PersistentDataType.STRING);
    }

    public static int getPlayerJobLevel(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(JobsString.JOB_LEVEL, PersistentDataType.INTEGER) == null) return -1;
        return data.get(JobsString.JOB_LEVEL, PersistentDataType.INTEGER);
    }

    public static Double getPlayerJobXP(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(JobsString.JOB_XP, PersistentDataType.DOUBLE) == null) return -1.0;
        return data.get(JobsString.JOB_XP, PersistentDataType.DOUBLE);
    }

    public static int getPlayerJobXPTarget(Player player) {
        JobsData jobsData = new JobsData();
        int newXPTarget = jobsData.getConfig().getInt(JobsUtil.getPlayerJobTitle(player) + "." + JobsString.XP_REQUIREMENT);
        double amp = jobsData.getConfig().getDouble(JobsUtil.getPlayerJobTitle(player) + "." + JobsString.XP_MULTIPLIER);
        for (int i = 0; i <= JobsUtil.getPlayerJobLevel(player); i++) {
            if (i == 0) continue;
            newXPTarget = (int) (newXPTarget * amp);
        }
        return newXPTarget;
    }

    public static int getPlayerToken(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(JobsString.JOB_TOKEN, PersistentDataType.INTEGER) == null) return -1;
        return data.get(JobsString.JOB_TOKEN, PersistentDataType.INTEGER);
    }

    public static void addTokenToPlayer(Player player, int token) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        if (data.get(JobsString.JOB_TOKEN, PersistentDataType.INTEGER) == null) {
            data.set(JobsString.JOB_TOKEN, PersistentDataType.INTEGER, 0);
        }
        int newToken = data.get(JobsString.JOB_TOKEN, PersistentDataType.INTEGER) + token;
        data.set(JobsString.JOB_TOKEN, PersistentDataType.INTEGER, newToken);
    }

    public static void setPlayerToken(Player player, int token) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(JobsString.JOB_TOKEN, PersistentDataType.INTEGER, token);
    }

    public static void upgradeLevel(Player player, int level) {
        JobsData jobsData = new JobsData();
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 10F, 1F);
        int tokenCost = jobsData.getConfig().getInt(JobsString.LEVEL_UP_TOKEN);
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(JobsString.JOB_LEVEL, PersistentDataType.INTEGER, level + 1);
        data.set(JobsString.JOB_TOKEN, PersistentDataType.INTEGER, data.get(JobsString.JOB_TOKEN, PersistentDataType.INTEGER) - tokenCost);
    }

    public static void changeJobLevel(Player player, int level) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(JobsString.JOB_LEVEL, PersistentDataType.INTEGER, level);
    }

    public static void addXPToPlayer(Player player, double xp) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        int xpRequirement = getPlayerJobXPTarget(player);
        double newXP = xp;
        if (xp >= xpRequirement) {
            newXP = xpRequirement;
        }
        data.set(JobsString.JOB_XP, PersistentDataType.DOUBLE, newXP);
    }

    public static boolean hasJob(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        return data.has(JobsString.JOB_TITLE, PersistentDataType.STRING);
    }

    public static int getEarnXP(ConfigurationSection section, String type) {
        return section.getInt(type + "." + JobsString.XP);
    }

    public static int getEarnIncome(ConfigurationSection section, String material) {
        return section.getInt(material + "." + JobsString.INCOME);
    }

    public static int getTokenNeeded(Player player) {
        JobsData jobsData = new JobsData();
        int level = JobsUtil.getPlayerJobLevel(player);
        int newToken = jobsData.getConfig().getInt(JobsString.LEVEL_UP_TOKEN);
        double ampl = jobsData.getConfig().getDouble(JobsString.LEVEL_UP_TOKEN_MULTIPLIER);
        for (int i = 0; i <= level; i++) {
            if (i == 0) continue;
            newToken = (int) (newToken * ampl);
        }
        return newToken;
    }

    public static String getLevelUPRawMessage(String title) {
        JobsData jobsData = new JobsData();
        return jobsData.getConfig().getString(title + "." + JobsString.JOBS_LEVEL_UP_MESSAGE);
    }

    public static boolean isJobCorrect(Player player, String title) {
        if (!hasJob(player)) {
            JobMessage msg = new JobMessage();
            String rawMsg = msg.getConfig().getString("No_Job");
            assert rawMsg != null;
            String message = ChatColor.translateAlternateColorCodes('&', rawMsg);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            return false;
        }
        PersistentDataContainer data = player.getPersistentDataContainer();
        return Objects.requireNonNull(data.get(JobsString.JOB_TITLE, PersistentDataType.STRING)).equalsIgnoreCase(title);
    }

    public static boolean isWieldingAxe(Player player) {
        List<Material> mat = new ArrayList<>();
        mat.add(Material.WOODEN_AXE);
        mat.add(Material.IRON_AXE);
        mat.add(Material.GOLDEN_AXE);
        mat.add(Material.DIAMOND_AXE);
        mat.add(Material.NETHERITE_AXE);
        ItemStack item = player.getInventory().getItemInMainHand();
        return mat.contains(item.getType());
    }

    public static boolean isWieldingSword(Player player) {
        List<Material> mat = new ArrayList<>();
        mat.add(Material.WOODEN_SWORD);
        mat.add(Material.IRON_SWORD);
        mat.add(Material.GOLDEN_SWORD);
        mat.add(Material.DIAMOND_SWORD);
        mat.add(Material.NETHERITE_SWORD);
        ItemStack item = player.getInventory().getItemInMainHand();
        return mat.contains(item.getType());
    }

    public static boolean isWieldingBow(Player player) {
        List<Material> mat = new ArrayList<>();
        mat.add(Material.BOW);
        mat.add(Material.CROSSBOW);
        mat.add(Material.TRIDENT);
        ItemStack item = player.getInventory().getItemInMainHand();
        return mat.contains(item.getType());
    }

    public static void addIncomeToPlayer(Player player, double income) {
        JobMessage msg = new JobMessage();
        Economy economy = OPKingdomsCore.getEconomy();
        EconomyResponse response = economy.depositPlayer(player, income);
        if (response.transactionSuccess()) {
            String rawMsg = msg.getConfig().getString("Income");
            assert rawMsg != null;
            String message = ChatColor.translateAlternateColorCodes('&', rawMsg);
            player.sendMessage(message.replace("%income%", "" + response.amount));
        } else {
            player.sendMessage(ChatColor.RED + "Failed to add balance");
        }
    }

    public static void checkBlockBreakJob(String material, Player player, String title, BlockBreakEvent event,
                                   ConfigurationSection jobBlock, ConfigurationSection jobDefaultBlock, boolean isCD) {

        assert jobBlock != null;
        assert jobDefaultBlock != null;

        double xp = 0;
        double income = 0;

        boolean addIncomeAndXP = false;

        for (String level : jobBlock.getKeys(false)) {
            if (!JobsUtil.isBlock(material, Objects.requireNonNull(jobBlock.getConfigurationSection(level)))) continue;
            if (!JobsUtil.isJobCorrect(player, title)) {
                sendWrongCertificate(player, title);
                event.setCancelled(true);
                return;
            }
            if (JobsUtil.getPlayerJobLevel(player) < JobsUtil.getLevelFromString(level)) {
                sendNotEnoughLevelMessage(player, title);
                event.setCancelled(true);
                return;
            }
            addIncomeAndXP = true;
            xp = JobsUtil.getEarnXP(Objects.requireNonNull(jobBlock.getConfigurationSection(level)), material);
            income = JobsUtil.getEarnIncome(Objects.requireNonNull(jobBlock.getConfigurationSection(level)), material);
        }

        if (JobsUtil.isBlock(material, jobDefaultBlock)) {
            if (JobsUtil.isJobCorrect(player, title)) {
                addIncomeAndXP = true;
                xp = JobsUtil.getEarnXP(jobDefaultBlock, material);
                income = JobsUtil.getEarnIncome(jobDefaultBlock, material);
            }
        }

        if (addIncomeAndXP) {
            if (isCD) return;
            double playerNewXP = JobsUtil.getPlayerJobXP(player) + xp;
            JobsUtil.addXPToPlayer(player, playerNewXP);
            addIncomeToPlayer(player, income);
        }
    }

    public static void addXPandIncome(Player player, ConfigurationSection section, String type) {
        for (String key : section.getKeys(false)) {
            if (!key.equalsIgnoreCase(type)) continue;
            double xp = JobsUtil.getEarnXP(section, key) + JobsUtil.getPlayerJobXP(player);
            double income = JobsUtil.getEarnIncome(section, key);
            JobsUtil.addXPToPlayer(player, xp);
            JobsUtil.addIncomeToPlayer(player, income);
        }
    }

    public static boolean announce() {
        JobsData data = new JobsData();
        return data.getConfig().getBoolean(JobsString.ANNOUNCE_EFFECT);
    }

    public static void addMobDrop(Player player, List<ItemStack> itemStackList) {
        if (!isJobCorrect(player, JobsString.WARRIOR_PATH) && !isJobCorrect(player, JobsString.ARCHER_PATH)) return;
        int level = JobsUtil.getPlayerJobLevel(player);
        if (level >= 4) {
            for (ItemStack item : itemStackList) {
                int ammount = item.getAmount();
                int newAmmount = ammount * 2;
                item.setAmount(newAmmount);
                if (JobsUtil.announce()) {
                    player.sendMessage(ChatColor.GOLD + "" + item.getType() +  " have doubled from " +
                            ammount + " to " +
                            newAmmount);
                }
            }
        }
        ItemStack[] itemStacks = itemStackList.toArray(new ItemStack[0]);
        HashMap<Integer, ItemStack> drops =  player.getInventory().addItem(itemStacks);
        if (drops.isEmpty()) return;
        for (ItemStack item : drops.values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        }
    }

    public static boolean shoudlDrop(Player player) {
        return isJobCorrect(player, JobsString.WARRIOR_PATH) || isJobCorrect(player, JobsString.ARCHER_PATH);
    }

    public static void removeCertificate(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        data.remove(JobsString.JOB_TITLE);
        data.remove(JobsString.JOB_LEVEL);
        data.remove(JobsString.JOB_XP);
        data.remove(JobsString.JOB_XP_TARGET);
    }

    public static void sendWrongCertificate(Player player, String job) {
        JobMessage msg = new JobMessage();
        if (!hasJob(player)) return;
        ConfigurationSection section = msg.getConfig().getConfigurationSection(JobsString.WRONG_CERT);
        assert section != null;
        assert job != null;
        String rawMsg = section.getString(job);
        assert rawMsg != null;
        String message = ChatColor.translateAlternateColorCodes('&', rawMsg);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static void sendNotEnoughLevelMessage(Player player, String job) {
        JobMessage msg = new JobMessage();
        if (!hasJob(player)) return;
        ConfigurationSection section = msg.getConfig().getConfigurationSection(JobsString.WRONG_LEVEL);
        assert section != null;
        assert job != null;
        String rawMsg = section.getString(job);
        assert rawMsg != null;
        String message = ChatColor.translateAlternateColorCodes('&', rawMsg);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static boolean isBlockSpecific(ConfigurationSection jobBlock, ConfigurationSection jobDefaultBlock, String material, Player player, String title) {
        for (String level : jobBlock.getKeys(false)) {
            if (JobsUtil.isBlock(material, Objects.requireNonNull(jobBlock.getConfigurationSection(level)))) return true;
        }
        return JobsUtil.isBlock(material, jobDefaultBlock);
    }
}
