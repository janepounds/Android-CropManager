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


public class RevenueEstimatorStep4Fragment extends Fragment {
    private Context context;
    private NavController navController;
    private EditText[] numericFields;
    //    private String currency= CropSettingsSingleton.getInstance().getCurrency()+" ";
    private String currency = "UGX ";

    private Toolbar toolbar;
    private EditText txtCropRoiStep4OwnerAllowanceQuantity, txtCropRoiStep4OwnerAllowanceCost, txtCropRoiStep4CasualEmployeeNumber,
            txtCropRoiStep4CasualEmployeeCost, txtCropRoiStep4CasualEmployeeHours,txtCropRoiStep4CasualEmployeeWeeks,txtCropRoiStep4CasualEmployeeTotal,
            txtCropRoiStep4TotalLabourCosts,txtCropRoiStep4CropTotalExpenses,txtCropRoiStep4SalariedEmployeeQuantity,txtCropRoiStep4SalariedEmployeeCost;
    private Button txtCropRoiStep4CropBtnPrevious,txtCropRoiStep4CropBtnNext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revenue_estimator_step4, container, false);

        toolbar = view.findViewById(R.id.toolbar_revenue_estimator_step4);
        txtCropRoiStep4OwnerAllowanceQuantity = view.findViewById(R.id.txt_crop_roi_step_4_owner_allowance_quantity);
        txtCropRoiStep4OwnerAllowanceCost = view.findViewById(R.id.txt_crop_roi_step_4_owner_allowance_cost);
        txtCropRoiStep4CasualEmployeeNumber = view.findViewById(R.id.txt_crop_roi_step_4_casual_employee_number);
        txtCropRoiStep4CasualEmployeeCost = view.findViewById(R.id.txt_crop_roi_step_4_casual_employee_cost);
        txtCropRoiStep4CasualEmployeeHours = view.findViewById(R.id.txt_crop_roi_step_4_casual_employee_hours);
        txtCropRoiStep4CasualEmployeeWeeks = view.findViewById(R.id.txt_crop_roi_step_4_casual_employee_weeks);
        txtCropRoiStep4CasualEmployeeTotal = view.findViewById(R.id.txt_crop_roi_step_4_casual_employee_total);
        txtCropRoiStep4TotalLabourCosts = view.findViewById(R.id.txt_crop_roi_step_4_total_labour_costs);
        txtCropRoiStep4CropTotalExpenses = view.findViewById(R.id.txt_crop_roi_step_4_crop_total_expenses);
        txtCropRoiStep4CropBtnPrevious = view.findViewById(R.id.txt_crop_roi_step_4_crop_btn_previous);
        txtCropRoiStep4CropBtnNext = view.findViewById(R.id.txt_crop_roi_step_4_crop_btn_next);
        txtCropRoiStep4SalariedEmployeeQuantity = view.findViewById(R.id.txt_crop_roi_step_4_salaried_employee_quantity);
        txtCropRoiStep4SalariedEmployeeCost = view.findViewById(R.id.txt_crop_roi_step_4_salaried_employee_cost);

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

        numericFields = new EditText[]{txtCropRoiStep4OwnerAllowanceQuantity, txtCropRoiStep4OwnerAllowanceCost, txtCropRoiStep4SalariedEmployeeQuantity, txtCropRoiStep4SalariedEmployeeCost, txtCropRoiStep4CasualEmployeeCost,
                txtCropRoiStep4CasualEmployeeNumber, txtCropRoiStep4CasualEmployeeHours, txtCropRoiStep4CasualEmployeeWeeks};

        txtCropRoiStep4CropBtnNext.setOnClickListener(v -> {
            validateEntries();

            //navigate to the next fragment
            navController.navigate(R.id.action_revenueEstimatorStep4Fragment_to_revenueEstimatorStep5Fragment);
        });

        txtCropRoiStep4CropBtnPrevious.setOnClickListener(v -> {
            validateEntries();
            //go back
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

    public void fillViews() {
        txtCropRoiStep4OwnerAllowanceQuantity.setText("" + CropROICalculatorSingleton.getInstance().getStep4OwnerAllowanceQty());
        txtCropRoiStep4OwnerAllowanceCost.setText("" + CropROICalculatorSingleton.getInstance().getStep4OwnerAllowanceCost());
        txtCropRoiStep4SalariedEmployeeQuantity.setText("" + CropROICalculatorSingleton.getInstance().getStep4SalariedEmployeeQty());
        txtCropRoiStep4SalariedEmployeeCost.setText("" + CropROICalculatorSingleton.getInstance().getStep4SalariedEmployeeCost());
        txtCropRoiStep4CasualEmployeeCost.setText("" + CropROICalculatorSingleton.getInstance().getStep4CasualEmployeeCost());
        txtCropRoiStep4CasualEmployeeHours.setText("" + CropROICalculatorSingleton.getInstance().getStep4CasualEmployeeHours());
        txtCropRoiStep4CasualEmployeeWeeks.setText("" + CropROICalculatorSingleton.getInstance().getStep4CasualEmployeeWeeks());
        updateCalculations();
    }

    public void updateCalculations() {
        try {
            CropROICalculatorSingleton.getInstance().setStep4OwnerAllowanceQty(Float.parseFloat(txtCropRoiStep4OwnerAllowanceQuantity.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep4OwnerAllowanceCost(Float.parseFloat(txtCropRoiStep4OwnerAllowanceCost.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep4SalariedEmployeeCost(Float.parseFloat(txtCropRoiStep4SalariedEmployeeCost.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep4SalariedEmployeeQty(Float.parseFloat(txtCropRoiStep4SalariedEmployeeQuantity.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeCost(Float.parseFloat(txtCropRoiStep4CasualEmployeeCost.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeNumber(Float.parseFloat(txtCropRoiStep4CasualEmployeeNumber.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeHours(Float.parseFloat(txtCropRoiStep4CasualEmployeeHours.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeWeeks(Float.parseFloat(txtCropRoiStep4CasualEmployeeWeeks.getText().toString()));
        } catch (Exception e) {

        }
        txtCropRoiStep4CropTotalExpenses.setText(currency + NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep4TotalExpenses()));
        txtCropRoiStep4CasualEmployeeTotal.setText(currency + NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep4TotalCasualEmployeeCost()));
        txtCropRoiStep4TotalLabourCosts.setText(currency + NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep4TotalLabourCosts()));
    }

    public void validateEntries() {
        for (EditText x : numericFields) {
            if (x.getText().toString().isEmpty()) {
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }
}