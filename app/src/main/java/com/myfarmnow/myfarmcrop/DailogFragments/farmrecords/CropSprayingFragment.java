package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropSpraying;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;
import com.myfarmnow.myfarmcrop.utils.SharedPreferenceHelper;

import java.util.ArrayList;


public class CropSprayingFragment extends DialogFragment {
    EditText dateTxt, rateTxt, reasonTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    Button btn_save;
    CropSpraying cropSpraying;
    LinearLayout daysBeforeLayout,remindersLayout;
    String cropId;
    MyFarmDbHandlerSingleton dbHandler;
    Spinner recurrenceSp,remindersSp, SprayUnits;
    AutoCompleteTextView sprayName;

    ImageView datePicker,sprayClose;
    TextView rateUnitsTextView,currencyTxt;
    private Context context;
    private NavController navController;

    private  String sprayType;
    private String pickedSprayId;
    private String pickedSprayName;
    private  ArrayList<CropSpinnerItem> spraysList = new ArrayList<>();



    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomAlertDialog);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view =inflater.inflate(R.layout.fragment_crop_spraying, null);
        builder.setView(view);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        if(getArguments()!=null){
            cropId = getArguments().getString("cropId");
            cropSpraying = (CropSpraying) getArguments().getSerializable("cropSpraying");
        }
        initializeForm(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context =context;
    }

    public void initializeForm(View view){
        dateTxt =view.findViewById(R.id.txt_crop_spraying_treatment_date);
        datePicker = view.findViewById(R.id.image_date_picker);
        sprayClose = view.findViewById(R.id.crop_spraying_close);
        rateTxt =view.findViewById(R.id.txt_crop_spraying_rate);
        reasonTxt =view.findViewById(R.id.txt_crop_spraying_treatment_reason);
        rateUnitsTextView =view.findViewById(R.id.txt_crop_spraying_rate_units);
        sprayName =view.findViewById(R.id.sp_crop_spraying_name);
        remindersSp = view.findViewById(R.id.sp_crop_spraying_reminders);
        recurrenceSp = view.findViewById(R.id.sp_crop_spraying_recurrence);
        daysBeforeTxt = view.findViewById(R.id.txt_crop_fertilizer_application_days_before);
        daysBeforeLayout = view.findViewById(R.id.layout_crop_spraying_days_before);
        remindersLayout = view.findViewById(R.id.layout_crop_spraying_reminders);
        sprayClose.setOnClickListener(v -> getDialog().dismiss());
        SprayUnits=view.findViewById(R.id.spinner_crop_spraying_unit);

        DashboardActivity.addDatePicker(dateTxt,context);
        datePicker.setOnClickListener(v ->DashboardActivity.addDatePicker(dateTxt,context));
        recurrenceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position ==0){
                        ((TextView) view).setTextColor(Color.GRAY);
                    }
                    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                String selection = parent.getItemAtPosition(position).toString();


                if(selection.toLowerCase().equals("once")){
                    remindersLayout.setVisibility(View.GONE);
                    daysBeforeLayout.setVisibility(View.GONE);
                }else {
                    remindersLayout.setVisibility(View.VISIBLE);
//                    remindersSp.setSelection(0);
                    daysBeforeLayout.setVisibility(View.VISIBLE);
                }



            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        remindersSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position ==0){
                        ((TextView) view).setTextColor(Color.GRAY);
                    }
                   else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                String selection = parent.getItemAtPosition(position).toString();
                if(selection.toLowerCase().equals("yes")){
                    remindersLayout.setVisibility(View.VISIBLE);
                    daysBeforeLayout.setVisibility(View.VISIBLE);
                }
                else{
                    daysBeforeLayout.setVisibility(View.GONE);
                    remindersLayout.setVisibility(View.GONE);
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btn_save = view.findViewById(R.id.btn_save);

        if(cropSpraying!=null)
            btn_save.setText(getString(R.string.update));


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropSpraying ==null){
                        saveSpraying();
                    }
                    else{
                        updateSpraying();
                    }
                    navController = Navigation.findNavController(getParentFragment().getView());
                    //navigate to crop list activities
                    Bundle bundle = new Bundle();
                    bundle.putString("cropId",cropId);
                    navController.navigate(R.id.action_cropSprayingFragment_to_cropActivitiesListFragment,bundle);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);



        //load spray name from autocomplete
        for(CropInventorySpray x: dbHandler.getCropSpray(DashboardActivity.RETRIEVED_USER_ID)){
            spraysList.add(new CropSpinnerItem() {
                @Override
                public String getId() {
                    return x.getId();
                }

                @NonNull
                @Override
                public String toString() {
                    return x.getName();
                }

                @Override
                public String getUnits(){
                    return x.getUsageUnits();
                };
            });
        }
        ArrayAdapter<CropSpinnerItem> sprayAdapter = new ArrayAdapter<CropSpinnerItem>(context,  android.R.layout.simple_dropdown_item_1line, spraysList);
        sprayName.setThreshold(1);
        sprayName.setAdapter(sprayAdapter);
        sprayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sprayName.showDropDown();
            }
        });


        sprayName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(spraysList.get(position).getUnits()!= null){

                    DashboardActivity.selectSpinnerItemByValue(SprayUnits, spraysList.get(position).getUnits());
                    rateUnitsTextView.setText(spraysList.get(position).getUnits()+"");
                    //sprayType=inventorySpray.getType();
                }
            }
        });
        fillViews();
    }

    public void saveSpraying(){
        for (int i = 0; i < spraysList.size(); i++) {

            if (sprayName.getText().toString().equals(spraysList.get(i).toString())) {
                pickedSprayId = spraysList.get(i).getId();

            } else {
                pickedSprayId = null;

            }
            pickedSprayName = sprayName.getText().toString();
        }
        cropSpraying = new CropSpraying();
        cropSpraying.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        cropSpraying.setRate(Float.parseFloat(rateTxt.getText().toString()));
        cropSpraying.setCropId(cropId);
        cropSpraying.setCost(Float.parseFloat("0.00"));
        cropSpraying.setDate(dateTxt.getText().toString());
        cropSpraying.setTreatmentReason(reasonTxt.getText().toString());
        cropSpraying.setSprayId(pickedSprayId);
        cropSpraying.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cropSpraying.setDaysBefore(Float.parseFloat("0"+daysBeforeTxt.getText().toString()));
        cropSpraying.setOperator(DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_LAST_NAME,getContext()));
        cropSpraying.setReminders(remindersSp.getSelectedItem().toString());
        cropSpraying.setSprayType(sprayType);
        cropSpraying.setUnits(SprayUnits.getSelectedItem().toString());


        dbHandler.insertCropSpraying(cropSpraying);

    }

    public void updateSpraying(){
        if(cropSpraying != null){
            for (int i = 0; i < spraysList.size(); i++) {

                if (sprayName.getText().toString().equals(spraysList.get(i).toString())) {
                    pickedSprayId = spraysList.get(i).getId();

                } else {
                    pickedSprayId = null;

                }
                pickedSprayName = sprayName.getText().toString();
            }
            cropSpraying.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            cropSpraying.setRate(Float.parseFloat(rateTxt.getText().toString()));
            cropSpraying.setCropId(cropId);
            cropSpraying.setCost(Float.parseFloat("0.00"));
            cropSpraying.setDate(dateTxt.getText().toString());
            cropSpraying.setTreatmentReason(reasonTxt.getText().toString());
            cropSpraying.setSprayId(pickedSprayId);
            cropSpraying.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cropSpraying.setDaysBefore(Float.parseFloat("0"+daysBeforeTxt.getText().toString()));
            cropSpraying.setOperator(DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_LAST_NAME,getContext()));
            cropSpraying.setReminders(remindersSp.getSelectedItem().toString());
            cropSpraying.setSprayType(sprayType);
            cropSpraying.setUnits(SprayUnits.getSelectedItem().toString());

            dbHandler.updateCropSpraying(cropSpraying);
        }
    }

    public void fillViews(){
        if(cropSpraying != null){
            DashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropSpraying.getRecurrence());
            DashboardActivity.selectSpinnerItemByValue(remindersSp, cropSpraying.getReminders());
            DashboardActivity.selectSpinnerItemByValue(SprayUnits, cropSpraying.getUsageUnits());
            sprayName.setText(cropSpraying.getSprayName());
            rateTxt.setText(cropSpraying.getRate()+"");
            dateTxt.setText(cropSpraying.getDate());
            reasonTxt.setText(cropSpraying.getTreatmentReason());
            rateTxt.setText(cropSpraying.getRate()+"");
            daysBeforeTxt.setText(cropSpraying.getDaysBefore()+"");

        }

    }

    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }else if(rateTxt.getText().toString().isEmpty()){
            message = getString(R.string.rate_not_entered);
            rateTxt.requestFocus();
        }

        else if(sprayName.getText().toString().isEmpty()){
            message = getString(R.string.spray_name_not_entered);
            sprayName.requestFocus();
        }
        else if(recurrenceSp.getSelectedItemPosition()==0){
            message = getString(R.string.recurrence_not_selected);
            recurrenceSp.requestFocus();
        }




        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}

