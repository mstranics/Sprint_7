package ru.praktikum.scooter;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String ORDERS_PATH = "/api/v1/orders";
    private static final String ORDERS_TRACK_PATH = "/api/v1/orders/track?t=";
    private static final String ORDERS_CANCEL_PATH = "/api/v1/orders/cancel";

    @Step("Send POST request to " + ORDERS_PATH)

    public ValidatableResponse create(Order order) {
        return given() //.log().body()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then();
    }

    @Step("Send GET request to " + ORDERS_TRACK_PATH)
    public ValidatableResponse getOrderByTrack(int track) {
        return given()  //.log().all()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_TRACK_PATH + track)
                .then();
    }

    @Step("Send GET request to " + ORDERS_PATH)
    public ValidatableResponse listOrders() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH)
                .then();
    }

    @Step("Send PUT request to " + ORDERS_CANCEL_PATH)
    public ValidatableResponse cancelOrder(OrderTrack orderTrack) {
        return given().log().all()
                .spec(getBaseSpec())
                .body(orderTrack)
                .when()
                .put(ORDERS_CANCEL_PATH)
                .then();
    }
}


