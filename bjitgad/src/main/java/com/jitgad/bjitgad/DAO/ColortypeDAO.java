package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;

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
}
