package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class ClaveValorColorModel {
    
    private int id;
    private String text;
    private String value;

    public ClaveValorColorModel() {
    }

    public ClaveValorColorModel(int id, String text, String value) {
        this.id = id;
        this.text = text;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

   

    
    
    
    
    
    
}
