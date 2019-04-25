package com.example.deeppatel.car_rerntal.Renting_Process.adapters;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.Cars.models.Car;
import com.example.deeppatel.car_rerntal.R;
import com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.Data_Available_car;
import com.example.deeppatel.car_rerntal.Renting_Process.fragments.AvailableCarsFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class MyAvailableCarsRecyclerViewAdapter extends RecyclerView.Adapter<MyAvailableCarsRecyclerViewAdapter.ViewHolder>implements Filterable {

    private final Data_Available_car mValues;
    private final OnListFragmentInteractionListener mListener;
    public static List<Car> rent_carListToCompare;

    public MyAvailableCarsRecyclerViewAdapter(Data_Available_car items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_availablecars, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.car=mValues.getCar(position);
        holder.car_name.setText(holder.car.getName().toUpperCase() );
        holder.model.setText(holder.car.getModel().toUpperCase());
        Car c= new Car();
        GradientDrawable bgShape = (GradientDrawable) holder.circle_ele.getBackground();
        bgShape.setColor(c.getColor());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.car);
                }
            }
        });
    }

    Data_Available_car data_available_car= new Data_Available_car();
    @Override
    public int getItemCount() {
        return data_available_car.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public  Car car;
        public final TextView car_name, model;
        public final View circle_ele;
        public final View  mView;

        public ViewHolder(View view) {
            super(view);
            mView=view;
            car_name = itemView.findViewById(R.id.available_car_name);
            model=itemView.findViewById(R.id.available_car_model);
            circle_ele=itemView.findViewById(R.id.available_car_CONTACT_circle);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + car_name.getText() + "'";
        }
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Car> carListNew = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                //No query in the search box
                carListNew.addAll(rent_carListToCompare);
            } else {

                String pattern = constraint.toString().toLowerCase().trim();

                for (Car car : rent_carListToCompare) {

                    if (car.getName().toLowerCase().contains(pattern)) {

                        carListNew.add(car);

                    }

                }

            }
            FilterResults results = new FilterResults();
            results.values = carListNew;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            Data_Available_car.setAvailable_carList((List) results.values);
            notifyDataSetChanged();
        }
    };
}
