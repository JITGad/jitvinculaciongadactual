package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.StatisticsgameDAO;
import com.jitgad.bjitgad.Models.StatisticsgameModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class StatisticsgameController {
    
    private final StatisticsgameDAO sgD;
    

    public StatisticsgameController() {
        sgD = new StatisticsgameDAO();
    }
    
    public ArrayList<StatisticsgameModel> selectStatisticsgamepage(int page) throws Exception {
        return sgD.selectStatisticsgamepage(page);
    }
    
    public int CountingPageStatisticsgame() throws Exception {
        return sgD.CountingPageStatisticsgame();
    }
    
    public ResponseData InsertstatisticsgameC(StatisticsgameModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);
        
        request.setCreationdate("NOW()");
        request.setUpdatedate("NOW()");

        if (sgD.insertStatisticsgame(request)) {

            responseData.setMessage("Registros insertados correctamente");
            responseData.setFlag(true);
            return responseData;
        }

        return responseData;
    }
    
    
    
}
