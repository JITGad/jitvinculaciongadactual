package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class DetailsimageModel {

    private String idgameimage = "-2";
    private String clipping_type = "-2";
    private String image = "-2";
    private String creationdate = "-2";
    private String updatedate = "-2";
    private String state = "-2";

    public DetailsimageModel() {
    }

    public DetailsimageModel(String _idgameimage,
            String _clipping_type,
            String _image,
            String _creationdate,
            String _updatedate,
            String _state) {
        this.idgameimage = _idgameimage;
        this.clipping_type = _clipping_type;
        this.image = _image;
        this.creationdate = _creationdate;
        this.creationdate = _updatedate;
        this.state = _state;
    }

    public String getIdgameimage() {
        return idgameimage;
    }

    public void setIdgameimage(String idgameimage) {
        this.idgameimage = idgameimage;
    }

    public String getClipping_type() {
        return clipping_type;
    }

    public void setClipping_type(String clipping_type) {
        this.clipping_type = clipping_type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image.replace("\\", "/");
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
