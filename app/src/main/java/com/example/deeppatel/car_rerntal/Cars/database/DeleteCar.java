package com.example.deeppatel.car_rerntal.Cars.database;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.deeppatel.car_rerntal.Cars.CarEngine.carList;

public class DeleteCar {

    public void deleteDocument(final int position)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Car deleteCar= carList.get(position);
        db.collection("cars").document(deleteCar.getID())
                .delete().addOnSuccessListener(new OnSuccessListener< Void >() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(getApplicationContext(), "DB: Cancel ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
