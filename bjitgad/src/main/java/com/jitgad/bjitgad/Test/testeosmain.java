package com.jitgad.bjitgad.Test;

/**
 *
 * @author jorge
 */
import com.jitgad.bjitgad.ApisClient.NewJerseyClient;
import com.jitgad.bjitgad.DAO.GameDAO;
import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import com.jitgad.bjitgad.Utilities.UniqueName;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class testeosmain {

    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
        ConectionPoolDataSource.getConnection().testConection();
//        UniqueName un = new UniqueName();
//        System.out.println(un.nunique());
         GameDAO gd = new GameDAO();
         System.out.println(gd.CountingPageGame());
         
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
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("misapis");
        Response r = webTarget.path("add").request(MediaType.APPLICATION_JSON).post(Entity.entity(datas, MediaType.APPLICATION_JSON), Response.class);

        System.out.println(r.getStatus());
        System.out.println(r.readEntity(String.class));
    }
}
