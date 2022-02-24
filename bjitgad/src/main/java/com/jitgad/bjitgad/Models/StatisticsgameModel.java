package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class StatisticsgameModel {
    
    private int idstatisticsgame;
    private int idgame;
    private int movements;
    private int minutes;
    private int seconds;
    private int score;
    private int stars;
    private int lvl;
    private String creationdate;
    private String updatedate;
    private boolean state;

    public StatisticsgameModel() {
    }

    public StatisticsgameModel(int idstatisticsgame, int idgame, int movements, int minutes, int seconds, int score, int stars, int lvl, String creationdate, String updatedate, boolean state) {
        this.idstatisticsgame = idstatisticsgame;
        this.idgame = idgame;
        this.movements = movements;
        this.minutes = minutes;
        this.seconds = seconds;
        this.score = score;
        this.stars = stars;
        this.lvl = lvl;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.state = state;
    }

    public int getIdstatisticsgame() {
        return idstatisticsgame;
    }

    public void setIdstatisticsgame(int idstatisticsgame) {
        this.idstatisticsgame = idstatisticsgame;
    }

    public int getIdgame() {
        return idgame;
    }

    public void setIdgame(int idgame) {
        this.idgame = idgame;
    }

    public int getMovements() {
        return movements;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    
    
    
    
    
    
    
}
