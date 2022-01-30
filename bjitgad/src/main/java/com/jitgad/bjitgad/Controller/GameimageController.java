package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameimageDAO;
import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Models.GameimageModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import java.sql.SQLException;

/**
 *
 * @author jorge
 */
public class GameimageController {

    private GameimageDAO giD;
    private GameimageModel giM;

    public GameimageController() {
        giD = new GameimageDAO();
        giM = new GameimageModel();
    }

    public String selectGameimage() {
        return giD.selectGameimage();
    }

    public ResponseData InsertGameimageC(GameimageModel request) throws SQLException {
        System.out.println("");
        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setCreationdate("NOW()");
        request.setUpdatedate("NOW()");

        if (giD.insertGameimage(request)) {
            responseData.setMessage("Registros insertados correctamente");
            responseData.setFlag(true);
            return responseData;
        }

        return responseData;
    }
    
    public ResponseData UpdateGameimageC(GameimageModel request) throws SQLException {
        System.out.println("");
        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setUpdatedate("NOW()");

        if (giD.updateGameimage(request)) {
            
            responseData.setMessage("Registros actualizados correctamente");
            responseData.setFlag(true);
            return responseData;
        }

        return responseData;
    }
    
    public ResponseData DeleteGameimageC(GameModel request) throws SQLException {

        ResponseData responseData = new ResponseData("Ocurrió un error", false);
     
            if (giD.deleteGameimage(request)) {

                responseData.setMessage("Registro eliminado correctamente");
                responseData.setFlag(true);
                return responseData;
            }

            return responseData;
    }
    
    
    

}
