package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.ConectionPool;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.GametypeModel;
import com.jitgad.bjitgad.Models.UserModel;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
public class GametypeDAO {

    ConectionPool con;
    String sentence;

    public GametypeDAO() {
        con = ConectionPoolDataSource.getConnection();
    }

//    public String selectGametype() {
//        sentence = "select * from tblgametype";
//        String json = con.getRecordsInJson(sentence);
//        return json;
//    }
    public String selectGametypepage(int page) {
        sentence = "select * from tblgametype order by idgametype asc limit 10 offset " + (page * 10 - 10);
        ArrayList<GametypeModel> datos = con.getObjectDB(sentence, GametypeModel.class, 1);
        return Methods.objectToJsonString(datos);
    }
    
    public String selectGametypebyid(int id) {
        sentence = "select * from tblgametype where idgametype="+id;
        ArrayList<GametypeModel> datos = con.getObjectDB(sentence, GametypeModel.class, 1);
        if (datos.size() > 0) {
            return Methods.objectToJsonString(datos.get(0));
        } else {
            return "{}";
        }
    }

    public int CountingPageGametype() {
        sentence = String.format("select * from tblgametype");
        return ((con.returnRecord(sentence)).getRowCount());
    }

    public boolean insertGametype(GametypeModel gametypeModel) {
        String structure = String.format(
                "<gametype>"
                + "<name>" + gametypeModel.getName() + "</name>"
                + "<image>" + gametypeModel.getImage() + "</image>"
                + "<audio_instructions>" + gametypeModel.getAudio_instructions() + "</audio_instructions>"
                + "<creationdate>" + gametypeModel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + gametypeModel.getUpdatedate() + "</updatedate>"
                + "<state>" + gametypeModel.getState() + "</state>"
                + "<shortname>" + gametypeModel.getShortname() + "</shortname>"
                + "</gametype>");

        String sentency = "Select * from insertgametype('" + structure + "')";
        return con.modifyBD(sentency);
    }

    public boolean updateGametype(GametypeModel gametypeModel) {
        String structure = String.format(
                "<gametype>"
                + "<idgametype>" + gametypeModel.getIdgametype() + "</idgametype>"
                + "<name>" + gametypeModel.getName() + "</name>"
                + "<image>" + gametypeModel.getImage() + "</image>"
                + "<audio_instructions>" + gametypeModel.getAudio_instructions() + "</audio_instructions>"
                + "<updatedate>" + gametypeModel.getUpdatedate() + "</updatedate>"
                + "<state>" + gametypeModel.getState() + "</state>"
                + "<shortname>" + gametypeModel.getShortname() + "</shortname>"
                + "</gametype>");

        String sentency = "Select * from updategametype('" + structure + "')";
        return con.modifyBD(sentency);
    }


    public boolean DeleteGametype(GametypeModel gametypeModel) {
        String structure = String.format(
                "<gametype>"
                + "<idgametype>" + gametypeModel.getIdgametype() + "</idgametype>"
                + "</gametype>");

        String sentency = "Select * from deletegametype('" + structure + "')";
        return con.modifyBD(sentency);
    }


}
