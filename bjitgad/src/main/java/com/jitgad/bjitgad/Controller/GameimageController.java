package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameimageDAO;
import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
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

}
