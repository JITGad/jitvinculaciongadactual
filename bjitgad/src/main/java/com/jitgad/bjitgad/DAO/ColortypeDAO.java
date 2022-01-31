package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import com.jitgad.bjitgad.Models.ColortypeModel;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ClaveValorColorModel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class ColortypeDAO {

    private final ConectionPool con;
    String sentence;

    public ColortypeDAO() {
        con = ConectionPoolDataSource.getConnection();
    }
    
    public ArrayList<ColortypeModel> selectColortypepage(int page) {
        sentence = "select * from tblcolortype order by idcolortype asc limit 10 offset " + (page * 10 - 10);
        ArrayList<ColortypeModel> datos = con.getObjectDB(sentence, ColortypeModel.class, 1);
        return datos;
    }
    
    public ArrayList<ClaveValorColorModel> selectColortypecv() {
        sentence = "select idcolortype as id, name as text, html as value from tblcolortype order by idcolortype";
        ArrayList<ClaveValorColorModel> datos = con.getObjectDB(sentence, ClaveValorColorModel.class, 1);
        
        return datos;
    }

    public int CountingPageColortype() {
        sentence = String.format("select * from tblcolortype");
        return ((con.returnRecord(sentence)).getRowCount());
    }

    public String selectColortypebyid(int idcolortype) {
        sentence = "select * from tblcolortype where idcolortype =" + idcolortype;
        ArrayList<ColortypeModel> datos = con.getObjectDB(sentence, ColortypeModel.class, 1);
        if (datos.size() > 0) {
            return Methods.objectToJsonString(datos.get(0));
        } else {
            return "{}";
        }
    }

    public boolean insertColortype(ColortypeModel colortypeModel) throws SQLException {
        String structure = String.format(
                "<colortype>"
                + "<name>" + colortypeModel.getName()+ "</name>"
                + "<html>" + colortypeModel.getHtml() + "</html>"
                + "<creationdate>" + colortypeModel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + colortypeModel.getUpdatedate() + "</updatedate>"
                + "<state>" + colortypeModel.getState() + "</state>"
                + "</colortype>");

        String sentency = "Select * from insertcolortype('" + structure + "')";
        return con.modifyBD(sentency);
    }
    
    public boolean updatecolortype(ColortypeModel colortypeModel) throws SQLException {
        String structure = String.format(
                "<colortype>"
                + "<idcolortype>" + colortypeModel.getIdcolortype()+ "</idcolortype>"        
                + "<name>" + colortypeModel.getName()+ "</name>"
                + "<html>" + colortypeModel.getHtml() + "</html>"
                + "<updatedate>" + colortypeModel.getUpdatedate() + "</updatedate>"
                + "<state>" + colortypeModel.getState() + "</state>"
                + "</colortype>");
        
        String sentency = "Select * from updatecolortype('" + structure + "')";
        System.out.println(structure);
        return con.modifyBD(sentency);
    }
    
    public boolean deletecolortype(ColortypeModel colortypeModel) throws SQLException {
        String structure = String.format(
                "<colortype>"
                + "<idcolortype>" + colortypeModel.getIdcolortype()+ "</idcolortype>"        
                + "</colortype>");
        String sentency = "Select * from deletecolortype('" + structure + "')";
        return con.modifyBD(sentency);
    }
    
    
}
