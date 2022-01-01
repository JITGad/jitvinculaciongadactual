package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.GametypeController;
import com.jitgad.bjitgad.DAO.GametypeDAO;
import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
import com.jitgad.bjitgad.Models.GametypeModel;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author jorge
 */
@Path("gametype")
public class Gametyperesource {

    @Context
    private UriInfo context;
    private AuthorizationController AuC;
    private GametypeController gtC;

    public Gametyperesource() {
        gtC = new GametypeController();
        AuC = new AuthorizationController();
    }

    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getgametype() {
        String responseJson = gtC.selectGametype();
        if (!Methods.jsonrecordcount(responseJson)) {
            responseJson = "{\"message\":\"No Records.\",\"flag\": true,\"data\":" + responseJson + "}";
        } else {
            responseJson = "{\"message\":\"Records returned successfully.\",\"flag\": true,\"data\":" + responseJson + "}";
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getgametypeAdmin")
    public Response getgametypeAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {
        String responseJson = "[]";
        int responseCountingPage = 0;
        //TOKENS
        String Authorization = headers.getHeaderString("Authorization");
        Authorization = Authorization == null ? "" : Authorization;
        System.out.println("Authorization: " + Authorization);
        Object[] Permt = AuC.VToken(Authorization);
        if (Permt[0].equals(true)) {
            responseJson = gtC.selectGametypepage(page);
            if (!Methods.jsonrecordcount(responseJson)) {
                responseJson = "{\"message\":\"No Records.\",\"flag\": true,\"data\":" + responseJson + "}";
            } else {
                responseJson = "{\"message\":\"Records returned successfully.\",\"flag\": true,\"data\":" + responseJson + "}";
            }
            responseCountingPage = gtC.CountingPageGametype();
        } else {
            responseJson = "{\"message\":\"" + Permt[1] + "\",\"data\":\"" + responseJson + "\",\"flag\":" + Permt[0] + "}";
        }
        return Response.ok(responseJson)
                .header("CountingPage", responseCountingPage)
                .header("TotalPages", (responseCountingPage / 10) + 1)
                .header("Acccess-Control-Expose-Headers", "TotalPages, CountingPage")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/insertgametype")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertactivitiestype(@Context HttpHeaders headers, String data) {
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        System.out.println("Ingresando PostActivitiesType...");
        //  boolean insertgametype = false;
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
            Object[] responsegtC;
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            System.out.println("Authorization: " + Authorization);
            Object[] Permt = AuC.VToken(Authorization);
            if (Permt[0].equals(true)) {
                responsegtC = gtC.InsertGametypeC(Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "audio_instructions", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));

                if (responsegtC[0].equals(true)) {
                    responseJson = "{\"message\":\"" + responsegtC[1] + "\",\"flag\":" + responsegtC[0] + "}";
                } else {
                    responseJson = "{\"message\":\"" + responsegtC[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + responsegtC[0] + "}";
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
