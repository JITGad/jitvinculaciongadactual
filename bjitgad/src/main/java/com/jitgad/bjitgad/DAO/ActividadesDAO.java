package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.ActividadesModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jorge
 */
public class ActividadesDAO {

    Conection con;
    String sentence;

    public ActividadesDAO() {
        con = new Conection();
    }

    public String selectActividadesDAO() {
        sentence = "select * from actividades";
        Conection con = new Conection();
        String json = con.getRecordsInJson(sentence);
        return json;
    }
    
    public boolean insertActividad(ActividadesModel actividadesModel){
        String structure = String.format(
            "<actividades>"
                + "<act_nombre>"+actividadesModel.getAct_nombre()+"</act_nombre>"
                + "<act_interacciones>"+actividadesModel.getAct_interacciones()+"</act_interacciones>"
                + "<act_icono>"+actividadesModel.getAct_icono()+"</act_icono>"
               + "</actividades>");
        
        String sentency = "Select * from insertactividades('"+structure+"')";
        System.out.println(structure);
        return con.modifyBD(sentency);
    }

}
