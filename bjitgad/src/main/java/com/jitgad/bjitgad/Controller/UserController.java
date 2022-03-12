/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.UserDAO;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.UserModel;
import com.jitgad.bjitgad.Models.UserRequestModel;
import com.jitgad.bjitgad.Models.UserTokenRModel;
import com.jitgad.bjitgad.Utilities.ResponseCreateFile;
import com.jitgad.bjitgad.Utilities.ResponseData;
import com.jitgad.bjitgad.Utilities.ResponseValidateToken;
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
    private final FileController fc;

    public UserController() {
        udao = new UserDAO();
        fc = new FileController();
    }

    /**
     * this function validates the entry and receives two variables for user
     * access to the application
     *
     * @return this function returns an object type vector, and receives two
     * variables for user access
     * @param request It is a String variable, this variable will contain the
     * user's mail
     * @throws java.lang.Exception
     */
    public ResponseData LogIn(UserRequestModel request)  throws Exception{
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

    public ResponseData UserRegistration(UserModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setImage(request.getImage() == null ? "" : request.getImage());

        if (udao.comprobeUniqueEmail(request)) {

            request.setPassword(encriptPassword(request.getPassword()));
            request.setCreationdate("NOW()");
            request.setUpdatedate("NOW()");

            if (udao.insertUser(request)) {
                responseData.setMessage("Registros insertados correctamente");
                responseData.setFlag(true);
                return responseData;
            }

        }
        responseData.setMessage("El correo ya se encuentra registrado");
        responseData.setFlag(false);

        return responseData;
    }

    public ResponseData PutUser(UserModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);
        boolean passband = false;

        request.setImage(request.getImage() == null ? "" : request.getImage());

        if (udao.comprobeUniqueEmailUpdate(request)) {

            if (request.getPassword().isEmpty()) {
                request.setPassword(encriptPassword(request.getPassword()));
            } else {
                passband = true;
            }

            request.setUpdatedate("NOW()");

            if (udao.updateUser(request, passband)) {
                responseData.setMessage("Registros actualizados correctamente");
                responseData.setFlag(true);
                return responseData;
            }
        }
        responseData.setMessage("El correo ya se encuentra registrado");
        responseData.setFlag(false);
        return responseData;
    }

    public ResponseData DeleteUser(UserModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        if (udao.deleteUser(request)) {

            responseData.setMessage("Usuario eliminado correctamente");
            responseData.setFlag(true);
            return responseData;
        }

        return responseData;
    }

    public ResponseValidateToken ValidateToken(String user_id, String email, String rol) throws Exception {
        ResponseValidateToken response = new ResponseValidateToken("Correo invalido", false, rol);
        if (!user_id.equals("")) {
            if (!udao.validatetoken(user_id, email)) {
                response.setStatus(true);
                response.setMessage("Token válido");
                return response;
            }
        }
        response.setMessage("Token inválido");
        return response;
    }

    public String encriptPassword(String pwd) throws Exception {
        return DigestUtils.sha256Hex(pwd);
    }

    public ArrayList<UserModel> selectUserspage(int page) throws Exception {
        return udao.selectUserspage(page);
    }

    public String selectUsersbyid(int id) throws Exception {
        return udao.selectUsersbyid(id);
    }

    public int CountingPageUsers() throws Exception {
        return udao.CountingPageUsers();
    }

    public UserTokenRModel BuildToken(UserTokenRModel userTokenRModel, 
            UserRequestModel userRequest) throws Exception {
        String key = Configuration.JWTKEY;
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
