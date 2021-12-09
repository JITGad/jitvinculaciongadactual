
package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameimageDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.GameimageModel;

/**
 *
 * @author jorge
 */
public class GameimageController {
    
    private Conection conex;
    private GameimageDAO giD;
    private GameimageModel giM;

    public GameimageController() {
        conex = new Conection();
        giD = new GameimageDAO();
        giM = new GameimageModel();
    }
    
    public String selectGameimage(){
        return giD.selectGameimage();
    }
    
    public Object[] InsertGameimageC(String idgame, String idcolortype,
            String image, String paragraph, String audio_parag,
            String video_parag, String creationdate, String updatedate,
            String state) {
        String message = "";
        boolean status = false;
        giM.setIdgame(idgame);
        giM.setIdcolortype(idcolortype);
        giM.setImage(image);
        giM.setParagraph(paragraph);
        giM.setAudio_parag(audio_parag);
        giM.setVideo_parag(video_parag);
        giM.setCreationdate(creationdate);
        giM.setUpdatedate(updatedate);
        giM.setState(state);
        
        if(giD.insertGameimage(giM)){
            message = "The Game image type was inserted.";
            status = true;
        }   else {
                message = "The Game image type was not inserted";
                status = false;
            }

        return new Object[]{status, message};
    }
    
    
    
}
