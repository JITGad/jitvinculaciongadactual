
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class testeosmain {

    public static void main(String[] args) throws UnsupportedEncodingException {
//        peu();
        new Conection().testConection();
       // String sentence = "SELECT * FROM \"public\".\"TBLUSER\"; 
        Conection con = new Conection(); 
    }
    public static void prueba1(){
        String datas = "{\"name\":\"Puchamon\",\"lastname\":\"Apell\",\"email\":\"pucha@gmail.com\",\"pass\":\"123\"}";
        NewJerseyClient nc = new NewJerseyClient();
        Response r = nc.save(datas);
        System.out.println(r.getStatus());
        System.out.println(r.readEntity(String.class));
    }
    public static void peu(){
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
