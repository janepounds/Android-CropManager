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
import android.widget.AdapterView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentCropFertilizerCalculatorEntryBinding;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropDatabaseInitializerSingleton;
import com.myfarmnow.myfarmcrop.singletons.CropFertilizerCalculator;

import java.util.ArrayList;


public class CropFertilizerCalculatorEntryFragment extends Fragment {
    private Context context;
    private NavController navController;
    private CropSpinnerAdapter cropsSpinnerAdapter,npkSpinnerAdapter,potassicSpinnerAdapter,nitrogenousSpinnerAdapter;
    private MyFarmDbHandlerSingleton dbHandler;
    private FragmentCropFertilizerCalculatorEntryBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_crop_fertilizer_calculator_entry, container, false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Fertilizer Calculator Entry");
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initializeViews();
    }

    public void initializeViews(){

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        cropsSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Crop",context);
        binding.spCropFertililizerCalculatorCrop.setAdapter(cropsSpinnerAdapter);
        ArrayList<CropItem> cropItems = dbHandler.getCropItems();
        for(CropItem cropItem: cropItems){
            cropsSpinnerAdapter.add(cropItem);
        }

        npkSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",context);
        ArrayList<CropFertilizer> npkCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NPK);
        for(CropFertilizer cropFertilizer: npkCropFertilizers){
            npkSpinnerAdapter.add(cropFertilizer);
        }
        binding.spCropFertililizerCalculatorNpkFert.setAdapter(npkSpinnerAdapter);
        potassicSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",context);
        ArrayList<CropFertilizer> potassicCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_POTASSIC);
        for(CropFertilizer cropFertilizer: potassicCropFertilizers){
            potassicSpinnerAdapter.add(cropFertilizer);
        }
        binding.spCropFertililizerCalculatorPotassicFert.setAdapter(potassicSpinnerAdapter);
        nitrogenousSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",context);
        ArrayList<CropFertilizer> nitrogenousCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NITROGENOUS);
        for(CropFertilizer cropFertilizer: nitrogenousCropFertilizers){
            nitrogenousSpinnerAdapter.add(cropFertilizer);
        }
        binding.spCropFertililizerCalculatorNitrogenousFert.setAdapter(nitrogenousSpinnerAdapter);

        binding.spCropFertililizerCalculatorCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CropItem cropItem = (CropItem)((CropSpinnerItem)binding.spCropFertililizerCalculatorCrop.getSelectedItem());
                    binding.txtCropFertililizerCalculatorNitrogen.setText(cropItem.getnPercentage()+"");
                    binding.txtCropFertililizerCalculatorPotassium.setText(cropItem.getkPercentage()+"");
                    binding.txtCropFertililizerCalculatorPhosphate.setText(cropItem.getpPercentage()+"");
                    CropFertilizerCalculator.getInstance().setCrop(cropItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spCropFertililizerCalculatorNitrogenousFert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CropFertilizer cropFertilizer = (CropFertilizer)((CropSpinnerItem)binding.spCropFertililizerCalculatorNitrogenousFert.getSelectedItem());
                    binding.txtCropFertililizerCalculatorNitrogenousCompo.setText(cropFertilizer.getComposition());
                    CropFertilizerCalculator.getInstance().setNitrogenousFertilizer(cropFertilizer);
                    binding.txtCropFertililizerCalculatorNitrogenousCompo.setVisibility(View.VISIBLE);
                }else{
                    binding.txtCropFertililizerCalculatorNitrogenousCompo.setVisibility(View.GONE);
                    CropFertilizerCalculator.getInstance().setNitrogenousFertilizer(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spCropFertililizerCalculatorPotassicFert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CropFertilizer cropFertilizer = (CropFertilizer)((CropSpinnerItem)binding.spCropFertililizerCalculatorPotassicFert.getSelectedItem());
                    binding.txtCropFertililizerCalculatorPotassicCompo.setText(cropFertilizer.getComposition());
                    CropFertilizerCalculator.getInstance().setPotassicFertilizer(cropFertilizer);
                    binding.txtCropFertililizerCalculatorPotassicCompo.setVisibility(View.VISIBLE);
                } else{
                    binding.txtCropFertililizerCalculatorPotassicCompo.setVisibility(View.GONE);
                    CropFertilizerCalculator.getInstance().setPotassicFertilizer(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spCropFertililizerCalculatorNpkFert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CropFertilizer cropFertilizer = (CropFertilizer)((CropSpinnerItem)binding.spCropFertililizerCalculatorNpkFert.getSelectedItem());
                    binding.txtCropFertililizerCalculatorNpkCompo.setText(cropFertilizer.getComposition());
                    binding.txtCropFertililizerCalculatorNpkCompo.setVisibility(View.VISIBLE);
                    CropFertilizerCalculator.getInstance().setNpkFertilizer(cropFertilizer);
                }
                else{
                    binding.txtCropFertililizerCalculatorNpkCompo.setVisibility(View.GONE);
                    CropFertilizerCalculator.getInstance().setNpkFertilizer(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnCropFertililizerCalculatorCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    try {
                        CropFertilizerCalculator.getInstance().setNitrogenComposition(Float.parseFloat(binding.txtCropFertililizerCalculatorNitrogen.getText().toString()));
                        CropFertilizerCalculator.getInstance().setPhosphateComposition(Float.parseFloat(binding.txtCropFertililizerCalculatorPhosphate.getText().toString()));
                        CropFertilizerCalculator.getInstance().setPotassiumComposition(Float.parseFloat(binding.txtCropFertililizerCalculatorPotassium.getText().toString()));
                        CropFertilizerCalculator.getInstance().setPotassicPrice(Float.parseFloat(binding.txtCropFertililizerCalculatorPotassicPrice.getText().toString()));
                        CropFertilizerCalculator.getInstance().setNitrogenousPrice(Float.parseFloat(binding.txtCropFertililizerCalculatorNitrogenousPrice.getText().toString()));
                        CropFertilizerCalculator.getInstance().setNpkPrice(Float.parseFloat(binding.txtCropFertililizerCalculatorNpkPrice.getText().toString()));
                        CropFertilizerCalculator.getInstance().setArea(Float.parseFloat(binding.txtCropFertililizerCalculatorArea.getText().toString()));
                        CropFertilizerCalculator.getInstance().setUnits(getString(R.string.acres));

                        if(CropFertilizerCalculator.getInstance().isCalculationPossible()){
                            //navigate to results fragment
                            navController.navigate(R.id.action_cropFertilizerCalculatorEntryFragment_to_cropFertilizerCalculatorResultsFragment);

                        }else{
                            Toast.makeText(context, getString(CropFertilizerCalculator.getInstance().determineMissingNutrient()), Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){

                    }
                }
            }
        });
    }

    public boolean validateEntries(){
        String message = null;
        if( binding.txtCropFertililizerCalculatorNitrogen.getText().toString().isEmpty()){
            message = "The nitrogen quantity is not entered";
            binding.txtCropFertililizerCalculatorNitrogen.requestFocus();
        }
        else if(binding.txtCropFertililizerCalculatorPhosphate.getText().toString().isEmpty()){
            message = "The phosphate quantity is not entered";
            binding.txtCropFertililizerCalculatorPhosphate.requestFocus();
        }
        else if(binding.txtCropFertililizerCalculatorPotassium.getText().toString().isEmpty()){
            message = "The potassium quantity is not entered";
            binding.txtCropFertililizerCalculatorPotassium.requestFocus();
        }
        else if(binding.txtCropFertililizerCalculatorNpkPrice.getText().toString().isEmpty()){
            message = getString(R.string.missing_unit_price);
            binding.txtCropFertililizerCalculatorNpkPrice.requestFocus();
        }
        else if(binding.txtCropFertililizerCalculatorPotassicPrice.getText().toString().isEmpty()){
            message = getString(R.string.missing_unit_price);
            binding.txtCropFertililizerCalculatorPotassicPrice.requestFocus();
        }
        else if(binding.txtCropFertililizerCalculatorNitrogenousPrice.getText().toString().isEmpty()){
            message = getString(R.string.missing_unit_price);
            binding.txtCropFertililizerCalculatorNitrogenousPrice.requestFocus();
        }




        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}