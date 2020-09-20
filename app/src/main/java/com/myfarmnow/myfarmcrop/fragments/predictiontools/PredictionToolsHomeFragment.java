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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.FragmentPredictionToolsHomeBinding;


public class PredictionToolsHomeFragment extends Fragment {
    private FragmentPredictionToolsHomeBinding binding;
    private Context context;
    private NavController navController;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_prediction_tools_home, container, false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        initializeDashboard();

    }
        public void initializeDashboard(){



        binding.layoutDashboardRevenueEstimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start step 1

                navController.navigate(R.id.action_predictionToolsHomeFragment_to_revenueEstimatorStep1Fragment);
            }
        });

        binding.layoutDashboardNutrientsloss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //navigate to nutrient
                navController.navigate(R.id.action_predictionToolsHomeFragment_to_nutrientCalculatorEntryFragment);
            }
        });

        binding.layoutDashboardYieldestimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to yield performance
                navController.navigate(R.id.action_predictionToolsHomeFragment_to_cropYieldPerformanceFragment);

            }
        });

        binding.layoutDashboardFertilizercomposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nagivate to fertilizer calculator
                navController.navigate(R.id.action_predictionToolsHomeFragment_to_cropFertilizerCalculatorEntryFragment);
            }
        });




    }
}