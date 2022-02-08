
package com.jitgad.bjitgad.DataStaticBD;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author jorge
 */
public class ConectionPoolDataSource {
    
    private static ConectionPool conection;
    
    static {
        try {
            conection = ConectionPool.create(Configuration.dburl, Configuration.dbUser, Configuration.dbPassword);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static ConectionPool getConnection() {
        return conection;
    }
    
    public ConectionPoolDataSource(){}
}
