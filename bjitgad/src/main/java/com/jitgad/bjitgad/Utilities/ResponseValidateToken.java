/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jitgad.bjitgad.Utilities;

/**
 *
 * @author jgarc
 */
public class ResponseValidateToken {
    private String Message;
    private boolean Status;
    private String Rol;

    public ResponseValidateToken() {
    }

    public ResponseValidateToken(String Message, boolean Status, String Rol) {
        this.Message = Message;
        this.Status = Status;
        this.Rol = Rol;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean Status) {
        this.Status = Status;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String Rol) {
        this.Rol = Rol;
    }
    
    
}
