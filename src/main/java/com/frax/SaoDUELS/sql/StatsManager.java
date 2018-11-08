package com.frax.SaoDUELS.sql;

import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatsManager {

    private Player p;
    private MySQL sql;

    public StatsManager(Player p, MySQL sql) {
        this.p = p;
        this.sql = sql;
    }

    public void addWin() {
        int p1 = getWins() + 1;

        try {
            PreparedStatement ps = sql.getCon().prepareStatement("UPDATE DuelStats SET Wins='?' WHERE UUID='?'");

            ps.setInt(1, p1);
            ps.setString(2, p.getUniqueId().toString());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLose() {
        int p1 = getLoses() - 1;

        try {
            PreparedStatement ps = sql.getCon().prepareStatement("UPDATE DuelStats SET Wins='?' WHERE UUID='?'");

            ps.setInt(1, p1);
            ps.setString(2, p.getUniqueId().toString());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getWins() {
        try {
            PreparedStatement ps = sql.getCon().prepareStatement("Select Wins FROM DuelStats WHERE UUID='?'");

            ps.setString(1, p.getUniqueId().toString());

            return ps.executeQuery().getInt("Wins");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getLoses() {
        try {
            PreparedStatement ps = sql.getCon().prepareStatement("Select Lose FROM DuelStats WHERE UUID='?'");

            ps.setString(1, p.getUniqueId().toString());

            return ps.executeQuery().getInt("Lose");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setP(Player p) {
        this.p = p;
    }
}
