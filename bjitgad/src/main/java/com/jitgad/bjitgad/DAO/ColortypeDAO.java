package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.ColortypeModel;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class ColortypeDAO {

    Conection con;
    String sentence;

    public ColortypeDAO() {
        con = new Conection();
    }

    public ArrayList<ColortypeModel> selectColortypepage(int page) {
        sentence = "select * from tblcolortype order by idcolortype asc limit 10 offset " + (page * 10 - 10);
        ArrayList<ColortypeModel> datos = con.getObjectDB(sentence, ColortypeModel.class, 1);
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

    public boolean insertColortype(ColortypeModel colortypeModel) {
        String structure = String.format(
                "<colortype>"
                + "<name>" + colortypeModel.getName()+ "</name>"
                + "<rgb>" + colortypeModel.getRgb() + "</rgb>"
                + "<html>" + colortypeModel.getHtml() + "</html>"
                + "<creationdate>" + colortypeModel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + colortypeModel.getUpdatedate() + "</updatedate>"
                + "<state>" + colortypeModel.getState() + "</state>"
                + "</colortype>");

        String sentency = "Select * from insertcolortype('" + structure + "')";
        return con.modifyBD(sentency);
    }
    public boolean updatecolortype(ColortypeModel colortypeModel) {
        String structure = String.format(
                "<colortype>"
                + "<idcolortype>" + colortypeModel.getIdcolortype()+ "</idcolortype>"        
                + "<name>" + colortypeModel.getName()+ "</name>"
                + "<rgb>" + colortypeModel.getRgb() + "</rgb>"
                + "<html>" + colortypeModel.getHtml() + "</html>"
                + "<updatedate>" + colortypeModel.getUpdatedate() + "</updatedate>"
                + "<state>" + colortypeModel.getState() + "</state>"
                + "</colortype>");
        
        String sentency = "Select * from updatecolortype('" + structure + "')";
        System.out.println(structure);
        return con.modifyBD(sentency);
    }
    
    public boolean deletecolortype(ColortypeModel colortypeModel) {
        String structure = String.format(
                "<colortype>"
                + "<idcolortype>" + colortypeModel.getIdcolortype()+ "</idcolortype>"        
                + "</colortype>");
        String sentency = "Select * from deletecolortype('" + structure + "')";
        return con.modifyBD(sentency);
    }
    
    
}
