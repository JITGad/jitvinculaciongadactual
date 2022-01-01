package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.GameController;
import com.jitgad.bjitgad.DAO.GameDAO;
import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
import com.jitgad.bjitgad.Models.GameModel;
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
@Path("game")

public class Gameresource {

    @Context
    private UriInfo context;
    // private GameModel gameModel;
    private GameController gC;
    private AuthorizationController AuC;

    public Gameresource() {
        // gameModel = new GameModel();
        gC = new GameController();
        AuC = new AuthorizationController();
    }

    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame() {
        String responseJson = gC.selectGame();
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
    @Path("/getGameAdmin")
    public Response getGameAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {
        String responseJson = "[]";
        int responseCountingPage = 0;
        int TotalPages = 0;
        //TOKENS
        String Authorization = headers.getHeaderString("Authorization");
        Authorization = Authorization == null ? "" : Authorization;
        System.out.println("Authorization: " + Authorization);
        Object[] Permt = AuC.VToken(Authorization);
        if (Permt[0].equals(true)) {
            responseJson = gC.selectGamepage(page);
            responseCountingPage = gC.CountingPageGame();
            TotalPages = (responseCountingPage / 10) + 1;
            if (!Methods.jsonrecordcount(responseJson)) {
                responseJson = "{\"message\":\"No Records.\",\"CountingPage\": "+responseCountingPage+",\"TotalPages\": "+TotalPages+",\"flag\": true,\"data\":" + responseJson + "}";
            } else {
                responseJson = "{\"message\":\"No Records.\",\"CountingPage\": "+responseCountingPage+",\"TotalPages\": "+TotalPages+",\"flag\": true,\"data\":" + responseJson + "}";
            }
        } else {
            responseJson = "{\"message\":\"" + Permt[1] + "\",\"CountingPage\": "+responseCountingPage+",\"TotalPages\": "+TotalPages+",\"flag\":" + Permt[0] + ",\"data\":" + responseJson + "}";
        }
        return Response.ok(responseJson)
//                .header("CountingPage", responseCountingPage)
//                .header("TotalPages", (responseCountingPage / 10) + 1)
          //      .header("Acccess-Control-Expose-Headers", "TotalPages, CountingPage")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/postGame")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostGame(@Context HttpHeaders headers, String data) {
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        System.out.println("Ingresando PostActivitiesType...");
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
            Object[] responsegC;
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            System.out.println("Authorization: " + Authorization);
            Object[] Permt = AuC.VToken(Authorization);
            if (Permt[0].equals(true)) {
                responsegC = gC.InsertGameC(
                        Methods.JsonToString(Jso.getAsJsonObject(), "idactivitiestype", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "idgametype", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
                if (responsegC[0].equals(true)) {
                    responseJson = "{\"message\":\"" + responsegC[1] + "\",\"flag\":" + responsegC[0] + "}";
                } else {
                    responseJson = "{\"message\":\"" + responsegC[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + responsegC[0] + "}";
                }
            } else {
                responseJson = "{\"message\":\"" + Permt[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + Permt[0] + "}";
            }

        } else {
            responseJson = "{\"message\":\"Missing data.\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + false + "}";
        }

        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

}
