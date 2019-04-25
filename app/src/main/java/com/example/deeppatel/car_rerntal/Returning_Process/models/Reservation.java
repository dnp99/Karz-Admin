package com.example.deeppatel.car_rerntal.Returning_Process.models;

public class Reservation {

    private String id;
    private String carId;
    private String userId;
    private String startDateTime;
    private String endDateTime;
    private double deposit;
    private String billingOverview;
    private double hours;
    private double mileageReturned;
    private String returned;

    public Reservation(String id, String carId, String userId, String startDateTime, String endDateTime, double deposit, String billingOverview, double hours, double mileageReturned, String returned) {
        this.id = id;
        this.carId = carId;
        this.userId = userId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.deposit = deposit;
        this.billingOverview = billingOverview;
        this.hours = hours;
        this.mileageReturned = mileageReturned;
        this.returned = returned;
    }

    public Reservation() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public String getBillingOverview() {
        return billingOverview;
    }

    public void setBillingOverview(String billingOverview) {
        this.billingOverview = billingOverview;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public double getMileageReturned() {
        return mileageReturned;
    }

    public void setMileageReturned(double mileageReturned) {
        this.mileageReturned = mileageReturned;
    }

    public String getReturned() {
        return returned;
    }

    public void setReturned(String returned) {
        this.returned = returned;
    }
}
