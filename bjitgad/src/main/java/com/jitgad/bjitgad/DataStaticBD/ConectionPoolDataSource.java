/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jitgad.bjitgad.DataStaticBD;

import java.sql.SQLException;

/**
 *
 * @author jgarc
 */
public class ConectionPoolDataSource {
    private static IConnectionPool conection;
    
    static {
        try {
            conection = Conection.create(Configuration.dburl, Configuration.dbUser, Configuration.dbPassword);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static IConnectionPool getConnection() {
        return conection;
    }
    
    private ConectionPoolDataSource(){}
}
