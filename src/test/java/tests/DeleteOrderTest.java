package tests;

import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import data.classes.Order;
import data.classes.Pet;
import data.utils.TestHelper;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.lessThan;

public class DeleteOrderTest extends TestBase {

    @Test
    public void deleteOrder_Test() {
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
    public void deleteNonExistingOrder_receivesNotFoundTest() {
        int orderId = TestHelper.generateRandomOrderId();
        // making sure order doesnt exist
        store.deleteOrder(orderId);

        Response response = store.deleteOrder(orderId);

        response.then()
        .log().status()
        .log().body()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .assertThat().body("message", equalToIgnoringCase("order not found"));
    }
    
}