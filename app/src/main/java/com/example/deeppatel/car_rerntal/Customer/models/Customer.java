package com.example.deeppatel.car_rerntal.Customer.models;

import com.example.deeppatel.car_rerntal.Helpers.RandomColorGenerator;

import java.io.Serializable;

public class Customer implements Serializable {

    private String firstName;
    private String lastName;
    private String licenseId;
    private String id;
    private String userId;
    private int color;

    public Customer(String firstName, String lastName, String licenseId, String userId, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.licenseId = licenseId;
        this.userId = userId;
        this.color = RandomColorGenerator.getRandColor();
        this.id = id;
    }

    public Customer() {

    }

    public String getCustomerName(){

        return getFirstName() + " " + getLastName();

    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String userId) {
        this.id = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
