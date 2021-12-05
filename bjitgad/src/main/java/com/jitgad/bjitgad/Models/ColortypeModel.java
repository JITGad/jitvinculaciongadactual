package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class ColortypeModel {

    private String color = "-2";
    private String creationdate = "-2";
    private String updatedate = "-2";
    private String state = "-2";

    public ColortypeModel(String _color,
            String _creationdate,
            String _updatedate,
            String _state) {
        this.color = _color;
        this.creationdate = _creationdate;
        this.updatedate = _updatedate;
        this.state = _state;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
