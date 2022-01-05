package com.jitgad.bjitgad.Test;

/**
 *
 * @author jorge
 */
import com.jitgad.bjitgad.DataStaticBD.Conection;
import com.jitgad.bjitgad.ApisClient.NewJerseyClient;
import com.jitgad.bjitgad.DataStaticBD.DataBd;
import com.jitgad.bjitgad.DataStaticBD.Methods;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jitgad.bjitgad.Controller.FileController;
import com.jitgad.bjitgad.Utilities.CropImage;
import com.jitgad.bjitgad.Utilities.UFile;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class testeosmain {

    public static void main(String[] args) throws UnsupportedEncodingException {
        new Conection().testConection();
        Conection con = new Conection();

       String ruta = "C:/Users/jorge/Downloads/WhatsApp Image 2022-01-03 at 16.48.07.jpeg";
       FileController fc = new FileController();
       fc.createfile(ruta);

    }

    public static void prueba1() {
        String datas = "{\"name\":\"Puchamon\",\"lastname\":\"Apell\",\"email\":\"pucha@gmail.com\",\"pass\":\"123\"}";
        NewJerseyClient nc = new NewJerseyClient();
        Response r = nc.save(datas);
        System.out.println(r.getStatus());
        System.out.println(r.readEntity(String.class));
    }

    public static void peu() {
        WebTarget webTarget;
        Client client;
        String BASE_URI = "http://localhost:8080/restAPI/webresources";
        String datas = "{\"name\":\"Puchamon\",\"lastname\":\"Apell\",\"email\":\"pucha@gmail.com\",\"pass\":\"123\"}";
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("misapis");
        Response r = webTarget.path("add").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(datas, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);

        System.out.println(r.getStatus());
        System.out.println(r.readEntity(String.class));
    }
}
