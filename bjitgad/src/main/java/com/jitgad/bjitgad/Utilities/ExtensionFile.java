/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jitgad.bjitgad.Utilities;

/**
 *
 * @author jgarc
 */
public class ExtensionFile {
    
    private String Extension;
    private boolean Ext;

    public ExtensionFile() {
    }

    public ExtensionFile(boolean Ext) {
        this.Ext = Ext;
    }

    public ExtensionFile(String Extension, boolean Ext) {
        this.Extension = Extension;
        this.Ext = Ext;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String Extension) {
        this.Extension = Extension;
    }

    public boolean isExt() {
        return Ext;
    }

    public void setExt(boolean Ext) {
        this.Ext = Ext;
    }
    
    
}
