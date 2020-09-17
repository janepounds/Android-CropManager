package com.myfarmnow.myfarmcrop.activities.predictiontools;

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
    EditText yeildperareaTxt, fertilizerTxt, pesticides,  otherCostsTxt;

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
        fertilizerTxt = findViewById(R.id.txt_crop_roi_step_2_fertilizer);
        pesticides = findViewById(R.id.txt_crop_roi_step_2_pesticides);
        yeildperareaTxt = findViewById(R.id.txt_crop_roi_step_2_yeildperarea);
        otherCostsTxt = findViewById(R.id.txt_crop_roi_step_2_othercosts);
        totalVariableCostTxt = findViewById(R.id.txt_crop_roi_step_2_crop_total_variable_cost);

        numericFields =new EditText[]{yeildperareaTxt, fertilizerTxt, pesticides, otherCostsTxt};
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
            CropROICalculatorSingleton.getInstance().setStep2SeedAndTreatment( Float.parseFloat(yeildperareaTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2Fertilizer(Float.parseFloat(fertilizerTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2Herbicide(Float.parseFloat(pesticides.getText().toString()));
        }catch (Exception e){

        }

        try{
            CropROICalculatorSingleton.getInstance().setStep2OtherCosts(Float.parseFloat(otherCostsTxt.getText().toString()));
        }catch (Exception e){

        }

        totalVariableCostTxt.setText(NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep2TotalVariableCosts()));
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

        yeildperareaTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2SeedAndTreatment());
        fertilizerTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2Fertilizer());
        pesticides.setText(""+CropROICalculatorSingleton.getInstance().getStep2Herbicide());
        otherCostsTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep2OtherCosts());
        updateCalculations();
    }
}


