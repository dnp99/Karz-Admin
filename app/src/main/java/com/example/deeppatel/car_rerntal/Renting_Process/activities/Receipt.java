package com.example.deeppatel.car_rerntal.Renting_Process.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.models.Reservation;
import com.example.deeppatel.car_rerntal.Renting_Process.models.User;

public class Receipt extends AppCompatActivity {

    TextView tv_customer_name;
    TextView tv_car_name,tv_startDate,tv_endDate,tv_deposit,tv_est_charge,tv_car_model,tv_booking_id;
    Reservation reservation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        //Initialize
        initialize();
        //Get Reservation Info
        getReservationInfo(savedInstanceState);
        //Set Fields


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setReceiptField();
    }

//    @Override
//    public void setTheme(int resid) {
//        super.setTheme(android.R.style.Theme_Translucent_NoTitleBar);
//    }

    private void setReceiptField() {
        tv_car_name.setText(reservation.getCar().getName());
        tv_customer_name.setText(reservation.getUser().getCustomer().getCustomerName());
        tv_startDate.setText(reservation.getStartDateTime());
        tv_endDate.setText(reservation.getEndDateTime());
        tv_deposit.setText(reservation.getDeposit()+"$");
        tv_est_charge.setText(reservation.getBillingOverview());
        tv_car_model.setText(reservation.getCar().getModel());
        tv_booking_id.setText(reservation.getId());
        //Log.d("rec",RentInfo.booking_id);

    }

    public void initialize()
    {
        tv_car_name=findViewById(R.id.receipt_car_name);
        tv_customer_name=findViewById(R.id.receipt_customer_name);
        int color= Color.parseColor("#000000");
        tv_car_name.setTextColor(color);
        tv_customer_name.setTextColor(color);

        tv_startDate=findViewById(R.id.receipt_start_period);
        tv_endDate=findViewById(R.id.receipt_end_period);
        tv_deposit=findViewById(R.id.receipt_deposit);
        tv_est_charge=findViewById(R.id.receipt_estimated_usage);
        tv_car_model=findViewById(R.id.receipt_model_name);

        tv_booking_id=findViewById(R.id.receipt_booking_id);

    }

    private void getReservationInfo(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                reservation= null;

            } else {
                reservation= (Reservation) getIntent().getSerializableExtra("reservation");
            }
        } else {
            reservation= (Reservation) getIntent().getSerializableExtra("reservation");
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        /****After transaction is done remove all fragment from backstack and redirect to home page********/
;
        FragmentManager fm = getSupportFragmentManager(); // or 'getSupportFragmentManager();'
        int count = fm.getBackStackEntryCount();
        for(int i = 0; i < count; ++i) {
            fm.popBackStack();
        }
        Intent homePage = new Intent(this, Home.class);
        startActivity(homePage);
    }

    public void ok_receipt(View view) {
        Intent i = new Intent(getBaseContext(),Home.class);
        startActivity(i);
    }
}
