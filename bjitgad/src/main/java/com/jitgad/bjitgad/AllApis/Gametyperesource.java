
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
    private GametypeDAO gametypeDAO;
    private GametypeModel gametypeModel;
    private AuthorizationController AuC;
    private GametypeController gtC;
    

    
    public Gametyperesource() {
        gametypeDAO = new GametypeDAO();
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
        //TODO return proper representation object
        String gametypeD = gtC.selectGametype();
        return Response.ok(gametypeD)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getgametypeAdmin")
    public Response getgametypeAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {
        String responseJson="[]";
        int responseCountingPage = 0;
        //TOKENS
        String Authorization = headers.getHeaderString("Authorization");
        Authorization = Authorization == null ? "" : Authorization;
        System.out.println("Authorization: " + Authorization);
        Object[] Permt = AuC.VToken(Authorization);
        if (Permt[0].equals(true)) {
            responseJson = "";
            if(!Methods.jsonrecordcount(responseJson)){
                responseJson = "{\"message\":\"" + "" + "\",\"data\":\"" + responseJson + "\",\"flag\":" + "true" + "}"; // FORMATO DE LAS PETICIONES
            }
        }
        
        
        //String gametypeD = gametypeDAO.selectGametype();
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
    
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/insertgametype")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertactivitiestype(String data) {
        System.out.println(data);
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        boolean insertgametype = false;
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
            
            gametypeModel = new GametypeModel();
            gametypeModel.setName(Methods.JsonToString(Jso.getAsJsonObject(), "name", ""));
            gametypeModel.setImage(Methods.JsonToString(Jso.getAsJsonObject(), "image", ""));
            gametypeModel.setAudio_instructions(Methods.JsonToString(Jso.getAsJsonObject(), "audio_instructions", ""));
            gametypeModel.setCreationdate(Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""));
            gametypeModel.setUpdatedate(Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""));
            gametypeModel.setState(Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
           
            insertgametype = gametypeDAO.insertGametype(gametypeModel);
            
            if(insertgametype){
               responseJson = "{ \"status\":" +  insertgametype + ",\"information\": \" The Game type was inserted.\"}"; 
            }
            else{
                responseJson = "{ \"status\":" +  insertgametype + ",\"information\": \" The Game type was not inserted.\"}"; 
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

