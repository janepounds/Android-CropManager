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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.FragmentCropFertilizerCalculatorResultsBinding;
import com.myfarmnow.myfarmcrop.models.FertilizerCalculationException;
import com.myfarmnow.myfarmcrop.singletons.CropFertilizerCalculator;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;


public class CropFertilizerCalculatorResultsFragment extends Fragment {
private Context context;
private NavController navController;
private TableRow npkRow,potassicRow,nitrogenousRow;
private FragmentCropFertilizerCalculatorResultsBinding binding;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_crop_fertilizer_calculator_results, container, false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Crop Fertilizer Calculator Results");
        binding.toolbar.setNavigationOnClickListener(view -> navController.popBackStack());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initializeViews();
    }
    public void initializeViews(){

        binding.txtCropFertililizerCalculatorArea.setText(CropFertilizerCalculator.getInstance().getArea()+" "+CropFertilizerCalculator.getInstance().getUnits());
        binding.txtCropFertililizerCalculatorCrop.setText(CropFertilizerCalculator.getInstance().getCrop().getName());

        binding.txtCropFertilizerCalculatorUnitPrice.setText("Unit Price ("+ CropSettingsSingleton.getInstance().getCurrency()+")");
        binding.txtCropFertilizerCalculatorTotalCost.setText("Total Cost ("+CropSettingsSingleton.getInstance().getCurrency()+")");


        if(CropFertilizerCalculator.getInstance().getNpkFertilizer() != null){
            binding.textViewCropFertCalcNpkFertilizer.setText(CropFertilizerCalculator.getInstance().getNpkFertilizer().getFertilizerName());
            try {
                binding.textViewCropFertCalcNpkQty.setText(CropFertilizerCalculator.getInstance().computeNpkQuantity()+"");
                binding.textViewCropFertCalcNpkPrice.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().getNpkPrice()));
                binding.textViewCropFertCalcNpkCost.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computeNpkCost()));
            } catch (FertilizerCalculationException e) {
                e.printStackTrace();
            }
        }else{
            npkRow.setVisibility(View.GONE);
        }
        if(CropFertilizerCalculator.getInstance().getPotassicFertilizer() != null){
            binding.textViewCropFertCalcPotassicFertilizer.setText(CropFertilizerCalculator.getInstance().getPotassicFertilizer().getFertilizerName());
            try {
                binding.textViewCropFertCalcPotassicQty.setText(CropFertilizerCalculator.getInstance().computePotassicQuantity()+"");
                binding.textViewCropFertCalcPotassicPrice.setText( NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().getPotassicPrice()));
                binding.textViewCropFertCalcPotassicCost.setText( NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computePotassicCost()));
            } catch (FertilizerCalculationException e) {
                e.printStackTrace();
            }
        }else{
            potassicRow.setVisibility(View.GONE);
        }
        if(CropFertilizerCalculator.getInstance().getNitrogenousFertilizer() != null){
           binding.textViewCropFertCalcNitogenousFertilizer.setText(CropFertilizerCalculator.getInstance().getNitrogenousFertilizer().getFertilizerName());
            try {
                binding.textViewCropFertCalcNitrogenousQty.setText(CropFertilizerCalculator.getInstance().computeNitrogenousQuantity()+"");
                binding.textViewCropFertCalcNitrogenousPrice.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().getNitrogenousPrice()));
                binding.textViewCropFertCalcNitrogenousCost.setText(NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computeNitrogenousCost()));

            } catch (FertilizerCalculationException e) {
                e.printStackTrace();
            }
        }else{
            nitrogenousRow.setVisibility(View.GONE);
        }
        try {
            binding.txtCropFertilizerCalculatorTotalCost.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+ NumberFormat.getInstance().format(CropFertilizerCalculator.getInstance().computeTotalCost()));
        } catch (FertilizerCalculationException e) {
            e.printStackTrace();
        }

    }
}