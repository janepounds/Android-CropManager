package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropCultivation;

public class CropCultivationManagerActivity extends AppCompatActivity {

    EditText dateTxt, notesTxt,costTxt, operatorTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    Button btn_save;
    CropCultivation cultivation;
    String cropId;
    Spinner operationTxt,recurrenceSp,remindersSp;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_cultivation_manager);
        if(getIntent().hasExtra("cropCultivation")){
            cultivation =(CropCultivation) getIntent().getSerializableExtra("cropCultivation");
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
        dateTxt =findViewById(R.id.txt_crop_cultivate_date);
        notesTxt =findViewById(R.id.txt_crop_cultivate_notes);

        operationTxt =findViewById(R.id.sp_crop_cultivate_operation);
        costTxt =findViewById(R.id.txt_crop_cultivate_fixed_cost);
        operatorTxt =findViewById(R.id.txt_crop_cultivate_operator);
        remindersSp = findViewById(R.id.sp_crop_cultivation_reminders);
        recurrenceSp = findViewById(R.id.sp_crop_cultivation_recurrence);
        weeksTxt = findViewById(R.id.txt_crop_cultivation_weekly_weeks);
        repeatUntilTxt = findViewById(R.id.txt_crop_cultivation_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_cultivation_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_cultivation_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_cultivation_days_before);


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
        CropDashboardActivity.addDatePicker(repeatUntilTxt,this);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cultivation==null){
                        saveCultivation();
                    }
                    else{
                        updateCultivation();
                    }
                    Intent toCropsList = new Intent(CropCultivationManagerActivity.this, CropCultivationsListActivity.class);
                    toCropsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    toCropsList.putExtra("cropId",cropId);
                    startActivity(toCropsList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        fillViews();
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

    }

    public void saveCultivation(){
        cultivation = new CropCultivation();
        cultivation.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cultivation.setDate(dateTxt.getText().toString());
        cultivation.setOperator(operatorTxt.getText().toString());
        cultivation.setOperation(operationTxt.getSelectedItem().toString());
        cultivation.setCropId(cropId);
        cultivation.setNotes(notesTxt.getText().toString());
        cultivation.setCost(Float.parseFloat(costTxt.getText().toString()));
        cultivation.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cultivation.setReminders(remindersSp.getSelectedItem().toString());
        if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
            String weeks = weeksTxt.getText().toString();
            String repeatUntil = repeatUntilTxt.getText().toString();

            cultivation.setFrequency(Float.parseFloat(weeks));
            cultivation.setRepeatUntil(repeatUntil);
        }
        if(daysBeforeLayout.getVisibility()==View.VISIBLE){
            String days = daysBeforeTxt.getText().toString();


            cultivation.setDaysBefore(days);

        }

        dbHandler.insertCropCultivate(cultivation);

    }
    public void updateCultivation(){
        if(cultivation != null){

            cultivation.setDate(dateTxt.getText().toString());
            cultivation.setOperator(operatorTxt.getText().toString());
            cultivation.setOperation(operationTxt.getSelectedItem().toString());
            cultivation.setCropId(cropId);
            cultivation.setNotes(notesTxt.getText().toString());
            cultivation.setCost(Float.parseFloat(costTxt.getText().toString()));
            cultivation.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cultivation.setReminders(remindersSp.getSelectedItem().toString());
            if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
                String weeks = weeksTxt.getText().toString();
                String repeatUntil = repeatUntilTxt.getText().toString();

                cultivation.setFrequency(Float.parseFloat(weeks));
                cultivation.setRepeatUntil(repeatUntil);
            }
            if(daysBeforeLayout.getVisibility()==View.VISIBLE){
                String days = daysBeforeTxt.getText().toString();


                cultivation.setDaysBefore(days);

            }

            dbHandler.updateCropCultivate(cultivation);
        }
    }

    public void fillViews(){
        if(cultivation != null){

            dateTxt.setText(cultivation.getDate());
            operatorTxt.setText(cultivation.getOperator());
            CropDashboardActivity.selectSpinnerItemByValue(operationTxt, cultivation.getOperation());
            notesTxt.setText(cultivation.getNotes());
            costTxt.setText(cultivation.getCost()+"");
            CropDashboardActivity.selectSpinnerItemByValue(recurrenceSp, cultivation.getRecurrence());
            CropDashboardActivity.selectSpinnerItemByValue(remindersSp, cultivation.getReminders());
            weeksTxt.setText(cultivation.getFrequency()+"");
            repeatUntilTxt.setText(cultivation.getRepeatUntil());
            daysBeforeTxt.setText(cultivation.getDaysBefore());

        }
    }

    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }
        else if(operationTxt.getSelectedItemPosition()==0){
            message = getString(R.string.operation_not_selected);
            operationTxt.requestFocus();
        }
        else if(operatorTxt.getText().toString().isEmpty()){
            message = getString(R.string.operator_not_entered);
            operatorTxt.requestFocus();
        }
        else if(recurrenceSp.getSelectedItemPosition()==0){
            message = getString(R.string.recurrence_not_selected);
            recurrenceSp.requestFocus();
        }
        else if(remindersSp.getSelectedItemPosition()==0){
            message = getString(R.string.reminders_not_selected);
            remindersSp.requestFocus();
        }

        if(costTxt.getText().toString().isEmpty()){
            costTxt.setText("0");
        }



        if(message != null){
            Toast.makeText(CropCultivationManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}
