package com.example.deeppatel.car_rerntal.Customer;

import android.util.Log;

import com.example.deeppatel.car_rerntal.Customer.models.Customer;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class CustomerEngine {

    private List<Customer> customerListList;

    public CustomerEngine(List<Customer> customerListList) {
        this.customerListList = customerListList;
    }

    public CustomerEngine() {
        customerListList = new ArrayList<>();
    }

    public int getCount(){

        return this.customerListList.size();

    }

    public List<Customer> getCustomerListList() {

        return customerListList;

    }

    public void setCustomerListList(List<Customer> customerListList) {
        this.customerListList = customerListList;
    }

    public Customer getcustomer(int position){

        if(customerListList.size() > 0){

            return customerListList.get(position);

        }

        return null;

    }

    public void addCustomers(final CustomerAdapter customerAdapter){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("user")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        if (e != null) {

                            Log.e("FIRE STORE ERROR", e.getMessage());

                            return;
                        }

                        customerListList = new ArrayList<>();

                        for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){

                            if(queryDocumentSnapshot != null){

                                Log.i("CUSTOMER", queryDocumentSnapshot.get("firstName").toString());

                                if(queryDocumentSnapshot.get("firstName") != null){

                                    if(queryDocumentSnapshot.get("lastName") != null){

                                        if(queryDocumentSnapshot.get("licenseId") != null){

                                            if(queryDocumentSnapshot.get("userId") != null){

                                                customerListList.add(new Customer(
                                                        queryDocumentSnapshot.get("firstName").toString(),
                                                        queryDocumentSnapshot.get("lastName").toString(),
                                                        queryDocumentSnapshot.get("licenseId").toString(),
                                                        queryDocumentSnapshot.get("userId").toString(),
                                                        queryDocumentSnapshot.getId()));


                                            }

                                        }

                                    }

                                }

                            }

                        }

                        //Notify the list and the dependent lists of the data change
                        customerAdapter.notifyDataSetChanged();
                        CustomerAdapter.customerListFull = getCustomerListList();

                    }
                });

    }

    public Customer delCustomer(int position){

        if(customerListList.size() > 0){

            return customerListList.remove(position);

        }

        return null;

    }

}
