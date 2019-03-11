package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropSoilAnalysis;

public class CropSoilAnalysisManagerActivity extends AppCompatActivity {


    EditText dateTxt,phTxt,organicMatterTxt,agronomistTxt,costTxt,resultsTxt ;
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
        initializeForm();
    }

    public void initializeForm(){
        dateTxt =findViewById(R.id.txt_crop_soil_analysis_date);
        phTxt =findViewById(R.id.txt_crop_soil_analysis_soil_ph);
        organicMatterTxt =findViewById(R.id.txt_crop_soil_analysis_organic_matter);
        agronomistTxt =findViewById(R.id.txt_crop_soil_analysis_agronomist);
        costTxt =findViewById(R.id.txt_crop_soil_analysis_cost);
        resultsTxt =findViewById(R.id.txt_crop_soil_analysis_results);
        btn_save = findViewById(R.id.btn_save);
        CropDashboardActivity.addDatePicker(dateTxt,this);

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
                    toCropsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropsList);
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
            dateTxt.setText(soilAnalysis.getDate());
            organicMatterTxt.setText(soilAnalysis.getOrganicMatter()+"");
            agronomistTxt.setText(soilAnalysis.getAgronomist()+"");
            costTxt.setText(soilAnalysis.getCost()+"");
            phTxt.setText(soilAnalysis.getPh()+"");
            resultsTxt.setText(soilAnalysis.getResult());
        }
    }

}
