package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.ActivitiestypeController;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.FileController;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Resources.ResponseAPI;
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
@Path("activitiestype")
public class Activitiestyperesource {

    @Context
    private UriInfo context;
    //  private ActivitiestypeModel atM;
    private ActivitiestypeController atC;
    private AuthorizationController AuC;
    private ResponseAPI Rapi;

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
        String data = atC.selectActivitiestype();
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
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with, Access-Control-Allow-Origin")
                .build();
    }

    // recibe token - administración
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getActivitiestypeAdmin")
    public Response getActivitiestypeAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {
        String responseJson = atC.selectActivitiestypepage(page);;
        int responseCountingPage = 0;
        try {
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            System.out.println("Authorization: " + Authorization);
            if (!Authorization.isEmpty()) {
                Object[] Permt = AuC.VToken(Authorization);
                if (Permt[0].equals(true)) {
                    responseCountingPage = atC.CountingPageActivitiesType();
                    if (responseJson.equals("{}")) {
                        responseJson = Rapi.AdminResponse("Información no encontrada", responseCountingPage, false, responseJson);
                    } else {
                        responseJson = Rapi.AdminResponse("Datos retornados correctamente", responseCountingPage, true, responseJson);
                    }
                } else {
                    responseJson = Rapi.AdminResponse(String.valueOf(Permt[1]), responseCountingPage, false, responseJson);
                }
            } else {
                responseJson = Rapi.AdminResponse("Token vacio", responseCountingPage, false, responseJson);
            }
        } catch (Exception e) {
            responseJson = Rapi.AdminResponse(e.getMessage(), responseCountingPage, false, responseJson);
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with, Access-Control-Allow-Origin")
                .build();
    }

    /**
     * Actividades por ID
     */
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/getactivitiesbyid")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getactivitiesbyid(@Context HttpHeaders headers, @QueryParam("activityid") int activityid) {
        String data = "{}";
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        try {
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            System.out.println("Authorization: " + Authorization);
            if (!Authorization.isEmpty()) {
                Object[] Permt = AuC.VToken(Authorization);
                if (Permt[0].equals(true)) {
                    data = atC.selectactivitiesbyid(activityid);
                    if (data.equals("{}")) {
                        responseJson = Rapi.Response("Información no encontrada", false, data);
                    } else {
                        responseJson = Rapi.Response("Datos retornados correctamente", true, data);
                    }
                } else {
                    responseJson = Rapi.Response(String.valueOf(Permt[1]), false, data);
                }
            } else {
                responseJson = Rapi.Response("Tokén vacio", true, data);
            }

        } catch (Exception e) {
            responseJson = Rapi.Response(e.getMessage(), false, data);
        }

        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
    
    /*
     add activities
     */
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/postActivitiesType")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostActivitiesType(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando PostActivitiesType...");
        JsonObject Jso = Methods.stringToJSON(data);
        try {
            if (Jso.size() > 0) {
                Object[] responseatC;
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;
                System.out.println("Authorization: " + Authorization);
                if (!Authorization.isEmpty()) {
                    Object[] Permt = AuC.VToken(Authorization);
                    if (Permt[0].equals(true)) {
                        responseatC = atC.InsertActivitiesTypeC(
                                Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
                        if (responseatC[0].equals(true)) {
                            responseJson = Rapi.Response(String.valueOf(responseatC[1]), Boolean.parseBoolean(responseatC[0].toString()), "{}");
                        } else {
                            responseJson = Rapi.Response(String.valueOf(responseatC[1]), Boolean.parseBoolean(responseatC[0].toString()), "{}");
                        }
                    } else {
                        responseJson = Rapi.Response(String.valueOf(Permt[1]), false, "{}");
                    }
                } else {
                    responseJson = Rapi.Response("Tokén vacio", true, data);
                }
            } else {
                responseJson = Rapi.Response("Información no encontrada", false, "{}");
            }
        } catch (Exception e) {
            responseJson = Rapi.Response(e.getMessage(), false, "{}");
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    @Path("/PutActivitiesType")
    public Response PutActivitiesType(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando PutActivitiesType...");
        JsonObject Jso = Methods.stringToJSON(data);
        try {
            if (Jso.size() > 0) {
                Object[] responseatC;
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;
                System.out.println("Authorization: " + Authorization);
                if (!Authorization.isEmpty()) {
                    Object[] Permt = AuC.VToken(Authorization);
                    if (Permt[0].equals(true)) {
                        responseatC = atC.UpdateActivitiesTypeC(
                                Methods.JsonToInteger(Jso.getAsJsonObject(), "idactivitiestype", 0),
                                Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "state", ""));
                        if (responseatC[0].equals(true)) {
                            responseJson = Rapi.Response(String.valueOf(responseatC[1]), Boolean.parseBoolean(responseatC[0].toString()), "{}");
                        } else {
                            responseJson = Rapi.Response(String.valueOf(responseatC[1]), Boolean.parseBoolean(responseatC[0].toString()), "{}");
                        }
                    } else {
                        responseJson = Rapi.Response(String.valueOf(Permt[1]), false, "{}");
                    }
                } else {
                    responseJson = Rapi.Response("Tokén vacio", true, data);
                }
            } else {
                responseJson = Rapi.Response("Información no encontrada", false, "{}");
            }
        } catch (Exception e) {
            responseJson = Rapi.Response(e.getMessage(), false, "{}");
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    @Path("/DeleteActivitiesType")
    public Response DeleteActivitiesType(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando DeleteActivitiesType...");
        JsonObject Jso = Methods.stringToJSON(data);
        System.out.println(responseJson);
        try {
            if (Jso.size() > 0) {
                Object[] responseatC;
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;
                System.out.println("Authorization: " + Authorization);
                if (!Authorization.isEmpty()) {
                    Object[] Permt = AuC.VToken(Authorization);
                    if (Permt[2].equals("Administrador")) {
                        if (Permt[0].equals(true)) {
                            responseatC = atC.DeleteActividadestype(
                                    Methods.JsonToInteger(Jso.getAsJsonObject(), "idactivitiestype", 0));
                            if (responseatC[0].equals(true)) {
                                responseJson = Rapi.Response(String.valueOf(responseatC[1]), Boolean.parseBoolean(responseatC[0].toString()), data);
                            } else {
                                responseJson = Rapi.Response(String.valueOf(responseatC[1]), Boolean.parseBoolean(responseatC[0].toString()), data);
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
        try {
            if (Jso.size() > 0) {
                String name = Methods.JsonToString(Jso.getAsJsonObject(), "name", "");
                String base64 = Methods.JsonToString(Jso.getAsJsonObject(), "base64", "");
                FileController fc = new FileController();
                Object[] CreateFile = fc.createfile(base64, "Activities", name);
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
