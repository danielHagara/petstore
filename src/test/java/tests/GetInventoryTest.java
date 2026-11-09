package tests;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import data.controllers.StoreController;
import data.utils.TestHelper;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;

public class GetInventoryTest extends TestBase {

    private StoreController store;

    @BeforeClass
    public void initSpec() { 
        var request = requestSpec(true);

        store = new StoreController(request);
    }

    @Test
    public void getInventory_returnsInventoryTest() {        
        Response response = store.getInventory();
        
        TestHelper.assertHeaders(response);
        response.then()
        .statusCode(HttpStatus.SC_OK)
        .body("$", hasKey("sold"))
        .body("$", hasKey("string"))
        .body("$", hasKey("available"));
    }

}
