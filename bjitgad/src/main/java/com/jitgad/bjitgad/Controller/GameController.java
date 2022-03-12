package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameDAO;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Jorge Molina
 */
public class GameController {

    private final GameDAO gD;

    public GameController() {
        gD = new GameDAO();
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

    public ResponseData InsertGameC(GameModel request) throws Exception {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);
        
        request = UpdateGameModel(request);
        
        request.setCreationdate("NOW()");
        request.setUpdatedate("NOW()");

        if (gD.insertGame(request)) {

            responseData.setMessage("Registros insertados correctamente");
            responseData.setFlag(true);
            return responseData;
        }

        return responseData;
    }

    public ResponseData UpdateGameC(GameModel request) throws Exception  {
        
        ResponseData responseData = new ResponseData("Ocurrió un error", false);
        
        request = UpdateGameModel(request);

        request.setUpdatedate("NOW()");

        if (gD.updateGame(request)) {
            
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
    
    
    private GameModel UpdateGameModel(GameModel request) throws IOException{
        request.setImage(request.getImage() == null ? "" : request.getImage());

        return request;
    }
}
