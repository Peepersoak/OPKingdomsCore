package me.peepersoak.opkingdomscore.dragon_event.GUI.main;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.dragon_event.DragonEggData;
import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import me.peepersoak.opkingdomscore.dragon_event.GUI.drag_phase.DragPhaseGUI;
import me.peepersoak.opkingdomscore.dragon_event.GUI.drag_skill.DragonSkillGUI;
import me.peepersoak.opkingdomscore.dragon_event.GUI.guardian.GuardianSettingsGUI;
import me.peepersoak.opkingdomscore.dragon_event.GUI.main.MainGUI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class MainGUIListener implements Listener {

    private HashMap<Player, String> playerList = new HashMap<>();

    public boolean isValidNumber(String message) {
        try {
            int health = Integer.parseInt(message);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        DragonEventData data = new DragonEventData();
        Player player = e.getPlayer();
        if (!playerList.containsKey(player)) return;
        String command = playerList.get(player);

        String message = e.getMessage();

        MainGUI inventory = new MainGUI();

        boolean exit = false;

        if (message.equalsIgnoreCase("cancel")) {
            playerList.remove(player);
            player.sendMessage(ChatColor.GREEN + "You have exit the edit mode");
            exit = true;
        } else {
            switch (command) {
                case "Dragon Name":
                    data.write(DragonStringpath.DRAGON_NAME, message);
                    player.sendMessage(ChatColor.GREEN + "You have change the dragon name");
                    exit = true;
                    break;
                case "Dragon Health":
                    if (!isValidNumber(message)) {
                        player.sendMessage(ChatColor.RED + "Please enter a Valid number");
                        e.setCancelled(true);
                        break;
                    }
                    int health = Integer.parseInt(message);

                    int maxHealthInConfig = Bukkit.spigot().getConfig().getInt("settings.attribute.maxHealth.max");

                    if (health > maxHealthInConfig) {
                        health = maxHealthInConfig;
                        player.sendMessage(ChatColor.RED + "You set the health above the limit, automatically set it to the highest possible value");
                    }

                    data.writeInteger(DragonStringpath.DRAGON_HEALTH, health);
                    player.sendMessage(ChatColor.GREEN + "You have change the dragon health");
                    exit = true;
                    break;
                case "Dragon Threshold":
                    if (!isValidNumber(message)) {
                        player.sendMessage(ChatColor.RED + "Please enter a Valid number");
                        e.setCancelled(true);
                        break;
                    }
                    int threshold = Integer.parseInt(message);
                    if (threshold > 100) {
                        threshold = 100;
                    }
                    data.writeInteger(DragonStringpath.DRAGON_HEALTH_THRESHOLD, threshold);
                    player.sendMessage(ChatColor.GREEN + "You have change the health threshold");
                    exit = true;
                    break;
                case "Dragon Skill Distance":
                    if (!isValidNumber(message)) {
                        player.sendMessage(ChatColor.RED + "Please enter a Valid number");
                        e.setCancelled(true);
                        break;
                    }
                    data.writeInteger(DragonStringpath.DRAGON_SKILL_DISTANCE, Integer.parseInt(message));
                    player.sendMessage(ChatColor.GREEN + "You have change the skill distance");
                    exit = true;
                    break;
                case "Dragon Skill Cooldown":
                    if (!isValidNumber(message)) {
                        player.sendMessage(ChatColor.RED + "Please enter a Valid number");
                        e.setCancelled(true);
                        break;
                    }
                    data.writeInteger(DragonStringpath.DRAGON_SKILL_COOLDOWN, Integer.parseInt(message));
                    player.sendMessage(ChatColor.GREEN + "You have change the skill cooldown");
                    exit = true;
                    break;
                case "Player Percentage":
                    if (!isValidNumber(message)) {
                        player.sendMessage(ChatColor.RED + "Please enter a Valid number");
                        e.setCancelled(true);
                        break;
                    }
                    int percentage = Integer.parseInt(message);
                    if (percentage > 100) {
                        percentage = 100;
                    }
                    data.writeInteger(DragonStringpath.DRAGON_SKILL_PLAYER_PERCENTAGE, percentage);
                    player.sendMessage(ChatColor.GREEN + "You have change the players percentage");
                    exit = true;
                    break;
            }
        }

        if (exit) {
            e.setCancelled(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.openInventory(inventory.openGUI());
                }
            }.runTask(OPKingdomsCore.getInstance());
            playerList.remove(player);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (!playerList.containsKey(player)) return;
        String command = playerList.get(player);

        DragonEventData data = new DragonEventData();

        Location location = e.getBlock().getLocation();

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        String world = location.getWorld().getName();

        String value = world + ";" + x + ";" + y + ";" + z;

        if (command.equalsIgnoreCase("Spawn")) {
            data.write(DragonStringpath.DRAGON_SPAWN_LOCATION, value);
            player.sendMessage(ChatColor.GREEN + "Successfully set the dragon spawn location");
            MainGUI inventory = new MainGUI();
            player.openInventory(inventory.openGUI());
            playerList.remove(player);
        } else if (command.equalsIgnoreCase("End Crystal")) {
            List<String> str = data.getConfig().getStringList(DragonStringpath.DRAGON_END_CRYSTAL_LOCATION);
            if (str.contains(value)) {
                player.sendMessage(ChatColor.RED + "You already set this location for end crystal!!");
            } else {
                str.add(value);
                data.writeList(DragonStringpath.DRAGON_END_CRYSTAL_LOCATION, str);
                player.sendMessage(ChatColor.GREEN + "End Crystal location has been set");
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        String invName = ChatColor.stripColor(e.getView().getTitle());
        if (!invName.equalsIgnoreCase(DragonStringpath.DRAGON_EVENT_SETTING_NAME)) return;
        e.setCancelled(true);

        DragonEventData data = new DragonEventData();
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();
        if (item.getType() == Material.BLACK_STAINED_GLASS_PANE) return;

        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();

        MainGUI inventory = new MainGUI();

        switch (item.getType()) {
            case END_STONE:
                data.write(DragonStringpath.DRAGON_WORLD_NAME, player.getWorld().getName());
                player.sendMessage(ChatColor.GREEN + "You have set the Dragon Event World to " + player.getWorld().getName());
                player.openInventory(inventory.openGUI());
                break;
            case END_PORTAL_FRAME:
                if (playerList.containsKey(player)) {
                    player.sendMessage(ChatColor.RED + "You are still in edit mode!!");
                    player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                    return;
                }
                playerList.put(player, "Spawn");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Break the block to set the spawn point");
                player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                break;
            case END_CRYSTAL:
                if (playerList.containsKey(player)) {
                    player.sendMessage(ChatColor.RED + "You are still in edit mode!!");
                    player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                    return;
                }
                playerList.put(player, "End Crystal");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Break the block to set end crystal location");
                player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                break;
            case RED_WOOL:
                List<String> str = new ArrayList<>();
                data.writeList(DragonStringpath.DRAGON_END_CRYSTAL_LOCATION, str);
                player.sendMessage(ChatColor.GREEN + "All end crystal location has beed cleared");
                player.openInventory(inventory.openGUI());
                break;
            case PAPER:
                if (playerList.containsKey(player)) {
                    player.sendMessage(ChatColor.RED + "You are still in edit mode!!");
                    player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                    return;
                }
                playerList.put(player, "Dragon Name");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Please enter the dragon name");
                player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                break;
            case POTION:
                if (playerList.containsKey(player)) {
                    player.sendMessage(ChatColor.RED + "You are still in edit mode!!");
                    player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                    return;
                }
                playerList.put(player, "Dragon Health");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Please enter the dragon health");
                player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                break;
            case TOTEM_OF_UNDYING:
                if (data.getConfig().getBoolean(DragonStringpath.DRAGON_ALLOW_SKILL)) {
                    data.writeBoolean(DragonStringpath.DRAGON_ALLOW_SKILL, false);
                    player.sendMessage(ChatColor.GREEN + "You have set it to false");
                } else {
                    data.writeBoolean(DragonStringpath.DRAGON_ALLOW_SKILL, true);
                    player.sendMessage(ChatColor.GREEN + "You have set it to true");
                }
                player.openInventory(inventory.openGUI());
                break;
            case ENCHANTED_GOLDEN_APPLE:
                if (playerList.containsKey(player)) {
                    player.sendMessage(ChatColor.RED + "You are still in edit mode!!");
                    player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                    return;
                }
                playerList.put(player, "Dragon Threshold");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter Dragon Heatlh Threshold");
                player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                break;
            case ENDER_PEARL:
                if (playerList.containsKey(player)) {
                    player.sendMessage(ChatColor.RED + "You are still in edit mode!!");
                    player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                    return;
                }
                playerList.put(player, "Dragon Skill Distance");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter Dragon Skill Distance");
                player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                break;
            case CLOCK:
                if (playerList.containsKey(player)) {
                    player.sendMessage(ChatColor.RED + "You are still in edit mode!!");
                    player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                    return;
                }
                playerList.put(player, "Dragon Skill Cooldown");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter Dragon Skill Cooldown");
                player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                break;
            case PLAYER_HEAD:
                if (playerList.containsKey(player)) {
                    player.sendMessage(ChatColor.RED + "You are still in edit mode!!");
                    player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                    return;
                }
                playerList.put(player, "Player Percentage");
                player.closeInventory();
                player.sendMessage(ChatColor.GREEN + "Enter player percentage");
                player.sendMessage(ChatColor.RED + "Enter cancel to exit edit mode");
                break;
            case NETHERITE_SWORD:
                DragonSkillGUI dragonSkillGUI = new DragonSkillGUI();
                player.openInventory(dragonSkillGUI.openGUI());
                break;
            case DRAGON_HEAD:
                DragPhaseGUI dragPhaseGUI = new DragPhaseGUI();
                player.openInventory(dragPhaseGUI.openGUI());
                break;
            case JACK_O_LANTERN:
                data.write(DragonStringpath.DRAGON_EVENT_STATUS, "Dead");
                player.sendMessage(ChatColor.GREEN + "You have set it to Dead");
                player.openInventory(inventory.openGUI());
                break;
            case CARVED_PUMPKIN:
                data.write(DragonStringpath.DRAGON_EVENT_STATUS, "Alive");
                player.sendMessage(ChatColor.GREEN + "You have set it to Alive");
                player.openInventory(inventory.openGUI());
                break;
            case DRAGON_EGG:
                if (data.getConfig().getString(DragonStringpath.DRAGON_SPAWN_LOCATION).equalsIgnoreCase("None")) {
                    player.sendMessage(ChatColor.RED +"Failed! Dragon Spawn Location has not been set yet");
                    break;
                }
                if (data.getConfig().getBoolean(DragonStringpath.DRAGON_PARTICLE)) {
                    data.writeBoolean(DragonStringpath.DRAGON_PARTICLE, false);
                    for (String eggData : DragonEggData.getEggData()) {
                        getBlockFromData(eggData, "Remove");
                    }
                    player.sendMessage(ChatColor.GREEN + "Egg has been remove");
                } else {
                    data.writeBoolean(DragonStringpath.DRAGON_PARTICLE, true);
                    for (String eggData : DragonEggData.getEggData()) {
                        getBlockFromData(eggData, "Place");
                    }
                    player.sendMessage(ChatColor.GREEN + "Egg has been place");
                }
                player.openInventory(inventory.openGUI());
                break;
            default:
                break;
        }
    }

    public void getBlockFromData(String data, String status) {
        String[] firstSplit = data.split(" ");
        String[] main = firstSplit[1].split(";");
        int offX = Integer.parseInt(main[0]);
        int offY = Integer.parseInt(main[1]);
        int offZ = Integer.parseInt(main[2]);
        BlockData blockData = Bukkit.createBlockData(main[3]);
        Block block = getLocation().getBlock().getRelative(offX, offY, offZ);

        if (status.equalsIgnoreCase("Place")) {
            block.setBlockData(blockData);
        } else if (status.equalsIgnoreCase("Remove")) {
            block.setType(Material.AIR);
        }
    }

    public Location getLocation() {
        DragonEventData data = new DragonEventData();
        String[] split = data.getConfig().getString(DragonStringpath.DRAGON_SPAWN_LOCATION).split(";");
        String name = split[0];
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int z = Integer.parseInt(split[3]);
        return new Location(Bukkit.getWorld(name), x , y, z);
    }
}
