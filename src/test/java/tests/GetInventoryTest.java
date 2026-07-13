package tests;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import data.controllers.StoreController;
import data.utils.TestHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;

public class GetInventoryTest extends TestBase {

    private StoreController store;

    @BeforeClass
    public void initSpec() { 
        RequestSpecification request = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri(baseUrl)
            .addHeader("api_key", apiKey)
            .build();

        store = new StoreController(request);
    }

    @Test
    public void getInventory_returnsInventoryTest() {        
        Response response = store.getInventory();
        
        TestHelper.assertHeaders(response);
        response.then()
        .statusCode(HttpStatus.SC_OK)
        .time(lessThan(2000L))
        .body("$", hasKey("sold"))
        .body("$", hasKey("string"))
        .body("$", hasKey("available"));
    }

}
