/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.Controller.GameimageController;
import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Models.GameimageModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class GameDAO {

    private final ConectionPool con;
    private final GameimageController giC;
    String sentence;
    Connection conex;

    public GameDAO() {
        con = ConectionPoolDataSource.getConnection();
        giC = new GameimageController();
    }

    public ArrayList<GameModel> selectGame() throws Exception {
        sentence = "select game.idgame,game.idactivitiestype,actype.name as nameactivities,game.idgametype,game.name,game.creationdate,game.updatedate,game.state,game.level,game.image\n"
                + "from tblgame as game inner join tblactivitiestype as actype on actype.idactivitiestype = game.idactivitiestype";
        ArrayList<GameModel> datos = con.getObjectDB(sentence, GameModel.class, 1);
        return datos;
    }

    public ArrayList<GameModel> selectGamepage(int page) throws Exception {
        sentence = "select "
                + "game.idgame, "
                + "game.idactivitiestype, "
                + "actype.name as nameactivities, "
                + "tblgametype.name as namegametype, "
                + "game.idgametype, "
                + "game.name, "
                + "game.creationdate, "
                + "game.updatedate, "
                + "game.state, "
                + "game.level, "
                + "game.image "
                + "from tblgame as game "
                + "inner join tblactivitiestype as actype on actype.idactivitiestype = game.idactivitiestype "
                + "inner join tblgametype on tblgametype.idgametype = game.idgametype "
                + "order by game.idgame asc limit 10 offset " + (page * 10 - 10);
        ArrayList<GameModel> datos = con.getObjectDB(sentence, GameModel.class, 1);

        return datos;
    }

    public String selectGamebyid(int gameid) throws Exception {
        sentence = "select "
                + "game.idgame, "
                + "game.idactivitiestype, "
                + "actype.name as nameactivities, "
                + "tblgametype.name as namegametype, "
                + "game.idgametype, "
                + "tblgametype.shortname, "
                + "game.name, "
                + "game.creationdate, "
                + "game.updatedate, "
                + "game.state, "
                + "game.level, "
                + "game.image "
                + "from tblgame as game "
                + "inner join tblactivitiestype as actype on actype.idactivitiestype = game.idactivitiestype "
                + "inner join tblgametype on tblgametype.idgametype = game.idgametype "
                + "where game.idgame=" + gameid;
        ArrayList<GameModel> datos = con.getObjectDB(sentence, GameModel.class, 1);

        GameModel JuegoSeleccionado = datos.get(0);

        sentence = "select tblgameimage.idgameimage, tblgameimage.idgame, tblgameimage.idcolortype,tblcolortype.name as color, tblcolortype.html, tblgameimage.image,\n"
                + "tblgameimage.paragraph, tblgameimage.audio_parag, tblgameimage.video_parag, tblgameimage.creationdate, tblgameimage.updatedate, tblgameimage.state,tblgameimage.imagefigure\n"
                + "from tblgameimage inner join tblcolortype on tblcolortype.idcolortype = tblgameimage.idcolortype where idgame=" + JuegoSeleccionado.getIdgame() + "\n"
                + "union\n"
                + "select tblgameimage.idgameimage, tblgameimage.idgame, tblgameimage.idcolortype,null as color, null as html, tblgameimage.image,\n"
                + "tblgameimage.paragraph, tblgameimage.audio_parag, tblgameimage.video_parag, tblgameimage.creationdate, tblgameimage.updatedate, tblgameimage.state,tblgameimage.imagefigure\n"
                + "from tblgameimage where idgame =" + JuegoSeleccionado.getIdgame() + " and tblgameimage.idcolortype = 0";
        JuegoSeleccionado.setDetalles(con.getObjectDB(sentence, GameimageModel.class, 1));

        for (int i = 0; i < JuegoSeleccionado.getDetalles().size(); i++) {
            JuegoSeleccionado.getDetalles().get(i).setSecuence(i);
        }

        return Methods.objectToJsonString(JuegoSeleccionado);
    }

    public int CountingPageGame() throws SQLException {
        sentence = String.format("select * from tblgame");
        return ((con.returnRecord(sentence)).getRowCount());
    }

    public ArrayList<ClaveValorModel> selectgamesbyactivities(int idactivity) throws Exception {
        sentence = "select "
                + "tblgame.idgame as id, "
                + "tblgame.name as name "
                + "from tblgametype "
                + "inner join tblgame on tblgame.idgametype = tblgametype.idgametype "
                + "inner join tblactivitiestype on tblgame.idactivitiestype = tblactivitiestype.idactivitiestypewhere "
                + "where tblactivitiestype.idactivitiestype =" + idactivity;
        ArrayList<ClaveValorModel> datos = con.getObjectDB(sentence, ClaveValorModel.class, 1);
        return datos;
    }

    public boolean insertGame(GameModel gameModel, String realpath) throws SQLException, Exception {
        String structure = String.format(
                "<game>"
                + "<idactivitiestype>" + gameModel.getIdactivitiestype() + "</idactivitiestype>"
                + "<idgametype>" + gameModel.getIdgametype() + "</idgametype>"
                + "<name>" + gameModel.getName() + "</name>"
                + "<creationdate>" + gameModel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + gameModel.getUpdatedate() + "</updatedate>"
                + "<state>" + gameModel.getState() + "</state>"
                + "<level>" + gameModel.getLevel() + "</level>"
                + "<image>" + gameModel.getImage() + "</image>"
                + "</game>");

        String sentency = "Select * from insertGame('" + structure + "')";
        try {
            conex = con.getConnection();
            conex.setAutoCommit(false);

            try (Statement st = conex.createStatement()) {

                ArrayList<GameModel> datagameModel = con.getObjectDBCon(sentency, GameModel.class, 1, conex);

                int id = datagameModel.get(0).getIdgame();

                for (int i = 0; i < gameModel.getDetalles().size(); i++) {
                    GameimageModel object = gameModel.getDetalles().get(i);
                    object.setIdgame(id);
                    object.setSecuence(i);
                    st.execute(giC.InsertGameimageCF(object, realpath));
                }
            }
            conex.commit();
            return true;
        } catch (SQLException exc) {
            conex.rollback();
            throw exc;
        } finally {
            con.releaseConnection(conex);
        }
    }

    public boolean updateGame(GameModel gameModel, String realpath)
            throws SQLException, Exception {
        String structure = String.format(
                "<game>"
                + "<idgame>" + gameModel.getIdgame() + "</idgame>"
                + "<idactivitiestype>" + gameModel.getIdactivitiestype() + "</idactivitiestype>"
                + "<idgametype>" + gameModel.getIdgametype() + "</idgametype>"
                + "<name>" + gameModel.getName() + "</name>"
                + "<updatedate>" + gameModel.getUpdatedate() + "</updatedate>"
                + "<state>" + gameModel.getState() + "</state>"
                + "<level>" + gameModel.getLevel() + "</level>"
                + "<image>" + gameModel.getImage() + "</image>"
                + "</game>");

        String sentency = "Select * from updateGame('" + structure + "')";
        try {
            conex = con.getConnection();
            conex.setAutoCommit(false);

            try (Statement st = conex.createStatement()) {
                st.execute(sentency);

                sentence = "select idgameimage from tblgameimage where idgame = " + gameModel.getIdgame();
                ArrayList<GameimageModel> dataid = con.getObjectDBCon(sentence, GameimageModel.class, 1, conex);

                for (int i = 0; i < dataid.size(); i++) {

                    GameimageModel gimx = dataid.get(i);
                    GameimageModel gim = gameModel
                            .getDetalles()
                            .stream()
                            .filter(gameim -> gimx.getIdgameimage() == gameim.getIdgameimage())
                            .findAny()
                            .orElse(null);

                    if (gim == null) {
                        String dgic = giC.DeleteGameimageC(gimx);
                        st.execute(dgic);
                    }
                }

                for (int i = 0; i < gameModel.getDetalles().size(); i++) {
                    GameimageModel object = gameModel.getDetalles().get(i);
                    GameimageModel gim = dataid
                            .stream()
                            .filter(gameim -> object.getIdgameimage() == gameim.getIdgameimage())
                            .findAny()
                            .orElse(null);

                    object.setIdgame(gameModel.getIdgame());
                    object.setSecuence(i);

                    if (gim == null) {
                        String dataInsert = giC.InsertGameimageCF(object, realpath);
                        st.execute(dataInsert);
                        continue;
                    }

                    String dataUpdate = giC.UpdateGameimageC(object, realpath);
                    st.execute(dataUpdate);
                }
            }
            conex.commit();
            return true;
        } catch (SQLException exc) {
            conex.rollback();
            throw exc;
        } finally {
            con.releaseConnection(conex);
        }
    }

    public boolean deleteGame(GameModel gameModel) throws SQLException {
        String structure = String.format(
                "<game>"
                + "<idgame>" + gameModel.getIdgame() + "</idgame>"
                + "</game>");

        String sentency = "Select * from deleteGame('" + structure + "')";
        return con.modifyBD(sentency);
    }

}
