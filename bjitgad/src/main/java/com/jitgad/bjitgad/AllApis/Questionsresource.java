package com.jitgad.bjitgad.AllApis;

import com.jitgad.bjitgad.DAO.QuestionsDAO;
import com.jitgad.bjitgad.Models.QuestionsModel;
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
@Path("questions")
public class Questionsresource {

    @Context
    private UriInfo context;
    private QuestionsDAO questionsDAO;
    private QuestionsModel questionsModel;

    public Questionsresource() {
        questionsDAO = new QuestionsDAO();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestions() {
        //TODO return proper representation object
        String responseJson = questionsDAO.selectQuestions();
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

}
