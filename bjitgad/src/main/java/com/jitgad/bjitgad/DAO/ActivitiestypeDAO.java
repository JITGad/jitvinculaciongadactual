package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class ActivitiestypeDAO {

    private final ConectionPool con;

    public ActivitiestypeDAO() {
        con = ConectionPoolDataSource.getConnection();
    }

    public ArrayList<ActivitiestypeModel> selectActivitiestype() throws Exception {

        String sentence = "select DISTINCT "
                + "tblactivitiestype.idactivitiestype, "
                + "tblactivitiestype.name, "
                + "tblactivitiestype.image, "
                + "tblactivitiestype.creationdate, "
                + "tblactivitiestype.updatedate, "
                + "tblactivitiestype.state "
                + "from tblactivitiestype "
                + "inner join tblgame on tblactivitiestype.idactivitiestype = tblgame.idactivitiestype "
                + "where tblactivitiestype.state = true "
                + "order by tblactivitiestype.idactivitiestype";
        ArrayList<ActivitiestypeModel> datos = con.getObjectDB(sentence, ActivitiestypeModel.class, 1);
        return datos;
    }

    public ArrayList<ClaveValorModel> selectactivitiestypeclavevalor() throws Exception {
        String sentence = "select "
                + "idactivitiestype as id, "
                + "name as name "
                + "from tblactivitiestype "
                + "order by idactivitiestype";
        ArrayList<ClaveValorModel> datos = con.getObjectDB(sentence, ClaveValorModel.class, 1);

        return datos;
    }

    public ArrayList<ActivitiestypeModel> selectActivitiestypepage(int page) throws Exception {
        String sentence = "select * "
                + "from tblactivitiestype "
                + "order by idactivitiestype asc limit 10 offset " + (page * 10 - 10);
        ArrayList<ActivitiestypeModel> datos = con.getObjectDB(sentence, ActivitiestypeModel.class, 1);
        return datos;
    }

    public int CountingPageActivitiestype() throws SQLException {
        String sentence = String.format("select "
                + "idactivitiestype as id, "
                + "name as tema, "
                + "image as urlimagen "
                + "from tblactivitiestype");
        return ((con.returnRecord(sentence)).getRowCount());
    }

    public String selectactivitiesbyid(int idactivity) throws Exception {
        String sentence = "select * "
                + "from tblactivitiestype "
                + "where idactivitiestype =" + idactivity;
        ArrayList<ActivitiestypeModel> datos = con.getObjectDB(sentence, ActivitiestypeModel.class, 1);
        
        if (datos.size() > 0) {
            return Methods.objectToJsonString(datos.get(0));
        } else {
            return "{}";
        }
    }

    public boolean insertActividadestype(ActivitiestypeModel activitiestypemodel) throws SQLException {
        String structure = String.format(
                "<actividadestype>"
                + "<name>" + activitiestypemodel.getName() + "</name>"
                + "<image>" + activitiestypemodel.getImage() + "</image>"
                + "<creationdate>" + activitiestypemodel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + activitiestypemodel.getUpdatedate() + "</updatedate>"
                + "<state>" + activitiestypemodel.getState() + "</state>"
                + "</actividadestype>");

        String sentency = "Select * from insertActividadestype('" + structure + "')";
        return con.modifyBD(sentency);
    }

    public boolean updateActividadestype(ActivitiestypeModel activitiestypemodel) throws SQLException {
        String structure = String.format(
                "<actividadestype>"
                + "<idactivitiestype>" + activitiestypemodel.getIdactivitiestype() + "</idactivitiestype>"
                + "<name>" + activitiestypemodel.getName() + "</name>"
                + "<image>" + activitiestypemodel.getImage() + "</image>"
                + "<updatedate>" + activitiestypemodel.getUpdatedate() + "</updatedate>"
                + "<state>" + activitiestypemodel.getState() + "</state>"
                + "</actividadestype>");

        String sentency = "Select * from updateActividadestype('" + structure + "')";
        return con.modifyBD(sentency);
    }

    public boolean DeleteActividadestype(ActivitiestypeModel activitiestypemodel) throws SQLException {
        String structure = String.format(
                "<actividadestype>"
                + "<idactivitiestype>" + activitiestypemodel.getIdactivitiestype() + "</idactivitiestype>"
                + "</actividadestype>");

        String sentency = "Select * from deleteActividadestype('" + structure + "')";
        return con.modifyBD(sentency);
    }

}
