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

    public String selectGametype() {
        return gtD.selectGametype();
    }

    public String selectGametypepage(int page) {
        return gtD.selectGametypepage(page);
    }

    public int CountingPageGametype() {
        return gtD.CountingPageGametype();
    }

    public Object[] InsertGametypeC(String name,
            String image, String audio_instructions, String creationdate,
            String updatedate,
            String state) {
        String message = "";
        boolean status = false;
        gtM.setName(name);
        gtM.setImage(image);
        gtM.setAudio_instructions(audio_instructions);
        gtM.setCreationdate(creationdate);
        gtM.setState(state);
        if (gtD.insertGametype(gtM)) {
            message = "The Game Type was inserted.";
            status = true;
        } else {
            message = "The Game Type was not inserted";
            status = false;
        }
        return new Object[]{status, message};

    }
}
