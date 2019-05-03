package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropMachineService;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;

public class CropMachineServiceManagerActivity extends AppCompatActivity {
    EditText serviceDateTxt,descriptionTxt,currentHoursTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt,costTxt;
    TextView currencyTxt;
    Spinner typeSp,personnelSp,recurrenceSp,remindersSp;
    Button saveBtn;
    CropSpinnerAdapter  employeesSpinnerAdapter;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout;
    MyFarmDbHandlerSingleton dbHandler;
    CropMachineService cropMachineService=null;
    String machineId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_machine_service_manager);
        if(getIntent().hasExtra("cropMachineService")){
            cropMachineService = (CropMachineService)getIntent().getSerializableExtra("cropMachineService");
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

        serviceDateTxt = findViewById(R.id.txt_crop_service_date);
        descriptionTxt = findViewById(R.id.txt_crop_service_description);
        currentHoursTxt = findViewById(R.id.txt_crop_service_current_hours);
        typeSp = findViewById(R.id.sp_crop_service_type);
        personnelSp = findViewById(R.id.sp_crop_service_personnel);
        costTxt = findViewById(R.id.txt_crop_service_cost);
        currencyTxt = findViewById(R.id.txt_crop_service_currency);

        recurrenceSp = findViewById(R.id.sp_crop_service_recurrence);
        remindersSp = findViewById(R.id.sp_crop_service_reminders);
        weeksTxt = findViewById(R.id.txt_crop_service_weekly_weeks);
        repeatUntilTxt = findViewById(R.id.txt_crop_service_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_service_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_machine_service_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_machine_service_days_before);


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
                }
                else{
                    daysBeforeLayout.setVisibility(View.GONE);

                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(serviceDateTxt,this);
        CropDashboardActivity.addDatePicker(repeatUntilTxt,this);
        ((ArrayAdapter)typeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
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

        typeSp.setOnItemSelectedListener(onItemSelectedListener);



        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());



        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropMachineService ==null){
                        saveFields();
                    }
                    else{
                        updateField();
                    }

                    Intent toCropMachineServicesList = new Intent(CropMachineServiceManagerActivity.this, CropMachineServicesListActivity.class);
                    toCropMachineServicesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    toCropMachineServicesList.putExtra("machineId",machineId);
                    startActivity(toCropMachineServicesList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });



        ArrayList<CropSpinnerItem> employeesItems = new ArrayList<>();
        for(CropEmployee x: dbHandler.getCropEmployee(CropDashboardActivity.getPreferences("userId",this))){
            employeesItems.add(x);
        }
        employeesSpinnerAdapter = new CropSpinnerAdapter(employeesItems,"Employees",this);
        personnelSp.setAdapter(employeesSpinnerAdapter);


        fillViews();
    }

    public void saveFields(){
        cropMachineService = new CropMachineService();
        cropMachineService.setMachineId(machineId);
        cropMachineService.setCurrentHours(Float.parseFloat(currentHoursTxt.getText().toString()));
        cropMachineService.setDate(serviceDateTxt.getText().toString());
        cropMachineService.setType(typeSp.getSelectedItem().toString());
        cropMachineService.setEmployeeName(((CropSpinnerItem)personnelSp.getSelectedItem()).getId());
        cropMachineService.setDescription(descriptionTxt.getText().toString());
        cropMachineService.setCost(Float.parseFloat(costTxt.getText().toString()));
        cropMachineService.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cropMachineService.setReminders(remindersSp.getSelectedItem().toString());
        if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
            String weeks = weeksTxt.getText().toString();
            String repeatUntil = repeatUntilTxt.getText().toString();

            cropMachineService.setFrequency(Float.parseFloat(weeks));
            cropMachineService.setRepeatUntil(repeatUntil);
        }
        if(daysBeforeLayout.getVisibility()==View.VISIBLE){
            String days = daysBeforeTxt.getText().toString();


            cropMachineService.setDaysBefore(days);

        }
        dbHandler.insertCropMachineService(cropMachineService);



    }

    public void updateField(){
        if(cropMachineService != null){
            cropMachineService.setMachineId(machineId);
            cropMachineService.setCurrentHours(Float.parseFloat(currentHoursTxt.getText().toString()));
            cropMachineService.setDate(serviceDateTxt.getText().toString());
            cropMachineService.setType(typeSp.getSelectedItem().toString());
            cropMachineService.setEmployeeName(((CropSpinnerItem)personnelSp.getSelectedItem()).getId());
            cropMachineService.setCost(Float.parseFloat(costTxt.getText().toString()));
            cropMachineService.setDescription(descriptionTxt.getText().toString());
            cropMachineService.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cropMachineService.setReminders(remindersSp.getSelectedItem().toString());
            if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
                String weeks = weeksTxt.getText().toString();
                String repeatUntil = repeatUntilTxt.getText().toString();

                cropMachineService.setFrequency(Float.parseFloat(weeks));
                cropMachineService.setRepeatUntil(repeatUntil);
            }
            if(daysBeforeLayout.getVisibility()==View.VISIBLE){
                String days = daysBeforeTxt.getText().toString();


                cropMachineService.setDaysBefore(days);

            }
            dbHandler.updateCropMachineService(cropMachineService);
        }
    }

    public void fillViews(){
        if(cropMachineService != null){

            CropDashboardActivity.selectSpinnerItemByValue(typeSp, cropMachineService.getType());
            CropDashboardActivity.selectSpinnerItemById(personnelSp, cropMachineService.getEmployeeName());
            CropDashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropMachineService.getRecurrence());
            CropDashboardActivity.selectSpinnerItemByValue(remindersSp, cropMachineService.getReminders());

            costTxt.setText(cropMachineService.getCost()+"");
            serviceDateTxt.setText(cropMachineService.getDate());
            currentHoursTxt.setText(cropMachineService.getCurrentHours()+"");
            descriptionTxt.setText(cropMachineService.getDescription());
            weeksTxt.setText(cropMachineService.getFrequency()+"");
            repeatUntilTxt.setText(cropMachineService.getRepeatUntil());
            daysBeforeTxt.setText(cropMachineService.getDaysBefore());

        }

    }

    public boolean validateEntries(){
        String message = null;
        if(serviceDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            serviceDateTxt.requestFocus();
        }

        else if(descriptionTxt.getText().toString().isEmpty()){
            message = getString(R.string.description_not_entered_message);
            descriptionTxt.requestFocus();
        }

        else if(typeSp.getSelectedItemPosition()==0){
            message = getString(R.string.type_not_selected);
            typeSp.requestFocus();
        }
        else if(personnelSp.getSelectedItemPosition()==0){
            message = getString(R.string.personnel_not_selected);
            personnelSp.requestFocus();
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
            Toast.makeText(CropMachineServiceManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


}
