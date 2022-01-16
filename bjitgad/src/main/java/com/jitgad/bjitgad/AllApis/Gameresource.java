package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.GameController;
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
@Path("game")

public class Gameresource {

    @Context
    private UriInfo context;
    // private GameModel gameModel;
    private GameController gC;
    private AuthorizationController AuC;
    private ResponseAPI Rapi;

    public Gameresource() {
        // gameModel = new GameModel();
        gC = new GameController();
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
    public Response getGame() {
        String data = gC.selectGame();
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
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/getgamesbyactivities")
    @Consumes(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getgamesbyactivities(@Context HttpHeaders headers, @QueryParam("activityid") int activityid) {
        String data = gC.selectgamesbyactivities(activityid);
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getGameAdmin")
    public Response getGameAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {
        String responseJson = gC.selectGamepage(page);
        int responseCountingPage = 0;
        try {
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;
            System.out.println("Authorization: " + Authorization);
            if (!Authorization.isEmpty()) {
                Object[] Permt = AuC.VToken(Authorization);
                if (Permt[0].equals(true)) {
                    responseCountingPage = gC.CountingPageGame();
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
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    /**
     * juegos por ID
     */
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/getGamebyid")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getGamebyid(@Context HttpHeaders headers, @QueryParam("idgame") int idgame) {
        String data = gC.selectGamebyid(idgame);
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
    @Path("/postGame")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostGame(@Context HttpHeaders headers, String data) {
        String responseJson = "{\"status\":\"poken:" + data + "\"}";
        System.out.println("Ingresando postGame...");
        JsonObject Jso = Methods.stringToJSON(data);
        try {
            if (Jso.size() > 0) {
                Object[] responsegC;
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;
                System.out.println("Authorization: " + Authorization);
                if (!Authorization.isEmpty()) {
                    Object[] Permt = AuC.VToken(Authorization);
                    if (Permt[0].equals(true)) {
                        responsegC = gC.InsertGameC(
                                Methods.JsonToString(Jso.getAsJsonObject(), "idactivitiestype", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "idgametype", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                                Boolean.parseBoolean(Methods.JsonToString(Jso.getAsJsonObject(), "state", "")),
                                Methods.JsonToInteger(Jso.getAsJsonObject(), "level", 0));
                        if (responsegC[0].equals(true)) {
                            responseJson = Rapi.Response(String.valueOf(responsegC[1]), Boolean.parseBoolean(responsegC[0].toString()), "{}");
                        } else {
                            responseJson = Rapi.Response(String.valueOf(responsegC[1]), Boolean.parseBoolean(responsegC[0].toString()), "{}");
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
    @Path("/putGame")
    public Response PutActivitiesType(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando PutActivitiesType...");
        JsonObject Jso = Methods.stringToJSON(data);
        try {
            if (Jso.size() > 0) {
                Object[] responsegC;
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;
                System.out.println("Authorization: " + Authorization);
                if (!Authorization.isEmpty()) {
                    Object[] Permt = AuC.VToken(Authorization);
                    if (Permt[0].equals(true)) {
                        responsegC = gC.UpdateGameC(
                                Methods.JsonToInteger(Jso.getAsJsonObject(), "idgame", 0),
                                Methods.JsonToString(Jso.getAsJsonObject(), "idactivitiestype", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "idgametype", ""),
                                Methods.JsonToString(Jso.getAsJsonObject(), "name", ""),
                                Boolean.parseBoolean(Methods.JsonToString(Jso.getAsJsonObject(), "state", "")),
                                Methods.JsonToInteger(Jso.getAsJsonObject(), "level", 0));
                                if (responsegC[0].equals(true)) {
                                    responseJson = Rapi.Response(String.valueOf(responsegC[1]), Boolean.parseBoolean(responsegC[0].toString()), "{}");
                                } else {
                                    responseJson = Rapi.Response(String.valueOf(responsegC[1]), Boolean.parseBoolean(responsegC[0].toString()), "{}");
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
    @Path("/deleteGame")
    public Response DeleteGame(@Context HttpHeaders headers, String data) {
        String responseJson = Rapi.Response("Ocurrió un error", false, data);
        System.out.println("Ingresando deleteGame...");
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
                            responseatC = gC.DeleteGameC(
                                 Methods.JsonToInteger(Jso.getAsJsonObject(), "idgame", 0));
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
