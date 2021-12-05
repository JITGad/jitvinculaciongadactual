
package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;

/**
 *
 * @author jorge
 */
public class ActivitiestypeDAO {
    
    Conection con;
    String sentence;

    public ActivitiestypeDAO() {
        con = new Conection();
    }
    
    public String selectActivitiestype(){
        sentence = "select idactivitiestype as id,name as tema,image as urlimagen from tblactivitiestype";
        String json = con.getRecordsInJson(sentence);
        return json;
    }
    
    public String selectgamesbyactivities(String idactivity){
        sentence = "select * from tblgametype \n" +
        "inner join tblgame on tblgame.idgametype = tblgametype.idgametype \n" +
        "inner join tblactivitiestype on tblgame.idactivitiestype = tblactivitiestype.idactivitiestype\n" +
        "where tblactivitiestype.idactivitiestype ="+idactivity;
        String json = con.getRecordsInJson(sentence);
        return json;
    }
    
    public int selectIDActivitiestype(){
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
    
}
