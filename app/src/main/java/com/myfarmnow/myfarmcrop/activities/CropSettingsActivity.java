package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropSettingsActivity extends AppCompatActivity {
        Spinner currencySp,dateFormatSp,heightUnitsSp,weightUnitsSp;
        EditText pregnancyCheckTxt,calfWeaningTxt,calfPrepTxt, nextBreedingTxt,calfDueTxt;
        Button saveBtn;
        CropSettingsSingleton settingsSingleton = CropSettingsSingleton.getInstance();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_crop_settings);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            initializeVariables();
        }

        public void initializeVariables(){

            currencySp = findViewById(R.id.spinner_crop_settings_currency);
            dateFormatSp = findViewById(R.id.spinner_crop_settings_date_format);
            heightUnitsSp = findViewById(R.id.spinner_crop_settings_area_units);
            weightUnitsSp = findViewById(R.id.spinner_crop_settings_weight_units);


            saveBtn = findViewById(R.id.btn_save);


            saveBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                @Override
                public void onClick(View v) {
                    if(validateEntries()){
                        saveSettings();
                       finish();
                    }else{
                        Log.d("ERROR","Testing");
                    }
                }
            });;
            fillViews();

        }

        public void saveSettings(){

        }

        public void fillViews(){
            if(settingsSingleton != null){

                CropDashboardActivity.selectSpinnerItemByValue(dateFormatSp,settingsSingleton.getDateFormat());
                CropDashboardActivity.selectSpinnerItemByValue(weightUnitsSp,settingsSingleton.getWeightUnits());
                CropDashboardActivity.selectSpinnerItemByValue(heightUnitsSp,settingsSingleton.getAreaUnits());
                CropDashboardActivity.selectSpinnerItemByValue(currencySp,settingsSingleton.getCurrency());

            }
        }

        public boolean validateEntries(){
            String message = null;

            if(dateFormatSp.getSelectedItemPosition()==0){
                message = "Date Format is not selected";
                dateFormatSp.requestFocus();
            }else  if(weightUnitsSp.getSelectedItemPosition()==0){
                message = "Weight units are not selected";
                weightUnitsSp.requestFocus();
            }
            else  if(heightUnitsSp.getSelectedItemPosition()==0){
                message = "Height units are not selected";
                heightUnitsSp.requestFocus();
            }
            else  if(currencySp.getSelectedItemPosition()==0){
                message = "Currency is not selected";
                currencySp.requestFocus();
            }
            if(message != null){
                Toast.makeText(CropSettingsActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
                return false;
            }
            // Log.d("ERROR",message);
            return true;


        }

}
