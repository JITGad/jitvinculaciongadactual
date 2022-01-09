package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.DetailsimageController;
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
