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
 * This is the DAO class for the Score model.
 *
 * @author Jonkke
 */
public class ScoreDao implements Dao<Score> {

    Connection conn;

    public ScoreDao(DatabaseService dbs) {
        this.conn = dbs.getConnection();
    }

    /**
     * Find and return a single score object in the database
     *
     * @param id the id of the score to be fetched
     * @return the score object that matches the id given as parameter
     */
    @Override
    public Score get(int id) {
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM Score WHERE id = (?)");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Score score = createScore(rs);
                rs.close();
                stmt.close();
                return score;
            }
            rs.close();
            stmt.close();
            return null;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * @return All scores in the database as a List object
     */
    @Override
    public List<Score> getAll() {
        List<Score> allScores = new ArrayList();
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM Score");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Score score = createScore(rs);
                allScores.add(score);
            }
            rs.close();
            stmt.close();
            return allScores;
        } catch (SQLException e) {
            System.out.println(e);
            return allScores;
        }
    }

    /**
     * Save a score object to the database
     *
     * @param s score object to be saved
     */
    @Override
    public void save(Score s) {
        try {
            PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO Score (points, scoreDateMillis, scoreTimeMillis, playerId) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, s.getPoints());
            stmt.setLong(2, s.getScoreDate().getTime());
            stmt.setLong(3, s.getScoreTime());
            stmt.setInt(4, s.getPlayerId());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Delete a score object from the database
     *
     * @param s score object to be deleted
     */
    @Override
    public void delete(Score s) {
        try {
            PreparedStatement stmt = this.conn.prepareStatement("DELETE FROM Score WHERE id = (?)");
            stmt.setInt(1, s.getId());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Helper method for creating a score object from a ResultSet object
     *
     * @param rs ResultSet holding score infomration
     * @return new Score object
     * @throws SQLException
     */
    private Score createScore(ResultSet rs) throws SQLException {
        int scoreId = rs.getInt("id");
        int points = rs.getInt("points");
        Date scoreDate = new Date(rs.getLong("scoreDateMillis"));
        long scoreTime = rs.getLong("scoreTimeMillis");
        int playerId = rs.getInt("playerId");
        return new Score(scoreId, points, scoreDate, scoreTime, playerId);
    }

}
