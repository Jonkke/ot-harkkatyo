/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This service provides the connection to the underlying SQLite database. As
 * this application uses a SQLite database, only a single active connection will
 * be used for everything.
 *
 * @author Jonkke
 */
public class DatabaseService {

    Connection conn = null;

    public void connect(String dbName) {
        try {
            String url = "jdbc:sqlite:" + dbName + ".db";
            this.conn = DriverManager.getConnection(url);
            System.out.println("Connection established.");
            createNonExistingTables(conn);
            createDefaultPlayerIfPlayersEmpty(this.conn);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    private void createNonExistingTables(Connection conn) throws SQLException {
        PreparedStatement stmt = this.conn.prepareStatement("CREATE TABLE IF NOT EXISTS Player "
                + "(id INTEGER PRIMARY KEY,"
                + "name TEXT UNIQUE)");
        stmt.execute();
        stmt = this.conn.prepareStatement("CREATE TABLE IF NOT EXISTS Score "
                + "(id INTEGER PRIMARY KEY,"
                + "points INTEGER,"
                + "scoreDateMillis INTEGER,"
                + "scoreTimeMillis INTEGER,"
                + "playerId INTEGER,"
                + "FOREIGN KEY(playerId) REFERENCES Player(id))");
        stmt.execute();
    }

    private void createDefaultPlayerIfPlayersEmpty(Connection conn) throws SQLException {
        PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM Player");
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            stmt = this.conn.prepareStatement("INSERT INTO Player (name) VALUES (?)");
            stmt.setString(1, "default");
            stmt.execute();
        }
    }

}
