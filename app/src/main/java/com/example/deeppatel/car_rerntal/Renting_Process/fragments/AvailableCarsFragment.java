package com.example.deeppatel.car_rerntal.Renting_Process.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.Customer.SearchCustomer;
import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.Data_Available_car;
import com.example.deeppatel.car_rerntal.Renting_Process.adapters.MyAvailableCarsRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AvailableCarsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MyAvailableCarsRecyclerViewAdapter adapter;
    private  Data_Available_car engine;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AvailableCarsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AvailableCarsFragment newInstance(int columnCount) {
        AvailableCarsFragment fragment = new AvailableCarsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_availablecars_list, container, false);

        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_rent);
        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.string_rent).toString());

        Log.i("INFO", "HOME CRE VIEW");

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                //Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            engine= new Data_Available_car();
            adapter = new MyAvailableCarsRecyclerViewAdapter(engine, mListener);
            engine.addCars(adapter);

            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: UpdateCar argument type and name
        void onListFragmentInteraction(Car item);
    }

    /**
     * A fragment representing a list of Items.
     * <p/>
     * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
     * interface.
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchQuery(SearchCustomer event)
    {
        String query=event.getQuery();
        adapter.getFilter().filter(query);
    }
}
