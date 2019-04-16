package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropSpraying;

import java.util.ArrayList;

public class CropSprayingManagerActivity extends AppCompatActivity {


    EditText dateTxt, startTimeTxt,endTimeTxt, operatorTxt, waterVolumeTxt,costTxt, rateTxt,
            reasonTxt, equipmentUsedTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    Button btn_save;
    CropSpraying cropSpraying;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout;
    String cropId;
    MyFarmDbHandlerSingleton dbHandler;
    Spinner windDirectionSp,waterConditionSp,sprayIdSp,recurrenceSp,remindersSp;

    TextView rateUnitsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_spraying_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        if(getIntent().hasExtra("cropSpraying")){
            cropSpraying =(CropSpraying) getIntent().getSerializableExtra("cropSpraying");
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
        rateUnitsTextView =findViewById(R.id.txt_crop_spraying_rate_units);
        sprayIdSp =findViewById(R.id.sp_crop_spraying_name);
        windDirectionSp =findViewById(R.id.sp_crop_spraying_wind_direction);
        waterConditionSp =findViewById(R.id.sp_crop_spraying_weather_condition);
        remindersSp = findViewById(R.id.sp_crop_spraying_reminders);
        recurrenceSp = findViewById(R.id.sp_crop_spraying_recurrence);
        weeksTxt = findViewById(R.id.txt_crop_spraying_weekly_weeks);
        repeatUntilTxt = findViewById(R.id.txt_crop_spraying_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_spraying_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_spraying_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_spraying_days_before);

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



        btn_save = findViewById(R.id.btn_save);
        CropDashboardActivity.addDatePicker(dateTxt,this);
        CropDashboardActivity.addTimePicker(startTimeTxt,this);
        CropDashboardActivity.addTimePicker(endTimeTxt,this);
        CropDashboardActivity.addDatePicker(repeatUntilTxt,this);

        ((ArrayAdapter)windDirectionSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)waterConditionSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropSpraying ==null){
                        saveSpraying();
                    }
                    else{
                        updateSpraying();
                    }
                    Intent toCropsList = new Intent(CropSprayingManagerActivity.this, CropActivitiesListActivity.class);
                    toCropsList.putExtra("cropId",cropId);
                    toCropsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropsList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayList<CropSpinnerItem> spraysList = new ArrayList<>();
        for(CropInventorySpray x: dbHandler.getCropSpray(CropDashboardActivity.getPreferences("userId",this))){
            spraysList.add(x);
        }
        CropSpinnerAdapter fertilizerAdapter  =new CropSpinnerAdapter(spraysList,"Spray",this);
        sprayIdSp.setAdapter(fertilizerAdapter);

        sprayIdSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    return;
                }
                CropInventorySpray inventorySpray = (CropInventorySpray) ((CropSpinnerItem)sprayIdSp.getSelectedItem());
                if(inventorySpray.getUsageUnits() != null){
                    rateUnitsTextView.setText(inventorySpray.getUsageUnits()+"/ha");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fillViews();
    }

    public void saveSpraying(){
        cropSpraying = new CropSpraying();
        cropSpraying.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropSpraying.setDate(dateTxt.getText().toString());
        cropSpraying.setRate(Float.parseFloat(rateTxt.getText().toString()));
        cropSpraying.setWaterVolume(Float.parseFloat(waterVolumeTxt.getText().toString()));
        cropSpraying.setCropId(cropId);
        cropSpraying.setStartTime(startTimeTxt.getText().toString());
        cropSpraying.setEndTime(endTimeTxt.getText().toString());
        cropSpraying.setCost(Float.parseFloat(costTxt.getText().toString()));
        cropSpraying.setOperator(operatorTxt.getText().toString());
        cropSpraying.setEquipmentUsed(equipmentUsedTxt.getText().toString());
        cropSpraying.setTreatmentReason(reasonTxt.getText().toString());
        cropSpraying.setSprayId(((CropSpinnerItem)sprayIdSp.getSelectedItem()).getId());
        cropSpraying.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cropSpraying.setReminders(remindersSp.getSelectedItem().toString());
        if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
            String weeks = weeksTxt.getText().toString();
            String repeatUntil = repeatUntilTxt.getText().toString();

            cropSpraying.setFrequency(Float.parseFloat(weeks));
            cropSpraying.setRepeatUntil(repeatUntil);
        }
        if(daysBeforeLayout.getVisibility()==View.VISIBLE){
            String days = daysBeforeTxt.getText().toString();


            cropSpraying.setDaysBefore(days);

        }


        if(waterConditionSp.getSelectedItemPosition()!=0){
            cropSpraying.setWaterCondition(waterConditionSp.getSelectedItem().toString());
        }
        if(windDirectionSp.getSelectedItemPosition()!=0){
            cropSpraying.setWindDirection(windDirectionSp.getSelectedItem().toString());
        }
        dbHandler.insertCropSpraying(cropSpraying);

    }
    public void updateSpraying(){
        if(cropSpraying != null){
            cropSpraying.setDate(dateTxt.getText().toString());
            cropSpraying.setRate(Float.parseFloat(rateTxt.getText().toString()));
            cropSpraying.setWaterVolume(Float.parseFloat(waterVolumeTxt.getText().toString()));
            cropSpraying.setCropId(cropId);
            cropSpraying.setStartTime(startTimeTxt.getText().toString());
            cropSpraying.setEndTime(endTimeTxt.getText().toString());
            cropSpraying.setCost(Float.parseFloat(costTxt.getText().toString()));
            cropSpraying.setOperator(operatorTxt.getText().toString());
            cropSpraying.setEquipmentUsed(equipmentUsedTxt.getText().toString());
            cropSpraying.setTreatmentReason(reasonTxt.getText().toString());
            cropSpraying.setSprayId(((CropSpinnerItem)sprayIdSp.getSelectedItem()).getId());
            cropSpraying.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cropSpraying.setReminders(remindersSp.getSelectedItem().toString());
            if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
                String weeks = weeksTxt.getText().toString();
                String repeatUntil = repeatUntilTxt.getText().toString();

                cropSpraying.setFrequency(Float.parseFloat(weeks));
                cropSpraying.setRepeatUntil(repeatUntil);
            }
            if(daysBeforeLayout.getVisibility()==View.VISIBLE){
                String days = daysBeforeTxt.getText().toString();


                cropSpraying.setDaysBefore(days);

            }

            if(waterConditionSp.getSelectedItemPosition()!=0){
                cropSpraying.setWaterCondition(waterConditionSp.getSelectedItem().toString());
            }
            if(windDirectionSp.getSelectedItemPosition()!=0){
                cropSpraying.setWindDirection(windDirectionSp.getSelectedItem().toString());
            }
            dbHandler.updateCropSpraying(cropSpraying);
        }
    }
    public void fillViews(){
        if(cropSpraying != null){
            CropDashboardActivity.selectSpinnerItemByValue(windDirectionSp, cropSpraying.getWindDirection());
            CropDashboardActivity.selectSpinnerItemByValue(waterConditionSp, cropSpraying.getWaterCondition());
            CropDashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropSpraying.getRecurrence());
            CropDashboardActivity.selectSpinnerItemByValue(remindersSp, cropSpraying.getReminders());

            rateTxt.setText(cropSpraying.getRate()+"");
            waterVolumeTxt.setText(cropSpraying.getWaterVolume()+"");
            dateTxt.setText(cropSpraying.getDate());
            startTimeTxt.setText(cropSpraying.getStartTime()+"");
            endTimeTxt.setText(cropSpraying.getEndTime()+"");
            equipmentUsedTxt.setText(cropSpraying.getEquipmentUsed());
            reasonTxt.setText(cropSpraying.getTreatmentReason());
            operatorTxt.setText(cropSpraying.getOperator());
            costTxt.setText(cropSpraying.getCost()+"");
            rateTxt.setText(cropSpraying.getRate()+"");
            weeksTxt.setText(cropSpraying.getFrequency()+"");
            repeatUntilTxt.setText(cropSpraying.getRepeatUntil());
            daysBeforeTxt.setText(cropSpraying.getDaysBefore());


            CropDashboardActivity.selectSpinnerItemById(sprayIdSp, cropSpraying.getId());
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
        else if(recurrenceSp.getSelectedItemPosition()==0){
            message = getString(R.string.recurrence_not_selected);
            recurrenceSp.requestFocus();
        }
        else if(remindersSp.getSelectedItemPosition()==0){
            message = getString(R.string.reminders_not_selected);
            remindersSp.requestFocus();
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
