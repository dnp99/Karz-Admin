package com.example.deeppatel.car_rerntal.Customer.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class CustomerRegistration extends AppCompatActivity {

    EditText license_number_edt, fname_edt, lname_edt, address_edt, province_edt, postal_code_edt, class_edt, lic_exp_edt, email_edt, password_edt;
    Button reg_customer_btn;
    private final String CUST_DB = "user";
    private final String LIC_DB = "licenses";
    //Initialize the db
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);

        //Initialize awesome validation
        final AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);

        license_number_edt = findViewById(R.id.licence_number_edt);
        fname_edt = findViewById(R.id.fname_edt);
        lname_edt = findViewById(R.id.lname_edt);
        address_edt = findViewById(R.id.address_edt);
        province_edt = findViewById(R.id.province_edt);
        postal_code_edt = findViewById(R.id.postal_code_edt);
        class_edt = findViewById(R.id.class_edt);
        lic_exp_edt = findViewById(R.id.lic_exp_edt);
        email_edt = findViewById(R.id.email_edt);
        password_edt = findViewById(R.id.password_edt);
        reg_customer_btn = findViewById(R.id.reg_customer_btn);

        //All validations
        //Lic no. Cannot be empty
        mAwesomeValidation.addValidation(license_number_edt, "[a-z, A-Z, 0-9]{15}", "required 15 characters");
        //Required fields
        mAwesomeValidation.addValidation(fname_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(lname_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(address_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(province_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(postal_code_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(class_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(lic_exp_edt,"^(?=\\s*\\S).*$", "Invalid date");
        //Email validation
        mAwesomeValidation.addValidation(email_edt, Patterns.EMAIL_ADDRESS , "Invalid Email");
        mAwesomeValidation.addValidation(password_edt,"^(?=\\s*\\S).*$", "Required");

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };

        lic_exp_edt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(CustomerRegistration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        reg_customer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAwesomeValidation.validate()){

                    CollectionReference refLic = db.collection(LIC_DB);

                    Query licQuery = refLic.whereEqualTo("lic_id", license_number_edt.getText().toString());

                    licQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if(task.isSuccessful()){

                                if(task.getResult().getDocuments().toArray().length <= 0){

                                    CollectionReference refEmail = db.collection(CUST_DB);

                                    refEmail.whereEqualTo("email", email_edt.getText().toString())
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                    if(queryDocumentSnapshots.getDocuments().toArray().length <= 0){


                                                        //If all the fields are valid

                                                        //Make a customer object
                                                        Map<String, Object> customer = new HashMap<>();
                                                        customer.put("email", email_edt.getText().toString());
                                                        customer.put("firstname", fname_edt.getText().toString());
                                                        customer.put("password", password_edt.getText().toString());


                                                        //Make a license object
                                                        final Map<String, Object> license = new HashMap<>();
                                                        license.put("lic_id", license_number_edt.getText().toString());
                                                        license.put("exp_date", lic_exp_edt.getText().toString());
                                                        license.put("lic_class", class_edt.getText().toString());
                                                        license.put("province", province_edt.getText().toString());
                                                        license.put("postal_code", postal_code_edt.getText().toString());
                                                        license.put("address", address_edt.getText().toString());
                                                        license.put("email", email_edt.getText().toString());


                                                        //Add a new customer document
                                                        db.collection(CUST_DB)
                                                                .add(customer)
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {

                                                                        //If the customer is saved successfully, then save the license information

                                                                        db.collection(LIC_DB)
                                                                                .add(license)
                                                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                    @Override
                                                                                    public void onSuccess(DocumentReference documentReference) {

                                                                                        Toasty.success(getApplicationContext(), "Registered").show();

                                                                                        //If the lic info is saved
                                                                                        Intent toHome = new Intent(CustomerRegistration.this, Home.class);
                                                                                        startActivity(toHome);

                                                                                    }
                                                                                })
                                                                                .addOnFailureListener(new OnFailureListener() {
                                                                                    @Override
                                                                                    public void onFailure(@NonNull Exception e) {

                                                                                        Toasty.error(getApplicationContext(), "License not saved").show();

                                                                                    }
                                                                                });

                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {

                                                                        Toasty.error(getApplicationContext(), "Customer not saved").show();

                                                                    }
                                                                });


                                                    }else{

                                                        Toasty.error(getApplicationContext(), "Email already exists").show();

                                                    }

                                                }
                                            });

                                }else{

                                    Toasty.error(getApplicationContext(), "License already exists").show();

                                }

                            }

                        }
                    });

                }else{

                    Toasty.error(getApplicationContext(), "Incorrect inputs").show();

                }

            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);

        lic_exp_edt.setText(sdf.format(myCalendar.getTime()));
    }

}
