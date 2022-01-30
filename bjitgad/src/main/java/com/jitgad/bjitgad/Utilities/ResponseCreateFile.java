/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jitgad.bjitgad.Utilities;

/**
 *
 * @author jgarc
 */
public class ResponseCreateFile {
    private boolean State;
    private String RutaRelativa;
    private String NombreArchivo;

    public ResponseCreateFile() {
    }

    public ResponseCreateFile(boolean State, String RutaRelativa, String NombreArchivo) {
        this.State = State;
        this.RutaRelativa = RutaRelativa;
        this.NombreArchivo = NombreArchivo;
    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean State) {
        this.State = State;
    }

    public String getRutaRelativa() {
        return RutaRelativa;
    }

    public void setRutaRelativa(String RutaRelativa) {
        this.RutaRelativa = RutaRelativa;
    }

    public String getNombreArchivo() {
        return NombreArchivo;
    }

    public void setNombreArchivo(String NombreArchivo) {
        this.NombreArchivo = NombreArchivo;
    }
    
    
}
