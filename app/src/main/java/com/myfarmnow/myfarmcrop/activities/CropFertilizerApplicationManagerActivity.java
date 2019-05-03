package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
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
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;

public class CropFertilizerApplicationManagerActivity extends AppCompatActivity {

    EditText dateTxt,  operatorTxt,costTxt, rateTxt,reasonTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    TextView currency;
    Button btn_save;
    CropFertilizerApplication fertilizerApplication;
    String cropId;
    MyFarmDbHandlerSingleton dbHandler;
    Spinner methodSp, fertilizerFormSp, fertilizerId,recurrenceSp,remindersSp;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout;
    boolean applicationMethodSet = false;//

    CropSpinnerAdapter applicationMethodAdapter,fertilizerAdapter;
    ArrayList<CropSpinnerItem> solidMethodsArrayList=new ArrayList<CropSpinnerItem>();
    ArrayList<CropSpinnerItem> liquidMethodsArrayList=new ArrayList<CropSpinnerItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_fertilizer_application_manager);
        if(getIntent().hasExtra("fertilizerApplication")){
            fertilizerApplication =(CropFertilizerApplication) getIntent().getSerializableExtra("fertilizerApplication");
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
        dateTxt =findViewById(R.id.txt_crop_fertilizer_application_date);
        operatorTxt =findViewById(R.id.txt_crop_fertilizer_application_operator);
        costTxt =findViewById(R.id.txt_crop_fertilizer_application_labour_cost);
        rateTxt =findViewById(R.id.txt_crop_fertilizer_application_rate);
        reasonTxt =findViewById(R.id.txt_crop_fertilizer_application_reason);
        fertilizerId =findViewById(R.id.sp_crop_fertilizer_application_fertilizername);
        methodSp =findViewById(R.id.sp_crop_fertilizer_application_method);
        fertilizerFormSp =findViewById(R.id.sp_crop_fertilizer_application_fertilizer_form);
        remindersSp = findViewById(R.id.sp_crop_fertilizer_application_reminders);
        recurrenceSp = findViewById(R.id.sp_crop_fertilizer_application_recurrence);
        weeksTxt = findViewById(R.id.txt_crop_fertilizer_application_weekly_weeks);
        currency = findViewById(R.id.txt_crop_fertilizer_application_currency);

        repeatUntilTxt = findViewById(R.id.txt_crop_fertilizer_application_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_fertilizer_application_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_fertilizer_application_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_fertilizer_application_days_before);

        applicationMethodAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Method",this);
        methodSp.setAdapter(applicationMethodAdapter);
        methodSp.setEnabled(false);
        btn_save = findViewById(R.id.btn_save);
        CropDashboardActivity.addDatePicker(dateTxt,this);
        CropDashboardActivity.addDatePicker(repeatUntilTxt,this);

        String liquidApplicationMethods [] = getResources().getStringArray(R.array.crop_fertilizer_application_method_liquid);
        String solidApplicationMethods [] = getResources().getStringArray(R.array.crop_fertilizer_application_method_solid);

        for(String x: liquidApplicationMethods){
            liquidMethodsArrayList.add(new FertilizerFormSpinnerItem(x));
        }
        for(String x: solidApplicationMethods){
            solidMethodsArrayList.add(new FertilizerFormSpinnerItem(x));
        }

        fertilizerFormSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
               if(selection.toLowerCase().equals("solid")){
                   methodSp.setEnabled(true);
                   applicationMethodAdapter.changeItems(solidMethodsArrayList);
               }
               else if(selection.toLowerCase().equals("liquid")){
                   methodSp.setEnabled(true);
                   applicationMethodAdapter.changeItems(liquidMethodsArrayList);
               }
               else{
                   methodSp.setEnabled(false);
               }

               if(!applicationMethodSet && fertilizerApplication != null){
                   CropDashboardActivity.selectSpinnerItemById(methodSp, fertilizerApplication.getMethod());
                   applicationMethodSet =true;
               }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(fertilizerApplication==null){
                        saveFertilizerApplication();
                    }
                    else{
                        updateFertilizerApplication();
                    }
                    Intent toCropsList = new Intent(CropFertilizerApplicationManagerActivity.this, CropActivitiesListActivity.class);
                    toCropsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    toCropsList.putExtra("cropId",cropId);
                    startActivity(toCropsList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });


        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)fertilizerFormSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayList<CropSpinnerItem> fertlizersList = new ArrayList<>();
        for(CropInventoryFertilizer x: dbHandler.getCropFertilizerInventorys(CropDashboardActivity.getPreferences("userId",this))){
            fertlizersList.add(x);
        }
        fertilizerAdapter  =new CropSpinnerAdapter(fertlizersList,"Fertilizer",this);
        fertilizerId.setAdapter(fertilizerAdapter);

        currency.setText(CropSettingsSingleton.getInstance().getCurrency());


        fillViews();
    }

    public void saveFertilizerApplication(){
        fertilizerApplication = new CropFertilizerApplication();
        fertilizerApplication.setUserId(CropDashboardActivity.getPreferences("userId",this));
        fertilizerApplication.setDate(dateTxt.getText().toString());
        fertilizerApplication.setRate(Float.parseFloat(rateTxt.getText().toString()));
        fertilizerApplication.setCropId(cropId);
        fertilizerApplication.setCost(Float.parseFloat(costTxt.getText().toString()));
        fertilizerApplication.setOperator(operatorTxt.getText().toString());
        fertilizerApplication.setReason(reasonTxt.getText().toString());
        fertilizerApplication.setFertilizerForm(fertilizerFormSp.getSelectedItem().toString());
        fertilizerApplication.setMethod((methodSp.getSelectedItem()).toString());
        fertilizerApplication.setFertilizerId(((CropSpinnerItem) fertilizerId.getSelectedItem()).getId());
        fertilizerApplication.setRecurrence(recurrenceSp.getSelectedItem().toString());
        fertilizerApplication.setReminders(remindersSp.getSelectedItem().toString());
        if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
            String weeks = weeksTxt.getText().toString();
            String repeatUntil = repeatUntilTxt.getText().toString();

            fertilizerApplication.setFrequency(Float.parseFloat(weeks));
            fertilizerApplication.setRepeatUntil(repeatUntil);
        }
        if(daysBeforeLayout.getVisibility()==View.VISIBLE){
            String days = daysBeforeTxt.getText().toString();


            fertilizerApplication.setDaysBefore(days);

        }


        dbHandler.insertCropFertilizerApplication(fertilizerApplication);

    }
    public void updateFertilizerApplication(){
        if(fertilizerApplication != null){
            fertilizerApplication.setDate(dateTxt.getText().toString());
            fertilizerApplication.setRate(Float.parseFloat(rateTxt.getText().toString()));
            fertilizerApplication.setCropId(cropId);
            fertilizerApplication.setCost(Float.parseFloat(costTxt.getText().toString()));
            fertilizerApplication.setOperator(operatorTxt.getText().toString());
            fertilizerApplication.setReason(reasonTxt.getText().toString());
            fertilizerApplication.setFertilizerForm(fertilizerFormSp.getSelectedItem().toString());
            fertilizerApplication.setMethod((methodSp.getSelectedItem()).toString());
            fertilizerApplication.setFertilizerId(((CropSpinnerItem) fertilizerId.getSelectedItem()).getId());
            fertilizerApplication.setRecurrence(recurrenceSp.getSelectedItem().toString());
            fertilizerApplication.setReminders(remindersSp.getSelectedItem().toString());
            if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
                String weeks = weeksTxt.getText().toString();
                String repeatUntil = repeatUntilTxt.getText().toString();

                fertilizerApplication.setFrequency(Float.parseFloat(weeks));
                fertilizerApplication.setRepeatUntil(repeatUntil);
            }
            if(daysBeforeLayout.getVisibility()==View.VISIBLE){
                String days = daysBeforeTxt.getText().toString();


                fertilizerApplication.setDaysBefore(days);

            }

            dbHandler.updateCropFertilizerApplication(fertilizerApplication);

        }
    }
    public void fillViews(){
        if(fertilizerApplication != null){
            CropDashboardActivity.selectSpinnerItemByValue(fertilizerFormSp, fertilizerApplication.getFertilizerForm());
            CropDashboardActivity.selectSpinnerItemByValue(recurrenceSp, fertilizerApplication.getRecurrence());
            CropDashboardActivity.selectSpinnerItemByValue(remindersSp, fertilizerApplication.getReminders());
            CropDashboardActivity.selectSpinnerItemById(fertilizerId, fertilizerApplication.getFertilizerName());

            rateTxt.setText(fertilizerApplication.getRate()+"");
            dateTxt.setText(fertilizerApplication.getDate());
            reasonTxt.setText(fertilizerApplication.getReason());
            operatorTxt.setText(fertilizerApplication.getOperator());
            costTxt.setText(fertilizerApplication.getCost()+"");
            rateTxt.setText(fertilizerApplication.getRate()+"");
            weeksTxt.setText(fertilizerApplication.getFrequency()+"");
            repeatUntilTxt.setText(fertilizerApplication.getRepeatUntil());
            daysBeforeTxt.setText(fertilizerApplication.getDaysBefore());

            CropDashboardActivity.selectSpinnerItemById(fertilizerId,fertilizerApplication.getFertilizerId());
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

        else if(fertilizerFormSp.getSelectedItemPosition()==0){
            message = getString(R.string.fertilizer_form_not_selected);
            fertilizerId.requestFocus();
        }
        else if(methodSp.getSelectedItemPosition()==0){
            message =  getString(R.string.application_method_not_entered);
            methodSp.requestFocus();
        }
        else if(fertilizerId.getSelectedItemPosition()==0){
            message = getString(R.string.fertilizer_name_not_entered);
            fertilizerId.requestFocus();
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
            Toast.makeText(CropFertilizerApplicationManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

    private class FertilizerFormSpinnerItem implements CropSpinnerItem{

        String value;
        FertilizerFormSpinnerItem(String value){
            this.value =value;
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public String getId() {
            return value;
        }
    }

    
}
