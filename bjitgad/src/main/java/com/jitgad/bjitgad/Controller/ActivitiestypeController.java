package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.ActivitiestypeDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public ResponseData InsertActivitiesTypeC(ActivitiestypeModel request,
            String realpath) {
      
        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        FileController fc = new FileController();
        Object[] CreateFile;
        try {
            CreateFile = fc.createfile(request.getImage(), "Activities",request.getName(),realpath);
            if(Boolean.parseBoolean(CreateFile[0].toString())){
                request.setImage(String.valueOf(CreateFile[1].toString() + "/" + "Activities" + "/" + CreateFile[2].toString()));
            }
            request.setCreationdate("NOW()");
            request.setUpdatedate("NOW()");

            if (atDAO.insertActividadestype(request)) {
                responseData.setMessage("Registros insertados correctamente");
                responseData.setFlag(true);
            } else {
                responseData.setMessage("Registros no insertados, datos erróneos para enviar a la base de datos!");
                responseData.setFlag(false);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ActivitiestypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responseData;
    }

    public Object[] UpdateActivitiesTypeC(int idactivitiestype,
            String name,
            String image,
            String state) {
        String message = "";
        boolean status = false;

        atM.setIdactivitiestype(idactivitiestype);
        atM.setName(name);
        atM.setImage(image);
        atM.setState(status);
        atM.setUpdatedate("NOW()");

        if (atDAO.updateActividadestype(atM)) {
            message = "Registros actualizados correctamente";
            status = true;
        } else {
            message = "Los registros no fueron actualizados, datos erróneos para enviar a la base de datos!";
            status = false;
        }

        return new Object[]{status, message};
    }

    public Object[] DeleteActividadestype(int idactivitiestype) {
        String message = "";
        boolean status = false;
        atM.setIdactivitiestype(idactivitiestype);

        if (atDAO.DeleteActividadestype(atM)) {
            message = "Registro eliminado correctamente";
            status = true;
        } else {
            message = "El registro no fué eliminado, datos erróneos para enviar a la base de datos!";
            status = false;
        }

        return new Object[]{status, message};
    }

    public ArrayList<ActivitiestypeModel>  selectActivitiestype(String path){
        return atDAO.selectActivitiestype(path);
    }

    public ArrayList<ActivitiestypeModel> selectActivitiestypepage(int page) {
        return atDAO.selectActivitiestypepage(page);
    }

    public int CountingPageActivitiesType() {
        return atDAO.CountingPageActivitiestype();
    }

    public String selectactivitiesbyid(int activityid) {
        return atDAO.selectactivitiesbyid(activityid);
    }

}
