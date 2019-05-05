/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import service.DatabaseService;

/**
 *
 * @author Jonkke
 */
public class PlayerDao implements Dao<Player> {

    Connection conn;

    public PlayerDao(DatabaseService dbs) {
        this.conn = dbs.getConnection();
    }

    @Override
    public Player get(int id) {
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM Player WHERE ID = (?)");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Player p = new Player(rs.getInt("id"), rs.getString("name"));
                rs.close();
                stmt.close();
                return p;
            }
            rs.close();
            stmt.close();
            return null;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Player> getAll() {
        List<Player> players = new ArrayList();
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM Player");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Player p = new Player(rs.getInt("id"), rs.getString("name"));
                players.add(p);
            }
            rs.close();
            stmt.close();
            return players;
        } catch (SQLException e) {
            System.out.println(e);
            return players;
        }
    }

    @Override
    public void save(Player p) {
        try {
            PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO Player (name) VALUES (?)");
            stmt.setString(1, p.getName());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void delete(Player p) {
        try {
            PreparedStatement stmt = this.conn.prepareStatement("DELETE FROM Player WHERE id = (?)");
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
