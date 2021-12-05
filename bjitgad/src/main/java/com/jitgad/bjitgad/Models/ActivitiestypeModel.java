package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class ActivitiestypeModel {
    
    private String idactivitiestype = "-2";
    private String name = "-2";
    private String image = "-2";
    private String creationdate = "-2";
    private String updatedate = "-2";
    private String state = "-2";

    public ActivitiestypeModel() {
    }

    public ActivitiestypeModel(String Idactivitiestype,
            String Name,
            String Image,
            String Creationdate,
            String Updatedate,
            String State) {
        this.idactivitiestype = Idactivitiestype;
        this.name = Name;
        this.image = Image;
        this.creationdate = Creationdate;
        this.updatedate = Updatedate;
        this.state = State;
    }

    public String getIdactivitiestype() {
        return idactivitiestype;
    }

    public void setIdactivitiestype(String idactivitiestype) {
        this.idactivitiestype = idactivitiestype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
