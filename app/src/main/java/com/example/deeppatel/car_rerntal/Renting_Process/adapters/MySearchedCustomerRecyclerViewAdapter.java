package com.example.deeppatel.car_rerntal.Renting_Process.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.R;

import com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.Data_Searched_Customer;
import com.example.deeppatel.car_rerntal.Renting_Process.fragments.SearchedCustomerFragment;
import com.example.deeppatel.car_rerntal.Renting_Process.models.User;


public class MySearchedCustomerRecyclerViewAdapter extends RecyclerView.Adapter<MySearchedCustomerRecyclerViewAdapter.ViewHolder> {

    Data_Searched_Customer   searched_customer= new Data_Searched_Customer();;

    private final SearchedCustomerFragment.OnListFragmentInteractionListener mListener;

    public MySearchedCustomerRecyclerViewAdapter( SearchedCustomerFragment.OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_searchedcustomer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = searched_customer.getSearchedUser(position);
        holder.mIdView.setText(String.valueOf(position+1));
        //set License class

       //Set Content to display
        String content=holder.mItem.getCustomer().getCustomerName()+" - "+holder.mItem.getLicense().getLicense();
        holder.mContentView.setText(content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem,1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return searched_customer.getSearchedCustomerSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView; //Number of Item
        public final TextView mContentView; //FirstName+lastName+License#
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
