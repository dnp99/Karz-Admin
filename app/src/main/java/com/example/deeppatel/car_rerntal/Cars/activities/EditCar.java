package com.example.deeppatel.car_rerntal.Cars.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.deeppatel.car_rerntal.Cars.database.UpdateCar;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class EditCar extends AppCompatActivity {

    private EditText name,model ,mileage,status;
    private ImageView imageView;
    Car editCar;
    //Initialize awesome validation
    final AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);
        android.support.v7.app.ActionBar actionBar= getSupportActionBar();//ActionBar();
        actionBar.setTitle("Edit a Car");

        name = findViewById(R.id.EDIT_name);
        model=findViewById(R.id.EDIT_model);
        mileage=findViewById(R.id.EDIT_mileage);
        status=findViewById(R.id.EDIT_status);
        imageView=findViewById(R.id.iv_edit_car);

        editCar= (Car) getIntent().getSerializableExtra("Car");
        if(editCar != null){
            name.setText(editCar.getName());
            model.setText(editCar.getModel());
            mileage.setText(String.valueOf(editCar.getMileage()));
            String status1="";
            if(editCar.getStatus().equals("false")){
                status1="Booked";
            }
            else
            {
                status1="Available";
            }
            status.setText(status1);
            status.setEnabled(false);
            Picasso.get().load(editCar.getImage()).placeholder(R.drawable.default_placeholder).into(imageView);

        }

        mAwesomeValidation.addValidation( name, "^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation( model, "^(?=\\s*\\S).*$", "Required");
        mAwesomeValidation.addValidation( mileage, "^(?=\\s*\\S).*$", "Required");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_car_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.edit_save)
        {
            if(mAwesomeValidation.validate())
            {
                Car updatedCar= new Car();
                updatedCar.setID(editCar.getID());
                updatedCar.setName(name.getText().toString());
                updatedCar.setModel(model.getText().toString());
                updatedCar.setMileage((mileage.getText().toString()));
                updatedCar.setStatus((status.getText().toString()));
                UpdateCar newCar = new UpdateCar();
                newCar.updateData(updatedCar);

                Toasty.success(getApplicationContext(), "Updated Successfully").show();

                Intent toHome = new Intent(getApplicationContext(), Home.class);
                startActivity(toHome);

            }

        }

        return true;

    }
}
