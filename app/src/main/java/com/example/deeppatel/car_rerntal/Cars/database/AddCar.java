package com.example.deeppatel.car_rerntal.Cars.database;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.fragments.CarFragment;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AddCar {

    public void addToDatabase(Car car) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> customer = new HashMap<>();
        customer.put("name", car.getName());
        customer.put("model", car.getModel());
        customer.put("mileage", car.getMileage());
        customer.put("image", car.getImage());
        customer.put("status", car.getStatus());

        // Add a new document with a generated ID
        db.collection("cars")
                .add(customer)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("db", "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("db", "Error adding document", e);
                    }
                });
    }


}
