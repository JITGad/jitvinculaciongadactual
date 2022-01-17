/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.UserDAO;
import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.UserModel;
import com.jitgad.bjitgad.Models.UserRequestModel;
import com.jitgad.bjitgad.Models.UserTokenRModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author jorge In this class, you validate the login and the creation of a new
 * user.
 */
public class UserController {

    private final UserDAO udao;
    private final UserModel um;

    public UserController() {
        udao = new UserDAO();
        um = new UserModel();
    }

    /**
     * this function validates the entry and receives two variables for user
     * access to the application
     *
     * @return this function returns an object type vector, and receives two
     * variables for user access
     * @param request It is a String variable, this variable will contain the
     * user's mail
     */
    public ResponseData LogIn(UserRequestModel request) {
        UserModel userDB = udao.getUserEmail(request.getEmail());
        ResponseData responseData = new ResponseData("Usuario no encontrado", false);
        if (userDB != null) {
            responseData.setMessage("Contraseña incorrecta");
            if (encriptPassword(request.getPassword()).equals(userDB.getPassword())) {
                responseData.setMessage("Acceso consedido.");
                responseData.setFlag(true);
                responseData.setData(Methods.StringJsonToObject(Methods.objectToJsonString(userDB), UserTokenRModel.class));
            }
        }
        return responseData;
    }
    
    public Object[] UserRegistration(String name, String last_name,
            String email, String password, String image, String birthday,
            String rol, String state) {
        String message = "Correo inválido";
        boolean status = false;
        um.setNames(name);
        um.setLast_name(last_name);
        um.setEmail(email);
        um.setPassword(encriptPassword(password));
        um.setImage(image);
        um.setBirthdate(birthday);
        um.setRol(rol);
        um.setCreationdate("NOW()");
        um.setUpdatedate("NOW()");
        um.setState(Boolean.parseBoolean(state));
        if (udao.comprobeUniqueEmail(um)) {
            if (udao.insertUser(um)) {
                message = "Usuario registrado";
                status = true;
            } else {
                message = "Usuario no registrado";
                status = false;
            }
        } else {
            message = "El correo ya se encuentra registrado";
            status = false;
        }
        return new Object[]{status, message};
    }

    public Object[] PutUser(int iduser,String name, String last_name,
            String email, String password, String image, String birthday,
            String rol, String state) {
        String message = "Correo inválido";
        boolean status = false;
        boolean passband = false;
        um.setIduser(iduser);
        um.setNames(name);
        um.setLast_name(last_name);
        um.setEmail(email);
        if(!password.isEmpty())
        {
          um.setPassword(encriptPassword(password));
        }
        else{
          passband = true;  
        }
        um.setImage(image);
        um.setBirthdate(birthday);
        um.setRol(rol);
        um.setUpdatedate("NOW()");
        um.setState(Boolean.parseBoolean(state));
        if (udao.comprobeUniqueEmailUpdate(um)) {
            if (udao.updateUser(um,passband)) {
                message = "Usuario actualizado con éxito";
                status = true;
            } else {
                message = "Usuario no actualizado, ocurrió un error";
                status = false;
            }
        } else {
            message = "El correo ya se encuentra registrado";
            status = false;
        }
        return new Object[]{status, message};
    }

    public Object[] DeleteUser(int iduser){
        String message = "";
        boolean status = false;
        um.setIduser(iduser);
        
        if (udao.deleteUser(um)) {
            message = "Usuario eliminado correctamente";
            status = true;
        } else {
            message = "El usuario no fué eliminado, ocurrió un error";
            status = false;
        }
        
        return new Object[]{status, message};
    }

    public Object[] ValidateToken(String user_id, String email, String rol) {
        String message = "Correo inválido";
        boolean status = false;
            
        if (!user_id.equals("")) {
            if (!udao.validatetoken(user_id, email)) {
                status = true;
                message = "Token válido";
            } else {
                status = false;
                message = "Token inválido";
            }
        }else{
            status = false;
            message = "Token inválido";
        }

        return new Object[]{status, message, rol};
    }

    public String encriptPassword(String pwd) {
        return DigestUtils.sha256Hex(pwd);
    }
    
    public String selectUserspage(int page) {
        return udao.selectUserspage(page);
    }
    public String selectUsersbyid(int id) {
        return udao.selectUsersbyid(id);
    }
    
    public int CountingPageUsers() {
        return udao.CountingPageUsers();
    }

    public UserTokenRModel BuildToken(UserTokenRModel userTokenRModel, UserRequestModel userRequest) {
        String key = Configuration.dbprivatekey;
        long tiempo = System.currentTimeMillis();
        long tiempoext;
        if (userRequest.getRecuerdame()) {
            // 10 días
            tiempoext = 864000000;
        } else {
            // 1 día
            tiempoext = 86400000;
        }
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, key)
                .setSubject(String.valueOf(userTokenRModel.getIduser()))
                .setIssuedAt(new Date(tiempo))
                .setExpiration(new Date(tiempo + tiempoext))
                .claim("email", userTokenRModel.getEmail())
                .claim("rol", userTokenRModel.getRol())
                .compact();

        userTokenRModel.setUser_token(jwt);

        return userTokenRModel;
    }

}
