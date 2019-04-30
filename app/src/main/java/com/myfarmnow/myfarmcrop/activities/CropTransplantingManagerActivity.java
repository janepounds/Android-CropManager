package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropTransplanting;

public class CropTransplantingManagerActivity extends AppCompatActivity {


    EditText operationDateTxt,totalSeedlingTxt,seedlingsPerHaTxt,cycleLengthTxt,expectedYieldTxt,expectedYieldPerHaTxt,operatorTxt,totalCostTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    Spinner varietyEarlinessSpinner,unitsSpinner,recurrenceSp,remindersSp;

    TextView expectedHarvestingDateTxt;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;
    CropTransplanting cropTransplanting=null;
    String cropId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_transplanting_manager);

        if(getIntent().hasExtra("cropTransplanting")){
            cropTransplanting = (CropTransplanting) getIntent().getSerializableExtra("cropTransplanting");
        }
        if(getIntent().hasExtra("cropId")){
            cropId =getIntent().getStringExtra("cropId");
        }
        else{
            finish();
        }
        initializeForm();
    }
    public void initializeForm(){

        operationDateTxt = findViewById(R.id.txt_crop_transplanting_operation_date);
        totalSeedlingTxt = findViewById(R.id.txt_crop_transplanting_total_seedling);
        seedlingsPerHaTxt = findViewById(R.id.txt_crop_transplanting_seedling_per_ha);
        varietyEarlinessSpinner = findViewById(R.id.sp_crop_transplanting_variety_earliness);
        cycleLengthTxt = findViewById(R.id.txt_crop_transplanting_cycle_length);
        expectedHarvestingDateTxt = findViewById(R.id.txt_crop_transplanting_expected_harvesting_date);
        unitsSpinner = findViewById(R.id.sp_crop_transplanting_units);
        expectedYieldTxt = findViewById(R.id.txt_crop_transplanting_expected_yield);
        expectedYieldPerHaTxt = findViewById(R.id.txt_crop_transplanting_expected_yield_per_ha);
       operatorTxt = findViewById(R.id.txt_crop_transplanting_operator);
        totalCostTxt = findViewById(R.id.txt_crop_transplanting_total_cost);
        remindersSp = findViewById(R.id.sp_crop_transplanting_reminders);
        recurrenceSp = findViewById(R.id.sp_crop_transplanting_recurrence);
        weeksTxt = findViewById(R.id.txt_crop_transplanting_weekly_weeks);
        repeatUntilTxt = findViewById(R.id.txt_crop_transplanting_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_transplanting_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_transplanting_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_transplanting_days_before);

        recurrenceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                String selection = parent.getItemAtPosition(position).toString();
                if(selection.toLowerCase().equals("weekly")){
                    weeklyRecurrenceLayout.setVisibility(View.VISIBLE);
                }
                else{
                    weeklyRecurrenceLayout.setVisibility(View.GONE);

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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                String selection = parent.getItemAtPosition(position).toString();
                if(selection.toLowerCase().equals("yes")){
                    daysBeforeLayout.setVisibility(View.VISIBLE);
                }
                else{
                    daysBeforeLayout.setVisibility(View.GONE);

                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(operationDateTxt,this);
        CropDashboardActivity.addDatePicker(repeatUntilTxt,this);

        ((ArrayAdapter)varietyEarlinessSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)unitsSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getColor(R.color.colorPrimary));

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
        varietyEarlinessSpinner.setOnItemSelectedListener(onItemSelectedListener);
        unitsSpinner.setOnItemSelectedListener(onItemSelectedListener);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropTransplanting==null){
                        saveTransplanting();
                    }
                    else{
                        updateTransplanting();
                    }

                    Intent cropTransplanting = new Intent(CropTransplantingManagerActivity.this, CropActivitiesListActivity.class);
                    cropTransplanting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    cropTransplanting.putExtra("cropId",cropId);
                    startActivity(cropTransplanting);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateHarvestDate();
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateHarvestDate();
            }
        };
        cycleLengthTxt.addTextChangedListener(watcher);
        operationDateTxt.addTextChangedListener(watcher);
        fillViews();
    }
    public void saveTransplanting() {
        cropTransplanting = new CropTransplanting();
        cropTransplanting.setUserId(CropDashboardActivity.getPreferences("userId", this));
        cropTransplanting.setCropId(cropId);
        cropTransplanting.setOperationDate(operationDateTxt.getText().toString());
        cropTransplanting.setTotalSeedling(Float.parseFloat(totalSeedlingTxt.getText().toString()));
        cropTransplanting.setSeedlingPerHa(Float.parseFloat(seedlingsPerHaTxt.getText().toString()));
        cropTransplanting.setVarietyEarliness(varietyEarlinessSpinner.getSelectedItem().toString());
        cropTransplanting.setCycleLength(Float.parseFloat(cycleLengthTxt.getText().toString()));

        cropTransplanting.setUnits(unitsSpinner.getSelectedItem().toString());
        cropTransplanting.setExpectedYield(Float.parseFloat(expectedYieldTxt.getText().toString()));
        cropTransplanting.setExpectedYieldPerHa(Float.parseFloat(expectedYieldPerHaTxt.getText().toString()));
        cropTransplanting.setOperator(operatorTxt.getText().toString());
        cropTransplanting.setTotalCost(Float.parseFloat(totalCostTxt.getText().toString()));
        cropTransplanting.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cropTransplanting.setReminders(remindersSp.getSelectedItem().toString());
        if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
            String weeks = weeksTxt.getText().toString();
            String repeatUntil = repeatUntilTxt.getText().toString();

            cropTransplanting.setFrequency(Float.parseFloat(weeks));
            cropTransplanting.setRepeatUntil(repeatUntil);
        }
        if(daysBeforeLayout.getVisibility()==View.VISIBLE){
            String days = daysBeforeTxt.getText().toString();


            cropTransplanting.setDaysBefore(days);

        }

        dbHandler.insertCropTransplanting(cropTransplanting);
    }


    public void updateHarvestDate(){
        try{
            int cycleLength  = Integer.parseInt(cycleLengthTxt.getText().toString());
            String harvestDate = CropTransplanting.determineHarvestDate(operationDateTxt.getText().toString(),cycleLength);
            expectedHarvestingDateTxt.setText(harvestDate);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateTransplanting(){
        if(cropTransplanting != null){
            cropTransplanting.setUserId(CropDashboardActivity.getPreferences("userId",this));
            cropTransplanting.setCropId(cropId);
            cropTransplanting.setOperationDate(operationDateTxt.getText().toString());
            cropTransplanting.setTotalSeedling(Float.parseFloat(totalSeedlingTxt.getText().toString()));
            cropTransplanting.setSeedlingPerHa(Float.parseFloat(seedlingsPerHaTxt.getText().toString()));
            cropTransplanting.setVarietyEarliness(varietyEarlinessSpinner.getSelectedItem().toString());
            cropTransplanting.setCycleLength(Float.parseFloat(cycleLengthTxt.getText().toString()));

            cropTransplanting.setUnits(unitsSpinner.getSelectedItem().toString());
            cropTransplanting.setExpectedYield(Float.parseFloat(expectedYieldTxt.getText().toString()));
            cropTransplanting.setExpectedYieldPerHa(Float.parseFloat(expectedYieldPerHaTxt.getText().toString()));
            cropTransplanting.setOperator(operatorTxt.getText().toString());
            cropTransplanting.setTotalCost(Float.parseFloat(totalCostTxt.getText().toString()));
            cropTransplanting.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cropTransplanting.setReminders(remindersSp.getSelectedItem().toString());
            if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
                String weeks = weeksTxt.getText().toString();
                String repeatUntil = repeatUntilTxt.getText().toString();

                cropTransplanting.setFrequency(Float.parseFloat(weeks));
                cropTransplanting.setRepeatUntil(repeatUntil);
            }
            if(daysBeforeLayout.getVisibility()==View.VISIBLE){
                String days = daysBeforeTxt.getText().toString();


                cropTransplanting.setDaysBefore(days);

            }

            dbHandler.updateCropTransplanting(cropTransplanting);
        }
    }
    public void fillViews(){
        if(cropTransplanting != null){
            CropDashboardActivity.selectSpinnerItemByValue(varietyEarlinessSpinner, cropTransplanting.getVarietyEarliness());
            CropDashboardActivity.selectSpinnerItemByValue(unitsSpinner, cropTransplanting.getUnits());
            CropDashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropTransplanting.getRecurrence());
            CropDashboardActivity.selectSpinnerItemByValue(remindersSp, cropTransplanting.getReminders());

            operationDateTxt.setText(cropTransplanting.getOperationDate());
            totalSeedlingTxt.setText(cropTransplanting.getTotalSeedling()+"");
            seedlingsPerHaTxt.setText(cropTransplanting.getSeedlingPerHa()+"");
            cycleLengthTxt.setText(cropTransplanting.getCycleLength()+"");
            expectedHarvestingDateTxt.setText(cropTransplanting.getExpectedHarvestingDate());
            expectedYieldTxt.setText(cropTransplanting.getExpectedYield()+"");
            expectedYieldPerHaTxt.setText(cropTransplanting.getExpectedYieldPerHa()+"");
            operatorTxt.setText(cropTransplanting.getOperator());
            totalCostTxt.setText(cropTransplanting.getTotalCost()+"");
            weeksTxt.setText(cropTransplanting.getFrequency()+"");
            repeatUntilTxt.setText(cropTransplanting.getRepeatUntil());
            daysBeforeTxt.setText(cropTransplanting.getDaysBefore());

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateEntries(){
        String message = null;
        if(operationDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.operation_date_not_selected_message);
            operationDateTxt.requestFocus();
        }
        else if(operatorTxt.getText().toString().isEmpty()){
            message = getString(R.string.operator_not_entered);
            operatorTxt.requestFocus();
        }
        else if(varietyEarlinessSpinner.getSelectedItemPosition()==0) {
            message = getString(R.string.variety_earliness_not_selected);
            varietyEarlinessSpinner.requestFocus();
        }
        else if(cycleLengthTxt.getText().toString().isEmpty()){
            message = getString(R.string.cycle_length_not_entered);
            cycleLengthTxt.requestFocus();
        }
        else if(totalCostTxt.getText().toString().isEmpty()){
            message = getString(R.string.total_cost_not_entered);
            totalCostTxt.requestFocus();
        }
        else if(recurrenceSp.getSelectedItemPosition()==0){
            message = getString(R.string.recurrence_not_selected);
            recurrenceSp.requestFocus();
        }
        else if(remindersSp.getSelectedItemPosition()==0){
            message = getString(R.string.reminders_not_selected);
            remindersSp.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropTransplantingManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }



}