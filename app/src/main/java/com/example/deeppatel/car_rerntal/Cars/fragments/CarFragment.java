package com.example.deeppatel.car_rerntal.Cars.fragments;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deeppatel.car_rerntal.Cars.CarAdapter;
import com.example.deeppatel.car_rerntal.Cars.CarEngine;
import com.example.deeppatel.car_rerntal.Cars.activities.AddNewCar;
import com.example.deeppatel.car_rerntal.Cars.activities.EditCar;
import com.example.deeppatel.car_rerntal.Customer.SearchCustomer;
import com.example.deeppatel.car_rerntal.Home;
import com.example.deeppatel.car_rerntal.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import es.dmoral.toasty.Toasty;

public class CarFragment extends Fragment implements CarAdapter.OnAllCarItemClickedListener {

    RecyclerView allCarsList;
    CarEngine carsEngine;
    CarAdapter carsAdapter;
    public final String CARSTR = "Car";
    FloatingActionButton addCar;
    Paint paint = new Paint();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.car_fragment_layout, container, false);

        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_car);

        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.title_car).toString());

        allCarsList = rootView.findViewById(R.id.all_cars_list);
        allCarsList.setLayoutManager(new LinearLayoutManager(getContext()));
        carsEngine= new CarEngine();

        carsAdapter = new CarAdapter(carsEngine, this);
        carsEngine.addCars(carsAdapter);
        allCarsList.setAdapter(carsAdapter);
        //For fab Button
        addCar = rootView.findViewById(R.id.fb_add_car);
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewCar = new Intent(getContext(), AddNewCar.class);
                startActivity(addNewCar);
//                Intent text = new Intent(getContext(), TextRecognition.class);
//                startActivity(text);

            }
        });


    /************************************  Swipe To delete Functionality **********************************************/
        ItemTouchHelper swipeToDel = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

                //Delete the customer from the list
                //Here the is not the item i.e. is swiped
                Snackbar.make(rootView, R.string.confirm_cust_del , Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.delete_confirm_snackbar, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Make sure the user wants to delete the customer after all
                                Toasty.success(getContext(), carsEngine.getCar(viewHolder.getAdapterPosition()).getName()+ " is deleted").show();
//                                Toast.makeText(getContext(),
//                                        carsEngine.getCar(viewHolder.getAdapterPosition()).getName()+ " is deleted" ,
//                                        Toast.LENGTH_SHORT).show();
                                carsEngine.deleteCar(viewHolder.getAdapterPosition());
                                carsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                carsAdapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), carsAdapter.getItemCount());
                            }
                        }).show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                paint.setColor(Color.RED);
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    if(dX > 0){
                        c.drawRect((float) viewHolder.itemView.getLeft(), (float) viewHolder.itemView.getTop(), dX,
                                (float) viewHolder.itemView.getBottom(), paint);
                    }else{

                        c.drawRect((float) viewHolder.itemView.getRight() + dX, (float) viewHolder.itemView.getTop(),
                                (float) viewHolder.itemView.getRight(), (float) viewHolder.itemView.getBottom(), paint);
                    }

                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });

        swipeToDel.attachToRecyclerView(allCarsList);

        /************************************  Swipe To delete Functionality  Over  **********************************************/

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //Set the bottom navigation to home
        ((Home) getActivity()).setBottomNavigationItemChecked(R.id.navigation_car);

        //Set the actionbar title to home
        ((Home) getActivity()).setActionBarTitle(getText(R.string.title_car).toString());

        EventBus.getDefault().register(this);

    }

    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        super.onPause();
    }

    @Override
    public void onAllCarItemClicked(int position, View view) {

        Intent toEditCar = new Intent(getContext(), EditCar.class);
        toEditCar.putExtra(CARSTR, carsEngine.getCar(position));
        startActivity(toEditCar);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchQuery(SearchCustomer event)
    {
        String query=event.getQuery();
        carsAdapter.getFilter().filter(query);
    }

}
