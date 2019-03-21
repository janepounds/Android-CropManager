package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.Crop;
import com.myfarmnow.myfarmcrop.models.CropField;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;

public class CropsManagerActivity extends AppCompatActivity {
   EditText dateTxt, varietyTxt, yearTxt,areaTxt,operatorTxt,costTxt,rateTxt;
   Spinner cropSP,growingCycleSp,seedSp,fieldSp,plantingMethodSp, seasonSp;
   Crop crop;
   MyFarmDbHandlerSingleton dbHandler;
   Button saveBtn;
   CropSpinnerAdapter fieldsSpinnerAdapter, seedsSpinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_manager);

        if(getIntent().hasExtra("crop")){
            crop =(Crop) getIntent().getSerializableExtra("crop");
        }
        initializeForm();
    }

    public void initializeForm(){
        dateTxt = findViewById(R.id.txt_crops_date_sown);
        varietyTxt  = findViewById(R.id.txt_crops_variety);
        yearTxt  = findViewById(R.id.txt_crops_cropping_year);
        areaTxt  = findViewById(R.id.txt_crops_area);
        operatorTxt  = findViewById(R.id.txt_crops_operator);
        rateTxt  = findViewById(R.id.txt_crops_rate);
        costTxt  = findViewById(R.id.txt_crops_operator_cost);
        cropSP  = findViewById(R.id.sp_crop_crop);
        growingCycleSp  = findViewById(R.id.sp_crops_growing_cycle);
        seasonSp  = findViewById(R.id.sp_crops_season);
        seedSp  = findViewById(R.id.sp_crops_seed_name);
        fieldSp  = findViewById(R.id.sp_crops_field);
        plantingMethodSp  = findViewById(R.id.sp_crops_planting_method);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(dateTxt,this);
        saveBtn = findViewById(R.id.btn_save);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(crop==null){
                        saveCrop();
                    }
                    else{
                        updateCrop();
                    }
                    Intent toCropsList = new Intent(CropsManagerActivity.this, CropsListActivity.class);
                    toCropsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropsList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });

        ArrayList<CropSpinnerItem> fieldsItems = new ArrayList<>();
        for(CropField x: dbHandler.getCropFields(CropDashboardActivity.getPreferences("userId",this))){
            fieldsItems.add(x);
        }
        fieldsSpinnerAdapter = new CropSpinnerAdapter(fieldsItems,"Field",this);
        fieldSp.setAdapter(fieldsSpinnerAdapter);

        ArrayList<CropSpinnerItem> seedItems = new ArrayList<>();
        for(CropInventorySeeds x: dbHandler.getCropSeeds(CropDashboardActivity.getPreferences("userId",this))){
            seedItems.add(x);
        }
        seedsSpinnerAdapter = new CropSpinnerAdapter(seedItems,"Seed",this);
        seedSp.setAdapter(seedsSpinnerAdapter);

        ((ArrayAdapter)cropSP.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        fillViews();
    }

    public void saveCrop(){
        crop = new Crop();
        crop.setUserId(CropDashboardActivity.getPreferences("userId",this));
        crop.setName(cropSP.getSelectedItem().toString());
        crop.setVariety(varietyTxt.getText().toString());
        crop.setCroppingYear(Integer.parseInt(yearTxt.getText().toString()));
        crop.setFieldId(((CropField)fieldSp.getSelectedItem()).getId());
        crop.setGrowingCycle(growingCycleSp.getSelectedItem().toString());
        crop.setSeason(seasonSp.getSelectedItem().toString());
        crop.setDateSown(dateTxt.getText().toString());
        crop.setArea(Float.parseFloat(areaTxt.getText().toString()));
        crop.setOperator(operatorTxt.getText().toString());
        crop.setCost(Float.parseFloat(costTxt.getText().toString()));
        if(seedSp.getSelectedItemPosition()!=0){
            crop.setSeedId(((CropInventorySeeds)seedSp.getSelectedItem()).getId());
        }
        crop.setRate(Float.parseFloat(rateTxt.getText().toString()));
        crop.setPlantingMethod(plantingMethodSp.getSelectedItem().toString());
        dbHandler.insertCrop(crop);
    }
    public void updateCrop(){
        if(crop != null){
            crop.setName(cropSP.getSelectedItem().toString());
            crop.setVariety(varietyTxt.getText().toString());
            crop.setCroppingYear(Integer.parseInt(yearTxt.getText().toString()));
            crop.setFieldId(((CropField)fieldSp.getSelectedItem()).getId());
            crop.setGrowingCycle(growingCycleSp.getSelectedItem().toString());
            crop.setSeason(seasonSp.getSelectedItem().toString());
            crop.setDateSown(dateTxt.getText().toString());
            crop.setArea(Float.parseFloat(areaTxt.getText().toString()));
            crop.setOperator(operatorTxt.getText().toString());
            crop.setCost(Float.parseFloat(costTxt.getText().toString()));
            if(seedSp.getSelectedItemPosition()!=0){
                crop.setSeedId(((CropInventorySeeds)seedSp.getSelectedItem()).getId());
            }
            crop.setRate(Float.parseFloat(rateTxt.getText().toString()));
            crop.setPlantingMethod(plantingMethodSp.getSelectedItem().toString());

            dbHandler.updateCrop(crop);
        }

    }

    public void fillViews(){
        if(crop != null){
            CropDashboardActivity.selectSpinnerItemByValue(cropSP,crop.getName());
            varietyTxt.setText(crop.getVariety());
            yearTxt.setText(crop.getCroppingYear()+"");
            CropDashboardActivity.selectSpinnerItemByValue(growingCycleSp,crop.getGrowingCycle());
            CropDashboardActivity.selectSpinnerItemByValue(seasonSp,crop.getSeason());
            CropDashboardActivity.selectSpinnerItemByValue(plantingMethodSp,crop.getPlantingMethod());
            dateTxt.setText(crop.getDateSown());
            areaTxt.setText(crop.getArea()+"");
            operatorTxt.setText(crop.getOperator());
            costTxt.setText(crop.getCost()+"");
            rateTxt.setText(crop.getRate()+"");
            CropDashboardActivity.selectSpinnerItemById(seedSp,crop.getSeedId());
            CropDashboardActivity.selectSpinnerItemById(fieldSp,crop.getFieldId());
        }

    }

    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }
        else if(areaTxt.getText().toString().isEmpty()){
            message = getString(R.string.area_not_entered);
            areaTxt.requestFocus();
        }
        else if(rateTxt.getText().toString().isEmpty()){
            message = getString(R.string.rate_not_entered);
            rateTxt.requestFocus();
        }else if(operatorTxt.getText().toString().isEmpty()){
            message = getString(R.string.operator_not_entered);
            operatorTxt.requestFocus();
        }
        else if(cropSP.getSelectedItemPosition()==0){
            message = getString(R.string.crop_not_selected);
            cropSP.requestFocus();
        }
        else if(fieldSp.getSelectedItemPosition()==0){
            message = getString(R.string.field_not_selected);
            fieldSp.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropsManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

}
