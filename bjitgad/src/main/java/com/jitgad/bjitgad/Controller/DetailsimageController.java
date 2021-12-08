
package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.DetailsimageDAO;
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.Models.DetailsimageModel;

/**
 *
 * @author jorge
 */
public class DetailsimageController {
    
    private Conection conex;
    private DetailsimageDAO dio;
    private DetailsimageModel dim;

    public DetailsimageController() {
        conex = new Conection();
        dio = new DetailsimageDAO();
        dim = new DetailsimageModel();
    }
    
    public String selectDetailsimage(){
        return dio.selectDetailsimage();
    }
    
    public Object[] InsertDetailsimageC(String idgameimage, String clipping_type_,
            String image, String creationdate, String updatedate, String state) {
        String message = "";
        boolean status = false;
        dim.setIdgameimage(idgameimage);
        dim.setClipping_type(clipping_type_);
        dim.setImage(image);
        dim.setCreationdate(creationdate);
        dim.setUpdatedate(updatedate);
        dim.setState(state);
        
        if(dio.insertDetailsimage(dim)){
            message = "The Details image type was inserted.";
            status = true;
        }   else {
                message = "The Details image type was not inserted";
                status = false;
            }

        return new Object[]{status, message};
    }
}