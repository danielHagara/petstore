package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import data.classes.Order;
import data.classes.Pet;
import data.controllers.PetController;
import data.controllers.StoreController;
import data.utils.TestHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.lessThan;

public class CreateOrderTest extends TestBase {

    private StoreController store;
    private PetController petController;

    @BeforeClass
    public void initSpec() { 
        RequestSpecification request = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri(baseUrl)
            .addHeader("api_key", apiKey)
            .build();

        store = new StoreController(request);
        petController = new PetController(request); 
    }

    @Test
    public void createOrder_usingValidOrder_isSuccessfullTest() {
        Pet pet = new Pet();
        petController.createPet(pet);
        Order order = Order.builder()
            .id(TestHelper.generateRandomOrderId())
            .petId(pet.getPetId())
            .quantity(1)
            .shipDate(TestHelper.getCurrentDateTimeString())
            .status("placed")
            .complete(true)
            .build();
        
        Response response = store.createOrder(order);

        response.then().statusCode(HttpStatus.SC_OK)
        .time(lessThan(1000L));
        TestHelper.assertHeaders(response);
        Order createdOrder = store.getOrder(order.getId())
        .then().extract().body().as(Order.class);
        Assert.assertTrue(order.equals(createdOrder));
    }

    @Ignore
    @Test
    public void createOrder_usingInvalidOrder_returnsBadRequestTest() {
        // contains pet, but not a quantity
        Pet pet = new Pet();
        petController.createPet(pet);
        Order order = Order.builder()
            .id(TestHelper.generateRandomOrderId())
            .petId(pet.getPetId())
            .shipDate(TestHelper.getCurrentDateTimeString())
            .status("placed")
            .complete(true)
            .build();
        
        Response response = store.createOrder(order);

        response.then().statusCode(HttpStatus.SC_BAD_REQUEST)
        .time(lessThan(1000L));
    }

}