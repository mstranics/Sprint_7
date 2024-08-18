package ru.praktikum.scooter;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class CreateCourierWithoutFieldsTest {

    public CreateCourierWithoutFieldsTest(String login, String password, String firstName,int expectedCode,String expectedText) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.expectedCode=expectedCode;
        this.expectedText=expectedText;
    }

    private final String login;
    private final String password;
    private final String firstName;
    private final int expectedCode;
    private final String expectedText;

    @Parameterized.Parameters
    public static Object[][] getCourierData() {
        return new Object[][] {
                { "", "midepass", "midename", 400 , "Недостаточно данных для создания учетной записи"},
                { null, "midepass", "midename", 400 , "Недостаточно данных для создания учетной записи"},
                { "midelog", "", "midename", 400 , "Недостаточно данных для создания учетной записи"},
                { "midelog", null, "midename", 400 , "Недостаточно данных для создания учетной записи"},
                { "midelog4", "midepass", "", 201 , "ok"},
                { "midelog5", "midepass", null, 201 , "ok"},
        };
    }

    private CourierClient courierClient;
    private Courier courier;
    private  int courierId;
    @Before
    public void setUp(){

        courierClient = new CourierClient();
        courier=CourierHelper.addCourier();
    }


    @Test
    @DisplayName("Check creation of courier without fields")
    public void createCourierRequiredFields() {
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        ValidatableResponse createResponse = courierClient.create(courier);
        assertEquals(expectedCode,createResponse.extract().statusCode());
        if (createResponse.extract().statusCode()==400) {
        assertEquals(expectedText,createResponse.extract().path("message"));
        } else if (createResponse.extract().statusCode()==201) {
            assertTrue(createResponse.extract().path("ok"));
        }
        courierClient.delete(courierId);
    }

}
