package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
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
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropInventoryFertilizerManagerActivity extends AppCompatActivity {
//TODO  FIX BUG- USAGE UNITS NOT SHOWING
    EditText dateTxt,nameTxt,batchNumberTxt,npkNTxt, npkPTxt, npkKTxt,qtyTxt,serialNumberTxt,supplierTxt,costTxt;
    EditText macrosCa,macrosMg,macrosS,microsB,microsMn,microsCl,microsMo,microsCu,microsZn,microsFe,microsNa;
    TextView currencyTxt;
    Spinner typeSp,usageUnitSp;
    Button saveBtn;
    CropInventoryFertilizer fertilizerInventory =null;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_inventory_fertilizer_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getIntent().hasExtra("fertilizerInventory")){
            fertilizerInventory =(CropInventoryFertilizer)getIntent().getSerializableExtra("fertilizerInventory");
        }
        initializeForm();


    }


    public void initializeForm(){
        dateTxt = findViewById(R.id.txt_crop_fertilizer_date_of_purchase);
        nameTxt = findViewById(R.id.txt_crop_fertilizer_fertilizer_name);
        typeSp = findViewById(R.id.spinner_crop_fertilizer_fertilizer_type);
        batchNumberTxt = findViewById(R.id.txt_crop_fertilizer_batch_number);
        serialNumberTxt = findViewById(R.id.txt_crop_fertilizer_serial_number);
        usageUnitSp = findViewById(R.id.spinner_crop_fertilizer_usage_unit);
        supplierTxt = findViewById(R.id.txt_crop_fertilizer_supplier);
        costTxt = findViewById(R.id.txt_crop_fertilizer_unit_cost);
        qtyTxt = findViewById(R.id.txt_crop_fertilizer_qty);
        currencyTxt = findViewById(R.id.txt_crop_fertilizer_currency);

        npkNTxt = findViewById(R.id.txt_crop_fertilizer_npk_n);
        npkKTxt = findViewById(R.id.txt_crop_fertilizer_npk_k);
        npkPTxt = findViewById(R.id.txt_crop_fertilizer_npk_p);
        macrosCa = findViewById(R.id.txt_crop_fertilizer_macros_ca);
        macrosMg = findViewById(R.id.txt_crop_fertilizer_macros_mg);
        macrosS = findViewById(R.id.txt_crop_fertilizer_macros_s);
        microsB= findViewById(R.id.txt_crop_fertilizer_micros_b);
        microsMn= findViewById(R.id.txt_crop_fertilizer_micros_mn);
        microsCl= findViewById(R.id.txt_crop_fertilizer_micros_cl);
        microsMo= findViewById(R.id.txt_crop_fertilizer_micros_mo);
        microsCu= findViewById(R.id.txt_crop_fertilizer_micros_cu);
        microsZn= findViewById(R.id.txt_crop_fertilizer_micros_zn);
        microsFe= findViewById(R.id.txt_crop_fertilizer_micros_fe);
        microsNa= findViewById(R.id.txt_crop_fertilizer_micros_na);
        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(dateTxt,this);
        ((ArrayAdapter)typeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)usageUnitSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

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
        usageUnitSp.setOnItemSelectedListener(onItemSelectedListener);
        typeSp.setOnItemSelectedListener(onItemSelectedListener);



        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());


        fillViews();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(fertilizerInventory==null){
                        saveSeeds();
                    }
                    else{
                        updateSeeds();
                    }
                    Intent toCropInventoryList = new Intent(CropInventoryFertilizerManagerActivity.this, CropInventoryListActivity.class);
                    toCropInventoryList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropInventoryList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }

    public void fillViews(){
        if(fertilizerInventory != null){

            CropDashboardActivity.selectSpinnerItemByValue(usageUnitSp,fertilizerInventory.getUsageUnits());
            Log.d("USAGE UNITS", fertilizerInventory.getUsageUnits());
            CropDashboardActivity.selectSpinnerItemByValue(typeSp,fertilizerInventory.getType());
            dateTxt.setText(fertilizerInventory.getPurchaseDate());
            nameTxt.setText(fertilizerInventory.getName());
            npkNTxt.setText(fertilizerInventory.getnPercentage()+"");
            npkKTxt.setText(fertilizerInventory.getkPercentage()+"");
            npkPTxt.setText(fertilizerInventory.getpPercentage()+"");
            qtyTxt.setText(fertilizerInventory.getQuantity()+"");
            costTxt.setText(fertilizerInventory.getCost()+"");
            serialNumberTxt.setText(fertilizerInventory.getSerialNumber());
            batchNumberTxt.setText(fertilizerInventory.getBatchNumber());
            supplierTxt.setText(fertilizerInventory.getSupplier());
            macrosCa.setText(fertilizerInventory.getMacroNutrientsCa()+"");
            macrosMg.setText(fertilizerInventory.getMacroNutrientsMg()+"");
            macrosS.setText(fertilizerInventory.getMacroNutrientsS()+"");
            microsB.setText(fertilizerInventory.getMicroNutrientsB()+"");
            microsMn.setText(fertilizerInventory.getMicroNutrientsMn()+"");
            microsCl.setText(fertilizerInventory.getMicroNutrientsCl()+"");
            microsMo.setText(fertilizerInventory.getMicroNutrientsMo()+"");
            microsCu.setText(fertilizerInventory.getMicroNutrientsCu()+"");
            microsZn.setText(fertilizerInventory.getMicroNutrientsZn()+"");
            microsNa.setText(fertilizerInventory.getMicroNutrientsNa()+"");
            microsFe.setText(fertilizerInventory.getMicroNutrientsFe()+"");




        }
    }
    public void saveSeeds(){
        fertilizerInventory = new CropInventoryFertilizer();
        fertilizerInventory.setUserId(CropDashboardActivity.getPreferences("userId",this));
        fertilizerInventory.setPurchaseDate(dateTxt.getText().toString());
        fertilizerInventory.setFertilizerName(nameTxt.getText().toString());
        fertilizerInventory.setType(typeSp.getSelectedItem().toString());
        fertilizerInventory.setnPercentage(Float.parseFloat(npkNTxt.getText().toString()));
        fertilizerInventory.setpPercentage(Float.parseFloat(npkPTxt.getText().toString()));
        fertilizerInventory.setkPercentage(Float.parseFloat(npkKTxt.getText().toString()));
        fertilizerInventory.setQuantity(Float.parseFloat(qtyTxt.getText().toString()));
        fertilizerInventory.setBatchNumber(batchNumberTxt.getText().toString());
        fertilizerInventory.setSerialNumber(serialNumberTxt.getText().toString());
        fertilizerInventory.setSupplier(supplierTxt.getText().toString());
        fertilizerInventory.setUsageUnits(usageUnitSp.getSelectedItem().toString());
        fertilizerInventory.setCost(Float.parseFloat(costTxt.getText().toString()));
        fertilizerInventory.setMacroNutrientsCa(Float.parseFloat(macrosCa.getText().toString()));
        fertilizerInventory.setMacroNutrientsMg(Float.parseFloat(macrosMg.getText().toString()));
        fertilizerInventory.setMacroNutrientsS(Float.parseFloat(macrosS.getText().toString()));
        fertilizerInventory.setMicroNutrientsB(Float.parseFloat(microsB.getText().toString()));
        fertilizerInventory.setMicroNutrientsMn(Float.parseFloat(microsMn.getText().toString()));
        fertilizerInventory.setMicroNutrientsCl(Float.parseFloat(microsCl.getText().toString()));
        fertilizerInventory.setMicroNutrientsMo(Float.parseFloat(microsMo.getText().toString()));
        fertilizerInventory.setMicroNutrientsCu(Float.parseFloat(microsCu.getText().toString()));
        fertilizerInventory.setMicroNutrientsZn(Float.parseFloat(microsZn.getText().toString()));
        fertilizerInventory.setMicroNutrientsFe(Float.parseFloat(microsFe.getText().toString()));
        fertilizerInventory.setMicroNutrientsNa(Float.parseFloat(microsNa.getText().toString()));
        Log.d("USAGE UNITS", fertilizerInventory.getUsageUnits());

        dbHandler.insertCropFertilizerInventory(fertilizerInventory);



    }
    public void updateSeeds(){

        if(fertilizerInventory != null) {

            fertilizerInventory.setUserId(CropDashboardActivity.getPreferences("userId",this));
            fertilizerInventory.setPurchaseDate(dateTxt.getText().toString());
            fertilizerInventory.setFertilizerName(nameTxt.getText().toString());
            fertilizerInventory.setType(typeSp.getSelectedItem().toString());
            fertilizerInventory.setnPercentage(Float.parseFloat(npkNTxt.getText().toString()));
            fertilizerInventory.setpPercentage(Float.parseFloat(npkPTxt.getText().toString()));
            fertilizerInventory.setkPercentage(Float.parseFloat(npkKTxt.getText().toString()));
            fertilizerInventory.setQuantity(Float.parseFloat(qtyTxt.getText().toString()));
            fertilizerInventory.setBatchNumber(batchNumberTxt.getText().toString());
            fertilizerInventory.setSerialNumber(serialNumberTxt.getText().toString());
            fertilizerInventory.setSupplier(supplierTxt.getText().toString());
            fertilizerInventory.setUsageUnits(usageUnitSp.getSelectedItem().toString());
            fertilizerInventory.setCost(Float.parseFloat(costTxt.getText().toString()));
            fertilizerInventory.setMacroNutrientsCa(Float.parseFloat(macrosCa.getText().toString()));
            fertilizerInventory.setMacroNutrientsMg(Float.parseFloat(macrosMg.getText().toString()));
            fertilizerInventory.setMacroNutrientsS(Float.parseFloat(macrosS.getText().toString()));
            fertilizerInventory.setMicroNutrientsB(Float.parseFloat(microsB.getText().toString()));
            fertilizerInventory.setMicroNutrientsMn(Float.parseFloat(microsMn.getText().toString()));
            fertilizerInventory.setMicroNutrientsCl(Float.parseFloat(microsCl.getText().toString()));
            fertilizerInventory.setMicroNutrientsMo(Float.parseFloat(microsMo.getText().toString()));
            fertilizerInventory.setMicroNutrientsCu(Float.parseFloat(microsCu.getText().toString()));
            fertilizerInventory.setMicroNutrientsZn(Float.parseFloat(microsZn.getText().toString()));
            fertilizerInventory.setMicroNutrientsFe(Float.parseFloat(microsFe.getText().toString()));
            fertilizerInventory.setMicroNutrientsNa(Float.parseFloat(microsNa.getText().toString()));
            dbHandler.updateCropFertilizerInventory(fertilizerInventory);
        }
    }
    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }
        else if(nameTxt.getText().toString().isEmpty()){
            message = getString(R.string.fertilizer_name_not_entered);
            nameTxt.requestFocus();
        }
        else if(qtyTxt.getText().toString().isEmpty()){
            message = getString(R.string.quantity_not_entered_message);
            qtyTxt.requestFocus();
        }else if(batchNumberTxt.getText().toString().isEmpty()){
            message = getString(R.string.batch_number_entered_message);
            batchNumberTxt.requestFocus();
        }
        else if(usageUnitSp.getSelectedItemPosition()==0){
            message = getString(R.string.usage_units_not_selected);
            usageUnitSp.requestFocus();
        }else if(typeSp.getSelectedItemPosition()==0){
            message = getString(R.string.fertilizer_type_not_selected);
            typeSp.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropInventoryFertilizerManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

}
