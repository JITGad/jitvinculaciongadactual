package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameimageDAO;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Models.GameimageModel;
import com.jitgad.bjitgad.Utilities.ResponseCreateFile;
import com.jitgad.bjitgad.Utilities.ResponseData;
import com.jitgad.bjitgad.Utilities.UniqueName;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
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
    
    public ArrayList<GameimageModel> selectGameimageid() throws Exception{
        return giD.selectGameimageid();
    }
    
    public String last_id() throws Exception{
        return giD.last_id();
    }

//    public ResponseData InsertGameimageC(GameimageModel request,
//            String realpath) throws SQLException, 
//            UnsupportedEncodingException, Exception {
//        System.out.println("");
//        
//        ResponseData responseData = new ResponseData("Ocurrió un error", false);
//
//        request.setImage(request.getImage() == null ? "" : request.getImage());
//        
//        request.setVideo_parag(request.getVideo_parag() == null ? "" : request.getVideo_parag());
//
//        request.setAudio_parag(request.getAudio_parag() == null ? "" : request.getAudio_parag());
//        
//       
//        ResponseCreateFile CreateFile = fc.createfile(request.getImage(), "gameimage", un.nunique(), realpath);
//        if (CreateFile.isState()) {
//            request.setImage(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
//        } 
//        
//        CreateFile = fc.createfile(request.getVideo_parag(), "gameimage", un.nunique(), realpath);
//        if (CreateFile.isState()) {
//            request.setVideo_parag(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
//        } 
//        
//        CreateFile = fc.createfile(request.getAudio_parag(), "gameimage", un.nunique(), realpath);
//        if (CreateFile.isState()) {
//            request.setAudio_parag(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
//        }
//        
//        request.setCreationdate("NOW()");
//        request.setUpdatedate("NOW()");
//
//        if (giD.insertGameimage(request)) {
//            responseData.setMessage("Registros insertados correctamente");
//            responseData.setFlag(true);
//            return responseData;
//        }
//
//        return responseData;
//    }
    
    public String InsertGameimageCF(GameimageModel request,
            String realpath) throws SQLException, 
            UnsupportedEncodingException, Exception {
        System.out.println("");
        
        ResponseData responseData = new ResponseData("Ocurrió un error", false);

        request.setImage(request.getImage() == null ? "" : request.getImage());
        
        request.setVideo_parag(request.getVideo_parag() == null ? "" : request.getVideo_parag());

        request.setAudio_parag(request.getAudio_parag() == null ? "" : request.getAudio_parag());
        
       
        ResponseCreateFile CreateFile = fc.createfile(request.getImage(), "gameimage", un.nunique(), realpath);
        if (CreateFile.isState()) {
            request.setImage(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        } 
        
        CreateFile = fc.createfile(request.getVideo_parag(), "gameimage", un.nunique(), realpath);
        if (CreateFile.isState()) {
            request.setVideo_parag(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        } 
        
        CreateFile = fc.createfile(request.getAudio_parag(), "gameimage", un.nunique(), realpath);
        if (CreateFile.isState()) {
            request.setAudio_parag(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        }
        
        request.setCreationdate("NOW()");
        request.setUpdatedate("NOW()");
        
        return giD.insertGameimagef(request);
    }

    public String UpdateGameimageC(GameimageModel request,
            String realpath) throws SQLException, Exception {
      //  ResponseData responseData = new ResponseData("Ocurrió un error", false);

       request.setImage(request.getImage() == null ? "" : request.getImage());
        
        request.setVideo_parag(request.getVideo_parag() == null ? "" : request.getVideo_parag());

        request.setAudio_parag(request.getAudio_parag() == null ? "" : request.getAudio_parag());
        
       
        ResponseCreateFile CreateFile = fc.createfile(request.getImage(), "gameimage", un.nunique(), realpath);
        if (CreateFile.isState()) {
            request.setImage(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        } 
        
        CreateFile = fc.createfile(request.getVideo_parag(), "gameimage", un.nunique(), realpath);
        if (CreateFile.isState()) {
            request.setVideo_parag(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        } 
        
        CreateFile = fc.createfile(request.getAudio_parag(), "gameimage", un.nunique(), realpath);
        if (CreateFile.isState()) {
            request.setAudio_parag(String.join("/", new String[]{CreateFile.getRutaRelativa(), CreateFile.getNombreArchivo()}));
        } 
        
        request.setUpdatedate("NOW()");

        return giD.updateGameimagef(request);
    }

    public String DeleteGameimageC(GameimageModel request) throws Exception {
        return giD.deleteGameimagef(request);
    }

}
