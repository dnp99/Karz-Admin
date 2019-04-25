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

import com.example.deeppatel.car_rerntal.Customer.SearchCustomer;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.Data_Searched_Customer;
import com.example.deeppatel.car_rerntal.Renting_Process.adapters.MySearchedCustomerRecyclerViewAdapter;
import com.example.deeppatel.car_rerntal.Renting_Process.models.User;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SearchedCustomerFragment extends Fragment
{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MySearchedCustomerRecyclerViewAdapter adapter;
    private Data_Searched_Customer engine;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    private  String customerName="";

    public SearchedCustomerFragment() {
    }

    public SearchedCustomerFragment(String toString) {
        this.customerName=toString;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SearchedCustomerFragment newInstance(int columnCount) {
        SearchedCustomerFragment fragment = new SearchedCustomerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /***********Pass Customer name to create data if same name for multiple customer found in database***/
        //Data_Searched_Customer.CreateList(customerName);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //get Text from text view
        //firstName = getArguments().getString("firstName");
        View view = inflater.inflate(R.layout.fragment_searchedcustomer_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            engine= new Data_Searched_Customer();
            adapter = new MySearchedCustomerRecyclerViewAdapter( mListener);
            engine.getSearchedCustomerList(adapter,customerName);

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
        void onListFragmentInteraction(User item, int count);

    }


}
