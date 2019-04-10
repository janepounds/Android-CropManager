package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.Crop;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropMachineTask;

import java.util.ArrayList;

public class CropMachineTaskManagerActivity extends AppCompatActivity {
    CropMachineTask cropMachineTask=null;
    EditText dateTxt, titleTxt, descriptionTxt;
    Spinner cropSp, typeSp, personnelSp,statusSp, recurrenceSp, remindersSp;
    Button saveBtn;
    LinearLayout weeklyRecurrenceLayout, daysBeforeLayout;
    CropSpinnerAdapter cropsSpinnerAdapter, employeesSpinnerAdapter;
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

        dateTxt = findViewById(R.id.txt_crop_task_date);
        titleTxt = findViewById(R.id.txt_crop_task_title);
        descriptionTxt = findViewById(R.id.txt_crop_task_description);
        cropSp = findViewById(R.id.sp_crop_task_crop);
        typeSp = findViewById(R.id.sp_crop_task_type);
        personnelSp = findViewById(R.id.sp_crop_task_personnel);
        statusSp = findViewById(R.id.sp_crop_task_status);
        recurrenceSp = findViewById(R.id.sp_crop_task_recurrence);
        remindersSp = findViewById(R.id.sp_crop_task_reminders);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_machine_task_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_machine_task_days_before);


        recurrenceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selection = parent.getItemAtPosition(position).toString();
                if(selection.toLowerCase().equals("weekly")){
                    weeklyRecurrenceLayout.setVisibility(View.VISIBLE);
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


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(dateTxt,this);
        ((ArrayAdapter)typeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)statusSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);



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
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });

        ArrayList<CropSpinnerItem> cropsItems = new ArrayList<>();
        for(Crop x: dbHandler.getCrops(CropDashboardActivity.getPreferences("userId",this))){
            cropsItems.add(x);
        }
        cropsSpinnerAdapter = new CropSpinnerAdapter(cropsItems,"Crops",this);
        cropSp.setAdapter(cropsSpinnerAdapter);

        ArrayList<CropSpinnerItem> employeesItems = new ArrayList<>();
        for(CropEmployee x: dbHandler.getCropEmployee(CropDashboardActivity.getPreferences("userId",this))){
            employeesItems.add(x);
        }
        employeesSpinnerAdapter = new CropSpinnerAdapter(employeesItems,"Employees",this);
        personnelSp.setAdapter(employeesSpinnerAdapter);


        fillViews();
    }



    public void saveFields(){
        cropMachineTask = new CropMachineTask();
        cropMachineTask.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropMachineTask.setMachineId(((CropSpinnerItem)cropSp.getSelectedItem()).getId());
        cropMachineTask.setTitle(titleTxt.getText().toString());
        cropMachineTask.setDate(dateTxt.getText().toString());
        cropMachineTask.setType(typeSp.getSelectedItem().toString());
        cropMachineTask.setEmployeeId(((CropSpinnerItem)personnelSp.getSelectedItem()).getId());
        cropMachineTask.setStatus(statusSp.getSelectedItem().toString());
        cropMachineTask.setDescription(descriptionTxt.getText().toString());
        cropMachineTask.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cropMachineTask.setReminders(remindersSp.getSelectedItem().toString());
        dbHandler.insertCropMachineTask(cropMachineTask);



    }

    public void updateField(){
        if(cropMachineTask != null){
            cropMachineTask.setUserId(CropDashboardActivity.getPreferences("userId",this));
            cropMachineTask.setMachineId(((CropSpinnerItem)cropSp.getSelectedItem()).getId());
            cropMachineTask.setTitle(titleTxt.getText().toString());
            cropMachineTask.setDate(dateTxt.getText().toString());
            cropMachineTask.setType(typeSp.getSelectedItem().toString());
            cropMachineTask.setEmployeeId(((CropSpinnerItem)personnelSp.getSelectedItem()).getId());
            cropMachineTask.setStatus(statusSp.getSelectedItem().toString());
            cropMachineTask.setDescription(descriptionTxt.getText().toString());
            cropMachineTask.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cropMachineTask.setReminders(remindersSp.getSelectedItem().toString());
            dbHandler.updateCropMachineTask(cropMachineTask);
        }
    }

    public void fillViews(){
        if(cropMachineTask != null){

            CropDashboardActivity.selectSpinnerItemByValue(typeSp, cropMachineTask.getType());
            CropDashboardActivity.selectSpinnerItemById(personnelSp, cropMachineTask.getEmployeeId());
            CropDashboardActivity.selectSpinnerItemByValue(statusSp, cropMachineTask.getStatus());
            CropDashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropMachineTask.getRecurrence());
            CropDashboardActivity.selectSpinnerItemByValue(remindersSp, cropMachineTask.getReminders());


            dateTxt.setText(cropMachineTask.getDate());
            titleTxt.setText(cropMachineTask.getTitle());
            descriptionTxt.setText(cropMachineTask.getDescription());


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
        else if(personnelSp.getSelectedItemPosition()==0){
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



        if(message != null){
            Toast.makeText(CropMachineTaskManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


}
