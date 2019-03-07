package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;

public class CropInventorySeedsManagerActivity extends AppCompatActivity {

    CropInventorySeeds seedsInventoryToEdit=null;
    EditText purchaseDatTxt, seedNameTxt,varietyTxt,dressingTxt,tgwTxt,quantityTxt,costTxt, batchTxt,supplierTxt;
    Spinner usageUnitSpinner;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_inventory_seeds_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeForm();
    }

    public void initializeForm(){
        purchaseDatTxt = findViewById(R.id.txt_crop_purchase_date);
        seedNameTxt = findViewById(R.id.txt_crop_seed_name);
        varietyTxt = findViewById(R.id.txt_crop_variety);
        dressingTxt = findViewById(R.id.txt_crop_dressing);
        tgwTxt = findViewById(R.id.txt_crop_tgw);
        usageUnitSpinner = findViewById(R.id.txt_crop_usage_unit);
        quantityTxt = findViewById(R.id.txt_crop_quantity);
        costTxt = findViewById(R.id.txt_crop_cost);
        batchTxt = findViewById(R.id.txt_crop_batch_number);
        supplierTxt = findViewById(R.id.txt_crop_supplier);
        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(purchaseDatTxt,this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(seedsInventoryToEdit==null){
                        saveSeeds();
                    }
                    else{
                        updateSeeds();
                    }
                    Intent toCropInventoryList = new Intent(CropInventorySeedsManagerActivity.this, CropInventoryListActivity.class);
                    toCropInventoryList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropInventoryList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }

    public void saveSeeds(){
        seedsInventoryToEdit = new CropInventorySeeds();
        seedsInventoryToEdit.setUserId(CropDashboardActivity.getPreferences("userId",this));
        seedsInventoryToEdit.setUsageUnits(usageUnitSpinner.getSelectedItem().toString());
        seedsInventoryToEdit.setDateOfPurchase(purchaseDatTxt.getText().toString());
        seedsInventoryToEdit.setName(seedNameTxt.getText().toString());
        seedsInventoryToEdit.setVariety(varietyTxt.getText().toString());
        seedsInventoryToEdit.setDressing(dressingTxt.getText().toString());
        seedsInventoryToEdit.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
        seedsInventoryToEdit.setCost(Float.parseFloat(costTxt.getText().toString()));
        seedsInventoryToEdit.setBatchNumber(batchTxt.getText().toString());
        seedsInventoryToEdit.setSupplier(supplierTxt.getText().toString());
        seedsInventoryToEdit.setTgw(tgwTxt.getText().toString());

        dbHandler.insertCropSeeds(seedsInventoryToEdit);



    }
    public void updateSeeds(){
        seedsInventoryToEdit = new CropInventorySeeds();
        seedsInventoryToEdit.setUserId(CropDashboardActivity.getPreferences("userId",this));
        seedsInventoryToEdit.setUsageUnits(usageUnitSpinner.getSelectedItem().toString());
        seedsInventoryToEdit.setDateOfPurchase(purchaseDatTxt.getText().toString());
        seedsInventoryToEdit.setName(seedNameTxt.getText().toString());
        seedsInventoryToEdit.setVariety(varietyTxt.getText().toString());
        seedsInventoryToEdit.setDressing(dressingTxt.getText().toString());
        seedsInventoryToEdit.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
        seedsInventoryToEdit.setCost(Float.parseFloat(costTxt.getText().toString()));
        seedsInventoryToEdit.setBatchNumber(batchTxt.getText().toString());
        seedsInventoryToEdit.setSupplier(supplierTxt.getText().toString());
        seedsInventoryToEdit.setTgw(tgwTxt.getText().toString());

        dbHandler.updateCropSeeds(seedsInventoryToEdit);
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
            Toast.makeText(CropInventorySeedsManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
       // Log.d("ERROR",message);
        return true;
    }



}
