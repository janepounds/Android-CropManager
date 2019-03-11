package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropSpraying;

import java.util.ArrayList;

public class CropSprayingManagerActivity extends AppCompatActivity {


    EditText dateTxt, startTimeTxt,endTimeTxt, operatorTxt, waterVolumeTxt,costTxt, rateTxt,reasonTxt, equipmentUsedTxt;
    Button btn_save;
    CropSpraying spraying;
    String cropId;
    MyFarmDbHandlerSingleton dbHandler;
    Spinner windDirectionSp,waterConditionSp,sprayIdSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_spraying_manager);
        if(getIntent().hasExtra("spraying")){
            spraying =(CropSpraying) getIntent().getSerializableExtra("spraying");
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
        dateTxt =findViewById(R.id.txt_crop_spraying_treatment_date);
        startTimeTxt =findViewById(R.id.txt_crop_spraying_start_time);
        endTimeTxt =findViewById(R.id.txt_crop_spraying_end_time);
        operatorTxt =findViewById(R.id.txt_crop_spraying_performed_by);
        waterVolumeTxt =findViewById(R.id.txt_crop_spraying_water_volume);
        costTxt =findViewById(R.id.txt_crop_spraying_labour_cost);
        rateTxt =findViewById(R.id.txt_crop_spraying_rate);
        reasonTxt =findViewById(R.id.txt_crop_spraying_treatment_reason);
        equipmentUsedTxt =findViewById(R.id.txt_crop_spraying_equipment_used);
        sprayIdSp =findViewById(R.id.sp_crop_spraying_name);
        windDirectionSp =findViewById(R.id.sp_crop_spraying_wind_direction);
        waterConditionSp =findViewById(R.id.sp_crop_spraying_weather_condition);

        btn_save = findViewById(R.id.btn_save);
        CropDashboardActivity.addDatePicker(dateTxt,this);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(spraying==null){
                        saveSpraying();
                    }
                    else{
                        updateSpraying();
                    }
                    Intent toCropsList = new Intent(CropSprayingManagerActivity.this, CropSprayingListActivity.class);
                    toCropsList.putExtra("cropId",cropId);
                    toCropsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropsList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ArrayList<CropSpinnerItem> spraysList = new ArrayList<>();
        for(CropInventorySpray x: dbHandler.getCropSpray(CropDashboardActivity.getPreferences("userId",this))){
            spraysList.add(x);
        }
        CropSpinnerAdapter fertilizerAdapter  =new CropSpinnerAdapter(spraysList,"Spray",this);
        sprayIdSp.setAdapter(fertilizerAdapter);
        fillViews();
    }

    public void saveSpraying(){
        spraying = new CropSpraying();
        spraying.setUserId(CropDashboardActivity.getPreferences("userId",this));
        spraying.setDate(dateTxt.getText().toString());
        spraying.setRate(Float.parseFloat(rateTxt.getText().toString()));
        spraying.setWaterVolume(Float.parseFloat(waterVolumeTxt.getText().toString()));
        spraying.setCropId(cropId);
        spraying.setStartTime(startTimeTxt.getText().toString());
        spraying.setEndTime(endTimeTxt.getText().toString());
        spraying.setCost(Float.parseFloat(costTxt.getText().toString()));
        spraying.setOperator(operatorTxt.getText().toString());
        spraying.setEquipmentUsed(equipmentUsedTxt.getText().toString());
        spraying.setTreatmentReason(reasonTxt.getText().toString());
        spraying.setSprayId(((CropSpinnerItem)sprayIdSp.getSelectedItem()).getId());

        if(waterConditionSp.getSelectedItemPosition()==0){
            spraying.setWaterCondition(waterConditionSp.getSelectedItem().toString());
        }
        if(windDirectionSp.getSelectedItemPosition()==0){
            spraying.setWindDirection(windDirectionSp.getSelectedItem().toString());
        }
        dbHandler.insertCropSpraying(spraying);

    }
    public void updateSpraying(){
        if(spraying != null){
            spraying.setDate(dateTxt.getText().toString());
            spraying.setRate(Float.parseFloat(rateTxt.getText().toString()));
            spraying.setWaterVolume(Float.parseFloat(waterVolumeTxt.getText().toString()));
            spraying.setCropId(cropId);
            spraying.setStartTime(startTimeTxt.getText().toString());
            spraying.setEndTime(endTimeTxt.getText().toString());
            spraying.setCost(Float.parseFloat(costTxt.getText().toString()));
            spraying.setOperator(operatorTxt.getText().toString());
            spraying.setEquipmentUsed(equipmentUsedTxt.getText().toString());
            spraying.setTreatmentReason(reasonTxt.getText().toString());
            spraying.setSprayId(((CropSpinnerItem)sprayIdSp.getSelectedItem()).getId());
            if(waterConditionSp.getSelectedItemPosition()!=0){
                spraying.setWaterCondition(waterConditionSp.getSelectedItem().toString());
            }
            if(windDirectionSp.getSelectedItemPosition()!=0){
                spraying.setWindDirection(windDirectionSp.getSelectedItem().toString());
            }
            dbHandler.updateCropSpraying(spraying);
        }
    }
    public void fillViews(){
        if(spraying != null){
            CropDashboardActivity.selectSpinnerItemByValue(windDirectionSp, spraying.getWindDirection());
            CropDashboardActivity.selectSpinnerItemByValue(waterConditionSp, spraying.getWaterCondition());
            rateTxt.setText(spraying.getRate()+"");
            waterVolumeTxt.setText(spraying.getWaterVolume()+"");
            dateTxt.setText(spraying.getDate());
            startTimeTxt.setText(spraying.getStartTime()+"");
            endTimeTxt.setText(spraying.getEndTime()+"");
            equipmentUsedTxt.setText(spraying.getEquipmentUsed());
            reasonTxt.setText(spraying.getTreatmentReason());
            operatorTxt.setText(spraying.getOperator());
            costTxt.setText(spraying.getCost()+"");
            rateTxt.setText(spraying.getRate()+"");

            CropDashboardActivity.selectSpinnerItemById(sprayIdSp,spraying.getId());
        }

    }

    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }
        else if(operatorTxt.getText().toString().isEmpty()){
            message = getString(R.string.operator_not_entered);
            operatorTxt.requestFocus();
        }
        else if(costTxt.getText().toString().isEmpty()){
            message = getString(R.string.crop_not_entered);
            costTxt.requestFocus();
        }else if(rateTxt.getText().toString().isEmpty()){
            message = getString(R.string.rate_not_entered);
            rateTxt.requestFocus();
        }
        else if(equipmentUsedTxt.getText().toString().isEmpty()){
            message = getString(R.string.equipment_not_entered);
            equipmentUsedTxt.requestFocus();
        }else if(sprayIdSp.getSelectedItemPosition()==0){
            message = getString(R.string.spray_name_not_entered);
            sprayIdSp.requestFocus();
        }

        if(waterVolumeTxt.getText().toString().isEmpty()){
            waterVolumeTxt.setText("0");
        }



        if(message != null){
            Toast.makeText(CropSprayingManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}
