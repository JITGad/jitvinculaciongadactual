package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class UserTokenRModel {

    private int iduser = 0;
    private String names = "";
    private String last_name = "";
    private String email = "";
    private String image = "";
    private String rol = "";
    private String user_token = "";

    public UserTokenRModel() {
        
    }

    public UserTokenRModel(int iduser, String names, String last_name, String email, String image, String rol, String user_token) {
        this.iduser = iduser;
        this.names = names;
        this.last_name = last_name;
        this.email = email;
        this.image = image;
        this.rol = rol;
        this.user_token = user_token;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    } 
    
    
}
