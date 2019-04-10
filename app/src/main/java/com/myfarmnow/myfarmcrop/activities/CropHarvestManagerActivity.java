package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;

public class CropHarvestManagerActivity extends AppCompatActivity {

    String cropId;
    CropHarvest cropHarvest=null;
    EditText harvestDateTxt,harvestMethodTxt,quantityTxt,dateSoldTxt,customerTxt,priceTxt,quantitySoldTxt,storageDateTxt,quantityStoredTxt,costTxt;
    Spinner  harvestUnitsSpinner,operatorSpinner,statusSpinner;
    LinearLayout harvestSoldLayout,harvestStoredLayout;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;
    CropSpinnerAdapter employeesSpinnerAdapter;
    boolean harvestSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_harvest_manager);
        if(getIntent().hasExtra("cropScouting")){
            cropHarvest = (CropHarvest) getIntent().getSerializableExtra("cropHarvest");
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

        harvestDateTxt = findViewById(R.id.txt_crop_harvest_date);
        harvestMethodTxt = findViewById(R.id.txt_crop_harvest_method);
        harvestUnitsSpinner = findViewById(R.id.sp_crop_harvest_units);
        quantityTxt = findViewById(R.id.txt_crop_harvest_quantity);
        operatorSpinner = findViewById(R.id.sp_crop_harvest_operator);
        statusSpinner = findViewById(R.id.sp_crop_harvest_status);
        dateSoldTxt = findViewById(R.id.txt_crop_harvest_date_sold);
        customerTxt = findViewById(R.id.txt_crop_harvest_customer);
        priceTxt = findViewById(R.id.txt_crop_harvest_price);
        quantitySoldTxt = findViewById(R.id.txt_crop_harvest_quantity_sold);
        storageDateTxt = findViewById(R.id.txt_crop_harvest_storage_date);
        quantityStoredTxt = findViewById(R.id.txt_crop_harvest_quantity_stored);
        costTxt = findViewById(R.id.txt_crop_harvest_cost);
        harvestSoldLayout=findViewById(R.id.layout_crop_harvest_sold);
        harvestStoredLayout=findViewById(R.id.layout_crop_harvest_stored);


        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==1){
                    harvestSoldLayout.setVisibility(View.VISIBLE);
                }
                else if(position==2){
                    harvestSoldLayout.setVisibility(View.GONE);
                }

                if(position==2){
                    harvestStoredLayout.setVisibility(View.VISIBLE);
                }
                else if(position==1){
                    harvestStoredLayout.setVisibility(View.GONE);
                }
                if(!harvestSet && cropHarvest != null){
                    CropDashboardActivity.selectSpinnerItemById(operatorSpinner, cropHarvest.getOperator());
                    harvestSet =true;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(harvestDateTxt,this);
        CropDashboardActivity.addDatePicker(dateSoldTxt,this);
        CropDashboardActivity.addDatePicker(storageDateTxt,this);

        ArrayList<CropSpinnerItem> employeesItems = new ArrayList<>();
        for(CropEmployee x: dbHandler.getCropEmployee(CropDashboardActivity.getPreferences("userId",this))){
            employeesItems.add(x);
        }
        employeesSpinnerAdapter = new CropSpinnerAdapter(employeesItems,"Employee",this);
        operatorSpinner.setAdapter(employeesSpinnerAdapter);

        ((ArrayAdapter)harvestUnitsSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
     //   ((ArrayAdapter)operatorSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)statusSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropHarvest==null){
                        saveHarvest();
                    }
                    else{
                        updateHarvest();
                    }

                    Intent cropHarvest = new Intent(CropHarvestManagerActivity.this, CropHarvestListActivity.class);
                    cropHarvest.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    cropHarvest.putExtra("cropId",cropId);
                    startActivity(cropHarvest);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        fillViews();
    }
    public void saveHarvest() {
        cropHarvest = new CropHarvest();
        cropHarvest.setUserId(CropDashboardActivity.getPreferences("userId", this));
        cropHarvest.setCropId(cropId);
        cropHarvest.setDate(harvestDateTxt.getText().toString());
        cropHarvest.setMethod(harvestMethodTxt.getText().toString());
        cropHarvest.setUnits(harvestUnitsSpinner.getSelectedItem().toString());
        cropHarvest.setQuantity(Float.parseFloat(quantitySoldTxt.getText().toString()));
        if(operatorSpinner.getSelectedItemPosition()!=0) {
            cropHarvest.setOperator(((CropEmployee) operatorSpinner.getSelectedItem()).getEmployeeId());
        }
        cropHarvest.setStatus(statusSpinner.getSelectedItem().toString());
        cropHarvest.setDateSold(dateSoldTxt.getText().toString());
        cropHarvest.setCustomer(customerTxt.getText().toString());
        cropHarvest.setPrice(Float.parseFloat(priceTxt.getText().toString()));
        cropHarvest.setQuantitySold(Float.parseFloat(quantitySoldTxt.getText().toString()));
        cropHarvest.setStorageDate(storageDateTxt.getText().toString());
        cropHarvest.setQuantityStored(Float.parseFloat(quantityStoredTxt.getText().toString()));
        cropHarvest.setCost(Float.parseFloat(costTxt.getText().toString()));

        dbHandler.insertCropHarvest(cropHarvest);
    }

    public void updateHarvest(){
        if(cropHarvest != null){
            cropHarvest.setUserId(CropDashboardActivity.getPreferences("userId", this));
            cropHarvest.setCropId(cropId);
            cropHarvest.setDate(harvestDateTxt.getText().toString());
            cropHarvest.setMethod(harvestMethodTxt.getText().toString());
            cropHarvest.setUnits(harvestUnitsSpinner.getSelectedItem().toString());
            cropHarvest.setQuantity(Float.parseFloat(quantitySoldTxt.getText().toString()));
            if(operatorSpinner.getSelectedItemPosition()!=0) {
                cropHarvest.setOperator(((CropEmployee) operatorSpinner.getSelectedItem()).getEmployeeId());
            }
            cropHarvest.setStatus(statusSpinner.getSelectedItem().toString());
            cropHarvest.setDateSold(dateSoldTxt.getText().toString());
            cropHarvest.setCustomer(customerTxt.getText().toString());
            cropHarvest.setPrice(Float.parseFloat(priceTxt.getText().toString()));
            cropHarvest.setQuantitySold(Float.parseFloat(quantitySoldTxt.getText().toString()));
            cropHarvest.setStorageDate(storageDateTxt.getText().toString());
            cropHarvest.setQuantityStored(Float.parseFloat(quantityStoredTxt.getText().toString()));
            cropHarvest.setCost(Float.parseFloat(costTxt.getText().toString()));

            dbHandler.updateCropHarvest(cropHarvest);
        }
    }
    public void fillViews(){
        if(cropHarvest != null){
            CropDashboardActivity.selectSpinnerItemByValue(harvestUnitsSpinner, cropHarvest.getUnits());
            CropDashboardActivity.selectSpinnerItemById(operatorSpinner, cropHarvest.getOperator());
            CropDashboardActivity.selectSpinnerItemByValue(statusSpinner, cropHarvest.getStatus());
            harvestDateTxt.setText(cropHarvest.getDate());
            harvestMethodTxt.setText(cropHarvest.getMethod());
            quantityTxt.setText(cropHarvest.getQuantity()+"");
            dateSoldTxt.setText(cropHarvest.getDateSold());
            customerTxt.setText(cropHarvest.getCustomer());
            priceTxt.setText(cropHarvest.getPrice()+"");
            quantitySoldTxt.setText(cropHarvest.getQuantitySold()+"");
            storageDateTxt.setText(cropHarvest.getStorageDate());
            quantityStoredTxt.setText(cropHarvest.getQuantityStored()+"");
            costTxt.setText(cropHarvest.getCost()+"");


        }

    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateEntries(){
        String message = null;
        if(harvestDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.harvest_date_not_selected_message);
            harvestDateTxt.requestFocus();
        }
        else if(quantityTxt.getText().toString().isEmpty()){
            message = getString(R.string.quantity_not_entered);
            quantityTxt.requestFocus();
        }
        else if(harvestUnitsSpinner.getSelectedItemPosition()==0) {
            message = getString(R.string.units_not_selected);
            harvestUnitsSpinner.requestFocus();
        }
        else if(statusSpinner.getSelectedItemPosition()==0) {
            message = getString(R.string.status_not_selected);
            statusSpinner.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropHarvestManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }



}
