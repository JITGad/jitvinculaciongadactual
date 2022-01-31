package com.jitgad.bjitgad.AllApis;

import com.jitgad.bjitgad.DAO.QuestionsDAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author jorge
 */
@Path("questions")
public class Questionsresource {

    private final QuestionsDAO questionsDAO;

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
