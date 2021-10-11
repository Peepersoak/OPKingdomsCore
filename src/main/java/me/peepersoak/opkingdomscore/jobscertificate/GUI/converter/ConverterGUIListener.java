package me.peepersoak.opkingdomscore.jobscertificate.GUI.converter;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.jobscertificate.JobsString;
import me.peepersoak.opkingdomscore.jobscertificate.data.JobsData;
import me.peepersoak.opkingdomscore.utilities.GUIUtils;
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
        String invName = e.getView().getTitle();
        if (!invName.equalsIgnoreCase(GUIUtils.getGUITItle(JobsString.JOB_TOKEN_CONVERT_GUI + "." + JobsString.TITLE))) return;
        e.setCancelled(true);

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();

        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();

        Material mat = item.getType();

        JobsData jobsData = new JobsData();
        int ratio = jobsData.getConfig().getInt(JobsString.CONVERTER_DOLLAR);

        String jobButton = JobsString.JOB_TOKEN_CONVERT_GUI + "." + JobsString.BUTTON;

        if (GUIUtils.getButtonMaterial(jobButton, 0) == mat) {
            convert(player, ratio * 2);
        }
        else if (GUIUtils.getButtonMaterial(jobButton, 1) == mat) {
            convert(player, ratio * 4);
        }
        else if (GUIUtils.getButtonMaterial(jobButton, 2) == mat) {
            convert(player, ratio * 6);
        }
        else if (GUIUtils.getButtonMaterial(jobButton, 3) == mat) {
            convert(player, ratio * 8);
        }
        else if (GUIUtils.getButtonMaterial(jobButton, 4) == mat) {
            convert(player, ratio * 10);
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
