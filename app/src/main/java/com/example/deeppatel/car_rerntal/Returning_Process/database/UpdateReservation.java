package com.example.deeppatel.car_rerntal.Returning_Process.database;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateReservation {
    public void updateReservation(String resId, String mileage){


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference resReference = db.collection("reservation").document(resId);

        resReference.update("mileageReturned", mileage)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.i("Mileage", "UPDATED");

                    }
                });

    }
}
