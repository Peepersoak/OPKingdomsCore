package me.peepersoak.opkingdomscore.jobscertificate.jobsGUI;

import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class JobsGUI {

    public Inventory openJobChangeGUI() {
        Inventory inv = Bukkit.createInventory(null, 36, ChatColor.GREEN + "" + ChatColor.BOLD + JobsString.NEW_JOB_GUI_NAME);
        return addButtons(inv);
    }

    public Inventory openMainGUI() {
        Inventory inv = Bukkit.createInventory(null, 36, ChatColor.GREEN + "" + ChatColor.BOLD + JobsString.JOBS_GUI_NAME);
        return addButtons(inv);
    }

    public Inventory addButtons(Inventory inv) {
        ButtonFactory btn = new ButtonFactory();
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        item.setItemMeta(meta);

        for (int i = 0; i < 36; i++) {
            switch (i) {
                case 10:
                    inv.setItem(i, btn.createButton(JobsString.MINER_PATH));
                    break;
                case 12:
                    inv.setItem(i, btn.createButton(JobsString.LOGGER_PATH));
                    break;
                case 14:
                    inv.setItem(i, btn.createButton(JobsString.BREWER_PATH));
                    break;
                case 16:
                    inv.setItem(i, btn.createButton(JobsString.ENCHANTER_PATH));
                    break;
                case 20:
                    inv.setItem(i, btn.createButton(JobsString.WARRIOR_PATH));
                    break;
                case 22:
                    inv.setItem(i, btn.createButton(JobsString.ARCHER_PATH));
                    break;
                case 24:
                    inv.setItem(i, btn.createButton(JobsString.SMITH_PATH));
                    break;
                default:
                    inv.setItem(i, item);
            }
        }
        return inv;
    }
}
