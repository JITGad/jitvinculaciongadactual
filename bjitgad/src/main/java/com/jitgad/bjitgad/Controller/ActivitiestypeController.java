package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.ActivitiestypeDAO;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class ActivitiestypeController {

    private final ActivitiestypeDAO atDAO;

    public ActivitiestypeController() {
        atDAO = new ActivitiestypeDAO();
    }

    public ResponseData InsertActivitiesTypeC(ActivitiestypeModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setImage(request.getImage() == null ? "" : request.getImage());

        request.setCreationdate("NOW()");
        request.setUpdatedate("NOW()");

        if (atDAO.insertActividadestype(request)) {
            responseData.setMessage("Registros insertados correctamente");
            responseData.setFlag(true);
            return responseData;
        }

        return responseData;
    }

    public ResponseData UpdateActivitiesTypeC(ActivitiestypeModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setImage(request.getImage() == null ? "" : request.getImage());

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
