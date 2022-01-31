
package com.jitgad.bjitgad.AllApis;

import com.jitgad.bjitgad.Controller.GameimageController;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author jorge
 */

@Path("gameimage")
public class Gameimageresource {
    
   private final GameimageController giC;

    public Gameimageresource() {
      giC = new GameimageController();
    }
   
   /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameimage() {
        String responseJson = giC.selectGameimage();
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with, Access-Control-Allow-Origin")
                .build();
    }
    
//    @Produces(MediaType.APPLICATION_JSON)
//    @POST
//    @Path("/postGameimage")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response postGameimage(@Context HttpHeaders headers, String data) {
//        String responseJson = "{\"status\":\"poken:" + data + "\"}";
//        System.out.println("Ingresando PostActivitiesType...");
//        JsonObject Jso = Methods.stringToJSON(data);
//        if (Jso.size() > 0) {
//            Object[] responsegiC;
//            //TOKENS
//            String Authorization = headers.getHeaderString("Authorization");
//            Authorization = Authorization == null ? "" : Authorization;
//            System.out.println("Authorization: " + Authorization);
//            Object[] Permt = AuC.VToken(Authorization);
//            if (Permt[0].equals(true)) {
//                responsegiC = giC.InsertGameimageC(
//                        Methods.JsonToString(Jso.getAsJsonObject(), "idgame", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "idcolortype", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "paragraph", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "audio_parag", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "video_parag", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""),
//                        Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
//
//                if (responsegiC[0].equals(true)) {
//                    responseJson = "{\"message\":\"" + responsegiC[1] + "\",\"flag\":" + responsegiC[0] + "}";
//                } else {
//                    responseJson = "{\"message\":\"" + responsegiC[1] + "\",\"nameApplication\":\"" + Configuration.nameApplication + "\",\"flag\":" + responsegiC[0] + "}";
//                }
//            } else {
//                responseJson = "{\"message\":\"" + Permt[1] + "\",\"nameApplication\":\"" + Configuration.nameApplication + "\",\"flag\":" + Permt[0] + "}";
//            }
//
//        } else {
//            responseJson = "{\"message\":\"Missing data.\",\"nameApplication\":\"" + Configuration.nameApplication + "\",\"flag\":" + false + "}";
//        }
//        return Response.ok(responseJson)
//                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
//                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
//                .build();
//    }
    
    
           
}
