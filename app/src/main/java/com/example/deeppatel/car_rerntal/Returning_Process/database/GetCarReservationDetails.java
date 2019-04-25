package com.example.deeppatel.car_rerntal.Returning_Process.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Returning_Process.models.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GetCarReservationDetails {

    private List<Reservation> reservationList = new ArrayList<>();

    public List<Reservation> getReservationDetails(String carId){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reservation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    List<Reservation> reservations = new ArrayList<>();

                    //Check if only one object is returned
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        Reservation r = new Reservation();
                        r.setId(document.getId());
                        r.setBillingOverview(document.get("billingOverview").toString());
                        r.setCarId(document.get("carId").toString());
                        r.setDeposit(Double.parseDouble(document.get("deposit").toString()));
                        r.setEndDateTime(document.get("endDateTime").toString());
                        r.setHours(Double.parseDouble(document.get("hours").toString()));
                        r.setMileageReturned(0);
                        r.setStartDateTime(document.get("startDateTime").toString());
                        r.setUserId(document.get("userId").toString());

                        Log.i("ID", r.getId());

                        reservations.add(r);
                    }

                    reservationList = reservations;

                } else {
                    Log.d("Db error", "Error getting documents: ", task.getException());
                }
            }
        });

        return reservationList;

    }

}
