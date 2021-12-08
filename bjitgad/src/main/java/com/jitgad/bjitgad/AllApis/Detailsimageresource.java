package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.DetailsimageController;
import com.jitgad.bjitgad.Controller.UserController;
import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.DetailsimageModel;
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
@Path("detailsimage")
public class Detailsimageresource {

    @Context
    private UriInfo context;
    private DetailsimageModel diM;
    private DetailsimageController diC;
    private UserController uc;
    

    public Detailsimageresource() {
        uc = new UserController();
        diM = new DetailsimageModel();
        diC = new DetailsimageController();
    }
    
    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetailsimage(@Context HttpHeaders headers) {

        String responseJson = "";
        //TOKENS
        String user_token = headers.getHeaderString("user_token");
        user_token = user_token == null ? "" : user_token;
        System.out.println("user token: " + user_token);
        String[] clains = Methods.getDataToJwt(user_token);
        Object[] Permt = uc.ValidateToken(clains[0], clains[1], clains[2]);

        if (Permt[0].equals(true)) {
            responseJson = diC.selectDetailsimage();
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
    @Path("/postDetailsimage")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostDetailsimage(@Context HttpHeaders headers, String data) {
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        System.out.println("Ingresando PostDetailsimage...");
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
            Object[] responseDiC;
            //TOKENS
            String user_token = headers.getHeaderString("user_token");
            user_token = user_token == null ? "" : user_token;
            System.out.println("user token: " + user_token);
            String[] clains = Methods.getDataToJwt(user_token);
            Object[] Permt = uc.ValidateToken(clains[0], clains[1], clains[2]);
            if (Permt[0].equals(true)) {
                responseDiC = diC.InsertDetailsimageC(
                        Methods.JsonToString(Jso.getAsJsonObject(), "idgameimage", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "clipping_type_", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));

                if (responseDiC[0].equals(true)) {
                    responseJson = "{\"message\":\"" + responseDiC[1] + "\",\"flag\":" + responseDiC[0] + "}";
                } else {
                    responseJson = "{\"message\":\"" + responseDiC[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + responseDiC[0] + "}";
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
