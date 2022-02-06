package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.ActivitiestypeDAO;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Utilities.ResponseCreateFile;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class ActivitiestypeController {

    private final ActivitiestypeDAO atDAO;
    private final FileController fc;

    public ActivitiestypeController() {
        atDAO = new ActivitiestypeDAO();
        fc = new FileController();
    }

    public ResponseData InsertActivitiesTypeC(ActivitiestypeModel request,
            String realpath) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setImage(request.getImage() == null ? "" : request.getImage());

        ResponseCreateFile CreateFile = fc.createfile(request.getImage(), "activities", request.getName(), realpath);
        if (CreateFile.isState()) {
            request.setImage(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
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
            String realpath) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setImage(request.getImage() == null ? "" : request.getImage());

        ResponseCreateFile CreateFile = fc.createfile(request.getImage(), "activities", request.getName(), realpath);
        if (CreateFile.isState()) {
            request.setImage(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        } else {
            request.setImage("");
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

    public ArrayList<ActivitiestypeModel> selectActivitiestype() throws Exception {
        return atDAO.selectActivitiestype();
    }

    public ArrayList<ActivitiestypeModel> selectActivitiestypepage(int page) throws Exception {
        return atDAO.selectActivitiestypepage(page);
    }

    public int CountingPageActivitiesType() throws Exception {
        return atDAO.CountingPageActivitiestype();
    }

    public String selectactivitiesbyid(int activityid) throws Exception {
        return atDAO.selectactivitiesbyid(activityid);
    }

    public ArrayList<ClaveValorModel> selectactivitiestypecv() throws Exception {

        return atDAO.selectactivitiestypeclavevalor();
    }

}
