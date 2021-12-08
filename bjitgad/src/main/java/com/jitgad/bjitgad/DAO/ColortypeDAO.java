package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.ColortypeModel;

/**
 *
 * @author jorge
 */
public class ColortypeDAO {

    Conection con;
    String sentence;

    public ColortypeDAO() {
        con = new Conection();
    }

    public String selectColortype() {
        sentence = "select * from tblcolortype";
        String json = con.getRecordsInJson(sentence);
        return json;
    }
    
    public boolean insertColortype(ColortypeModel colortypeModel) {
        String structure = String.format(
                "<colortype>"
                + "<color>" + colortypeModel.getColor() + "</color>"
                + "<creationdate>" + colortypeModel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + colortypeModel.getUpdatedate() + "</updatedate>"
                + "<state>" + colortypeModel.getState() + "</state>"
                + "</colortype>");

        String sentency = "Select * from insertcolortype('" + structure + "')";
       // System.out.println(structure);
        return con.modifyBD(sentency);
    }
}
