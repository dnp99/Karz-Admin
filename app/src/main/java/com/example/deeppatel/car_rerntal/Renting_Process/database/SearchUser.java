package com.example.deeppatel.car_rerntal.Renting_Process.database;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.deeppatel.car_rerntal.Customer.models.Customer;
import com.example.deeppatel.car_rerntal.Customer.models.License;
import com.example.deeppatel.car_rerntal.Renting_Process.adapters.MySearchedCustomerRecyclerViewAdapter;
import com.example.deeppatel.car_rerntal.Renting_Process.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.Data_Searched_Customer.searchedCustomerList;
import static com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.Data_Searched_Customer.searchedCustomersLicense;
import static com.example.deeppatel.car_rerntal.Renting_Process.DataEngine.Data_Searched_Customer.searchedUser;

public class SearchUser {

    License tempLicense;
    List<Customer> searchedCustomer= new ArrayList<>();


    public List<Customer> searchCustomer(MySearchedCustomerRecyclerViewAdapter adapter, String firstName)
    {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")

                .whereEqualTo("firstName", firstName) // <-- This line
                .get()
                .addOnCompleteListener(task -> {
                    if(task.getResult().isEmpty())
                    {

                        Customer customer= new Customer();
                        customer.setFirstName("No Customer with first name ");
                        customer.setLastName(firstName+ " was found");
                        searchedCustomerList.add(customer);
                        User user= new User();
                        user.setCustomer(customer);
                        License license=new License();
                        license.setLicense("first name is case sensitive");
                        user.setLicense(license);
                        searchedUser.add(user);
                        adapter.notifyDataSetChanged();

                    }
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {

                            if (document.exists()){
                                Log.d("user-db"," user found");
                            }
                            else{
                                Log.d("user-db","No user found");
                            }
                            Log.d("Searched_cust", document.getId() + " => " + document.getData());
                            Customer customer= document.toObject(Customer.class);
                            customer.setId(document.getId());
                            //get License Information from License Collection
                            getLicense(adapter,customer);

                        }

                        //Notify Recycler View
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());

                    }

                });
        Log.d("searched_cust_size", searchedCustomer.size()+firstName);


        return  searchedCustomer;
    }


public License getLicense(MySearchedCustomerRecyclerViewAdapter adapter,Customer customer)
{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef = db.collection("license").document(customer.getLicenseId());
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                        License license = document.toObject(License.class);
                        license.setId(document.getId());
                        Log.d("lic", license.toString());
                        tempLicense = license;
                        searchedCustomer.add(customer);
                        searchedCustomersLicense.add(license);
                        User user = new User();
                        user.setCustomer(customer);
                        user.setLicense(license);
                        searchedUser.add(user);

                    adapter.notifyDataSetChanged();

                    Log.d("searched_license_global", String.valueOf(searchedCustomersLicense.get(0)));

                } else {
                    Log.d(TAG, "No License document");
                }
            } else {
                Log.d(TAG, "License get failed with ", task.getException());
            }
        }
    });

    return null;
}
}