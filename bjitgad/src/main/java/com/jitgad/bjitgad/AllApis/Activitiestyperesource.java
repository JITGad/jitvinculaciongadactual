
package com.jitgad.bjitgad.AllApis;


import com.google.gson.JsonObject;
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
    private ActivitiestypeModel activitiestM;
    

    public Activitiestyperesource() {
        activitiestypeDAO = new ActivitiestypeDAO();
    }
    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivitiestype() {
        //TODO return proper representation object
        String responseJson = activitiestypeDAO.selectActivitiestype();
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/gamesbyactivities")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getgamesbyactivities(@QueryParam("activityid") String activityid){
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
    @Path("/insertactivitiestype")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertactivitiestype(String data) {
        System.out.println(data);
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        boolean insertactivitiestype = false;
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
            
            activitiestM = new ActivitiestypeModel();
            activitiestM.setName(Methods.JsonToString(Jso.getAsJsonObject(), "name", ""));
            activitiestM.setImage(Methods.JsonToString(Jso.getAsJsonObject(), "image", ""));
            activitiestM.setCreationdate(Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""));
            activitiestM.setUpdatedate(Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""));
            activitiestM.setState(Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
           
            insertactivitiestype = activitiestypeDAO.insertActividadestype(activitiestM);
            
            if(insertactivitiestype){
               responseJson = "{ \"status\":" +  insertactivitiestype + ",\"information\": \" The Activities type was inserted.\"}"; 
            }
            else{
                responseJson = "{ \"status\":" +  insertactivitiestype + ",\"information\": \" The Activities type was not inserted.\"}"; 
            }
            
        }else {
            responseJson = "{\"message\":\"Missing data.\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + false + "}";
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
    
}
