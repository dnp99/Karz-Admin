package com.example.deeppatel.car_rerntal.Customer.models;

import java.io.Serializable;

public class License implements Serializable {

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    private String license;
    private String address;
    private String clazz;
    private String exp_date;
    //private String name;
//    private String license;
    private String zip;

    private String id;



    public License(String address, String clazz, String exp_date, String name, String license, String zip, String id) {
        this.address = address;
        this.clazz = clazz;
        this.exp_date = exp_date;
        //this.name = name;
        this.license = license;
        this.zip = zip;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public License() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }


    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
