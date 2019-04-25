package com.example.deeppatel.car_rerntal.Renting_Process.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.database.ReserveCar;
import com.example.deeppatel.car_rerntal.Renting_Process.models.BusinessRule;
import com.example.deeppatel.car_rerntal.Renting_Process.models.Reservation;
import com.example.deeppatel.car_rerntal.Renting_Process.models.User;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RentInfo extends AppCompatActivity implements View.OnClickListener {

    private TextView  carName,carModel,mileage, price,deposit,startDate,endDate,dateEstimate,hourEstimate,priceEstimate;
    private ImageView car_image;
    private Car car;
    private User user;
    private  int key;
    public double totalHours;
    String startDateTime_ = "", endDateTime_ = "";
    LinearLayout estimateLayout;
    BusinessRule businessRule= new BusinessRule();
    public static String booking_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_info);

        //Intialize view
        intialize();
        //get data from last activity
        getUserandCarInfo(savedInstanceState);
        //populate View
        carName.setText(car.getName());
        carModel.setText(car.getModel());
        mileage.setText(car.getMileage());
        price.setText("Price/Km - 1.5 $/KM");
        deposit.setText("Deposit - 50$");
        estimateLayout.setVisibility(View.GONE);
        Picasso.get().load(car.getImage()).into(car_image);
        //Set listener for start and end date
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key=1;
                showDialog(0);
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key=0;
                showDialog(0);
            }
        });

    }
    private int hoursDifference(Date date1, Date date2) {
        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }

    private void intialize() {
        car_image=findViewById(R.id.rent_car_image1);
        carName=findViewById(R.id.rent_car_name);
        carModel=findViewById(R.id.rent_car_model);
        mileage=findViewById(R.id.rent_car_mileage);
        price=findViewById(R.id.rent_price_per_km);
        deposit=findViewById(R.id.rent_fixed_deposit);
        startDate=findViewById(R.id.start_date_time);
        endDate=findViewById(R.id.end_date_time);
        dateEstimate=findViewById(R.id.date_estimate);
        //hourEstimate=findViewById(R.id.hour_estimate);
        priceEstimate=findViewById(R.id.price_estimate);
        estimateLayout=findViewById(R.id.rent_ll_overview_info);
    }

    private void getUserandCarInfo(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                user= null;
                car=null;
            } else {
                car= (Car)getIntent().getSerializableExtra("selected_car");
                user=(User)getIntent().getSerializableExtra("selected_user");
            }
        } else {
            car= (Car)getIntent().getSerializableExtra("selected_car");
            user=(User)getIntent().getSerializableExtra("selected_user");
        }
    }

    /***************************************    Date and time Picker    *********************************************************/
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            if (key == 1)
                updateDate(new SimpleDateFormat("dd MMM, yyyy").format(calendar.getTime()), key);
            else
                updateDate(new SimpleDateFormat("dd MMM, yyyy").format(calendar.getTime()), 0);
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(0, 0, 0, hourOfDay, minute);
            if (key == 1)
                updateTime(new SimpleDateFormat("hh:mm a").format(calendar.getTime()), key);
            else
                updateTime(new SimpleDateFormat("hh:mm a").format(calendar.getTime()), 0);
        }
    };

    private void updateDate(String date, int flag) {
        if (flag == 1) {
            startDate.setText(date);
            setTime();
        } else {
            endDate.setText(date);
            setTime();
        }
    }

    private void updateTime(String time, int flag) {
        if (flag == 1) {
            startDateTime_ = startDate.getText().toString() + " " + time;
            startDate.append(" " + time);
        } else {
            endDateTime_ = endDate.getText().toString() + " " + time;
            endDate.append(" " + time);
        }
        if ((!(startDateTime_.isEmpty()) && (!(endDateTime_.isEmpty())))) {
            try {
                validateDateAndTime(startDateTime_, endDateTime_);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                startDate.setText("");
                endDate.setText("");
                estimateLayout.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void validateDateAndTime(String startDate, String endDate) throws Exception {
        try {
            Date sDate = new SimpleDateFormat("dd MMM, yyyy hh:mm a").parse(startDate);
            Date eDate = new SimpleDateFormat("dd MMM, yyyy hh:mm a").parse(endDate);

            if (sDate.before(Calendar.getInstance().getTime()))
                throw new Exception("Start date cannot come before current date, please review");
            else if (eDate.before(Calendar.getInstance().getTime()))
                throw new Exception("end date cannot come before start date, please review");
            else if (sDate.after(eDate))
                throw new Exception("Start date cannot come after the end date, please review");
            else
                showEstimate(sDate, eDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void showEstimate(Date sDate, Date eDate) {
        String startDate = new SimpleDateFormat("dd MMM, yyyy").format(sDate);
        String endDate = new SimpleDateFormat("dd MMM, yyyy").format(eDate);
        String sTime = new SimpleDateFormat("hh:mm a").format(sDate);
        String eTime = new SimpleDateFormat("hh:mm a").format(eDate);

        estimateLayout.setVisibility(View.VISIBLE);

        DateTime sdt = new DateTime(sDate.getTime());
        DateTime edt = new DateTime(eDate.getTime());

        int days = Days.daysBetween(sdt, edt).getDays();
        dateEstimate.setText("You have hired " + carName.getText().toString() + " for " + days + " day(s) starting from " + startDate + " at " + sTime + " to " + endDate + " at " + eTime);

        totalHours = hoursDifference(eDate, sDate);
        priceEstimate.setText("The estimate bill for the hire duration is $" + totalHours * 1.5);
    }
    private void setTime() {
        showDialog(1);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                final Calendar c = Calendar.getInstance();
                int startYear = c.get(Calendar.YEAR);
                int startMonth = c.get(Calendar.MONTH);
                int startDate = c.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(this,
                        mDateSetListener, startYear, startMonth, startDate);
            case 1:
                final Calendar ca = Calendar.getInstance();
                int startMin = ca.get(Calendar.MINUTE);
                int startHour = ca.get(Calendar.HOUR);
                return new TimePickerDialog(this, mTimeListener, startHour, startMin, false);
        }
        return null;
    }



    /***************************************    Date and time Picker  End   *********************************************************/


    /****************************************       Alert Dialogue Box  *****************************/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_continue_rentinfo:
                Toast.makeText(this, "Continue Button", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:
                                // User clicked the Yes button
                                Toast.makeText(getBaseContext() , "OK", Toast.LENGTH_SHORT).show();
                                /******************Redirect To Payment Activity *************/

                                /******For debit or credit****/
                                Intent intent = new Intent(getBaseContext(), Payment.class);
                                intent.putExtra("reservation", getReservationObject());
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                // User clicked the No button
                                Toast.makeText(getBaseContext(), "Cancel", Toast.LENGTH_SHORT).show();
                                break;

                            /*****  If cash is selected *****/
                            case DialogInterface.BUTTON_NEUTRAL:
                                //Save Reservation to database
                                Reservation reservation=getReservationObject();
                                String res_id= reserveCar(reservation);
                                reservation.setId(res_id);
                                Log.d("res_id",reservation.getId());
                                //Call Receipt Activity
                                Intent recieptIntent = new Intent(getBaseContext(), Receipt.class);
                                recieptIntent.putExtra("reservation",reservation );
                                startActivity(recieptIntent);
                                break;

                        }
                    }
                };

                alert.setTitle("PAYMENT");
                alert.setMessage("Please select hte payment method");
                alert.setNeutralButton("Cash",dialogClickListener);
                alert.setPositiveButton("Debit/Credit", dialogClickListener);
                alert.setNegativeButton("Cancel",dialogClickListener);
                AlertDialog dialog = alert.create();
                dialog.show();
                break;

            case R.id.btn_cancel_rentinfo:
                Toast.makeText(getBaseContext(), "Cancel Button", Toast.LENGTH_SHORT).show();

                /*******     Back to All Available Car Fragment  ********/
                Intent serachForCustomer = new Intent(this, SearchForCustomer.class);
                serachForCustomer.putExtra("selected_car", String.valueOf("To be Solved "));
                startActivity(serachForCustomer);
                break;

        }
    }

    public String reserveCar(Reservation reservation)
    {
        String res="";
        ReserveCar reserveCar1= new ReserveCar();
                 res=  reserveCar1.saveReservation(reservation);


        return res;
    }
    /****   Make reservation object to pass to next activity **/
    public  Reservation getReservationObject()
    {
        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setUser(user);
        reservation.setDeposit(String.valueOf(businessRule.getDeposit()));
        reservation.setStartDateTime(startDate.getText().toString());
        reservation.setEndDateTime((endDate.getText().toString()));
        reservation.setBillingOverview(String.valueOf(totalHours * 1.5)+"$");
        reservation.setHours(String.valueOf(totalHours));
        reservation.setId(booking_id+" ");
        return reservation;
    }
}
