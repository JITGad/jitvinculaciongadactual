package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.GametypeDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.GametypeModel;

/**
 *
 * @author jorge
 */
public class GametypeController {

    private Conection conex;
    private GametypeDAO gtD;
    private GametypeModel gtM;

    public GametypeController() {
        conex = new Conection();
        gtD = new GametypeDAO();
        gtM = new GametypeModel();
    }
    
    public String selectGametype(){
        return gtD.selectGametype();
    }
    
//    public String selectGametypepage(){
//       // return gtD.selectGametype();
//    }
    
}
