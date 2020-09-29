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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropSpraying;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;


public class CropSprayingFragment extends DialogFragment {
    EditText dateTxt, rateTxt, reasonTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    Button btn_save;
    CropSpraying cropSpraying;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout,remindersLayout;
    String cropId;
    MyFarmDbHandlerSingleton dbHandler;
    Spinner sprayIdSp,recurrenceSp,remindersSp;
    ImageView datePicker,sprayClose;
//    Spinner windDirectionSp,waterConditionSp;
    TextView rateUnitsTextView,currencyTxt;
    private Context context;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crop_spraying, container, false);
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view =inflater.inflate(R.layout.fragment_crop_spraying, null);
        builder.setView(view);
        if(getArguments()!=null){
            cropId = getArguments().getString("cropId");
        }
        initializeForm(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);


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
//        startTimeTxt =view.findViewById(R.id.txt_crop_spraying_start_time);
//        endTimeTxt =view.findViewById(R.id.txt_crop_spraying_end_time);
//        operatorTxt =view.findViewById(R.id.txt_crop_spraying_performed_by);
//        waterVolumeTxt =view.findViewById(R.id.txt_crop_spraying_water_volume);
//        costTxt =view.findViewById(R.id.txt_crop_spraying_labour_cost);
        rateTxt =view.findViewById(R.id.txt_crop_spraying_rate);
        reasonTxt =view.findViewById(R.id.txt_crop_spraying_treatment_reason);
//        equipmentUsedTxt =view.findViewById(R.id.txt_crop_spraying_equipment_used);
        rateUnitsTextView =view.findViewById(R.id.txt_crop_spraying_rate_units);
//        currencyTxt =view.findViewById(R.id.txt_crop_spraying_currency);
        sprayIdSp =view.findViewById(R.id.sp_crop_spraying_name);
//        windDirectionSp =view.findViewById(R.id.sp_crop_spraying_wind_direction);
//        waterConditionSp =view.findViewById(R.id.sp_crop_spraying_weather_condition);
        remindersSp = view.findViewById(R.id.sp_crop_spraying_reminders);
        recurrenceSp = view.findViewById(R.id.sp_crop_spraying_recurrence);
//        weeksTxt = view.findViewById(R.id.txt_crop_spraying_weekly_weeks);
//        repeatUntilTxt = view.findViewById(R.id.txt_crop_spraying_repeat_until);
        daysBeforeTxt = view.findViewById(R.id.txt_crop_fertilizer_application_days_before);
//        weeklyRecurrenceLayout = view.findViewById(R.id.layout_crop_spraying_weekly_reminder);
        daysBeforeLayout = view.findViewById(R.id.layout_crop_spraying_days_before);
        remindersLayout = view.findViewById(R.id.layout_crop_spraying_reminders);

//        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());
        sprayClose.setOnClickListener(v -> getDialog().dismiss());

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
                if(selection.toLowerCase().equals("weekly")){

                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }

                if(selection.toLowerCase().equals("daily")){
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }

                if(selection.toLowerCase().equals("once")){
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }

                if(selection.toLowerCase().equals("monthly")){
                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }

                if(selection.toLowerCase().equals("annually")){
                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        remindersSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                try{
//                    if(position ==0){
//                        ((TextView) view).setTextColor(Color.GRAY);
//                    }
//                   else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));
//
//                    }
//                    else {
//                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
//                    }
//                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
//                }catch (Exception e){
//
//                }
//                String selection = parent.getItemAtPosition(position).toString();
//                if(selection.toLowerCase().equals("yes")){
//                    daysBeforeLayout.setVisibility(View.VISIBLE);
//                    if(recurrenceSp.getSelectedItem().equals("Weekly")){
//                        weeklyRecurrenceLayout.setVisibility(View.VISIBLE);
//                    }
//                }
//                else{
//                    daysBeforeLayout.setVisibility(View.GONE);
//                    weeklyRecurrenceLayout.setVisibility(View.GONE);
//                }
//
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });



        btn_save = view.findViewById(R.id.btn_save);
//        DashboardActivity.addDatePicker(dateTxt,context);
//        DashboardActivity.addTimePicker(startTimeTxt,context);
//        DashboardActivity.addTimePicker(endTimeTxt,context);
//        DashboardActivity.addDatePicker(repeatUntilTxt,context);
//
//        ((ArrayAdapter)windDirectionSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
//        ((ArrayAdapter)waterConditionSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position == 0){
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
//        windDirectionSp.setOnItemSelectedListener(onItemSelectedListener);
//        waterConditionSp.setOnItemSelectedListener(onItemSelectedListener);


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
                    navController = Navigation.findNavController(v);
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
//        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayList<CropSpinnerItem> spraysList = new ArrayList<>();
        for(CropInventorySpray x: dbHandler.getCropSpray(DashboardActivity.RETRIEVED_USER_ID)){
            spraysList.add(x);
        }
        CropSpinnerAdapter fertilizerAdapter  =new CropSpinnerAdapter(spraysList,"Spray",context);
        sprayIdSp.setAdapter(fertilizerAdapter);

        sprayIdSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    return;
                }
                CropInventorySpray inventorySpray = (CropInventorySpray) ((CropSpinnerItem)sprayIdSp.getSelectedItem());
                if(inventorySpray.getUsageUnits() != null){
                    rateUnitsTextView.setText(inventorySpray.getUsageUnits()+"/ha");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fillViews();
    }

    public void saveSpraying(){
        cropSpraying = new CropSpraying();
        cropSpraying.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        cropSpraying.setRate(Float.parseFloat(rateTxt.getText().toString()));
        cropSpraying.setCropId(cropId);
        cropSpraying.setCost(Float.parseFloat("0.00"));
        cropSpraying.setTreatmentReason(reasonTxt.getText().toString());
        cropSpraying.setSprayId(((CropSpinnerItem)sprayIdSp.getSelectedItem()).getId());
        cropSpraying.setRecurrence(recurrenceSp.getSelectedItem().toString());
//        cropSpraying.setReminders(remindersSp.getSelectedItem().toString());
        cropSpraying.setRepeatUntil(repeatUntilTxt.getText().toString());
        cropSpraying.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
        cropSpraying.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));
//
//        cropSpraying.setEquipmentUsed(equipmentUsedTxt.getText().toString());
//        cropSpraying.setWaterVolume(Float.parseFloat(waterVolumeTxt.getText().toString()));
//        cropSpraying.setStartTime(startTimeTxt.getText().toString());
//        cropSpraying.setEndTime(endTimeTxt.getText().toString());
//        cropSpraying.setOperator(operatorTxt.getText().toString());
//        if(waterConditionSp.getSelectedItemPosition()!=0){
//            cropSpraying.setWaterCondition(waterConditionSp.getSelectedItem().toString());
//        }
//        if(windDirectionSp.getSelectedItemPosition()!=0){
//            cropSpraying.setWindDirection(windDirectionSp.getSelectedItem().toString());
//        }

        dbHandler.insertCropSpraying(cropSpraying);

    }
    public void updateSpraying(){
        if(cropSpraying != null){
            cropSpraying.setDate(dateTxt.getText().toString());
            cropSpraying.setRate(Float.parseFloat(rateTxt.getText().toString()));
            cropSpraying.setCropId(cropId);
            cropSpraying.setCost(Float.parseFloat("0.0"));
            cropSpraying.setTreatmentReason(reasonTxt.getText().toString());
            cropSpraying.setSprayId(((CropSpinnerItem)sprayIdSp.getSelectedItem()).getId());
            cropSpraying.setRecurrence(recurrenceSp.getSelectedItem().toString());
//            cropSpraying.setReminders(remindersSp.getSelectedItem().toString());
            cropSpraying.setRepeatUntil(repeatUntilTxt.getText().toString());
            cropSpraying.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
            cropSpraying.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));
//            cropSpraying.setEquipmentUsed(equipmentUsedTxt.getText().toString());
//            cropSpraying.setWaterVolume(Float.parseFloat(waterVolumeTxt.getText().toString()));
//            cropSpraying.setStartTime(startTimeTxt.getText().toString());
//            cropSpraying.setEndTime(endTimeTxt.getText().toString());
//            cropSpraying.setOperator(operatorTxt.getText().toString());

            dbHandler.updateCropSpraying(cropSpraying);
        }
    }
    public void fillViews(){
        if(cropSpraying != null){
            DashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropSpraying.getRecurrence());
//
//            DashboardActivity.selectSpinnerItemByValue(windDirectionSp, cropSpraying.getWindDirection());
//            DashboardActivity.selectSpinnerItemByValue(waterConditionSp, cropSpraying.getWaterCondition());
//            DashboardActivity.selectSpinnerItemByValue(remindersSp, cropSpraying.getReminders());

            rateTxt.setText(cropSpraying.getRate()+"");
            dateTxt.setText(cropSpraying.getDate());
            reasonTxt.setText(cropSpraying.getTreatmentReason());
            rateTxt.setText(cropSpraying.getRate()+"");
            weeksTxt.setText(cropSpraying.getFrequency()+"");
            repeatUntilTxt.setText(cropSpraying.getRepeatUntil());
            daysBeforeTxt.setText(cropSpraying.getDaysBefore()+"");
//
//            equipmentUsedTxt.setText(cropSpraying.getEquipmentUsed());
//            costTxt.setText(cropSpraying.getCost()+"");
//            waterVolumeTxt.setText(cropSpraying.getWaterVolume()+"");
//            startTimeTxt.setText(cropSpraying.getStartTime()+"");
//            endTimeTxt.setText(cropSpraying.getEndTime()+"");
//            operatorTxt.setText(cropSpraying.getOperator());


            DashboardActivity.selectSpinnerItemById(sprayIdSp, cropSpraying.getId());
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
//        else if(equipmentUsedTxt.getText().toString().isEmpty()){
//            message = getString(R.string.equipment_not_entered);
//            equipmentUsedTxt.requestFocus();
//        }
        else if(sprayIdSp.getSelectedItemPosition()==0){
            message = getString(R.string.spray_name_not_entered);
            sprayIdSp.requestFocus();
        }
        else if(recurrenceSp.getSelectedItemPosition()==0){
            message = getString(R.string.recurrence_not_selected);
            recurrenceSp.requestFocus();
        }
//        else if(remindersSp.getSelectedItemPosition()==0){
//            message = getString(R.string.reminders_not_selected);
//            remindersSp.requestFocus();
//        }
        else if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE && repeatUntilTxt.getText().toString().isEmpty()){
            message = getString(R.string.repeat_until_not_selected);
            repeatUntilTxt.requestFocus();
        }



        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}

