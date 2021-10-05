package me.peepersoak.opkingdomscore.jobscertificate.converter;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobsData;
import me.peepersoak.opkingdomscore.utilities.JobsUtil;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class ConverterGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        String invName = ChatColor.stripColor(e.getView().getTitle());
        if (!invName.equalsIgnoreCase(JobsString.CONVERTER_GUI_NAME)) return;
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();
        if (item.getType() == Material.ORANGE_STAINED_GLASS_PANE) return;

        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();

        Material mat = item.getType();

        JobsData jobsData = new JobsData();
        int ratio = jobsData.getConfig().getInt(JobsString.CONVERTER_DOLLAR);

        switch (mat) {
            case IRON_BLOCK:
                convert(player, ratio * 2);
                break;
            case GOLD_BLOCK:
                convert(player, ratio * 4);
                break;
            case EMERALD_BLOCK:
                convert(player, ratio * 6);
                break;
            case DIAMOND_BLOCK:
                convert(player, ratio * 8);
                break;
            case NETHERITE_BLOCK:
                convert(player, ratio * 10);
                break;
        }
    }

    public void convert(Player player, double ammount) {
        Economy eco = OPKingdomsCore.getEconomy();
        ConverterGUI gui = new ConverterGUI();
        JobsData jobsData = new JobsData();
        if (ammount > eco.getBalance(player)) {
            player.sendMessage(ChatColor.RED + "Not enough balance!!");
        } else {
            EconomyResponse response = eco.withdrawPlayer(player, ammount);
            if (response.transactionSuccess()) {
                int ratio = jobsData.getConfig().getInt(JobsString.CONVERTER_DOLLAR);
                int token = (int) (ammount/ratio);
                JobsUtil.addTokenToPlayer(player, token);
                player.sendMessage(ChatColor.GREEN + "Successfully converted " +
                        ChatColor.GOLD + response.amount + "$" +
                        ChatColor.GREEN + " to " +
                        ChatColor.YELLOW + token + " Tokens");

            }
        }
        player.openInventory(gui.openGUI(player));
    }
}
