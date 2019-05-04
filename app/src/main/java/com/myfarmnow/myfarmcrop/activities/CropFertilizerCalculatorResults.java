package com.myfarmnow.myfarmcrop.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.FertilizerCalculationException;
import com.myfarmnow.myfarmcrop.singletons.CropFertilizerCalculator;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;

public class CropFertilizerCalculatorResults extends AppCompatActivity {

    TextView cropTextView, areaTextView, totalAmountTextView, npkFertilizerTextView,nitrogenousFertilizerTextView,
    potassicFertilizerTextView, npkQuantityTextView, potassicQtyTextView,nitrogenousQtyTextView,
            npkriceTextView, potassicPriceTextView,nitrogenousPriceTextView,potassicCostTextView,npkCostTextView,nitrogenousCostTextView,
            quantityTextView,unitPriceTextView,totalCostTextView;

    TableRow npkRow,potassicRow,nitrogenousRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_fertilizer_calculator_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
    }

    public void initializeViews(){
        quantityTextView = findViewById(R.id.txt_crop_fertilizer_calculator_quantity);
        unitPriceTextView = findViewById(R.id.txt_crop_fertilizer_calculator_unit_price);
        totalCostTextView = findViewById(R.id.txt_crop_fertilizer_calculator_total_cost);

        areaTextView = findViewById(R.id.txt_crop_fertililizer_calculator_area);
        cropTextView = findViewById(R.id.txt_crop_fertililizer_calculator_crop);
        totalAmountTextView = findViewById(R.id.txt_crop_fertililizer_calculator_total);
        npkFertilizerTextView = findViewById(R.id.text_view_crop_fert_calc_npk_fertilizer);
        nitrogenousFertilizerTextView = findViewById(R.id.text_view_crop_fert_calc_nitogenous_fertilizer);
        potassicFertilizerTextView = findViewById(R.id.text_view_crop_fert_calc_potassic_fertilizer);
        npkRow = findViewById(R.id.table_row_crop_fertilizer_npk);
        potassicRow = findViewById(R.id.table_row_crop_fertilizer_potassic);
        nitrogenousRow = findViewById(R.id.table_row_crop_fertilizer_nitrogenous);
        npkQuantityTextView = findViewById(R.id.text_view_crop_fert_calc_npk_qty);
        potassicQtyTextView = findViewById(R.id.text_view_crop_fert_calc_potassic_qty);
        nitrogenousQtyTextView = findViewById(R.id.text_view_crop_fert_calc_nitrogenous_qty);
        npkriceTextView = findViewById(R.id.text_view_crop_fert_calc_npk_price);
        potassicPriceTextView = findViewById(R.id.text_view_crop_fert_calc_potassic_price);
        nitrogenousPriceTextView = findViewById(R.id.text_view_crop_fert_calc_nitrogenous_price);
        npkCostTextView = findViewById(R.id.text_view_crop_fert_calc_npk_cost);
        potassicCostTextView = findViewById(R.id.text_view_crop_fert_calc_potassic_cost);
        nitrogenousCostTextView = findViewById(R.id.text_view_crop_fert_calc_nitrogenous_cost);

        areaTextView.setText(CropFertilizerCalculator.getInstance().getArea()+" "+CropFertilizerCalculator.getInstance().getUnits());
        cropTextView.setText(CropFertilizerCalculator.getInstance().getCrop().getName());

        unitPriceTextView.setText("Unit Price ("+CropSettingsSingleton.getInstance().getCurrency()+")");
        totalCostTextView.setText("Total Cost ("+CropSettingsSingleton.getInstance().getCurrency()+")");


        if(CropFertilizerCalculator.getInstance().getNpkFertilizer() != null){
           npkFertilizerTextView.setText(CropFertilizerCalculator.getInstance().getNpkFertilizer().getFertilizerName());
            try {
                npkQuantityTextView.setText(CropFertilizerCalculator.getInstance().computeNpkQuantity()+"");
                npkriceTextView.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().getNpkPrice()));
                npkCostTextView.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computeNpkCost()));
            } catch (FertilizerCalculationException e) {
                e.printStackTrace();
            }
        }else{
            npkRow.setVisibility(View.GONE);
        }
        if(CropFertilizerCalculator.getInstance().getPotassicFertilizer() != null){
            potassicFertilizerTextView.setText(CropFertilizerCalculator.getInstance().getPotassicFertilizer().getFertilizerName());
            try {
                potassicQtyTextView.setText(CropFertilizerCalculator.getInstance().computePotassicQuantity()+"");
                potassicPriceTextView.setText( NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().getPotassicPrice()));
                potassicCostTextView.setText( NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computePotassicCost()));
            } catch (FertilizerCalculationException e) {
                e.printStackTrace();
            }
        }else{
            potassicRow.setVisibility(View.GONE);
        }
        if(CropFertilizerCalculator.getInstance().getNitrogenousFertilizer() != null){
            nitrogenousFertilizerTextView.setText(CropFertilizerCalculator.getInstance().getNitrogenousFertilizer().getFertilizerName());
            try {
                nitrogenousQtyTextView.setText(CropFertilizerCalculator.getInstance().computeNitrogenousQuantity()+"");
                nitrogenousPriceTextView.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().getNitrogenousPrice()));
                nitrogenousCostTextView.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computeNitrogenousCost()));

            } catch (FertilizerCalculationException e) {
                e.printStackTrace();
            }
        }else{
            nitrogenousRow.setVisibility(View.GONE);
        }
        try {
            totalAmountTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+ NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computeTotalCost()));
        } catch (FertilizerCalculationException e) {
            e.printStackTrace();
        }

    }


}
