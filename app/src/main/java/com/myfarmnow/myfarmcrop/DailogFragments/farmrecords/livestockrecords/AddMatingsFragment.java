package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords.livestockrecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
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
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.LivestockSpinnerAdapter;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.MatingListAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.models.livestock_models.Litter;
import com.myfarmnow.myfarmcrop.models.livestock_models.LivestockSpinnerItem;
import com.myfarmnow.myfarmcrop.models.livestock_models.Mating;

import java.util.ArrayList;

public class AddMatingsFragment extends DialogFragment {
    private Context context;
    private Mating mating;
    private MyFarmDbHandlerSingleton dbHandler;
    NavController navController;
    private EditText matingDate,femaleName,maleName,deliveryAlert,note,gestationPeriod;
    private Button submit;
    private Spinner method;
    private ImageView close;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context =context;
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomAlertDialog);

        //get arguments for edit
        if(getArguments()!=null){
            mating = (Mating) getArguments().getSerializable("mating");
        }
        View view =getLayoutInflater().inflate(R.layout.fragment_add_matings, null);
        initializeForm(view);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);


    }


    public void initializeForm(View view){
        close = view.findViewById(R.id.add_mating_close);
        matingDate = view.findViewById(R.id.add_mating_date);
        femaleName = view.findViewById(R.id.add_mating_female_name);
        maleName = view.findViewById(R.id.add_mating_male_name);
        method = view.findViewById(R.id.add_mating_method);
        gestationPeriod = view.findViewById(R.id.add_mating_gestation_period);
        deliveryAlert = view.findViewById(R.id.add_mating_delivery_alert);
        note = view.findViewById(R.id.add_mating_note);
        submit = view.findViewById(R.id.add_mating_submit);
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(mating==null){
                        saveMating();
                    }
                    else{
                        updateMating();
                    }
                    //dismiss dialog and refresh fragment
                    navController = Navigation.findNavController(getParentFragment().getView());
                    navController.popBackStack();




                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
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