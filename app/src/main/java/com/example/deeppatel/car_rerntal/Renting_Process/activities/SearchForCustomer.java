package com.example.deeppatel.car_rerntal.Renting_Process.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.fragments.SearchedCustomerFragment;
import com.example.deeppatel.car_rerntal.Renting_Process.models.User;

public class SearchForCustomer extends AppCompatActivity

        implements  SearchedCustomerFragment.OnListFragmentInteractionListener

{
    int flag=0;
    private Car car_selected; //From First Activity

    private EditText et_custoerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_for_customer);
        et_custoerName=findViewById(R.id.et_search_for_customer);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                car_selected= null;
            } else {
                 car_selected= (Car)getIntent().getSerializableExtra("selected_car");
            }
        } else {
            car_selected= (Car) savedInstanceState.getSerializable("selected_car");
        }
    }

    //Button Event
    public void searchCustomer(View view) {

        et_custoerName= findViewById(R.id.et_search_for_customer);
        if(et_custoerName.getText().toString().equals(""))
        {
            et_custoerName.setHint("Please Enter the First Name");
            int color= Color.parseColor("#FF0000");
            et_custoerName.setHintTextColor( color);
        }
        else {

            if(flag==0)
            {
                //Seacrh in db for customer
                //No previous fragment in backstack
                SearchedCustomerFragment cutomersFragment = new SearchedCustomerFragment(et_custoerName.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putString("firstName", et_custoerName.getText().toString());
                // set Fragmentclass Arguments
                cutomersFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.searched_customer_fragment, cutomersFragment,"SearchedCustomer")
                        .commit();
                flag=1;
            }
            else{
                //Seacrh in db for customer
                //fragment exist
                //Remove Fragment from backstack
                //To maintain memory
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("SearchedCustomer");
                getSupportFragmentManager().beginTransaction().remove(fragment);
                //Start New Fragment
                SearchedCustomerFragment cutomersFragment = new SearchedCustomerFragment(et_custoerName.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putString("firstName", et_custoerName.getText().toString());
                // set Fragmentclass Arguments
                cutomersFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.searched_customer_fragment, cutomersFragment,"SearchedCustomer")
                            .commit();

        }}
    }

    //On selecting customer
    @Override
    public void onListFragmentInteraction(User item, int count) {

        if(item.getLicense().getLicense().equals("first name is case sensitive"))
        {
            Toast.makeText(this, "Please Select a valid customer", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this, RentInfo.class);
            intent.putExtra("selected_user",item);
            intent.putExtra("selected_car",car_selected);
            startActivity(intent);
        }
//        Toast.makeText(this, "Customer: "+item.getCustomer().getLastName()+item.getCustomer().getFirstName()
//                +item.getLicense().getLicense(), Toast.LENGTH_SHORT).show();

    }
}
