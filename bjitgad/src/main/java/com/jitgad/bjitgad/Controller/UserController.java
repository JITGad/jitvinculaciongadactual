/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.UserDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.UserModel;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author jorge In this class, you validate the login and the creation of a new
 * user.
 */
public class UserController {

    private Conection conex;
    UserDAO udao;
    UserModel um;

    public UserController() {
        conex = new Conection();
        udao = new UserDAO();
        um = new UserModel();
    }

    /**
     * this function validates the entry and receives two variables for user
     * access to the application
     *
     * @return this function returns an object type vector, and receives two
     * variables for user access
     * @param email It is a String variable, this variable will contain the
     * user's mail
     * @param pwd It is a String variable, this variable will contain the
     * encrypted password of the user
     */
    
    public Object[] LogIn(String email, String pwd) {
        DefaultTableModel table = conex.returnRecord("select * from tbluser where email='" + email + "'");
        String message = "Usuario no encontrado";
        boolean status = false;
        UserModel usr = new UserModel();
        if (table.getRowCount() > 0) {
            message = "Contraseña incorrecta";
            for (int index = 0; index < table.getRowCount(); index++) {
                System.out.println("-" + encriptPassword(pwd) + "-");
                System.out.println("-" + table.getValueAt(index, 4).toString() + "-");
                System.out.println(encriptPassword(pwd).equals(table.getValueAt(index, 4).toString()));
                if (encriptPassword(pwd).equals(table.getValueAt(index, 4).toString())) {
                    usr = udao.setUser(table, index);
                    message = "Acceso consedido.";
                    status = true;
                }
            }
        }
        return new Object[]{status, message, usr};
    }

    public Object[] UserRegistration(String name, String last_name,
            String email, String password, String image, String birthday,
            String rol, String creationdate, String updatedate,
            String state) {
        String message = "Correo inválido";
        boolean status = false;
        um.setNames(name);
        um.setLast_name(last_name);
        um.setEmail(email);
        um.setPassword(encriptPassword(password));
        um.setImage(image);
        um.setBirthdate(birthday);
        um.setRol(rol);
        um.setCreationdate(creationdate);
        um.setUpdatedate(updatedate);
        um.setState(state);
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

    public String encriptPassword(String pwd) {
        return DigestUtils.sha256Hex(pwd);
    }
}
