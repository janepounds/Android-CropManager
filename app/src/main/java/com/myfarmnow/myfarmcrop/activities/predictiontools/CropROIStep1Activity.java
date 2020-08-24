package com.myfarmnow.myfarmcrop.activities.predictiontools;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;

import java.text.NumberFormat;

public class CropROIStep1Activity extends AppCompatActivity {

    Button btnNext;
    Spinner yieldUnitsSp;
    EditText avgPriceTxt, yieldTxt, otherIncomeTxt;
    TextView estGrossRevenueTxt;
    EditText [] numericFields;
    TextView yieldsUnitsTextView;
    String currency="UGX ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_roistep1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();
    }

    public void initializeViews(){
        btnNext = findViewById(R.id.txt_crop_roi_step_1_crop_btn_next);
        yieldUnitsSp = findViewById(R.id.spinner_crop_roi_step_1_yield_units);
        avgPriceTxt = findViewById(R.id.txt_crop_ro1_step_1_avg_price);
        yieldTxt = findViewById(R.id.txt_crop_ro1_step_yield);
        otherIncomeTxt = findViewById(R.id.txt_crop_ro1_step_other_income);
        estGrossRevenueTxt = findViewById(R.id.txt_crop_ro1_step_1_est_gross_revenue);
        yieldsUnitsTextView = findViewById(R.id.txt_crop_ro1_step_label_yield_units);

        yieldUnitsSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection ="";
                if(position==0){
                    selection ="Units";
                    yieldsUnitsTextView.setText(selection);
                    return;
                }
                else {
                    selection=yieldUnitsSp.getSelectedItem().toString();
                }
                yieldsUnitsTextView.setText(selection);
                CropROICalculatorSingleton.getInstance().setStep1YieldUnits(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numericFields =new EditText[]{avgPriceTxt, yieldTxt, otherIncomeTxt};
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                finish();
                startActivity(new Intent(CropROIStep1Activity.this,CropROIStep2Activity.class));
            }
        });

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

        try{
            CropROICalculatorSingleton.getInstance().setStep1Price( Float.parseFloat(avgPriceTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep1YieldPerAcre(Float.parseFloat(yieldTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep1OtherIncome(Float.parseFloat(otherIncomeTxt.getText().toString()));
        }catch (Exception e){

        }
        estGrossRevenueTxt.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep1GrossRevenue()));
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
        DashboardActivity.selectSpinnerItemByValue(yieldUnitsSp,CropROICalculatorSingleton.getInstance().getStep1YieldUnits());
        avgPriceTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep1Price());
        yieldTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep1YieldPerAcre());
        otherIncomeTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep1OtherIncome());
    }
}
