
package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.ColortypeController;
import com.jitgad.bjitgad.Controller.UserController;
import com.jitgad.bjitgad.DAO.ColortypeDAO;
import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ColortypeModel;
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

@Path("colortype")
public class Colortyperesource {
    
   @Context
   private UriInfo context;
   private ColortypeModel ctypeModel;
   private ColortypeController ctypeC;
   private UserController uc;
           

    public Colortyperesource() {
       ctypeC = new ColortypeController();
       uc = new UserController();
       ctypeModel = new ColortypeModel();
    }
    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getColortype(@Context HttpHeaders headers) {
        //TODO return proper representation object
        String responseJson = "";
        String user_token = headers.getHeaderString("user_token");
        user_token = user_token == null ? "" : user_token;
        System.out.println("user token: " + user_token);
        String[] clains = Methods.getDataToJwt(user_token);
        Object[] Permt = uc.ValidateToken(clains[0], clains[1], clains[2]);

        if (Permt[0].equals(true)) {
            responseJson = ctypeC.selectColortype();
        } else {
            responseJson = "{\"message\":\"" + Permt[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + Permt[0] + "}";
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/postColortype")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostColortype(@Context HttpHeaders headers, String data) {
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        System.out.println("Ingresando PostColortype...");
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
            Object[] responsectype;
            //TOKENS
            String user_token = headers.getHeaderString("user_token");
            user_token = user_token == null ? "" : user_token;
            System.out.println("user token: " + user_token);
            String[] clains = Methods.getDataToJwt(user_token);
            Object[] Permt = uc.ValidateToken(clains[0], clains[1], clains[2]);
            if (Permt[0].equals(true)) {
                responsectype = ctypeC.InsertColortype(
                        Methods.JsonToString(Jso.getAsJsonObject(), "color", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));

                if (responsectype[0].equals(true)) {
                    responseJson = "{\"message\":\"" + responsectype[1] + "\",\"flag\":" + responsectype[0] + "}";
                } else {
                    responseJson = "{\"message\":\"" + responsectype[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + responsectype[0] + "}";
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
