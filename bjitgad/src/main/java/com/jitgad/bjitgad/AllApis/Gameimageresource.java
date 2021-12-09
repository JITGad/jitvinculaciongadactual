
package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.GameimageController;
import com.jitgad.bjitgad.Controller.UserController;
import com.jitgad.bjitgad.DAO.GameimageDAO;
import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.GameimageModel;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author jorge
 */

@Path("gameimage")
public class Gameimageresource {
    
   @Context
   private UriInfo context;
   private GameimageModel GiM;
   private GameimageController giC;
   private UserController uc;

    public Gameimageresource() {
      uc = new UserController();
      GiM = new GameimageModel();
      giC = new GameimageController();
    }
   
   /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameimage(@Context HttpHeaders headers) {
        String responseJson = "";
        //TOKENS
        String user_token = headers.getHeaderString("user_token");
        user_token = user_token == null ? "" : user_token;
        System.out.println("user token: " + user_token);
        String[] clains = Methods.getDataToJwt(user_token);
        Object[] Permt = uc.ValidateToken(clains[0], clains[1], clains[2]);

        if (Permt[0].equals(true)) {
            responseJson = giC.selectGameimage();
        } else {
            responseJson = "{\"message\":\"" + Permt[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + Permt[0] + "}";
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with, Access-Control-Allow-Origin")
                .build();
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/postGameimage")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postGameimage(@Context HttpHeaders headers, String data) {
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        System.out.println("Ingresando PostActivitiesType...");
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
            Object[] responsegiC;
            //TOKENS
            String user_token = headers.getHeaderString("user_token");
            user_token = user_token == null ? "" : user_token;
            System.out.println("user token: " + user_token);
            String[] clains = Methods.getDataToJwt(user_token);
            Object[] Permt = uc.ValidateToken(clains[0], clains[1], clains[2]);
            if (Permt[0].equals(true)) {
                responsegiC = giC.InsertGameimageC(
                        Methods.JsonToString(Jso.getAsJsonObject(), "idgame", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "idcolortype", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "paragraph", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "audio_parag", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "video_parag", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));

                if (responsegiC[0].equals(true)) {
                    responseJson = "{\"message\":\"" + responsegiC[1] + "\",\"flag\":" + responsegiC[0] + "}";
                } else {
                    responseJson = "{\"message\":\"" + responsegiC[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + responsegiC[0] + "}";
                }
            } else {
                responseJson = "{\"message\":\"" + Permt[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + Permt[0] + "}";
            }

        } else {
            responseJson = "{\"message\":\"Missing data.\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + false + "}";
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
    
    
           
}
