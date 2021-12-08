
package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.ColortypeDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.ColortypeModel;

/**
 *
 * @author jorge
 */
public class ColortypeController {
    
    private Conection conex;
    private ColortypeModel ctModel;
    private ColortypeDAO ctypDAO;

    public ColortypeController() {
      conex = new Conection();
      ctModel = new ColortypeModel();
      ctypDAO = new ColortypeDAO();
    }
    
    public String selectColortype(){
        return ctypDAO.selectColortype();
    }
    
    public Object[] InsertColortype(String color,
            String creationdate, String updatedate, String state) {
        String message = "";
        boolean status = false;
        ctModel.setColor(color);
        ctModel.setCreationdate(creationdate);
        ctModel.setUpdatedate(updatedate);
        ctModel.setState(state);
        
        if(ctypDAO.insertColortype(ctModel)){
            message = "The Color type was inserted.";
            status = true;
        }   else {
                message = "The Color type was not inserted";
                status = false;
            }

        return new Object[]{status, message};
    }
    
    
    
    
    
}
