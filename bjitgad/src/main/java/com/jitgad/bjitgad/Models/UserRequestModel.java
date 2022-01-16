/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jitgad.bjitgad.Models;

/**
 *
 * @author jgarc
 */
public class UserRequestModel {
 
    private Boolean recuerdame;
    
    private String email;
    
    private String password;

    public UserRequestModel() {
    }

    public UserRequestModel(Boolean recuerdame, String email, String password) {
        this.recuerdame = recuerdame;
        this.email = email;
        this.password = password;
    }

    public Boolean getRecuerdame() {
        return recuerdame;
    }

    public void setRecuerdame(Boolean recuerdame) {
        this.recuerdame = recuerdame;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
