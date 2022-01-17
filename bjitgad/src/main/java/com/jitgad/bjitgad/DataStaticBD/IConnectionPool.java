package com.jitgad.bjitgad.DataStaticBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jgarc
 */
public interface IConnectionPool {
    Connection getConnection() throws SQLException;
    
    boolean releaseConnection(Connection connection);
    
    List<Connection> getConnectionPool();
    
    int getSize();
    
    String getUrl();
    
    String getUser();

    String getPassword();
    
    void shutdown() throws SQLException;
}
