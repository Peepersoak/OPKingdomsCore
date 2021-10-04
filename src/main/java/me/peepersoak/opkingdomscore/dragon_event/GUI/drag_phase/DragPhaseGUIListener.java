package me.peepersoak.opkingdomscore.dragon_event.GUI.drag_phase;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.dragon_event.DragonEggData;
import me.peepersoak.opkingdomscore.dragon_event.DragonEvent;
import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import me.peepersoak.opkingdomscore.dragon_event.GUI.aoe_lightning.AOELightningSettingsGUI;
import me.peepersoak.opkingdomscore.dragon_event.GUI.drag_skill.DragonSkillGUI;
import me.peepersoak.opkingdomscore.dragon_event.GUI.guardian.GuardianSettingsGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class DragPhaseGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        String invName = ChatColor.stripColor(e.getView().getTitle());
        if (!invName.equalsIgnoreCase(DragonStringpath.DRAGON_PHASE_CONTROL_GUI)) return;
        e.setCancelled(true);

        DragonEventData data = new DragonEventData();
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();
        if (item.getType() == Material.BLACK_STAINED_GLASS_PANE) return;

        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();

        if (DragonEvent.getDragon() == null) {
            player.sendMessage(ChatColor.RED + "Error, Can't find the dragon!!");
            return;
        }

        if (DragonEvent.getDragon().isDead()) {
            player.sendMessage(ChatColor.RED + "Error, Dragon is dead!!");
            return;
        }

        EnderDragon dragon = DragonEvent.getDragon();

        switch (item.getType()) {
            case ELYTRA:
                dragon.setPhase(EnderDragon.Phase.CIRCLING);
                player.sendMessage(ChatColor.GREEN + "Change dragon phase to circling");
                player.closeInventory();
                break;
            case NETHERITE_SWORD:
                dragon.setPhase(EnderDragon.Phase.CHARGE_PLAYER);
                player.sendMessage(ChatColor.GREEN + "Made dragon charge to player");
                player.closeInventory();
                break;
            case BEDROCK:
                dragon.setPhase(EnderDragon.Phase.LAND_ON_PORTAL);
                player.sendMessage(ChatColor.GREEN + "Made dragon land on portal");
                player.closeInventory();
                break;
            case SLIME_BALL:
                dragon.setPhase(EnderDragon.Phase.HOVER);
                player.sendMessage(ChatColor.GREEN + "Made dragon hover on location");
                player.closeInventory();
                break;
            case DRAGON_EGG:
                dragon.setPhase(EnderDragon.Phase.DYING);
                data.write(DragonStringpath.DRAGON_EVENT_STATUS, "Dead");
                player.closeInventory();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (String eggData : DragonEggData.getEggData()) {
                            getBlockFromData(eggData, "Place");
                        }
                    }
                }.runTaskLater(OPKingdomsCore.getInstance(),200);
                player.sendMessage(ChatColor.GREEN + "Killed the dragon");
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
