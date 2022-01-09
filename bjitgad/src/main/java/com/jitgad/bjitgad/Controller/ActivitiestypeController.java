package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.ActivitiestypeDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
import java.io.UnsupportedEncodingException;
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

    public Object[] InsertActivitiesTypeC(String name, String image,
            String state) {
        String message = "";
        boolean status = false;

        FileController fc = new FileController();
        Object[] CreateFile;
        try {
            CreateFile = fc.createfile(image, "Activities",name);
            if(Boolean.parseBoolean(CreateFile[0].toString())){
                image = String.valueOf(CreateFile[1].toString() + "/" + name + "/" + CreateFile[2].toString());
            }
            atM.setName(name);
            atM.setImage(image);
            atM.setCreationdate("NOW()");
            atM.setUpdatedate("NOW()");
            atM.setState(Boolean.parseBoolean(state));

            if (atDAO.insertActividadestype(atM)) {
                message = "Registros insertados correctamente";
                status = true;
            } else {
                message = "Registros no insertados, ocurrió un error!";
                status = false;
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ActivitiestypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
       // responseJson = Rapi.Response("Imagen creada con éxito", Boolean.parseBoolean(CreateFile[0].toString()), String.valueOf(CreateFile[1].toString() + "/" + name + "/" + CreateFile[2].toString()));

        return new Object[]{status, message, image};
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
            message = "Los registros no fueron actualizados, ocurrió un error";
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
            message = "El registro no fué eliminado, ocurrió un error";
            status = false;
        }

        return new Object[]{status, message};
    }

    public String selectActivitiestype(){
        return atDAO.selectActivitiestype();
    }

    public String selectActivitiestypepage(int page) {
        return atDAO.selectActivitiestypepage(page);
    }

    public int CountingPageActivitiesType() {
        return atDAO.CountingPageActivitiestype();
    }

    public String selectactivitiesbyid(int activityid) {
        return atDAO.selectactivitiesbyid(activityid);
    }

}
