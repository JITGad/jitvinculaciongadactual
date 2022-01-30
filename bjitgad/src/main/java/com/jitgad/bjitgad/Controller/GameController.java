package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameDAO;
import com.jitgad.bjitgad.DAO.GameimageDAO;
import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Models.GameimageModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jorge Molina
 */
public class GameController {

    private GameDAO gD;
    private GameModel gM;
    private GameimageModel gameimageModel;
    private GameimageController giC;
    private GameimageDAO giD;

    public GameController() {
        gD = new GameDAO();
        gM = new GameModel();
        giC = new GameimageController();
        gameimageModel = new GameimageModel();
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

    public ResponseData InsertGameC(GameModel request) throws SQLException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

            request.setCreationdate("NOW()");
            request.setUpdatedate("NOW()");

            if (gD.insertGame(request)) {

                int id = Integer.parseInt(giD.last_id());
               // System.out.println(id);
                for (GameimageModel object : request.getDetalles()) {
                    object.setIdgame(id);
                    responseData = giC.InsertGameimageC(object);
                }

                responseData.setMessage("Registros insertados correctamente");
                responseData.setFlag(true);
                return responseData;
                }

        return responseData;
    }

    public ResponseData UpdateGameC(GameModel request) throws SQLException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setUpdatedate("NOW()");

     
            if (gD.updateGame(request)) {
                
                for (GameimageModel object : request.getDetalles()) {
                    responseData = giC.UpdateGameimageC(object);
                }
                
                responseData.setMessage("Registros actualizados correctamente");
                responseData.setFlag(true);

                return responseData;
                }

        return responseData;
    }

    public ResponseData DeleteGameC(GameModel request) throws SQLException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

            if (gD.deleteGame(request)) {
                
                giC.DeleteGameimageC(request);
                
                responseData.setMessage("Registro eliminado correctamente");
                responseData.setFlag(true);
                return responseData;
            }

            return responseData;
    }
}
