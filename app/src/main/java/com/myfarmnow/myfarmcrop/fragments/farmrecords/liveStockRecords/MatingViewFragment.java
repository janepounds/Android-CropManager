package com.myfarmnow.myfarmcrop.fragments.farmrecords.liveStockRecords;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.BreedingStockListAdapter;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.MatingListAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.models.livestock_models.Mating;

import java.util.ArrayList;

public class MatingViewFragment extends Fragment {
    private static final String TAG = "MatingViewFragment";
    private Context context;
    private Mating mating;
    private MatingListAdapter matingListAdapter;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MyFarmDbHandlerSingleton dbHandler;
    NavController navController;
    private Dialog dialog;
    public ArrayList<Mating> matingArrayList = new ArrayList();
    private LinearLayoutManager linearLayoutManager;
    private EditText matingDate,femaleName,maleName,deliveryAlert,note,gestationPeriod;
    private Button submit;
    private Spinner method;
    private  ImageView close;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mating_view, container, false);

        toolbar = view.findViewById(R.id.toolbar_matings);
        recyclerView = view.findViewById(R.id.matings_recyclerView);

        setHasOptionsMenu(true);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> navController.popBackStack());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        matingListAdapter = new MatingListAdapter(context, matingArrayList);
        recyclerView.setAdapter(matingListAdapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadMatings();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mating_view_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_new_mating:
                addMating();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addMating() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);

        View addMatingDialog = getLayoutInflater().inflate(R.layout.add_mating_dialog, null);

        close = addMatingDialog.findViewById(R.id.add_mating_close);
        matingDate = addMatingDialog.findViewById(R.id.add_mating_date);
        femaleName = addMatingDialog.findViewById(R.id.add_mating_female_name);
        maleName = addMatingDialog.findViewById(R.id.add_mating_male_name);
        method = addMatingDialog.findViewById(R.id.add_mating_method);
        gestationPeriod = addMatingDialog.findViewById(R.id.add_mating_gestation_period);
        deliveryAlert = addMatingDialog.findViewById(R.id.add_mating_delivery_alert);
        note = addMatingDialog.findViewById(R.id.add_mating_note);
        submit = addMatingDialog.findViewById(R.id.add_mating_submit);
        DashboardActivity.addDatePicker(matingDate,context);
        ((ArrayAdapter)method.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position == 0){
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }

                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        fillViews();
        close.setOnClickListener(view -> dialog.dismiss());

        submit.setOnClickListener(v -> {
            if(validateEntries()){
                if(mating==null){
                    saveMating();
                }
                else{
                    updateMating();
                }
                //dismiss dialog and refresh fragment

                dialog.dismiss();
                loadMatings();



            }else{
                Log.d("ERROR","Testing");
            }
        });

        builder.setView(addMatingDialog);
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }
    private void loadMatings(){
        matingListAdapter.clearMatingList();
        matingListAdapter.addList(dbHandler.getMatings(DashboardActivity.RETRIEVED_USER_ID));

    }

    public void fillViews(){
        if(mating != null){
            matingDate.setText(mating.getMatingDate());
            femaleName.setText(mating.getFemaleName());
            maleName.setText(mating.getMaleName());
            deliveryAlert.setText(mating.getDeliveryAlertDaysBefore() + "");
            note.setText(mating.getNotes());
            gestationPeriod.setText(mating.getGestationPeriod()+"");
            DashboardActivity.selectSpinnerItemByValue(method,mating.getMethod());



        }
    }
    public void saveMating(){
        mating = new Mating();
        mating.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        mating.setMatingDate(matingDate.getText().toString());
        mating.setFemaleName(femaleName.getText().toString());
        mating.setMaleName(maleName.getText().toString());
        mating.setDeliveryAlertDaysBefore(Float.parseFloat(deliveryAlert.getText().toString()));
        mating.setGestationPeriod(Float.parseFloat(gestationPeriod.getText().toString()));
        mating.setNotes(note.getText().toString());
        mating.setMethod(method.getSelectedItem().toString());
        dbHandler.insertMating(mating);



    }
    public void updateMating(){

        if(mating != null) {
            mating.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            mating.setMatingDate(matingDate.getText().toString());
            mating.setFemaleName(femaleName.getText().toString());
            mating.setMaleName(maleName.getText().toString());
            mating.setDeliveryAlertDaysBefore(Float.parseFloat(deliveryAlert.getText().toString()));
            mating.setGestationPeriod(Float.parseFloat(gestationPeriod.getText().toString()));
            mating.setNotes(note.getText().toString());
            mating.setMethod(method.getSelectedItem().toString());
            dbHandler.updateMating(mating);
        }
    }

    public boolean validateEntries(){
        String message = null;
        if(matingDate.getText().toString().isEmpty()){
            message = getString(R.string.mating_date_not_entered);
            matingDate.requestFocus();
        }
        else if(femaleName.getText().toString().isEmpty()){
            message = getString(R.string.female_name_not_entered);
            femaleName.requestFocus();
        }

        else if(maleName.getText().toString().isEmpty()){
            message = getString(R.string.male_name_not_entered);
            maleName.requestFocus();
        }else if(deliveryAlert.getText().toString().isEmpty()){
            message = getString(R.string.deliverey_alert_not_entered);
            deliveryAlert.requestFocus();
        }
        else if(method.getSelectedItemPosition()==0){
            message = getString(R.string.method_not_selected);
            method.requestFocus();
        }else if(note.getText().toString().isEmpty()){
            message = getString(R.string.note_not_entered);
            note.requestFocus();
        }
        else if(gestationPeriod.getText().toString().isEmpty()){
        message = getString(R.string.gestation_period_not_entered);
        gestationPeriod.requestFocus();
        }


        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}