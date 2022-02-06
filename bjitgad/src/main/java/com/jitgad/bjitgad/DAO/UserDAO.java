package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.UserModel;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jorge
 */
public class UserDAO {

    private final ConectionPool con;
    String sentence;

    public UserDAO() {
        con = ConectionPoolDataSource.getConnection();
    }

    public ArrayList<UserModel> selectUserspage(int page) throws Exception {
        sentence = "select iduser,names,last_name, email,image, birthdate, rol, creationdate, updatedate, state from tbluser order by iduser asc limit 10 offset " + (page * 10 - 10);
        ArrayList<UserModel> datos = con.getObjectDB(sentence, UserModel.class, 1);
//        for (int i = 0; i < datos.size(); i++) {
//            datos.get(i).setImage((Configuration.ipdominioservidor + datos.get(i).getImage()).replace('\\', '/'));
//        }
        return datos;
    }

    public String selectUsersbyid(int id) throws Exception {
        sentence = "select iduser,names,last_name, email,image, birthdate, rol, state from tbluser where iduser =" + id;
        ArrayList<UserModel> datos = con.getObjectDB(sentence, UserModel.class, 1);
//        for (int i = 0; i < datos.size(); i++) {
//            datos.get(i).setImage((Configuration.ipdominioservidor + datos.get(i).getImage()).replace('\\', '/'));
//        }
        if (datos.size() > 0) {
            return Methods.objectToJsonString(datos.get(0));
        } else {
            return "{}";
        }
    }

    public int CountingPageUsers() throws Exception {
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

    public boolean comprobeUniqueEmail(UserModel usr) {
        String sentency = String.format("select * from tbluser where email='%s';", usr.getEmail());
        System.out.println(sentency);
        System.out.println("");
        return (((con.returnRecord(sentency)).getRowCount() <= 0));
    }

    public boolean comprobeUniqueEmailUpdate(UserModel usr) {
        System.out.println(usr.getEmail().trim());
        System.out.println(usr.getIduser());
        String sentency = String.format("select * from tbluser where email='%s' and iduser != '%s';", usr.getEmail(), usr.getIduser());
        System.out.println(sentency);
        System.out.println("");
        return (((con.returnRecord(sentency)).getRowCount() <= 0));
    }

    public boolean validatetoken(String iduser, String email) {
        String sentency = String.format("select * from tbluser where email='%s' and iduser='%s';", email, iduser);
        return (((con.returnRecord(sentency)).getRowCount() <= 0));
    }

    public boolean insertUser(UserModel userM) throws SQLException {
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

    public boolean updateUser(UserModel userM, boolean passband) throws SQLException {
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
        if (Configuration.DEBUG) {
            System.out.println(structure);    
        }
        return con.modifyBD(sentency);
    }

    public boolean deleteUser(UserModel userM) throws SQLException {
        String structure = String.format(
                "<user>"
                + "<iduser>" + userM.getIduser() + "</iduser>"
                + "</user>");

        String sentency = "Select * from deleteUser('" + structure + "')";
        if (Configuration.DEBUG) {
            System.out.println(structure);    
        }
        return con.modifyBD(sentency);
    }

    public UserModel getUserEmail(String email) throws Exception {
        ArrayList<UserModel> datos = con.getObjectDB("select * from tbluser where email='" + email + "'", UserModel.class, 1);
        if (datos.size() > 0) {
            return datos.get(0);
        }
        return null;
    }
}
