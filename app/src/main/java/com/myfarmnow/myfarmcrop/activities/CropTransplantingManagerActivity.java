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
import com.myfarmnow.myfarmcrop.models.CropTransplanting;

public class CropTransplantingManagerActivity extends AppCompatActivity {

    //TODO CALCULATING expectedHarvestDate FROM operationDate and cycleLength
    EditText operationDateTxt,totalSeedlingTxt,seedlingsPerHaTxt,cycleLengthTxt,expectedYieldTxt,expectedYieldPerHaTxt,operatorTxt,totalCostTxt;
    Spinner varietyEarlinessSpinner,unitsSpinner;
    TextView expectedHarvestingDateTxt;
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

        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(operationDateTxt,this);

        ((ArrayAdapter)varietyEarlinessSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)unitsSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


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

                    Intent cropTransplanting = new Intent(CropTransplantingManagerActivity.this, CropTransplantingListActivity.class);
                    cropTransplanting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    cropTransplanting.putExtra("cropId",cropId);
                    startActivity(cropTransplanting);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
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
        cropTransplanting.setExpectedHarvestingDate(expectedHarvestingDateTxt.getText().toString());
        cropTransplanting.setUnits(unitsSpinner.getSelectedItem().toString());
        cropTransplanting.setExpectedYield(Float.parseFloat(expectedYieldTxt.getText().toString()));
        cropTransplanting.setExpectedYieldPerHa(Float.parseFloat(expectedYieldPerHaTxt.getText().toString()));
        cropTransplanting.setOperator(operatorTxt.getText().toString());
        cropTransplanting.setTotalCost(Float.parseFloat(totalCostTxt.getText().toString()));
        dbHandler.insertCropTransplanting(cropTransplanting);
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
            cropTransplanting.setExpectedHarvestingDate(expectedHarvestingDateTxt.getText().toString());
            cropTransplanting.setUnits(unitsSpinner.getSelectedItem().toString());
            cropTransplanting.setExpectedYield(Float.parseFloat(expectedYieldTxt.getText().toString()));
            cropTransplanting.setExpectedYieldPerHa(Float.parseFloat(expectedYieldPerHaTxt.getText().toString()));
            cropTransplanting.setOperator(operatorTxt.getText().toString());
            cropTransplanting.setTotalCost(Float.parseFloat(totalCostTxt.getText().toString()));

            dbHandler.updateCropTransplanting(cropTransplanting);
        }
    }
    public void fillViews(){
        if(cropTransplanting != null){
            CropDashboardActivity.selectSpinnerItemByValue(varietyEarlinessSpinner, cropTransplanting.getVarietyEarliness());
            CropDashboardActivity.selectSpinnerItemByValue(unitsSpinner, cropTransplanting.getUnits());
            operationDateTxt.setText(cropTransplanting.getOperationDate());
            totalSeedlingTxt.setText(cropTransplanting.getTotalSeedling()+"");
            seedlingsPerHaTxt.setText(cropTransplanting.getSeedlingPerHa()+"");
            cycleLengthTxt.setText(cropTransplanting.getCycleLength()+"");
            expectedHarvestingDateTxt.setText(cropTransplanting.getExpectedHarvestingDate());
            expectedYieldTxt.setText(cropTransplanting.getExpectedYield()+"");
            expectedYieldPerHaTxt.setText(cropTransplanting.getExpectedYieldPerHa()+"");
            operatorTxt.setText(cropTransplanting.getOperator());
            totalCostTxt.setText(cropTransplanting.getTotalCost()+"");

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

        if(message != null){
            Toast.makeText(CropTransplantingManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }



}
