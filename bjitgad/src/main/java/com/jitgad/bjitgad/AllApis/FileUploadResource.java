/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jitgad.bjitgad.AllApis;

import com.jitgad.bjitgad.Controller.AuthorizationController;
import com.jitgad.bjitgad.Controller.FileController;
import com.jitgad.bjitgad.DataStaticBD.Configuration;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.jitgad.bjitgad.Utilities.ResponseCreateFile;
import com.jitgad.bjitgad.Utilities.ResponseData;
import com.jitgad.bjitgad.Utilities.ResponseValidateToken;
import com.jitgad.bjitgad.Utilities.UFile;
import com.jitgad.bjitgad.Utilities.ValidateFormat;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author jgarc
 */
@Path("fileupload")
public class FileUploadResource {

    private final AuthorizationController AuC;
    private final FileController fc;

    public FileUploadResource() {
        AuC = new AuthorizationController();
        fc = new FileController();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response UploadFile(@Context HttpHeaders headers, @Context HttpServletRequest request) {
        ResponseData responseData = new ResponseData("Ocurrio un error", false);

        try {
            Part filepart = request.getPart("file"); //file es el campo donde se enviará el archivo en el request.

            if (Configuration.DEBUG) {
                System.out.println("Ingresando putGame...");
            }
            String Authorization = headers.getHeaderString("Authorization");
            Authorization = Authorization == null ? "" : Authorization;

            if (Configuration.DEBUG) {
                System.out.println("Authorization: " + Authorization);
            }

            if (!Authorization.isEmpty()) {

                ResponseValidateToken validateToken = AuC.VToken(Authorization);

                if (validateToken.isStatus()) {
                    java.nio.file.Path rutaPadre = Paths.get(request.getServletContext().getRealPath("/")).getParent();
                    String RutaRelativa = String.join(File.separator, new String[]{"jit", "static"});

                    java.nio.file.Path RutaBaseGuardar = Paths.get(String.join(File.separator, new String[]{rutaPadre.toAbsolutePath().toString(), RutaRelativa}));
                    if (Files.notExists(RutaBaseGuardar)) {
                        Files.createDirectories(RutaBaseGuardar);
                    }
                    ValidateFormat format = new ValidateFormat(true);
                    String NombreCarpeta = "upload";
                    format.setRutaAbsoluta(String.join(File.separator, new String[]{RutaBaseGuardar.toAbsolutePath().toString(), NombreCarpeta}));
                    format.setRutaRelativa(String.join("/", new String[]{RutaRelativa.replace("\\", "/"), NombreCarpeta}));
                    if (fc.createfilebase(format.getRutaAbsoluta())) {
                        java.nio.file.Path RutaGuardar = Paths.get(format.getRutaAbsoluta());
                        if (Files.notExists(RutaGuardar)) {  //se comprueba si la ruta existe o no
                            Files.createDirectories(RutaGuardar);
                        }
                        String extension = filepart.getContentType().split("/")[1];
                        String NombreArchivo = String.join(".", new String[]{"upload" + System.currentTimeMillis(), extension});
                        String rutaArchivo = String.join(File.separator, new String[]{RutaGuardar.toAbsolutePath().toString(), NombreArchivo});

                        java.nio.file.Files.copy(
                                filepart.getInputStream(),
                                new File(rutaArchivo).toPath(),
                                StandardCopyOption.REPLACE_EXISTING);

                        responseData.setData(String.join("/", new String[]{format.getRutaRelativa(), NombreArchivo}));
                        responseData.setFlag(Boolean.TRUE);
                        responseData.setMessage("Archivo creado");
                        return Response.ok(Methods.objectToJsonString(responseData)).build();
                    }
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
}
