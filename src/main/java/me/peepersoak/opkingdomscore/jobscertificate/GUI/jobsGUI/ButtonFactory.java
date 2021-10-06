package me.peepersoak.opkingdomscore.jobscertificate.GUI.jobsGUI;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobsData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ButtonFactory {

    private String jobTitle;
    private YamlConfiguration config;
    private Material material;
    private NamespacedKey key;

    public ItemStack createButton(String jobTitle) {
        JobsData data = new JobsData();
        config = data.getConfig();
        this.jobTitle = jobTitle;
        setMaterial();
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(JobsString.JOB_TITLE, PersistentDataType.STRING, jobTitle);

        String titleData = config.getString(jobTitle + "." + JobsString.JOBS_TITLE_NAME);
        String title = ChatColor.translateAlternateColorCodes('&', titleData);
        meta.setDisplayName(title);

        List<String> lores = new ArrayList<>();
        for (String str : config.getStringList(jobTitle + "." + JobsString.JOBS_LORE)) {
            String lore = ChatColor.translateAlternateColorCodes('&', str);
            lores.add(lore);
        }
        meta.setLore(lores);
        item.setItemMeta(meta);

        return item;
    }

    public void setMaterial() {
        switch (jobTitle) {
            case JobsString.MINER_PATH:
                material = Material.DIAMOND_PICKAXE;
                break;
            case JobsString.LOGGER_PATH:
                material = Material.DIAMOND_AXE;
                break;
            case JobsString.BREWER_PATH:
                material = Material.POTION;
                break;
            case JobsString.ENCHANTER_PATH:
                material = Material.ENCHANTING_TABLE;
                break;
            case JobsString.WARRIOR_PATH:
                material = Material.DIAMOND_SWORD;
                break;
            case JobsString.ARCHER_PATH:
                material = Material.BOW;
                break;
            case JobsString.SMITH_PATH:
                material = Material.ANVIL;
                break;
        }
    }
}
