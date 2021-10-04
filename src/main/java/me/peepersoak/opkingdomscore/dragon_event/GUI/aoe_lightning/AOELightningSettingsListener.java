package me.peepersoak.opkingdomscore.dragon_event.GUI.aoe_lightning;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import me.peepersoak.opkingdomscore.dragon_event.GUI.main.MainGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class AOELightningSettingsListener implements Listener {

    private final HashMap<Player, String> playerList = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        String invName = ChatColor.stripColor(e.getView().getTitle());
        if (!invName.equalsIgnoreCase(DragonStringpath.DRAGON_SKILL_AOE_SETTINGS_GUI)) return;
        e.setCancelled(true);

        DragonEventData data = new DragonEventData();
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();
        if (item.getType() == Material.BLACK_STAINED_GLASS_PANE) return;

        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();

        Material mat = item.getType();

        String cancelMessage = ChatColor.RED + "or Type CANCEL to exit";

        AOELightningSettingsGUI gui = new AOELightningSettingsGUI();

        switch (mat) {
            case LIGHTNING_ROD:
                boolean status;
                if (data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_AOE_ALLOW)) {
                    data.writeBoolean(DragonStringpath.DRAGON_SKILL_AOE_ALLOW, false);
                    status = false;
                } else {
                    data.writeBoolean(DragonStringpath.DRAGON_SKILL_AOE_ALLOW, true);
                    status = true;
                }
                player.openInventory(gui.openGUI());
                player.sendMessage(ChatColor.GREEN + "Change to " + status);
                break;
            case CLOCK:
                playerList.put(player, "AOELightningInterval");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter the new Interval, " + cancelMessage);
                break;
            case WITHER_SKELETON_SKULL:
                playerList.put(player, "AOEWitherAmplifier");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter the new Wither Amplifier, " + cancelMessage);
                break;
            case PLAYER_HEAD:
                boolean targetStatus;
                if (data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_AOE_TARGET_ALL)) {
                    data.writeBoolean(DragonStringpath.DRAGON_SKILL_AOE_TARGET_ALL, false);
                    targetStatus = false;
                } else {
                    data.writeBoolean(DragonStringpath.DRAGON_SKILL_AOE_TARGET_ALL, true);
                    targetStatus = true;
                }
                player.openInventory(gui.openGUI());
                player.sendMessage(ChatColor.GREEN + "Change to " + targetStatus);
                break;
            case ENDER_PEARL:
                playerList.put(player, "AOELightningDistance");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter the new Distance, " + cancelMessage);
                break;
            case POTION:
                playerList.put(player, "AOELightningThreshold");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter the threshold between 1-100, " + cancelMessage);
                break;
            case RED_STAINED_GLASS_PANE:
                MainGUI guiCreator = new MainGUI();
                player.openInventory(guiCreator.openGUI());
                break;
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (!playerList.containsKey(player)) return;

        String msg = e.getMessage();
        String setting = playerList.get(player);

        DragonEventData data = new DragonEventData();
        e.setCancelled(true);

        AOELightningSettingsGUI gui = new AOELightningSettingsGUI();

        boolean exit = false;

        if (msg.equalsIgnoreCase("cancel")) {
            playerList.remove(player);
            player.openInventory(gui.openGUI());
            exit = true;
        } else {
            switch (setting) {
                case "AOELightningInterval":
                    int interval;
                    try {
                        interval = Integer.parseInt(msg);
                        data.writeInteger(DragonStringpath.DRAGON_SKILL_AOE_INTERVAL, interval);
                        player.sendMessage(ChatColor.GREEN + "Interval has been set to " + ChatColor.GOLD + interval + ChatColor.GREEN + " ticks");
                        exit = true;
                    } catch (NumberFormatException ex) {
                        player.sendMessage(ChatColor.RED + "Please enter a valid number!! or type CANCEL to exit");
                        return;
                    }
                    break;
                case "AOEWitherAmplifier":
                    int amplifier;
                    try {
                        amplifier = Integer.parseInt(msg) + 1;
                        data.writeInteger(DragonStringpath.DRAGON_SKILL_AOE_WITHER_AMPLIFIER, amplifier);
                        player.sendMessage(ChatColor.GREEN + "Wither Amplifier has been set to " + ChatColor.GOLD + amplifier);
                        exit = true;
                    } catch (NumberFormatException ex) {
                        player.sendMessage(ChatColor.RED + "Please enter a valid number!! or type CANCEL to exit");
                        return;
                    }
                    break;
                case "AOELightningDistance":
                    int distance;
                    try {
                        distance = Integer.parseInt(msg);
                        data.writeInteger(DragonStringpath.DRAGON_SKILL_AOE_DISTANCE, distance);
                        player.sendMessage(ChatColor.GREEN + "AOE Lightning casting distance has been set to " + ChatColor.GOLD + distance);
                        exit = true;
                    } catch (NumberFormatException ex) {
                        player.sendMessage(ChatColor.RED + "Please enter a valid number!! or type CANCEL to exit");
                        return;
                    }
                    break;
                case "AOELightningThreshold":
                    int threshold;
                    try {
                        threshold = Integer.parseInt(msg);
                        if (threshold > 100) {
                            threshold = 100;
                        }
                        data.writeInteger(DragonStringpath.DRAGON_SKILL_AOE_THRESHOLD, threshold);
                        player.sendMessage(ChatColor.GREEN + "AOE Lightning Threshold has been set to " + ChatColor.GOLD + threshold);
                        exit = true;
                    } catch (NumberFormatException ex) {
                        player.sendMessage(ChatColor.RED + "Please enter a valid number!! or type CANCEL to exit");
                        return;
                    }
                    break;
            }
        }
        if (exit) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.openInventory(gui.openGUI());
                    playerList.remove(player);
                }
            }.runTask(OPKingdomsCore.getInstance());
        }
    }
}
