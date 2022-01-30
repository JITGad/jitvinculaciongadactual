package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.GameController;
import com.jitgad.bjitgad.Controller.GameimageController;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ClaveValorModel;
import com.jitgad.bjitgad.Models.GameModel;
import com.jitgad.bjitgad.Models.GameimageModel;
import com.jitgad.bjitgad.Resources.ResponseAPI;
import com.jitgad.bjitgad.Utilities.ResponseData;
import com.jitgad.bjitgad.Utilities.ResponseDataPage;
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
@Path("game")

public class Gameresource {

    @Context
    private UriInfo context;
    private GameController gC;
    private AuthorizationController AuC;
    private ResponseAPI Rapi;
    private GameModel gameModel;
    private GameimageModel gameimageModel;
    private GameimageController giC;

    public Gameresource() {
        gC = new GameController();
        giC = new GameimageController();
        AuC = new AuthorizationController();
        Rapi = new ResponseAPI();
        gameimageModel = new GameimageModel();
    }

    /**
     * Retrieves representation of an instance of ini.CRUD
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGame() {

        ResponseData responseData = new ResponseData("Ocurrio un error", true);

        try {
            ArrayList<GameModel> data = gC.selectGame();

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

    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/getgamesbyactivities")
    @Consumes(MediaType.APPLICATION_JSON)
    // @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getgamesbyactivities(@Context HttpHeaders headers, @QueryParam("activityid") int activityid) {

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

                    ArrayList<ClaveValorModel> data = gC.selectgamesbyactivities(activityid);

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getGameAdmin")
    public Response getGameAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando getGameAdmin...");
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
                ArrayList<GameModel> data = gC.selectGamepage(page);
                Object[] Permt = AuC.VToken(Authorization);
                if (Permt[0].equals(true)) {
                    responseCountingPage = gC.CountingPageGame();
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
     * juegos por ID
     */
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/getGamebyid")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getGamebyid(@Context HttpHeaders headers, @QueryParam("idgame") int idgame) {

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

                    JsonObject data = Methods.stringToJSON(gC.selectGamebyid(idgame));

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
    @Path("/postGame")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostGame(@Context HttpHeaders headers, String data) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando PostGame...");
        }

        ResponseData responseData = new ResponseData("Ocurrio un error", false);

        gameModel = (GameModel) Methods.StringJsonToObject(data, GameModel.class);

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

                        responseData = gC.InsertGameC(gameModel);
                        
//                        if(responseData.flag){
//                                 
//                            for (GameimageModel object : gameModel.getDetalles()) {
//                                responseData = giC.InsertGameimageC(object); 
//                            }
//                            
//                            return Response.ok(Methods.objectToJsonString(responseData)).build();
//
//                        }

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
    @Path("/putGame")
    public Response PutActivitiesType(@Context HttpHeaders headers, String data) {

        if (Configuration.DEBUG) {
            System.out.println("Ingresando putGame...");
        }

        ResponseData responseData = new ResponseData("Ocurrio un error", false);

        gameModel = (GameModel) Methods.StringJsonToObject(data, GameModel.class);

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

                        responseData = gC.UpdateGameC(gameModel);

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
    @Path("/deleteGame")
    public Response DeleteGame(@Context HttpHeaders headers, String data) {
        

        if (Configuration.DEBUG) {
            System.out.println("Ingresando DeleteGame...");
        }

        ResponseData responseData = new ResponseData("Ocurrio un error", false);

        gameModel = (GameModel) Methods.StringJsonToObject(data, GameModel.class);

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

                            responseData = gC.DeleteGameC(gameModel);

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
