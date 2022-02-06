package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import com.jitgad.bjitgad.Models.GameimageModel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class GameimageDAO {

    private final ConectionPool con;
    String sentence;

    public GameimageDAO() {
        con = ConectionPoolDataSource.getConnection();
    }

    public String selectGameimage() throws SQLException {
        sentence = "select * from tblgameimage";
        String json = con.getRecordsInJson(sentence);
        return json;
    }
    

    public ArrayList<GameimageModel> selectGameimageid() throws Exception {
        sentence  = "select idgameimage from tblgameimage";
        ArrayList<GameimageModel> datos = con.getObjectDB(sentence, GameimageModel.class, 1);
        return datos;
    }

    public String last_id() throws SQLException {
        sentence = "select idgame from tblgame ORDER BY idgame DESC LIMIT 1";
        String id = con.getNextID(sentence);
        return id;
    }

    public boolean insertGameimage(GameimageModel giM) throws Exception {
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
    
    public String insertGameimagef(GameimageModel giM) throws Exception {
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
        return sentency;
    }

    public String updateGameimagef(GameimageModel giM) throws Exception {
        System.out.println("");
        String structure = String.format(
                "<gameimage>"
                + "<idgameimage>" + giM.getIdgameimage() + "</idgameimage>"
                + "<idgame>" + giM.getIdgame() + "</idgame>"
                + "<idcolortype>" + giM.getIdcolortype() + "</idcolortype>"
                + "<image>" + giM.getImage() + "</image>"
                + "<paragraph>" + giM.getParagraph() + "</paragraph>"
                + "<audio_parag>" + giM.getAudio_parag() + "</audio_parag>"
                + "<video_parag>" + giM.getVideo_parag() + "</video_parag>"
                + "<updatedate>" + giM.getUpdatedate() + "</updatedate>"
                + "<state>" + giM.getState() + "</state>"
                + "</gameimage>");

        String sentency = "Select * from updategameimage('" + structure + "')";
        // System.out.println(structure);
        return sentency;
    }

    public String deleteGameimagef(GameimageModel gM) throws Exception {
        String structure = String.format(
                "<gameimage>"
                + "<idgameimage>" + gM.getIdgameimage() + "</idgameimage>"
                + "</gameimage>");

        String sentency = "Select * from deletegameimage('" + structure + "')";
        System.out.println(structure);
        return sentency;
    }

}
