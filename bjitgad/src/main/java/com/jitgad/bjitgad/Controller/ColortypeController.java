package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.ColortypeDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.ColortypeModel;

/**
 *
 * @author jorge
 */
public class ColortypeController {

    private Conection conex;
    private ColortypeModel ctModel;
    private ColortypeDAO ctypDAO;

    public ColortypeController() {
        conex = new Conection();
        ctModel = new ColortypeModel();
        ctypDAO = new ColortypeDAO();
    }

    public String selectColortypepage(int page) {
        return ctypDAO.selectColortypepage(page);
    }

    public int CountingPageColortype() {
        return ctypDAO.CountingPageColortype();
    }

    public String selectColortypebyid(int id) {
        return ctypDAO.selectColortypebyid(id);
    }

    public Object[] InsertColortype(String name, String rgb, String html,
             boolean state) {
        String message = "";
        boolean status = false;
        ctModel.setName(name);
        ctModel.setRgb(rgb);
        ctModel.setHtml(html);
        ctModel.setCreationdate("NOW()");
        ctModel.setUpdatedate("NOW()");
        ctModel.setState(state);
        
        if(ctypDAO.insertColortype(ctModel)){
            message = "Registros actualizados correctamente";
            status = true;
        } else {
            message = "Registros no insertados, datos erróneos para enviar a la base de datos!";
            status = false;
        }
        return new Object[]{status, message};
    }

    public Object[] UpdateColortype(int idcolortype,String name, String rgb, String html,
             boolean state) {
        String message = "";
        boolean status = false;
        ctModel.setIdcolortype(idcolortype);
        ctModel.setName(name);
        ctModel.setRgb(rgb);
        ctModel.setHtml(html);
        ctModel.setUpdatedate("NOW()");
        ctModel.setState(state);
        
        if(ctypDAO.updatecolortype(ctModel)){
            message = "Registros actualizados correctamente";
            status = true;
        } else {
            message = "Los registros no fueron actualizados, datos erróneos para enviar a la base de datos!";
            status = false;
        }
        return new Object[]{status, message};
    }

    public Object[] DeleteColortype(int idcolortype) {
        String message = "";
        boolean status = false;
        ctModel.setIdcolortype(idcolortype);
        
        if(ctypDAO.deletecolortype(ctModel)){
            message = "Registro eliminado correctamente";
            status = true;
        } else {
            message = "El registro no fué eliminado, datos erróneos para enviar a la base de datos!";
            status = false;
        }
        return new Object[]{status, message};
    }


}
