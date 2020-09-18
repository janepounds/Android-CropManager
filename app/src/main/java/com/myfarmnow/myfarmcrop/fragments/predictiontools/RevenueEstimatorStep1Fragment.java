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
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.predictiontools.CropROIStep1Activity;
import com.myfarmnow.myfarmcrop.activities.predictiontools.CropROIStep2Activity;
import com.myfarmnow.myfarmcrop.databinding.FragmentRevenueEstimatorStep1Binding;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;

import java.text.NumberFormat;


public class RevenueEstimatorStep1Fragment extends Fragment {
private FragmentRevenueEstimatorStep1Binding binding;
private Context context;
private NavController navController;
private  EditText [] numericFields;
private  String currency="UGX ";


    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_revenue_estimator_step1,container,false);
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


        binding.spinnerCropRoiStep1YieldUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection ="";
                if(position==0){
                    selection ="Units";
                    binding.txtCropRo1StepLabelYieldUnits.setText(selection);
                    binding.txtCropRo1StepLabelYieldUnits2.setText("/"+selection);
                    return;
                }
                else {
                    selection=binding.spinnerCropRoiStep1YieldUnits.getSelectedItem().toString();
                }
                binding.txtCropRo1StepLabelYieldUnits.setText(selection);
                binding.txtCropRo1StepLabelYieldUnits2.setText("/"+selection);
                CropROICalculatorSingleton.getInstance().setStep1YieldUnits(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numericFields =new EditText[]{binding.txtCropRo1Step1AvgPrice, binding.txtCropRo1StepYield, binding.txtCropRo1StepOtherIncome};
        binding.txtCropRoiStep1CropBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                //navigation to step 2
                navController.navigate(R.id.action_revenueEstimatorStep1Fragment_to_revenueEstimatorStep2Fragment);

            }
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

        for(EditText x : numericFields){
            x.addTextChangedListener(watcher);
        }
//
//        fillViews();
    }


    public void updateCalculations(){

        try{
            CropROICalculatorSingleton.getInstance().setStep1Price( Float.parseFloat(binding.txtCropRo1Step1AvgPrice.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep1YieldPerAcre(Float.parseFloat(binding.txtCropRo1StepYield.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep1OtherIncome(Float.parseFloat(binding.txtCropRo1StepOtherIncome.getText().toString()));
        }catch (Exception e){

        }
        binding.txtCropRo1Step1EstGrossRevenue.setText(currency+ NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep1GrossRevenue()));
    }

    //
    public void validateEntries(){
        for(EditText x : numericFields){
            if(x.getText().toString().isEmpty()){
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }

    public void fillViews(){
        DashboardActivity.selectSpinnerItemByValue(binding.spinnerCropRoiStep1YieldUnits,CropROICalculatorSingleton.getInstance().getStep1YieldUnits());
        binding.txtCropRo1Step1AvgPrice.setText(""+CropROICalculatorSingleton.getInstance().getStep1Price());
        binding.txtCropRo1StepYield.setText(""+CropROICalculatorSingleton.getInstance().getStep1YieldPerAcre());
        binding.txtCropRo1StepOtherIncome.setText(""+CropROICalculatorSingleton.getInstance().getStep1OtherIncome());
    }
}