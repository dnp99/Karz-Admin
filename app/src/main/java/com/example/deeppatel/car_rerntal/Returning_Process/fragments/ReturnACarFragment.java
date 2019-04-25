package com.example.deeppatel.car_rerntal.Returning_Process.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deeppatel.car_rerntal.Customer.SearchCustomer;
import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Returning_Process.CarsAdapter;
import com.example.deeppatel.car_rerntal.Returning_Process.CarsEngine;
import com.example.deeppatel.car_rerntal.Returning_Process.activities.ReturnDetails;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ReturnACarFragment extends Fragment implements CarsAdapter.OnCarItemClickedListener {

    RecyclerView bookedCarsList;
    CarsEngine carsEngine;
    CarsAdapter carsAdapter;
    private final String CARSTR = "Car";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_booked_cars, container, false);


        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_return_car);

        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.return_car).toString());

        bookedCarsList = rootView.findViewById(R.id.booked_cars_list);
        bookedCarsList.setLayoutManager(new LinearLayoutManager(getContext()));

        carsEngine = new CarsEngine();

        carsAdapter = new CarsAdapter();

        carsAdapter.setCarsEngine(carsEngine);

        carsAdapter.setOnCarItemClickedListener(this);

        carsEngine.addCars(carsAdapter);

        bookedCarsList.setAdapter(carsAdapter);

        return rootView;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchQuery(SearchCustomer event) {
        String query=event.getQuery();
        carsAdapter.getFilter().filter(query);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_return_car);

        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.return_car).toString());

        EventBus.getDefault().register(this);

    }

    @Override
    public void onCarItemClicked(int position, View view) {

        //Call the details page intent, here...
        Intent toReturnDetails = new Intent(getContext(), ReturnDetails.class);
        toReturnDetails.putExtra(CARSTR, carsEngine.getCar(position));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                // the context of the activity
                (Activity) getContext(),

                // For each shared element, add to this method a new Pair item,
                // which contains the reference of the view we are transitioning *from*,
                // and the value of the transitionName attribute
                new Pair<View, String>(view.findViewById(R.id.circle_ele),
                        getString(R.string.transition_name_circle_ele)),
                new Pair<View, String>(view.findViewById(R.id.car_name),
                        getString(R.string.transition_name_car_name))
        );
        ActivityCompat.startActivity(getContext(), toReturnDetails, options.toBundle());

    }

    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        super.onPause();
    }




}
