package com.jitgad.bjitgad.DAO;

import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.DetailsimageModel;

/**
 *
 * @author jorge
 */
public class DetailsimageDAO {

    Conection con;
    String sentence;

    public DetailsimageDAO() {
        con = new Conection();
    }

    public String selectDetailsimage() {
        sentence = "select * from tbldetailsimage";
        String json = con.getRecordsInJson(sentence);
        return json;
    }

    public boolean insertDetailsimage(DetailsimageModel detailsimageModel) {
        String structure = String.format(
                "<detailsimag>"
                + "<idgameimage>" + detailsimageModel.getIdgameimage() + "</idgameimage>"
                + "<clipping_type_>" + detailsimageModel.getClipping_type() + "</clipping_type_>"
                + "<image>" + detailsimageModel.getImage() + "</image>"
                + "<creationdate>" + detailsimageModel.getCreationdate() + "</creationdate>"
                + "<updatedate>" + detailsimageModel.getUpdatedate() + "</updatedate>"
                + "<state>" + detailsimageModel.getState() + "</state>"
                + "</detailsimag>");

        String sentency = "Select * from insertdetailsimag('" + structure + "')";
        // System.out.println(structure);
        return con.modifyBD(sentency);
    }

}
