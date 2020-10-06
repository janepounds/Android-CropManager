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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;

import java.text.NumberFormat;

public class RevenueEstimatorStep1Fragment extends Fragment {
    private Context context;
    private NavController navController;
    private EditText[] numericFields;
    private String currency = "UGX ";

    private Toolbar toolbar;
    private Spinner spinnerCropRoiStep1YieldUnits;
    private TextView txtCropRo1StepLabelYieldUnits, txtCropRo1StepLabelYieldUnits2, txtCropRo1Step1EstGrossRevenue;
    private EditText txtCropRo1Step1AvgPrice, txtCropRo1StepYield, txtCropRo1StepOtherIncome;
    private Button txtCropRoiStep1CropBtnNext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revenue_estimator_step1, container, false);

        toolbar = view.findViewById(R.id.toolbar_revenue_estimator_step1);
        spinnerCropRoiStep1YieldUnits = view.findViewById(R.id.spinner_crop_roi_step_1_yield_units);
        txtCropRo1StepLabelYieldUnits = view.findViewById(R.id.txt_crop_ro1_step_label_yield_units);
        txtCropRo1StepLabelYieldUnits2 = view.findViewById(R.id.txt_crop_ro1_step_label_yield_units2);
        txtCropRo1Step1AvgPrice = view.findViewById(R.id.txt_crop_ro1_step_1_avg_price);
        txtCropRo1StepYield = view.findViewById(R.id.txt_crop_ro1_step_yield);
        txtCropRo1StepOtherIncome = view.findViewById(R.id.txt_crop_ro1_step_other_income);
        txtCropRoiStep1CropBtnNext = view.findViewById(R.id.txt_crop_roi_step_1_crop_btn_next);
        txtCropRo1Step1EstGrossRevenue = view.findViewById(R.id.txt_crop_ro1_step_1_est_gross_revenue);

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

        spinnerCropRoiStep1YieldUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = "";
                if (position == 0) {
                    selection = "Units";
                    txtCropRo1StepLabelYieldUnits.setText(selection);
                    txtCropRo1StepLabelYieldUnits2.setText("/" + selection);
                    return;
                } else {
                    selection = spinnerCropRoiStep1YieldUnits.getSelectedItem().toString();
                }
                txtCropRo1StepLabelYieldUnits.setText(selection);
                txtCropRo1StepLabelYieldUnits2.setText("/" + selection);
                CropROICalculatorSingleton.getInstance().setStep1YieldUnits(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        numericFields = new EditText[]{txtCropRo1Step1AvgPrice, txtCropRo1StepYield, txtCropRo1StepOtherIncome};

        txtCropRoiStep1CropBtnNext.setOnClickListener(v -> {
            validateEntries();
            //navigation to step 2
            navController.navigate(R.id.action_revenueEstimatorStep1Fragment_to_revenueEstimatorStep2Fragment);
        });

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
//
//        fillViews();
    }


    public void updateCalculations() {

        try {
            CropROICalculatorSingleton.getInstance().setStep1Price(Float.parseFloat(txtCropRo1Step1AvgPrice.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CropROICalculatorSingleton.getInstance().setStep1YieldPerAcre(Float.parseFloat(txtCropRo1StepYield.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            CropROICalculatorSingleton.getInstance().setStep1OtherIncome(Float.parseFloat(txtCropRo1StepOtherIncome.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtCropRo1Step1EstGrossRevenue.setText(currency + NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep1GrossRevenue()));
    }

    public void validateEntries() {
        for (EditText x : numericFields) {
            if (x.getText().toString().isEmpty()) {
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }

    public void fillViews() {
        DashboardActivity.selectSpinnerItemByValue(spinnerCropRoiStep1YieldUnits, CropROICalculatorSingleton.getInstance().getStep1YieldUnits());
        txtCropRo1Step1AvgPrice.setText("" + CropROICalculatorSingleton.getInstance().getStep1Price());
        txtCropRo1StepYield.setText("" + CropROICalculatorSingleton.getInstance().getStep1YieldPerAcre());
        txtCropRo1StepOtherIncome.setText("" + CropROICalculatorSingleton.getInstance().getStep1OtherIncome());
    }
}