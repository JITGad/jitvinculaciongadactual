package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.GameimageModel;

/**
 *
 * @author jorge
 */
public class GameimageDAO {

    Conection con;
    String sentence;

    public GameimageDAO() {
        con = new Conection();
    }

    public String selectGameimage() {
        sentence = "select * from tblgameimage";
        String json = con.getRecordsInJson(sentence);
        return json;
    }

    public boolean insertGameimage(GameimageModel giM) {
        String structure = String.format(
                "<gameimage>"
                + "<idgame>" + giM.getIdgame() + "</idgame>"
                + "<idcolortype>" + giM.getIdcolortype() + "</idcolortype>"
                + "<image>" + giM.getImage() + "</image>"
                + "<paragraph>" + giM.getParagraph() + "</paragraph>"
                + "<audio_parag>" + giM.getAudio_parag() + "</audio_parag>"
                + "<video_parag>" + giM.getVideo_parag() + "</video_parag>"
                + "<creationdate>" + giM.getCreationdate() + "</creationdate>"
                + "<updatedate>" + giM.getUpdatedate() + "</updatedate>"
                + "<state>" + giM.getState() + "</state>"
                + "</gameimage>");

        String sentency = "Select * from insertgameimage('" + structure + "')";
        // System.out.println(structure);
        return con.modifyBD(sentency);
    }

}
