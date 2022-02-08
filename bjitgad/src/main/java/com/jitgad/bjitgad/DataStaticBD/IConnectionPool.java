package com.jitgad.bjitgad.DataStaticBD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.tomcat.jdbc.pool.DataSource;

/**
 *
 * @author jgarc
 */
public interface IConnectionPool {
    Connection getConnection() throws SQLException;
    
    void releaseConnection(Connection connection) throws SQLException;
    
    DataSource getConnectionPool();
    
    int getSize();
    
    String getUrl();
    
    String getUser();

    String getPassword();
    
    void shutdown() throws SQLException;
}
