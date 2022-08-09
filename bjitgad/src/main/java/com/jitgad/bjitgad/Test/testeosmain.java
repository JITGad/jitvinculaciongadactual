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
         GameDAO gd = new GameDAO();
         System.out.println(gd.CountingPageGame());
    }
}
