
package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;

/**
 *
 * @author jorge
 */
public class QuestionsDAO {
    
    private final ConectionPool con;
    String sentence;

    public QuestionsDAO() {
        con = ConectionPoolDataSource.getConnection();
    }
    
    public String selectQuestions(){
        sentence = "select * from tblquestions";
        String json = con.getRecordsInJson(sentence);
        return json;
    }
    
    
    
}
