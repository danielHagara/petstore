package data.controllers;

import data.classes.Pet;
import data.utils.JsonConverter;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PetController {
    
    private RequestSpecification request;

    public PetController(String baseUrl, String apiKey) {
        request = RestAssured
            .given().baseUri(baseUrl + "/pet")
            .header("api_key", apiKey)
            .log().method()
            .log().uri()
            .when();
    }

    public Response createPet(Pet pet) {
        return request
            .header("Content-Type", "application/json")
            .body(JsonConverter.convertToJson(pet))
            .post();
    }
}
