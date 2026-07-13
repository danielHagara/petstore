package tests;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import data.classes.Order;
import data.classes.Pet;
import data.utils.JsonConverter;
import data.utils.TestHelper;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.lessThan;

public class NegativeTest extends TestBase {
    
    RequestSpecification unauthorizedSpec = RestAssured
            .given().baseUri(baseUrl + "/store")
            .log().method()
            .log().uri()
            .when()
            .header("Content-Type", "application/json");

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
        .time(lessThan(1000L))
        .log().ifValidationFails();    
    }

    @Ignore
    @Test
    public void createOrder_returnsUnauthorizedTest() {
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
        
        Response response = unauthorizedSpec
            .body(JsonConverter.convertToJson(order))
            .post("/order");

        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED)
        .time(lessThan(1000L))
        .log().ifValidationFails();
    }

    @Ignore
    @Test
    public void deleteOrderTest_returnsUnauthorizedTest() {
        Response response = unauthorizedSpec.delete("order/" + TestHelper.generateRandomOrderId());

        response.then().statusCode(HttpStatus.SC_UNAUTHORIZED)
        .time(lessThan(1000L))
        .log().ifValidationFails();        
    }

}
