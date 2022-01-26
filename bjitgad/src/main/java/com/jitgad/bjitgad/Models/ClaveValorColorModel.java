package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class ClaveValorColorModel {
    
    private int id;
    private String name;
    private String hexcode;

    public ClaveValorColorModel() {
    }

    public ClaveValorColorModel(int id, String name, String hexcode) {
        this.id = id;
        this.name = name;
        this.hexcode = hexcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHexcode() {
        return hexcode;
    }

    public void setHexcode(String hexcode) {
        this.hexcode = hexcode;
    }

    
    
    
    
    
    
}
