package ru.praktikum.scooter;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    private static final String COURIER_PATH = "/api/v1/courier/";
    private static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";

    @Step("Send POST request to " + COURIER_PATH)

    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Send POST request to " + COURIER_LOGIN_PATH)

    public ValidatableResponse login(CourierCreds creds) {
        return given()
                .spec(getBaseSpec())
                .body(creds)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then();
    }

    @Step("Send DELETE request to " + COURIER_PATH)

    public ValidatableResponse delete(int courierId) {
        return given()  //.log().all()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }
}