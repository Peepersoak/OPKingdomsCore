package me.peepersoak.opkingdomscore.utilities;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class GUICreator {

    public GUICreator(String title, int slot) {
        this.title = title;
        this.slot = slot;
        createInv();
    }

    private final String title;
    private final int slot;
    private Inventory inv;

    private void createInv() {
        inv = Bukkit.createInventory(null, slot, title);
    }

    public Inventory getInv() {
        return inv;
    }
}
