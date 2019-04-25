package com.example.deeppatel.car_rerntal.Returning_Process.database;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateCar {



    public void updateCar(String carId, String mileage){


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference carReference = db.collection("cars").document(carId);

        carReference.update("status", "true");
        carReference.update("mileage", mileage)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.i("STATUS", "UPDATED");

                    }
                });

    }

}
