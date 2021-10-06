package me.peepersoak.opkingdomscore.jobscertificate.GUI.converter;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobsData;
import me.peepersoak.opkingdomscore.utilities.GUIButton;
import me.peepersoak.opkingdomscore.utilities.GUICreator;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class ConverterGUI {

    public Inventory openGUI(Player player) {

        JobsData data = new JobsData();
        GUICreator creator = new GUICreator(JobsString.CONVERTER_GUI_NAME, 27);
        GUIButton button = new GUIButton();
        Inventory inv = creator.getInv();
        Economy economy = OPKingdomsCore.getEconomy();

        String name = ChatColor.GRAY + "Convert:";
        String balance = ChatColor.YELLOW + "" + economy.getBalance(player) + "$" + ChatColor.GRAY + " | " + ChatColor.GOLD + JobsUtil.getPlayerToken(player) + " Tokens";
        List<String> lore;

        int dollarRatio = data.getConfig().getInt(JobsString.CONVERTER_DOLLAR);

        for (int i = 0; i < 27; i++) {
            switch (i) {
                case 11:
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.YELLOW + "" + dollarRatio*2 + "$" + ChatColor.GRAY + " = " + ChatColor.GOLD + "2 Token");
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Wallet:");
                    lore.add(balance);
                    inv.setItem(i, button.createButton(Material.IRON_BLOCK, name, lore, false));
                    break;
                case 12:
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.YELLOW + "" + dollarRatio*4 + "$" + ChatColor.GRAY + " = " + ChatColor.GOLD + "4 Token");
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Wallet:");
                    lore.add(balance);
                    inv.setItem(i, button.createButton(Material.GOLD_BLOCK, name, lore, false));
                    break;
                case 13:
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.YELLOW + "" + dollarRatio*6 + "$" + ChatColor.GRAY + " = " + ChatColor.GOLD + "6 Token");
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Wallet:");
                    lore.add(balance);
                    inv.setItem(i, button.createButton(Material.EMERALD_BLOCK, name, lore, false));
                    break;
                case 14:
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.YELLOW + "" + dollarRatio*8 + "$" + ChatColor.GRAY + " = " + ChatColor.GOLD + "8 Token");
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Wallet:");
                    lore.add(balance);
                    inv.setItem(i, button.createButton(Material.DIAMOND_BLOCK, name, lore, false));
                    break;
                case 15:
                    lore = new ArrayList<>();
                    lore.add("");
                    lore.add(ChatColor.YELLOW + "" + dollarRatio*10 + "$" + ChatColor.GRAY + " = " + ChatColor.GOLD + "10 Token");
                    lore.add("");
                    lore.add(ChatColor.AQUA + "Wallet:");
                    lore.add(balance);
                    inv.setItem(i, button.createButton(Material.NETHERITE_BLOCK, name, lore, false));
                    break;
                default:
                    inv.setItem(i, button.createButton(Material.ORANGE_STAINED_GLASS_PANE, " ", null, false));
                    break;
            }
        }
        return inv;
    }
}
