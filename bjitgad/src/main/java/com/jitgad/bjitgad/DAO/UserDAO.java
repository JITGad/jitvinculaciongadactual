package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.Models.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
    
    public String selectUserAll() {
        sentence = "select * from tbluser";
        String json = con.getRecordsInJson(sentence);
        return json;
    }

    public UserModel setUser(DefaultTableModel table, int index) {
        UserModel usr = new UserModel();
        usr.setIduser(table.getValueAt(index, 0).toString());
        usr.setNames(table.getValueAt(index, 1).toString());
        usr.setLast_name(table.getValueAt(index, 2).toString());
        usr.setEmail(table.getValueAt(index, 3).toString());
        usr.setPassword(table.getValueAt(index, 4).toString());
        usr.setImage(table.getValueAt(index, 5).toString());
        usr.setBirthdate(table.getValueAt(index, 6).toString());
        usr.setRol(table.getValueAt(index, 7).toString());
        usr.setCreationdate(table.getValueAt(index, 8).toString());
        usr.setUpdatedate(table.getValueAt(index, 9).toString());
        usr.setState(table.getValueAt(index, 10).toString());
        return usr;
    }  
    public String userDataJson(UserModel usr) {
        String key = DataBd.dbprivatekey;
        long tiempo = System.currentTimeMillis();
//        System.out.println(new Date(tiempo) +"-" + new Date(tiempo+900000));
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, key)
                .setSubject(usr.getIduser())
                .setIssuedAt(new Date(tiempo))
                .setExpiration(new Date(tiempo + 900000))
                .compact();
        JsonObjectBuilder jsoB = Json.createObjectBuilder();
        jsoB.add("iduser", usr.getIduser());
        jsoB.add("names", usr.getNames());
        jsoB.add("last_name", usr.getLast_name());
        jsoB.add("email", usr.getEmail());
      //  jsoB.add("password", usr.getPassword());
        jsoB.add("image", usr.getImage());
      //  jsoB.add("birthdate", usr.getBirthdate());
        jsoB.add("rol", usr.getRol());
     //   jsoB.add("creationdate", usr.getCreationdate());
     //   jsoB.add("updatedate", usr.getUpdatedate());
     //   jsoB.add("state", usr.getState());
        jsoB.add("user_token", jwt);
        javax.json.JsonObject jsonObj = jsoB.build();
        return jsonObj.toString();
    }
    
    public boolean comprobeUniqueEmail(UserModel usr) {
        String sentency = String.format("select * from tbluser where email='%s';", usr.getEmail());
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
}
