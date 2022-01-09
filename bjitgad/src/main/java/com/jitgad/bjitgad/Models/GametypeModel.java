package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class GametypeModel {
    
    private int idgametype = 0;
    private String name = "";
    private String image = "";
    private String audio_instructions = "";
    private String creationdate = "";
    private String updatedate = "";
    private boolean state = true;
    private String shortname = "";
          
            
    public GametypeModel(){
        
    }
    public GametypeModel(String _name,
            String _image,
            String _audio_instructions,
            String _creationdate,
            String _updatedate,
            boolean _state,
            String _shortname){
    this.name=_name;
    this.image=_image;
    this.audio_instructions=_audio_instructions;
    this.creationdate=_creationdate;
    this.updatedate=_updatedate;
    this.state=_state;
    this.shortname=_shortname;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    
    
    
    
    
}
