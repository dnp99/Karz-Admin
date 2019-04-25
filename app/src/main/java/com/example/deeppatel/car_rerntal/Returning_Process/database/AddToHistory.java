package com.example.deeppatel.car_rerntal.Returning_Process.database;

import android.support.annotation.NonNull;

import com.example.deeppatel.car_rerntal.Returning_Process.models.Reservation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class AddToHistory {

    public void addToHistory(Reservation reservation, String milRet, String finalAmt){

        Map<String, Object> data = new HashMap<>();

        data.put("billingOverview", reservation.getBillingOverview());
        data.put("carId", reservation.getCarId());
        data.put("deposit", reservation.getDeposit());
        data.put("endDateTime", reservation.getEndDateTime());
        data.put("startDateTime", reservation.getStartDateTime());
        data.put("hours", reservation.getHours());
        data.put("mileageReturned", milRet);
        data.put("userId", reservation.getUserId());
        data.put("finalAmount", finalAmt);



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("history")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }

}
