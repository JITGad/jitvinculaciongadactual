package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.ActivitiestypeController;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.FileController;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ActivitiestypeModel;
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
import java.util.ArrayList;

/**
 *
 * @author jorge
 */
@Path("activitiestype")
public class Activitiestyperesource {

    @Context
    private HttpServletRequest request;
    //  private ActivitiestypeModel atM;
    private ActivitiestypeController atC;
    private AuthorizationController AuC;
    private ResponseAPI Rapi;
    private ActivitiestypeModel activitiestypeModel;

    public Activitiestyperesource() {
        atC = new ActivitiestypeController();
        // atM = new ActivitiestypeModel();
        AuC = new AuthorizationController();
        Rapi = new ResponseAPI();
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

        try {
            ArrayList<ActivitiestypeModel> data = atC.selectActivitiestype(request.getServletContext().getRealPath("/"));

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

        if (Configuration.DEBUG) {
            System.out.println("Ingresando getActivitiestypeAdmin...");
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
                ArrayList<ActivitiestypeModel> data = atC.selectActivitiestypepage(page);
                Object[] Permt = AuC.VToken(Authorization);
                if (Permt[0].equals(true)) {
                    responseCountingPage = atC.CountingPageActivitiesType();
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

    /**
     * Actividades por ID
     */
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/getactivitiesbyid")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getactivitiesbyid(@Context HttpHeaders headers, @QueryParam("activityid") int activityid) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando getactivitiesbyid...");
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

                    JsonObject data = Methods.stringToJSON(atC.selectactivitiesbyid(activityid));

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

    /*
     add activities
     */
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/postActivitiesType")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostActivitiesType(@Context HttpHeaders headers, String data) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando PostActivitiesType...");
        }
        ResponseData responseData = new ResponseData("Ocurrio un error", false);

        activitiestypeModel
                = (ActivitiestypeModel) Methods.StringJsonToObject(data, ActivitiestypeModel.class);

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

                        responseData = atC.InsertActivitiesTypeC(activitiestypeModel,
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
    @Path("/PutActivitiesType")
    public Response PutActivitiesType(@Context HttpHeaders headers, String data) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando PutActivitiesType...");
        }

        ResponseData responseData = new ResponseData("Ocurrio un error", false);

        activitiestypeModel
                = (ActivitiestypeModel) Methods.StringJsonToObject(data, ActivitiestypeModel.class);

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

                        responseData = atC.UpdateActivitiesTypeC(activitiestypeModel,
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
    @Path("/DeleteActivitiesType")
    public Response DeleteActivitiesType(@Context HttpHeaders headers, String data) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando DeleteActivitiesType...");
        }
        ResponseData responseData = new ResponseData("Ocurrio un error", false);

        activitiestypeModel
                = (ActivitiestypeModel) Methods.StringJsonToObject(data, ActivitiestypeModel.class);

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

                            responseData = atC.DeleteActividadestype(activitiestypeModel);

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

    /*
     add activities
     */
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/postActivitiesimage")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postActivitiesimage(String data) {
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        System.out.println("Ingresando postActivitiesimage...");
        JsonObject Jso = Methods.stringToJSON(data);
        String contextx = request.getServletContext().getRealPath("/");
        try {
            if (Jso.size() > 0) {
                String name = Methods.JsonToString(Jso.getAsJsonObject(), "name", "");
                String base64 = Methods.JsonToString(Jso.getAsJsonObject(), "base64", "");
                FileController fc = new FileController();
                Object[] CreateFile = fc.createfile(base64, "Activities", name, contextx);
                responseJson = Rapi.Response("Imagen creada con éxito", Boolean.parseBoolean(CreateFile[0].toString()), String.valueOf(CreateFile[1].toString() + "/" + name + "/" + CreateFile[2].toString()));

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
