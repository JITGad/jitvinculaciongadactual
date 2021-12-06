
package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.UserController;
import com.jitgad.bjitgad.DAO.UserDAO;
import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.UserModel;
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

    public Userresource() {
        ud = new UserDAO();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        //TODO return proper representation object
        String responseJson = ud.selectUserAll();
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
            String email = Methods.JsonToString(Jso.getAsJsonObject(), "email", "");
            String password = Methods.JsonToString(Jso.getAsJsonObject(), "password", "");
            System.out.println(email);
            System.out.println(password);
            UserController userCon = new UserController();
            Object[] responseLogIn;
            responseLogIn = userCon.LogIn(email, password);

            if (responseLogIn[0].equals(true)) {
                responseJson = "{\"message\":\"" + responseLogIn[1] + "\",\"flag\":" + responseLogIn[0] + ",\"data\":" + (new UserDAO().userDataJson((UserModel) responseLogIn[2])) + "}";
            } else {
                responseJson = "{\"message\":\"" + responseLogIn[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + responseLogIn[0] + "}";
            }
        } else {
            responseJson = "{\"message\":\"Missing data.\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + false + "}";
        }
//        System.out.println(responseJson);
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/UserRegistration")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response UserRegistration(String data) {
        System.out.println(data);
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        JsonObject Jso = Methods.stringToJSON(data);
        if (Jso.size() > 0) {
            
            UserController userCon = new UserController();
            Object[] responseUserRegistration;
            responseUserRegistration = 
                userCon.UserRegistration(
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
                responseJson = "{\"message\":\"" + responseUserRegistration[1] + "\",\"flag\":" + responseUserRegistration[0] + "}";
            } else {
                responseJson = "{\"message\":\"" + responseUserRegistration[1] + "\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + responseUserRegistration[0] + "}";
            }
        } else {
            responseJson = "{\"message\":\"Missing data.\",\"nameApplication\":\"" + DataBd.nameApplication + "\",\"flag\":" + false + "}";
        }
//        System.out.println(responseJson);
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
    
    
    
    
}
