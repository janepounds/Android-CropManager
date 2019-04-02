package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class CropROIStep3Activity extends AppCompatActivity {

    Button btnNext,btnPrevious;
    EditText buildingRepairTxt, machineryDepreciationTxt,propertyTaxesTxt,businessOverheadTxt,buildingDepreciationTxt,
    otherCostsTxt;
    TextView totalOverheadCostsTxt;
    EditText [] numericFields ;
    String currency="UGX ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_roistep3);
        Log.d("Calculator 0",CropROICalculatorSingleton.getInstance()+"");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();

    }

    public void initializeViews(){
        btnNext = findViewById(R.id.txt_crop_roi_step_3_crop_btn_next);
        btnPrevious = findViewById(R.id.txt_crop_roi_step_3_crop_btn_previous);
        buildingRepairTxt = findViewById(R.id.txt_crop_roi_step_3_building_repair);
        machineryDepreciationTxt = findViewById(R.id.txt_crop_roi_step_3_machinery_depreciation);
        propertyTaxesTxt = findViewById(R.id.txt_crop_roi_step_3_property_taxes);
        businessOverheadTxt = findViewById(R.id.txt_crop_roi_step_3_business_overhead);
        buildingDepreciationTxt = findViewById(R.id.txt_crop_roi_step_3_building_depreciation);
        otherCostsTxt = findViewById(R.id.txt_crop_roi_step_3_total_other_costs);
        totalOverheadCostsTxt = findViewById(R.id.txt_crop_ro1_step_3_total_overhead_costs);

        numericFields = new EditText[]{
                buildingRepairTxt, machineryDepreciationTxt,propertyTaxesTxt,businessOverheadTxt,buildingDepreciationTxt,
                otherCostsTxt
        };

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                finish();
                startActivity(new Intent(CropROIStep3Activity.this,CropROIStep4Activity.class));
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                finish();
                startActivity(new Intent(CropROIStep3Activity.this,CropROIStep2Activity.class));
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

        fillViews();
    }
    public void updateCalculations(){
        /*
         , ,propertyTaxesTxt,businessOverheadTxt,buildingDepreciationTxt,
                otherCostsTxt
         */
        try{
            CropROICalculatorSingleton.getInstance().setStep3BuildingRepair( Float.parseFloat(buildingRepairTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep3machineDepreciation(Float.parseFloat(machineryDepreciationTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep3PropertyTaxes(Float.parseFloat(propertyTaxesTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep3BusinessOverhead(Float.parseFloat(businessOverheadTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep3BuildingDepreciation(Float.parseFloat(buildingDepreciationTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep3TotalOtherExpenses(Float.parseFloat(otherCostsTxt.getText().toString()));
        }catch (Exception e){

        }
        totalOverheadCostsTxt.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep3TotalOverheadCosts()));
    }
    public void fillViews(){

        /*
         buildingRepairTxt, machineryDepreciationTxt,propertyTaxesTxt,businessOverheadTxt,buildingDepreciationTxt,
                otherCostsTxt
         */
        buildingRepairTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep3BuildingRepair());
        machineryDepreciationTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep3machineDepreciation());
        propertyTaxesTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep3PropertyTaxes());
        businessOverheadTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep3BusinessOverhead());
        buildingDepreciationTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep3BuildingDepreciation());
        otherCostsTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep3TotalOtherExpenses());
        updateCalculations();
    }
    public void validateEntries(){
        for(EditText x : numericFields){
            if(x.getText().toString().isEmpty()){
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }

}
