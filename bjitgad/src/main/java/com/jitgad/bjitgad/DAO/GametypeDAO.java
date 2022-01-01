package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.GametypeModel;

/**
 *
 * @author jorge
 */
public class GametypeDAO {

    Conection con;
    String sentence;

    public GametypeDAO() {
        con = new Conection();
    }

    public String selectGametype() {
        sentence = "select * from tblgametype";
        String json = con.getRecordsInJson(sentence);
        return json;
    }
    
    public String selectGametypepage(int page){
    sentence ="select * from tblgametype order by idgametype asc limit 10 offset "+ (page * 10 - 10);
        String json = con.getRecordsInJson(sentence);
        return json;
    }
    
    public int CountingPageGametype(){
      sentence = String.format("select * from tblgametype");
      return  ((con.returnRecord(sentence)).getRowCount());
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
                + "</gametype>");

        String sentency = "Select * from insertgametype('" + structure + "')";
        // System.out.println(structure);
        return con.modifyBD(sentency);
    }
}
