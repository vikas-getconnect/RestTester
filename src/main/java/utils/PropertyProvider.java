package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

public class PropertyProvider {
    protected static Logger logger = LoggerFactory.getLogger(PropertyProvider.class);
    private Properties dictionary;

    public PropertyProvider(String fileName) {
        dictionary = new Properties();
        try {
            File initialFile = new File(fileName);
            InputStream in = new FileInputStream(initialFile);
            if (in == null)
                throw new IllegalArgumentException("file not found!");
            dictionary.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getProperty(String key) {
        return dictionary.getProperty(key);
    }

    public void setProperty(String key, String value) {
        dictionary.setProperty(key, value);
    }
}
