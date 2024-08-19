package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListOrdersTest {

    private OrderClient orderClient;
    private Order order;

    private OrderTrack orderWithTrack;
    private int track;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        orderWithTrack = new OrderTrack();
        order = OrderHelper.addOrder();
        ValidatableResponse createdResponse = orderClient.create(order);
        track = createdResponse.extract().path("track");

    }
    @After
    public void cleanUp(){
        orderWithTrack.setTrack(track);
        orderClient.cancelOrder(orderWithTrack);
    }
    @Test
    @DisplayName("List orders")
    public void OrderList() {
        ValidatableResponse listResponse = orderClient.listOrders();
        assertEquals("не совпадает код ответа",200, listResponse.extract().statusCode());
        assertNotEquals("ордер не должен быть пустым",null, listResponse.extract().path("orders"));
    }
}
