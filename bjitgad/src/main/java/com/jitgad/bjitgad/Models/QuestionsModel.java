
package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class QuestionsModel {
    
    private String idgame = "-2";
    private String name = "-2";
    private String creationdate = "-2";
    private String updatedate = "-2";
    private String state = "-2";

    public QuestionsModel() {
    }
    
    public QuestionsModel(String _idgame,
            String _name,
            String _creationdate,
            String _updatedate,
            String _state) {
        this.idgame=_idgame;
        this.name=_name;
        this.creationdate=_creationdate;
        this.updatedate=_updatedate;
        this.state=_state;
    }

    public String getIdgame() {
        return idgame;
    }

    public void setIdgame(String idgame) {
        this.idgame = idgame;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    
}
