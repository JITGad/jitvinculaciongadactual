package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class ColortypeModel {

    private int idcolortype = 0;
    private String name = "";
    private String html = "";
    private String creationdate = "";
    private String updatedate = "";
    private boolean state = true;

    public ColortypeModel() {
    }
    
    public ColortypeModel(int _idcolortype,String _name,
            String _html,
            String _creationdate,
            String _updatedate,
            boolean _state) {
        this.idcolortype = _idcolortype;
        this.name = _name;
        this.html = _html;
        this.creationdate = _creationdate;
        this.updatedate = _updatedate;
        this.state = _state;
    }

    public int getIdcolortype() {
        return idcolortype;
    }

    public void setIdcolortype(int idcolortype) {
        this.idcolortype = idcolortype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
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
