package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GametypeDAO;
import com.jitgad.bjitgad.Models.ClaveValorGameModel;
import com.jitgad.bjitgad.Models.GametypeModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class GametypeController {

    private final GametypeDAO gtD;

    public GametypeController() {
        gtD = new GametypeDAO();
    }

    public ArrayList<GametypeModel> selectGametypepage(int page) throws Exception{
        return gtD.selectGametypepage(page);
    }
    
    
    public ArrayList<GametypeModel> selectGametypewithgames(int idactivitiestype) throws Exception{
        return gtD.selectGametypewithgames(idactivitiestype);
    }        
            

    public int CountingPageGametype() throws Exception {
        return gtD.CountingPageGametype();
    }

    public String selectGametypebyid(int id) throws Exception {
        return gtD.selectGametypebyid(id);
    }

    public ArrayList<ClaveValorGameModel> selectgametypecv() throws Exception {
        return gtD.selectgametypecv();
    }

    public ResponseData InsertGametypeC(GametypeModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request = UpdateGameTypeModel(request);
       
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

    public ResponseData UpdateGametypeC(GametypeModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request = UpdateGameTypeModel(request);
        
        request.setUpdatedate("NOW()");

        if (gtD.updateGametype(request)) {

            responseData.setMessage("Registros actualizados correctamente");
            responseData.setFlag(true);

            return responseData;
        }

        return responseData;
    }

    public ResponseData DeleteGametypeC(GametypeModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        if (gtD.DeleteGametype(request)) {

            responseData.setMessage("Registro eliminado correctamente");
            responseData.setFlag(true);

            return responseData;
        }

        return responseData;
    }
    
    private GametypeModel UpdateGameTypeModel(GametypeModel request) throws IOException{
        request.setImage(request.getImage() == null ? "" : request.getImage());
        
        request.setVideo_instructions(request.getVideo_instructions() == null
                ? "" : request.getVideo_instructions());
        
        request.setAudio_instructions(request.getAudio_instructions() == null ? "" : request.getAudio_instructions());
        
        return request;
    }

}
