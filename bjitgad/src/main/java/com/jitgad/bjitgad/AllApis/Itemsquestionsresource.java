
package com.jitgad.bjitgad.AllApis;

import com.jitgad.bjitgad.DAO.ItemsquestionsDAO;
import com.jitgad.bjitgad.Models.ItemsquestionsModel;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

/**
 *
 * @author jorge
 */
@Path("itemsquestions")
public class Itemsquestionsresource {
    @Context
    private UriInfo context;
    private ItemsquestionsDAO itemsquestionsdao;
    private ItemsquestionsModel itemsquestionsmodel;

    public Itemsquestionsresource() {
        itemsquestionsdao = new ItemsquestionsDAO();
    }
    
    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsquestions() {
        //TODO return proper representation object
        String responseJson = itemsquestionsdao.selectItemsquestions();
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
}
