package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateCourierTest {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {

        courierClient = new CourierClient();
        courier = CourierHelper.addCourier();
    }

    @After
    public void cleanUp() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Creation of courier with all fields")
    public void CourierCreated() {
        ValidatableResponse createResponse = courierClient.create(courier);
        assertEquals("не совпадает код ответа",201, createResponse.extract().statusCode());
        assertTrue(createResponse.extract().path("ok"));

        ValidatableResponse loginResponse = courierClient.login(CourierCreds.from(courier));
        assertEquals("не совпадает код ответа",200, loginResponse.extract().statusCode());
        courierId = loginResponse.extract().path("id");
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("The same courier can't be created")
    public void sameCourierNotCreated() {
        ValidatableResponse createResponse = courierClient.create(courier);
        assertEquals("не совпадает код ответа",201, createResponse.extract().statusCode());
        assertTrue(createResponse.extract().path("ok"));
        ValidatableResponse createResponseNegative = courierClient.create(courier);
        assertEquals("не совпадает код ответа",409, createResponseNegative.extract().statusCode());
        assertEquals("Этот логин уже используется. Попробуйте другой.", createResponseNegative.extract().path("message"));
    }

    @Test
    @DisplayName("Creation of courier with empty first name")
    public void CourierCreatedEmptyFirstName() {
        courier.setFirstName("");
        ValidatableResponse createResponse = courierClient.create(courier);
        assertEquals("не совпадает код ответа",201, createResponse.extract().statusCode());
        assertTrue("не совпадает значение поля ok в ответе",createResponse.extract().path("ok"));
        ValidatableResponse loginResponse = courierClient.login(CourierCreds.from(courier));
        assertEquals("не совпадает код ответа",200, loginResponse.extract().statusCode());
        courierId = loginResponse.extract().path("id");
        assertNotEquals("id не должен быть пустым",0, courierId);
    }

    @Test
    @DisplayName("Creation of courier with no first name")
    public void CourierCreatedNoFirstName() {
        courier.setFirstName(null);
        ValidatableResponse createResponse = courierClient.create(courier);
        assertEquals("не совпадает код ответа",201, createResponse.extract().statusCode());
        assertTrue("не совпадает значение поля ok в ответе",createResponse.extract().path("ok"));
        ValidatableResponse loginResponse = courierClient.login(CourierCreds.from(courier));
        assertEquals("не совпадает код ответа",200, loginResponse.extract().statusCode());
        courierId = loginResponse.extract().path("id");
        assertNotEquals("id не должен быть пустым",0, courierId);
    }
}
