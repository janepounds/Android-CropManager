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

import com.myfarmnow.myfarmcrop.R;


public class PredictionToolsHomeFragment extends Fragment {
    private Context context;
    private NavController navController;

    private Toolbar toolbar;
    private LinearLayout layoutDashboardRevenueEstimation, layoutDashboardYieldEstimation, layoutDashboardNutrientLoss, layoutDashboardFertilizerComposition;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prediction_tools_home, container, false);

        toolbar = view.findViewById(R.id.toolbar_prediction_tools_home);
        layoutDashboardRevenueEstimation = view.findViewById(R.id.layout_dashboard_revenue_estimation);
        layoutDashboardYieldEstimation = view.findViewById(R.id.layout_dashboard_yield_estimation);
        layoutDashboardNutrientLoss = view.findViewById(R.id.layout_dashboard_nutrient_loss);
        layoutDashboardFertilizerComposition = view.findViewById(R.id.layout_dashboard_fertilizer_composition);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        initializeDashboard();

    }

    public void initializeDashboard() {


        layoutDashboardRevenueEstimation.setOnClickListener((View.OnClickListener) v -> {
            //start step 1
            navController.navigate(R.id.action_predictionToolsHomeFragment_to_revenueEstimatorStep1Fragment);
        });

        layoutDashboardNutrientLoss.setOnClickListener((View.OnClickListener) v -> {
            //navigate to nutrient
            navController.navigate(R.id.action_predictionToolsHomeFragment_to_nutrientCalculatorEntryFragment);
        });

        layoutDashboardYieldEstimation.setOnClickListener((View.OnClickListener) v -> {
            //navigate to yield performance
            navController.navigate(R.id.action_predictionToolsHomeFragment_to_cropYieldPerformanceFragment);

        });

        layoutDashboardFertilizerComposition.setOnClickListener((View.OnClickListener) v -> {
            // Navigate to fertilizer calculator
            navController.navigate(R.id.action_predictionToolsHomeFragment_to_cropFertilizerCalculatorEntryFragment);
        });


    }
}