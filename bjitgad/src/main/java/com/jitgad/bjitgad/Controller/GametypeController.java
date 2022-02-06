package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GametypeDAO;
import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.Models.ClaveValorGameModel;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Models.GametypeModel;
import com.jitgad.bjitgad.Utilities.ResponseCreateFile;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.io.File;
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

    public ArrayList<GametypeModel> selectGametypepage(int page) throws Exception{
        return gtD.selectGametypepage(page);
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

    public ResponseData InsertGametypeC(GametypeModel request,
            String realpath) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setImage(request.getImage() == null
                ? ""
                : request.getImage());

        request.setVideo_instructions(request.getVideo_instructions() == null ? ""
                : request.getVideo_instructions());

        request.setAudio_instructions(request.getAudio_instructions() == null ? "" : request.getAudio_instructions());

        ResponseCreateFile CreateFile = fc.createfile(request.getImage(), "gametype", request.getName(), realpath);
        if (CreateFile.isState()) {
            request.setImage(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        }

        CreateFile = fc.createfile(request.getVideo_instructions(), "gametype", request.getName(), realpath);
        if (CreateFile.isState()) {
            request.setVideo_instructions(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        }

        CreateFile = fc.createfile(request.getAudio_instructions(), "gametype", request.getName(), realpath);
        if (CreateFile.isState()) {
            request.setAudio_instructions(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
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
            String realpath) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setImage(request.getImage() == null ? "" : request.getImage());
        request.setVideo_instructions(request.getVideo_instructions() == null
                ? "" : request.getVideo_instructions());
        request.setAudio_instructions(request.getAudio_instructions() == null ? "" : request.getAudio_instructions());

        ResponseCreateFile CreateFile = fc.createfile(request.getImage(), "gametype", request.getName(), realpath);
        if (CreateFile.isState()) {
            request.setImage(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        }

        CreateFile = fc.createfile(request.getVideo_instructions(), "gametype", request.getName(), realpath);
        if (CreateFile.isState()) {
            request.setVideo_instructions(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        }

        CreateFile = fc.createfile(request.getAudio_instructions(), "gametype", request.getName(), realpath);
        if (CreateFile.isState()) {
            request.setAudio_instructions(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
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

    public ResponseData DeleteGametypeC(GametypeModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        if (gtD.DeleteGametype(request)) {

            responseData.setMessage("Registro eliminado correctamente");
            responseData.setFlag(true);

            return responseData;
        }

        return responseData;
    }

}
