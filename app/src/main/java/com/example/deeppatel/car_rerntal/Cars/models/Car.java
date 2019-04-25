package com.example.deeppatel.car_rerntal.Cars.models;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.Serializable;
import java.util.Random;

public class Car  implements Serializable {
    private String name;
    private String model;
    private String mileage;
    private String image=null;
    private String status;
    private int color;
    private String ID;


    public void setColor(int color) {
        this.color = color;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getColor()
    {
        setColor();
        return color;
    }

    public void setColor()
    {
        this.color=getRandColor();
    }
    public static int getRandColor(){

        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

    }


    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", mileage=" + mileage +
                ", image=" + image +
                ", status='" + status+ '\'' +
                '}';
    }
}
