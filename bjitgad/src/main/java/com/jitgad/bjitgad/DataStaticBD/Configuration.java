
package com.jitgad.bjitgad.DataStaticBD;

/**
 *
 * @author Usuario
 */
public class Configuration {
    public static String DATABASENAME = "dfc5nss9nj0c2e";
    public static String DATABASEUSER = "fjdmwraxdzfdqi";
    public static String DATABASEPASSWORD = "e2519d7aa881ecf70a6228c203e777fd403925bac01379cb5a95a1ee44883f78";
    public static String DATABASEURL = "jdbc:postgresql://ec2-34-195-69-118.compute-1.amazonaws.com:5432/dfc5nss9nj0c2e";
    public static String JWTKEY = "12345GFEDCBA$.$123Barcelona";
    public static String IPDOMINIOSERVIDOR= "http://192.241.152.203:8080/bjitgad/";
    public static boolean DEBUG = true;
    
    
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
