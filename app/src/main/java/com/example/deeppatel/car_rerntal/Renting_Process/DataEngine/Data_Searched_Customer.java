package com.example.deeppatel.car_rerntal.Renting_Process.DataEngine;

import com.example.deeppatel.car_rerntal.Customer.models.Customer;
import com.example.deeppatel.car_rerntal.Customer.models.License;
import com.example.deeppatel.car_rerntal.Renting_Process.adapters.MySearchedCustomerRecyclerViewAdapter;
import com.example.deeppatel.car_rerntal.Renting_Process.database.SearchUser;
import com.example.deeppatel.car_rerntal.Renting_Process.models.User;

import java.util.ArrayList;
import java.util.List;

public class Data_Searched_Customer {

    public static  List<Customer> searchedCustomerList= new ArrayList<>();
    public static  List<License> searchedCustomersLicense = new ArrayList<>();

    public static  List<User> searchedUser = new ArrayList<>();

    public static List<User> getSearchedUser() {
        return searchedUser;
    }

    public static void setSearchedUser(List<User> searchedUser) {
        Data_Searched_Customer.searchedUser = searchedUser;
    }


    public User getSearchedUser(int position)
    {
        return searchedUser.get(position);
    }
    public static List<License> getSearchedCustomersLicense() {
        return searchedCustomersLicense;
    }

    public static void setSearchedCustomersLicense(List<License> searchedCustomersLicense) {
        Data_Searched_Customer.searchedCustomersLicense = searchedCustomersLicense;
    }


    public int getSearchedCustomersLicenseSize()
    {
        return searchedCustomersLicense.size();
    }

    public License getSearchedLicense(String id)
    {
        int flag=0;
        License l1 = null;
        for(License license:searchedCustomersLicense)
        {
            if(license.getLicense().equals(id))
            {
                l1= license;

            }
        }
        //return searchedCustomersLicense;
        return l1;
    }

    public static List<Customer> getCustomerList() {
        return searchedCustomerList;
    }

    public static void setCustomerList(List<Customer> customerList) {
        Data_Searched_Customer.searchedCustomerList = customerList;
    }

    public int getSearchedCustomerSize()
    {
        return searchedCustomerList.size();
    }
    public Customer getSearchedCustomer(int position)
    {
        return  searchedCustomerList.get(position);
    }


    public void getSearchedCustomerList(MySearchedCustomerRecyclerViewAdapter adapter, String firstName) {
        SearchUser searchUser = new SearchUser();
        //empty list
        searchedCustomerList.clear();
        searchedCustomersLicense.clear();
        searchedUser.clear();
        searchedCustomerList= searchUser.searchCustomer(adapter,firstName);


    }
}
