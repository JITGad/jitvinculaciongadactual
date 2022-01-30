/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jitgad.bjitgad.Utilities;

/**
 *
 * @author jgarc
 */
public class ValidateFormat {
    private boolean FormatValid;
    private String RutaAbsoluta;
    private String RutaRelativa;

    public ValidateFormat(boolean FormatValid) {
        this.FormatValid = FormatValid;
    }

    public ValidateFormat(boolean FormatValid, String RutaAbsoluta, String RutaRelativa) {
        this.FormatValid = FormatValid;
        this.RutaAbsoluta = RutaAbsoluta;
        this.RutaRelativa = RutaRelativa;
    }

    public ValidateFormat() {
    }

    public boolean isFormatValid() {
        return FormatValid;
    }

    public void setFormatValid(boolean FormatValid) {
        this.FormatValid = FormatValid;
    }

    public String getRutaAbsoluta() {
        return RutaAbsoluta;
    }

    public void setRutaAbsoluta(String RutaAbsoluta) {
        this.RutaAbsoluta = RutaAbsoluta;
    }

    public String getRutaRelativa() {
        return RutaRelativa;
    }

    public void setRutaRelativa(String RutaRelativa) {
        this.RutaRelativa = RutaRelativa;
    }
    
}
