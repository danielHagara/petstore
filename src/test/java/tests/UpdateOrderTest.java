package tests;

import static org.hamcrest.Matchers.lessThan;

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

public class UpdateOrderTest extends TestBase {

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
    public void updateOrder_isNotAllowedTest() {
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
        store.createOrder(order);

        order.setQuantity(2);
        Response response = store.updateOrder(order);

        response.then().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
        .time(lessThan(1000L));    
    }
}
