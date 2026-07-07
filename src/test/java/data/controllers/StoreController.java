package data.controllers;

import data.classes.Order;
import data.utils.JsonConverter;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class StoreController {

    private RequestSpecification request;

    public StoreController(String baseUrl, String apiKey) {
        request = RestAssured
            .given().baseUri(baseUrl + "/store")
            .header("api_key", apiKey)
            .log().method()
            .log().uri()
            .when();
    }

    public Response getInventory() {
         return request
            .get("/inventory");
    }

    public Response createOrder(Order order) {
         return request
            .header("Content-Type", "application/json")
            .body(JsonConverter.convertToJson(order)).log().body()
            .post("/order");
    }

    public Response updateOrder(Order order) {
         return request
            .header("Content-Type", "application/json")
            .body(JsonConverter.convertToJson(order)).log().body()
            .put("order/" + order.getId());
    }

    public Response getOrder(int orderId) {
         return request
            .get("order/" + orderId);
    }

    public Response deleteOrder(int orderId) {
        return request
            .header("Content-Type", "application/json")
            .delete("order/" + orderId);
    }

}
