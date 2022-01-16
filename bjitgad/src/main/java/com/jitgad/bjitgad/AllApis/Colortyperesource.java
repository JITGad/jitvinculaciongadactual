package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.ColortypeController;
import com.jitgad.bjitgad.Controller.UserController;
import com.jitgad.bjitgad.DAO.ColortypeDAO;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ColortypeModel;
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
@Path("colortype")
public class Colortyperesource {

    @Context
    private UriInfo context;
    private ColortypeController ctypeC;
    private AuthorizationController AuC;
    private ResponseAPI Rapi;

    public Colortyperesource() {
        ctypeC = new ColortypeController();
        AuC = new AuthorizationController();
        Rapi = new ResponseAPI();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getcolortypeAdmin")
    public Response getColortypeAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {
        String data = ctypeC.selectColortypepage(page);
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
                    responseCountingPage = ctypeC.CountingPageColortype();
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
            responseJson = Rapi.AdminResponse(e.getMessage(), responseCountingPage, false, data);
        }
        return Response.ok(responseJson)
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with, Access-Control-Allow-Origin")
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getcolortypebyid")
    public Response getColortypebyid(@Context HttpHeaders headers, @QueryParam("idcolortype") int idcolortype) {
        String data = ctypeC.selectColortypebyid(idcolortype);
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
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with, Access-Control-Allow-Origin")
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/postColortype")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostColortype(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando postColortype...");
        JsonObject Jso = Methods.stringToJSON(data);
        try {
            if (Jso.size() > 0) {
                Object[] responsectype;
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;
                System.out.println("Authorization: " + Authorization);
                if (!Authorization.isEmpty()) {
                    Object[] Permt = AuC.VToken(Authorization);
                    if (Permt[0].equals(true)) {
                        responsectype = ctypeC.InsertColortype(
                                Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "rgb", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "html", ""),
                                Boolean.parseBoolean(Methods.JsonToString(Jso.getAsJsonObject(), "state", "")));

                        if (responsectype[0].equals(true)) {
                            responseJson = Rapi.Response(String.valueOf(responsectype[1]), Boolean.parseBoolean(responsectype[0].toString()), "{}");
                        } else {
                            responseJson = Rapi.Response(String.valueOf(responsectype[1]), Boolean.parseBoolean(responsectype[0].toString()), "{}");
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
    @Path("/putColortype")
    public Response PutColortype(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando putColortype...");
        JsonObject Jso = Methods.stringToJSON(data);
        try {
            if (Jso.size() > 0) {
                Object[] responsectype;
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;
                System.out.println("Authorization: " + Authorization);
                if (!Authorization.isEmpty()) {
                    Object[] Permt = AuC.VToken(Authorization);
                    if (Permt[0].equals(true)) {
                        responsectype = ctypeC.UpdateColortype(
                            Methods.JsonToInteger(Jso.getAsJsonObject(), "idcolortype", 0),
                            Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                            Methods.JsonToString(Jso.getAsJsonObject(), "rgb", ""),
                            Methods.JsonToString(Jso.getAsJsonObject(), "html", ""),
                            Boolean.parseBoolean(Methods.JsonToString(Jso.getAsJsonObject(), "state", "")));
                        if (responsectype[0].equals(true)) {
                            responseJson = Rapi.Response(String.valueOf(responsectype[1]), Boolean.parseBoolean(responsectype[0].toString()), "{}");
                        } else {
                            responseJson = Rapi.Response(String.valueOf(responsectype[1]), Boolean.parseBoolean(responsectype[0].toString()), "{}");
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
    @Path("/deleteColortype")
    public Response DeleteActivitiesType(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando deleteColortype...");
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
                            responseatC = ctypeC.DeleteColortype(
                                    Methods.JsonToInteger(Jso.getAsJsonObject(), "idcolortype", 0));
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
