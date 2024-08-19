package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
@RunWith(Parameterized.class)
public class LoginCourierWithoutFieldsTest {
    public LoginCourierWithoutFieldsTest(String login, String password, int expectedCode, String expectedText) {
        this.login = login;
        this.password = password;
        this.expectedCode = expectedCode;
        this.expectedText = expectedText;
    }

    private final String login;
    private final String password;
    private final int expectedCode;
    private final String expectedText;

    private CourierClient courierClient;
    private static Courier courier;
    private  int courierId;

    @Parameterized.Parameters
    public static Object[][] getCourierData() {
        return new Object[][] {
                { "", "midepass",  400 , "Недостаточно данных для входа"},
                { null, "midepass",  400 , "Недостаточно данных для входа"},
                { "midelog", "",  400 , "Недостаточно данных для входа"},
                { "midelog", null,  400 , "Недостаточно данных для входа"},


        };
    }
    @Before
    public void setUp(){

        courierClient = new CourierClient();
        courier=CourierHelper.addCourier();
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCreds.from(courier));
        courierId = loginResponse.extract().path("id");
        courier.setLogin(login);
        courier.setPassword(password);
    }
    @After
    public  void cleanUp () {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Courier's can't login without login or password")
    public  void  courierLoginNegative () {

        ValidatableResponse loginResponse= courierClient.login(CourierCreds.from(courier));
        assertEquals("не совпадает код ответа",expectedCode,loginResponse.extract().statusCode());
        assertEquals("текст ошибки не совападает",expectedText, loginResponse.extract().path("message"));
    }
}
