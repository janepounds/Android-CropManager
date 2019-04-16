package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
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
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;

public class CropHarvestManagerActivity extends AppCompatActivity {

    String cropId;
    CropHarvest cropHarvest=null;
    EditText harvestDateTxt,harvestMethodTxt,quantityTxt,dateSoldTxt,customerTxt,priceTxt,
            quantitySoldTxt,storageDateTxt,quantityStoredTxt,costTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    TextView quantityStoredUnitsTxt,pricePerUnitTxt,quantitySoldUnitsTxt,incomeGeneratedTxt;
    Spinner  harvestUnitsSpinner,operatorSpinner,statusSpinner,recurrenceSp,remindersSp;
    LinearLayout harvestSoldLayout,harvestStoredLayout,weeklyRecurrenceLayout,daysBeforeLayout;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;
    CropSpinnerAdapter employeesSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_harvest_manager);
        if(getIntent().hasExtra("cropHarvest")){
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
        quantityStoredUnitsTxt=findViewById(R.id.txt_crop_harvest_stored_unit);
        pricePerUnitTxt=findViewById(R.id.txt_crop_harvest_price_per_unit);
        quantitySoldUnitsTxt=findViewById(R.id.txt_crop_harvest_sold_unit);
        recurrenceSp = findViewById(R.id.sp_crop_harvest_recurrence);
        remindersSp = findViewById(R.id.sp_crop_harvest_reminders);
        weeksTxt = findViewById(R.id.txt_crop_harvest_weekly_weeks);
        repeatUntilTxt = findViewById(R.id.txt_crop_harvest_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_harvest_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_harvest_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_harvest_days_before);

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        harvestUnitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getItemAtPosition(position).toString();
                if(selection.toLowerCase().equals("boxes")){
                   quantityStoredUnitsTxt.setText("Boxes");
                    pricePerUnitTxt.setText("Boxes");
                    quantitySoldUnitsTxt.setText("/ Box");

                }
                else if(selection.toLowerCase().equals("kg")){

                    quantityStoredUnitsTxt.setText("Kg");
                    pricePerUnitTxt.setText("Kg");
                    quantitySoldUnitsTxt.setText("/ Kg");

                }
                else if(selection.toLowerCase().equals("tonnes")){
                    quantityStoredUnitsTxt.setText("Tonnes");
                    pricePerUnitTxt.setText("Tonnes");
                    quantitySoldUnitsTxt.setText("/ Tonne");



                }
                else if(selection.toLowerCase().equals("bushels")){

                    quantityStoredUnitsTxt.setText("Bushels");
                    pricePerUnitTxt.setText("Bushels");
                    quantitySoldUnitsTxt.setText("/ Bushel");


                }
                else if(selection.toLowerCase().equals("bags")){

                    quantityStoredUnitsTxt.setText("Bags");
                    pricePerUnitTxt.setText("Bags");
                    quantitySoldUnitsTxt.setText("/ Bag");


                }
                else if(selection.toLowerCase().equals("bunches")){
                    quantityStoredUnitsTxt.setText("Bunches");
                    pricePerUnitTxt.setText("Bunches");
                    quantitySoldUnitsTxt.setText("/ Bunch");


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
        CropDashboardActivity.addDatePicker(repeatUntilTxt,this);


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
        TextWatcher watcher =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                computeIncomeGenerated();
            }

            @Override
            public void afterTextChanged(Editable s) {
                computeIncomeGenerated();
            }
        };


        priceTxt.addTextChangedListener(watcher);
        quantitySoldTxt.addTextChangedListener(watcher);

        recurrenceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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


        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        fillViews();
    }
    public void saveHarvest() {
        cropHarvest = new CropHarvest();
        cropHarvest.setUserId(CropDashboardActivity.getPreferences("userId", this));
        cropHarvest.setCropId(cropId);
        cropHarvest.setDate(harvestDateTxt.getText().toString());
        cropHarvest.setMethod(harvestMethodTxt.getText().toString());
        cropHarvest.setUnits(harvestUnitsSpinner.getSelectedItem().toString());
        cropHarvest.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
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
        cropHarvest.setUnits(quantityStoredUnitsTxt.getText().toString());
        cropHarvest.setUnits("/ "+pricePerUnitTxt.getText().toString());
        cropHarvest.setUnits(quantitySoldUnitsTxt.getText().toString());
        cropHarvest.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cropHarvest.setReminders(remindersSp.getSelectedItem().toString());
        if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
            String weeks = weeksTxt.getText().toString();
            String repeatUntil = repeatUntilTxt.getText().toString();

            cropHarvest.setFrequency(Float.parseFloat(weeks));
            cropHarvest.setRepeatUntil(repeatUntil);
        }
        if(daysBeforeLayout.getVisibility()==View.VISIBLE){
            String days = daysBeforeTxt.getText().toString();


            cropHarvest.setDaysBefore(days);

        }

        dbHandler.insertCropHarvest(cropHarvest);
    }

    public void updateHarvest(){
        if(cropHarvest != null){
            cropHarvest.setUserId(CropDashboardActivity.getPreferences("userId", this));
            cropHarvest.setCropId(cropId);
            cropHarvest.setDate(harvestDateTxt.getText().toString());
            cropHarvest.setMethod(harvestMethodTxt.getText().toString());
            cropHarvest.setUnits(harvestUnitsSpinner.getSelectedItem().toString());
            cropHarvest.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
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
            cropHarvest.setUnits(quantityStoredUnitsTxt.getText().toString());
            cropHarvest.setUnits("/ "+pricePerUnitTxt.getText().toString());
            cropHarvest.setUnits(quantitySoldUnitsTxt.getText().toString());
            cropHarvest.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cropHarvest.setReminders(remindersSp.getSelectedItem().toString());
            if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
                String weeks = weeksTxt.getText().toString();
                String repeatUntil = repeatUntilTxt.getText().toString();

                cropHarvest.setFrequency(Float.parseFloat(weeks));
                cropHarvest.setRepeatUntil(repeatUntil);
            }
            if(daysBeforeLayout.getVisibility()==View.VISIBLE){
                String days = daysBeforeTxt.getText().toString();


                cropHarvest.setDaysBefore(days);

            }


            dbHandler.updateCropHarvest(cropHarvest);
        }
    }
    public void fillViews(){
        if(cropHarvest != null){
            CropDashboardActivity.selectSpinnerItemByValue(harvestUnitsSpinner, cropHarvest.getUnits());
            CropDashboardActivity.selectSpinnerItemById(operatorSpinner, cropHarvest.getOperator());
            CropDashboardActivity.selectSpinnerItemByValue(statusSpinner, cropHarvest.getStatus());
            CropDashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropHarvest.getRecurrence());
            CropDashboardActivity.selectSpinnerItemByValue(remindersSp, cropHarvest.getReminders());

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
            quantityStoredUnitsTxt.setText(cropHarvest.getUnits());
            pricePerUnitTxt.setText(cropHarvest.getUnits());
            quantitySoldUnitsTxt.setText(cropHarvest.getUnits());
            weeksTxt.setText(cropHarvest.getFrequency()+"");
            repeatUntilTxt.setText(cropHarvest.getRepeatUntil());
            daysBeforeTxt.setText(cropHarvest.getDaysBefore());

        }

    }

    public float computeIncomeGenerated(){
        try{
            float price = Float.parseFloat(priceTxt.getText().toString());
            float quantitySold = Float.parseFloat(quantitySoldTxt.getText().toString());
            float incomeGenerated = (price*quantitySold);
            incomeGeneratedTxt.setText(incomeGenerated+"");
            return incomeGenerated;
        }catch (Exception e){

        }
        return 0;
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
        else if(recurrenceSp.getSelectedItemPosition()==0){
            message = getString(R.string.recurrence_not_selected);
            recurrenceSp.requestFocus();
        }
        else if(remindersSp.getSelectedItemPosition()==0){
            message = getString(R.string.reminders_not_selected);
            remindersSp.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropHarvestManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }



}
