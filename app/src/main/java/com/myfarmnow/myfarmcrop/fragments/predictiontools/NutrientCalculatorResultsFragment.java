package com.myfarmnow.myfarmcrop.fragments.predictiontools;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.FragmentNutrientCalculatorResultsBinding;
import com.myfarmnow.myfarmcrop.singletons.CropNutrientsCalculator;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class NutrientCalculatorResultsFragment extends Fragment {
    private Context context;
    private NavController navController;
    private FragmentNutrientCalculatorResultsBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_nutrient_calculator_results, container, false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Nutrient Calculator Results");
        binding.toolbar.setNavigationOnClickListener(view -> navController.popBackStack());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if(!CropNutrientsCalculator.getInstance().isCalculationPossible()){
            requireActivity().finish();
        }
        initializeViews();
    }

    public void initializeViews(){

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        binding.textViewCropNutrientsCalculatorResultsN.setText("N = "+ df.format(CropNutrientsCalculator.getInstance().computeNitrogenLost())+"Kg");
        binding.textViewCropNutrientsCalculatorResultsP.setText("P = "+ df.format(CropNutrientsCalculator.getInstance().computePhosphateLost())+"Kg");
        binding.textViewCropNutrientsCalculatorResultsK.setText("K = "+ df.format(CropNutrientsCalculator.getInstance().computePotassiumLost())+"Kg");

        if(CropNutrientsCalculator.getInstance().isEconomicImpactRequired()){
            binding.textViewCropNutrientsCalculatorResultsNCost.setText("N = "+ CropSettingsSingleton.getInstance().getCurrency()+" " + NumberFormat.getInstance().format(CropNutrientsCalculator.getInstance().computeNitrogenLostValue()));
            binding.textViewCropNutrientsCalculatorResultsPCost.setText("P = "+ (CropSettingsSingleton.getInstance().getCurrency())+" " +NumberFormat.getInstance().format(CropNutrientsCalculator.getInstance().computePhosphorusLostValue()));
            binding.textViewCropNutrientsCalculatorResultsKCost.setText("K = "+ (CropSettingsSingleton.getInstance().getCurrency())+" " +NumberFormat.getInstance().format(CropNutrientsCalculator.getInstance().computePotassiumLostValue()));
            binding.layoutCropNutrientsCalculatorResultsImpact.setVisibility(View.VISIBLE);
        }

    }
}