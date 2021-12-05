
package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class GameModel {

    private String idactivitiestype = "-2";
    private String idgametype = "-2";
    private String name = "-2";
    private String audio_instructions = "-2";
    private String creationdate = "-2";
    private String updatedate = "-2";
    private String state = "-2";

    public GameModel() {
    }

    public GameModel(String _idactivitiestype,
            String _idgametype,
            String _name,
            String _audio_instructions,
            String _creationdate,
            String _updatedate,
            String _state) {
        this.idactivitiestype = _idactivitiestype;
        this.idgametype = _idgametype;
        this.name = _name;
        this.audio_instructions = _audio_instructions;
        this.creationdate = _creationdate;
        this.updatedate = _updatedate;
        this.state = _state;
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

    public String getAudio_instructions() {
        return audio_instructions;
    }

    public void setAudio_instructions(String audio_instructions) {
        this.audio_instructions = audio_instructions;
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
