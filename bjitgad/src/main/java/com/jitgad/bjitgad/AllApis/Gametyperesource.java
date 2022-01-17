package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.GametypeController;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Resources.ResponseAPI;
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

/**
 *
 * @author jorge
 */
@Path("gametype")
public class Gametyperesource {

    @Context
    private UriInfo context;
    private AuthorizationController AuC;
    private ResponseAPI Rapi;
    private GametypeController gtC;

    public Gametyperesource() {
        gtC = new GametypeController();
        Rapi = new ResponseAPI();
        AuC = new AuthorizationController();
    }

    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getgametypeAdmin")
    public Response getgametypeAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {
        String data = gtC.selectGametypepage(page);
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        int responseCountingPage = 0;
        try {
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            System.out.println("Authorization: " + Authorization);
            if (!Authorization.isEmpty()) {
                Object[] Permt = AuC.VToken(Authorization);
                if (Permt[0].equals(true)) {
                    responseCountingPage = gtC.CountingPageGametype();
                    if (data.equals("{}")) {
                        responseJson = Rapi.AdminResponse("Información no encontrada", responseCountingPage, false, data);
                    } else {
                        responseJson = Rapi.AdminResponse("Datos retornados correctamente", responseCountingPage, true, data);
                    }
                } else {
                    responseJson = Rapi.AdminResponse(String.valueOf(Permt[1]), responseCountingPage, false, data);
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
    @Path("/getgametypeAdminbyid")
    public Response getgametypeAdminbyid(@Context HttpHeaders headers, @QueryParam("id") int id) {
        String data = gtC.selectGametypebyid(id);
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        try {
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            System.out.println("Authorization: " + Authorization);
            if (!Authorization.isEmpty()) {
                Object[] Permt = AuC.VToken(Authorization);
                if (Permt[0].equals(true)) {
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

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/Postgametype")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postgametype(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando PostActivitiesType...");
        //  boolean insertgametype = false;
        JsonObject Jso = Methods.stringToJSON(data);
        try {
            if (Jso.size() > 0) {
                Object[] responsegtC;
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;
                System.out.println("Authorization: " + Authorization);
                Object[] Permt = AuC.VToken(Authorization);
                if (!Authorization.isEmpty()) {
                    if (Permt[0].equals(true)) {
                        responsegtC = gtC.InsertGametypeC(Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "audio_instructions", ""),
                                Boolean.parseBoolean(Methods.JsonToString(Jso.getAsJsonObject(), "state", "")),
                                Methods.JsonToString(Jso.getAsJsonObject(), "shortname", "").replaceAll("\\s+","").toLowerCase());
                        if (responsegtC[0].equals(true)) {
                            responseJson = Rapi.Response(String.valueOf(responsegtC[1]), Boolean.parseBoolean(responsegtC[0].toString()), "{}");
                        } else {
                            responseJson = Rapi.Response(String.valueOf(responsegtC[1]), Boolean.parseBoolean(responsegtC[0].toString()), "{}");
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
    @Path("/Putgametype")
    public Response Putgametype(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando Putgametype...");
        JsonObject Jso = Methods.stringToJSON(data);
        try {
            if (Jso.size() > 0) {
                Object[] responsegtC;
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;
                System.out.println("Authorization: " + Authorization);
                if (!Authorization.isEmpty()) {
                    Object[] Permt = AuC.VToken(Authorization);
                    if (Permt[0].equals(true)) {
                        responsegtC = gtC.UpdateGametypeC(
                            Methods.JsonToInteger(Jso.getAsJsonObject(), "idgametype", 0),    
                        Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "image", ""),
                        Methods.JsonToString(Jso.getAsJsonObject(), "audio_instructions", ""),
                        Boolean.parseBoolean(Methods.JsonToString(Jso.getAsJsonObject(), "state", "")),
                        Methods.JsonToString(Jso.getAsJsonObject(), "shortname", "").replaceAll("\\s+","").toLowerCase());
                        if (responsegtC[0].equals(true)) {
                            responseJson = Rapi.Response(String.valueOf(responsegtC[1]), Boolean.parseBoolean(responsegtC[0].toString()), "{}");
                        } else {
                            responseJson = Rapi.Response(String.valueOf(responsegtC[1]), Boolean.parseBoolean(responsegtC[0].toString()), "{}");
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
    @Path("/Deletegametype")
    public Response Deletegametype(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando Deletegametype...");
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
                            responseatC = gtC.DeleteGametypeC(
                                    Methods.JsonToInteger(Jso.getAsJsonObject(), "idgametype", 0));
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




}
