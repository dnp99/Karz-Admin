package com.example.deeppatel.car_rerntal.Returning_Process.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeleteReservation {

    public void deleteReservation(String docRef){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("reservation").document(docRef)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.i("DEL", "Success");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.i("DEL", "Failure");

                    }
                });

    }

}
