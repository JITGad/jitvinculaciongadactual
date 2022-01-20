package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.UserController;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.UserModel;
import com.jitgad.bjitgad.Models.UserRequestModel;
import com.jitgad.bjitgad.Models.UserTokenRModel;
import com.jitgad.bjitgad.Resources.ResponseAPI;
import com.jitgad.bjitgad.Utilities.ResponseData;
import com.jitgad.bjitgad.Utilities.ResponseDataPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
@Path("users")
public class Userresource {

    @Context
    private HttpServletRequest request;
    private final UserController userC;
    private final ResponseAPI Rapi;
    private final AuthorizationController AuC;
    private UserModel userModel;

    public Userresource() {
        userC = new UserController();
        Rapi = new ResponseAPI();
        AuC = new AuthorizationController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUsersAdmin")
    public Response getUsersAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando getUsersAdmin...");
        }

        ResponseDataPage responseDataPage = new ResponseDataPage("Ocurrió un error", page, true);

        try {
            int responseCountingPage = 0;
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            if (Configuration.DEBUG) {
                System.out.println("Authorization: " + Authorization);
            }

            if (!Authorization.isEmpty()) {
                ArrayList<UserModel> data = userC.selectUserspage(page);
                Object[] Permt = AuC.VToken(Authorization);
                if (Permt[0].equals(true)) {
                    responseCountingPage = userC.CountingPageUsers();
                    if (data.size() > 0) {

                        responseDataPage.setMessage("Información encontrada");
                        responseDataPage.setCountingpage(responseCountingPage);
                        responseDataPage.setData(data);
                        return Response.ok(Methods.objectToJsonString(responseDataPage)).build();
                    }

                    responseDataPage.setMessage("Información no encontrada");
                    responseDataPage.setCountingpage(responseCountingPage);
                    responseDataPage.setData(data);
                    return Response.ok(Methods.objectToJsonString(responseDataPage)).build();
                }

                responseDataPage.setMessage(String.valueOf(Permt[1]));
                responseDataPage.setCountingpage(responseCountingPage);
                return Response.ok(Methods.objectToJsonString(responseDataPage)).build();

            }

            responseDataPage.setMessage("Token vacio");
            responseDataPage.setCountingpage(responseCountingPage);
            return Response.ok(Methods.objectToJsonString(responseDataPage)).build();

        } catch (Exception e) {
            responseDataPage.setFlag(false);

            if (Configuration.DEBUG) {

                responseDataPage.setMessage(e.getMessage());

                return Response.ok(Methods.objectToJsonString(responseDataPage)).build();
            }

            responseDataPage.setMessage("Ha ocurrido un error en el servidor, vuelva a intentarlo mas tarde");

            System.err.println(e.getMessage());
        }
        return Response.ok(Methods.objectToJsonString(responseDataPage)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUsersAdminbyid")
    public Response getUsersAdminbyid(@Context HttpHeaders headers, @QueryParam("id") int id) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando getUsersAdminbyid...");
        }
        ResponseData responseData = new ResponseData("Ocurrio un error", true);

        try {

            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            if (Configuration.DEBUG) {
                System.out.println("Authorization: " + Authorization);
            }

            if (!Authorization.isEmpty()) {
                Object[] Permt = AuC.VToken(Authorization);
                if (Permt[0].equals(true)) {

                    JsonObject data = Methods.stringToJSON(userC.selectUsersbyid(id));

                    if (data.size() > 0) {

                        responseData.setMessage("Información encontrada");
                        responseData.setData(data);

                        return Response.ok(Methods.objectToJsonString(responseData)).build();

                    }
                    responseData.setMessage("Información no encontrada");

                    return Response.ok(Methods.objectToJsonString(responseData)).build();

                }
                responseData.setMessage(String.valueOf(Permt[1]));

                return Response.ok(Methods.objectToJsonString(responseData)).build();

            }
            responseData.setMessage("Tokén vacio");

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
    @Path("/logIn")
    @Consumes(MediaType.APPLICATION_JSON)

    public Response logIn(String data) {
        ResponseData responseData = new ResponseData("Ocurrio un error", false);
        UserRequestModel userRequest = (UserRequestModel) Methods.StringJsonToObject(data, UserRequestModel.class);
        try {
            responseData = userC.LogIn(userRequest);
            responseData.setData(userC.BuildToken((UserTokenRModel) responseData.getData(), userRequest));

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
    public Response PostUserRegistration(@Context HttpHeaders headers, String data) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando PostUserRegistration...");
        }

        ResponseData responseData = new ResponseData("Ocurrio un error", false);

        userModel = (UserModel) Methods.StringJsonToObject(data, UserModel.class);

        JsonObject Jso = Methods.stringToJSON(data);

        try {
            if (Jso.size() > 0) {
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;

                if (Configuration.DEBUG) {
                    System.out.println("Authorization: " + Authorization);
                }

                if (!Authorization.isEmpty()) {

                    Object[] Permt = AuC.VToken(Authorization);

                    if (Permt[0].equals(true)) {

                        responseData = userC.UserRegistration(userModel,
                        request.getServletContext().getRealPath("/"));

                        return Response.ok(Methods.objectToJsonString(responseData)).build();
                    }
                    responseData.setMessage(String.valueOf(Permt[1]));
                    return Response.ok(Methods.objectToJsonString(responseData)).build();

                }
                responseData.setMessage("Tokén vacio");
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
    @PUT
    @Path("/putuser")
    public Response PutUser(@Context HttpHeaders headers, String data) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando PutUser...");
        }

        ResponseData responseData = new ResponseData("Ocurrio un error", false);

        userModel = (UserModel) Methods.StringJsonToObject(data, UserModel.class);

        JsonObject Jso = Methods.stringToJSON(data);

        try {
            if (Jso.size() > 0) {
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;

                if (Configuration.DEBUG) {
                    System.out.println("Authorization: " + Authorization);
                }

                if (!Authorization.isEmpty()) {

                    Object[] Permt = AuC.VToken(Authorization);

                    if (Permt[0].equals(true)) {

                        responseData = userC.PutUser(userModel,
                        request.getServletContext().getRealPath("/"));

                        return Response.ok(Methods.objectToJsonString(responseData)).build();
                    }
                    responseData.setMessage(String.valueOf(Permt[1]));
                    return Response.ok(Methods.objectToJsonString(responseData)).build();

                }
                responseData.setMessage("Tokén vacio");
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
    @DELETE
    @Path("/deleteuser")
    public Response deleteuser(@Context HttpHeaders headers, String data) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando deleteuser...");
        }

        ResponseData responseData = new ResponseData("Ocurrio un error", false);

        userModel = (UserModel) Methods.StringJsonToObject(data, UserModel.class);

        JsonObject Jso = Methods.stringToJSON(data);

        try {
            if (Jso.size() > 0) {
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

                            responseData = userC.DeleteUser(userModel);

                            return Response.ok(Methods.objectToJsonString(responseData)).build();
                        }
                        responseData.setMessage(String.valueOf(Permt[1]));
                        return Response.ok(Methods.objectToJsonString(responseData)).build();

                    }
                    responseData.setMessage("Usuario sin privilegios para realizar esta actividad");
                    return Response.ok(Methods.objectToJsonString(responseData)).build();

                }
                responseData.setMessage("Tokén vacio");
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
}
