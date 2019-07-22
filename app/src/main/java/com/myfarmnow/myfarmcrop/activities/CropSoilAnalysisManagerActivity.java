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
import com.myfarmnow.myfarmcrop.models.CropSoilAnalysis;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropSoilAnalysisManagerActivity extends AppCompatActivity {


    EditText dateTxt,phTxt,organicMatterTxt,agronomistTxt,costTxt,resultsTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt ;
    TextView currencyTxt;
    Spinner recurrenceSp,remindersSp;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout,remindersLayout;
    Button btn_save;
    CropSoilAnalysis soilAnalysis;
    String fieldId;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_soil_analysis_manager);

        if(getIntent().hasExtra("soilAnalysis")){
            soilAnalysis =(CropSoilAnalysis) getIntent().getSerializableExtra("soilAnalysis");
        }
        if(getIntent().hasExtra("fieldId")){
            fieldId =getIntent().getStringExtra("fieldId");
        }
        else{
            finish();
        }
        Log.d("FIELD ID",fieldId);
        initializeForm();
    }

    public void initializeForm(){
        dateTxt =findViewById(R.id.txt_crop_soil_analysis_date);
        phTxt =findViewById(R.id.txt_crop_soil_analysis_soil_ph);
        organicMatterTxt =findViewById(R.id.txt_crop_soil_analysis_organic_matter);
        agronomistTxt =findViewById(R.id.txt_crop_soil_analysis_agronomist);
        costTxt =findViewById(R.id.txt_crop_soil_analysis_cost);
        currencyTxt =findViewById(R.id.txt_crop_soil_analysis_currency);
        resultsTxt =findViewById(R.id.txt_crop_soil_analysis_results);
        btn_save = findViewById(R.id.btn_save);
        remindersSp = findViewById(R.id.sp_crop_soil_analysis_reminders);
        recurrenceSp = findViewById(R.id.sp_crop_soil_analysis_recurrence);
        weeksTxt = findViewById(R.id.txt_crop_soil_analysis_weekly_weeks);
        repeatUntilTxt = findViewById(R.id.txt_crop_soil_analysis_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_soil_analysis_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_soil_analysis_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_soil_analysis_days_before);
        remindersLayout = findViewById(R.id.layout_crop_soil_analysis_reminders);
        CropDashboardActivity.addDatePicker(dateTxt,this);
        CropDashboardActivity.addDatePicker(repeatUntilTxt,this);


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

        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(soilAnalysis==null){
                        saveSoilAnalysis();
                    }
                    else{
                        updateSoilAnalysis();
                    }
                    Intent toCropsList = new Intent(CropSoilAnalysisManagerActivity.this, CropSoilAnalysisListActivity.class);
                    toCropsList.putExtra("fieldId",fieldId);

                    toCropsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropsList);
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        fillViews();
    }

    public void saveSoilAnalysis(){
        soilAnalysis = new CropSoilAnalysis();
        soilAnalysis.setUserId(CropDashboardActivity.getPreferences("userId",this));
        soilAnalysis.setDate(dateTxt.getText().toString());
        soilAnalysis.setResult(resultsTxt.getText().toString());
        soilAnalysis.setAgronomist(agronomistTxt.getText().toString());
        soilAnalysis.setFieldId(fieldId);
        soilAnalysis.setPh(Float.parseFloat(phTxt.getText().toString()));
        soilAnalysis.setCost(Float.parseFloat(costTxt.getText().toString()));
        soilAnalysis.setOrganicMatter(Float.parseFloat(organicMatterTxt.getText().toString()));
        soilAnalysis.setRecurrence(recurrenceSp.getSelectedItem().toString());
        soilAnalysis.setReminders(remindersSp.getSelectedItem().toString());
        soilAnalysis.setRepeatUntil(repeatUntilTxt.getText().toString());
        soilAnalysis.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
        soilAnalysis.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));


        dbHandler.insertCropSoilAnalysis(soilAnalysis);

    }
    public void updateSoilAnalysis(){
        if(soilAnalysis != null){
            soilAnalysis.setDate(dateTxt.getText().toString());
            soilAnalysis.setResult(resultsTxt.getText().toString());
            soilAnalysis.setAgronomist(agronomistTxt.getText().toString());
            soilAnalysis.setFieldId(fieldId);
            soilAnalysis.setPh(Float.parseFloat(phTxt.getText().toString()));
            soilAnalysis.setCost(Float.parseFloat(costTxt.getText().toString()));
            soilAnalysis.setOrganicMatter(Float.parseFloat(organicMatterTxt.getText().toString()));
            soilAnalysis.setRecurrence(recurrenceSp.getSelectedItem().toString());
            soilAnalysis.setReminders(remindersSp.getSelectedItem().toString());
            soilAnalysis.setRepeatUntil(repeatUntilTxt.getText().toString());
            soilAnalysis.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
            soilAnalysis.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));


            dbHandler.updateCropSoilAnalysis(soilAnalysis);
        }
    }

    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }
        else if(agronomistTxt.getText().toString().isEmpty()){
            message = getString(R.string.agronomist_not_entered);
            agronomistTxt.requestFocus();
        }
        else if(costTxt.getText().toString().isEmpty()){
            message = getString(R.string.crop_not_entered);
            costTxt.requestFocus();
        }else if(resultsTxt.getText().toString().isEmpty()){
            message = getString(R.string.operator_not_entered);
            resultsTxt.requestFocus();
        }
        else if(recurrenceSp.getSelectedItemPosition()==0){
            message = getString(R.string.recurrence_not_selected);
            recurrenceSp.requestFocus();
        }
        else if(remindersSp.getSelectedItemPosition()==0){
            message = getString(R.string.reminders_not_selected);
            remindersSp.requestFocus();
        }
        if(phTxt.getText().toString().isEmpty()){
            phTxt.setText("0");
        }
        if(organicMatterTxt.getText().toString().isEmpty()){
            organicMatterTxt.setText("0");
        }


        if(message != null){
            Toast.makeText(CropSoilAnalysisManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

    public void fillViews() {
        if (soilAnalysis != null) {
            CropDashboardActivity.selectSpinnerItemByValue(recurrenceSp, soilAnalysis.getRecurrence());
            CropDashboardActivity.selectSpinnerItemByValue(remindersSp, soilAnalysis.getReminders());

            dateTxt.setText(soilAnalysis.getDate());
            organicMatterTxt.setText(soilAnalysis.getOrganicMatter()+"");
            agronomistTxt.setText(soilAnalysis.getAgronomist()+"");
            costTxt.setText(soilAnalysis.getCost()+"");
            phTxt.setText(soilAnalysis.getPh()+"");
            resultsTxt.setText(soilAnalysis.getResult());
            weeksTxt.setText(soilAnalysis.getFrequency()+"");
            repeatUntilTxt.setText(soilAnalysis.getRepeatUntil());
            daysBeforeTxt.setText(soilAnalysis.getDaysBefore()+"");

        }
    }

}
