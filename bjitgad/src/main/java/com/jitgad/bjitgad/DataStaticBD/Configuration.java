
package com.jitgad.bjitgad.DataStaticBD;

/**
 *
 * @author Usuario
 */
public class Configuration {
//    public static String DATABASENAME = "jit";
//    public static String DATABASEUSER = "@dminvincul@cion2022";
//    public static String DATABASEPASSWORD = "P@ssw0rd@admin2022";
 //   public static String DATABASEURL = "jdbc:postgresql://10.1.1.89:5432/jit";
//    public static String DATABASENAME = "jit";
    public static String DATABASEUSER = "jit";
    public static String DATABASEPASSWORD = "jit_p@ssword";
    public static String DATABASEURL = "jdbc:postgresql://10.1.1.89:5432/jit";
    
    public static String JWTKEY = "12345GFEDCBA$.$123Barcelona";
    //public static String IPDOMINIOSERVIDOR= "http://192.241.152.203:8080/bjitgad/";
    //public static String IPDOMINIOSERVIDOR= "http://fyc.uteq.edu.ec:8080/bjitgad/";
    public static String IPDOMINIOSERVIDOR= "https://fyc.uteq.edu.ec:/bjitgad/";
    public static boolean DEBUG = false;
    
    
    /**
     * Database Connection Configuration global variables.
     */
    
    /**
     * Minimum number of connections created at the start of the project.
     */
    public static final int INITIAL_POOL_SIZE = 1;
    /**
     * Maximum number of users connected at the same time making data requests to the database.
     */
    public static final int MAX_POOL_SIZE = 20;
    
    /**
     * Maximum waiting time to verify if the connection is valid.
     */
    
    public static final int MAX_TIMEOUT = 5;
    
}
