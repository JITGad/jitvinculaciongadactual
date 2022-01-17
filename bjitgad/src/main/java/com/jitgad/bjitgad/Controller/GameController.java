package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.util.ArrayList;

/**
 *
 * @author Jorge Molina
 */
public class GameController {

    private Conection conex;
    private GameDAO gD;
    private GameModel gM;

    public GameController() {
        conex = new Conection();
        gD = new GameDAO();
        gM = new GameModel();

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

    public ResponseData InsertGameC(GameModel request) {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        try {
            request.setCreationdate("NOW()");
            request.setUpdatedate("NOW()");

            if (gD.insertGame(request)) {
                responseData.setMessage("Registros insertados correctamente");
                responseData.setFlag(true);
                return responseData;
            }
            responseData.setMessage("Registros no insertados,"
                    + "datos erróneos para enviar a la base de datos!");
            responseData.setFlag(false);
            return responseData;

        } catch (Exception e) {
            responseData.setFlag(false);

            if (Configuration.DEBUG) {
                responseData.setMessage(e.getMessage());
                return responseData;
            }

            responseData.setMessage("Ha ocurrido un error eliminando "
                    + "un tipo de color, vuelva a intentarlo mas tarde");

            System.err.println(e.getMessage());
        }
        return responseData;
    }

    public ResponseData UpdateGameC(GameModel request) {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setUpdatedate("NOW()");

        try {
            if (gD.updateGame(request)) {
                
                responseData.setMessage("Registros actualizados correctamente");
                responseData.setFlag(true);
                
                return responseData;
            }
            
            responseData.setMessage("Registros no insertados,"
                    + "datos erróneos para enviar a la base de datos!");
            responseData.setFlag(false);
            
            return responseData;
            
        } catch (Exception e) {
            
            responseData.setFlag(false);

            if (Configuration.DEBUG) {
                responseData.setMessage(e.getMessage());
                return responseData;
            }

            responseData.setMessage("Ha ocurrido un error eliminando "
                    + "un tipo de color, vuelva a intentarlo mas tarde");

            System.err.println(e.getMessage());
        }
        return responseData;
    }

    public ResponseData DeleteGameC(GameModel request) {
        
        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        try {
            if (gD.deleteGame(request)) {

                responseData.setMessage("Registro eliminado correctamente");
                responseData.setFlag(true);
                return responseData;
            }
            responseData.setMessage("El registro no fue eliminado,"
                    + "datos erróneos para enviar a la base de datos!");
            responseData.setFlag(false);

            return responseData;
        } catch (Exception e) {
            responseData.setFlag(false);

            if (Configuration.DEBUG) {
                responseData.setMessage(e.getMessage());
                return responseData;
            }

            responseData.setMessage("Ha ocurrido un error eliminando "
                    + "un tipo de color, vuelva a intentarlo mas tarde");

            System.err.println(e.getMessage());
        }
        return responseData;
    }
}
