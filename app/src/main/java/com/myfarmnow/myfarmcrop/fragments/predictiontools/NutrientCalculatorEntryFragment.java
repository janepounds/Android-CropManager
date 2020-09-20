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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.predictiontools.CropNutrientsCalculatorEntryActivity;
import com.myfarmnow.myfarmcrop.activities.predictiontools.CropNutrientsCalculatorResults;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentNutrientCalculatorEntryBinding;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropDatabaseInitializerSingleton;
import com.myfarmnow.myfarmcrop.singletons.CropNutrientsCalculator;

import java.util.ArrayList;


public class NutrientCalculatorEntryFragment extends Fragment {
    private Context context;
    private MyFarmDbHandlerSingleton dbHandler;
    private FragmentNutrientCalculatorEntryBinding binding;
    private NavController navController;
    private  CropSpinnerAdapter cropsSpinnerAdapter,nSourceSpinnerAdapter,kSourceSpinnerAdapter,pSourceSpinnerAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_nutrient_calculator_entry, container, false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Nutrient Calculator Entry");
        binding.toolbar.setNavigationOnClickListener(view -> navController.popBackStack());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        initializeViews();

    }
    public void initializeViews(){

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        cropsSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Crop",context);
        binding.spCropNutrientsCalculatorCrop.setAdapter(cropsSpinnerAdapter);
        ArrayList<CropItem> cropItems = dbHandler.getCropItemsForNutrientRemoval();
        for(CropItem cropItem: cropItems){
            cropsSpinnerAdapter.add(cropItem);
        }

        nSourceSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",context);
        pSourceSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",context);
        kSourceSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",context);

        ArrayList<CropFertilizer> npkCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NPK);
        for(CropFertilizer cropFertilizer: npkCropFertilizers){
            nSourceSpinnerAdapter.add(cropFertilizer);
            kSourceSpinnerAdapter.add(cropFertilizer);
            pSourceSpinnerAdapter.add(cropFertilizer);
        }
        binding.spCropNutrientsCalculatorNSourceFert.setAdapter(nSourceSpinnerAdapter);

        ArrayList<CropFertilizer> kSourceCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_POTASSIC);
        for(CropFertilizer cropFertilizer: kSourceCropFertilizers){
            kSourceSpinnerAdapter.add(cropFertilizer);
        }
        binding.spCropNutrientsCalculatorKSourceFert.setAdapter(kSourceSpinnerAdapter);

        ArrayList<CropFertilizer> nitrogenousCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NITROGENOUS);

        for(CropFertilizer cropFertilizer: nitrogenousCropFertilizers){
            pSourceSpinnerAdapter.add(cropFertilizer);
            nSourceSpinnerAdapter.add(cropFertilizer);
        }
        binding.spCropNutrientsCalculatorPSourceFert.setAdapter(pSourceSpinnerAdapter);

        binding.spCropNutrientsCalculatorCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CropItem cropItem = (CropItem)((CropSpinnerItem)binding.spCropNutrientsCalculatorCrop.getSelectedItem());
                    CropNutrientsCalculator.getInstance().setCrop(cropItem);
                    System.out.println("REMOVED PER TONNES: N="+cropItem.getnRemoved()+" P = "+cropItem.getpRemoved()+" K ="+cropItem.getkRemoved());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.checkCropNutrientsCalculatorEconImpact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    binding.layoutCropNutrientsCalculatorEconImpact.setVisibility(View.VISIBLE);
                }else{
                    binding.layoutCropNutrientsCalculatorEconImpact.setVisibility(View.GONE);
                }
            }
        });

        binding.btnCropNutrientsCalculatorCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    try {
                        CropItem cropItem = (CropItem)((CropSpinnerItem)binding.spCropNutrientsCalculatorCrop.getSelectedItem());
                        CropNutrientsCalculator.getInstance().setCrop(cropItem);
                        CropNutrientsCalculator.getInstance().setEstimatedYield(Float.parseFloat(binding.txtCropNutrientsCalculatorYield.getText().toString()));
                        CropNutrientsCalculator.getInstance().setEconomicImpactRequired(binding.checkCropNutrientsCalculatorEconImpact.isChecked());
                        if(binding.checkCropNutrientsCalculatorEconImpact.isChecked()){
                            CropNutrientsCalculator.getInstance().setNitrogenSourceFertilizer((CropFertilizer)((CropSpinnerItem) binding.spCropNutrientsCalculatorNSourceFert.getSelectedItem()));
                            CropNutrientsCalculator.getInstance().setPotassiumSourceFertilizer((CropFertilizer)((CropSpinnerItem) binding.spCropNutrientsCalculatorKSourceFert.getSelectedItem()));
                            CropNutrientsCalculator.getInstance().setPhosphorusSourceFertilizer((CropFertilizer)((CropSpinnerItem) binding.spCropNutrientsCalculatorPSourceFert.getSelectedItem()));
                            CropNutrientsCalculator.getInstance().setNitrogenSourcePrice(Float.parseFloat(binding.txtCropNutrientsCalculatorNSourcePrice.getText().toString()));
                            CropNutrientsCalculator.getInstance().setPhosphorusSourcePrice(Float.parseFloat(binding.txtCropNutrientsCalculatorPSourcePrice.getText().toString()));
                            CropNutrientsCalculator.getInstance().setPotassiumSourcePrice(Float.parseFloat(binding.txtCropNutrientsCalculatorKSourcePrice.getText().toString()));

                        }

                        //start results fragment
                        navController.navigate(R.id.action_nutrientCalculatorEntryFragment_to_nutrientCalculatorResultsFragment);


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public boolean validateEntries(){
        String message = null;
        if(binding.txtCropNutrientsCalculatorYield.getText().toString().isEmpty()){
            message = getString(R.string.estimated_yield_not_entered);
            binding.txtCropNutrientsCalculatorYield.requestFocus();
        }
        else if(binding.spCropNutrientsCalculatorCrop.getSelectedItemPosition()==0){
            message = getString(R.string.crop_not_selected);
            binding.spCropNutrientsCalculatorCrop.requestFocus();
        }

        else if(binding.checkCropNutrientsCalculatorEconImpact.isChecked()){
            if(binding.txtCropNutrientsCalculatorNSourcePrice.getText().toString().isEmpty()){
                message = getString(R.string.missing_unit_price);
                binding.txtCropNutrientsCalculatorNSourcePrice.requestFocus();
            }
            else if(binding.txtCropNutrientsCalculatorKSourcePrice.getText().toString().isEmpty()){
                message = getString(R.string.missing_unit_price);
                binding.txtCropNutrientsCalculatorKSourcePrice.requestFocus();
            }
            else if(binding.txtCropNutrientsCalculatorPSourcePrice.getText().toString().isEmpty()){
                message = getString(R.string.missing_unit_price);
                binding.txtCropNutrientsCalculatorPSourcePrice.requestFocus();
            }
        }
        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}