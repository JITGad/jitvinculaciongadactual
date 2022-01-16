package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.DetailsimageController;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.DetailsimageModel;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

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
    private AuthorizationController AuC;
    

    public Detailsimageresource() {
        AuC = new AuthorizationController();
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
    public Response getDetailsimage() {
        String responseJson = diC.selectDetailsimage();
        return Response.ok(responseJson)
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
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            System.out.println("Authorization: " + Authorization);
            Object[] Permt = AuC.VToken(Authorization);
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
                    responseJson = "{\"message\":\"" + responseDiC[1] + "\",\"nameApplication\":\"" + Configuration.nameApplication + "\",\"flag\":" + responseDiC[0] + "}";
                }
            } else {
                responseJson = "{\"message\":\"" + Permt[1] + "\",\"nameApplication\":\"" + Configuration.nameApplication + "\",\"flag\":" + Permt[0] + "}";
            }

        } else {
            responseJson = "{\"message\":\"Missing data.\",\"nameApplication\":\"" + Configuration.nameApplication + "\",\"flag\":" + false + "}";
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

}
