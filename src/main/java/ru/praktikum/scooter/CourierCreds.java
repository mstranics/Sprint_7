package ru.praktikum.scooter;

public class CourierCreds {


    public CourierCreds(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String login;
    private String password;

    public static CourierCreds from(Courier courier) {
        return new CourierCreds(courier.getLogin(), courier.getPassword());
    }

}
