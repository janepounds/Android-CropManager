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
import com.myfarmnow.myfarmcrop.databinding.FragmentRevenueEstimatorStep4Binding;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;


public class RevenueEstimatorStep4Fragment extends Fragment {
    private Context context;
    private NavController navController;
    private FragmentRevenueEstimatorStep4Binding binding;
    private EditText [] numericFields ;
//    private String currency= CropSettingsSingleton.getInstance().getCurrency()+" ";
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_revenue_estimator_step4, container, false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Revenue Estimator");
        binding.toolbar.setNavigationOnClickListener(view -> navController.popBackStack());
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        initializeViews();

    }


    public void initializeViews(){

        numericFields =new EditText[]{binding.txtCropRoiStep4OwnerAllowanceQuantity, binding.txtCropRoiStep4OwnerAllowanceCost,binding.txtCropRoiStep4SalariedEmployeeQuantity, binding.txtCropRoiStep4SalariedEmployeeCost , binding.txtCropRoiStep4CasualEmployeeCost,
                binding.txtCropRoiStep4CasualEmployeeNumber,binding.txtCropRoiStep4CasualEmployeeHours,binding.txtCropRoiStep4CasualEmployeeWeeks};
        binding.txtCropRoiStep4CropBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();

                //navigate to the next fragment
                navController.navigate(R.id.action_revenueEstimatorStep4Fragment_to_revenueEstimatorStep5Fragment);
            }
        });

        binding.txtCropRoiStep4CropBtnPrevious.setOnClickListener(new View.OnClickListener() {
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
        for(EditText x : numericFields){
            x.addTextChangedListener(watcher);
        }


    }
    public void fillViews(){
        binding.txtCropRoiStep4OwnerAllowanceQuantity.setText(""+ CropROICalculatorSingleton.getInstance().getStep4OwnerAllowanceQty());
        binding.txtCropRoiStep4OwnerAllowanceCost.setText(""+CropROICalculatorSingleton.getInstance().getStep4OwnerAllowanceCost());
        binding.txtCropRoiStep4SalariedEmployeeQuantity.setText(""+CropROICalculatorSingleton.getInstance().getStep4SalariedEmployeeQty());
        binding.txtCropRoiStep4SalariedEmployeeCost.setText(""+CropROICalculatorSingleton.getInstance().getStep4SalariedEmployeeCost());
        binding.txtCropRoiStep4CasualEmployeeCost.setText(""+CropROICalculatorSingleton.getInstance().getStep4CasualEmployeeCost());
        binding.txtCropRoiStep4CasualEmployeeHours.setText(""+CropROICalculatorSingleton.getInstance().getStep4CasualEmployeeHours());
        binding.txtCropRoiStep4CasualEmployeeWeeks.setText(""+CropROICalculatorSingleton.getInstance().getStep4CasualEmployeeWeeks());
        updateCalculations();
    }
    public void updateCalculations(){
        try{
            CropROICalculatorSingleton.getInstance().setStep4OwnerAllowanceQty( Float.parseFloat(binding.txtCropRoiStep4OwnerAllowanceQuantity.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4OwnerAllowanceCost(Float.parseFloat(binding.txtCropRoiStep4OwnerAllowanceCost.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4SalariedEmployeeCost(Float.parseFloat(binding.txtCropRoiStep4SalariedEmployeeCost.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4SalariedEmployeeQty(Float.parseFloat(binding.txtCropRoiStep4SalariedEmployeeQuantity.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeCost(Float.parseFloat(binding.txtCropRoiStep4CasualEmployeeCost.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeNumber(Float.parseFloat(binding.txtCropRoiStep4CasualEmployeeNumber.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeHours(Float.parseFloat(binding.txtCropRoiStep4CasualEmployeeHours.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep4CasualEmployeeWeeks(Float.parseFloat(binding.txtCropRoiStep4CasualEmployeeWeeks.getText().toString()));
        }catch (Exception e){

        }
        binding.txtCropRoiStep4CropTotalExpenses.setText(currency+ NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep4TotalExpenses()));
        binding.txtCropRoiStep4CasualEmployeeTotal.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep4TotalCasualEmployeeCost()));
        binding.txtCropRoiStep4TotalLabourCosts.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep4TotalLabourCosts()));
    }
    public void validateEntries(){
        for(EditText x : numericFields){
            if(x.getText().toString().isEmpty()){
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }
}