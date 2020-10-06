package com.myfarmnow.myfarmcrop.fragments.predictiontools;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.FertilizerCalculationException;
import com.myfarmnow.myfarmcrop.singletons.CropFertilizerCalculator;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;

public class CropFertilizerCalculatorResultsFragment extends Fragment {
    private Context context;
    private NavController navController;
    private TableRow npkRow, potassicRow, nitrogenousRow;

    private Toolbar toolbar;
    private TextView txtCropFertilizerCalculatorArea, txtCropFertilizerCalculatorCrop, txtCropFertilizerCalculatorUnitPrice, txtCropFertilizerCalculatorTotalCost,
            textViewCropFertCalcNpkFertilizer, textViewCropFertCalcNpkQty, textViewCropFertCalcNpkPrice, textViewCropFertCalcNpkCost,
            textViewCropFertCalcPotassicFertilizer, textViewCropFertCalcPotassicQty, textViewCropFertCalcPotassicPrice, textViewCropFertCalcPotassicCost,
            textViewCropFertCalcNitogenousFertilizer, textViewCropFertCalcNitrogenousQty, textViewCropFertCalcNitrogenousPrice, textViewCropFertCalcNitrogenousCost;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop_fertilizer_calculator_results, container, false);

        toolbar = view.findViewById(R.id.toolbar_crop_fertilizer_calculator_result);
        txtCropFertilizerCalculatorArea = view.findViewById(R.id.txt_crop_fertilizer_calculator_area);
        txtCropFertilizerCalculatorCrop = view.findViewById(R.id.txt_crop_fertilizer_calculator_crop);
        txtCropFertilizerCalculatorUnitPrice = view.findViewById(R.id.txt_crop_fertilizer_calculator_unit_price);
        txtCropFertilizerCalculatorTotalCost = view.findViewById(R.id.txt_crop_fertilizer_calculator_total_cost);
        textViewCropFertCalcNpkFertilizer = view.findViewById(R.id.text_view_crop_fert_calc_npk_fertilizer);
        textViewCropFertCalcNpkQty = view.findViewById(R.id.text_view_crop_fert_calc_npk_qty);
        textViewCropFertCalcNpkPrice = view.findViewById(R.id.text_view_crop_fert_calc_npk_price);
        textViewCropFertCalcNpkCost = view.findViewById(R.id.text_view_crop_fert_calc_npk_cost);
        textViewCropFertCalcPotassicFertilizer = view.findViewById(R.id.text_view_crop_fert_calc_potassic_fertilizer);
        textViewCropFertCalcPotassicQty = view.findViewById(R.id.text_view_crop_fert_calc_potassic_qty);
        textViewCropFertCalcPotassicPrice = view.findViewById(R.id.text_view_crop_fert_calc_potassic_price);
        textViewCropFertCalcPotassicCost = view.findViewById(R.id.text_view_crop_fert_calc_potassic_cost);
        textViewCropFertCalcNitogenousFertilizer = view.findViewById(R.id.text_view_crop_fert_calc_nitogenous_fertilizer);
        textViewCropFertCalcNitrogenousQty = view.findViewById(R.id.text_view_crop_fert_calc_nitrogenous_qty);
        textViewCropFertCalcNitrogenousPrice = view.findViewById(R.id.text_view_crop_fert_calc_nitrogenous_price);
        textViewCropFertCalcNitrogenousCost = view.findViewById(R.id.text_view_crop_fert_calc_nitrogenous_cost);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Crop Fertilizer Calculator Results");

        toolbar.setNavigationOnClickListener(v -> navController.popBackStack());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initializeViews();
    }

    public void initializeViews() {

        txtCropFertilizerCalculatorArea.setText(CropFertilizerCalculator.getInstance().getArea() + " " + CropFertilizerCalculator.getInstance().getUnits());
        txtCropFertilizerCalculatorCrop.setText(CropFertilizerCalculator.getInstance().getCrop().getName());

        txtCropFertilizerCalculatorUnitPrice.setText("Unit Price (" + CropSettingsSingleton.getInstance().getCurrency() + ")");
        txtCropFertilizerCalculatorTotalCost.setText("Total Cost (" + CropSettingsSingleton.getInstance().getCurrency() + ")");

        if (CropFertilizerCalculator.getInstance().getNpkFertilizer() != null) {
            textViewCropFertCalcNpkFertilizer.setText(CropFertilizerCalculator.getInstance().getNpkFertilizer().getFertilizerName());
            try {
                textViewCropFertCalcNpkQty.setText(CropFertilizerCalculator.getInstance().computeNpkQuantity() + "");
                textViewCropFertCalcNpkPrice.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().getNpkPrice()));
                textViewCropFertCalcNpkCost.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computeNpkCost()));
            } catch (FertilizerCalculationException e) {
                e.printStackTrace();
            }
        } else {
            npkRow.setVisibility(View.GONE);
        }

        if (CropFertilizerCalculator.getInstance().getPotassicFertilizer() != null) {
            textViewCropFertCalcPotassicFertilizer.setText(CropFertilizerCalculator.getInstance().getPotassicFertilizer().getFertilizerName());
            try {
                textViewCropFertCalcPotassicQty.setText(CropFertilizerCalculator.getInstance().computePotassicQuantity() + "");
                textViewCropFertCalcPotassicPrice.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().getPotassicPrice()));
                textViewCropFertCalcPotassicCost.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computePotassicCost()));
            } catch (FertilizerCalculationException e) {
                e.printStackTrace();
            }
        } else {
            potassicRow.setVisibility(View.GONE);
        }

        if (CropFertilizerCalculator.getInstance().getNitrogenousFertilizer() != null) {
            textViewCropFertCalcNitogenousFertilizer.setText(CropFertilizerCalculator.getInstance().getNitrogenousFertilizer().getFertilizerName());
            try {
                textViewCropFertCalcNitrogenousQty.setText(CropFertilizerCalculator.getInstance().computeNitrogenousQuantity() + "");
                textViewCropFertCalcNitrogenousPrice.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().getNitrogenousPrice()));
                textViewCropFertCalcNitrogenousCost.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computeNitrogenousCost()));

            } catch (FertilizerCalculationException e) {
                e.printStackTrace();
            }
        } else {
            nitrogenousRow.setVisibility(View.GONE);
        }
        try {
            txtCropFertilizerCalculatorTotalCost.setText(CropSettingsSingleton.getInstance().getCurrency() + " " + NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computeTotalCost()));
        } catch (FertilizerCalculationException e) {
            e.printStackTrace();
        }
    }
}