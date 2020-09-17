package com.myfarmnow.myfarmcrop.fragments.predictiontools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.FragmentRevenueEstimatorStep3Binding;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;

import java.text.NumberFormat;


public class RevenueEstimatorStep3Fragment extends Fragment {
    private FragmentRevenueEstimatorStep3Binding binding;
    private Context context;
    private NavController navController;
    private   EditText[] numericFields ;
    private String currency="UGX ";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_revenue_estimator_step3,container,false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Revenue Estimator");
        binding.toolbar.setNavigationOnClickListener(view -> navController.popBackStack());
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        initializeViews();

    }

    public void initializeViews(){

        numericFields = new EditText[]{
                binding.txtCropRoiStep3BuildingRepair, binding.txtCropRoiStep3Power,binding.txtCropRoiStep3PropertyTaxes,binding.txtCropRoiStep3TotalOtherCosts
        };

        binding.txtCropRoiStep3CropBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();


                //navigate to step 4
                navController.navigate(R.id.action_revenueEstimatorStep3Fragment_to_revenueEstimatorStep4Fragment);
            }
        });

        binding.txtCropRoiStep3CropBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                //pop back
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

        for(EditText x : numericFields){
            x.addTextChangedListener(watcher);
        }

        fillViews();
    }
    public void updateCalculations(){
        /*
         , ,propertyTaxesTxt,businessOverheadTxt,buildingDepreciationTxt,
                otherCostsTxt
         */
        try{
            CropROICalculatorSingleton.getInstance().setStep3BuildingRepair( Float.parseFloat(binding.txtCropRoiStep3BuildingRepair.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep3machineDepreciation(Float.parseFloat(binding.txtCropRoiStep3Power.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep3PropertyTaxes(Float.parseFloat(binding.txtCropRoiStep3PropertyTaxes.getText().toString()));
        }catch (Exception e){

        }

        try{
            CropROICalculatorSingleton.getInstance().setStep3TotalOtherExpenses(Float.parseFloat(binding.txtCropRoiStep3TotalOtherCosts.getText().toString()));
        }catch (Exception e){

        }
        binding.txtCropRo1Step3TotalOverheadCosts.setText(currency+ NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep3TotalOverheadCosts()));
    }
    public void fillViews(){
        binding.txtCropRoiStep3Power.setText(""+CropROICalculatorSingleton.getInstance().getStep3BuildingDepreciation());
        binding.txtCropRoiStep3BuildingRepair.setText(""+CropROICalculatorSingleton.getInstance().getStep3BuildingRepair());
        binding.txtCropRoiStep3Power.setText(""+CropROICalculatorSingleton.getInstance().getStep3machineDepreciation());
        binding.txtCropRoiStep3PropertyTaxes.setText(""+CropROICalculatorSingleton.getInstance().getStep3PropertyTaxes());
        binding.txtCropRoiStep3TotalOtherCosts.setText(""+CropROICalculatorSingleton.getInstance().getStep3TotalOtherExpenses());
        updateCalculations();
    }
    public void validateEntries(){
        for(EditText x : numericFields){
            if(x.getText().toString().isEmpty()){
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }
}