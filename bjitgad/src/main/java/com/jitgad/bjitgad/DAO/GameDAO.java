/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class GameDAO {

    ConectionPool con;
    String sentence;

    public GameDAO() {
        con = ConectionPoolDataSource.getConnection();
    }

    public ArrayList<GameModel> selectGame() {
        sentence = "select * from tblgame";
        ArrayList<GameModel> datos = con.getObjectDB(sentence, GameModel.class, 1);
        return datos;
    }
    
    public ArrayList<GameModel> selectGamepage(int page){
        sentence ="select * from tblgame order by idgame asc limit 10 offset "+ (page * 10 - 10);
        ArrayList<GameModel> datos = con.getObjectDB(sentence, GameModel.class, 1);
        return datos;
    }
    
    public String selectGamebyid(int gameid){
        sentence ="select * from tblgame where idgame="+gameid;
        ArrayList<GameModel> datos = con.getObjectDB(sentence, GameModel.class, 1);
       if (datos.size() > 0) {
            return Methods.objectToJsonString(datos.get(0));
        } else {
            return "{}";
        }
    }
    
    public int CountingPageGame(){
      sentence = String.format("select * from tblgame");
      return  ((con.returnRecord(sentence)).getRowCount());
    }

    public ArrayList<ClaveValorModel> selectgamesbyactivities(int idactivity) {
        sentence = "select tblgame.idgame as id, tblgame.name as name from tblgametype "
                + "inner join tblgame on tblgame.idgametype = tblgametype.idgametype "
                + "inner join tblactivitiestype "
                + "on tblgame.idactivitiestype = tblactivitiestype.idactivitiestypewhere tblactivitiestype.idactivitiestype =" + idactivity;
        ArrayList<ClaveValorModel> datos = con.getObjectDB(sentence, ClaveValorModel.class, 1);
        return datos;
    }

    public boolean insertGame(GameModel gameModel) throws SQLException {
        String structure = String.format(
                "<game>"
                + "<idactivitiestype>" + gameModel.getIdactivitiestype() + "</idactivitiestype>"
                + "<idgametype>" + gameModel.getIdgametype() + "</idgametype>"
                + "<name>" + gameModel.getName() + "</name>"
                + "<creationdate>" + gameModel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + gameModel.getUpdatedate() + "</updatedate>"
                + "<state>" + gameModel.getState() + "</state>"
                + "<level>" + gameModel.getLevel() + "</level>"        
                + "</game>");

        String sentency = "Select * from insertGame('" + structure + "')";
        System.out.println(structure);
        return con.modifyBD(sentency);
    }

    public boolean updateGame(GameModel gameModel) throws SQLException {
        String structure = String.format(
                "<game>"
                + "<idgame>" + gameModel.getIdgame() + "</idgame>"
                + "<idactivitiestype>" + gameModel.getIdactivitiestype() + "</idactivitiestype>"
                + "<idgametype>" + gameModel.getIdgametype() + "</idgametype>"
                + "<name>" + gameModel.getName() + "</name>"
                + "<updatedate>" + gameModel.getUpdatedate() + "</updatedate>"
                + "<state>" + gameModel.getState() + "</state>"
                + "<level>" + gameModel.getLevel() + "</level>" 
                + "</game>");

        String sentency = "Select * from updateGame('" + structure + "')";
        System.out.println(structure);
        return con.modifyBD(sentency);
    }

    public boolean deleteGame(GameModel gameModel) throws SQLException {
        String structure = String.format(
                "<game>"
                + "<idgame>" + gameModel.getIdgame() + "</idgame>"
                + "</game>");

        String sentency = "Select * from deleteGame('" + structure + "')";
        System.out.println(structure);
        return con.modifyBD(sentency);
    }

}
