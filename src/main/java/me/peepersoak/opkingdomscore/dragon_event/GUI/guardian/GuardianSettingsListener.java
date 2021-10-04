package me.peepersoak.opkingdomscore.dragon_event.GUI.guardian;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import me.peepersoak.opkingdomscore.dragon_event.GUI.GUICreator2;
import org.bukkit.Bukkit;
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

public class GuardianSettingsListener implements Listener {

    private final HashMap<Player, String> playerList = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        String invName = ChatColor.stripColor(e.getView().getTitle());
        if (!invName.equalsIgnoreCase(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_GUI)) return;
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

        switch (mat) {
            case WITHER_SKELETON_SKULL:
                boolean status;
                if (data.getConfig().getBoolean(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_ALLOW)) {
                    data.writeBoolean(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_ALLOW, false);
                    status = false;
                } else {
                    data.writeBoolean(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_ALLOW, true);
                    status = true;
                }
                GuardianSettingsGUI gui = new GuardianSettingsGUI();
                player.openInventory(gui.createInventory());
                player.sendMessage(ChatColor.GREEN + "Change to " + status);
                break;
            case POTION:
                playerList.put(player, "GuardianHealth");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter the new Health for the Guardian, " + cancelMessage);
                break;
            case NETHERITE_SWORD:
                playerList.put(player, "GuardianDamage");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter the new Guardian Damage, " + cancelMessage);
                break;
            case PAPER:
                playerList.put(player, "GuardianName");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter the new Name for the Guardian, " + cancelMessage);
                break;
            case CLOCK:
                playerList.put(player, "GuardianDuration");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter the new Duration in Seconds, " + cancelMessage);
                break;
            case REDSTONE:
                playerList.put(player, "GuardianChance");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter the chance 1-100 of the guardian spawning, " + cancelMessage);
                break;
            case RED_STAINED_GLASS_PANE:
                GUICreator2 guiCreator = new GUICreator2();
                guiCreator.createInventory();
                player.openInventory(guiCreator.getInv());
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
        GuardianSettingsGUI gui = new GuardianSettingsGUI();

        boolean exit = false;

        if (msg.equalsIgnoreCase("cancel")) {
            playerList.remove(player);
            player.openInventory(gui.createInventory());
            exit = true;
        } else {
            switch (setting) {
                case "GuardianHealth":
                    int health;
                    try {
                        health = Integer.parseInt(msg);

                        int maxHealth = Bukkit.spigot().getConfig().getInt("settings.attribute.maxHealth.max");
                        if (health > maxHealth) {
                            health = maxHealth;
                            player.sendMessage(ChatColor.RED + "You have set the health over the limit. Automatically set it to the highest value");
                        }

                        data.writeInteger(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_HEALTH, health);
                        player.sendMessage(ChatColor.GREEN + "Guardians health has been set to " + ChatColor.GOLD + health);
                        exit = true;
                    } catch (NumberFormatException ex) {
                        player.sendMessage(ChatColor.RED + "Please enter a valid number!! or type CANCEL to exit");
                        return;
                    }
                    break;
                case "GuardianDamage":
                    int damage;
                    try {
                        damage = Integer.parseInt(msg);

                        int maxDamage = Bukkit.spigot().getConfig().getInt("settings.attribute.attackDamage.max");

                        if (damage > maxDamage) {
                            damage = maxDamage;
                            player.sendMessage(ChatColor.RED + "You have set the damage over the limit. Automatically set it to the highest value");
                        }

                        data.writeInteger(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_DAMAGE, damage);
                        player.sendMessage(ChatColor.GREEN + "Guardians damage has been set to " + ChatColor.GOLD + damage);
                        exit = true;
                    } catch (NumberFormatException ex) {
                        player.sendMessage(ChatColor.RED + "Please enter a valid number!! or type CANCEL to exit");
                        return;
                    }
                    break;
                case "GuardianName":
                    if (msg.length() >= 16) {
                        player.sendMessage(ChatColor.RED + "Name is too long!!");
                        return;
                    }
                    data.write(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_NAME, msg);
                    exit = true;
                    break;
                case "GuardianDuration":
                    int duration;
                    try {
                        duration = Integer.parseInt(msg);
                        data.writeInteger(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_DURATION, duration);
                        player.sendMessage(ChatColor.GREEN + "Guardians duration has been set to " + ChatColor.GOLD + duration);
                        exit = true;
                    } catch (NumberFormatException ex) {
                        player.sendMessage(ChatColor.RED + "Please enter a valid number!! or type CANCEL to exit");
                        return;
                    }
                    break;
                case "GuardianChance":
                    int chance;
                    try {
                        chance = Integer.parseInt(msg);
                        if (chance > 100) {
                            chance = 100;
                        }
                        data.writeInteger(DragonStringpath.DRAGON_GUARDIAN_SETTINGS_CHANCE, chance);
                        player.sendMessage(ChatColor.GREEN + "Guardians chance has been set to " + ChatColor.GOLD + chance);
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
                    player.openInventory(gui.createInventory());
                    playerList.remove(player);
                }
            }.runTask(OPKingdomsCore.getInstance());
        }
    }
}
