package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import data.classes.Order;
import data.classes.Pet;
import data.controllers.PetController;
import data.controllers.StoreController;
import data.utils.TestHelper;
import io.restassured.response.Response;

import org.apache.http.HttpStatus;


public class CreateOrderTest extends TestBase {

    private StoreController store;
    private PetController petController;

    @BeforeClass
    public void initSpec() { 
        var request = requestSpec(true);

        store = new StoreController(request);
        petController = new PetController(request); 
    }

    @Test
    public void createOrder_usingValidOrder_isSuccessfullTest() {
        Pet pet = new Pet();
        pet.setId(TestHelper.generatePetId());
        petController.createPet(pet).then().statusCode(HttpStatus.SC_OK);
        Order order = Order.builder()
            .id(TestHelper.generateRandomOrderId())
            .petId(pet.getId())
            .quantity(1)
            .shipDate(TestHelper.getCurrentDateTimeString())
            .status("placed")
            .complete(true)
            .build();
        
        Response response = store.createOrder(order);

        response.then().statusCode(HttpStatus.SC_OK);
        TestHelper.assertHeaders(response);
        Order createdOrder = store.getOrder(order.getId())
        .then().extract().body().as(Order.class);
        Assert.assertTrue(order.equals(createdOrder));
    }

    @Test
    public void createOrder_usingInvalidOrder_returnsBadRequestTest() {
        // contains pet, but not a quantity
        Pet pet = new Pet();
        pet.setId(TestHelper.generatePetId());
        petController.createPet(pet).then().statusCode(HttpStatus.SC_OK);
        Order order = Order.builder()
            .id(TestHelper.generateRandomOrderId())
            .petId(pet.getId())
            .shipDate(TestHelper.getCurrentDateTimeString())
            .status("placed")
            .complete(true)
            .build();
        
        Response response = store.createOrder(order);

        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

}