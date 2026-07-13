package tests;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.lessThan;

public class DeleteOrderTest extends TestBase {
 
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
    public void deleteOrder_ofExistingOrder_isSuccessfulTest() {
        // making sure pet exists
        Pet pet = new Pet();
        petController.createPet(pet);
        // making sure order exists
        Order order = Order.builder()
            .id(TestHelper.generateRandomOrderId())
            .petId(pet.getPetId())
            .quantity(1)
            .shipDate(TestHelper.getCurrentDateTimeString())
            .status("placed")
            .complete(true)
            .build();
        store.createOrder(order);

        Response response = store.deleteOrder(order.getId());

        response.then().statusCode(HttpStatus.SC_OK)
        .time(lessThan(1000L))
        .body("message", equalTo(String.valueOf(order.getId())));
        TestHelper.assertHeaders(response);           
       
        store.getOrder(order.getId())
        .then().statusCode(404);    
    }

    @Test
    public void deleteOrder_nonExistingOrder_receivesNotFoundTest() {
        int orderId = TestHelper.generateRandomOrderId();
        // making sure order doesnt exist
        store.deleteOrder(orderId);

        Response response = store.deleteOrder(orderId);

        response.then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .assertThat().body("message", equalToIgnoringCase("order not found"));
    }
    
}