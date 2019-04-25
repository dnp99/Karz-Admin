package com.example.deeppatel.car_rerntal.Customer.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.deeppatel.car_rerntal.Customer.models.Customer;
import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import es.dmoral.toasty.Toasty;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class EditCustomer extends AppCompatActivity {

    EditText fname_edt, lname_edt, licence_number_edt, address_edt, province_edt, postal_code_edt, class_edt, lic_exp_edt;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Customer customer;
    //Initialize awesome validation
    final AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        fname_edt = findViewById(R.id.fname_edt);
        lname_edt = findViewById(R.id.lname_edt);
        licence_number_edt = findViewById(R.id.licence_number_edt);
        address_edt = findViewById(R.id.address_edt);
        province_edt = findViewById(R.id.province_edt);
        postal_code_edt =findViewById(R.id.postal_code_edt);
        class_edt = findViewById(R.id.class_edt);
        lic_exp_edt = findViewById(R.id.lic_exp_edt);

        //After the button is clicked save the data
        //All validations
        //Lic no. Cannot be empty
        mAwesomeValidation.addValidation(licence_number_edt, "[a-z, A-Z, 0-9]{15}", "required 15 characters");
        //Required fields
        mAwesomeValidation.addValidation(fname_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(lname_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(address_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(province_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(postal_code_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(class_edt,"^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation(lic_exp_edt,"^(?=\\s*\\S).*$", "Invalid date");

        //Get the Customer object from the bundle
        customer = (Customer) getIntent().getSerializableExtra("Customer");

        if(customer != null){

            fname_edt.setText(customer.getFirstName());
            lname_edt.setText(customer.getLastName());

            db.collection("license")
                    .document(customer.getLicenseId())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if(documentSnapshot != null){

                                licence_number_edt.setText(documentSnapshot.get("license").toString());
                                address_edt.setText(documentSnapshot.get("address").toString());
                                province_edt.setText(documentSnapshot.get("province").toString());
                                postal_code_edt.setText(documentSnapshot.get("zip").toString());
                                class_edt.setText(documentSnapshot.get("clazz").toString());
                                lic_exp_edt.setText(documentSnapshot.get("exp_date").toString());

                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.e("FIRE STORE ERROR", e.getMessage());

                        }
                    });

        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_customer_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.edit_save_customer){

            if(mAwesomeValidation.validate()){

                DocumentReference customerDocument = db.collection("user").document(customer.getId());

                customerDocument.update("firstName", fname_edt.getText().toString());
                customerDocument.update("lastName", lname_edt.getText().toString());

                DocumentReference licenseDocument = db.collection("license").document(customer.getLicenseId());

                licenseDocument.update("license", licence_number_edt.getText().toString());
                licenseDocument.update("address", address_edt.getText().toString());
                licenseDocument.update("clazz", class_edt.getText().toString());
                licenseDocument.update("exp_date", lic_exp_edt.getText().toString());
                licenseDocument.update("name", fname_edt.getText().toString() + " " + lname_edt.getText().toString());
                licenseDocument.update("province", province_edt.getText().toString());
                licenseDocument.update("zip", postal_code_edt.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toasty.success(getApplicationContext(), "Updated Successfully").show();
                                //Back to home
                                startActivity(new Intent(EditCustomer.this, Home.class));

                            }
                        });

            }

        }

        return true;

    }
}
