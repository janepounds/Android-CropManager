package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropNutrientsCalculator;

import java.util.ArrayList;

public class CropNutrientsCalculatorEntryActivity extends AppCompatActivity {

    Spinner cropSp, unitsSp;
    EditText yieldTxt, areaTxt;
    CropSpinnerAdapter cropsSpinnerAdapter;
    MyFarmDbHandlerSingleton dbHandler;
    Button calculateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_nutrients_calculator_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();



    }

    public void initializeViews(){
        cropSp = findViewById(R.id.sp_crop_fertililizer_calculator_crop);
        unitsSp = findViewById(R.id.sp_crop_fertililizer_calculator_units);

        yieldTxt = findViewById(R.id.txt_crop_fertililizer_calculator_npk_price);

        calculateBtn = findViewById(R.id.btn_crop_fertililizer_calculator_calculate);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        cropsSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Crop",this);
        cropSp.setAdapter(cropsSpinnerAdapter);
        ArrayList<CropItem> cropItems = dbHandler.getCropItemsForNutrientRemoval();
        for(CropItem cropItem: cropItems){
            cropsSpinnerAdapter.add(cropItem);
        }

        cropSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CropItem cropItem = (CropItem)((CropSpinnerItem)cropSp.getSelectedItem());
                    CropNutrientsCalculator.getInstance().setCrop(cropItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    try {
                        CropNutrientsCalculator.getInstance().setArea(Float.parseFloat(areaTxt.getText().toString()));
                        CropNutrientsCalculator.getInstance().setUnits(unitsSp.getSelectedItem().toString());
                        startActivity(new Intent(CropNutrientsCalculatorEntryActivity.this,CropNutrientsCalculatorResults.class));

                    }catch (Exception e){

                    }
                }
            }
        });
    }

    public boolean validateEntries(){
        String message = null;
        if(yieldTxt.getText().toString().isEmpty()){
            message = getString(R.string.estimated_yield_not_entered);
            yieldTxt.requestFocus();
        }
        if(areaTxt.getText().toString().isEmpty()){
            message = getString(R.string.area_not_entered);
            areaTxt.requestFocus();
        }


        // potassiumTxt,, ,



        if(message != null){
            Toast.makeText(CropNutrientsCalculatorEntryActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

}
