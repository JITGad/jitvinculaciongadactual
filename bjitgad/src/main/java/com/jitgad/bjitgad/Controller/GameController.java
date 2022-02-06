package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameDAO;
import com.jitgad.bjitgad.DAO.GameimageDAO;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Models.GameimageModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jorge Molina
 */
public class GameController {

    private final GameDAO gD;
    private final GameimageController giC;
    private final GameimageDAO giD;

    public GameController() {
        gD = new GameDAO();
        giC = new GameimageController();
        giD = new GameimageDAO();
    }

    public ArrayList<GameModel> selectGame() {
        return gD.selectGame();
    }

    public ArrayList<GameModel> selectGamepage(int page) {
        return gD.selectGamepage(page);
    }

    public int CountingPageGame() {
        return gD.CountingPageGame();
    }

    public String selectGamebyid(int gameid) {
        return gD.selectGamebyid(gameid);
    }

    public ArrayList<ClaveValorModel> selectgamesbyactivities(int activityid) {
        return gD.selectgamesbyactivities(activityid);
    }

    public ResponseData InsertGameC(GameModel request, 
            String realpath) throws SQLException, Exception, IOException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

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
            String realpath) throws SQLException, Exception, IOException  {
        System.out.println("");
        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setUpdatedate("NOW()");

        if (gD.updateGame(request,realpath)) {
            
            responseData.setMessage("Registros actualizados correctamente");
            responseData.setFlag(true);

            return responseData;
        }

        return responseData;
    }

    public ResponseData DeleteGameC(GameModel request) throws SQLException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        if (gD.deleteGame(request)) {

           // giC.DeleteGameimageC(request);

            responseData.setMessage("Registro eliminado correctamente");
            responseData.setFlag(true);
            return responseData;
        }

        return responseData;
    }
}
