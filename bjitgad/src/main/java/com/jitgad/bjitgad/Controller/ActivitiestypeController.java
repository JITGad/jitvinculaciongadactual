package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.ActivitiestypeDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;

/**
 *
 * @author jorge
 */
public class ActivitiestypeController {

    private Conection conex;
    private ActivitiestypeDAO atDAO;
    private ActivitiestypeModel atM;

    public ActivitiestypeController() {
        conex = new Conection();
        atM = new ActivitiestypeModel();
        atDAO = new ActivitiestypeDAO();
    }

    public Object[] InsertActivitiesTypeC(String name, String image,
            String creationdate, String updatedate, String state) {
        String message = "";
        boolean status = false;
        atM.setName(name);
        atM.setImage(image);
        atM.setCreationdate(creationdate);
        atM.setUpdatedate(updatedate);
        atM.setState(state);
        
        if(atDAO.insertActividadestype(atM)){
            message = "The Activities type was inserted.";
            status = true;
        }   else {
                message = "The Activities type was not inserted";
                status = false;
            }

        return new Object[]{status, message};
    }

}
