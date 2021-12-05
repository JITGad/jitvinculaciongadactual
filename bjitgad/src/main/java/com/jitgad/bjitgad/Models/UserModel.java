package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class UserModel {

    private String iduser = "-2";
    private String names = "-2";
    private String last_name = "-2";
    private String email = "-2";
    private String password = "-2";
    private String image = "-2";
    private String birthdate = "-2";
    private String rol = "-2";
    private String creationdate = "-2";
    private String updatedate = "-2";
    private String state = "-2";

    public UserModel() {
    }
    
    public UserModel(String _iduser,
            String _names,
            String _last_name,
            String _email,
            String _password,
            String _image,
            String _birthdate,
            String _rol,
            String _creationdate,
            String _updatedate,
            String _state) {
        
        this.iduser = _iduser; 
        this.names = _names;
        this.last_name = _last_name;
        this.email = _email;
        this.password = _password;
        this.image = _image;
        this.birthdate = _birthdate;
        this.rol = _rol;
        this.creationdate = _creationdate;
        this.updatedate = _updatedate;
        this.state = _state;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
}
