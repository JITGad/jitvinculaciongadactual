
package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import java.sql.SQLException;

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
    
    public String selectQuestions() throws SQLException{
        sentence = "select * from tblquestions";
        String json = con.getRecordsInJson(sentence);
        return json;
    }
    
    
    
}
