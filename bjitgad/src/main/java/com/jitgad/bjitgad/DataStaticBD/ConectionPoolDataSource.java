
package com.jitgad.bjitgad.DataStaticBD;

import java.sql.SQLException;

/**
 * 
 * @author jorge
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
