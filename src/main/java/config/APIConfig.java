package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigReader;

public class APIConfig {
    public static final String BASE_URL = "baseUrl";
    public static final String UserName = "userName";
    public static final String Password = "password";
    public static final String Port = "port";
    public static final String DB_ENDPOINT = "db.endpoint";
    public static final String DB_USER = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_DRIVER = "db.driver-class-name";
    public static final String DB_PORT = "db.port";


    public static Logger logger = LoggerFactory.getLogger(APIConfig.class);

    private static ConfigReader conf = null;

    protected APIConfig() {
        // do nothing
    }

    private static ConfigReader getProperties() throws Exception {
        // new code added starts - This ensures that the property file is only loaded once.
        if (conf == null) {
            conf = new ConfigReader();
        }
        return conf;
    }

    public static String getBaseUrl() throws Exception {
        ConfigReader properties = getProperties();
        return properties.getValue(BASE_URL);
    }

    public static String getUserName() throws Exception {
        ConfigReader properties = getProperties();
        return properties.getValue(UserName);
    }

    public static String getPassword() throws Exception {
        ConfigReader properties = getProperties();
        return properties.getValue(Password);
    }

    public static String getPort() throws Exception {
        ConfigReader properties = getProperties();
        return properties.getValue(Port);
    }

    public static String getDbEndpoint() throws Exception {
        ConfigReader properties = getProperties();
        return properties.getValue(DB_ENDPOINT);
    }

    public static String getDbUser() throws Exception {
        ConfigReader properties = getProperties();
        return properties.getValue(DB_USER);
    }

    public static String getDbPassword() throws Exception {
        ConfigReader properties = getProperties();
        return properties.getValue(DB_PASSWORD);
    }

    public static String getDbPort() throws Exception {
        ConfigReader properties = getProperties();
        return properties.getValue(DB_PORT);
    }
}
