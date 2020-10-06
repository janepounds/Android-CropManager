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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropDatabaseInitializerSingleton;
import com.myfarmnow.myfarmcrop.singletons.CropNutrientsCalculator;

import java.util.ArrayList;

public class NutrientCalculatorEntryFragment extends Fragment {
    private Context context;
    private MyFarmDbHandlerSingleton dbHandler;
    private NavController navController;
    private CropSpinnerAdapter cropsSpinnerAdapter, nSourceSpinnerAdapter, kSourceSpinnerAdapter, pSourceSpinnerAdapter;

    private Toolbar toolbar;
    private Spinner spCropNutrientsCalculatorCrop, spCropNutrientsCalculatorNSourceFert, spCropNutrientsCalculatorKSourceFert, spCropNutrientsCalculatorPSourceFert;
    private CheckBox checkCropNutrientsCalculatorEconImpact;
    private LinearLayout layoutCropNutrientsCalculatorEconImpact;
    private Button btnCropNutrientsCalculatorCalculate;
    private EditText txtCropNutrientsCalculatorYield, txtCropNutrientsCalculatorNSourcePrice, txtCropNutrientsCalculatorPSourcePrice, txtCropNutrientsCalculatorKSourcePrice;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrient_calculator_entry, container, false);

        toolbar = view.findViewById(R.id.toolbar_nutrient_calculator_entry);
        spCropNutrientsCalculatorCrop = view.findViewById(R.id.sp_crop_nutrients_calculator_crop);
        spCropNutrientsCalculatorNSourceFert = view.findViewById(R.id.sp_crop_nutrients_calculator_nSource_fert);
        spCropNutrientsCalculatorKSourceFert = view.findViewById(R.id.sp_crop_nutrients_calculator_kSource_fert);
        spCropNutrientsCalculatorPSourceFert = view.findViewById(R.id.sp_crop_nutrients_calculator_pSource_fert);
        checkCropNutrientsCalculatorEconImpact = view.findViewById(R.id.check_crop_nutrients_calculator_econ_impact);
        layoutCropNutrientsCalculatorEconImpact = view.findViewById(R.id.layout_crop_nutrients_calculator_econ_impact);
        txtCropNutrientsCalculatorYield = view.findViewById(R.id.txt_crop_nutrients_calculator_yield);
        txtCropNutrientsCalculatorNSourcePrice = view.findViewById(R.id.txt_crop_nutrients_calculator_nSource_price);
        txtCropNutrientsCalculatorPSourcePrice = view.findViewById(R.id.txt_crop_nutrients_calculator_pSource_price);
        txtCropNutrientsCalculatorKSourcePrice = view.findViewById(R.id.txt_crop_nutrients_calculator_kSource_price);

        btnCropNutrientsCalculatorCalculate = view.findViewById(R.id.btn_crop_nutrients_calculator_calculate);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Nutrient Calculator Entry");
        toolbar.setNavigationOnClickListener(v -> navController.popBackStack());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        initializeViews();

    }

    public void initializeViews() {

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        cropsSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(), "Crop", context);
        spCropNutrientsCalculatorCrop.setAdapter(cropsSpinnerAdapter);
        ArrayList<CropItem> cropItems = dbHandler.getCropItemsForNutrientRemoval();

        for (CropItem cropItem : cropItems) {
            cropsSpinnerAdapter.add(cropItem);
        }

        nSourceSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(), "Fertilizer", context);
        pSourceSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(), "Fertilizer", context);
        kSourceSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(), "Fertilizer", context);

        ArrayList<CropFertilizer> npkCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NPK);
        for (CropFertilizer cropFertilizer : npkCropFertilizers) {
            nSourceSpinnerAdapter.add(cropFertilizer);
            kSourceSpinnerAdapter.add(cropFertilizer);
            pSourceSpinnerAdapter.add(cropFertilizer);
        }

        spCropNutrientsCalculatorNSourceFert.setAdapter(nSourceSpinnerAdapter);

        ArrayList<CropFertilizer> kSourceCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_POTASSIC);

        for (CropFertilizer cropFertilizer : kSourceCropFertilizers) {
            kSourceSpinnerAdapter.add(cropFertilizer);
        }

        spCropNutrientsCalculatorKSourceFert.setAdapter(kSourceSpinnerAdapter);

        ArrayList<CropFertilizer> nitrogenousCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NITROGENOUS);

        for (CropFertilizer cropFertilizer : nitrogenousCropFertilizers) {
            pSourceSpinnerAdapter.add(cropFertilizer);
            nSourceSpinnerAdapter.add(cropFertilizer);
        }

        spCropNutrientsCalculatorPSourceFert.setAdapter(pSourceSpinnerAdapter);

        spCropNutrientsCalculatorCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    CropItem cropItem = (CropItem) ((CropSpinnerItem) spCropNutrientsCalculatorCrop.getSelectedItem());
                    CropNutrientsCalculator.getInstance().setCrop(cropItem);
                    System.out.println("REMOVED PER TONNES: N=" + cropItem.getnRemoved() + " P = " + cropItem.getpRemoved() + " K =" + cropItem.getkRemoved());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkCropNutrientsCalculatorEconImpact.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                layoutCropNutrientsCalculatorEconImpact.setVisibility(View.VISIBLE);
            } else {
                layoutCropNutrientsCalculatorEconImpact.setVisibility(View.GONE);
            }
        });

        btnCropNutrientsCalculatorCalculate.setOnClickListener(v -> {
            if (validateEntries()) {
                try {
                    CropItem cropItem = (CropItem) ((CropSpinnerItem) spCropNutrientsCalculatorCrop.getSelectedItem());
                    CropNutrientsCalculator.getInstance().setCrop(cropItem);
                    CropNutrientsCalculator.getInstance().setEstimatedYield(Float.parseFloat(txtCropNutrientsCalculatorYield.getText().toString()));
                    CropNutrientsCalculator.getInstance().setEconomicImpactRequired(checkCropNutrientsCalculatorEconImpact.isChecked());
                    if (checkCropNutrientsCalculatorEconImpact.isChecked()) {
                        CropNutrientsCalculator.getInstance().setNitrogenSourceFertilizer((CropFertilizer) ((CropSpinnerItem) spCropNutrientsCalculatorNSourceFert.getSelectedItem()));
                        CropNutrientsCalculator.getInstance().setPotassiumSourceFertilizer((CropFertilizer) ((CropSpinnerItem) spCropNutrientsCalculatorKSourceFert.getSelectedItem()));
                        CropNutrientsCalculator.getInstance().setPhosphorusSourceFertilizer((CropFertilizer) ((CropSpinnerItem) spCropNutrientsCalculatorPSourceFert.getSelectedItem()));
                        CropNutrientsCalculator.getInstance().setNitrogenSourcePrice(Float.parseFloat(txtCropNutrientsCalculatorNSourcePrice.getText().toString()));
                        CropNutrientsCalculator.getInstance().setPhosphorusSourcePrice(Float.parseFloat(txtCropNutrientsCalculatorPSourcePrice.getText().toString()));
                        CropNutrientsCalculator.getInstance().setPotassiumSourcePrice(Float.parseFloat(txtCropNutrientsCalculatorKSourcePrice.getText().toString()));
                    }

                    //start results fragment
                    navController.navigate(R.id.action_nutrientCalculatorEntryFragment_to_nutrientCalculatorResultsFragment);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean validateEntries() {
        String message = null;
        if (txtCropNutrientsCalculatorYield.getText().toString().isEmpty()) {
            message = getString(R.string.estimated_yield_not_entered);
            txtCropNutrientsCalculatorYield.requestFocus();
        } else if (spCropNutrientsCalculatorCrop.getSelectedItemPosition() == 0) {
            message = getString(R.string.crop_not_selected);
            spCropNutrientsCalculatorCrop.requestFocus();
        } else if (checkCropNutrientsCalculatorEconImpact.isChecked()) {
            if (txtCropNutrientsCalculatorNSourcePrice.getText().toString().isEmpty()) {
                message = getString(R.string.missing_unit_price);
                txtCropNutrientsCalculatorNSourcePrice.requestFocus();
            } else if (txtCropNutrientsCalculatorKSourcePrice.getText().toString().isEmpty()) {
                message = getString(R.string.missing_unit_price);
                txtCropNutrientsCalculatorKSourcePrice.requestFocus();
            } else if (txtCropNutrientsCalculatorPSourcePrice.getText().toString().isEmpty()) {
                message = getString(R.string.missing_unit_price);
                txtCropNutrientsCalculatorPSourcePrice.requestFocus();
            }
        }

        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}