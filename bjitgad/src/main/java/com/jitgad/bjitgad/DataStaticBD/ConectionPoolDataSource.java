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
    
    private static ConectionPool conection;
    
    static {
        try {
            conection = ConectionPool.create(Configuration.dburl, Configuration.dbUser, Configuration.dbPassword);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static ConectionPool getConnection() {
        return conection;
    }
    
    private ConectionPoolDataSource(){}
}
