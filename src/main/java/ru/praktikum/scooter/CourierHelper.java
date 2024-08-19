package ru.praktikum.scooter;

import java.util.Random;

public class CourierHelper {
    public static Courier addCourier() {
        Courier courier = new Courier();
        courier.setFirstName("John" + new Random().nextInt(1000));
        courier.setPassword("pass" + new Random().nextInt(1000));
        courier.setLogin("Johnnie" + new Random().nextInt(1000));
        return courier;
    }
}
