package ru.praktikum.scooter;

public class OrderHelper {
    public static Order addOrder() {
        Order order = new Order();
        order.setComment("some comment");
        order.setRentTime(5);
        order.setPhone("some phone");
        order.setDeliveryDate("2024-10-10");
        order.setMetroStation("some station");
        order.setAddress("some address");
        order.setLastName("last name");
        order.setFirstName("first Name");
        return order;
    }

}
