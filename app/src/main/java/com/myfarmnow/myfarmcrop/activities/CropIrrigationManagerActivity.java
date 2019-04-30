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
import com.myfarmnow.myfarmcrop.models.CropIrrigation;

public class CropIrrigationManagerActivity extends AppCompatActivity {
    CropIrrigation cropIrrigation=null;
    TextView totalWaterQuantityTxt,unitsTxt,quantityPerUnitTxt;
    EditText operationDateTxt, systemRateTxt,startTimeTxt,endTimeTxt,areaIrrigatedTxt,totalCostTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    Spinner recurrenceSpinner,remindersSpinner;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout;
    Button saveBtn;
    String cropId;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_irrigation_manager);

        if(getIntent().hasExtra("cropIrrigation")){
            cropIrrigation = (CropIrrigation) getIntent().getSerializableExtra("cropIrrigation");
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

        operationDateTxt = findViewById(R.id.txt_crop_irrigation_operation_date);
        systemRateTxt = findViewById(R.id.txt_crop_irrigation_system_rate);
        startTimeTxt = findViewById(R.id.txt_crop_irrigation_start_time);
        endTimeTxt = findViewById(R.id.txt_crop_irrigation_end_time);
        totalWaterQuantityTxt = findViewById(R.id.txt_crop_irrigation_total_water_quantity);
        areaIrrigatedTxt = findViewById(R.id.txt_crop_irrigation_area_irrigated);
        unitsTxt = findViewById(R.id.txt_crop_irrigation_area_irrigated_units);
        quantityPerUnitTxt = findViewById(R.id.txt_crop_irrigation_quantity_per_unit);
        recurrenceSpinner = findViewById(R.id.sp_crop_irrigation_recurrence);
        remindersSpinner = findViewById(R.id.sp_crop_irrigation_reminders);
        totalCostTxt = findViewById(R.id.txt_crop_irrigation_total_cost);
        weeksTxt = findViewById(R.id.txt_crop_irrigation_weekly_weeks);
        repeatUntilTxt = findViewById(R.id.txt_crop_irrigation_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_irrigation_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_irrigation_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_irrigation_days_before);
        recurrenceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        remindersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        CropDashboardActivity.addTimePicker(startTimeTxt,this);
        CropDashboardActivity.addTimePicker(endTimeTxt,this);
        CropDashboardActivity.addDatePicker(repeatUntilTxt,this);

        ((ArrayAdapter)recurrenceSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropIrrigation==null){
                        saveIrrigation();
                    }
                    else{
                        updateIrrigation();
                    }

                    Intent cropIrrigation = new Intent(CropIrrigationManagerActivity.this, CropActivitiesListActivity.class);
                    cropIrrigation.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    cropIrrigation.putExtra("cropId",cropId);
                    startActivity(cropIrrigation);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
//TODO TEXT WATCHER ISSUE
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateWaterQuantity();
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateWaterQuantity();
            }
        };
        startTimeTxt.addTextChangedListener(watcher);
        endTimeTxt.addTextChangedListener(watcher);
        systemRateTxt.addTextChangedListener(watcher);
        fillViews();
    }

    public void saveIrrigation() {
        cropIrrigation = new CropIrrigation();
        cropIrrigation.setUserId(CropDashboardActivity.getPreferences("userId", this));
        cropIrrigation.setOperationDate(operationDateTxt.getText().toString());
        cropIrrigation.setSystemRate(Float.parseFloat(systemRateTxt.getText().toString()));
        cropIrrigation.setStartTime(startTimeTxt.getText().toString());
        cropIrrigation.setEndTime(endTimeTxt.getText().toString());
        cropIrrigation.setCropId(cropId);
        cropIrrigation.setTotalWaterQuantity(Float.parseFloat(totalWaterQuantityTxt.getText().toString()));
        cropIrrigation.setAreaIrrigated(Float.parseFloat(areaIrrigatedTxt.getText().toString()));
        cropIrrigation.setUnits(unitsTxt.getText().toString());
        cropIrrigation.setRecurrence(recurrenceSpinner.getSelectedItem().toString());
        cropIrrigation.setReminders(remindersSpinner.getSelectedItem().toString());
        cropIrrigation.setTotalCost(Float.parseFloat(totalCostTxt.getText().toString()));
        if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
            String weeks = weeksTxt.getText().toString();
            String repeatUntil = repeatUntilTxt.getText().toString();

            cropIrrigation.setFrequency(Float.parseFloat(weeks));
            cropIrrigation.setRepeatUntil(repeatUntil);
        }
        if(daysBeforeLayout.getVisibility()==View.VISIBLE){
            String days = daysBeforeTxt.getText().toString();


            cropIrrigation.setDaysBefore(days);

        }
        dbHandler.insertCropIrrigation(cropIrrigation);
    }

    public void updateIrrigation(){
        if(cropIrrigation != null){
            cropIrrigation.setUserId(CropDashboardActivity.getPreferences("userId",this));
            cropIrrigation.setOperationDate(operationDateTxt.getText().toString());
            cropIrrigation.setSystemRate(Float.parseFloat(systemRateTxt.getText().toString()));
            cropIrrigation.setStartTime(startTimeTxt.getText().toString());
            cropIrrigation.setEndTime(endTimeTxt.getText().toString());
            cropIrrigation.setCropId(cropId);
            cropIrrigation.setTotalWaterQuantity(Float.parseFloat(totalWaterQuantityTxt.getText().toString()));
            cropIrrigation.setAreaIrrigated(Float.parseFloat(areaIrrigatedTxt.getText().toString()));
            cropIrrigation.setUnits(unitsTxt.getText().toString());
            cropIrrigation.setRecurrence(recurrenceSpinner.getSelectedItem().toString());
            cropIrrigation.setReminders(remindersSpinner.getSelectedItem().toString());
            cropIrrigation.setTotalCost(Float.parseFloat(totalCostTxt.getText().toString()));
            if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
                String weeks = weeksTxt.getText().toString();
                String repeatUntil = repeatUntilTxt.getText().toString();

                cropIrrigation.setFrequency(Float.parseFloat(weeks));
                cropIrrigation.setRepeatUntil(repeatUntil);
            }
            if(daysBeforeLayout.getVisibility()==View.VISIBLE){
                String days = daysBeforeTxt.getText().toString();


                cropIrrigation.setDaysBefore(days);

            }
            dbHandler.updateCropIrrigation(cropIrrigation);
        }
    }
    public void fillViews(){
        if(cropIrrigation != null){
            CropDashboardActivity.selectSpinnerItemByValue(recurrenceSpinner, cropIrrigation.getRecurrence());
            CropDashboardActivity.selectSpinnerItemByValue(remindersSpinner, cropIrrigation.getReminders());
            operationDateTxt.setText(cropIrrigation.getOperationDate());
            systemRateTxt.setText(cropIrrigation.getSystemRate()+"");
            startTimeTxt.setText(cropIrrigation.getStartTime());
            endTimeTxt.setText(cropIrrigation.getEndTime());
            totalWaterQuantityTxt.setText(cropIrrigation.getTotalWaterQuantity()+"");
            areaIrrigatedTxt.setText(cropIrrigation.getAreaIrrigated()+"");
            unitsTxt.setText(cropIrrigation.getUnits()+"");
            quantityPerUnitTxt.setText(cropIrrigation.getQuantityPerUnit()+"");
            totalCostTxt.setText(cropIrrigation.getTotalCost()+"");
            weeksTxt.setText(cropIrrigation.getFrequency()+"");
            repeatUntilTxt.setText(cropIrrigation.getRepeatUntil());
            daysBeforeTxt.setText(cropIrrigation.getDaysBefore());

        }

    }



    public void updateWaterQuantity(){
        try{
            float area = Float.parseFloat(areaIrrigatedTxt.getText().toString());
            float rate = Float.parseFloat(systemRateTxt.getText().toString());
            float hours = CropIrrigation.calculateTime(startTimeTxt.getText().toString(),endTimeTxt.getText().toString());
            float quantity = rate*hours;
            totalWaterQuantityTxt.setText(quantity+"");
            float qtyPerArea =quantity/area;
            quantityPerUnitTxt.setText(qtyPerArea+"");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateEntries(){
        String message = null;
        if(operationDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.operation_date_not_selected_message);
            operationDateTxt.requestFocus();
        }
        else if(areaIrrigatedTxt.getText().toString().isEmpty()){
            message = getString(R.string.area_irrigated_not_entered_message);
            areaIrrigatedTxt.requestFocus();
        }
        else if(recurrenceSpinner.getSelectedItemPosition()==0) {
            message = getString(R.string.recurrence_not_selected);
            recurrenceSpinner.requestFocus();
        }
        else if(remindersSpinner.getSelectedItemPosition()==0) {
            message = getString(R.string.reminders_not_selected);
            remindersSpinner.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropIrrigationManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

}