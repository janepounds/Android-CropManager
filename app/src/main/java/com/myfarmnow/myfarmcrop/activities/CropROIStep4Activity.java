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

public class CropROIStep4Activity extends AppCompatActivity {

    Button btnNext,btnPrevious;

    EditText ownerAllowanceQtyTxt, ownerAllowanceCostTxt,salariedEmployeeQtyTxt, salariedEmployeeCostTxt ,casualEmployeeNumberTxt, casualEmployeeCostTxt,
    casualEmployeeHoursTxt,casualEmployeeWeeksTxt,casualEmployeeTotalTxt,totalLabourCostTxt;
    TextView totalExpensesTxt;
    EditText [] numericFields ;



    String currency= CropSettingsSingleton.getInstance().getCurrency()+" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_roistep4);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();
    }

    public void initializeViews(){
        btnNext = findViewById(R.id.txt_crop_roi_step_4_crop_btn_next);
        btnPrevious = findViewById(R.id.txt_crop_roi_step_4_crop_btn_previous);
        ownerAllowanceQtyTxt = findViewById(R.id.txt_crop_roi_step_4_owner_allowance_quantity);
        ownerAllowanceCostTxt = findViewById(R.id.txt_crop_roi_step_4_owner_allowance_cost);
        salariedEmployeeQtyTxt = findViewById(R.id.txt_crop_roi_step_4_salaried_employee_quantity);
        salariedEmployeeCostTxt = findViewById(R.id.txt_crop_roi_step_4_salaried_employee_cost);
        casualEmployeeNumberTxt = findViewById(R.id.txt_crop_roi_step_4_casual_employee_number);
        casualEmployeeCostTxt = findViewById(R.id.txt_crop_roi_step_4_casual_employee_cost);
        casualEmployeeHoursTxt = findViewById(R.id.txt_crop_roi_step_4_casual_employee_hours);
        casualEmployeeWeeksTxt = findViewById(R.id.txt_crop_roi_step_4_casual_employee_weeks);
        totalExpensesTxt = findViewById(R.id.txt_crop_roi_step_4_crop_total_expenses);
        casualEmployeeTotalTxt = findViewById(R.id.txt_crop_roi_step_4_casual_employee_total);
        totalLabourCostTxt = findViewById(R.id.txt_crop_roi_step_4_total_labour_costs);

        numericFields =new EditText[]{ownerAllowanceQtyTxt, ownerAllowanceCostTxt,salariedEmployeeQtyTxt, salariedEmployeeCostTxt , casualEmployeeCostTxt,
                casualEmployeeNumberTxt,casualEmployeeHoursTxt,casualEmployeeWeeksTxt};
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                finish();
                startActivity(new Intent(CropROIStep4Activity.this,CropROIStep5Activity.class));
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                finish();
                startActivity(new Intent(CropROIStep4Activity.this,CropROIStep3Activity.class));
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
        ownerAllowanceQtyTxt.setText(""+ CropROICalculatorSingleton.getInstance().getStep4OwnerAllowanceQty());
        ownerAllowanceCostTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep4OwnerAllowanceCost());
        salariedEmployeeQtyTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep4SalariedEmployeeQty());
        salariedEmployeeCostTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep4SalariedEmployeeCost());
        casualEmployeeCostTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep4CasualEmployeeCost());
        casualEmployeeHoursTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep4CasualEmployeeHours());
        casualEmployeeWeeksTxt.setText(""+CropROICalculatorSingleton.getInstance().getStep4CasualEmployeeWeeks());
        updateCalculations();
    }
    public void updateCalculations(){
        try{
            CropROICalculatorSingleton.getInstance().setStep4OwnerAllowanceQty( Float.parseFloat(ownerAllowanceQtyTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4OwnerAllowanceCost(Float.parseFloat(ownerAllowanceCostTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4SalariedEmployeeCost(Float.parseFloat(salariedEmployeeCostTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4SalariedEmployeeQty(Float.parseFloat(salariedEmployeeQtyTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeCost(Float.parseFloat(casualEmployeeCostTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeNumber(Float.parseFloat(casualEmployeeNumberTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeHours(Float.parseFloat(casualEmployeeHoursTxt.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeWeeks(Float.parseFloat(casualEmployeeWeeksTxt.getText().toString()));
        }catch (Exception e){

        }
        totalExpensesTxt.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep4TotalExpenses()));
        casualEmployeeTotalTxt.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep4TotalCasualEmployeeCost()));
        totalLabourCostTxt.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep4TotalLabourCosts()));
    }
    public void validateEntries(){
        for(EditText x : numericFields){
            if(x.getText().toString().isEmpty()){
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }

}
