package utils;

import config.APIConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Utility class to connect and query mysql server
 */
public class DBAccess {

    protected static Logger logger = LoggerFactory.getLogger(DBAccess.class);
    private static DBAccess instance;

    private static Connection connection;

    private ConfigReader apiConfig;

    private String mySQLDatabaseIP;
    private String mySQLDatabasePort;
    private String mySQLDBUser;
    private String mySQLDBPwd;
    private String mySQLDB;
    private String databaseURL;

    private DBAccess() {
        try {

            apiConfig = new ConfigReader();

            mySQLDatabaseIP = apiConfig.getValue("mySQLDatabaseIP");
            mySQLDatabasePort = apiConfig.getValue("mySQLDatabasePort");
            mySQLDBUser = apiConfig.getValue("mySQLDBUser");
            mySQLDBPwd = apiConfig.getValue("mySQLDBPwd");
            mySQLDB = apiConfig.getValue("mySQLDB");

            logger.info("\n\nINFO: DBConnection is being constructed by THREAD="
                    + Thread.currentThread().getName() + "\n\n");
            setUpConnection();

        } catch (ClassNotFoundException ex) {
            logger.info("Error in connecting DB");
            ex.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBAccess(Map<String, String> dbDeatils) {
        try {
            mySQLDatabaseIP = dbDeatils.get("dbIp");
            mySQLDatabasePort = dbDeatils.get("dbPort");
            mySQLDBUser = dbDeatils.get("dbUser");
            mySQLDBPwd = dbDeatils.get("dbPwd");
            mySQLDB = dbDeatils.get("dbName");
            System.out
                    .println("\n\nINFO: DBConnection is being constructed by THREAD="
                            + Thread.currentThread().getName() + "\n\n");
            setUpConnection();

        } catch (ClassNotFoundException ex) {
            logger.info("Error in connecting DB");
            ex.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBAccess getInstance() {
        instance = new DBAccess();
        return instance;
    }

    public static DBAccess getInstance(Map<String, String> dbDetails) {
        instance = new DBAccess(dbDetails);
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            System.out
                    .println("\n\nINFO: DBConnection is being (re)constructed by THREAD="
                            + Thread.currentThread().getName() + "\n\n");
            try {
                getInstance().setUpConnection();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void main(String[] args) {

        System.setProperty("type", "api");
        System.setProperty("env", "test");
        HashMap<String, String> dbDeatils = new HashMap<>();
        try {
            dbDeatils.put("dbIp", APIConfig.getDbEndpoint());
            dbDeatils.put("dbPort", APIConfig.getDbPort()
            );
            dbDeatils.put("dbUser", APIConfig.getDbUser());
            dbDeatils.put("dbPwd", APIConfig.getDbPassword());
            dbDeatils.put("dbName", "user");
        } catch (Exception e) {
            e.printStackTrace();
        }

        DBAccess db = DBAccess.getInstance(dbDeatils);
        ArrayList<ArrayList<String>> data = db.getTable("Select * from user");
        for (ArrayList<String> al : data) {
            for (String s : al)
                System.out.print(":" + s);
            System.out.println("\n");
        }

        db.closeConnection();
    }

    private void setUpConnection() throws SQLException, InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        databaseURL = "jdbc:mysql://" + mySQLDatabaseIP + ":"
                + mySQLDatabasePort + '/' + mySQLDB;

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection(databaseURL, mySQLDBUser,
                mySQLDBPwd);
        connection.setAutoCommit(true);
    }

    public void closeConnection() {
        try {
            logger.info("\n\nINFO: DBConnection being closed by THREAD="
                    + Thread.currentThread().getName() + "\n\n");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crude method. Returns raw access to ResultSet
     *
     * @param query
     * @return
     */
    public ResultSet executeSQLQuery(String query) {
        ResultSet resultSet = null;

        try {

            Statement statement = getConnection().createStatement();
            resultSet = statement.executeQuery(query);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resultSet;
    }

    /**
     * This returns a more structured data as List of <K,V> pairs where
     * K=columnName & V=value
     *
     * @param rs
     * @return List
     */
    public List<HashMap<Object, Object>> getStructuredData(ResultSet rs) {
        HashMap<Object, Object> row;
        List<HashMap<Object, Object>> completeData = new ArrayList<HashMap<Object, Object>>();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            while (rs.next()) {
                row = new HashMap<Object, Object>();
                for (int i = 1; i <= columns; i++) {
                    row.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                completeData.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return completeData;
    }

    public String getvalue(String query) {
        Statement statement;
        String result = null;
        try {
            statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            result = rs.getString(1);
            logger.info("query excuted:" + query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return result;

    }

    public ArrayList<String> getList(String query) {
        Statement statement;
        ArrayList<String> result = new ArrayList<String>();
        try {
            statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                result.add(rs.getString(1));
            }
            logger.info("query excuted:" + query);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        closeConnection();
        return result;
    }

    public ArrayList<ArrayList<String>> getTable(String query) {
        Statement statement;
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        try {
            statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<String>();
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i + 1));

                }
                result.add(row);
            }
            logger.info("query excuted:" + query);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        closeConnection();
        return result;
    }
}


