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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.models.CropContact;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropTask;

import java.util.ArrayList;

public class CropTaskManagerActivity extends AppCompatActivity {
    CropTask cropTask=null;
    EditText dateTxt, titleTxt, descriptionTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    Spinner cropSp, typeSp,statusSp, recurrenceSp, remindersSp;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout, remindersLayout;
    Button saveBtn;
    AutoCompleteTextView personnelSp;
    CropSpinnerAdapter cropsSpinnerAdapter;
    ArrayAdapter<String> employeesSpinnerAdapter;
    MyFarmDbHandlerSingleton dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_task_manager);

        if(getIntent().hasExtra("cropTask")){
            cropTask = (CropTask)getIntent().getSerializableExtra("cropTask");
        }
        initializeForm();

    }

    public void initializeForm(){

        dateTxt = findViewById(R.id.txt_crop_task_date);
        titleTxt = findViewById(R.id.txt_crop_task_title);
        descriptionTxt = findViewById(R.id.txt_crop_task_description);
        cropSp = findViewById(R.id.sp_crop_task_crop);
        typeSp = findViewById(R.id.sp_crop_task_type);
        personnelSp = findViewById(R.id.sp_crop_task_personnel);
        statusSp = findViewById(R.id.sp_crop_task_status);
        recurrenceSp = findViewById(R.id.sp_crop_task_recurrence);
        remindersSp = findViewById(R.id.sp_crop_task_reminders);
        weeksTxt = findViewById(R.id.txt_crop_task_weekly_weeks);
        repeatUntilTxt = findViewById(R.id.txt_crop_task_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_task_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_task_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_task_days_before);
        remindersLayout = findViewById(R.id.layout_crop_task_reminders);
        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        DashboardActivity.addDatePicker(dateTxt,this);
        DashboardActivity.addDatePicker(repeatUntilTxt,this);
        ((ArrayAdapter)typeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)statusSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
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
        typeSp.setOnItemSelectedListener(onItemSelectedListener);

        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropTask ==null){
                        saveFields();
                    }
                    else{
                        updateField();
                    }

                    Intent toCropTasksList = new Intent(CropTaskManagerActivity.this, CropTasksListActivity.class);
                    toCropTasksList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropTasksList);
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });

        ArrayList<CropSpinnerItem> cropsItems = new ArrayList<>();
        for(Crop x: dbHandler.getCrops(DashboardActivity.getPreferences("userId",this))){
            cropsItems.add(x);
        }
        cropsSpinnerAdapter = new CropSpinnerAdapter(cropsItems,"Crops",this);
        cropSp.setAdapter(cropsSpinnerAdapter);

        ArrayList<String> employeesItems = new ArrayList<>();
        for(CropEmployee x: dbHandler.getCropEmployees(DashboardActivity.getPreferences("userId",this))){
            employeesItems.add(x.getFullName());
        }
        for(CropContact x: dbHandler.getCropContacts(DashboardActivity.getPreferences("userId",this))){
            employeesItems.add(x.getName());
        }
        employeesSpinnerAdapter  = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, employeesItems);;
        personnelSp.setAdapter(employeesSpinnerAdapter);


        fillViews();
    }



    public void saveFields(){
        cropTask = new CropTask();
        cropTask.setUserId(DashboardActivity.getPreferences("userId",this));
        cropTask.setCropId(((CropSpinnerItem)cropSp.getSelectedItem()).getId());
        cropTask.setTitle(titleTxt.getText().toString());
        cropTask.setDate(dateTxt.getText().toString());
        cropTask.setType(typeSp.getSelectedItem().toString());
        cropTask.setEmployeeId((personnelSp.getText().toString()));
        cropTask.setStatus(statusSp.getSelectedItem().toString());
        cropTask.setDescription(descriptionTxt.getText().toString());
        cropTask.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cropTask.setReminders(remindersSp.getSelectedItem().toString());
        cropTask.setRepeatUntil(repeatUntilTxt.getText().toString());
        cropTask.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
        cropTask.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));

        dbHandler.insertCropTask(cropTask);



    }

    public void updateField(){
        if(cropTask != null){
            cropTask.setUserId(DashboardActivity.getPreferences("userId",this));
            cropTask.setCropId(((CropSpinnerItem)cropSp.getSelectedItem()).getId());
            cropTask.setTitle(titleTxt.getText().toString());
            cropTask.setDate(dateTxt.getText().toString());
            cropTask.setType(typeSp.getSelectedItem().toString());
            cropTask.setEmployeeId((personnelSp.getText().toString()));
            cropTask.setStatus(statusSp.getSelectedItem().toString());
            cropTask.setDescription(descriptionTxt.getText().toString());
            cropTask.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cropTask.setReminders(remindersSp.getSelectedItem().toString());
            cropTask.setRepeatUntil(repeatUntilTxt.getText().toString());
            cropTask.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
            cropTask.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));

            dbHandler.updateCropTask(cropTask);
        }
    }

    public void fillViews(){
        if(cropTask != null){
            DashboardActivity.selectSpinnerItemById(cropSp, cropTask.getCropId());
            DashboardActivity.selectSpinnerItemByValue(typeSp, cropTask.getType());
            DashboardActivity.selectSpinnerItemByValue(statusSp, cropTask.getStatus());
            DashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropTask.getRecurrence());
            DashboardActivity.selectSpinnerItemByValue(remindersSp, cropTask.getReminders());


            dateTxt.setText(cropTask.getDate());
            personnelSp.setText(cropTask.getEmployeeName());
            titleTxt.setText(cropTask.getTitle());
            descriptionTxt.setText(cropTask.getDescription());
            weeksTxt.setText(cropTask.getFrequency()+"");
            repeatUntilTxt.setText(cropTask.getRepeatUntil());
            daysBeforeTxt.setText(cropTask.getDaysBefore()+"");


        }

    }

    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }
        else if(titleTxt.getText().toString().isEmpty()){
            message = getString(R.string.title_not_entered_message);
            titleTxt.requestFocus();
        }
        else if(descriptionTxt.getText().toString().isEmpty()){
            message = getString(R.string.description_not_entered_message);
            descriptionTxt.requestFocus();
        }

        else if(cropSp.getSelectedItemPosition()==0){
            message = getString(R.string.crop_not_selected);
            cropSp.requestFocus();
        }
        else if(typeSp.getSelectedItemPosition()==0){
            message = getString(R.string.type_not_selected);
            typeSp.requestFocus();
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
            Toast.makeText(CropTaskManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


}
