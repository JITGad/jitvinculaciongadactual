package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ClaveValorGameModel;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Models.GametypeModel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class GametypeDAO {

    private final ConectionPool con;
    private String sentence;

    public GametypeDAO() {
        con = ConectionPoolDataSource.getConnection();
    }

    public ArrayList<GametypeModel> selectGametypepage(int page) throws Exception {
        sentence = "select * from tblgametype order by idgametype asc limit 10 offset " + (page * 10 - 10);
        ArrayList<GametypeModel> datos = con.getObjectDB(sentence, GametypeModel.class, 1);
        return datos;
    }

    public ArrayList<GametypeModel> selectGametypewithgames() throws Exception {
        sentence = "select * from tblgametype order by idgametype asc ";
        ArrayList<GametypeModel> datos = con.getObjectDB(sentence, GametypeModel.class, 1);

        for (int i = 0; i < datos.size(); i++) {
            sentence = "select tblgame.idgame, tblgame.idactivitiestype, tblgame.idgametype, \n"
                    + "tblgame.name, tblgame.creationdate, tblgame.updatedate, tblgame.state, tblgame.level \n"
                    + "from tblgame inner join tblgametype on tblgame.idgametype = tblgametype.idgametype \n"
                    + "where tblgametype.idgametype =" + datos.get(i).getIdgametype() + "order by idgame asc ";
            GametypeModel Tipojuegoactual = datos.get(i);
            Tipojuegoactual.setDetalles(con.getObjectDB(sentence, GameModel.class, 1));
            datos.set(i, Tipojuegoactual);
        }
        return datos;
    }

    public ArrayList<ClaveValorGameModel> selectgametypecv() throws Exception {
        sentence = "select idgametype as id, name as text, shortname as value from tblgametype order by idgametype";
        ArrayList<ClaveValorGameModel> datos = con.getObjectDB(sentence, ClaveValorGameModel.class, 1);
        return datos;
    }

    public String selectGametypebyid(int id) throws Exception {
        sentence = "select * from tblgametype where idgametype=" + id;
        ArrayList<GametypeModel> datos = con.getObjectDB(sentence, GametypeModel.class, 1);
        if (datos.size() > 0) {
            return Methods.objectToJsonString(datos.get(0));
        } else {
            return "{}";
        }
    }

    public int CountingPageGametype() throws SQLException {
        sentence = String.format("select * from tblgametype");
        return ((con.returnRecord(sentence)).getRowCount());
    }

    public boolean insertGametype(GametypeModel gametypeModel) throws Exception {
        String structure = String.format(
                "<gametype>"
                + "<name>" + gametypeModel.getName() + "</name>"
                + "<image>" + gametypeModel.getImage() + "</image>"
                + "<audio_instructions>" + gametypeModel.getAudio_instructions() + "</audio_instructions>"
                + "<text_instructions>" + gametypeModel.getText_instructions() + "</text_instructions>"
                + "<video_instructions>" + gametypeModel.getVideo_instructions() + "</video_instructions>"
                + "<shortname>" + gametypeModel.getShortname() + "</shortname>"
                + "<creationdate>" + gametypeModel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + gametypeModel.getUpdatedate() + "</updatedate>"
                + "<state>" + gametypeModel.getState() + "</state>"
                + "</gametype>");

        String sentency = "Select * from insertgametype('" + structure + "')";
        return con.modifyBD(sentency);
    }

    public boolean updateGametype(GametypeModel gametypeModel) throws Exception {
        String structure = String.format(
                "<gametype>"
                + "<idgametype>" + gametypeModel.getIdgametype() + "</idgametype>"
                + "<name>" + gametypeModel.getName() + "</name>"
                + "<image>" + gametypeModel.getImage() + "</image>"
                + "<audio_instructions>" + gametypeModel.getAudio_instructions() + "</audio_instructions>"
                + "<text_instructions>" + gametypeModel.getText_instructions() + "</text_instructions>"
                + "<video_instructions>" + gametypeModel.getVideo_instructions() + "</video_instructions>"
                + "<updatedate>" + gametypeModel.getUpdatedate() + "</updatedate>"
                + "<state>" + gametypeModel.getState() + "</state>"
                + "</gametype>");

        String sentency = "Select * from updategametype('" + structure + "')";
        return con.modifyBD(sentency);
    }

    public boolean DeleteGametype(GametypeModel gametypeModel) throws Exception {
        String structure = String.format(
                "<gametype>"
                + "<idgametype>" + gametypeModel.getIdgametype() + "</idgametype>"
                + "</gametype>");

        String sentency = "Select * from deletegametype('" + structure + "')";
        return con.modifyBD(sentency);
    }

}
