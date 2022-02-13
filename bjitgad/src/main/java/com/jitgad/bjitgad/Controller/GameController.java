package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameDAO;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Utilities.ResponseCreateFile;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Jorge Molina
 */
public class GameController {

    private final GameDAO gD;
    private final FileController fc;

    public GameController() {
        gD = new GameDAO();
        fc = new FileController();
    }
 
    public ArrayList<GameModel> selectGame() throws Exception {
        return gD.selectGame();
    }

    public ArrayList<GameModel> selectGamepage(int page) throws Exception {
        return gD.selectGamepage(page);
    }

    public int CountingPageGame() throws Exception {
        return gD.CountingPageGame();
    }

    public String selectGamebyid(int gameid) throws Exception {
        return gD.selectGamebyid(gameid);
    }

    public ArrayList<ClaveValorModel> selectgamesbyactivities(int activityid) throws Exception {
        return gD.selectgamesbyactivities(activityid);
    }

    public ResponseData InsertGameC(GameModel request, 
            String realpath) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);
        
        request = UpdateGameModel(request, realpath);
        
        request.setCreationdate("NOW()");
        request.setUpdatedate("NOW()");

        if (gD.insertGame(request, realpath)) {

            responseData.setMessage("Registros insertados correctamente");
            responseData.setFlag(true);
            return responseData;
        }

        return responseData;
    }

    public ResponseData UpdateGameC(GameModel request, 
            String realpath) throws Exception  {
        
        ResponseData responseData = new ResponseData("Ocurrió un error", false);
        
        request = UpdateGameModel(request, realpath);

        request.setUpdatedate("NOW()");

        if (gD.updateGame(request,realpath)) {
            
            responseData.setMessage("Registros actualizados correctamente");
            responseData.setFlag(true);

            return responseData;
        }

        return responseData;
    }

    public ResponseData DeleteGameC(GameModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        if (gD.deleteGame(request)) {

            responseData.setMessage("Registro eliminado correctamente");
            responseData.setFlag(true);
            return responseData;
        }

        return responseData;
    }
    
    
    private GameModel UpdateGameModel(GameModel request, String realpath) throws IOException{
        request.setImage(request.getImage() == null ? "" : request.getImage());

        ResponseCreateFile CreateFile = fc.createfile(request.getImage(), "game", request.getName(), realpath);
        if (CreateFile.isState()) {
            request.setImage(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        }   
        return request;
    }
}
