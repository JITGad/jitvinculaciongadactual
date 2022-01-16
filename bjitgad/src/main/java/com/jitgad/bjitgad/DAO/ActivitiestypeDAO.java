package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
import com.jitgad.bjitgad.Utilities.InetAddressUtil;
import com.jitgad.bjitgad.Utilities.UFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class ActivitiestypeDAO {

    Conection con;
    String sentence;
    UFile uf;
    InetAddressUtil ipreal;

    public ActivitiestypeDAO() {
        con = new Conection();
        uf = new UFile();
        ipreal = new InetAddressUtil();
    }

    public ArrayList<ActivitiestypeModel> selectActivitiestype(String path) {

        sentence = "select * from tblactivitiestype where state = true";
        ArrayList<ActivitiestypeModel> datos = con.getObjectDB(sentence, ActivitiestypeModel.class, 1);
        for (int i = 0; i < datos.size(); i++) {
            datos.get(i).setImage((ipreal.getHostIp() + ":8080/bjitgad/" + datos.get(i).getImage()).replace('\\', '/'));
        }
        return datos;
    }

    public ArrayList<ActivitiestypeModel> selectActivitiestypepage(int page) {
        sentence = "select * from tblactivitiestype order by idactivitiestype asc limit 10 offset " + (page * 10 - 10);
        ArrayList<ActivitiestypeModel> datos = con.getObjectDB(sentence, ActivitiestypeModel.class, 1);
        return datos;
    }

    public int CountingPageActivitiestype() {
        sentence = String.format("select idactivitiestype as id,name as tema,image as urlimagen from tblactivitiestype");
        return ((con.returnRecord(sentence)).getRowCount());
    }

    public String selectactivitiesbyid(int idactivity) {
        sentence = "select * from tblactivitiestype where idactivitiestype =" + idactivity;
        ArrayList<ActivitiestypeModel> datos = con.getObjectDB(sentence, ActivitiestypeModel.class, 1);
        if (datos.size() > 0) {
            return Methods.objectToJsonString(datos.get(0));
        } else {
            return "{}";
        }

    }

    public int selectIDActivitiestype() {
        sentence = "";
        String id = con.getNextID(sentence);
        return 1;
    }

    public boolean insertActividadestype(ActivitiestypeModel activitiestypemodel) {
        String structure = String.format(
                "<actividadestype>"
                + "<name>" + activitiestypemodel.getName() + "</name>"
                + "<image>" + activitiestypemodel.getImage() + "</image>"
                + "<creationdate>" + activitiestypemodel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + activitiestypemodel.getUpdatedate() + "</updatedate>"
                + "<state>" + activitiestypemodel.getState() + "</state>"
                + "</actividadestype>");

        String sentency = "Select * from insertActividadestype('" + structure + "')";
        // System.out.println(structure);
        return con.modifyBD(sentency);
    }

    public boolean updateActividadestype(ActivitiestypeModel activitiestypemodel) {
        String structure = String.format(
                "<actividadestype>"
                + "<idactivitiestype>" + activitiestypemodel.getIdactivitiestype() + "</idactivitiestype>"
                + "<name>" + activitiestypemodel.getName() + "</name>"
                + "<image>" + activitiestypemodel.getImage() + "</image>"
                + "<updatedate>" + activitiestypemodel.getUpdatedate() + "</updatedate>"
                + "<state>" + activitiestypemodel.getState() + "</state>"
                + "</actividadestype>");

        String sentency = "Select * from updateActividadestype('" + structure + "')";
        // System.out.println(structure);
        return con.modifyBD(sentency);
    }

    public boolean DeleteActividadestype(ActivitiestypeModel activitiestypemodel) {
        String structure = String.format(
                "<actividadestype>"
                + "<idactivitiestype>" + activitiestypemodel.getIdactivitiestype() + "</idactivitiestype>"
                + "</actividadestype>");

        String sentency = "Select * from deleteActividadestype('" + structure + "')";
        // System.out.println(structure);
        return con.modifyBD(sentency);
    }

}
