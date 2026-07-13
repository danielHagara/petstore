package tests;

import org.testng.util.Strings;

import data.controllers.PetController;
import data.controllers.StoreController;
import data.utils.PropertyReader;

public abstract class TestBase {

    protected PropertyReader propertyReader;
    protected String baseUrl = System.getProperty("baseUrl");
    protected String apiKey = System.getProperty("apiKey");
    protected StoreController store;
    protected PetController petController;

    public TestBase() {
        if (Strings.isNullOrEmpty(baseUrl)) {
            baseUrl = getFileProperty("baseUrl");
        }

        if (Strings.isNullOrEmpty(apiKey)) {
            apiKey = getFileProperty("apiKey");
        }

        store = new StoreController(baseUrl, apiKey);
        petController = new PetController(baseUrl, apiKey);
    }

    private String getFileProperty(String key) {
        try {
            if (propertyReader == null) {
                propertyReader = new PropertyReader();
            }
            return propertyReader.getProperty(key);
        } catch (Exception e) {
            System.err.println("Could not read from properties file: " + e.getMessage());
            return null;
        }
    }
    
}
