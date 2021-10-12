package me.peepersoak.opkingdomscore.utilities;

import me.peepersoak.opkingdomscore.OPKingdomsCore;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtils {

    public static PreparedStatement setPreparedStatement(String query) {
        PreparedStatement ps = null;
        try {
            ps = OPKingdomsCore.getSqlConnection().prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public static void createPlayerTable(String playerUUD) {
        String statement = "CREATE TABLE IF NOT EXISTS " +
                playerUUD + " " +
                "(ITEM_NAME VARCHAR(100), " +
                "SELLER_NAME VARCHAR(100), " +
                "ITEM_LORE VARCHAR(200), " +
                "ITEM_ENCHANTMENTS VARCHAR(200), " +
                "ITEM_AMMOUNT INT(64), " +
                "ITEM_PRICE INT(100000), " +
                "ITEM_STOCK INT(5000), PRIMARY KEY (ITEM_NAME)));";
        setPreparedStatement(statement);
        PreparedStatement ps = null;
    }

    public static void addItemToDatabase(ItemStack item, int ammount, int stock, int price, String seller, String uuid) {
        ItemMeta meta = item.getItemMeta();
        String name = null;
        if (meta != null) {
            name = meta.getDisplayName();
        }
        String lore = null;
        if (meta.getLore() != null) {
            lore = GeneralUtils.convertLoretoString(meta.getLore());
        }
        String enchantment = null;
        item.getEnchantments();
        enchantment = GeneralUtils.convertEnchantstoString(item.getEnchantments());

        try {
            PreparedStatement ps = setPreparedStatement("SELECT * FROM " + uuid + " WHERE NAME=?;");
            ps.setString(1, name);
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                PreparedStatement ps2 = setPreparedStatement("INSERT INTO " + uuid +
                        " (ITEM_NAME, SELLER_NAME, ITEM_LORE, ITEM_ENCHANTMENTS, ITEM_AMMOUNT, ITEM_PRICE, ITEM_STOCK) " +
                        "VALUES (ITEM_NAME=?, SELLER_NAME=?, ITEM_LORE=?, ITEM_ENCHANTMENTS=?, ITEM_AMMOUNT=?, ITEM_PRICE=?, ITEM_STOCK=?);");
                ps2.setString(1, name);
                ps2.setString(2, seller);
                ps2.setString(3, lore);
                ps2.setString(4, enchantment);
                ps2.setInt(5, ammount);
                ps2.setInt(6, price);
                ps2.setInt(7, stock);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
