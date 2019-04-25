package com.example.deeppatel.car_rerntal.Returning_Process;

import android.util.Log;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class CarsEngine {

    private List<com.example.deeppatel.car_rerntal.Cars.models.Car> carList = new ArrayList<>();

    public CarsEngine(List<com.example.deeppatel.car_rerntal.Cars.models.Car> carList) {
        this.carList = carList;
    }

    public CarsEngine() {

    }

    public List<com.example.deeppatel.car_rerntal.Cars.models.Car> getCarList() {
        return carList;
    }

    public void setCarList(List<com.example.deeppatel.car_rerntal.Cars.models.Car> carList) {
        this.carList = carList;
    }

    public int getCount(){

        return this.carList.size();

    }

    public com.example.deeppatel.car_rerntal.Cars.models.Car getCar(int position){

        if(carList.size() > 0){

            return carList.get(position);

        }

        return null;

    }

    public void addCars(CarsAdapter carsAdapter){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cars")
                .whereEqualTo("status", "false")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if(e != null){

                            Log.e("FIRE STORE ERROR", e.getMessage());

                            return;

                        }

                        carList = new ArrayList<>();

                        for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){

                            if(queryDocumentSnapshot != null){
                                if(queryDocumentSnapshot.get("name") != null){

                                    if(queryDocumentSnapshot.get("mileage") != null){

                                        if(queryDocumentSnapshot.get("model") != null){

                                            if(queryDocumentSnapshot.get("status") != null){

                                                Car car = new Car();

                                                car.setID(queryDocumentSnapshot.getId());
                                                car.setName(queryDocumentSnapshot.get("name").toString());
                                                car.setMileage(queryDocumentSnapshot.get("mileage").toString());
                                                car.setStatus(queryDocumentSnapshot.get("status").toString());
                                                car.setModel(queryDocumentSnapshot.get("model").toString());
                                                if(queryDocumentSnapshot.get("image") != null){

                                                    car.setImage(queryDocumentSnapshot.get("image").toString());

                                                }

                                                carList.add(car);

                                            }

                                        }

                                    }

                                }
                            }

                        }

                        //Notify changes to all the relevant list here...
                        carsAdapter.notifyDataSetChanged();
                        carsAdapter.bookedCarsListFull = getCarList();
                        Log.i("SIZE", String.valueOf(carList.toArray().length));

                    }
                });

    }


}
