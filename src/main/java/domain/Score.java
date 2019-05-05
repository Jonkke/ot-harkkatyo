/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Date;

/**
 * This class represents a single score attained from a game. Included
 * information includes the amount of points scored, date of score, time played
 * to attain the score (in milliseconds) and the player, referenced by id, who
 * is credited for this particular score.
 *
 * @author Jonkke
 */
public class Score {

    private int id;
    private int points;
    private Date scoreDate;
    private long scoreTime;
    private int playerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getScoreDate() {
        return scoreDate;
    }

    public void setScoreDate(Date scoreDate) {
        this.scoreDate = scoreDate;
    }

    public long getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(long scoreTime) {
        this.scoreTime = scoreTime;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Score(int id, int points, Date scoreDate, long scoreTime, int playerId) {
        this.id = id;
        this.points = points;
        this.scoreDate = scoreDate;
        this.scoreTime = scoreTime;
        this.playerId = playerId;
    }

}
