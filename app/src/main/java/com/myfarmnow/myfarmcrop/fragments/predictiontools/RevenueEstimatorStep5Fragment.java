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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.FragmentRevenueEstimatorStep5Binding;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;

import java.text.NumberFormat;


public class RevenueEstimatorStep5Fragment extends Fragment {
    private Context context;
    private NavController navController;
    private FragmentRevenueEstimatorStep5Binding binding;
//    private String currency= CropSettingsSingleton.getInstance().getCurrency()+" ";
    private  EditText [] numericFields ;
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_revenue_estimator_step5, container, false);
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

        numericFields =new EditText[]{binding.txtCropRoiStep5LandInvestment, binding.txtCropRoiStep5MachineryInvestment,binding.txtCropRoiStep5BuildingInvestment};
        binding.txtCropRoiStep5CropBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                //navigate to results fragment
                navController.navigate(R.id.action_revenueEstimatorStep5Fragment_to_revenueEstimatorResultsFragment);
            }
        });
        binding.txtCropRoiStep5CropBtnPrevious.setOnClickListener(new View.OnClickListener() {
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
        binding.txtCropRoiStep5LandInvestment.setText(""+ CropROICalculatorSingleton.getInstance().getStep5LandInvestment());
        binding.txtCropRoiStep5MachineryInvestment.setText(""+CropROICalculatorSingleton.getInstance().getStep5MachineryInvestment());
        binding.txtCropRoiStep5BuildingInvestment.setText(""+CropROICalculatorSingleton.getInstance().getStep5BuildingInvestment());
        updateCalculations();
    }
    public void updateCalculations(){
        try{
            CropROICalculatorSingleton.getInstance().setStep5BuildingInvestment( Float.parseFloat(binding.txtCropRoiStep5BuildingInvestment.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep5LandInvestment(Float.parseFloat(binding.txtCropRoiStep5LandInvestment.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep5MachineryInvestment(Float.parseFloat(binding.txtCropRoiStep5MachineryInvestment.getText().toString()));
        }catch (Exception e){

        }

        binding.txtCropRoiStep4CropTotalCapitalInvestment.setText(currency+ NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep5TotalCapitalInvestment()));
    }
    public void validateEntries(){
        for(EditText x : numericFields){
            if(x.getText().toString().isEmpty()){
                x.setText(getString(R.string.default_numeric_value));
            }
        }
    }
}