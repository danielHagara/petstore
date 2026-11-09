package data.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static final String APP_CONFIG_PATH = "/application.properties";
    private final Properties appProperties = new Properties();

    public PropertyReader() {
        try (InputStream propertiesStream = PropertyReader.class.getResourceAsStream(APP_CONFIG_PATH)) {
            if (propertiesStream == null) {
                throw new IllegalStateException("Could not find " + APP_CONFIG_PATH);
            }
            appProperties.load(propertiesStream);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load " + APP_CONFIG_PATH, e);
        }
    }

    public String getProperty(String key) {
        String value = appProperties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Could not find property: " + key);
        }
        return value.trim();
    }
    
}
