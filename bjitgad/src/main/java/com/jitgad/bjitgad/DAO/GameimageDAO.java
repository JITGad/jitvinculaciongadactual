package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Models.GameimageModel;
import java.sql.SQLException;

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

    public String selectGameimage() {
        sentence = "select * from tblgameimage";
        String json = con.getRecordsInJson(sentence);
        return json;
    }
    
    public String last_id() {
        sentence = "select idgame from tblgame ORDER BY idgame DESC LIMIT 1";
        String id = con.getNextID(sentence);
        return id;
    }

    public boolean insertGameimage(GameimageModel giM) throws SQLException {
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
    
    public boolean updateGameimage(GameimageModel giM) throws SQLException {
        String structure = String.format(
                "<gameimage>"
                + "<idgameimage>" + giM.getIdgameimage()+ "</idgameimage>"        
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
        return con.modifyBD(sentency);
    }
    
    public boolean deleteGameimage(GameModel gM) throws SQLException {
        String structure = String.format(
                "<gameimage>"
                + "<idgame>" + gM.getIdgame()+ "</idgame>"        
                + "</gameimage>");

        String sentency = "Select * from deletegameimage('" + structure + "')";
        // System.out.println(structure);
        return con.modifyBD(sentency);
    }

}
