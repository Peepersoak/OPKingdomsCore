package me.peepersoak.opkingdomscore.dragon_event.GUI.drag_skill;

import me.peepersoak.opkingdomscore.dragon_event.DragonEventData;
import me.peepersoak.opkingdomscore.dragon_event.DragonStringpath;
import me.peepersoak.opkingdomscore.dragon_event.GUI.aoe_lightning.AOELightningSettingsGUI;
import me.peepersoak.opkingdomscore.dragon_event.GUI.guardian.GuardianSettingsGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DragonSkillGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        String invName = ChatColor.stripColor(e.getView().getTitle());
        if (!invName.equalsIgnoreCase(DragonStringpath.DRAGON_SKILL_GUI_NAME)) return;
        e.setCancelled(true);

        DragonEventData data = new DragonEventData();
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() != InventoryType.CHEST) return;
        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();
        if (item.getType() == Material.BLACK_STAINED_GLASS_PANE) return;

        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();

        DragonSkillGUI skillGUI = new DragonSkillGUI();

        switch (item.getType()) {
            case LIGHTNING_ROD:
                if (data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_LIGHTNINGSTRIKE)) {
                    data.writeBoolean(DragonStringpath.DRAGON_SKILL_LIGHTNINGSTRIKE, false);
                    player.sendMessage(ChatColor.GREEN + "Lightning strike set to false");
                } else {
                    data.writeBoolean(DragonStringpath.DRAGON_SKILL_LIGHTNINGSTRIKE, true);
                    player.sendMessage(ChatColor.GREEN + "Lightning strike set to true");
                }
                player.openInventory(skillGUI.openGUI());
                break;
            case FIRE_CHARGE:
                if (data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_EXPLOSION)) {
                    data.writeBoolean(DragonStringpath.DRAGON_SKILL_EXPLOSION, false);
                    player.sendMessage(ChatColor.GREEN + "Explosion Skill set to false");
                } else {
                    data.writeBoolean(DragonStringpath.DRAGON_SKILL_EXPLOSION, true);
                    player.sendMessage(ChatColor.GREEN + "Explosion Skill set to true");
                }
                player.openInventory(skillGUI.openGUI());
                break;
            case WITHER_ROSE:
                if (data.getConfig().getBoolean(DragonStringpath.DRAGON_SKILL_WITHER)) {
                    data.writeBoolean(DragonStringpath.DRAGON_SKILL_WITHER, false);
                    player.sendMessage(ChatColor.GREEN + "Wither Skill set to false");
                } else {
                    data.writeBoolean(DragonStringpath.DRAGON_SKILL_WITHER, true);
                    player.sendMessage(ChatColor.GREEN + "Wither Skill set to true");
                }
                player.openInventory(skillGUI.openGUI());
                break;
            case WITHER_SKELETON_SKULL:
                GuardianSettingsGUI guardianSettingsGUI = new GuardianSettingsGUI();
                player.openInventory(guardianSettingsGUI.openGUI());
                break;
            case TRIDENT:
                AOELightningSettingsGUI lightningSettingsGUI = new AOELightningSettingsGUI();
                player.openInventory(lightningSettingsGUI.openGUI());
                break;
            default:
                break;
        }
    }
}
