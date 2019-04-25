package com.example.deeppatel.car_rerntal.Renting_Process.models;

import com.example.deeppatel.car_rerntal.Customer.models.Customer;
import com.example.deeppatel.car_rerntal.Customer.models.License;

import java.io.Serializable;

public class User implements Serializable {

    private Customer customer;
    private License license;

    public User(Customer customer, License license) {
        this.customer = customer;
        this.license = license;
    }

    public User() {
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "customer=" + customer.toString() +
//                ", license=" + license.toString() +
//                '}';
//    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }
}
