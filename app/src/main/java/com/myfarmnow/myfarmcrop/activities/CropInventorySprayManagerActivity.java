package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;

public class CropInventorySprayManagerActivity extends AppCompatActivity {

    public EditText  purchaseDatTxt,seedNameTxt, typeTxt,costTxt,
            quantityTxt,batchTxt,supplierTxt,activeIngredientsTxt,harvestIntervalTxt,expiryDateTxt;
    Button saveBtn;
    Spinner usageUnitSpinner;
    MyFarmDbHandlerSingleton dbHandler;
    CropInventorySpray sprayInventory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_inventory_spray_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("sprayInventory")){
            sprayInventory =(CropInventorySpray)getIntent().getSerializableExtra("sprayInventory");
        }
        initializeForm();


    }

    public void initializeForm(){
        purchaseDatTxt = findViewById(R.id.txt_crop_spray_date_of_purchase);
        seedNameTxt = findViewById(R.id.txt_crop_spray_spray_name);
        typeTxt = findViewById(R.id.txt_crop_spray_type);
        harvestIntervalTxt = findViewById(R.id.txt_crop_spray_harvest_interval);
        activeIngredientsTxt = findViewById(R.id.txt_crop_spray_active_ingreds);
        expiryDateTxt = findViewById(R.id.txt_crop_pray_exp_date);
        usageUnitSpinner = findViewById(R.id.spinner_crop_spray_unit);
        quantityTxt = findViewById(R.id.txt_crop_spray_qty);
        costTxt = findViewById(R.id.txt_crop_spray_cost);
        batchTxt = findViewById(R.id.txt_crop_spray_batch_no);
        supplierTxt = findViewById(R.id.txt_crop_spray_supplier);
        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(purchaseDatTxt,this);
        CropDashboardActivity.addDatePicker(expiryDateTxt,this);
        fillViews();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(sprayInventory==null){
                        saveSeeds();
                    }
                    else{
                        updateSeeds();
                    }
                    Intent toCropInventoryList = new Intent(CropInventorySprayManagerActivity.this, CropInventoryListActivity.class);
                    toCropInventoryList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropInventoryList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }
    public void fillViews(){
        if(sprayInventory != null){

            expiryDateTxt.setText(sprayInventory.getExpiryDate());
            purchaseDatTxt.setText(sprayInventory.getDateOfPurchase());
            seedNameTxt.setText(sprayInventory.getName());
            typeTxt.setText(sprayInventory.getType());
            harvestIntervalTxt.setText(sprayInventory.getHarvestInterval()+"");
            activeIngredientsTxt.setText(sprayInventory.getActiveIngredients());
            activeIngredientsTxt.setText(sprayInventory.getActiveIngredients());
            CropDashboardActivity.selectSpinnerItemByValue(usageUnitSpinner,sprayInventory.getUsageUnits());
            quantityTxt.setText(sprayInventory.getQuantity()+"");
            costTxt.setText(sprayInventory.getCost()+"");
            batchTxt.setText(sprayInventory.getBatchNumber()+"");
            supplierTxt.setText(sprayInventory.getSupplier()+"");

        }
    }
    public void saveSeeds(){
        sprayInventory = new CropInventorySpray();
        sprayInventory.setUserId(CropDashboardActivity.getPreferences("userId",this));
        sprayInventory.setUsageUnits(usageUnitSpinner.getSelectedItem().toString());
        sprayInventory.setDateOfPurchase(purchaseDatTxt.getText().toString());

        sprayInventory.setName(seedNameTxt.getText().toString());
        sprayInventory.setType(typeTxt.getText().toString());
        sprayInventory.setActiveIngredients(activeIngredientsTxt.getText().toString());
        sprayInventory.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
        sprayInventory.setCost(Float.parseFloat(costTxt.getText().toString()));
        sprayInventory.setBatchNumber(batchTxt.getText().toString());
        sprayInventory.setSupplier(supplierTxt.getText().toString());
        sprayInventory.setHarvestInterval(Integer.parseInt(harvestIntervalTxt.getText().toString()));
        sprayInventory.setExpiryDate(expiryDateTxt.getText().toString());
       // sprayInventory.setTgw(tgwTxt.getText().toString());


        dbHandler.insertCropSpray(sprayInventory);



    }
    public void updateSeeds(){

        if(sprayInventory != null) {

            sprayInventory.setUserId(CropDashboardActivity.getPreferences("userId",this));
            sprayInventory.setUsageUnits(usageUnitSpinner.getSelectedItem().toString());
            sprayInventory.setDateOfPurchase(purchaseDatTxt.getText().toString());
            sprayInventory.setName(seedNameTxt.getText().toString());
            sprayInventory.setType(typeTxt.getText().toString());
            sprayInventory.setActiveIngredients(activeIngredientsTxt.getText().toString());
            sprayInventory.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
            sprayInventory.setCost(Float.parseFloat(costTxt.getText().toString()));
            sprayInventory.setBatchNumber(batchTxt.getText().toString());
            sprayInventory.setSupplier(supplierTxt.getText().toString());
            sprayInventory.setHarvestInterval(Integer.parseInt(harvestIntervalTxt.getText().toString()));
            sprayInventory.setExpiryDate(expiryDateTxt.getText().toString());
            Log.d("TASTE",sprayInventory.getName()+" : "+seedNameTxt.getText().toString());
            dbHandler.updateCropSpray(sprayInventory);
        }
    }

    public boolean validateEntries(){
        String message = null;
        if(purchaseDatTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            purchaseDatTxt.requestFocus();
        }
        else if(seedNameTxt.getText().toString().isEmpty()){
            message = getString(R.string.seed_name_not_entered_message);
            seedNameTxt.requestFocus();
        }
        else if(quantityTxt.getText().toString().isEmpty()){
            message = getString(R.string.quantity_not_entered_message);
            quantityTxt.requestFocus();
        }else if(batchTxt.getText().toString().isEmpty()){
            message = getString(R.string.batch_number_entered_message);
            batchTxt.requestFocus();
        }
        else if(usageUnitSpinner.getSelectedItemPosition()==0){
            message = getString(R.string.usage_units_not_selected);
            usageUnitSpinner.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropInventorySprayManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}
