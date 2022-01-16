package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.ActivitiestypeDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
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
    private FileController fc;

    public ActivitiestypeController() {
        conex = new Conection();
        atM = new ActivitiestypeModel();
        atDAO = new ActivitiestypeDAO();
        fc = new FileController();
    }

    public ResponseData InsertActivitiesTypeC(ActivitiestypeModel request,
            String realpath) {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        Object[] CreateFile;
        try {
            CreateFile = fc.createfile(request.getImage(),
                    "Activities", request.getName(), realpath);
            if (Boolean.parseBoolean(CreateFile[0].toString())) {
                request.setImage(String.valueOf(CreateFile[1].toString()
                        + "/" + "Activities" + "/" + CreateFile[2].toString()));
            } else {
                request.setImage("");
            }
            request.setCreationdate("NOW()");
            request.setUpdatedate("NOW()");

            if (atDAO.insertActividadestype(request)) {
                responseData.setMessage("Registros insertados correctamente");
                responseData.setFlag(true);
                return responseData;
            }
            responseData.setMessage("Registros no insertados,"
                    + "datos erróneos para enviar a la base de datos!");
            responseData.setFlag(false);
            return responseData;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ActivitiestypeController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (Exception e) {
            responseData.setFlag(false);

            if (Configuration.DEBUG) {
                responseData.setMessage(e.getMessage());
                return responseData;
            }

            responseData.setMessage("Ha ocurrido un error insertado "
                    + "un tipo de color, vuelva a intentarlo mas tarde");

            System.err.println(e.getMessage());
        }
        return responseData;
    }

    public ResponseData UpdateActivitiesTypeC(ActivitiestypeModel request,
            String realpath) {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);
        Object[] CreateFile;

        try {
            CreateFile = fc.createfile(request.getImage(),
                    "Activities", request.getName(), realpath);
            if (Boolean.parseBoolean(CreateFile[0].toString())) {
                request.setImage(String.valueOf(CreateFile[1].toString()
                        + "/" + "Activities" + "/" + CreateFile[2].toString()));
            } else {
                request.setImage("");
            }

            request.setUpdatedate("NOW()");

            if (atDAO.updateActividadestype(request)) {
                responseData.setMessage("Registros actualizados correctamente");
                responseData.setFlag(true);
               return responseData;
            }
            responseData.setMessage("Registros no insertados,"
                    + "datos erróneos para enviar a la base de datos!");
            responseData.setFlag(false);
            return responseData;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ActivitiestypeController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (Exception e) {
            responseData.setFlag(false);

            if (Configuration.DEBUG) {
                responseData.setMessage(e.getMessage());
                return responseData;
            }

            responseData.setMessage("Ha ocurrido un error insertado "
                    + "un tipo de color, vuelva a intentarlo mas tarde");

            System.err.println(e.getMessage());
        }
        return responseData;
    }

    public ResponseData DeleteActividadestype(ActivitiestypeModel request) {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        try {
            if (atDAO.DeleteActividadestype(request)) {

                responseData.setMessage("Registro eliminado correctamente");
                responseData.setFlag(true);
                return responseData;
            }
            responseData.setMessage("El registro no fué eliminado,"
                    + "datos erróneos para enviar a la base de datos!");
            responseData.setFlag(false);

            return responseData;
        } catch (Exception e) {
            responseData.setFlag(false);

            if (Configuration.DEBUG) {
                responseData.setMessage(e.getMessage());
                return responseData;
            }

            responseData.setMessage("Ha ocurrido un error eliminando "
                    + "un tipo de color, vuelva a intentarlo mas tarde");

            System.err.println(e.getMessage());
        }
        return responseData;
    }

    public ArrayList<ActivitiestypeModel> selectActivitiestype(String path) {
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
