package tests;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import data.classes.Order;
import data.classes.Pet;
import data.controllers.PetController;
import data.controllers.StoreController;
import data.utils.TestHelper;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalToIgnoringCase;

public class GetOrderTest extends TestBase {
    
    private StoreController store;
    private PetController petController;

    @BeforeClass
    public void initSpec() { 
        var request = requestSpec(true);

        store = new StoreController(request);
        petController = new PetController(request); 
    }
    
    @Test
    public void getOrder_ofExistingOrder_isSuccessfulTest() {
        // making sure pet exists
        Pet pet = new Pet();
        pet.setId(TestHelper.generatePetId());
        petController.createPet(pet).then().statusCode(HttpStatus.SC_OK);
        // making sure order exists
        Order order = Order.builder()
            .id(TestHelper.generateRandomOrderId())
            .petId(pet.getId())
            .quantity(1)
            .shipDate(TestHelper.getCurrentDateTimeString())
            .status("placed")
            .complete(true)
            .build();
        store.createOrder(order).then().statusCode(HttpStatus.SC_OK);

        Response response = store.getOrder(order.getId());

        response.then()
        .statusCode(HttpStatus.SC_OK);
        TestHelper.assertHeaders(response);
        Order createdOrder = response.then()
        .extract().body().as(Order.class);
        Assert.assertTrue(order.equals(createdOrder)); 
    }

    @Test
    public void getOrder_ofNonExistingOrder_returnsNotFoundTest() {
        int orderId = TestHelper.generateRandomOrderId();
        Response response = store.getOrder(orderId);
        
        response.then()
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .assertThat().body("message", equalToIgnoringCase("order not found"));
    }
}
