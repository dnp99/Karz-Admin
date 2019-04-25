package com.example.deeppatel.car_rerntal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.Cars.fragments.CarFragment;
import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Customer.fragments.CustomerFragment;
import com.example.deeppatel.car_rerntal.Customer.SearchCustomer;
import com.example.deeppatel.car_rerntal.Renting_Process.activities.SearchForCustomer;
import com.example.deeppatel.car_rerntal.Renting_Process.fragments.AvailableCarsFragment;
import com.example.deeppatel.car_rerntal.Returning_Process.fragments.ReturnACarFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Stack;

public class Home extends AppCompatActivity
        implements
        AvailableCarsFragment.OnListFragmentInteractionListener {

    private TextView mTextMessage;
    private android.support.v7.app.ActionBar actionBar;
    private Stack fragmentStack;
    private BottomNavigationView navigation;
    private SearchView searchView;
    //Get the fragment manipulators ready for the transaction
    FragmentManager fragmentManager = getSupportFragmentManager();

    //Based on the button clicks, the fragments must be added to the activity

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_customer:

                    replaceFragment(item.getItemId());
//                    whichFragmentIsVisible();

                    return true;

                case R.id.navigation_car:

                    replaceFragment(item.getItemId());
//                    whichFragmentIsVisible();

                    return true;

                case R.id.navigation_rent:

                    replaceFragment(item.getItemId());
//                    whichFragmentIsVisible();

                    return true;

                case R.id.navigation_return_car:

                    replaceFragment(item.getItemId());
//                    whichFragmentIsVisible();

                    return true;

            }
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_home_search_info_menu, menu);

        MenuItem actionOrderSearch = menu.findItem(R.id.item_search);
        searchView = (SearchView) actionOrderSearch.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            //For real time search results
            @Override
            public boolean onQueryTextChange(String s) {

                EventBus.getDefault().post(new SearchCustomer(s));

                return false;
            }
        });

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        actionBar = getSupportActionBar();;

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Initialize the stack
        fragmentStack = new Stack();
        //Add HomeFragment object to it
        replaceFragment(R.id.navigation_rent);
        getPermission();

    }
    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    //Custom methods

//    public void whichFragmentIsVisible(){
//
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//
//        if(fragment != null){
//
//            if(fragment instanceof HomeFragment && fragment.isVisible()){
//
//                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
//
////                setBottomNavigationItemChecked(R.id.navigation_home);
////                setActionBarTitle(getText(R.string.title_home).toString());
//
//            }else if(fragment instanceof CustomerFragment && fragment.isVisible()){
//
//                Toast.makeText(this, "CUST", Toast.LENGTH_SHORT).show();
//
////                setBottomNavigationItemChecked(R.id.navigation_customer);
////                setActionBarTitle(getText(R.string.title_customer).toString());
//
//            }else if(fragment instanceof CarFragment && fragment.isVisible()){
//
//                Toast.makeText(this, "CAR", Toast.LENGTH_SHORT).show();
//
////                setBottomNavigationItemChecked(R.id.navigation_car);
////                setActionBarTitle(getText(R.string.title_car).toString());
//
//            }else if(fragment instanceof RentACarFragment && fragment.isVisible()){
//
//                Toast.makeText(this, "RENT", Toast.LENGTH_SHORT).show();
//
////                setBottomNavigationItemChecked(R.id.navigation_rent);
////                setActionBarTitle(getText(R.string.string_rent).toString());
//
//            }else if(fragment instanceof ReturnACarFragment && fragment.isVisible()){
//
//                Toast.makeText(this, "RETU", Toast.LENGTH_SHORT).show();
//
////                setBottomNavigationItemChecked(R.id.navigation_return_car);
////                setActionBarTitle(getText(R.string.return_car).toString());
//
//            }else{
//                Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//
//
//    }

    public void setActionBarTitle(String title){

        actionBar.setTitle(title);

    }

    public void setBottomNavigationItemChecked(int navId){

        switch (navId) {

            case R.id.navigation_customer:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

            case R.id.navigation_car:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

            case R.id.navigation_rent:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

            case R.id.navigation_return_car:

                navigation.getMenu().findItem(navId).setChecked(true);

                break;

        }

    }

    public void closeSearchView(){

        if(searchView != null){

            if(!searchView.isIconified()){

                searchView.setIconified(true);

            }

        }

    }

    public  void replaceFragment(int fragmentId){

        FragmentTransaction fragmentTransaction;

        CustomerFragment customerFragment;
        AvailableCarsFragment rentACarFragment;
        ReturnACarFragment returnACarFragment;
        CarFragment carFragment;

        //Based on the navigation button clicked, the fragments must be added to the activity

        switch (fragmentId){

            case R.id.navigation_customer:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                customerFragment = new CustomerFragment();
                //Add the fragment to screen
                fragmentTransaction.replace(R.id.fragment_container, customerFragment);
                //whichFragmentIsVisible();
                //Add the fragment to custom fragment stack
                //fragmentTransaction.addToBackStack("Added Cust");
                fragmentTransaction.commit();

                closeSearchView();

                break;

            case R.id.navigation_car:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                carFragment = new CarFragment();
                //Add the fragment to screen
                fragmentTransaction.replace(R.id.fragment_container, carFragment);
                //whichFragmentIsVisible();
                //Add the fragment to custom fragment stack
                //fragmentTransaction.addToBackStack("Added Car");
                fragmentTransaction.commit();

                closeSearchView();

                break;

            case R.id.navigation_rent:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                rentACarFragment = new AvailableCarsFragment();
                //Add the fragment to screen
                fragmentTransaction.replace(R.id.fragment_container, rentACarFragment);
                //whichFragmentIsVisible();
                //Add the fragment to custom fragment stack
                //fragmentTransaction.addToBackStack("Added rent");
                fragmentTransaction.commit();

                closeSearchView();

                break;

            case R.id.navigation_return_car:

                fragmentTransaction = fragmentManager.beginTransaction();

                //Initialize the fragment
                returnACarFragment = new ReturnACarFragment();
                //Add the fragment to screen
                fragmentTransaction.replace(R.id.fragment_container, returnACarFragment);
                //whichFragmentIsVisible();
                //Add the fragment to custom fragment stack
                //fragmentTransaction.addToBackStack("Added return");
                fragmentTransaction.commit();

                closeSearchView();

                break;

        }

    }

    @Override
    public void onListFragmentInteraction(Car item) {
        Intent serachForCustomer = new Intent(this, SearchForCustomer.class);
        serachForCustomer.putExtra("selected_car", item);
        startActivity(serachForCustomer);
    }
}
