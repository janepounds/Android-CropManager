package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords.livestockrecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.LivestockSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.models.livestock_models.Litter;
import com.myfarmnow.myfarmcrop.models.livestock_models.LivestockSpinnerItem;

import java.util.ArrayList;

public class AddLittersFragment extends DialogFragment {
    private Context context;
    private NavController navController;
    private Litter litter;
    private MyFarmDbHandlerSingleton dbHandler;
    private EditText litterDob,litterSize,bornAlive,bornDead,noOfMale,noOfFemale,weaning,weaningAlert;
    private ImageView close,datePicker;
    private Spinner motherDam,fatherSire;
    private Button submit;
    public LivestockSpinnerAdapter litterSpinnerAdapter;




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
            litter = (Litter) getArguments().getSerializable("litter");
        }
        View view =getLayoutInflater().inflate(R.layout.fragment_add_litters, null);
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
        close = view.findViewById(R.id.add_litter_close);
        litterDob = view.findViewById(R.id.add_litter_dob);
        litterSize = view.findViewById(R.id.add_litter_litters_size);
        bornAlive = view.findViewById(R.id.add_litter_born_alive_et);
        bornDead = view.findViewById(R.id.add_litter_born_dead_et);
        motherDam = view.findViewById(R.id.add_litter_mother_dam_spinner);
        fatherSire = view.findViewById(R.id.add_litter_father_sire_spinner);
        noOfMale = view.findViewById(R.id.add_litter_number_of_males_et);
        noOfFemale = view.findViewById(R.id.add_litter_number_of_females_et);
        weaning = view.findViewById(R.id.add_litter_weaning_et);
        weaningAlert = view.findViewById(R.id.add_litter_weaning_alert_et);
        datePicker = view.findViewById(R.id.image_dob);
        submit = view.findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        DashboardActivity.addDatePickerImageView(datePicker,litterDob,context);
        ArrayList<LivestockSpinnerItem> motherDams = new ArrayList<>();
        for (BreedingStock x : dbHandler.getFemaleBreeds(DashboardActivity.RETRIEVED_USER_ID,"Female")) {
            motherDams.add(new LivestockSpinnerItem() {
                @Override
                public String getId() {
                    return null;
                }

                @NonNull
                @Override
                public String toString() {
                    return x.getName();
                }
            });
        }

        litterSpinnerAdapter = new LivestockSpinnerAdapter(motherDams, "Mother", context);
        motherDam.setAdapter(litterSpinnerAdapter);

        ArrayList<LivestockSpinnerItem> fatherSires = new ArrayList<>();

        for (BreedingStock x : dbHandler.getMaleBreeds(DashboardActivity.RETRIEVED_USER_ID, "Male")) {
            fatherSires.add(new LivestockSpinnerItem() {
                @Override
                public String getId() {
                    return null;
                }

                @NonNull
                @Override
                public String toString() {
                    return x.getName();
                }
            });
        }

        litterSpinnerAdapter = new LivestockSpinnerAdapter(fatherSires, "Father", context);
        fatherSire.setAdapter(litterSpinnerAdapter);
        close.setOnClickListener(view1 -> dismiss());

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
                    if(litter==null){
                        saveLitter();
                    }
                    else{
                        updateLitter();
                    }
                    //dismiss dialog and refresh fragment
                    navController = Navigation.findNavController(getParentFragment().getView());
                    navController.navigate(R.id.action_addLittersFragment_to_littersViewFragment);




                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }
    public void fillViews(){
        if(litter != null){
            litterDob.setText(litter.getDateOfBirth());
            litterSize.setText(litter.getLitterSize());
            bornAlive.setText(litter.getBornAlive());
            bornDead.setText(litter.getBornDead());
            noOfFemale.setText(litter.getNoOfFemale());
            noOfMale.setText(litter.getNoOfMale());
            weaning.setText(litter.getWeaning());
            weaningAlert.setText(litter.getWeaningAlert());
            DashboardActivity.selectSpinnerItemByValue(motherDam,litter.getMotherDam());
            DashboardActivity.selectSpinnerItemByValue(fatherSire,litter.getFatherSire());


        }
    }
    public void saveLitter(){
        litter = new Litter();
        litter.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        litter.setDateOfBirth(litterDob.getText().toString());
        litter.setLitterSize(Integer.parseInt(litterSize.getText().toString()));
        litter.setBornAlive(Integer.parseInt(bornAlive.getText().toString()));
        litter.setBornDead(Integer.parseInt(bornDead.getText().toString()));
        litter.setNoOfFemale(Integer.parseInt(noOfFemale.getText().toString()));
        litter.setNoOfMale(Integer.parseInt(noOfMale.getText().toString()));
        litter.setWeaning(Integer.parseInt(weaning.getText().toString()));
        litter.setMotherDam(motherDam.getSelectedItem().toString());
        litter.setFatherSire(fatherSire.getSelectedItem().toString());
        litter.setWeaningAlert(Integer.parseInt(weaningAlert.getText().toString()));
        dbHandler.insertLitter(litter);



    }
    public void updateLitter(){

        if(litter != null) {
            litter.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            litter.setDateOfBirth(litterDob.getText().toString());
            litter.setLitterSize(Integer.parseInt(litterSize.getText().toString()));
            litter.setBornAlive(Integer.parseInt(bornAlive.getText().toString()));
            litter.setBornDead(Integer.parseInt(bornDead.getText().toString()));
            litter.setNoOfFemale(Integer.parseInt(noOfFemale.getText().toString()));
            litter.setNoOfMale(Integer.parseInt(noOfMale.getText().toString()));
            litter.setWeaning(Integer.parseInt(weaning.getText().toString()));
            litter.setMotherDam(motherDam.getSelectedItem().toString());
            litter.setFatherSire(fatherSire.getSelectedItem().toString());
            litter.setWeaningAlert(Integer.parseInt(weaningAlert.getText().toString()));
            dbHandler.updateLitter(litter);
        }
    }

    public boolean validateEntries(){
        String message = null;
        if(litterDob.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            litterDob.requestFocus();
        }
        else if(litterSize.getText().toString().isEmpty()){
            message = getString(R.string.litter_size_not_entered);
            litterSize.requestFocus();
        }

        else if(fatherSire.getSelectedItemPosition()==0){
            message = getString(R.string.father_sire_not_selected);
            fatherSire.requestFocus();
        }else if(motherDam.getSelectedItemPosition()==0){
            message = getString(R.string.mother_dam_not_selected);
            motherDam.requestFocus();
        }
        else if(bornAlive.getText().toString().isEmpty()){
            message = getString(R.string.born_alive_not_entered);
            bornAlive.requestFocus();
        }else if(bornDead.getText().toString().isEmpty()){
            message = getString(R.string.born_dead_not_entered);
            bornDead.requestFocus();
        }else if(noOfMale.getText().toString().isEmpty()){
            message = getString(R.string.no_of_male_not_entered);
            noOfMale.requestFocus();
        }else if(noOfFemale.getText().toString().isEmpty()){
            message = getString(R.string.no_of_female_not_entered);
            noOfFemale.requestFocus();
        }else if(weaning.getText().toString().isEmpty()){
            message = getString(R.string.weaning_days_not_entered);
            weaning.requestFocus();
        }else if(weaningAlert.getText().toString().isEmpty()){
            message = getString(R.string.weaning_alert_not_entered);
            weaningAlert.requestFocus();
        }

        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}