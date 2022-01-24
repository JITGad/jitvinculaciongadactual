package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.ActivitiestypeDAO;
import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class ActivitiestypeController {

    private ActivitiestypeDAO atDAO;
    private ActivitiestypeModel atM;
    private FileController fc;

    public ActivitiestypeController() {
        atM = new ActivitiestypeModel();
        atDAO = new ActivitiestypeDAO();
        fc = new FileController();
    }
    

    public ResponseData InsertActivitiesTypeC(ActivitiestypeModel request,
            String realpath) throws UnsupportedEncodingException, SQLException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);
        
        request.setImage(request.getImage() == null ? "" : request.getImage());
         
        Object[] CreateFile = fc.createfile(request.getImage(),
                    "activities", request.getName(), realpath);
            if (Boolean.parseBoolean(CreateFile[0].toString())) {
                request.setImage(String.valueOf(CreateFile[1].toString()
                        + "/" + "activities" + "/" + CreateFile[2].toString()));
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

        return responseData;
    }

    public ResponseData UpdateActivitiesTypeC(ActivitiestypeModel request,
            String realpath) throws UnsupportedEncodingException, SQLException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);
        
        request.setImage(request.getImage() == null ? "" : request.getImage());
         
        Object[] CreateFile;

            CreateFile = fc.createfile(request.getImage(),
                    "activities", request.getName(), realpath);
            if (Boolean.parseBoolean(CreateFile[0].toString())) {
                request.setImage(String.valueOf(CreateFile[1].toString()
                        + "/" + "activities" + "/" + CreateFile[2].toString()));
            } 

            request.setUpdatedate("NOW()");

            if (atDAO.updateActividadestype(request)) {
                responseData.setMessage("Registros actualizados correctamente");
                responseData.setFlag(true);
               return responseData;
            }
           
            return responseData;
    }

    public ResponseData DeleteActividadestype(ActivitiestypeModel request) throws SQLException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);
     
            if (atDAO.DeleteActividadestype(request)) {

                responseData.setMessage("Registro eliminado correctamente");
                responseData.setFlag(true);
                return responseData;
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
    
    public ArrayList<ClaveValorModel> selectactivitiestypecv(){
        
        return atDAO.selectactivitiestypeclavevalor();
    }

}
