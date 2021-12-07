package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.ActivitiestypeController;
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
    private ActivitiestypeDAO activitiestypeDAO;
    private ActivitiestypeModel atM;
    private ActivitiestypeController atC;

    public Activitiestyperesource() {
        activitiestypeDAO = new ActivitiestypeDAO();
        atC = new ActivitiestypeController();
        atM = new ActivitiestypeModel();
    }

    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivitiestype(@Context HttpHeaders headers) {

        String user_token = headers.getHeaderString("user_token");
        user_token = user_token == null ? "" : user_token;
        System.out.println("user token: " + user_token);

        String[] clains = Methods.getDataToJwt(user_token);
        System.out.println("CLAINS 0" + clains[0]);
        System.out.println("CLAINS 1" + clains[1]);
        
        String responseJson = activitiestypeDAO.selectActivitiestype();
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
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
        //TODO return proper representation object
        String responseJson = activitiestypeDAO.selectgamesbyactivities(activityid);
        System.out.println(activityid);
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
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

            String user_token = headers.getHeaderString("user_token");
            user_token = user_token == null ? "" : user_token;
            System.out.println("user token: " + user_token);

            String[] clains = Methods.getDataToJwt(user_token);

            //   String[] permit = Methods.validatePermit(clains[0],clains[1],4);
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
            responseJson = "{\"message\":\"Missing data.\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + false + "}";
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

}
