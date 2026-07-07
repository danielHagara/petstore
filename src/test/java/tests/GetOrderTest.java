package tests;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import data.classes.Order;
import data.classes.Pet;
import data.utils.TestHelper;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.lessThan;

public class GetOrderTest extends TestBase {
    
    @Test
    public void getOrder_orderFoundTest() {
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

        Response response = store.getOrder(order.getId());

        response.then()
        .statusCode(HttpStatus.SC_OK)
        .time(lessThan(1000L));
        TestHelper.assertHeaders(response);
        Order createdOrder = response.then()
        .extract().body().as(Order.class);
        Assert.assertTrue(order.equals(createdOrder)); 
    }

    @Test
    public void getOrder_orderNotFoundTest() {
        int orderId = TestHelper.generateRandomOrderId();
        // making sure order doesnt exist
        store.deleteOrder(orderId);

        Response response = store.getOrder(orderId);
        
        response.then()
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .assertThat().body("message", equalToIgnoringCase("order not found"));
    }
}
