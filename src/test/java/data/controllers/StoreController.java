package data.controllers;

import data.classes.Order;
import data.utils.JsonConverter;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StoreController {

    private RequestSpecification request;
    private String orderUrlPath = "store/order/";

    public StoreController(RequestSpecification requestSpecification) {
        this.request = RestAssured.given().spec(requestSpecification);
    }

    public Response getInventory() {
         return request
            .get("store/inventory");
    }

    public Response createOrder(Order order) {
         return request
            .header("Content-Type", "application/json")
            .body(JsonConverter.convertToJson(order))
            .post(orderUrlPath);
    }

    public Response updateOrder(Order order) {
         return request
            .header("Content-Type", "application/json")
            .body(JsonConverter.convertToJson(order))
            .put(orderUrlPath+ order.getId());
    }

    public Response getOrder(int orderId) {
         return request
            .get(orderUrlPath + orderId);
    }

    public Response deleteOrder(int orderId) {
        return request
            .header("Content-Type", "application/json")
            .delete(orderUrlPath + orderId);
    }

}
