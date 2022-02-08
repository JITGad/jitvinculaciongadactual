/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jitgad.bjitgad.Resources;

import com.jitgad.bjitgad.DataStaticBD.ConectionPoolDataSource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jgarc
 */
@WebListener
public class MyAppServletContextListener implements ServletContextListener{

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Server context destroyed");
        try {
            ConectionPoolDataSource.getConnection().shutdown();
        } catch (SQLException ex) {
            Logger.getLogger(MyAppServletContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Server context initialized");
        ConectionPoolDataSource.getConnection();
    }
    
}
