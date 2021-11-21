
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
public class HistorialjugadoresDAO {
    Conection con;
    String sentence;
    
    public HistorialjugadoresDAO(){
       con = new Conection(); 
    }
    
    public String selectHistorialjugadoresDAO() {
        sentence = "select * from historial_jugadores";
        Conection con = new Conection();
        String json = con.getRecordsInJson(sentence);
        return json;
    }
}
