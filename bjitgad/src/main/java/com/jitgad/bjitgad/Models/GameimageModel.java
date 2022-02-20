package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class GameimageModel {

    private int idgameimage;
    private int idgame;
    private int idcolortype;
    private String color;
    private String html;
    private String image;
    private String paragraph;
    private String audio_parag;
    private String video_parag;
    private String creationdate;
    private String updatedate;
    private boolean state;

    public GameimageModel() {
    }

    public GameimageModel(int idgameimage, int idgame, int idcolortype, String color, String html, String image, String paragraph, String audio_parag, String video_parag, String creationdate, String updatedate, boolean state) {
        this.idgameimage = idgameimage;
        this.idgame = idgame;
        this.idcolortype = idcolortype;
        this.color = color;
        this.html = html;
        this.image = image;
        this.paragraph = paragraph;
        this.audio_parag = audio_parag;
        this.video_parag = video_parag;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.state = state;
    }
    

    public int getIdgameimage() {
        return idgameimage;
    }

    public void setIdgameimage(int idgameimage) {
        this.idgameimage = idgameimage;
    }

    public int getIdgame() {
        return idgame;
    }

    public void setIdgame(int idgame) {
        this.idgame = idgame;
    }

    public int getIdcolortype() {
        return idcolortype;
    }

    public void setIdcolortype(int idcolortype) {
        this.idcolortype = idcolortype;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image != null ? image.replace("\\", "/") : image;
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
        this.audio_parag = audio_parag != null ? audio_parag.replace("\\", "/") : audio_parag;
    }

    public String getVideo_parag() {
        return video_parag;
    }

    public void setVideo_parag(String video_parag) {
        this.video_parag = video_parag != null ? video_parag.replace("\\", "/") : video_parag;
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
