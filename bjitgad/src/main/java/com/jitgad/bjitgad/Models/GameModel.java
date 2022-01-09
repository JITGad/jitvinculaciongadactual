
package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class GameModel {

    private int idgame = 0;
    private String idactivitiestype = "";
    private String idgametype = "";
    private String name = "";
    private String creationdate = "";
    private String updatedate = "";
    private boolean state = true;
    private int level = 0;

    public GameModel() {
    }

    public GameModel(String _idactivitiestype,
            String _idgametype,
            String _name,
            String _creationdate,
            String _updatedate,
            boolean _state,
            int _level) {
        this.idactivitiestype = _idactivitiestype;
        this.idgametype = _idgametype;
        this.name = _name;
        this.creationdate = _creationdate;
        this.updatedate = _updatedate;
        this.state = _state;
        this.level = _level;
    }

    public int getIdgame() {
        return idgame;
    }

    public void setIdgame(int idgame) {
        this.idgame = idgame;
    }

    public String getIdactivitiestype() {
        return idactivitiestype;
    }

    public void setIdactivitiestype(String idactivitiestype) {
        this.idactivitiestype = idactivitiestype;
    }

    public String getIdgametype() {
        return idgametype;
    }

    public void setIdgametype(String idgametype) {
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
    
    

}
