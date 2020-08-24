package com.myfarmnow.myfarmcrop.activities.predictiontools;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.singletons.CropNutrientsCalculator;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CropNutrientsCalculatorResults extends AppCompatActivity {

    TextView nitrogenRemovedTextView, potassiumRemovedTextView, phoshateRemovedTextView,
            nitrogenRemovedCostTextView, potassiumRemovedCostTextView, phoshateRemovedCostTextView;
    LinearLayout economicImpactLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_nutrients_calculator_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(!CropNutrientsCalculator.getInstance().isCalculationPossible()){
            finish();
        }
        initializeViews();
    }

    public void initializeViews(){

        nitrogenRemovedTextView = findViewById(R.id.text_view_crop_nutrients_calculator_results_n);
        potassiumRemovedTextView = findViewById(R.id.text_view_crop_nutrients_calculator_results_k);
        phoshateRemovedTextView = findViewById(R.id.text_view_crop_nutrients_calculator_results_p);
        nitrogenRemovedCostTextView = findViewById(R.id.text_view_crop_nutrients_calculator_results_n_cost);
        potassiumRemovedCostTextView = findViewById(R.id.text_view_crop_nutrients_calculator_results_k_cost);
        phoshateRemovedCostTextView = findViewById(R.id.text_view_crop_nutrients_calculator_results_p_cost);
        economicImpactLayout = findViewById(R.id.layout_crop_nutrients_calculator_results_impact);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        nitrogenRemovedTextView.setText("N = "+ df.format(CropNutrientsCalculator.getInstance().computeNitrogenLost())+"Kg");
        phoshateRemovedTextView.setText("P = "+ df.format(CropNutrientsCalculator.getInstance().computePhosphateLost())+"Kg");
        potassiumRemovedTextView.setText("K = "+ df.format(CropNutrientsCalculator.getInstance().computePotassiumLost())+"Kg");

        if(CropNutrientsCalculator.getInstance().isEconomicImpactRequired()){
            nitrogenRemovedCostTextView.setText("N = "+ CropSettingsSingleton.getInstance().getCurrency()+" " + NumberFormat.getInstance().format(CropNutrientsCalculator.getInstance().computeNitrogenLostValue()));
            phoshateRemovedCostTextView.setText("P = "+ (CropSettingsSingleton.getInstance().getCurrency())+" " +NumberFormat.getInstance().format(CropNutrientsCalculator.getInstance().computePhosphorusLostValue()));
            potassiumRemovedCostTextView.setText("K = "+ (CropSettingsSingleton.getInstance().getCurrency())+" " +NumberFormat.getInstance().format(CropNutrientsCalculator.getInstance().computePotassiumLostValue()));
            economicImpactLayout.setVisibility(View.VISIBLE);
        }

    }

}
