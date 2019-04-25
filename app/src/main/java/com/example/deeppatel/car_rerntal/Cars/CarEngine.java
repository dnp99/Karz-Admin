package com.example.deeppatel.car_rerntal.Cars;
import com.example.deeppatel.car_rerntal.Cars.database.CarsFetcher;
import com.example.deeppatel.car_rerntal.Cars.database.DeleteCar;
import com.example.deeppatel.car_rerntal.Cars.database.UpdateCar;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import java.util.ArrayList;
import java.util.List;

public class CarEngine {

    public static  List<Car> carList = new ArrayList<>();

    public int getCount(){
        return this.carList.size();
    }

    public static List<Car> getCarList() {
        return carList;
    }

    public  void setCarList(List<Car> carList) {
        CarEngine.carList = carList;
    }

    //Get Car From Local List
    public Car getCar(int position){
        if(carList.size() > 0){
            return carList.get(position);
        }
        return null;
    }

    //Delete from Database
    public Car deleteCar(int position){

        if(carList.size() > 0)
        {
            DeleteCar deleteCar = new DeleteCar();
            deleteCar.deleteDocument(position);
            return carList.remove(position);
        }
        return null;
    }

    //Get All Cars From Database
    public void addCars(final CarAdapter carsAdapter){
        CarsFetcher getCarsFromDB= new CarsFetcher();
        carList=getCarsFromDB.getAllCars(carsAdapter);
    }

    //update Car
    public void updateCar(Car car){

            UpdateCar updateCar = new UpdateCar();
            updateCar.updateData(car);

    }

}
