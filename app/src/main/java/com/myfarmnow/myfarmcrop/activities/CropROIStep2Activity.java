package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;

import java.text.NumberFormat;

public class CropROIStep2Activity extends AppCompatActivity {

    Button btnNext,btnPrevious;
    EditText seedAndTreatmentTxt, fertilizerTxt, herbicideTxt, insecticideTxt, fuelTxt, machineryOperatingTxt, machineryLeaseTxt,
    landRentalTxt, insurancePremiumTxt,dryingCostsTxt,otherCostsTxt;

    TextView totalVariableCostTxt;
    EditText [] numericFields ;
    String currency="UGX ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_roistep2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.d("Calculator 0",CropROICalculatorSingleton.getInstance()+"");
        setSupportActionBar(toolbar);
        initializeViews();

    }
    public void initializeViews(){
        btnNext = findViewById(R.id.txt_crop_roi_step_2_crop_btn_next);
        btnPrevious = findViewById(R.id.txt_crop_roi_step_2_crop_btn_previous);
        seedAndTreatmentTxt = findViewById(R.id.txt_crop_roi_step_2_seed_and_treatment);
        fertilizerTxt = findViewById(R.id.txt_crop_roi_step_2_fertilizer);
        herbicideTxt = findViewById(R.id.txt_crop_roi_step_2_herbicide);
        insecticideTxt = findViewById(R.id.txt_crop_roi_step_2_insecticide);
        fuelTxt = findViewById(R.id.txt_crop_roi_step_2_fuel);
        machineryOperatingTxt = findViewById(R.id.txt_crop_roi_step_2_machinery_operating);
        machineryLeaseTxt = findViewById(R.id.txt_crop_roi_step_2_machinery_lease);
        landRentalTxt = findViewById(R.id.txt_crop_roi_step_2_land_rental);
        insurancePremiumTxt = findViewById(R.id.txt_crop_roi_step_2_crop_insurance_premium);
        dryingCostsTxt = findViewById(R.id.txt_crop_roi_step_2_crop_drying_costs);
        otherCostsTxt = findViewById(R.id.txt_crop_roi_step_2_crop_other_costs);
        totalVariableCostTxt = findViewById(R.id.txt_crop_roi_step_2_crop_total_variable_cost);

        numericFields =new EditText[]{seedAndTreatmentTxt, fertilizerTxt, herbicideTxt, insecticideTxt, fuelTxt, machineryOperatingTxt, machineryLeaseTxt,
                landRentalTxt, insurancePremiumTxt,dryingCostsTxt,otherCostsTxt};
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                finish();
                Log.d("Calculator 1",CropROICalculatorSingleton.getInstance()+"");
                startActivity(new Intent(CropROIStep2Activity.this,CropROIStep3Activity.class));
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                finish();
                Log.d("Calculator 2",CropROICalculatorSingleton.getInstance()+"");
                startActivity(new Intent(CropROIStep2Activity.this,CropROIStep1Activity.class));
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

    public void updateCalculations(){
        try{
            CropROICalculatorSingleton.getInstance().setStep2SeedAndTreatment( Float.parseFloat(seedAndTreatmentTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2Fertilizer(Float.parseFloat(fertilizerTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2Herbicide(Float.parseFloat(herbicideTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2Insecticide(Float.parseFloat(insecticideTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2Fuel(Float.parseFloat(fuelTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2MachineryOperating(Float.parseFloat(machineryOperatingTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2MachineryLease(Float.parseFloat(machineryLeaseTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2LandRental(Float.parseFloat(landRentalTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2CropInsurancePremium(Float.parseFloat(insurancePremiumTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2DryingCosts(Float.parseFloat(dryingCostsTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2OtherCosts(Float.parseFloat(otherCostsTxt.getText().toString()));
        }catch (Exception e){

        }

        totalVariableCostTxt.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep2TotalVariableCosts()));
    }

    //
    public void validateEntries(){
        for(EditText x : numericFields){
            if(x.getText().toString().isEmpty()){
               x.setText(getString(R.string.default_numeric_value));
            }
        }
    }

    public void fillViews(){

        seedAndTreatmentTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2SeedAndTreatment());
        fertilizerTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2Fertilizer());
        herbicideTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2Herbicide());
        insecticideTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2Insecticide());
        fuelTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2Fuel());
        machineryOperatingTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2MachineryOperating());
        machineryLeaseTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2MachineryLease());
        insurancePremiumTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2CropInsurancePremium());
        landRentalTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2LandRental());
        dryingCostsTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2DryingCosts());
        otherCostsTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2OtherCosts());
        updateCalculations();
    }
}


