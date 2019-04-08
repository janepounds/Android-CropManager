package com.myfarmnow.myfarmcrop.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.singletons.CropNutrientsCalculator;

public class CropNutrientsCalculatorResults extends AppCompatActivity {

    TextView nitrogenRemovedTextView, potassiumRemovedTextView, phoshateRemovedTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_nutrients_calculator_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(CropNutrientsCalculator.getInstance().isCalculationPossible()){
            finish();
        }
        initializeViews();
    }

    public void initializeViews(){
        //text_view_crop_nutrients_calculator_results_n
        nitrogenRemovedTextView = findViewById(R.id.text_view_crop_nutrients_calculator_results_n);
        potassiumRemovedTextView = findViewById(R.id.text_view_crop_nutrients_calculator_results_k);
        phoshateRemovedTextView = findViewById(R.id.text_view_crop_nutrients_calculator_results_p);

        nitrogenRemovedTextView.setText("N = "+ CropNutrientsCalculator.getInstance().computeNitrogenLost()+CropNutrientsCalculator.getInstance().getUnits());
        potassiumRemovedTextView.setText("P = "+ CropNutrientsCalculator.getInstance().computeNitrogenLost()+CropNutrientsCalculator.getInstance().getUnits());
        phoshateRemovedTextView.setText("K = "+ CropNutrientsCalculator.getInstance().computeNitrogenLost()+CropNutrientsCalculator.getInstance().getUnits());

    }

}
