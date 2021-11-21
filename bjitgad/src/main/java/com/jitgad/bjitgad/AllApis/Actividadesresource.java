package com.jitgad.bjitgad.AllApis;

import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ActividadesModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jitgad.bjitgad.DAO.ActividadesDAO;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author jorge
 */
@Path("actividades")

public class Actividadesresource {

    @Context
    private UriInfo context;
    private ActividadesDAO ad;

    public Actividadesresource() {
        ad = new ActividadesDAO();
    }
    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllActividades() {

        String actividades = ad.selectActividadesDAO();
        //TODO return proper representation object
        return Response.ok(actividades)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/insertactividad")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertactividad(String data) {
        // System.out.println(data);
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        boolean insertam = false;
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {

            ActividadesModel am = new ActividadesModel();
            am.setAct_nombre(Methods.JsonToString(Jso.getAsJsonObject(), "act_nombre", ""));
            am.setAct_interacciones(Methods.JsonToString(Jso.getAsJsonObject(), "act_interacciones", ""));
            am.setAct_icono(Methods.JsonToString(Jso.getAsJsonObject(), "act_icono", ""));

            insertam = ad.insertActividad(am);

            if (insertam) {
                responseJson = "{ \"status\":" + insertam + ",\"information\": \" The Activity was inserted.\"}";
            } else {
                responseJson = "{ \"status\":" + insertam + ",\"information\": \" The Activity was not inserted.\"}";
            }
        } else {
            responseJson = "{\"message\":\"Missing data.\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + insertam + "}";
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
}
