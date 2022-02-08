
package com.jitgad.bjitgad.DataStaticBD;

/**
 * 
 * @author jorge
 */
public class ConectionPoolDataSource {
    
    private static ConectionPool conection;
    
    static {
        try {
            conection = ConectionPool.create(Configuration.DATABASEURL, Configuration.DATABASEUSER, Configuration.DATABASEPASSWORD);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static ConectionPool getConnection() {
        return conection;
    }
    
    public ConectionPoolDataSource(){}
}
