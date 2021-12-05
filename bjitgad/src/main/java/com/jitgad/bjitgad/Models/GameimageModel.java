
package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class GameimageModel {
    
    private String idgame = "-2";
    private String idcolortype = "-2";
    private String image = "-2";
    private String paragraph = "-2";
    private String audio_parag = "-2";
    private String video_parag = "-2";
    private String creationdate = "-2";
    private String updatedate = "-2";
    private String state = "-2";

    public GameimageModel() {
    }
    
    public GameimageModel(String _idgame,
            String _idcolortype,
            String _image,
            String _paragraph,
            String _audio_parag,
            String _video_parag,
            String _creationdate,
            String _updatedate,
            String _state) {
      this.idgame = _idgame;
      this.idcolortype = _idcolortype;
      this.image = _image;
      this.paragraph = _paragraph;
      this.audio_parag = _audio_parag;
      this.video_parag = _video_parag;
      this.creationdate = _creationdate;
      this.updatedate = _updatedate;
      this.state = _state;
      
    }
    public String getIdgame() {
        return idgame;
    }

    public void setIdgame(String idgame) {
        this.idgame = idgame;
    }

    public String getIdcolortype() {
        return idcolortype;
    }

    public void setIdcolortype(String idcolortype) {
        this.idcolortype = idcolortype;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String getAudio_parag() {
        return audio_parag;
    }

    public void setAudio_parag(String audio_parag) {
        this.audio_parag = audio_parag;
    }

    public String getVideo_parag() {
        return video_parag;
    }

    public void setVideo_parag(String video_parag) {
        this.video_parag = video_parag;
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
