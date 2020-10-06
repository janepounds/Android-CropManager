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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;

import java.text.NumberFormat;

public class RevenueEstimatorStep5Fragment extends Fragment {
    private Context context;
    private NavController navController;
    private EditText[] numericFields;
    private String currency = "UGX ";

    private Toolbar toolbar;
    private EditText txtCropRoiStep5LandInvestment, txtCropRoiStep5MachineryInvestment, txtCropRoiStep5BuildingInvestment, txtCropRoiStep4CropTotalCapitalInvestment;
    private Button txtCropRoiStep5CropBtnNext, txtCropRoiStep5CropBtnPrevious;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revenue_estimator_step5, container, false);

        toolbar = view.findViewById(R.id.toolbar_revenue_estimator_step5);
        txtCropRoiStep5LandInvestment = view.findViewById(R.id.txt_crop_roi_step_5_land_investment);
        txtCropRoiStep5MachineryInvestment = view.findViewById(R.id.txt_crop_roi_step_5_machinery_investment);
        txtCropRoiStep5BuildingInvestment = view.findViewById(R.id.txt_crop_roi_step_5_building_investment);
        txtCropRoiStep5CropBtnNext = view.findViewById(R.id.txt_crop_roi_step_5_crop_btn_next);
        txtCropRoiStep5CropBtnPrevious = view.findViewById(R.id.txt_crop_roi_step_5_crop_btn_previous);
        txtCropRoiStep4CropTotalCapitalInvestment = view.findViewById(R.id.txt_crop_roi_step_4_crop_total_capital_investment);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Revenue Estimator");

        toolbar.setNavigationOnClickListener(v -> navController.popBackStack());

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        initializeViews();

    }

    public void initializeViews() {
        numericFields = new EditText[]{txtCropRoiStep5LandInvestment, txtCropRoiStep5MachineryInvestment, txtCropRoiStep5BuildingInvestment};

        txtCropRoiStep5CropBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                //navigate to results fragment
                navController.navigate(R.id.action_revenueEstimatorStep5Fragment_to_revenueEstimatorResultsFragment);
            }
        });

        txtCropRoiStep5CropBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();

                //go back
                navController.popBackStack();
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
        for (EditText x : numericFields) {
            x.addTextChangedListener(watcher);
        }
    }

    public void fillViews() {
        txtCropRoiStep5LandInvestment.setText("" + CropROICalculatorSingleton.getInstance().getStep5LandInvestment());
        txtCropRoiStep5MachineryInvestment.setText("" + CropROICalculatorSingleton.getInstance().getStep5MachineryInvestment());
        txtCropRoiStep5BuildingInvestment.setText("" + CropROICalculatorSingleton.getInstance().getStep5BuildingInvestment());
        updateCalculations();
    }

    public void updateCalculations() {
        try {
            CropROICalculatorSingleton.getInstance().setStep5BuildingInvestment(Float.parseFloat(txtCropRoiStep5BuildingInvestment.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep5LandInvestment(Float.parseFloat(txtCropRoiStep5LandInvestment.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep5MachineryInvestment(Float.parseFloat(txtCropRoiStep5MachineryInvestment.getText().toString()));
        } catch (Exception e) {

        }

        txtCropRoiStep4CropTotalCapitalInvestment.setText(currency + NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep5TotalCapitalInvestment()));
    }

    public void validateEntries() {
        for (EditText x : numericFields) {
            if (x.getText().toString().isEmpty()) {
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }
}