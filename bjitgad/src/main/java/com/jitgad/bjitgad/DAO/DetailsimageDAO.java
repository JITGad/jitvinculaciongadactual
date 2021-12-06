package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;

/**
 *
 * @author jorge
 */
public class DetailsimageDAO {

    Conection con;
    String sentence;

    public DetailsimageDAO() {
        con = new Conection();
    }

    public String selectDetailsimage() {
        sentence = "select * from tbldetailsimage";
        String json = con.getRecordsInJson(sentence);
        return json;
    }
}
