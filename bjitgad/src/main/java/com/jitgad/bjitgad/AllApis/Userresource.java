package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.UserController;
import com.jitgad.bjitgad.DAO.UserDAO;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.UserModel;
import com.jitgad.bjitgad.Resources.ResponseAPI;
import com.jitgad.bjitgad.Utilities.ResponseData;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
@Path("users")
public class Userresource {

    @Context
    private UriInfo context;
    private UserController userC;
    private ResponseAPI Rapi;
    private AuthorizationController AuC;

    public Userresource() {
        userC = new UserController();
        Rapi = new ResponseAPI();
        AuC = new AuthorizationController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUsersAdmin")
    public Response getUsersAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {
        //TODO return proper representation object
        String data = userC.selectUserspage(page);
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        int responseCountingPage = 0;
        try {
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            System.out.println("Authorization: " + Authorization);
            Object[] Permt = AuC.VToken(Authorization);
            if (!Authorization.isEmpty()) {
                if (Permt[2].equals("Administrador")) {
                    if (Permt[0].equals(true)) {
                        responseCountingPage = userC.CountingPageUsers();
                        if (data.equals("{}")) {
                            responseJson = Rapi.AdminResponse("Información no encontrada", responseCountingPage, false, data);
                        } else {
                            responseJson = Rapi.AdminResponse("Datos retornados correctamente", responseCountingPage, true, data);
                        }
                    } else {
                        responseJson = Rapi.AdminResponse(String.valueOf(Permt[1]), responseCountingPage, false, data);
                    }
                } else {
                    responseJson = Rapi.Response("Usuario sin privilegios para realizar esta actividad", false, data);
                }
            } else {
                responseJson = Rapi.AdminResponse("Token vacio", responseCountingPage, false, data);
            }

        } catch (Exception e) {
            responseJson = Rapi.AdminResponse(e.getMessage(), responseCountingPage, false, responseJson);
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUsersAdminbyid")
    public Response getUsersAdminbyid(@Context HttpHeaders headers, @QueryParam("id") int id) {
        //TODO return proper representation object
        String data = userC.selectUsersbyid(id);
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        try {
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            if (Configuration.DEBUG) {
                System.out.println("Authorization: " + Authorization);
            }
            if (!Authorization.isEmpty()) {
                Object[] Permt = AuC.VToken(Authorization);
                if (Permt[2].equals("Administrador")) {
                    if (Permt[0].equals(true)) {
                        if (data.equals("{}")) {
                            responseJson = Rapi.Response("Información no encontrada", false, data);
                            return Response.ok(responseJson).build();
                        } 
                        responseJson = Rapi.Response("Datos retornados correctamente", true, data);
                    } else {
                        responseJson = Rapi.Response(String.valueOf(Permt[1]), false, data);
                    }
                } else {
                    responseJson = Rapi.Response("Usuario sin privilegios para realizar esta actividad", false, data);
                }
            } else {
                responseJson = Rapi.Response("Tokén vacio", true, data);
            }

        } catch (Exception e) {
            responseJson = Rapi.Response(e.getMessage(), false, data);
        }
        return Response.ok(responseJson).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/logIn")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logIn(String data) {
        ResponseData responseData = new ResponseData("Ocurrio un error", false);
        try {
            JsonObject Jso = Methods.stringToJSON(data);
            if (Jso.size() > 0) {
                UserController userCon = new UserController();
    
                Object[] responseLogIn = userCon.LogIn(Methods.JsonToString(Jso.getAsJsonObject(), "email", "{}"),
                        Methods.JsonToString(Jso.getAsJsonObject(), "password", ""));
    
                responseData.setMessage(String.valueOf(responseLogIn[1]));
                responseData.setFlag(Boolean.valueOf(responseLogIn[0].toString()));
                responseData.setData((new UserDAO().userDataJson((UserModel) responseLogIn[2],
                        Methods.JsonToString(Jso.getAsJsonObject(), "recuerdame", "{}"))));
                
                return Response.ok(Methods.objectToJsonString(responseData)).build();
            }
            
            responseData.setMessage("Información no encontrada");
            
            return Response.ok(Methods.objectToJsonString(responseData)).build();
        } catch (Exception e) {
            responseData.setFlag(false);

            if (Configuration.DEBUG) {
                responseData.setMessage(e.getMessage());
                return Response.ok(Methods.objectToJsonString(responseData)).build();
            }

            responseData.setMessage("Ha ocurrido un error en el servidor, vuelva a intentarlo mas tarde");

            System.err.println(e.getMessage());
        }
        return Response.ok(Methods.objectToJsonString(responseData)).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/PostUserRegistration")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostUserRegistration(String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
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
                                Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
                if (responseUserRegistration[0].equals(true)) {
                    responseJson = Rapi.Response(String.valueOf(responseUserRegistration[1]),
                            Boolean.parseBoolean(responseUserRegistration[0].toString()), "{}");
                } else {
                    responseJson = Rapi.Response(String.valueOf(responseUserRegistration[1]),
                            Boolean.parseBoolean(responseUserRegistration[0].toString()), "{}");
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

    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    @Path("/putuser")
    public Response PutUser(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando PutUser...");
        JsonObject Jso = Methods.stringToJSON(data);
        try {
            if (Jso.size() > 0) {
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;
                System.out.println("Authorization: " + Authorization);
                Object[] Permt = AuC.VToken(Authorization);
                if (!Authorization.isEmpty()) {
                    if (Permt[0].equals(true)) {
                        Object[] responseUserUpdate = userC.PutUser(
                                Methods.JsonToInteger(Jso.getAsJsonObject(), "iduser", 0),
                                Methods.JsonToString(Jso.getAsJsonObject(), "names", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "last_name", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "email", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "password", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "birthday", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "rol", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
                        if (responseUserUpdate[0].equals(true)) {
                            responseJson = Rapi.Response(String.valueOf(responseUserUpdate[1]),
                                    Boolean.parseBoolean(responseUserUpdate[0].toString()), "{}");
                        } else {
                            responseJson = Rapi.Response(String.valueOf(responseUserUpdate[1]),
                                    Boolean.parseBoolean(responseUserUpdate[0].toString()), "{}");
                        }
                    } else {
                        responseJson = Rapi.Response(String.valueOf(Permt[1]), false, "{}");
                    }
                } else {
                    responseJson = Rapi.Response("Tokén vacio", true, data);
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

    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    @Path("/deleteuser")
    public Response DeleteActivitiesType(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando deleteuser...");
        JsonObject Jso = Methods.stringToJSON(data);
        System.out.println(responseJson);
        try {
            if (Jso.size() > 0) {
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;
                System.out.println("Authorization: " + Authorization);
                Object[] Permt = AuC.VToken(Authorization);
                if (!Authorization.isEmpty()) {
                    if (Permt[2].equals("Administrador")) {
                        if (Permt[0].equals(true)) {
                            Object[] responseUserDelete = userC.DeleteUser(
                                    Methods.JsonToInteger(Jso.getAsJsonObject(), "iduser", 0));
                            if (responseUserDelete[0].equals(true)) {
                                responseJson = Rapi.Response(String.valueOf(responseUserDelete[1]),
                                        Boolean.parseBoolean(responseUserDelete[0].toString()), data);
                            } else {
                                responseJson = Rapi.Response(String.valueOf(responseUserDelete[1]),
                                        Boolean.parseBoolean(responseUserDelete[0].toString()), data);
                            }
                        } else {
                            responseJson = Rapi.Response(String.valueOf(Permt[1]), false, data);
                        }
                    } else {
                        responseJson = Rapi.Response("Usuario sin privilegios para realizar esta actividad", false, data);
                    }
                } else {
                    responseJson = Rapi.Response("Tokén vacio", true, data);
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
