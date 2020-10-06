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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.singletons.CropNutrientsCalculator;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NutrientCalculatorResultsFragment extends Fragment {
    private Context context;
    private NavController navController;

    private Toolbar toolbar;
    private TextView textViewCropNutrientsCalculatorResultsN, textViewCropNutrientsCalculatorResultsP, textViewCropNutrientsCalculatorResultsK,
            textViewCropNutrientsCalculatorResultsNCost, textViewCropNutrientsCalculatorResultsPCost, textViewCropNutrientsCalculatorResultsKCost;
    private LinearLayout layoutCropNutrientsCalculatorResultsImpact;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nutrient_calculator_results, container, false);

        toolbar = view.findViewById(R.id.toolbar_nutrient_calculator_results);
        textViewCropNutrientsCalculatorResultsN = view.findViewById(R.id.text_view_crop_nutrients_calculator_results_n);
        textViewCropNutrientsCalculatorResultsP = view.findViewById(R.id.text_view_crop_nutrients_calculator_results_p);
        textViewCropNutrientsCalculatorResultsK = view.findViewById(R.id.text_view_crop_nutrients_calculator_results_k);
        textViewCropNutrientsCalculatorResultsNCost = view.findViewById(R.id.text_view_crop_nutrients_calculator_results_n_cost);
        textViewCropNutrientsCalculatorResultsPCost = view.findViewById(R.id.text_view_crop_nutrients_calculator_results_p_cost);
        textViewCropNutrientsCalculatorResultsKCost = view.findViewById(R.id.text_view_crop_nutrients_calculator_results_k_cost);
        layoutCropNutrientsCalculatorResultsImpact = view.findViewById(R.id.layout_crop_nutrients_calculator_results_impact);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Nutrient Calculator Results");

        toolbar.setNavigationOnClickListener(v -> navController.popBackStack());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if (!CropNutrientsCalculator.getInstance().isCalculationPossible()) {
            requireActivity().finish();
        }
        initializeViews();
    }

    public void initializeViews() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        textViewCropNutrientsCalculatorResultsN.setText("N = " + df.format(CropNutrientsCalculator.getInstance().computeNitrogenLost()) + "Kg");
        textViewCropNutrientsCalculatorResultsP.setText("P = " + df.format(CropNutrientsCalculator.getInstance().computePhosphateLost()) + "Kg");
        textViewCropNutrientsCalculatorResultsK.setText("K = " + df.format(CropNutrientsCalculator.getInstance().computePotassiumLost()) + "Kg");

        if (CropNutrientsCalculator.getInstance().isEconomicImpactRequired()) {
            textViewCropNutrientsCalculatorResultsNCost.setText("N = " + CropSettingsSingleton.getInstance().getCurrency() + " " + NumberFormat.getInstance().format(CropNutrientsCalculator.getInstance().computeNitrogenLostValue()));
            textViewCropNutrientsCalculatorResultsPCost.setText("P = " + (CropSettingsSingleton.getInstance().getCurrency()) + " " + NumberFormat.getInstance().format(CropNutrientsCalculator.getInstance().computePhosphorusLostValue()));
            textViewCropNutrientsCalculatorResultsKCost.setText("K = " + (CropSettingsSingleton.getInstance().getCurrency()) + " " + NumberFormat.getInstance().format(CropNutrientsCalculator.getInstance().computePotassiumLostValue()));
            layoutCropNutrientsCalculatorResultsImpact.setVisibility(View.VISIBLE);
        }

    }
}