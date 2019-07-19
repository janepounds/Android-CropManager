package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropTransplantingManagerActivity extends AppCompatActivity {


    EditText operationDateTxt,totalSeedlingTxt,cycleLengthTxt,expectedYieldTxt,operatorTxt,totalCostTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt,expectedYieldPerHaTxt,seedlingsPerHaTxt;
    Spinner varietyEarlinessSpinner,unitsSpinner,recurrenceSp,remindersSp;

    TextView expectedHarvestingDateTxt,expectedYieldPerUnitTxt,seedlingPerUnitTxt,currencyTxt,areaTxt;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout,remindersLayout;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;
    CropTransplanting cropTransplanting=null;
    String cropId;
    Float area;
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


        initializeForm();
    }
    public void initializeForm(){

        operationDateTxt = findViewById(R.id.txt_crop_transplanting_operation_date);
        totalSeedlingTxt = findViewById(R.id.txt_crop_transplanting_total_seedling);
        seedlingsPerHaTxt = findViewById(R.id.txt_crop_transplanting_seedling_per_ha);
        seedlingPerUnitTxt = findViewById(R.id.txt_crop_transplanting_seedling_per_unit);
        varietyEarlinessSpinner = findViewById(R.id.sp_crop_transplanting_variety_earliness);
        cycleLengthTxt = findViewById(R.id.txt_crop_transplanting_cycle_length);
        expectedHarvestingDateTxt = findViewById(R.id.txt_crop_transplanting_expected_harvesting_date);
        unitsSpinner = findViewById(R.id.sp_crop_transplanting_units);
        expectedYieldTxt = findViewById(R.id.txt_crop_transplanting_expected_yield);
        expectedYieldPerHaTxt = findViewById(R.id.txt_crop_transplanting_expected_yield_per_ha);
        expectedYieldPerUnitTxt = findViewById(R.id.txt_crop_transplanting_expected_yield_per_unit);
        operatorTxt = findViewById(R.id.txt_crop_transplanting_operator);
        totalCostTxt = findViewById(R.id.txt_crop_transplanting_total_cost);
        currencyTxt = findViewById(R.id.txt_crop_transplanting_currency);
        remindersSp = findViewById(R.id.sp_crop_transplanting_reminders);
        recurrenceSp = findViewById(R.id.sp_crop_transplanting_recurrence);
        weeksTxt = findViewById(R.id.txt_crop_transplanting_weekly_weeks);
        repeatUntilTxt = findViewById(R.id.txt_crop_transplanting_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_transplanting_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_transplanting_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_transplanting_days_before);
        remindersLayout = findViewById(R.id.layout_crop_transplanting_reminders);




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
                    if(recurrenceSp.getSelectedItem().equals("Weekly")){
                        weeklyRecurrenceLayout.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    daysBeforeLayout.setVisibility(View.GONE);
                    weeklyRecurrenceLayout.setVisibility(View.GONE);
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
        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());

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

        seedlingPerUnitTxt.setText("Seedling / "+CropSettingsSingleton.getInstance().getAreaUnits());
        expectedYieldPerUnitTxt.setText("Expected Yield / "+CropSettingsSingleton.getInstance().getAreaUnits());


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
                    finish();
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

       /* TextWatcher watcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateExpectedYieldPerUnit();
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateExpectedYieldPerUnit();
            }
        };
        expectedYieldTxt.addTextChangedListener(watcher1);
        TextWatcher watcher2 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateSeedlingPerUnit();
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateSeedlingPerUnit();
            }
        };
        totalSeedlingTxt.addTextChangedListener(watcher2);*/


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
        cropTransplanting.setRepeatUntil(repeatUntilTxt.getText().toString());
        cropTransplanting.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
        cropTransplanting.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));


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
   /* public float calculateExpectedYieldPerUnit(){
        try{
            float expectedYield = Float.parseFloat(expectedYieldTxt.getText().toString());
            float area = Float.parseFloat(areaTxt.getText().toString());
            float expectedYieldPerUnit = (expectedYield/area);
            expectedYieldPerHaTxt.setText(expectedYieldPerUnit+"");
            return expectedYieldPerUnit;
        }catch (Exception e){

        }
        return 0;
    }

    public float calculateSeedlingPerUnit(){
        try{
            float totalSeedling = Float.parseFloat(totalSeedlingTxt.getText().toString());
            float area = Float.parseFloat(areaTxt.getText().toString());
            float seedlingPerUnit = (totalSeedling/area);
            seedlingPerUnitTxt.setText(seedlingPerUnit+"");
            return seedlingPerUnit;
        }catch (Exception e){

        }
        return 0;
    }*/
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
            cropTransplanting.setRepeatUntil(repeatUntilTxt.getText().toString());
            cropTransplanting.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
            cropTransplanting.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));


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
            daysBeforeTxt.setText(cropTransplanting.getDaysBefore()+"");

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
        else if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE && repeatUntilTxt.getText().toString().isEmpty()){
            message = getString(R.string.repeat_until_not_selected);
            repeatUntilTxt.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropTransplantingManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }



}
