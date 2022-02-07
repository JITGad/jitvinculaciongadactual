/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jitgad.bjitgad.DataStaticBD;

import com.jitgad.bjitgad.Utilities.ReflectToClass;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * In this class, the necessary methods for connecting, obtaining and sending
 * data to the database are carried out.
 */
public class ConectionPool implements IConnectionPool {

    private final String user;
    private final String password;
    private final String url;

    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();

    Connection conex;
    
    /**
     * Receives a query and saves it in a table
     *
     * @param sentecy This String variable contains the query.
     * @return Returns a table with the data loaded from the query
     * @throws java.sql.SQLException
     */
    public DefaultTableModel returnRecord(String sentecy) throws SQLException {
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
            throw  exc;
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
        boolean test = true;
        try {
            conex = getConnection();
            test = releaseConnection(conex);
        } catch (SQLException ex) {
            System.out.println("error test conection:" + ex.getMessage());
        }
        System.out.println("test:" + test);
        return test;
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
     *Function mapping data to connection 
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
        usedConnections.forEach(this::releaseConnection);
        for (Connection c : connectionPool) {
            c.close();
        }
        connectionPool.clear();
    }

    /**
     *
     * @return @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        System.out.println("Conexiones actuales: " + connectionPool.size() + "libres y " + usedConnections.size() + " en uso" );
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < Configuration.MAX_POOL_SIZE) {
                connectionPool.add(createConnection(url, user, password));
            } else {
                throw new RuntimeException(
                        "Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);

        if (!connection.isValid(Configuration.MAX_TIMEOUT)) {
            connection.close();
         //   connection = createConnection(url, user, password);
           connection = getConnection();
        }

        usedConnections.add(connection);
        return connection;
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        System.out.println("Liberando conexion");
        connectionPool.add(connection);
        return usedConnections.remove(connection);
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
            String password) throws SQLException, ClassNotFoundException {
        System.out.println("Creando conexiones");
        Class.forName("org.postgresql.Driver");
        
        List<Connection> pool = new ArrayList<>(Configuration.INITIAL_POOL_SIZE);
        for (int i = 0; i < Configuration.INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        System.out.println("Conexiones creadas" + pool.size());
        return new ConectionPool(url, user, password, pool);
    }

    private ConectionPool(String url, String user, String password, List<Connection> connectionPool){        
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Connection> getConnectionPool() {
        return connectionPool;
    }

}
