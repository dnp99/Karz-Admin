package com.example.deeppatel.car_rerntal.Customer;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.deeppatel.car_rerntal.Customer.models.Customer;
import com.example.deeppatel.car_rerntal.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> implements Filterable {

    private CustomerEngine customerEngine = new CustomerEngine();
    private OnCustomerItemClickedListener onCustomerItemClickedListener;
    //Make a duplicate of the customer list
    static List<Customer> customerListFull;

    public CustomerEngine getCustomerEngine() {
        return customerEngine;
    }

    public void setCustomerEngine(CustomerEngine customerEngine) {
        this.customerEngine = customerEngine;
    }

    public OnCustomerItemClickedListener getOnCustomerItemClickedListener() {
        return onCustomerItemClickedListener;
    }

    public void setOnCustomerItemClickedListener(OnCustomerItemClickedListener onCustomerItemClickedListener) {
        this.onCustomerItemClickedListener = onCustomerItemClickedListener;
    }

    public CustomerAdapter(CustomerEngine customerEngine, OnCustomerItemClickedListener onCustomerItemClickedListener) {
        this.customerEngine = customerEngine;
        this.onCustomerItemClickedListener = onCustomerItemClickedListener;
        this.customerListFull = customerEngine.getCustomerListList();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Customer> customerListNew = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                //No query in the search box
                customerListNew.addAll(customerListFull);
            } else {

                String pattern = constraint.toString().toLowerCase().trim();

                for (Customer customer : customerListFull) {

                    if (customer.getCustomerName().toLowerCase().contains(pattern)) {

                        customerListNew.add(customer);

                    }

                }

            }

            FilterResults results = new FilterResults();

            results.values = customerListNew;

            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            customerEngine.setCustomerListList((List) results.values);
            notifyDataSetChanged();

        }

    };

    public CustomerAdapter() {
        this.customerListFull = new ArrayList<>( customerEngine.getCustomerListList() );
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.list_item_customer, viewGroup, false);

        return new ViewHolder(listItem, onCustomerItemClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Customer customer = customerEngine.getcustomer(i);
        viewHolder.customer_name.setText(customer.getCustomerName().toUpperCase());

        GradientDrawable bgShape = (GradientDrawable) viewHolder.circle_ele.getBackground();
        bgShape.setColor(customer.getColor());

    }

    @Override
    public int getItemCount() {
        return customerEngine.getCount();
    }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView customer_name;
        View circle_ele;
        OnCustomerItemClickedListener onCustomerItemClickedListener;
        //Gotta declare image for the car once available


        public ViewHolder(@NonNull View itemView, OnCustomerItemClickedListener onCustomerItemClickedListener) {
            super(itemView);

            customer_name = itemView.findViewById(R.id.customer_name);
            circle_ele = itemView.findViewById(R.id.circle_ele_customer);

            this.onCustomerItemClickedListener = onCustomerItemClickedListener;

            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {

            onCustomerItemClickedListener.onCustomerItemClicked(getAdapterPosition(), v);

        }

    }

    public interface OnCustomerItemClickedListener{

        void onCustomerItemClicked(int position, View view);

    }

}
