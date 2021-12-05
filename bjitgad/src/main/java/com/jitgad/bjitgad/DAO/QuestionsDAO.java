
package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;

/**
 *
 * @author jorge
 */
public class QuestionsDAO {
    
    Conection con;
    String sentence;

    public QuestionsDAO() {
        con = new Conection();
    }
    
    public String selectQuestions(){
        sentence = "select * from tblquestions";
        String json = con.getRecordsInJson(sentence);
        return json;
    }
    
    
    
}
