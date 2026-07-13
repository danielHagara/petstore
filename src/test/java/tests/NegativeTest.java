package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import data.classes.Order;
import data.controllers.StoreController;
import data.utils.TestHelper;
import io.restassured.response.Response;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.lessThan;

public class NegativeTest extends TestBase {
    
    private StoreController store;

    @BeforeClass
    public void initSpec() { 
        RequestSpecification unauthorizedSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri(baseUrl)
            .build();
        store = new StoreController(unauthorizedSpec);  
    }

    @Ignore
    @Test
    public void createOrder_withoutAuthorizationHeader_returnsUnauthorizedTest() {
        Response response = store.createOrder(new Order());

        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED)
        .time(lessThan(1000L));
    }

    @Ignore
    @Test
    public void deleteOrderTest_withoutAuthorizationHeader_returnsUnauthorizedTest() {
        Response response = store.deleteOrder(TestHelper.generateRandomOrderId());

        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED)
        .time(lessThan(1000L));        
    }

}
