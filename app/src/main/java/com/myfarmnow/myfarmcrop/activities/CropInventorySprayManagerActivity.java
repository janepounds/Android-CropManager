package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropInventorySprayManagerActivity extends AppCompatActivity {

    public EditText  purchaseDatTxt,seedNameTxt,costTxt,
            quantityTxt,batchTxt,supplierTxt,activeIngredientsTxt,harvestIntervalTxt,expiryDateTxt;
    TextView currencyTxt;
    Button saveBtn;
    Spinner usageUnitSpinner,typeSp;
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
        typeSp = findViewById(R.id.sp_crop_spray_type);
        harvestIntervalTxt = findViewById(R.id.txt_crop_spray_harvest_interval);
        activeIngredientsTxt = findViewById(R.id.txt_crop_spray_active_ingreds);
        expiryDateTxt = findViewById(R.id.txt_crop_pray_exp_date);
        usageUnitSpinner = findViewById(R.id.spinner_crop_spray_unit);
        quantityTxt = findViewById(R.id.txt_crop_spray_qty);
        costTxt = findViewById(R.id.txt_crop_spray_cost);
        currencyTxt = findViewById(R.id.txt_crop_spray_currency);

        batchTxt = findViewById(R.id.txt_crop_spray_batch_no);
        supplierTxt = findViewById(R.id.txt_crop_spray_supplier);
        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(purchaseDatTxt,this);
        CropDashboardActivity.addDatePicker(expiryDateTxt,this);

        ((ArrayAdapter)usageUnitSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)typeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

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
        usageUnitSpinner.setOnItemSelectedListener(onItemSelectedListener);
        typeSp.setOnItemSelectedListener(onItemSelectedListener);



        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());


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
                    finish();
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
            harvestIntervalTxt.setText(sprayInventory.getHarvestInterval()+"");
            activeIngredientsTxt.setText(sprayInventory.getActiveIngredients());
            activeIngredientsTxt.setText(sprayInventory.getActiveIngredients());
            CropDashboardActivity.selectSpinnerItemByValue(usageUnitSpinner,sprayInventory.getUsageUnits());
            CropDashboardActivity.selectSpinnerItemByValue(typeSp,sprayInventory.getType());
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
        sprayInventory.setType(typeSp.getSelectedItem().toString());
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
            sprayInventory.setType(typeSp.getSelectedItem().toString());
            sprayInventory.setActiveIngredients(activeIngredientsTxt.getText().toString());
            sprayInventory.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
            sprayInventory.setCost(Float.parseFloat(costTxt.getText().toString()));
            sprayInventory.setBatchNumber(batchTxt.getText().toString());
            sprayInventory.setSupplier(supplierTxt.getText().toString());
            sprayInventory.setHarvestInterval(Integer.parseInt(harvestIntervalTxt.getText().toString()));
            sprayInventory.setExpiryDate(expiryDateTxt.getText().toString());
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
        }else if(typeSp.getSelectedItemPosition() ==0){
            message = getString(R.string.type_not_selected);
            typeSp.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropInventorySprayManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}
