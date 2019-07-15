package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropCultivation;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropCultivationManagerActivity extends AppCompatActivity {

    EditText dateTxt, notesTxt,costTxt, operatorTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    TextView currencyTxt;
    Button btn_save;
    CropCultivation cultivation;
    String cropId;
    Spinner operationTxt,recurrenceSp,remindersSp;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout,remindersLayout;
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
        currencyTxt =findViewById(R.id.txt_crop_cultivate_currency);

        repeatUntilTxt = findViewById(R.id.txt_crop_cultivation_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_cultivation_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_cultivation_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_cultivation_days_before);
        remindersLayout = findViewById(R.id.layout_crop_reminders);

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
                    weeklyRecurrenceLayout.setVisibility(View.VISIBLE);
                    remindersLayout.setVisibility(View.VISIBLE);
                }
                else{
                    remindersLayout.setVisibility(View.GONE);
                    weeklyRecurrenceLayout.setVisibility(View.GONE);

                }
                if(selection.toLowerCase().equals("daily")){
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }
                else{
                    remindersSp.setSelection(0);
                }

                if(selection.toLowerCase().equals("once")){
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }
                else{
                    remindersSp.setSelection(0);
                }
                if(selection.toLowerCase().equals("monthly")){
                    remindersLayout.setVisibility(View.VISIBLE);
                }
                else{
                    remindersLayout.setVisibility(View.GONE);
                }
                if(selection.toLowerCase().equals("annually")){
                    remindersLayout.setVisibility(View.VISIBLE);
                }
                else{
                    remindersLayout.setVisibility(View.GONE);
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
                } else if (selection.toLowerCase().equals("no")) {

                    daysBeforeLayout.setVisibility(View.GONE);

                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());
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
                    Intent toCropsList = new Intent(CropCultivationManagerActivity.this, CropActivitiesListActivity.class);
                    toCropsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    toCropsList.putExtra("cropId",cropId);
                    startActivity(toCropsList);
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        fillViews();
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)operationTxt.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


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
        operationTxt.setOnItemSelectedListener(onItemSelectedListener);


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
        else if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE && repeatUntilTxt.getText().toString().isEmpty()){
            message = getString(R.string.repeat_until_not_selected);
            repeatUntilTxt.requestFocus();
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
