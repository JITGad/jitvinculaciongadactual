package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GametypeDAO;
import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.Models.GametypeModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class GametypeController {

    private GametypeDAO gtD;
    private GametypeModel gtM;
    private FileController fc;

    public GametypeController() {
        gtD = new GametypeDAO();
        gtM = new GametypeModel();
        fc = new FileController();
    }

    public ArrayList<GametypeModel> selectGametypepage(int page) {
        return gtD.selectGametypepage(page);
    }

    public int CountingPageGametype() {
        return gtD.CountingPageGametype();
    }

    public String selectGametypebyid(int id) {
        return gtD.selectGametypebyid(id);
    }

    public ResponseData InsertGametypeC(GametypeModel request,
            String realpath) throws SQLException, UnsupportedEncodingException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        Object[] CFImage = fc.createfile(request.getImage(),
                    "Game", request.getName(), realpath);
        
        if (Boolean.parseBoolean(CFImage[0].toString())) {
                request.setImage(String.valueOf(CFImage[1].toString()
                        + "/" + "Game" + "/" + CFImage[2].toString()));
        } else {
            request.setImage("");
        }
        
        request.setAudio_instructions("");
        request.setVideo_instructions("");

        request.setShortname(request.getShortname()
                .trim()
                .replaceAll("\\s+", "")
                .toLowerCase());
        request.setCreationdate("NOW()");
        request.setUpdatedate("NOW()");

        if (gtD.insertGametype(request)) {
            
            responseData.setMessage("Registros insertados correctamente");
            responseData.setFlag(true);
            
            return responseData;
        }

        return responseData;
    }

    public ResponseData UpdateGametypeC(GametypeModel request,
            String realpath) throws SQLException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setImage("");
        request.setAudio_instructions("");
        request.setVideo_instructions("");

        request.setShortname(request.getShortname()
                .trim()
                .replaceAll("\\s+", "")
                .toLowerCase());
        request.setUpdatedate("NOW()");

        if (gtD.updateGametype(request)) {
            
            responseData.setMessage("Registros actualizados correctamente");
            responseData.setFlag(true);
            
            return responseData;
        }

        return responseData;
    }

    public ResponseData DeleteGametypeC(GametypeModel request) throws SQLException {
        
        ResponseData responseData = new ResponseData("Ocurrió un error", false);
     
            if (gtD.DeleteGametype(request)) {

                responseData.setMessage("Registro eliminado correctamente");
                responseData.setFlag(true);
                
                return responseData;
            }

            return responseData;
    }

}
