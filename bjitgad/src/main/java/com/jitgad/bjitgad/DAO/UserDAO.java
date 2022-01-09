package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jorge
 */
public class UserDAO {

    Conection con;
    String sentence;

    public UserDAO() {
        con = new Conection();
    }
    
    public String selectUserspage(int page) {
        sentence = "select iduser,names,last_name, email,image, birthdate, rol, creationdate, updatedate, state from tbluser order by iduser asc limit 10 offset " + (page * 10 - 10);
        ArrayList<UserModel> datos = con.getObjectDB(sentence, UserModel.class, 1);
//        datos.forEach(proyecto -> {
//            proyecto.setShare_users(getEmailsToProyectUserAdmin(proyecto.getProjects_id_pr(), id));
//        });
        return Methods.objectToJsonString(datos);
    }

    public String selectUsersbyid(int id) {
        sentence = "select iduser,names,last_name, email,image, birthdate, rol, state from tbluser where iduser ="+ id;
        ArrayList<UserModel> datos = con.getObjectDB(sentence, UserModel.class, 1);
        if (datos.size() > 0) {
            return Methods.objectToJsonString(datos.get(0));
        } else {
            return "{}";
        }
    }

    public int CountingPageUsers() {
        sentence = String.format("select * from tbluser");
        return ((con.returnRecord(sentence)).getRowCount());
    }

    public UserModel setUser(DefaultTableModel table, int index) {
        UserModel usr = new UserModel();
        usr.setIduser(Integer.parseInt(table.getValueAt(index, 0).toString()));
        usr.setNames(table.getValueAt(index, 1).toString());
        usr.setLast_name(table.getValueAt(index, 2).toString());
        usr.setEmail(table.getValueAt(index, 3).toString());
        usr.setPassword(table.getValueAt(index, 4).toString());
        usr.setImage(table.getValueAt(index, 5).toString());
        usr.setBirthdate(table.getValueAt(index, 6).toString());
        usr.setRol(table.getValueAt(index, 7).toString());
        usr.setCreationdate(table.getValueAt(index, 8).toString());
        usr.setUpdatedate(table.getValueAt(index, 9).toString());
        usr.setState(Boolean.parseBoolean(table.getValueAt(index, 10).toString()));
        return usr;
    }

    public String userDataJson(UserModel usr, String rec) {
        String key = DataBd.dbprivatekey;
        long tiempo = System.currentTimeMillis();
        long tiempoext = 0;
        if (!rec.isEmpty()) {
            if (rec.equals(true)) {
                // 10 días
                tiempoext = 864000000;
            } else {
                // 1 día
                tiempoext = 86400000;
            }
        } else {
            // 1 día
            tiempoext = 86400000;
        }
//        System.out.println(new Date(tiempo) +"-" + new Date(tiempo+900000));
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, key)
                .setSubject(String.valueOf(usr.getIduser()))
                .setIssuedAt(new Date(tiempo))
                //900000 que equivale a 15 minutos
                //   .setExpiration(new Date(tiempo + 900000))
                .setExpiration(new Date(tiempo + tiempoext))
                .claim("email", usr.getEmail())
                .claim("rol", usr.getRol())
                .compact();
        JsonObjectBuilder jsoB = Json.createObjectBuilder();
        jsoB.add("iduser", usr.getIduser());
        jsoB.add("names", usr.getNames());
        jsoB.add("last_name", usr.getLast_name());
        jsoB.add("email", usr.getEmail());
        jsoB.add("image", usr.getImage());
        jsoB.add("rol", usr.getRol());
        jsoB.add("user_token", jwt);
        javax.json.JsonObject jsonObj = jsoB.build();
        return jsonObj.toString();
    }

    public boolean comprobeUniqueEmail(UserModel usr) {
        String sentency = String.format("select * from tbluser where email='%s';", usr.getEmail());
        return (((con.returnRecord(sentency)).getRowCount() <= 0));
    }
    
    public boolean comprobeUniqueEmailUpdate(UserModel usr) {
        String sentency = String.format("select * from tbluser where email='%s' and iduser != '%s';", usr.getEmail(), usr.getIduser());
        return (((con.returnRecord(sentency)).getRowCount() <= 0));
    }
    
    
    

    public boolean validatetoken(String iduser, String email) {
        String sentency = String.format("select * from tbluser where email='%s' and iduser='%s';", email, iduser);
        return (((con.returnRecord(sentency)).getRowCount() <= 0));
    }

    public boolean insertUser(UserModel userM) {
        String structure = String.format(
                "<user>"
                + "<names>" + userM.getNames() + "</names>"
                + "<last_name>" + userM.getLast_name() + "</last_name>"
                + "<email>" + userM.getEmail() + "</email>"
                + "<password>" + userM.getPassword() + "</password>"
                + "<image>" + userM.getImage() + "</image>"
                + "<birthdate>" + userM.getBirthdate() + "</birthdate>"
                + "<rol>" + userM.getRol() + "</rol>"
                + "<creationdate>" + userM.getCreationdate() + "</creationdate>"
                + "<updatedate>" + userM.getUpdatedate() + "</updatedate>"
                + "<state>" + userM.getState() + "</state>"
                + "</user>");

        String sentency = "Select * from insertuser('" + structure + "')";
        System.out.println(structure);
        return con.modifyBD(sentency);
    }

    public boolean updateUser(UserModel userM, boolean passband) {
        String structure = String.format(
                "<user>"
                + "<iduser>" + userM.getIduser() + "</iduser>"
                + "<names>" + userM.getNames() + "</names>"
                + "<last_name>" + userM.getLast_name() + "</last_name>"
                + "<email>" + userM.getEmail() + "</email>"
                + "<password>" + userM.getPassword() + "</password>"
                + "<image>" + userM.getImage() + "</image>"
                + "<birthdate>" + userM.getBirthdate() + "</birthdate>"
                + "<rol>" + userM.getRol() + "</rol>"
                + "<updatedate>" + userM.getUpdatedate() + "</updatedate>"
                + "<state>" + userM.getState() + "</state>"
                + "<passband>" + userM.getState() + "</passband>"        
                + "</user>");

        String sentency = "Select * from updateUser('" + structure + "')";
        System.out.println(structure);
        return con.modifyBD(sentency);
    }

    public boolean deleteUser(UserModel userM) {
        String structure = String.format(
                "<user>"
                + "<iduser>" + userM.getIduser() + "</iduser>"
                + "</user>");

        String sentency = "Select * from deleteUser('" + structure + "')";
        System.out.println(structure);
        return con.modifyBD(sentency);
    }
}
