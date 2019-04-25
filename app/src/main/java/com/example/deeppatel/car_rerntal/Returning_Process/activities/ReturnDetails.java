package com.example.deeppatel.car_rerntal.Returning_Process.activities;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Customer.models.Customer;
import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.models.BusinessRule;
import com.example.deeppatel.car_rerntal.Returning_Process.database.AddToHistory;
import com.example.deeppatel.car_rerntal.Returning_Process.database.DeleteReservation;
import com.example.deeppatel.car_rerntal.Returning_Process.database.UpdateCar;
import com.example.deeppatel.car_rerntal.Returning_Process.database.UpdateReservation;
import com.example.deeppatel.car_rerntal.Returning_Process.models.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class ReturnDetails extends AppCompatActivity {

    private double balance;
    private View circle_ele;
    private ImageView car_image_iv;
    private TextView car_name, car_model, booked_on, booked_by, return_date, deposit_paid, initial_mileage;
    private EditText currentMileage;
    private View parentActivityLayout;
    private Intent toHomeAfterSuccess;
    private LinearLayout calculationParent;
    private Reservation r = new Reservation();
    private Customer c = new Customer();
    private com.example.deeppatel.car_rerntal.Cars.models.Car car;
    //Initialize awesome validation
    final AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation_return_activity);

        calculationParent = findViewById(R.id.calculation_parent);
        car_model = findViewById(R.id.DETAILS_model);
        parentActivityLayout = findViewById(R.id.calculation_parent);
        car_image_iv = findViewById(R.id.car_image_iv);
        circle_ele = findViewById(R.id.DETAILS_circle);
        car_name = findViewById(R.id.DETAILS_car_name);
        booked_on = findViewById(R.id.DETAILS_booked_on);
        booked_by = findViewById(R.id.DETAILS_booked_by);
        return_date = findViewById(R.id.DETAILS_return_date);
        deposit_paid = findViewById(R.id.DETAILS_deposit_paid);
        currentMileage = findViewById(R.id.DETAILS_current_mileage);
        initial_mileage = findViewById(R.id.DETAILS_mileage);


        mAwesomeValidation.addValidation(currentMileage, "^(?=\\s*\\S).*$", "Required");



        //Get the car details from the calling activity
        car = (Car) getIntent().getSerializableExtra("Car");

        if(car != null){

            //Set the text views according to the car object sent
            GradientDrawable bgShape = (GradientDrawable) circle_ele.getBackground();
            bgShape.setColor(car.getColor());
            car_name.setText(car.getName().toUpperCase());
            car_model.setText(car.getModel().toUpperCase());
            initial_mileage.setText(car.getMileage());

            Picasso.get().setLoggingEnabled(true);
            Picasso.get().load(car.getImage()).placeholder(R.drawable.default_placeholder).resize(180, 180).centerCrop().into(car_image_iv);

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("reservation")
                .whereEqualTo("carId", car.getID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            QuerySnapshot documentSnapshot = task.getResult();

                            if(!documentSnapshot.isEmpty()){

                                r.setId(documentSnapshot.getDocuments().get(0).getId());
                                r.setBillingOverview(documentSnapshot.getDocuments().get(0).get("billingOverview").toString());
                                r.setCarId(documentSnapshot.getDocuments().get(0).get("carId").toString());
                                r.setDeposit(Double.parseDouble(documentSnapshot.getDocuments().get(0).get("deposit").toString()));
                                r.setEndDateTime(documentSnapshot.getDocuments().get(0).get("endDateTime").toString());
                                r.setHours(Double.parseDouble(documentSnapshot.getDocuments().get(0).get("hours").toString()));
                                r.setMileageReturned(0);
                                r.setStartDateTime(documentSnapshot.getDocuments().get(0).get("startDateTime").toString());
                                r.setUserId(documentSnapshot.getDocuments().get(0).get("userId").toString());

                                Log.i("RES", r.toString());

                                DocumentReference df = db.collection("user").document(r.getUserId());

                                df.get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                if(task.isSuccessful()){

                                                    DocumentSnapshot doc = task.getResult();

                                                    if(doc.exists()){

                                                        c.setFirstName(doc.get("firstName").toString());
                                                        c.setLastName(doc.get("lastName").toString());
                                                        c.setLicenseId(doc.get("licenseId").toString());
                                                        c.setUserId(doc.get("userId").toString());

                                                        booked_on.setText(r.getStartDateTime().toUpperCase());
                                                        booked_by.setText(c.getFirstName().toUpperCase() + " " + c.getLastName().toUpperCase());
                                                        return_date.setText(r.getEndDateTime().toUpperCase());
                                                        deposit_paid.setText(String.valueOf(r.getDeposit()));

                                                    }

                                                }

                                            }
                                        });

                            }

                        }

                    }

                });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.return_details, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.payment){

            if(mAwesomeValidation.validate()){

                if(Double.parseDouble(currentMileage.getText().toString()) < Double.parseDouble(car.getMileage())){

                    Toasty.warning(getApplicationContext(), "Current mileage is less than the previous mileage").show();

                }else{

                    final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
                    LayoutInflater inflater = this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.payment_dialog, null);

                    Button debit = dialogView.findViewById(R.id.btn_debit);
                    Button credit = dialogView.findViewById(R.id.btn_credit);
                    Button cash = dialogView.findViewById(R.id.btn_cash);
                    TextView amount_tv = dialogView.findViewById(R.id.balance_tv);

                    if(car != null && r != null && c != null){

                        double kms = (Double.parseDouble(currentMileage.getText().toString()) - Double.parseDouble(car.getMileage())) / 0.62137;

                        Log.i("kms", String.valueOf(kms));

                        balance = (kms * BusinessRule.price_per_km) - BusinessRule.deposit;

                        balance = Math.round(balance);

                        Log.i("Balance", String.valueOf(balance));

                        amount_tv.setText("$ " + balance);

                    }else {
                        Toasty.error(getApplicationContext(), "Something went wrong");
                    }

                    debit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogBuilder.dismiss();
                            Snackbar.make(parentActivityLayout, R.string.payment_processed , Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.back, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            //Update the current mileageReturned for the reservation
                                            new UpdateReservation().updateReservation(r.getId(), currentMileage.getText().toString());
                                            //Change the car status and the mileage
                                            new UpdateCar().updateCar(car.getID(), currentMileage.getText().toString());
                                            //Save to history
                                            new AddToHistory().addToHistory(r, currentMileage.getText().toString(), String.valueOf(balance));
                                            //Delete the reservation
                                            new DeleteReservation().deleteReservation(r.getId());

                                            Toasty.success(getApplicationContext(), "Payment Successful! Receipt Emailed.").show();

                                            toHomeAfterSuccess = new Intent(getApplicationContext(), Home.class);
                                            startActivity(toHomeAfterSuccess);

                                        }
                                    }).show();

                        }
                    });

                    credit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogBuilder.dismiss();
                            Snackbar.make(parentActivityLayout, R.string.payment_processed, Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.back, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            //Update the current mileageReturned for the reservation
                                            new UpdateReservation().updateReservation(r.getId(), currentMileage.getText().toString());
                                            //Change the car status and the mileage
                                            new UpdateCar().updateCar(car.getID(), currentMileage.getText().toString());

                                            //Save to history
                                            new AddToHistory().addToHistory(r, currentMileage.getText().toString(), String.valueOf(balance));
                                            //Delete the reservation
                                            new DeleteReservation().deleteReservation(r.getId());

                                            Toasty.success(getApplicationContext(), "Payment Successful! Receipt Emailed.").show();

                                            toHomeAfterSuccess = new Intent(getApplicationContext(), Home.class);
                                            startActivity(toHomeAfterSuccess);

                                        }
                                    }).show();

                        }
                    });

                    cash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogBuilder.dismiss();
                            Snackbar.make(parentActivityLayout, R.string.payment_processed, Snackbar.LENGTH_INDEFINITE)
                                    .setAction(R.string.back, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            //Update the current mileageReturned for the reservation
                                            new UpdateReservation().updateReservation(r.getId(), currentMileage.getText().toString());
                                            //Change the car status and the mileage
                                            new UpdateCar().updateCar(car.getID(), currentMileage.getText().toString());

                                            //Save to history
                                            new AddToHistory().addToHistory(r, currentMileage.getText().toString(), String.valueOf(balance));
                                            //Delete the reservation
                                            new DeleteReservation().deleteReservation(r.getId());

                                            Toasty.success(getApplicationContext(), "Payment Successful! Receipt Emailed.").show();

                                            toHomeAfterSuccess = new Intent(getApplicationContext(), Home.class);
                                            startActivity(toHomeAfterSuccess);

                                        }
                                    }).show();

                        }
                    });

                    dialogBuilder.setView(dialogView);
                    dialogBuilder.show();

                }

            }

        }

        return true;

    }
}
