package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.Crop;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropTask;

import java.util.ArrayList;

public class CropTaskManagerActivity extends AppCompatActivity {
CropTask cropTask=null;
    EditText dateTxt, titleTxt, descriptionTxt;
    Spinner cropSp, typeSp, personnelSp,statusSp, recurrenceSp, remindersSp;
    Button saveBtn;
    CropSpinnerAdapter cropsSpinnerAdapter, employeesSpinnerAdapter;
    MyFarmDbHandlerSingleton dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_task_manager);

        if(getIntent().hasExtra("cropTaskProduct")){
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

        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(dateTxt,this);

        fillViews();
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


    }



    public void saveFields(){
        cropTask = new CropTask();
        cropTask.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropTask.setCropId(cropSp.getSelectedItem().toString());
        cropTask.setTitle(titleTxt.getText().toString());
        cropTask.setType(typeSp.getSelectedItem().toString());
        cropTask.setEmployeeId(personnelSp.getSelectedItem().toString());
        cropTask.setStatus(statusSp.getSelectedItem().toString());
        cropTask.setDescription(descriptionTxt.getText().toString());
        cropTask.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cropTask.setReminders(remindersSp.getSelectedItem().toString());
        dbHandler.insertCropTask(cropTask);



    }

    public void updateField(){
        if(cropTask != null){
            cropTask.setUserId(CropDashboardActivity.getPreferences("userId",this));
            cropTask.setCropId(cropSp.getSelectedItem().toString());
            cropTask.setTitle(titleTxt.getText().toString());
            cropTask.setType(typeSp.getSelectedItem().toString());
            cropTask.setEmployeeId(personnelSp.getSelectedItem().toString());
            cropTask.setStatus(statusSp.getSelectedItem().toString());
            cropTask.setDescription(descriptionTxt.getText().toString());
            cropTask.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cropTask.setReminders(remindersSp.getSelectedItem().toString());
            dbHandler.updateCropTask(cropTask);
        }
    }

    public void fillViews(){
        if(cropTask != null){
            CropDashboardActivity.selectSpinnerItemByValue(cropSp, cropTask.getCropId());
            CropDashboardActivity.selectSpinnerItemByValue(typeSp, cropTask.getType());
            CropDashboardActivity.selectSpinnerItemByValue(personnelSp, cropTask.getEmployeeId());
            CropDashboardActivity.selectSpinnerItemByValue(statusSp, cropTask.getStatus());
            CropDashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropTask.getRecurrence());
            CropDashboardActivity.selectSpinnerItemByValue(remindersSp, cropTask.getReminders());


            dateTxt.setText(cropTask.getDate());
            titleTxt.setText(cropTask.getTitle());
            descriptionTxt.setText(cropTask.getDescription());


        }

    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
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
            Toast.makeText(CropTaskManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


}
