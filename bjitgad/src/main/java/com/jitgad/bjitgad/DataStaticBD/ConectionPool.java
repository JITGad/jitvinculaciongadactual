/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jitgad.bjitgad.DataStaticBD;

import com.jitgad.bjitgad.Utilities.ReflectToClass;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 *
 * In this class, the necessary methods for connecting, obtaining and sending
 * data to the database are carried out.
 */
public class ConectionPool implements IConnectionPool {

    private final String user;
    private final String password;
    private final String url;

    private final DataSource datasource;

    /**
     * Receives a query and saves it in a table
     *
     * @param sentecy This String variable contains the query.
     * @return Returns a table with the data loaded from the query
     * @throws java.sql.SQLException
     */
    public DefaultTableModel returnRecord(String sentecy) throws SQLException {
        Connection conex = null;
        try {
            conex = getConnection();
            try (Statement st = conex.createStatement()) {
                try (ResultSet result = st.executeQuery(sentecy)) {
                    DefaultTableModel dataModel = new DefaultTableModel();
                    ResultSetMetaData rsmd = result.getMetaData();
                    int n = rsmd.getColumnCount();
                    for (int i = 1; i <= n; i++) {
                        dataModel.addColumn(rsmd.getColumnName(i));
                    }
                    String[] row = new String[n];
                    while (result.next()) {
                        for (int i = 0; i < n; i++) {
                            row[i] = (result.getString(rsmd.getColumnName(i + 1)) == null) ? "" : result.getString(rsmd.getColumnName(i + 1));
                        }
                        dataModel.addRow(row);
                    }
                    return dataModel;
                }
            }
        } catch (SQLException exc) {
            throw exc;
        } finally {
            releaseConnection(conex);
        }
    }

    /**
     * This method receives a query from a function.
     *
     * @param sentecy This String variable, contains a query of a function.
     * @return Return a Boolean.
     * @throws java.sql.SQLException
     */
    public boolean modifyBD(String sentecy) throws SQLException {
        Connection conex = null;
        try {
            conex = getConnection();
            try (Statement st = conex.createStatement()) {
                st.execute(sentecy);
                return true;
            }
        } catch (SQLException exc) {
            throw exc;
        } finally {
            releaseConnection(conex);
        }
    }

    /**
     * Method to run an update on the database.
     *
     * @param sentecy This String variable, contains a query of a function.
     * @return an integer, amount of updates.
     * @throws java.sql.SQLException
     */
    public int updateDB(String sentecy) throws SQLException {
        int counts = 0;
        Connection conex = null;
        try {
            conex = getConnection();
            try (Statement st = conex.createStatement()) {
                counts = st.executeUpdate(sentecy);
            }
        } catch (SQLException exc) {
            throw exc;
        } finally {
            releaseConnection(conex);
        }
        return counts;
    }

    /**
     * Execute any sentence in the database.
     *
     * @param sentecy this variable contains the sentence that will be executed
     * in the database
     * @return the value obtained when the sentence is executed in the database.
     * @throws java.sql.SQLException
     */
    public String fillString(String sentecy) throws SQLException {
        String a = "";
        Connection conex = null;
        try {
            conex = getConnection();
            try (Statement st = conex.createStatement()) {
                try (ResultSet result = st.executeQuery(sentecy)) {
                    while (result.next()) {
                        a = result.getString(1);
                    }
                }
            }
        } catch (SQLException exc) {
            System.out.println("Error fill string:" + exc.getMessage());
            throw exc;
        } finally {
            releaseConnection(conex);
        }
        return a;
    }

    /**
     * Get the following ID
     *
     * @param sentecy This String variable, contains a query of a function.
     * @return a string, with the following identifier.
     * @throws java.sql.SQLException
     */
    public String getNextID(String sentecy) throws SQLException {
        String a = "-1";
        Connection conex = null;
        try {
            conex = getConnection();
            try (Statement st = conex.createStatement()) {
                try (ResultSet result = st.executeQuery(sentecy)) {
                    while (result.next()) {
                        a = result.getString(1) + 1;
                    }
                    int numer;
                    try {
                        numer = Integer.parseInt(a);
                    } catch (NumberFormatException e) {
                        numer = 1;
                    }
                    a = numer + "";
                }
            }
        } catch (SQLException exc) {
            System.out.println("No next id:" + exc.getMessage());
            a = "1";
            throw exc;
        } finally {
            releaseConnection(conex);
        }
        return a;
    }

    /**
     * Obtain data and store it in a json
     *
     * @param sentency This String variable, contains a query of a function.
     * @return a string, containing json.
     * @throws java.sql.SQLException
     */
    public String getRecordsInJson(String sentency) throws SQLException {
        String resul = "[";
        DefaultTableModel table = returnRecord(sentency);
        if (table != null) {
            int columCount = table.getColumnCount();
            for (int row = 0; row < table.getRowCount(); row++) {
                String line = "";
                for (int colum = 0; colum < columCount; colum++) {
                    line += "\"" + table.getColumnName(colum).trim() + "\":\"" + table.getValueAt(row, colum).toString().trim() + "\"";
                    if (colum < columCount - 1) {
                        line += ",";
                    }
                }
                if (line.length() > 0) {
                    resul += "{" + line + "}";
                    if (row < table.getRowCount() - 1) {
                        resul += ",";
                    }
                }
            }
            resul += "]";
        } else {
            resul = "[]";
        }
        return resul;
    }

    /**
     * Test the connection to the database.
     *
     * @return a Boolean
     */
    public boolean testConection() {
        return datasource.isTestOnConnect();
    }

    /**
     * This method cast query database in object java
     *
     * @param <T> type object
     * @param sql is query
     * @param obj cast resultset
     * @param structure 0 = kamelycasestructure, 1 dabaseestructure
     * @return ArrayList object
     * @throws java.lang.Exception
     */
    public <T> ArrayList<T> getObjectDB(String sql, Class<T> obj, int structure) throws Exception {
        ArrayList<T> datos = new ArrayList();
        Connection conex = null;
        try {
            conex = getConnection();
            try (Statement stm = conex.createStatement()) {
                try (ResultSet rs = stm.executeQuery(sql)) {
                    datos = ReflectToClass.putResult(rs, obj, structure);
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            releaseConnection(conex);
        }
        return datos;
    }

    /**
     * Function mapping data to connection
     *
     * @param <T> Is Class Mapping data result
     * @param sql Sql query to execute db
     * @param obj Is Class Mapping data result
     * @param structure structure de format data
     * @param conex conexion opened
     * @return list data return T
     * @throws Exception
     */
    public <T> ArrayList<T> getObjectDBCon(String sql, Class<T> obj, int structure, Connection conex) throws Exception {
        ArrayList<T> datos = new ArrayList();
        try {
            try (Statement stm = conex.createStatement()) {
                try (ResultSet rs = stm.executeQuery(sql)) {
                    datos = ReflectToClass.putResult(rs, obj, structure);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return datos;
    }

    @Override
    public void shutdown() throws SQLException {
        datasource.close(true);
        datasource.purge();
    }

    /**
     *
     * @return @throws SQLException
     */
    @Override
    public synchronized Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    @Override
    public int getSize() {
        return datasource.getSize();
    }

    @Override
    public void releaseConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public static ConectionPool create(
            String url, String user,
            String password) throws Exception {

        PoolProperties p = new PoolProperties();
        p.setDriverClassName("org.postgresql.Driver");
        p.setUrl(url);
        p.setUsername(user);
        p.setPassword(password);
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(1);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);

        return new ConectionPool(url, user, password, datasource);
    }

    private ConectionPool(String url, String user, String password, DataSource source) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.datasource = source;
    }

    @Override
    public DataSource getConnectionPool() {
        return datasource;
    }

}
