package com.jitgad.bjitgad.AllApis;

import com.jitgad.bjitgad.Controller.DetailsimageController;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
@Path("detailsimage")
public class Detailsimageresource {

    private final DetailsimageController diC;
    

    public Detailsimageresource() {
        diC = new DetailsimageController();
    }
    
    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetailsimage() {
        String responseJson = "";
        try {
            responseJson = diC.selectDetailsimage();
        } catch (SQLException ex) {
            Logger.getLogger(Detailsimageresource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with, Access-Control-Allow-Origin")
                .build();
    }
    
//    @Produces(MediaType.APPLICATION_JSON)
//    @POST
//    @Path("/postDetailsimage")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response PostDetailsimage(@Context HttpHeaders headers, String data) {
//        String responseJson = "{\"status\":\"poken:" + data + "\"}";
//        System.out.println("Ingresando PostDetailsimage...");
//        JsonObject Jso = Methods.stringToJSON(data);
//        if (Jso.size() > 0) {
//            Object[] responseDiC;
//            //TOKENS
//            String Authorization = headers.getHeaderString("Authorization");
//            Authorization = Authorization == null ? "" : Authorization;
//            System.out.println("Authorization: " + Authorization);
//            Object[] Permt = AuC.VToken(Authorization);
//            if (Permt[0].equals(true)) {
//                responseDiC = diC.InsertDetailsimageC(
//                        Methods.JsonToString(Jso.getAsJsonObject(), "idgameimage", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "clipping_type_", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
//
//                if (responseDiC[0].equals(true)) {
//                    responseJson = "{\"message\":\"" + responseDiC[1] + "\",\"flag\":" + responseDiC[0] + "}";
//                } else {
//                    responseJson = "{\"message\":\"" + responseDiC[1] + "\",\"nameApplication\":\"" + Configuration.nameApplication + "\",\"flag\":" + responseDiC[0] + "}";
//                }
//            } else {
//                responseJson = "{\"message\":\"" + Permt[1] + "\",\"nameApplication\":\"" + Configuration.nameApplication + "\",\"flag\":" + Permt[0] + "}";
//            }
//
//        } else {
//            responseJson = "{\"message\":\"Missing data.\",\"nameApplication\":\"" + Configuration.nameApplication + "\",\"flag\":" + false + "}";
//        }
//        return Response.ok(responseJson)
//                .header("Access-Control-Allow-Origin", "*")
//                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
//                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
//                .build();
//    }

}
