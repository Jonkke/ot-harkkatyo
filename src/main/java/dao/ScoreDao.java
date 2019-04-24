/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Score;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import service.DatabaseService;

/**
 *
 * @author Jonkke
 */
public class ScoreDao implements Dao<Score> {

    Connection conn;

    public ScoreDao(DatabaseService dbs) {
        this.conn = dbs.getConnection();
    }

    @Override
    public Score get(int id) {
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM Score WHERE id = (?)");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return createScore(rs);
            }
            return null;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public List<Score> getAll() {
        List<Score> allScores = new ArrayList();
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM Score");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                allScores.add(createScore(rs));
            }
            return allScores;
        } catch (SQLException e) {
            System.out.println(e);
            return allScores;
        }
    }

    @Override
    public void save(Score s) {
        try {
            PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO Score (points, scoreDateMillis, scoreTimeMillis, playerId) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, s.getPoints());
            stmt.setLong(2, s.getScoreDate().getTime());
            stmt.setLong(3, s.getScoreTime());
            stmt.setInt(4, s.getPlayerId());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void delete(Score s) {
        try {
            PreparedStatement stmt = this.conn.prepareStatement("DELETE FROM Score WHERE id = (?)");
            stmt.setInt(1, s.getId());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private Score createScore(ResultSet rs) throws SQLException {
        int scoreId = rs.getInt("id");
        int points = rs.getInt("points");
        Date scoreDate = new Date(rs.getLong("scoreDateMillis"));
        long scoreTime = rs.getLong("scoreTimeMillis");
        int playerId = rs.getInt("playerId");
        return new Score(scoreId, points, scoreDate, scoreTime, playerId);
    }

}
