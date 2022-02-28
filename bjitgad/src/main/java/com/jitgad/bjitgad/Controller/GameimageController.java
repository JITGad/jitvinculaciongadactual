package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameimageDAO;
import com.jitgad.bjitgad.Models.GameimageModel;
import com.jitgad.bjitgad.Utilities.ResponseCreateFile;
import com.jitgad.bjitgad.Utilities.UniqueName;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class GameimageController {

    private final GameimageDAO giD;
    private final FileController fc;
    private final UniqueName un;

    public GameimageController() {
        giD = new GameimageDAO();
        fc = new FileController();
        un = new UniqueName();
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

    public String InsertGameimageCF(GameimageModel request,
            String realpath) throws Exception {

        request = UpdateFilesGameImage(request, realpath);

        request.setCreationdate("NOW()");
        request.setUpdatedate("NOW()");

        return giD.insertGameimagef(request);
    }

    public String UpdateGameimageC(GameimageModel request,
            String realpath) throws Exception {

        request = UpdateFilesGameImage(request, realpath);

        request.setUpdatedate("NOW()");

        return giD.updateGameimagef(request);
    }

    public String DeleteGameimageC(GameimageModel request) throws Exception {
        return giD.deleteGameimagef(request);
    }

    private GameimageModel UpdateFilesGameImage(GameimageModel request, String realpath) throws IOException {

        request.setImage(request.getImage() == null ? "" : request.getImage());

        request.setVideo_parag(request.getVideo_parag() == null ? "" : request.getVideo_parag());

        request.setAudio_parag(request.getAudio_parag() == null ? "" : request.getAudio_parag());

        request.setImagefigure(request.getImagefigure() == null ? "" : request.getImagefigure());

        ResponseCreateFile CreateFile = fc.createfile(request.getImage(), "gameimage", un.nunique(), realpath);
        if (CreateFile.isState()) {
            request.setImage(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        }

        CreateFile = fc.createfile(request.getImagefigure(), "gameimage", un.nunique(), realpath);
        if (CreateFile.isState()) {
            request.setImagefigure(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        }

        CreateFile = fc.createfile(request.getVideo_parag(), "gameimage", un.nunique(), realpath);
        if (CreateFile.isState()) {
            request.setVideo_parag(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        }

        CreateFile = fc.createfile(request.getAudio_parag(), "gameimage", un.nunique(), realpath);
        if (CreateFile.isState()) {
            request.setAudio_parag(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        }

        return request;
    }
}
