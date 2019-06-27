package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropSettingsActivity extends AppCompatActivity {
        Spinner currencySp,dateFormatSp, areaUnitsSp,weightUnitsSp;

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
            areaUnitsSp = findViewById(R.id.spinner_crop_settings_area_units);
            weightUnitsSp = findViewById(R.id.spinner_crop_settings_weight_units);


            saveBtn = findViewById(R.id.btn_save);


            saveBtn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
                @Override
                public void onClick(View v) {

                    saveSettings();


                }
            });;
            fillViews();
            ((ArrayAdapter)currencySp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
            ((ArrayAdapter)weightUnitsSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
            ((ArrayAdapter)areaUnitsSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
            ((ArrayAdapter)dateFormatSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        }

        public void saveSettings(){
            settingsSingleton.setDateFormat(dateFormatSp.getSelectedItem().toString());
            settingsSingleton.setWeightUnits(weightUnitsSp.getSelectedItem().toString());
            settingsSingleton.setAreaUnits(areaUnitsSp.getSelectedItem().toString());
            settingsSingleton.setCurrency(currencySp.getSelectedItem().toString());

            MyFarmDbHandlerSingleton.getHandlerInstance(this).updateSettings(settingsSingleton);
            startActivity(new Intent(this, CropDashboardActivity.class));
            finish();

        }

        public void fillViews(){
            if(settingsSingleton != null){

                CropDashboardActivity.selectSpinnerItemByValue(dateFormatSp,settingsSingleton.getDateFormat());
                CropDashboardActivity.selectSpinnerItemByValue(weightUnitsSp,settingsSingleton.getWeightUnits());
                CropDashboardActivity.selectSpinnerItemByValue(areaUnitsSp,settingsSingleton.getAreaUnits());
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
            else  if(areaUnitsSp.getSelectedItemPosition()==0){
                message = "Height units are not selected";
                areaUnitsSp.requestFocus();
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
