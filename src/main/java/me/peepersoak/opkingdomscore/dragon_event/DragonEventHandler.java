package me.peepersoak.opkingdomscore.dragon_event;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import me.peepersoak.opkingdomscore.dragon_event.GUI.drag_phase.DragPhaseGUIListener;
import me.peepersoak.opkingdomscore.dragon_event.GUI.drag_skill.DragonSkillGUIListener;
import me.peepersoak.opkingdomscore.dragon_event.GUI.main.MainGUIListener;
import me.peepersoak.opkingdomscore.dragon_event.GUI.aoe_lightning.AOELightningSettingsListener;
import me.peepersoak.opkingdomscore.dragon_event.GUI.guardian.GuardianSettingsListener;
import org.bukkit.plugin.PluginManager;

public class DragonEventHandler {

    public void registerDragonEvent(OPKingdomsCore plugin, PluginManager pm) {
        pm.registerEvents(new DragonEventListener(), plugin);
        pm.registerEvents(new MainGUIListener(), plugin);
        pm.registerEvents(new DragonSkillGUIListener(), plugin);
        pm.registerEvents(new GuardianSettingsListener(), plugin);
        pm.registerEvents(new AOELightningSettingsListener(), plugin);
        pm.registerEvents(new DragPhaseGUIListener(), plugin);
    }
}
