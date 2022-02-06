package com.jitgad.bjitgad.Controller;

import com.jitgad.bjitgad.DAO.DetailsimageDAO;
import com.jitgad.bjitgad.Models.DetailsimageModel;
import java.sql.SQLException;

/**
 *
 * @author jorge
 */
public class DetailsimageController {

    private final DetailsimageDAO dio;
    private final DetailsimageModel dim;

    public DetailsimageController() {
        dio = new DetailsimageDAO();
        dim = new DetailsimageModel();
    }

    public String selectDetailsimage() throws SQLException {
        return dio.selectDetailsimage();
    }

    public Object[] InsertDetailsimageC(String idgameimage, String clipping_type_,
            String image, String creationdate, String updatedate, String state) throws Exception {
        String message = "The Details image type was not inserted";
        boolean status = false;
        dim.setIdgameimage(idgameimage);
        dim.setClipping_type(clipping_type_);
        dim.setImage(image);
        dim.setCreationdate(creationdate);
        dim.setUpdatedate(updatedate);
        dim.setState(state);

        if (dio.insertDetailsimage(dim)) {
            message = "The Details image type was inserted.";
            status = true;
        } 
        return new Object[]{status, message};
    }
}
