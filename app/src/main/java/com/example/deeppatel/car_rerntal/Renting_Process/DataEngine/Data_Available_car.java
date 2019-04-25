package com.example.deeppatel.car_rerntal.Renting_Process.DataEngine;

import com.example.deeppatel.car_rerntal.Cars.database.DeleteCar;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Renting_Process.adapters.MyAvailableCarsRecyclerViewAdapter;
import com.example.deeppatel.car_rerntal.Renting_Process.database.Available_CarsFetcher;

import java.util.ArrayList;
import java.util.List;

public class Data_Available_car {


    public static List<Car> available_carList = new ArrayList<>();


    public static List<Car> getAvailable_carList() {
        return available_carList;
    }

    public static void setAvailable_carList(List<Car> available_carList) {
        Data_Available_car.available_carList = available_carList;
    }

    public int getCount(){
        return this.available_carList.size();
    }


    //Get Car From Local List
    public Car getCar(int position){
        if(available_carList.size() > 0){
            return available_carList.get(position);
        }
        return null;
    }

    public List<Car> getAllCars()
    {
        return available_carList;
    }
    //Delete from Database
    public Car deleteCar(int position){

        if(available_carList.size() > 0)
        {
            DeleteCar deleteCar = new DeleteCar();
            deleteCar.deleteDocument(position);
            return available_carList.remove(position);
        }
        return null;
    }

    //Get All Cars From Database
    public void addCars(final MyAvailableCarsRecyclerViewAdapter adapter){

        Available_CarsFetcher  getCarsFromDB= new Available_CarsFetcher();
        available_carList=getCarsFromDB.getAllCars(adapter);
    }


}
