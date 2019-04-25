package com.example.deeppatel.car_rerntal.Renting_Process.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Renting_Process.activities.RentInfo;
import com.example.deeppatel.car_rerntal.Renting_Process.models.Reservation;
import com.example.deeppatel.car_rerntal.Renting_Process.models.ReservationDatabase;
import com.example.deeppatel.car_rerntal.Renting_Process.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class ReserveCar {
    String res_id = "";
    public String saveReservation(Reservation reservation)  {

        String carId=getCarId(reservation.getCar());
        String userId=getCustomerId(reservation.getUser());

        ReservationDatabase reservationDatabase= new ReservationDatabase();
        reservationDatabase.setCarId(carId);
        reservationDatabase.setUserId(userId);
        reservationDatabase.setBillingOverview(reservation.getBillingOverview());
        reservationDatabase.setDeposit(reservation.getDeposit());
        reservationDatabase.setEndDateTime(reservation.getEndDateTime());
        reservationDatabase.setStartDateTime(reservation.getStartDateTime());
        reservationDatabase.setHours(reservation.getHours());

        FirebaseFirestore db=  FirebaseFirestore.getInstance();
        db.collection("reservation")
                .add(reservationDatabase)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("reservation ", "Reservation added with ID: " + documentReference.getId());
                        res_id =documentReference.getId();
                        RentInfo.booking_id=documentReference.getId();
                        Log.d("reservation ", "global_bookingid" + RentInfo.booking_id);
                        //Update the status of car to false
                        changeCarStatus(reservation.getCar());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("reservation ", "Error:  " + e.getMessage());
                    }
                });
                    return  res_id;

    }

    public String getCarId(Car car)
    {
        return car.getID();
    }


    public String getCustomerId(User user)
    {
        return user.getCustomer().getId();
    }

    public void changeCarStatus(Car car)
    {
        car.getID();
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("cars").document(car.getID()).
                update("status","false")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("reservation-car_status","Successfully changed to false");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("reservation-car_status","Error status of car is not changed");
                    }
                });
    }

}
