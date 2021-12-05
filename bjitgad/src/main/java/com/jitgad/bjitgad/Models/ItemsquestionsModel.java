
package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class ItemsquestionsModel {
    
   private String idquestions = "-2";
   private String name = "-2";
   private String correct = "-2";
   private String image = "-2";
   private String audio = "-2";
   private String creationdate = "-2";
   private String updatedate = "-2";
   private String state = "-2";

   public ItemsquestionsModel() {
   }
   
   public ItemsquestionsModel(String _idquestions,
           String _name,
           String _correct,
           String _image,
           String _audio,
           String _creationdate,
           String _updatedate,
           String _state) {
    this.idquestions = _idquestions;
    this.name = _name;
    this.correct= _correct;
    this.image= _image;
    this.audio= _audio;
    this.creationdate = _creationdate;
    this.updatedate = _updatedate;
    this.state = _state;
   }

    public String getIdquestions() {
        return idquestions;
    }

    public void setIdquestions(String idquestions) {
        this.idquestions = idquestions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
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
