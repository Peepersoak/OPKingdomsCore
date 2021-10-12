package me.peepersoak.opkingdomscore.utilities;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralUtils {

    public static String convertLoretoString(List<String> lores) {
        StringBuilder lore = new StringBuilder();
        for (String str : lores) {
            String splitLore = str + "%";
            lore.append(splitLore);
        }
        return lore.toString();
    }

    public static String convertEnchantstoString(Map<Enchantment, Integer> enchants) {
        StringBuilder enchantment = new StringBuilder();
        for (Enchantment en : enchants.keySet()) {
            int level = enchants.get(en);
            String enchantString = en.getKey() + ":" + level + "%";
            enchantment.append(enchantString);
        }
        return enchantment.toString();
    }
}
