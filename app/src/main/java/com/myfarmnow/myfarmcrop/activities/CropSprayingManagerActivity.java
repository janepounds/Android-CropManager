package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
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
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;

public class CropSprayingManagerActivity extends AppCompatActivity {


    EditText dateTxt, startTimeTxt,endTimeTxt, operatorTxt, waterVolumeTxt,costTxt, rateTxt,
            reasonTxt, equipmentUsedTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    Button btn_save;
    CropSpraying cropSpraying;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout,remindersLayout;
    String cropId;
    MyFarmDbHandlerSingleton dbHandler;
    Spinner windDirectionSp,waterConditionSp,sprayIdSp,recurrenceSp,remindersSp;

    TextView rateUnitsTextView,currencyTxt;

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
        currencyTxt =findViewById(R.id.txt_crop_spraying_currency);
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
        remindersLayout = findViewById(R.id.layout_crop_spraying_reminders);

        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());


        recurrenceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                String selection = parent.getItemAtPosition(position).toString();
                if(selection.toLowerCase().equals("weekly")){

                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }

                if(selection.toLowerCase().equals("daily")){
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }

                if(selection.toLowerCase().equals("once")){
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }


                if(selection.toLowerCase().equals("monthly")){
                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }


                if(selection.toLowerCase().equals("annually")){
                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        remindersSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                String selection = parent.getItemAtPosition(position).toString();
                if(selection.toLowerCase().equals("yes")){
                    daysBeforeLayout.setVisibility(View.VISIBLE);
                    if(recurrenceSp.getSelectedItem().equals("Weekly")){
                        weeklyRecurrenceLayout.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    daysBeforeLayout.setVisibility(View.GONE);
                    weeklyRecurrenceLayout.setVisibility(View.GONE);
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btn_save = findViewById(R.id.btn_save);
        DashboardActivity.addDatePicker(dateTxt,this);
        DashboardActivity.addTimePicker(startTimeTxt,this);
        DashboardActivity.addTimePicker(endTimeTxt,this);
        DashboardActivity.addDatePicker(repeatUntilTxt,this);

        ((ArrayAdapter)windDirectionSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)waterConditionSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

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
        windDirectionSp.setOnItemSelectedListener(onItemSelectedListener);
        waterConditionSp.setOnItemSelectedListener(onItemSelectedListener);


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
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayList<CropSpinnerItem> spraysList = new ArrayList<>();
        for(CropInventorySpray x: dbHandler.getCropSpray(DashboardActivity.getPreferences("userId",this))){
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
        cropSpraying.setUserId(DashboardActivity.getPreferences("userId",this));
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
        cropSpraying.setRepeatUntil(repeatUntilTxt.getText().toString());
        cropSpraying.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
        cropSpraying.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));



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
            cropSpraying.setRepeatUntil(repeatUntilTxt.getText().toString());
            cropSpraying.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
            cropSpraying.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));


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
            DashboardActivity.selectSpinnerItemByValue(windDirectionSp, cropSpraying.getWindDirection());
            DashboardActivity.selectSpinnerItemByValue(waterConditionSp, cropSpraying.getWaterCondition());
            DashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropSpraying.getRecurrence());
            DashboardActivity.selectSpinnerItemByValue(remindersSp, cropSpraying.getReminders());

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
            daysBeforeTxt.setText(cropSpraying.getDaysBefore()+"");


            DashboardActivity.selectSpinnerItemById(sprayIdSp, cropSpraying.getId());
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
        else if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE && repeatUntilTxt.getText().toString().isEmpty()){
            message = getString(R.string.repeat_until_not_selected);
            repeatUntilTxt.requestFocus();
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
