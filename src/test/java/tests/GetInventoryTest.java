package tests;

import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import data.utils.TestHelper;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;

public class GetInventoryTest extends TestBase {
    
    @Test
    public void getInventoryTest() {        
        Response response = store.getInventory();
        
        TestHelper.assertHeaders(response);
        response.then()
        .statusCode(HttpStatus.SC_OK)
        .time(lessThan(2100L))
        .body("$", hasKey("sold"))
        .body("$", hasKey("string"))
        .body("$", hasKey("available"))
        .body("$", hasKey("peric"));
    }

}
