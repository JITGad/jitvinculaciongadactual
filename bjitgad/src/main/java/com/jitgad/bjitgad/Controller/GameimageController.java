package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameimageDAO;
import com.jitgad.bjitgad.Models.GameimageModel;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class GameimageController {

    private final GameimageDAO giD;

    public GameimageController() {
        giD = new GameimageDAO();
    }

    public String selectGameimage() throws Exception {
        return giD.selectGameimage();
    }

    public ArrayList<GameimageModel> selectGameimageid() throws Exception {
        return giD.selectGameimageid();
    }

    public String last_id() throws Exception {
        return giD.last_id();
    }

    public String InsertGameimageCF(GameimageModel request) throws Exception {

        request = UpdateFilesGameImage(request);

        request.setCreationdate("NOW()");
        request.setUpdatedate("NOW()");

        return giD.insertGameimagef(request);
    }

    public String UpdateGameimageC(GameimageModel request) throws Exception {

        request = UpdateFilesGameImage(request);

        request.setUpdatedate("NOW()");

        return giD.updateGameimagef(request);
    }

    public String DeleteGameimageC(GameimageModel request) throws Exception {
        return giD.deleteGameimagef(request);
    }

    private GameimageModel UpdateFilesGameImage(GameimageModel request) throws IOException {

        request.setImage(request.getImage() == null ? "" : request.getImage());

        request.setVideo_parag(request.getVideo_parag() == null ? "" : request.getVideo_parag());

        request.setAudio_parag(request.getAudio_parag() == null ? "" : request.getAudio_parag());

        request.setImagefigure(request.getImagefigure() == null ? "" : request.getImagefigure());

        return request;
    }
}
