package com.example.deeppatel.car_rerntal.Renting_Process.models;


import com.example.deeppatel.car_rerntal.Cars.models.Car;

import java.io.Serializable;

public class Reservation implements Serializable {
    private String id;
    private String StartDateTime;
    private String EndDateTime;
    private String deposit;
    private String billingOverview;
    private String hours;
    private String mileageReturned;
    private Car car;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reservation() {
    }

    public Reservation(String id, String carId, String userId, String startDateTime, String endDateTime, String deposit, String billingOverview, String hours, String mileageReturned, String returned, Car car) {
        this.id = id;
        StartDateTime = startDateTime;
        EndDateTime = endDateTime;
        this.deposit = deposit;
        this.billingOverview = billingOverview;
        this.hours = hours;
        this.mileageReturned = mileageReturned;
        this.car = car;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        StartDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return EndDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        EndDateTime = endDateTime;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getBillingOverview() {
        return billingOverview;
    }

    public void setBillingOverview(String billingOverview) {
        this.billingOverview = billingOverview;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMileageReturned() {
        return mileageReturned;
    }

    public void setMileageReturned(String mileageReturned) {
        this.mileageReturned = mileageReturned;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
