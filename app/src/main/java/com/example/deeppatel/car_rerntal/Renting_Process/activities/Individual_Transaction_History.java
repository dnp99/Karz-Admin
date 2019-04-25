package com.example.deeppatel.car_rerntal.Renting_Process.activities;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.R;

public class Individual_Transaction_History extends AppCompatActivity {
    private View circle_ele;
    private TextView car_name,customer_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual__transaction__history);

        car_name = findViewById(R.id.DETAILS_car_name);
        customer_name=findViewById(R.id.DETAILS_customer);
        String car1="";
        String customer1;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                car1= null;
                customer1=null;
            } else {
                car1= extras.getString("transaction_object_car");
                customer1=extras.getString("transaction_object_customer");
            }
        } else {
            car1= (String) savedInstanceState.getSerializable("transaction_object");
            customer1=(String) savedInstanceState.getSerializable("transaction_object_customer");
        }
        car_name.setText(car1);
        customer_name.setText(customer1);

}
}
