package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

import com.myfarmnow.myfarmcrop.models.CropContact;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropMachineTask;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;

public class CropMachineTaskManagerActivity extends AppCompatActivity {
    CropMachineTask cropMachineTask=null;
    EditText startDateTxt,endDateTxt, titleTxt, descriptionTxt,costTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    TextView currencyTxt;
    Spinner  statusSp, recurrenceSp, remindersSp;
    AutoCompleteTextView personnelSp;
    Button saveBtn;
    LinearLayout weeklyRecurrenceLayout, daysBeforeLayout,remindersLayout;
    ArrayAdapter<String>   employeesSpinnerAdapter;
    MyFarmDbHandlerSingleton dbHandler;
    String machineId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_machine_task_manager);

        if(getIntent().hasExtra("cropMachineTask")){
            cropMachineTask = (CropMachineTask)getIntent().getSerializableExtra("cropMachineTask");
        }
        if(getIntent().hasExtra("machineId")){
            machineId =getIntent().getStringExtra("machineId");
        }
        else{
            finish();
        }
        initializeForm();

    }

    public void initializeForm(){

        startDateTxt = findViewById(R.id.txt_crop_task_start_date);
        endDateTxt = findViewById(R.id.txt_crop_task_end_date);
        titleTxt = findViewById(R.id.txt_crop_task_title);
        descriptionTxt = findViewById(R.id.txt_crop_task_description);
        costTxt = findViewById(R.id.txt_crop_task_cost);
        weeksTxt = findViewById(R.id.txt_crop_task_weekly_weeks);
        repeatUntilTxt = findViewById(R.id.txt_crop_task_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_task_days_before);
        currencyTxt = findViewById(R.id.txt_crop_task_currency);
        personnelSp = findViewById(R.id.sp_crop_task_personnel);
        statusSp = findViewById(R.id.sp_crop_task_status);

        recurrenceSp = findViewById(R.id.sp_crop_task_recurrence);
        remindersSp = findViewById(R.id.sp_crop_task_reminders);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_machine_task_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_machine_task_days_before);
        remindersLayout = findViewById(R.id.layout_crop_machine_task_reminders);


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


        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        DashboardActivity.addDatePicker(startDateTxt,this);
        DashboardActivity.addDatePicker(endDateTxt,this);
        DashboardActivity.addDatePicker(repeatUntilTxt,this);
        ((ArrayAdapter)statusSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
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

        statusSp.setOnItemSelectedListener(onItemSelectedListener);



        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());




        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropMachineTask ==null){
                        saveFields();
                    }
                    else{
                        updateField();
                    }

                    Intent toCropMachineTasksList = new Intent(CropMachineTaskManagerActivity.this, CropMachineTasksListActivity.class);
                    toCropMachineTasksList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    toCropMachineTasksList.putExtra("machineId",machineId);
                    startActivity(toCropMachineTasksList);
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });


        ArrayList<String> employeesItems = new ArrayList<>();
        for(CropEmployee x: dbHandler.getCropEmployees(DashboardActivity.RETRIEVED_USER_ID)){
            employeesItems.add(x.getFullName());
        }
        for(CropContact x: dbHandler.getCropContacts(DashboardActivity.RETRIEVED_USER_ID)){
            employeesItems.add(x.getName());
        }
        employeesSpinnerAdapter  = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, employeesItems);;
        personnelSp.setAdapter(employeesSpinnerAdapter);


        fillViews();
    }



    public void saveFields(){
        cropMachineTask = new CropMachineTask();
        cropMachineTask.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        cropMachineTask.setTitle(titleTxt.getText().toString());
        cropMachineTask.setMachineId(machineId);
        cropMachineTask.setStartDate(startDateTxt.getText().toString());
        cropMachineTask.setEndDate(startDateTxt.getText().toString());
        cropMachineTask.setCost(Float.parseFloat(costTxt.getText().toString()));
        cropMachineTask.setEmployeeName(personnelSp.getText().toString());
        cropMachineTask.setStatus(statusSp.getSelectedItem().toString());
        cropMachineTask.setDescription(descriptionTxt.getText().toString());
        cropMachineTask.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cropMachineTask.setReminders(remindersSp.getSelectedItem().toString());
        cropMachineTask.setRepeatUntil(repeatUntilTxt.getText().toString());
        cropMachineTask.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
        cropMachineTask.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));


        dbHandler.insertCropMachineTask(cropMachineTask);



    }

    public void updateField(){
        if(cropMachineTask != null){
            cropMachineTask.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            cropMachineTask.setTitle(titleTxt.getText().toString());
            cropMachineTask.setMachineId(machineId);
            cropMachineTask.setStartDate(startDateTxt.getText().toString());
            cropMachineTask.setEndDate(startDateTxt.getText().toString());
            cropMachineTask.setCost(Float.parseFloat(costTxt.getText().toString()));
            cropMachineTask.setEmployeeName(personnelSp.getText().toString());
            cropMachineTask.setStatus(statusSp.getSelectedItem().toString());
            cropMachineTask.setDescription(descriptionTxt.getText().toString());
            cropMachineTask.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cropMachineTask.setReminders(remindersSp.getSelectedItem().toString());
            cropMachineTask.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));
            cropMachineTask.setRepeatUntil(repeatUntilTxt.getText().toString());
            cropMachineTask.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));

            dbHandler.updateCropMachineTask(cropMachineTask);
        }
    }

    public void fillViews(){
        if(cropMachineTask != null){
            DashboardActivity.selectSpinnerItemByValue(statusSp, cropMachineTask.getStatus());
            DashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropMachineTask.getRecurrence());
            DashboardActivity.selectSpinnerItemByValue(remindersSp, cropMachineTask.getReminders());
            personnelSp.setText(cropMachineTask.getEmployeeName());
            costTxt.setText(cropMachineTask.getCost()+"");
            startDateTxt.setText(cropMachineTask.getStartDate());
            endDateTxt.setText(cropMachineTask.getEndDate());
            titleTxt.setText(cropMachineTask.getTitle());
            descriptionTxt.setText(cropMachineTask.getDescription());
            weeksTxt.setText(cropMachineTask.getFrequency()+"");
            repeatUntilTxt.setText(cropMachineTask.getRepeatUntil());
            daysBeforeTxt.setText(cropMachineTask.getDaysBefore()+"");
        }

    }

    public boolean validateEntries(){
        String message = null;
        if(startDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.start_date_not_entered_message);
            startDateTxt.requestFocus();
        }
        else if(endDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.end_date_not_entered_message);
            endDateTxt.requestFocus();
        }
        else if(titleTxt.getText().toString().isEmpty()){
            message = getString(R.string.title_not_entered_message);
            titleTxt.requestFocus();
        }
        else if(descriptionTxt.getText().toString().isEmpty()){
            message = getString(R.string.description_not_entered_message);
            descriptionTxt.requestFocus();
        }

        else if(personnelSp.getText().toString().isEmpty()){
            message = getString(R.string.personnel_not_selected);
            personnelSp.requestFocus();
        }
        else if(statusSp.getSelectedItemPosition()==0){
            message = getString(R.string.status_not_selected);
            statusSp.requestFocus();
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

        if(message != null){
            Toast.makeText(CropMachineTaskManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


}
