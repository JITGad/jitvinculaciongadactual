package com.jitgad.bjitgad.AllApis;

import com.google.gson.JsonObject;
import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.ColortypeController;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Models.ClaveValorColorModel;
import com.jitgad.bjitgad.Models.ColortypeModel;
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
@Path("colortype")
public class Colortyperesource {

    private final ColortypeController ctypeC;
    private final AuthorizationController AuC;
    private ColortypeModel colortypeModel;

    public Colortyperesource() {
        ctypeC = new ColortypeController();
        AuC = new AuthorizationController();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getcolortypeAdmin")
    public Response getColortypeAdmin(@Context HttpHeaders headers, @QueryParam("page") int page) {
        ResponseDataPage responseDataPage = new ResponseDataPage("Ocurrió un error", page, true);
        if (Configuration.DEBUG) {
            System.out.println("Ingresando a getcolortypeAdmin");
        }
        try {
            int responseCountingPage = 0;
            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;

            if (!Authorization.isEmpty()) {
                ArrayList<ColortypeModel> data = ctypeC.selectColortypepage(page);
                ResponseValidateToken validateToken = AuC.VToken(Authorization);
                if (validateToken.isStatus()) {
                    responseCountingPage = ctypeC.CountingPageColortype();
                    if (data.size() > 0) {

                        responseDataPage.setMessage("Información encontrada");
                        responseDataPage.setCountingpage(responseCountingPage);
                        responseDataPage.setData(data);
                        responseDataPage.setTotalPages(Math.round((responseCountingPage / 10) + 1));
                        return Response.ok(Methods.objectToJsonString(responseDataPage)).build();
                    }

                    responseDataPage.setMessage("Información no encontrada");
                    responseDataPage.setCountingpage(responseCountingPage);
                    responseDataPage.setData(data);
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
    @Path("/getcolortypebyid")
    public Response getColortypebyid(@Context HttpHeaders headers, @QueryParam("idcolortype") int idcolortype) {
        ResponseData responseData = new ResponseData("Ocurrio un error", true);
        if (Configuration.DEBUG) {
            System.out.println("Ingresando a getcolortypebyid " + idcolortype);
        }
        try {

            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;

            if (!Authorization.isEmpty()) {
                ResponseValidateToken validateToken = AuC.VToken(Authorization);
                if (validateToken.isStatus()) {

                    JsonObject data = Methods.stringToJSON(ctypeC.selectColortypebyid(idcolortype));

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getcolortypecv")
    public Response getcolortypecv(@Context HttpHeaders headers) {
        ResponseData responseData = new ResponseData("Ocurrio un error", true);
if (Configuration.DEBUG) {
            System.out.println("Ingresando a getcolortypecv ");
        }
        try {

            //TOKENS
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;

            if (!Authorization.isEmpty()) {
                ResponseValidateToken validateToken = AuC.VToken(Authorization);
                if (validateToken.isStatus()) {

                    ArrayList<ClaveValorColorModel> data = ctypeC.selectColortypecv();

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
    @POST
    @Path("/postColortype")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response PostColortype(@Context HttpHeaders headers, String data) {
        ResponseData responseData = new ResponseData("Ocurrio un error", false);
if (Configuration.DEBUG) {
            System.out.println("Ingresando a postColortype ");
        }
        colortypeModel
                = (ColortypeModel) Methods.StringJsonToObject(data, ColortypeModel.class);

        JsonObject Jso = Methods.stringToJSON(data);

        try {

            if (Jso.size() > 0) {
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;

                if (!Authorization.isEmpty()) {

                    ResponseValidateToken validateToken = AuC.VToken(Authorization);

                    if (validateToken.isStatus()) {

                        responseData = ctypeC.InsertColortype(colortypeModel);

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
    @Path("/putColortype")
    public Response PutColortype(@Context HttpHeaders headers, String data) {
        ResponseData responseData = new ResponseData("Ocurrio un error", false);
if (Configuration.DEBUG) {
            System.out.println("Ingresando a putColortype ");
        }
        colortypeModel
                = (ColortypeModel) Methods.StringJsonToObject(data, ColortypeModel.class);

        JsonObject Jso = Methods.stringToJSON(data);

        try {

            if (Jso.size() > 0) {
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;

                if (!Authorization.isEmpty()) {

                    ResponseValidateToken validateToken = AuC.VToken(Authorization);

                    if (validateToken.isStatus()) {

                        responseData = ctypeC.UpdateColortype(colortypeModel);

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
    @DELETE
    @Path("/deleteColortype")
    public Response DeleteActivitiesType(@Context HttpHeaders headers, String data) {
        ResponseData responseData = new ResponseData("Ocurrio un error", false);
        if (Configuration.DEBUG) {
            System.out.println("Ingresando a deleteColortype ");
        }
        colortypeModel
                = (ColortypeModel) Methods.StringJsonToObject(data, ColortypeModel.class);

        JsonObject Jso = Methods.stringToJSON(data);

        try {

            if (Jso.size() > 0) {
                //TOKENS
                String Authorization = headers.getHeaderString("Authorization");
                Authorization = Authorization == null ? "" : Authorization;

                if (!Authorization.isEmpty()) {

                    ResponseValidateToken validateToken = AuC.VToken(Authorization);

                    if (validateToken.getRol().equals("Administrador")) {

                        if (validateToken.isStatus()) {

                            responseData = ctypeC.DeleteColortype(colortypeModel);

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
