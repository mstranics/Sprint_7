package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    private final List<String> color;

    private OrderClient orderClient;
    private Order order;
    private OrderTrack orderWithTrack;
    private int track;

    @Parameterized.Parameters
    public static Object[][] getColorData() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {null},


        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderHelper.addOrder();
        orderWithTrack = new OrderTrack();

    }

    @After
    public void cleanUp(){
        orderWithTrack.setTrack(track);
        orderClient.cancelOrder(orderWithTrack);
    }

    @Test
    @DisplayName("Creation of order with all fields  and  different  colors")
    public void OrderCreated() {
        order.setColor(color);

        ValidatableResponse createdResponse = orderClient.create(order);
        assertEquals("не совпадает код ответа",201, createdResponse.extract().statusCode());
        track = createdResponse.extract().path("track");
        assertNotEquals("track не должен быть пустым",0, track);
        ValidatableResponse getOrderResponse = orderClient.getOrderByTrack(track);
        assertEquals("не совпадает код ответа",200, getOrderResponse.extract().statusCode());


    }
}
