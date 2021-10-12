package me.peepersoak.opkingdomscore.utilities;

import me.peepersoak.opkingdomscore.OPKingdomsCore;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtils {

    public static PreparedStatement getPreparedStatement(String query) {
        PreparedStatement ps = null;
        try {
            ps = OPKingdomsCore.getSqlConnection().prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }
}
