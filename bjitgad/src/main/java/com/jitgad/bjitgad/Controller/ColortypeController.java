package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.ColortypeDAO;
import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ColortypeModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class ColortypeController {

    private ColortypeModel ctModel;
    private ColortypeDAO ctypDAO;

    public ColortypeController() {
        ctModel = new ColortypeModel();
        ctypDAO = new ColortypeDAO();
    }

    public ArrayList<ColortypeModel> selectColortypepage(int page) {
        return ctypDAO.selectColortypepage(page);
    }

    public int CountingPageColortype() {
        return ctypDAO.CountingPageColortype();
    }

    public String selectColortypebyid(int id) {
        return ctypDAO.selectColortypebyid(id);
    }

    public ResponseData InsertColortype(ColortypeModel colortypeModel) {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        try {

            colortypeModel.setCreationdate("NOW()");
            colortypeModel.setUpdatedate("NOW()");

            if (ctypDAO.insertColortype(colortypeModel)) {
                responseData.setMessage("Registros insertados correctamente");
                responseData.setFlag(true);
                return responseData;
            }
            responseData.setMessage("Registros no insertados,"
                    + "datos erróneos para enviar a la base de datos!");
            responseData.setFlag(false);
            return responseData;
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

    public ResponseData UpdateColortype(ColortypeModel colortypeModel) {
        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        try {

            colortypeModel.setUpdatedate("NOW()");

            if (ctypDAO.updatecolortype(colortypeModel)) {
                responseData.setMessage("Registros actualizados correctamente");
                responseData.setFlag(true);
                return responseData;
            }
            responseData.setMessage("Registros no insertados,"
                    + "datos erróneos para enviar a la base de datos!");
            responseData.setFlag(false);
            return responseData;

        } catch (Exception e) {
            responseData.setFlag(false);

            if (Configuration.DEBUG) {
                responseData.setMessage(e.getMessage());
                return responseData;
            }

            responseData.setMessage("Ha ocurrido un error actualizando "
                    + "un tipo de color, vuelva a intentarlo mas tarde");

            System.err.println(e.getMessage());
        }
        return responseData;
    }

    public ResponseData DeleteColortype(ColortypeModel colortypeModel) {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        try {

            if (ctypDAO.deletecolortype(colortypeModel)) {
                responseData.setMessage("Registro eliminado correctamente");
                responseData.setFlag(true);
                return responseData;
            }
            responseData.setMessage("El registro no fue eliminado,"
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

}
