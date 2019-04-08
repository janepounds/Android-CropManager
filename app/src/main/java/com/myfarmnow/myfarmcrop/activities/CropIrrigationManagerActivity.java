package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropIrrigation;

public class CropIrrigationManagerActivity extends AppCompatActivity {
    //TODO CALCULATING TOTAL WATER QUANTITY FROM endtime-starttime*systemrate
    //TODO CALCULATING QUANTITY FROM totalWaterQuantity/areaIrrigated
    CropIrrigation cropIrrigation=null;
    TextView totalWaterQuantityTxt,unitsTxt,quantityPerUnitTxt;
    EditText operationDateTxt, systemRateTxt,startTimeTxt,endTimeTxt,areaIrrigatedTxt,totalCostTxt;
    Spinner recurrenceSpinner,remindersSpinner;
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


        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(operationDateTxt,this);
        CropDashboardActivity.addTimePicker(startTimeTxt,this);
        CropDashboardActivity.addTimePicker(endTimeTxt,this);

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

                    Intent cropIrrigation = new Intent(CropIrrigationManagerActivity.this, CropIrrigationListActivity.class);
                    cropIrrigation.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    cropIrrigation.putExtra("cropId",cropId);
                    startActivity(cropIrrigation);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
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
        cropIrrigation.setQuantityPerUnit(Float.parseFloat(quantityPerUnitTxt.getText().toString()));
        cropIrrigation.setRecurrence(recurrenceSpinner.getSelectedItem().toString());
        cropIrrigation.setReminders(remindersSpinner.getSelectedItem().toString());
        cropIrrigation.setTotalCost(Float.parseFloat(totalCostTxt.getText().toString()));
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
            cropIrrigation.setQuantityPerUnit(Float.parseFloat(quantityPerUnitTxt.getText().toString()));
            cropIrrigation.setRecurrence(recurrenceSpinner.getSelectedItem().toString());
            cropIrrigation.setReminders(remindersSpinner.getSelectedItem().toString());
            cropIrrigation.setTotalCost(Float.parseFloat(totalCostTxt.getText().toString()));
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
