package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GametypeDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.GametypeModel;

/**
 *
 * @author jorge
 */
public class GametypeController {

    private Conection conex;
    private GametypeDAO gtD;
    private GametypeModel gtM;

    public GametypeController() {
        conex = new Conection();
        gtD = new GametypeDAO();
        gtM = new GametypeModel();
    }

//    public String selectGametype() {
//        return gtD.selectGametype();
//    }
    public String selectGametypepage(int page) {
        return gtD.selectGametypepage(page);
    }

    public int CountingPageGametype() {
        return gtD.CountingPageGametype();
    }

    public String selectGametypebyid(int id) {
        return gtD.selectGametypebyid(id);
    }

    public Object[] InsertGametypeC(String name,
            String image, String audio_instructions,
            boolean state,
            String shortname) {
        String message = "";
        boolean status = false;
        gtM.setName(name);
        gtM.setImage(image);
        gtM.setAudio_instructions(audio_instructions);
        gtM.setCreationdate("NOW()");
        gtM.setUpdatedate("NOW()");
        gtM.setState(state);
        gtM.setShortname(shortname);
        if (gtD.insertGametype(gtM)) {
            message = "The Game Type was inserted.";
            status = true;
        } else {
            message = "The Game Type was not inserted";
            status = false;
        }
        return new Object[]{status, message};

    }

    public Object[] UpdateGametypeC(int idgametype,
            String name,String image, 
            String audio_instructions,
            boolean state,
            String shortname) {
        String message = "";
        boolean status = false;
        gtM.setIdgametype(idgametype);
        gtM.setName(name);
        gtM.setImage(image);
        gtM.setAudio_instructions(audio_instructions);
        gtM.setUpdatedate("NOW()");
        gtM.setState(state);
        gtM.setShortname(shortname);
        if (gtD.updateGametype(gtM)) {
            message = "Registros actualizados correctamente";
            status = true;
        } else {
            message = "Los registros no fueron actualizados, ocurrió un error";
            status = false;
        }
        return new Object[]{status, message};
    }

    public Object[] DeleteGametypeC(int idgametype) {
        String message = "";
        boolean status = false;
        gtM.setIdgametype(idgametype);

        if (gtD.DeleteGametype(gtM)) {
            message = "Registro eliminado correctamente";
            status = true;
        } else {
            message = "El registro no fué eliminado, ocurrió un error";
            status = false;
        }

        return new Object[]{status, message};
    }

}
