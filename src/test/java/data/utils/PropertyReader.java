package data.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {

    private String appConfigPath = "src/test/resources/application.properties";
    private Properties appProperties = new Properties();

    public PropertyReader() throws Exception {
        try {
            appProperties.load(new FileInputStream(appConfigPath));
        } catch(Exception e) {
            System.err.println("Could not load properties file: " + e.getMessage());
        }
    }

    public String getProperty(String key) throws Exception {
        Object obj = appProperties.get(key);
        if(obj != null)
        return ((String) obj);
        else {
            throw new Exception("Could not find Property " + key);
        }
    }
    
}
