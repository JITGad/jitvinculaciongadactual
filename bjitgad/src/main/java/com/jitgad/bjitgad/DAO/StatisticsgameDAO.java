package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import com.jitgad.bjitgad.Models.StatisticsgameModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class StatisticsgameDAO {

    private final ConectionPool con;
    String sentence;
    Connection conex;

    public StatisticsgameDAO() {
        con = ConectionPoolDataSource.getConnection();
    }

    public ArrayList<StatisticsgameModel> selectStatisticsgamepage(int page) throws Exception {
        sentence = "select * "
                + "from tblstatisticsgame "
                + "order by idstatisticsgame asc limit 10 offset " + (page * 10 - 10);
        ArrayList<StatisticsgameModel> datos = con.getObjectDB(sentence, StatisticsgameModel.class, 1);

        return datos;
    }

    public int CountingPageStatisticsgame() throws SQLException {
        sentence = String.format("select * from tblstatisticsgame");
        return ((con.returnRecord(sentence)).getRowCount());
    }

    public boolean insertStatisticsgame(StatisticsgameModel statisticsgameModel) throws Exception {
        String structure = String.format(
                "<insertstatisticsgame>"
                + "<idgame>" + statisticsgameModel.getIdgame() + "</idgame>"
                + "<movements>" + statisticsgameModel.getMovements() + "</movements>"
                + "<minutes>" + statisticsgameModel.getMinutes() + "</minutes>"
                + "<seconds>" + statisticsgameModel.getSeconds() + "</seconds>"
                + "<score>" + statisticsgameModel.getScore() + "</score>"
                + "<stars>" + statisticsgameModel.getStars() + "</stars>"
                + "<lvl>" + statisticsgameModel.getLvl() + "</lvl>"
                + "<creationdate>" + statisticsgameModel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + statisticsgameModel.getUpdatedate()+ "</updatedate>"
                + "<state>" + statisticsgameModel.getState() + "</state>"
                + "</insertstatisticsgame>");

        String sentency = "Select * from insertstatisticsgame('" + structure + "')";
        return con.modifyBD(sentency);
    }

}
