package com.example.deeppatel.car_rerntal.Cars.database;

import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.fragments.CarFragment;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.deeppatel.car_rerntal.Cars.CarEngine.carList;

public class UpdateCar {

    public void updateData( Car car) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference contact = db.collection("cars").document(car.getID());
        contact.update("name", car.getName());
        contact.update("model", car.getModel());
        contact.update("mileage", car.getMileage());
        contact.update("image", car.getImage());
        contact.update("status", car.getStatus())
                .addOnSuccessListener(new OnSuccessListener< Void >() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
}
}
