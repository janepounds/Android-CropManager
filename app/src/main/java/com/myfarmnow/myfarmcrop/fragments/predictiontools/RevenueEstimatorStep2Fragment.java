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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;

public class RevenueEstimatorStep2Fragment extends Fragment {
    private Context context;
    private NavController navController;
    private EditText[] numericFields;
    private String currency = "UGX ";

    private Toolbar toolbar;
    private Button step2CropBtnNext, step2CropBtnPrevious;
    private EditText yieldPerArea, step2Fertilizer, step2Pesticides, step2OtherCosts;
    private TextView totalVariableCost;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revenue_estimator_step2, container, false);

        toolbar = view.findViewById(R.id.toolbar_revenue_estimator_step2);
        step2CropBtnNext = view.findViewById(R.id.txt_crop_roi_step_2_crop_btn_next);
        step2CropBtnPrevious = view.findViewById(R.id.txt_crop_roi_step_2_crop_btn_previous);
        yieldPerArea = view.findViewById(R.id.txt_crop_roi_step_2_yeildperarea);
        step2Fertilizer = view.findViewById(R.id.txt_crop_roi_step_2_fertilizer);
        step2Pesticides = view.findViewById(R.id.txt_crop_roi_step_2_pesticides);
        step2OtherCosts = view.findViewById(R.id.txt_crop_roi_step_2_othercosts);
        totalVariableCost = view.findViewById(R.id.txt_crop_roi_step_2_crop_total_variable_cost);

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

        numericFields = new EditText[]{yieldPerArea, step2Fertilizer, step2Pesticides, step2OtherCosts};

        step2CropBtnNext.setOnClickListener(v -> {
            validateEntries();

            Log.d("Calculator 1", CropROICalculatorSingleton.getInstance() + "");
            //navigate to step 3
            navController.navigate(R.id.action_revenueEstimatorStep2Fragment_to_revenueEstimatorStep3Fragment);
        });

        step2CropBtnPrevious.setOnClickListener(v -> {
            validateEntries();
            Log.d("Calculator 2", CropROICalculatorSingleton.getInstance() + "");

            //pop back stack to step 1
            navController.popBackStack();
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

    public void updateCalculations() {
        try {
            CropROICalculatorSingleton.getInstance().setStep2SeedAndTreatment(Float.parseFloat(yieldPerArea.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep2Fertilizer(Float.parseFloat(step2Fertilizer.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep2Herbicide(Float.parseFloat(step2Pesticides.getText().toString()));
        } catch (Exception e) {

        }

        try {
            CropROICalculatorSingleton.getInstance().setStep2OtherCosts(Float.parseFloat(step2OtherCosts.getText().toString()));
        } catch (Exception e) {

        }

        totalVariableCost.setText(NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep2TotalVariableCosts()));
    }

    //
    public void validateEntries() {
        for (EditText x : numericFields) {
            if (x.getText().toString().isEmpty()) {
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }

    public void fillViews() {
        yieldPerArea.setText("" + CropROICalculatorSingleton.getInstance().getStep2SeedAndTreatment());
        step2Fertilizer.setText("" + CropROICalculatorSingleton.getInstance().getStep2Fertilizer());
        step2Pesticides.setText("" + CropROICalculatorSingleton.getInstance().getStep2Herbicide());
        step2OtherCosts.setText("" + CropROICalculatorSingleton.getInstance().getStep2OtherCosts());
        updateCalculations();
    }
}