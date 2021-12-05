
package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;

/**
 *
 * @author jorge
 */
public class ItemsquestionsDAO {
    
   Conection con;
   String sentence; 

    public ItemsquestionsDAO() {
        con = new Conection();
    }
    
    public String selectItemsquestions(){
        sentence = "select * from tblitemsquestions";
        String json = con.getRecordsInJson(sentence);
        return json;
    }   
   
}
