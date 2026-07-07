package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import data.classes.Order;
import data.classes.Pet;
import data.utils.TestHelper;
import io.restassured.response.Response;

import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.lessThan;

public class CreateOrderTest extends TestBase {

    @Test
    public void createOrderTest() {
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
        .time(lessThan(1000L))
        .log().ifValidationFails();
        TestHelper.assertHeaders(response);
        Order createdOrder = store.getOrder(order.getId())
        .then().extract().body().as(Order.class);
        Assert.assertTrue(order.equals(createdOrder));
    }


}