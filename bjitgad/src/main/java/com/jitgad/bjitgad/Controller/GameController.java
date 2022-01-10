package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GameDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.GameModel;

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
    
     public String selectGame(){
        return gD.selectGame();
    }
    
    public String selectGamepage(int page){
        return gD.selectGamepage(page);
    }
    
    public int CountingPageGame(){
        return gD.CountingPageGame();
    }

    public String selectGamebyid(int gameid){
        return gD.selectGamebyid(gameid);
    }

    public String selectgamesbyactivities(int activityid) {
        return gD.selectgamesbyactivities(activityid);
    }
     
    public Object[] InsertGameC(String Idactivitiestype, String Idgametype,
            String Name, boolean state, int level) {
        String message = "";
        boolean status = false;
        gM.setIdactivitiestype(Idactivitiestype);
        gM.setIdgametype(Idgametype);
        gM.setName(Name);
        gM.setCreationdate("NOW()");
        gM.setUpdatedate("NOW()");
        gM.setState(state);
        gM.setLevel(level);
        
        if(gD.insertGame(gM)){
            message = "Registros insertados correctamente";
            status = true;
        }   else {
                message = "Registros no insertados, datos erróneos para enviar a la base de datos!";
                status = false;
            }

        return new Object[]{status, message};
    }  

    public Object[] UpdateGameC(int Idgame,String Idactivitiestype, String Idgametype,
            String Name, boolean state, int level) {
        String message = "";
        boolean status = false;
        gM.setIdgame(Idgame);
        gM.setIdactivitiestype(Idactivitiestype);
        gM.setIdgametype(Idgametype);
        gM.setName(Name);
        gM.setCreationdate("NOW()");
        gM.setUpdatedate("NOW()");
        gM.setState(state);
        gM.setLevel(level);
        
        if(gD.updateGame(gM)){
            message = "Registros actualizados correctamente";
            status = true;
        }   else {
            message = "Los registros no fueron actualizados, datos erróneos para enviar a la base de datos!";
                status = false;
            }

        return new Object[]{status, message};
    } 
    
    public Object[] DeleteGameC(int Idgame) {
        String message = "";
        boolean status = false;
        gM.setIdgame(Idgame);
        
        if(gD.deleteGame(gM)){
            message = "Registro eliminado correctamente";
            status = true;
        }   else {
            message = "El registro no fué eliminado, datos erróneos para enviar a la base de datos!";
            status = false;
            }

        return new Object[]{status, message};
    }
}
