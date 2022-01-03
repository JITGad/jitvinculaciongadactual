package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.UserController;
import com.jitgad.bjitgad.DAO.UserDAO;
import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.UserModel;
import com.jitgad.bjitgad.Resources.ResponseAPI;
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
@Path("users")
public class Userresource {

    @Context
    private UriInfo context;
    private UserModel um;
    private UserDAO ud;
    private UserController userC;
    private ResponseAPI Rapi;

    public Userresource() {
        ud = new UserDAO();
        userC = new UserController();
        Rapi = new ResponseAPI();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        //TODO return proper representation object
        String data = ud.selectUser();
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        try {
            if (data.equals("{}")) {
                responseJson = Rapi.Response("Información no encontrada", false, data);
            } else {
                responseJson = Rapi.Response("Datos retornados correctamente", true, data);
            }
        } catch (Exception e) {
            responseJson = Rapi.Response(e.getMessage(), false, data);
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/logIn")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logIn(String data) {
        System.out.println(data);
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
            UserController userCon = new UserController();
            Object[] responseLogIn;
            responseLogIn = userCon.LogIn(Methods.JsonToString(Jso.getAsJsonObject(), "email", ""),
                    Methods.JsonToString(Jso.getAsJsonObject(), "password", ""));

            if (responseLogIn[0].equals(true)) {
                responseJson = Rapi.Response(String.valueOf(responseLogIn[1]),
                        Boolean.valueOf(responseLogIn[0].toString()),
                        (new UserDAO().userDataJson((UserModel) responseLogIn[2],
                                Methods.JsonToString(Jso.getAsJsonObject(), "recuerdame", ""))));
            } else {
                responseJson = Rapi.Response(String.valueOf(responseLogIn[1]),
                        Boolean.valueOf(responseLogIn[0].toString()),
                        (new UserDAO().userDataJson((UserModel) responseLogIn[2],
                                Methods.JsonToString(Jso.getAsJsonObject(), "recuerdame", ""))));
            }
        } else {
            responseJson = Rapi.Response("Información no encontrada", false, data);
        }
//        System.out.println(responseJson);
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/PostUserRegistration")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostUserRegistration(String data) {
        System.out.println(data);
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        System.out.println("Ingresando PostUserRegistration...");
        JsonObject Jso = Methods.stringToJSON(data);
        try {
            if (Jso.size() > 0) {
                Object[] responseUserRegistration;
                responseUserRegistration
                        = userC.UserRegistration(
                                Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "last_name", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "email", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "password", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "birthday", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "rol", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "creationdate", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "updatedate", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
                if (responseUserRegistration[0].equals(true)) {
                    responseJson = Rapi.Response(String.valueOf(responseUserRegistration[1]),
                            Boolean.parseBoolean(responseUserRegistration[0].toString()), data);
                } else {
                    responseJson = Rapi.Response(String.valueOf(responseUserRegistration[1]),
                            Boolean.parseBoolean(responseUserRegistration[0].toString()), data);
                }
            } else {
                responseJson = Rapi.Response("Información no encontrada", false, data);
            }
        } catch (Exception e) {
            responseJson = Rapi.Response(e.getMessage(), false, data);
        }

        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

}
