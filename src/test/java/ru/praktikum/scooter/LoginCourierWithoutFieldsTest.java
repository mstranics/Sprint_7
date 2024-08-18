package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
@RunWith(Parameterized.class)
public class LoginCourierNegativeTest {
    public LoginCourierNegativeTest(String login, String password, int expectedCode, String expectedText) {
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
                { courier.getLogin(), courier.getPassword(),  200 , "ok"},
             //   { "midelog1", "midepass",  201 , "ok"},
        };
    }
    @Before
    public void setUp(){

        courierClient = new CourierClient();
        courier=CourierHelper.addCourier();

    }
    @After
    public  void cleanUp () {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check courier's login")
    public  void  checkCourierLoginNegative () {
        courierClient.create(courier);
        courier.setLogin(login);
        courier.setPassword(password);
        ValidatableResponse loginResponse= courierClient.login(CourierCreds.from(courier));
        assertEquals(expectedCode,loginResponse.extract().statusCode());
        if (loginResponse.extract().statusCode()==400) {
        assertEquals(expectedText,loginResponse.extract().path("message"));
        } else if (loginResponse.extract().statusCode()==200) {
            courierId=loginResponse.extract().path("id");
            assertNotEquals(0,courierId);
        }
    }
}
