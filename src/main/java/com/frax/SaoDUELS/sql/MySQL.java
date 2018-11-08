package com.frax.SaoDUELS.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    private String username;
    private String password;
    private String database;
    private String host;
    private Connection con;

    public void openConnection() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + 3306 + "/" + database, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createStructure() {
        if (isConnected()) {
            try {
                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS DuelStats(UUID VARCHAR(64), Wins INT(100), Lose INT(100))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet getResult(String qry) {
        if (isConnected()) {
            try {
                return con.prepareStatement(qry).executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean isConnected() {
        if (con != null) return true;
        else return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Connection getCon() {
        return con;
    }
}
