package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropInventorySeedsManagerActivity extends AppCompatActivity {

    CropInventorySeeds seedsInventoryToEdit=null;
    EditText purchaseDatTxt, seedNameTxt,varietyTxt,dressingTxt,tgwTxt,quantityTxt,costTxt, batchTxt,supplierTxt;
    TextView currencyTxt;
    Spinner usageUnitSpinner,typeSp;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_inventory_seeds_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getIntent().hasExtra("seedsInventory")){
            seedsInventoryToEdit =(CropInventorySeeds)getIntent().getSerializableExtra("seedsInventory");
        }
        initializeForm();
    }

    public void initializeForm(){
        purchaseDatTxt = findViewById(R.id.txt_crop_purchase_date);
        seedNameTxt = findViewById(R.id.txt_crop_seed_name);
        currencyTxt = findViewById(R.id.txt_crop_seeds_currency);
        varietyTxt = findViewById(R.id.txt_crop_variety);
        dressingTxt = findViewById(R.id.txt_crop_dressing);
        tgwTxt = findViewById(R.id.txt_crop_tgw);
        usageUnitSpinner = findViewById(R.id.txt_crop_usage_unit);
        quantityTxt = findViewById(R.id.txt_crop_quantity);
        costTxt = findViewById(R.id.txt_crop_cost);
        batchTxt = findViewById(R.id.txt_crop_batch_number);
        supplierTxt = findViewById(R.id.txt_crop_supplier);
        saveBtn = findViewById(R.id.btn_save);
        typeSp = findViewById(R.id.sp_crop_seed_type);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(purchaseDatTxt,this);

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

    public void fillViews(){
        if(seedsInventoryToEdit != null){

            CropDashboardActivity.selectSpinnerItemByValue(usageUnitSpinner,seedsInventoryToEdit.getUsageUnits());
            CropDashboardActivity.selectSpinnerItemByValue(typeSp,seedsInventoryToEdit.getType());
            purchaseDatTxt.setText(seedsInventoryToEdit.getDateOfPurchase());
            seedNameTxt.setText(seedsInventoryToEdit.getName());
            varietyTxt.setText(seedsInventoryToEdit.getVariety());
            dressingTxt.setText(seedsInventoryToEdit.getDressing());
            quantityTxt.setText(seedsInventoryToEdit.getQuantity()+"");
            costTxt.setText(seedsInventoryToEdit.getCost()+"");
            batchTxt.setText(seedsInventoryToEdit.getBatchNumber()+"");
            supplierTxt.setText(seedsInventoryToEdit.getSupplier()+"");
            tgwTxt.setText(seedsInventoryToEdit.getTgw()+"");


        }
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
        seedsInventoryToEdit.setType(typeSp.getSelectedItem().toString());

        dbHandler.insertCropSeeds(seedsInventoryToEdit);



    }
    public void updateSeeds(){

        if(seedsInventoryToEdit != null) {

            seedsInventoryToEdit.setUserId(CropDashboardActivity.getPreferences("userId", this));
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
            seedsInventoryToEdit.setType(typeSp.getSelectedItem().toString());

            dbHandler.updateCropSeeds(seedsInventoryToEdit);
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
        else if(typeSp.getSelectedItemPosition()==0){
            message = getString(R.string.seed_type_not_selected);
            typeSp.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropInventorySeedsManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
       // Log.d("ERROR",message);
        return true;
    }



}
