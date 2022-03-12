package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.ActivitiestypeController;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Utilities.ResponseData;
import com.jitgad.bjitgad.Utilities.ResponseDataPage;
import com.jitgad.bjitgad.Utilities.ResponseValidateToken;
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
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
@Path("activitiestype")
public class Activitiestyperesource {

    private final ActivitiestypeController atC;
    private final AuthorizationController AuC;
    private ActivitiestypeModel activitiestypeModel;

    public Activitiestyperesource() {
        atC = new ActivitiestypeController();
        AuC = new AuthorizationController();
    }

    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivitiestype() {
        ResponseData responseData = new ResponseData("Ocurrio un error", true);
        if (Configuration.DEBUG) {
            System.out.println("Ingresando a getActivitiestype ");
        }
        try {
            ArrayList<ActivitiestypeModel> data = atC.selectActivitiestype();

            if (data.size() > 0) {

                responseData.setMessage("Información encontrada");
                responseData.setData(data);

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
    
    // recibe token - administración
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getActivitiestypeAdmin")
    public Response getActivitiestypeAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {
        ResponseDataPage responseDataPage = new ResponseDataPage("Ocurrió un error", page, false);
        if (Configuration.DEBUG) {
            System.out.println("Ingresando a getActivitiestypeAdmin ");
        }
        try {
            int responseCountingPage = 0;
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;

            if (!Authorization.isEmpty()) {
                ResponseValidateToken validateToken = AuC.VToken(Authorization);
                if (validateToken.isStatus()) {
                    ArrayList<ActivitiestypeModel> data = atC.selectActivitiestypepage(page);
                    responseCountingPage = atC.CountingPageActivitiesType();
                    responseDataPage.setMessage("Información encontrada");
                    responseDataPage.setCountingpage(responseCountingPage);
                    responseDataPage.setData(data);
                    responseDataPage.setFlag(true);
                    responseDataPage.setTotalPages(Math.round((responseCountingPage / 10) + 1));
                    return Response.ok(Methods.objectToJsonString(responseDataPage)).build();
                }
                responseDataPage.setMessage(validateToken.getMessage());
                responseDataPage.setCountingpage(responseCountingPage);
                return Response.ok(Methods.objectToJsonString(responseDataPage)).build();

            }
            responseDataPage.setMessage("Token vacio");
            responseDataPage.setCountingpage(responseCountingPage);
            return Response.ok(Methods.objectToJsonString(responseDataPage)).build();

        } catch (Exception e) {
            if (Configuration.DEBUG) {

                responseDataPage.setMessage(e.getMessage());

                return Response.ok(Methods.objectToJsonString(responseDataPage)).build();
            }

            responseDataPage.setMessage("Ha ocurrido un error en el servidor, vuelva a intentarlo mas tarde");

            System.err.println(e.getMessage());
        }
        return Response.ok(Methods.objectToJsonString(responseDataPage)).build();
    }

    /**
     * Actividades por ID
     *
     * @param headers
     * @param activityid
     * @return
     */
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/getactivitiesbyid")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getactivitiesbyid(@Context HttpHeaders headers, @QueryParam("activityid") int activityid) {
        ResponseData responseData = new ResponseData("Ocurrio un error", true);
        if (Configuration.DEBUG) {
            System.out.println("Ingresando a getactivitiesbyid ");
        }
        try {

            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;

            if (!Authorization.isEmpty()) {
                ResponseValidateToken validateToken = AuC.VToken(Authorization);
                if (validateToken.isStatus()) {

                    JsonObject data = Methods.stringToJSON(atC.selectactivitiesbyid(activityid));

                    if (data.size() > 0) {

                        responseData.setMessage("Información encontrada");
                        responseData.setData(data);

                        return Response.ok(Methods.objectToJsonString(responseData)).build();

                    }
                    responseData.setMessage("Información no encontrada");

                    return Response.ok(Methods.objectToJsonString(responseData)).build();

                }
                responseData.setMessage(validateToken.getMessage());

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
    @GET
    @Path("/getactivitiesbyidsk")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getactivitiesbyidsk(@QueryParam("activityid") int activityid) {
        ResponseData responseData = new ResponseData("Ocurrio un error", true);
        if (Configuration.DEBUG) {
            System.out.println("Ingresando a getactivitiesbyidsk ");
        }
        try {

            JsonObject data = Methods.stringToJSON(atC.selectactivitiesbyid(activityid));

            if (data.size() > 0) {

                responseData.setMessage("Información encontrada");
                responseData.setData(data);

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getactivitiestypecv")
    public Response getactivitiestypecv(@Context HttpHeaders headers) {
        ResponseData responseData = new ResponseData("Ocurrio un error", true);
        if (Configuration.DEBUG) {
            System.out.println("Ingresando a getactivitiestypecv ");
        }
        try {

            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;

            if (!Authorization.isEmpty()) {
                ResponseValidateToken validateToken = AuC.VToken(Authorization);
                if (validateToken.isStatus()) {

                    ArrayList<ClaveValorModel> data = atC.selectactivitiestypecv();

                    if (data.size() > 0) {

                        responseData.setMessage("Información encontrada");
                        responseData.setData(data);

                        return Response.ok(Methods.objectToJsonString(responseData)).build();

                    }
                    responseData.setMessage("Información no encontrada");

                    return Response.ok(Methods.objectToJsonString(responseData)).build();

                }
                responseData.setMessage(validateToken.getMessage());

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

    /*
     add activities
     */
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/postActivitiesType")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostActivitiesType(@Context HttpHeaders headers, String data) {
        ResponseData responseData = new ResponseData("Ocurrio un error", false);
        if (Configuration.DEBUG) {
            System.out.println("Ingresando a PostActivitiesType ");
        }

        try {
            activitiestypeModel
                    = (ActivitiestypeModel) Methods.StringJsonToObject(data, ActivitiestypeModel.class);

            JsonObject Jso = Methods.stringToJSON(data);
            if (Jso.size() > 0) {
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;

                if (!Authorization.isEmpty()) {

                    ResponseValidateToken validateToken = AuC.VToken(Authorization);

                    if (validateToken.isStatus()) {

                        responseData = atC.InsertActivitiesTypeC(activitiestypeModel);

                        return Response.ok(Methods.objectToJsonString(responseData)).build();
                    }
                    responseData.setMessage(validateToken.getMessage());
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
    @Path("/PutActivitiesType")
    public Response PutActivitiesType(@Context HttpHeaders headers, String data) {
        ResponseData responseData = new ResponseData("Ocurrio un error", false);
        if (Configuration.DEBUG) {
            System.out.println("Ingresando a PutActivitiesType ");
        }

        try {
            activitiestypeModel
                    = (ActivitiestypeModel) Methods.StringJsonToObject(data, ActivitiestypeModel.class);

            JsonObject Jso = Methods.stringToJSON(data);
            if (Jso.size() > 0) {
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;

                if (!Authorization.isEmpty()) {

                    ResponseValidateToken validateToken = AuC.VToken(Authorization);

                    if (validateToken.isStatus()) {

                        responseData = atC.UpdateActivitiesTypeC(activitiestypeModel);

                        return Response.ok(Methods.objectToJsonString(responseData)).build();
                    }
                    responseData.setMessage(validateToken.getMessage());
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
                responseData.setMessage(e.getMessage() + e.getLocalizedMessage() + e.toString());
                return Response.ok(Methods.objectToJsonString(responseData)).build();
            }

            responseData.setMessage("Ha ocurrido un error en el servidor, vuelva a intentarlo mas tarde");

            System.err.println(e.getMessage() + e.getLocalizedMessage() + e.toString() + e.getCause().toString());
        }
        return Response.ok(Methods.objectToJsonString(responseData)).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    @Path("/DeleteActivitiesType")
    public Response DeleteActivitiesType(@Context HttpHeaders headers, String data) {
        ResponseData responseData = new ResponseData("Ocurrio un error", false);
        if (Configuration.DEBUG) {
            System.out.println("Ingresando a DeleteActivitiesType ");
        }

        try {
            activitiestypeModel
                    = (ActivitiestypeModel) Methods.StringJsonToObject(data, ActivitiestypeModel.class);

            JsonObject Jso = Methods.stringToJSON(data);
            if (Jso.size() > 0) {
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;

                if (!Authorization.isEmpty()) {

                    ResponseValidateToken validateToken = AuC.VToken(Authorization);

                    if (validateToken.getRol().equals("Administrador")) {

                        if (validateToken.isStatus()) {

                            responseData = atC.DeleteActividadestype(activitiestypeModel);

                            return Response.ok(Methods.objectToJsonString(responseData)).build();
                        }
                        responseData.setMessage(validateToken.getMessage());
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
