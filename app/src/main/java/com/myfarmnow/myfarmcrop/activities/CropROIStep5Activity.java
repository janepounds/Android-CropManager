package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;

public class CropROIStep5Activity extends AppCompatActivity {

    EditText landInvestmentTxt, machineryInvestmentTxt,buildingInvestmentTxt;
    Button btnNext,btnPrevious;
    EditText [] numericFields ;
    TextView totalcapitalInvestmentTxt;
    String currency= CropSettingsSingleton.getInstance().getCurrency()+" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_roistep5);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();
    }

    public void initializeViews(){
        btnNext = findViewById(R.id.txt_crop_roi_step_5_crop_btn_next);
        btnPrevious = findViewById(R.id.txt_crop_roi_step_5_crop_btn_previous);
        landInvestmentTxt = findViewById(R.id.txt_crop_roi_step_5_land_investment);
        totalcapitalInvestmentTxt = findViewById(R.id.txt_crop_roi_step_4_crop_total_capital_investment);
        machineryInvestmentTxt = findViewById(R.id.txt_crop_roi_step_5_machinery_investment);
        buildingInvestmentTxt = findViewById(R.id.txt_crop_roi_step_5_building_investment);
        numericFields =new EditText[]{landInvestmentTxt, machineryInvestmentTxt,buildingInvestmentTxt};
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                finish();
                startActivity(new Intent(CropROIStep5Activity.this,CropROIResultsActivity.class));
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                finish();
                startActivity(new Intent(CropROIStep5Activity.this,CropROIStep4Activity.class));
            }
        });
        fillViews();
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateCalculations();
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateCalculations();
            }
        };
        for(EditText x : numericFields){
            x.addTextChangedListener(watcher);
        }


    }
    public void fillViews(){
        landInvestmentTxt.setText(""+ CropROICalculatorSingleton.getInstance().getStep5LandInvestment());
        machineryInvestmentTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep5MachineryInvestment());
        buildingInvestmentTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep5BuildingInvestment());
        updateCalculations();
    }
    public void updateCalculations(){
        try{
            CropROICalculatorSingleton.getInstance().setStep5BuildingInvestment( Float.parseFloat(buildingInvestmentTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep5LandInvestment(Float.parseFloat(landInvestmentTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep5MachineryInvestment(Float.parseFloat(machineryInvestmentTxt.getText().toString()));
        }catch (Exception e){

        }

        totalcapitalInvestmentTxt.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep5TotalCapitalInvestment()));
    }
    public void validateEntries(){
        for(EditText x : numericFields){
            if(x.getText().toString().isEmpty()){
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }

}
