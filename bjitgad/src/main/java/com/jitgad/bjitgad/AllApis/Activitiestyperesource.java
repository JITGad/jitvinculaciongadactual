package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.ActivitiestypeController;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.UserController;
import com.jitgad.bjitgad.DAO.ActivitiestypeDAO;
import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
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
@Path("activitiestype")
public class Activitiestyperesource {

    @Context
    private UriInfo context;
    //  private ActivitiestypeModel atM;
    private ActivitiestypeController atC;
    private AuthorizationController AuC;

    public Activitiestyperesource() {
        atC = new ActivitiestypeController();
        // atM = new ActivitiestypeModel();
        AuC = new AuthorizationController();
    }

    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivitiestype() {
        String responseJson = atC.selectActivitiestype();
        if (!Methods.jsonrecordcount(responseJson)) {
            responseJson = "{\"message\":\"No Records.\",\"flag\": true,\"data\":" + responseJson + "}";
        } else {
            responseJson = "{\"message\":\"Records returned successfully.\",\"flag\": true,\"data\":" + responseJson + "}";
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with, Access-Control-Allow-Origin")
                .build();
    }

    // recibe token - administración
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getActivitiestypeAdmin")
    public Response getActivitiestypeAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {
        String responseJson = "[]";
        int responseCountingPage = 0;
        //TOKENS
        String Authorization = headers.getHeaderString("Authorization");
        Authorization = Authorization == null ? "" : Authorization;
        System.out.println("Authorization: " + Authorization);
        Object[] Permt = AuC.VToken(Authorization);
        if (Permt[0].equals(true)) {
            responseJson = atC.selectActivitiestypepage(page);
            if (!Methods.jsonrecordcount(responseJson)) {
                responseJson = "{\"message\":\"No Records.\",\"flag\": true,\"data\":" + responseJson + "}";
            } else {
                responseJson = "{\"message\":\"Records returned successfully.\",\"flag\": true,\"data\":" + responseJson + "}";
            }
            responseCountingPage = atC.CountingPageActivitiesType();

        } else {
            responseJson = "{\"message\":\"" + Permt[1] + "\",\"data\":\"" + responseJson + "\",\"flag\":" + Permt[0] + "}";
        }
        return Response.ok(responseJson)
                .header("CountingPage", responseCountingPage)
                .header("TotalPages", (responseCountingPage / 10) + 1)
                .header("Acccess-Control-Expose-Headers", "TotalPages, CountingPage")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with, Access-Control-Allow-Origin")
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/gamesbyactivities")
    @Consumes(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getgamesbyactivities(@QueryParam("activityid") String activityid) {
        String responseJson = atC.selectgamesbyactivities(activityid);;
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/postActivitiesType")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostActivitiesType(@Context HttpHeaders headers, String data) {
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        System.out.println("Ingresando PostActivitiesType...");
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
            Object[] responseatC;
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            System.out.println("Authorization: " + Authorization);
            Object[] Permt = AuC.VToken(Authorization);
            if (Permt[0].equals(true)) {
                responseatC = atC.InsertActivitiesTypeC(
                        Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
                if (responseatC[0].equals(true)) {
                    responseJson = "{\"message\":\"" + responseatC[1] + "\",\"flag\":" + responseatC[0] + "}";
                } else {
                    responseJson = "{\"message\":\"" + responseatC[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + responseatC[0] + "}";
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
