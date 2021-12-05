
package com.jitgad.bjitgad.AllApis;

import com.jitgad.bjitgad.DAO.ColortypeDAO;
import com.jitgad.bjitgad.Models.ColortypeModel;
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

@Path("colortype")
public class Colortyperesource {
    
   @Context
   private UriInfo context;
   private ColortypeModel colortypeModel;
   private ColortypeDAO colortypeDAO;
           

    public Colortyperesource() {
       colortypeDAO = new ColortypeDAO();
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
        String responseJson = colortypeDAO.selectColortype();
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
   
   
}
