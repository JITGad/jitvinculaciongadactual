package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.ColortypeDAO;
import com.jitgad.bjitgad.Models.ClaveValorColorModel;
import com.jitgad.bjitgad.Models.ColortypeModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class ColortypeController {

    private final ColortypeDAO ctypDAO;

    public ColortypeController() {
        ctypDAO = new ColortypeDAO();
    }

    public ArrayList<ColortypeModel> selectColortypepage(int page) throws Exception {
        return ctypDAO.selectColortypepage(page);
    }

    public int CountingPageColortype() throws Exception {
        return ctypDAO.CountingPageColortype();
    }

    public String selectColortypebyid(int id) throws Exception {
        return ctypDAO.selectColortypebyid(id);
    }
    
    public ArrayList<ClaveValorColorModel> selectColortypecv() throws Exception{
        return ctypDAO.selectColortypecv();
    }

    public ResponseData InsertColortype(ColortypeModel colortypeModel) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        colortypeModel.setCreationdate("NOW()");
        colortypeModel.setUpdatedate("NOW()");

        if (ctypDAO.insertColortype(colortypeModel)) {
            responseData.setMessage("Registros insertados correctamente");
            responseData.setFlag(true);
            return responseData;
        }

        return responseData;
    }

    public ResponseData UpdateColortype(ColortypeModel colortypeModel) throws Exception {
        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        colortypeModel.setUpdatedate("NOW()");

        if (ctypDAO.updatecolortype(colortypeModel)) {
            responseData.setMessage("Registros actualizados correctamente");
            responseData.setFlag(true);
            return responseData;
        }

        return responseData;
    }

    public ResponseData DeleteColortype(ColortypeModel colortypeModel) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        if (ctypDAO.deletecolortype(colortypeModel)) {
            responseData.setMessage("Registro eliminado correctamente");
            responseData.setFlag(true);
            return responseData;
        }
        responseData.setMessage("El registro no fue eliminado,"
                + "datos erróneos para enviar a la base de datos!");
        responseData.setFlag(false);

        return responseData;
    }

}
