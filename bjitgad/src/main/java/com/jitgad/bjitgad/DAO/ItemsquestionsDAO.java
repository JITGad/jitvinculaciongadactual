
package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;

/**
 *
 * @author jorge
 */
public class ItemsquestionsDAO {
    
   ConectionPool con;
   String sentence; 

    public ItemsquestionsDAO() {
        con = ConectionPoolDataSource.getConnection();
    }
    
    public String selectItemsquestions(){
        sentence = "select * from tblitemsquestions";
        String json = con.getRecordsInJson(sentence);
        return json;
    }   
   
}
