package com.jitgad.bjitgad.Models;

import java.util.List;

/**
 *
 * @author jorge
 */
public class GametypeModel {

    private int idgametype = 0;
    private String name = "";
    private String image = "";
    private String audio_instructions = "";
    private String text_instructions = "";
    private String video_instructions = "";
    private String shortname = "";
    private String creationdate = "";
    private String updatedate = "";
    private boolean state = true;
    public List<GameModel> detalles;

    public GametypeModel() {
    }

    public GametypeModel(int idgametype, String name, String image, String audio_instructions, String text_instructions, String video_instructions, String shortname, String creationdate, String updatedate, boolean state, List<GameModel> detalles) {
        this.idgametype = idgametype;
        this.name = name;
        this.image = image;
        this.audio_instructions = audio_instructions;
        this.text_instructions = text_instructions;
        this.video_instructions = video_instructions;
        this.shortname = shortname;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.state = state;
        this.detalles = detalles;
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
        this.image = image != null ? image.replace("\\", "/") : image;
    }

    public String getAudio_instructions() {
        return audio_instructions;
    }

    public void setAudio_instructions(String audio_instructions) {
        this.audio_instructions = audio_instructions != null ? audio_instructions.replace("\\", "/") : audio_instructions;
    }

    public String getText_instructions() {
        return text_instructions;
    }

    public void setText_instructions(String text_instructions) {
        this.text_instructions = text_instructions;
    }

    public String getVideo_instructions() {
        return video_instructions;
    }

    public void setVideo_instructions(String video_instructions) {
        this.video_instructions = video_instructions != null ? video_instructions.replace("\\", "/") : video_instructions;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
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

    public List<GameModel> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<GameModel> detalles) {
        this.detalles = detalles;
    }
    
    

}   
