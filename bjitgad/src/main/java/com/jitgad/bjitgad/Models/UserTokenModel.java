package com.jitgad.bjitgad.Models;

/**
 *
 * @author jorge
 */
public class UserTokenModel extends UserModel {
    
    private String user_token;

    public UserTokenModel() {
        super();
    }

    public UserTokenModel(int _iduser, String _names, String _last_name, String _email, String _password, String _image, String _birthdate, String _rol, String _creationdate, String _updatedate, boolean _state) {
        super(_iduser, _names, _last_name, _email, _password, _image, _birthdate, _rol, _creationdate, _updatedate, _state);
    }
    
    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }  
}
