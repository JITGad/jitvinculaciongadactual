package com.jitgad.bjitgad.Models;

import java.util.List;

/**
 *
 * @author jorge
 */
public class GameModel {

    private int idgame;
    private int idactivitiestype;
    private String nameactivities;
    private String namegametype;
    private int idgametype;
    private String name;
    private String creationdate;
    private String updatedate;
    private boolean state;
    private int level;
    public List<GameimageModel> detalles;

    public GameModel() {
    }

    public GameModel(int idgame, int idactivitiestype, String nameactivities, String namegametype, int idgametype, String name, String creationdate, String updatedate, boolean state, int level, List<GameimageModel> detalles) {
        this.idgame = idgame;
        this.idactivitiestype = idactivitiestype;
        this.nameactivities = nameactivities;
        this.namegametype = namegametype;
        this.idgametype = idgametype;
        this.name = name;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.state = state;
        this.level = level;
        this.detalles = detalles;
    }

    public int getIdgame() {
        return idgame;
    }

    public void setIdgame(int idgame) {
        this.idgame = idgame;
    }

    public int getIdactivitiestype() {
        return idactivitiestype;
    }

    public void setIdactivitiestype(int idactivitiestype) {
        this.idactivitiestype = idactivitiestype;
    }

    public String getNameactivities() {
        return nameactivities;
    }

    public void setNameactivities(String nameactivities) {
        this.nameactivities = nameactivities;
    }

    public String getNamegametype() {
        return namegametype;
    }

    public void setNamegametype(String namegametype) {
        this.namegametype = namegametype;
    }

    public int getIdgametype() {
        return idgametype;
    }

    public void setIdgametype(int idgametype) {
        this.idgametype = idgametype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<GameimageModel> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<GameimageModel> detalles) {
        this.detalles = detalles;
    }

   

    
}
