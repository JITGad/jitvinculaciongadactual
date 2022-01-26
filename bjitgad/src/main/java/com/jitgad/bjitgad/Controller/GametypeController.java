package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GametypeDAO;
import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.Models.ClaveValorModel;
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
            
    public ArrayList<ClaveValorModel> selectgametypecv() {
        return gtD.selectgametypecv();
    }        

    public ResponseData InsertGametypeC(GametypeModel request,
            String realpath) throws SQLException, UnsupportedEncodingException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setImage(request.getImage() == null ? 
                "" : 
                request.getImage());
        
        
        request.setVideo_instructions(request.getVideo_instructions() == null ? "" : 
                request.getVideo_instructions());
        
        
        request.setAudio_instructions(request.getAudio_instructions() == null ? "" : request.getAudio_instructions());
        
        
//        System.out.println("IMAGEN"+ " - " + request.getImage());
//        System.out.println("VIDEO"+ " - " + request.getVideo_instructions());
//        System.out.println("AUDIO"+ " - " + request.getAudio_instructions());

        Object[] CFImage = fc.createfile(request.getImage(), "gametype", request.getName(), realpath);

        if (Boolean.parseBoolean(CFImage[0].toString())) {
            request.setImage(String.valueOf(CFImage[1].toString()
                    + "/" + "gametype" + "/" + CFImage[2].toString()));
            System.out.println("Inserta positivo imagen");
            System.out.println(request.getImage());
        } else {
            request.setImage("");
            System.out.println("Inserta negativo imagen");
        }

        Object[] CFVideo = fc.createfile(request.getVideo_instructions(), "gametype", request.getName(), realpath);

        if (Boolean.parseBoolean(CFVideo[0].toString())) {
            request.setVideo_instructions(String.valueOf(CFVideo[1].toString()
                    + "/" + "gametype" + "/" + CFVideo[2].toString()));
            System.out.println("Inserta positivo video");
            System.out.println(request.getVideo_instructions());
        } else {
            request.setVideo_instructions("");
            System.out.println("Inserta negativo video");
        }
        
        Object[] CFAudio = fc.createfile(request.getAudio_instructions(), "gametype", request.getName(), realpath);

        
        if (Boolean.parseBoolean(CFAudio[0].toString())) {
            request.setAudio_instructions(String.valueOf(CFAudio[1].toString()
                    + "/" + "gametype" + "/" + CFAudio[2].toString()));
            System.out.println("Inserta positivo audio");
            System.out.println(request.getAudio_instructions());
        } else {
            request.getAudio_instructions();
            System.out.println("Inserta negativo audio");
        }
        

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
            String realpath) throws SQLException, UnsupportedEncodingException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setImage(request.getImage() == null ? "" : request.getImage());
        request.setVideo_instructions(request.getVideo_instructions() == null
                ? "" : request.getVideo_instructions());
        request.setAudio_instructions(request.getAudio_instructions() == null ? "" : request.getAudio_instructions());

        Object[] CFImage = fc.createfile(request.getImage(), "gametype", request.getName(), realpath);

        if (Boolean.parseBoolean(CFImage[0].toString())) {
            request.setImage(String.valueOf(CFImage[1].toString()
                    + "/" + "gametype" + "/" + CFImage[2].toString()));
        }

        Object[] CFVideo = fc.createfile(request.getVideo_instructions(), "gametype", request.getName(), realpath);

        if (Boolean.parseBoolean(CFVideo[0].toString())) {
            request.setVideo_instructions(String.valueOf(CFVideo[1].toString()
                    + "/" + "gametype" + "/" + CFVideo[2].toString()));
        } 
        
        Object[] CFAudio = fc.createfile(request.getAudio_instructions(), "gametype", request.getName(), realpath);
        
        if (Boolean.parseBoolean(CFAudio[0].toString())) {
            request.setAudio_instructions(String.valueOf(CFAudio[1].toString()
                    + "/" + "gametype" + "/" + CFAudio[2].toString()));
        } 

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
