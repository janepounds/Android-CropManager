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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.FragmentRevenueEstimatorStep2Binding;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;

import java.text.NumberFormat;


public class RevenueEstimatorStep2Fragment extends Fragment {
    private Context context;
    private FragmentRevenueEstimatorStep2Binding binding;
    private NavController navController;
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_revenue_estimator_step2,container,false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(view -> navController.popBackStack());
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        initializeViews();



    }

    public void initializeViews(){

        numericFields =new EditText[]{binding.txtCropRoiStep2Yeildperarea, binding.txtCropRoiStep2Fertilizer, binding.txtCropRoiStep2Pesticides, binding.txtCropRoiStep2Othercosts};
        binding.txtCropRoiStep2CropBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                requireActivity().finish();
                Log.d("Calculator 1", CropROICalculatorSingleton.getInstance()+"");
                //navigate to step 3
//                startActivity(new Intent(CropROIStep2Activity.this, CropROIStep3Activity.class));
            }
        });
        binding.txtCropRoiStep2CropBtnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateEntries();
                requireActivity().finish();
                Log.d("Calculator 2",CropROICalculatorSingleton.getInstance()+"");

               //popback stack to step 1
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

    public void updateCalculations(){
        try{
            CropROICalculatorSingleton.getInstance().setStep2SeedAndTreatment( Float.parseFloat(binding.txtCropRoiStep2Yeildperarea.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2Fertilizer(Float.parseFloat(binding.txtCropRoiStep2Fertilizer.getText().toString()));
        }catch (Exception e){

        }
        try{
            CropROICalculatorSingleton.getInstance().setStep2Herbicide(Float.parseFloat(binding.txtCropRoiStep2Pesticides.getText().toString()));
        }catch (Exception e){

        }

        try{
            CropROICalculatorSingleton.getInstance().setStep2OtherCosts(Float.parseFloat(binding.txtCropRoiStep2Othercosts.getText().toString()));
        }catch (Exception e){

        }

        binding.txtCropRoiStep2CropTotalVariableCost.setText(NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeStep2TotalVariableCosts()));
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

        binding.txtCropRoiStep2Yeildperarea.setText(""+CropROICalculatorSingleton.getInstance().getStep2SeedAndTreatment());
        binding.txtCropRoiStep2Fertilizer.setText(""+CropROICalculatorSingleton.getInstance().getStep2Fertilizer());
        binding.txtCropRoiStep2Pesticides.setText(""+CropROICalculatorSingleton.getInstance().getStep2Herbicide());
        binding.txtCropRoiStep2Othercosts.setText(""+CropROICalculatorSingleton.getInstance().getStep2OtherCosts());
        updateCalculations();
    }
}