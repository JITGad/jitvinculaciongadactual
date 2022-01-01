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
     
    public Object[] InsertGameC(String Idactivitiestype, String Idgametype,
            String Name, String creationdate, String updatedate, String state) {
        String message = "";
        boolean status = false;
        gM.setIdactivitiestype(Idactivitiestype);
        gM.setIdgametype(Idgametype);
        gM.setName(Name);
        gM.setCreationdate(creationdate);
        gM.setUpdatedate(updatedate);
        gM.setState(state);
        
        if(gD.insertGame(gM)){
            message = "The Game was inserted.";
            status = true;
        }   else {
                message = "The Game was not inserted";
                status = false;
            }

        return new Object[]{status, message};
    }  
}
