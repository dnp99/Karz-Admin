package com.example.deeppatel.car_rerntal.Renting_Process.models;

import java.io.Serializable;

public class ReservationDatabase implements Serializable
{
    private String userId,carId,billingOverview,deposit,startDateTime,mileageReturned,id,hours,endDateTime;

    public ReservationDatabase() {
    }



    public void setUserId(String userId) {
        this.userId = userId;
    }


    public void setCarId(String carId) {
        this.carId = carId;
    }


    public void setBillingOverview(String billingOverview) {
        this.billingOverview = billingOverview;
    }


    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }


    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }


    public void setMileageReturned(String mileageReturned) {
        this.mileageReturned = mileageReturned;
    }

    public String getUserId() {
        return userId;
    }

    public String getCarId() {
        return carId;
    }

    public String getBillingOverview() {
        return billingOverview;
    }

    public String getDeposit() {
        return deposit;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getMileageReturned() {
        return mileageReturned;
    }

    public String getId() {
        return id;
    }

    public String getHours() {
        return hours;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setHours(String hours) {
        this.hours = hours;
    }


    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }
}