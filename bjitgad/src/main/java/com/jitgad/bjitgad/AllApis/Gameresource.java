
package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
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
import javax.ws.rs.core.Context;
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
    private GameDAO gameDAO;
    private GameModel gameModel;

    public Gameresource() {
        gameDAO = new GameDAO();
    }
    
    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame() {
        //TODO return proper representation object
        String gameD = gameDAO.selectGame();
        return Response.ok(gameD)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/insertgame")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertgame(String data) {
        System.out.println(data);
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        boolean insertgame = false;
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
            gameModel = new GameModel();
            gameModel.setIdactivitiestype(Methods.JsonToString(Jso.getAsJsonObject(), "idactivitiestype", ""));
            gameModel.setIdgametype(Methods.JsonToString(Jso.getAsJsonObject(), "idgametype", ""));
            gameModel.setName(Methods.JsonToString(Jso.getAsJsonObject(), "name", ""));
            gameModel.setCreationdate(Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""));
            gameModel.setUpdatedate(Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""));
            gameModel.setState(Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
           
            insertgame = gameDAO.insertGame(gameModel);
            
            if(insertgame){
               responseJson = "{ \"status\":" +  insertgame + ",\"information\": \" The Game was inserted.\"}"; 
            }
            else{
                responseJson = "{ \"status\":" +  insertgame + ",\"information\": \" The Game was not inserted.\"}"; 
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
