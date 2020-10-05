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
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;

import java.text.NumberFormat;

public class RevenueEstimatorStep3Fragment extends Fragment {
    private Context context;
    private NavController navController;
    private EditText[] numericFields;
    private String currency = "UGX ";

    private Toolbar toolbar;
    private EditText txtCropRoiStep3BuildingRepair, txtCropRoiStep3Power, txtCropRoiStep3PropertyTaxes, txtCropRoiStep3TotalOtherCosts;
    private Button txtCropRoiStep3CropBtnNext, txtCropRoiStep3CropBtnPrevious;
    private TextView txtCropRoiStep3TotalOverHeadCosts;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revenue_estimator_step3, container, false);

        toolbar = view.findViewById(R.id.toolbar_revenue_estimator_step3);
        txtCropRoiStep3BuildingRepair = view.findViewById(R.id.txt_crop_roi_step_3_building_repair);
        txtCropRoiStep3Power = view.findViewById(R.id.txt_crop_roi_step_3_power);
        txtCropRoiStep3PropertyTaxes = view.findViewById(R.id.txt_crop_roi_step_3_property_taxes);
        txtCropRoiStep3TotalOtherCosts = view.findViewById(R.id.txt_crop_roi_step_3_total_other_costs);
        txtCropRoiStep3CropBtnNext = view.findViewById(R.id.txt_crop_roi_step_3_crop_btn_next);
        txtCropRoiStep3CropBtnPrevious = view.findViewById(R.id.txt_crop_roi_step_3_crop_btn_previous);
        txtCropRoiStep3TotalOverHeadCosts = view.findViewById(R.id.txt_crop_roi_step_3_total_over_head_costs);

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

        numericFields = new EditText[]{
                txtCropRoiStep3BuildingRepair, txtCropRoiStep3Power, txtCropRoiStep3PropertyTaxes, txtCropRoiStep3TotalOtherCosts
        };

        txtCropRoiStep3CropBtnNext.setOnClickListener(v -> {
            validateEntries();
            //navigate to step 4
            navController.navigate(R.id.action_revenueEstimatorStep3Fragment_to_revenueEstimatorStep4Fragment);
        });

        txtCropRoiStep3CropBtnPrevious.setOnClickListener(v -> {
            validateEntries();
            //pop back
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

        fillViews();
    }

    public void updateCalculations() {
        /*
         , ,propertyTaxesTxt,businessOverheadTxt,buildingDepreciationTxt,
                otherCostsTxt
         */
        try {
            CropROICalculatorSingleton.getInstance().setStep3BuildingRepair(Float.parseFloat(txtCropRoiStep3BuildingRepair.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep3machineDepreciation(Float.parseFloat(txtCropRoiStep3Power.getText().toString()));
        } catch (Exception e) {

        }
        try {
            CropROICalculatorSingleton.getInstance().setStep3PropertyTaxes(Float.parseFloat(txtCropRoiStep3PropertyTaxes.getText().toString()));
        } catch (Exception e) {

        }

        try {
            CropROICalculatorSingleton.getInstance().setStep3TotalOtherExpenses(Float.parseFloat(txtCropRoiStep3TotalOtherCosts.getText().toString()));
        } catch (Exception e) {

        }
        txtCropRoiStep3TotalOverHeadCosts.setText(currency + NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep3TotalOverheadCosts()));
    }

    public void fillViews() {
        txtCropRoiStep3Power.setText("" + CropROICalculatorSingleton.getInstance().getStep3BuildingDepreciation());
        txtCropRoiStep3BuildingRepair.setText("" + CropROICalculatorSingleton.getInstance().getStep3BuildingRepair());
        txtCropRoiStep3Power.setText("" + CropROICalculatorSingleton.getInstance().getStep3machineDepreciation());
        txtCropRoiStep3PropertyTaxes.setText("" + CropROICalculatorSingleton.getInstance().getStep3PropertyTaxes());
        txtCropRoiStep3TotalOtherCosts.setText("" + CropROICalculatorSingleton.getInstance().getStep3TotalOtherExpenses());
        updateCalculations();
    }

    public void validateEntries() {
        for (EditText x : numericFields) {
            if (x.getText().toString().isEmpty()) {
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }
}