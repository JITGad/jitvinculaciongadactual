/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.GameModel;

/**
 *
 * @author jorge
 */
public class GameDAO {

    Conection con;
    String sentence;

    public GameDAO() {
        con = new Conection();
    }

    public String selectGame() {
        sentence = "select * from tblgame";
        String json = con.getRecordsInJson(sentence);
        return json;
    }

    public boolean insertGame(GameModel gameModel) {
        String structure = String.format(
                "<game>"
                + "<idactivitiestype>" + gameModel.getIdactivitiestype() + "</idactivitiestype>"
                + "<idgametype>" + gameModel.getIdgametype() + "</idgametype>"
                + "<name>" + gameModel.getName() + "</name>"
                + "<creationdate>" + gameModel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + gameModel.getUpdatedate() + "</updatedate>"
                + "<state>" + gameModel.getState() + "</state>"
                + "</game>");

        String sentency = "Select * from insertGame('" + structure + "')";
        // System.out.println(structure);
        return con.modifyBD(sentency);
    }

}
