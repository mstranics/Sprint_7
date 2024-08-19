package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LoginCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {

        courierClient = new CourierClient();
        courier = CourierHelper.addCourier();
        courierClient.create(courier);
    }

    @After
    public void cleanUp() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Courier's login")
    public void courierLogin() {
        ValidatableResponse loginResponse = courierClient.login(CourierCreds.from(courier));
        assertEquals("не совпадает код ответа",200, loginResponse.extract().statusCode());
        courierId = loginResponse.extract().path("id");
        assertNotEquals("id не должен быть пустым",0, courierId);
    }

    @Test
    @DisplayName("Courier's can't login with incorrect login")
    public void courierLoginWrongLogin() {
        courier.setLogin("midelog2024");
        ValidatableResponse loginResponse = courierClient.login(CourierCreds.from(courier));
        assertEquals("не совпадает код ответа",404, loginResponse.extract().statusCode());
        assertEquals("текст ошибки не совападает","Учетная запись не найдена", loginResponse.extract().path("message"));
    }

    @Test
    @DisplayName("Courier's can't login with incorrect pass")
    public void courierLoginWrongPass() {
        courier.setPassword("midepass2024");
        ValidatableResponse loginResponse = courierClient.login(CourierCreds.from(courier));
        assertEquals("не совпадает код ответа",404, loginResponse.extract().statusCode());
        assertEquals("текст ошибки не совападает","Учетная запись не найдена", loginResponse.extract().path("message"));
    }
}
