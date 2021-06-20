package com.iqra.carrenting;

public class Bookings {

    String booking_durations;
    String booking_price;
    String car_id;
    String car_name;
    String engine_size;
    String userid;

    public Bookings() {

    }

    public Bookings(String booking_durations, String booking_price, String car_id, String car_name, String engine_size, String userid) {
        this.booking_durations = booking_durations;
        this.booking_price = booking_price;
        this.car_id = car_id;
        this.car_name = car_name;
        this.engine_size = engine_size;
        this.userid = userid;
    }

    public String getBooking_durations() {
        return booking_durations;
    }

    public void setBooking_durations(String booking_durations) {
        this.booking_durations = booking_durations;
    }

    public String getBooking_price() {
        return booking_price;
    }

    public void setBooking_price(String booking_price) {
        this.booking_price = booking_price;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getEngine_size() {
        return engine_size;
    }

    public void setEngine_size(String engine_size) {
        this.engine_size = engine_size;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
