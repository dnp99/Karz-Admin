package com.example.deeppatel.car_rerntal.Renting_Process.database;

import android.util.Log;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.Data_Available_car;
import com.example.deeppatel.car_rerntal.Renting_Process.adapters.MyAvailableCarsRecyclerViewAdapter;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


public class Available_CarsFetcher {

    Data_Available_car engine = new Data_Available_car();
    //Get All Cars from FireStore
    public List<Car> getAllCars(final MyAvailableCarsRecyclerViewAdapter adapter) {
        final List<Car> cars = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("cars")
                .whereEqualTo("status", "true")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {

                            Log.e("FIRE STORE ERROR", e.getMessage());

                            return;
                        }
                        engine.available_carList.clear();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            if (queryDocumentSnapshot.getData() != null) {
                                Car c = queryDocumentSnapshot.toObject(Car.class);
                                c.setID(queryDocumentSnapshot.getId());
                                Log.d("db_car_fetched:", c.toString());
                                cars.add(c);

                            }

                        }
                        adapter.notifyDataSetChanged();
                        MyAvailableCarsRecyclerViewAdapter.rent_carListToCompare=Data_Available_car.getAvailable_carList();
                    }
                });
        return cars;
    }
}
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult())
//                    {
//                      //Create object of a car
//                        Car c = document.toObject(Car.class);
//                        c.setID(document.getId());
//                        Log.d("CarObject", String.valueOf(task.getResult().size()));
//                        cars.add(c);
//                    }
//                    //Notify Recycler View
//                    adapter.notifyDataSetChanged();
//
//                } else {
//                    Log.d("Db error", "Error getting documents: ", task.getException());
//                }
//            }
//        });
//        return cars;
//    }




