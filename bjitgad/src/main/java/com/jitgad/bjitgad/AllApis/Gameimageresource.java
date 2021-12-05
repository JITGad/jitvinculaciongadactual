
package com.jitgad.bjitgad.AllApis;

import com.jitgad.bjitgad.DAO.GameimageDAO;
import com.jitgad.bjitgad.Models.GameimageModel;
import javax.ws.rs.GET;
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

@Path("gameimage")
public class Gameimageresource {
    
   @Context
   private UriInfo context;
   private GameimageDAO gameimageDAO;
   private GameimageModel GameimageModel;

    public Gameimageresource() {
      gameimageDAO = new GameimageDAO();
    }
   
   /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGameimage() {
        //TODO return proper representation object
        String responseJson = gameimageDAO.selectGameimage();
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
           
}
