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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropDatabaseInitializerSingleton;
import com.myfarmnow.myfarmcrop.singletons.CropFertilizerCalculator;

import java.util.ArrayList;

public class CropFertilizerCalculatorEntryFragment extends Fragment {
    private Context context;
    private NavController navController;
    private CropSpinnerAdapter cropsSpinnerAdapter, npkSpinnerAdapter, potassicSpinnerAdapter, nitrogenousSpinnerAdapter;
    private MyFarmDbHandlerSingleton dbHandler;

    private Toolbar toolbar;
    private Spinner spCropFertilizerCalculatorCrop, spCropFertilizerCalculatorNpkFert, spCropFertilizerCalculatorPotassicFert,
            spCropFertilizerCalculatorNitrogenousFert;
    private EditText txtCropFertilizerCalculatorNitrogen, txtCropFertilizerCalculatorPotassium, txtCropFertilizerCalculatorPhosphate,
            txtCropFertilizerCalculatorNitrogenousPrice, txtCropFertilizerCalculatorPotassicPrice, txtCropFertilizerCalculatorNpkPrice,
            txtCropFertilizerCalculatorArea;
    private TextView txtCropFertilizerCalculatorPotassicCompo, txtCropFertilizerCalculatorNpkCompo,txtCropFertilizerCalculatorNitrogenousCompo;
    private Button btnCropFertilizerCalculatorCalculate;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crop_fertilizer_calculator_entry, container, false);

        toolbar = view.findViewById(R.id.toolbar_crop_fertilizer_calculator_entry_fragment);
        spCropFertilizerCalculatorCrop = view.findViewById(R.id.sp_crop_fertilizer_calculator_crop);
        spCropFertilizerCalculatorNpkFert = view.findViewById(R.id.sp_crop_fertilizer_calculator_npk_fert);
        spCropFertilizerCalculatorPotassicFert = view.findViewById(R.id.sp_crop_fertilizer_calculator_potassic_fert);
        spCropFertilizerCalculatorNitrogenousFert = view.findViewById(R.id.sp_crop_fertilizer_calculator_nitrogenous_fert);
        txtCropFertilizerCalculatorNitrogen = view.findViewById(R.id.txt_crop_fertililizer_calculator_nitrogen);
        txtCropFertilizerCalculatorPotassium = view.findViewById(R.id.txt_crop_fertilizer_calculator_potassium);
        txtCropFertilizerCalculatorPhosphate = view.findViewById(R.id.txt_crop_fertilizer_calculator_phosphate);
        btnCropFertilizerCalculatorCalculate = view.findViewById(R.id.btn_crop_fertilizer_calculator_calculate);
        txtCropFertilizerCalculatorNitrogenousPrice = view.findViewById(R.id.txt_crop_fertilizer_calculator_nitrogenous_price);
        txtCropFertilizerCalculatorPotassicPrice = view.findViewById(R.id.txt_crop_fertilizer_calculator_potassic_price);
        txtCropFertilizerCalculatorNpkPrice = view.findViewById(R.id.txt_crop_fertilizer_calculator_npk_price);
        txtCropFertilizerCalculatorArea = view.findViewById(R.id.txt_crop_fertilizer_calculator_area);
        txtCropFertilizerCalculatorPotassicCompo = view.findViewById(R.id.txt_crop_fertilizer_calculator_potassic_compo);
        txtCropFertilizerCalculatorNpkCompo = view.findViewById(R.id.txt_crop_fertilizer_calculator_npk_compo);
        txtCropFertilizerCalculatorNitrogenousCompo = view.findViewById(R.id.txt_crop_fertilizer_calculator_nitrogenous_compo);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Fertilizer Calculator Entry");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initializeViews();
    }

    public void initializeViews() {

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        cropsSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(), "Crop", context);
        spCropFertilizerCalculatorCrop.setAdapter(cropsSpinnerAdapter);
        ArrayList<CropItem> cropItems = dbHandler.getCropItems();
        for (CropItem cropItem : cropItems) {
            cropsSpinnerAdapter.add(cropItem);
        }

        npkSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(), "Fertilizer", context);
        ArrayList<CropFertilizer> npkCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NPK);
        for (CropFertilizer cropFertilizer : npkCropFertilizers) {
            npkSpinnerAdapter.add(cropFertilizer);
        }

        spCropFertilizerCalculatorNpkFert.setAdapter(npkSpinnerAdapter);
        potassicSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(), "Fertilizer", context);
        ArrayList<CropFertilizer> potassicCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_POTASSIC);
        for (CropFertilizer cropFertilizer : potassicCropFertilizers) {
            potassicSpinnerAdapter.add(cropFertilizer);
        }

        spCropFertilizerCalculatorPotassicFert.setAdapter(potassicSpinnerAdapter);
        nitrogenousSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(), "Fertilizer", context);
        ArrayList<CropFertilizer> nitrogenousCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NITROGENOUS);
        for (CropFertilizer cropFertilizer : nitrogenousCropFertilizers) {
            nitrogenousSpinnerAdapter.add(cropFertilizer);
        }

        spCropFertilizerCalculatorNitrogenousFert.setAdapter(nitrogenousSpinnerAdapter);

        spCropFertilizerCalculatorCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    CropItem cropItem = (CropItem) ((CropSpinnerItem) spCropFertilizerCalculatorCrop.getSelectedItem());
                    txtCropFertilizerCalculatorNitrogen.setText(cropItem.getnPercentage() + "");
                    txtCropFertilizerCalculatorPotassium.setText(cropItem.getkPercentage() + "");
                    txtCropFertilizerCalculatorPhosphate.setText(cropItem.getpPercentage() + "");
                    CropFertilizerCalculator.getInstance().setCrop(cropItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCropFertilizerCalculatorNitrogenousFert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    CropFertilizer cropFertilizer = (CropFertilizer) ((CropSpinnerItem) spCropFertilizerCalculatorNitrogenousFert.getSelectedItem());
                    txtCropFertilizerCalculatorNitrogenousCompo.setText(cropFertilizer.getComposition());
                    CropFertilizerCalculator.getInstance().setNitrogenousFertilizer(cropFertilizer);
                    txtCropFertilizerCalculatorNitrogenousCompo.setVisibility(View.VISIBLE);
                } else {
                    txtCropFertilizerCalculatorNitrogenousCompo.setVisibility(View.GONE);
                    CropFertilizerCalculator.getInstance().setNitrogenousFertilizer(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCropFertilizerCalculatorPotassicFert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    CropFertilizer cropFertilizer = (CropFertilizer) ((CropSpinnerItem) spCropFertilizerCalculatorPotassicFert.getSelectedItem());
                    txtCropFertilizerCalculatorPotassicCompo.setText(cropFertilizer.getComposition());
                    CropFertilizerCalculator.getInstance().setPotassicFertilizer(cropFertilizer);
                    txtCropFertilizerCalculatorPotassicCompo.setVisibility(View.VISIBLE);
                } else {
                    txtCropFertilizerCalculatorPotassicCompo.setVisibility(View.GONE);
                    CropFertilizerCalculator.getInstance().setPotassicFertilizer(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCropFertilizerCalculatorNpkFert.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    CropFertilizer cropFertilizer = (CropFertilizer) ((CropSpinnerItem) spCropFertilizerCalculatorNpkFert.getSelectedItem());
                    txtCropFertilizerCalculatorNpkCompo.setText(cropFertilizer.getComposition());
                    txtCropFertilizerCalculatorNpkCompo.setVisibility(View.VISIBLE);
                    CropFertilizerCalculator.getInstance().setNpkFertilizer(cropFertilizer);
                } else {
                    txtCropFertilizerCalculatorNpkCompo.setVisibility(View.GONE);
                    CropFertilizerCalculator.getInstance().setNpkFertilizer(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCropFertilizerCalculatorCalculate.setOnClickListener(v -> {
            if (validateEntries()) {
                try {
                    CropFertilizerCalculator.getInstance().setNitrogenComposition(Float.parseFloat(txtCropFertilizerCalculatorNitrogen.getText().toString()));
                    CropFertilizerCalculator.getInstance().setPhosphateComposition(Float.parseFloat(txtCropFertilizerCalculatorPhosphate.getText().toString()));
                    CropFertilizerCalculator.getInstance().setPotassiumComposition(Float.parseFloat(txtCropFertilizerCalculatorPotassium.getText().toString()));
                    CropFertilizerCalculator.getInstance().setPotassicPrice(Float.parseFloat(txtCropFertilizerCalculatorPotassicPrice.getText().toString()));
                    CropFertilizerCalculator.getInstance().setNitrogenousPrice(Float.parseFloat(txtCropFertilizerCalculatorNitrogenousPrice.getText().toString()));
                    CropFertilizerCalculator.getInstance().setNpkPrice(Float.parseFloat(txtCropFertilizerCalculatorNpkPrice.getText().toString()));
                    CropFertilizerCalculator.getInstance().setArea(Float.parseFloat(txtCropFertilizerCalculatorArea.getText().toString()));
                    CropFertilizerCalculator.getInstance().setUnits(getString(R.string.acres));

                    if (CropFertilizerCalculator.getInstance().isCalculationPossible()) {
                        //navigate to results fragment
                        navController.navigate(R.id.action_cropFertilizerCalculatorEntryFragment_to_cropFertilizerCalculatorResultsFragment);

                    } else {
                        Toast.makeText(context, getString(CropFertilizerCalculator.getInstance().determineMissingNutrient()), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    public boolean validateEntries() {
        String message = null;
        if (txtCropFertilizerCalculatorNitrogen.getText().toString().isEmpty()) {
            message = "The nitrogen quantity is not entered";
            txtCropFertilizerCalculatorNitrogen.requestFocus();
        } else if (txtCropFertilizerCalculatorPhosphate.getText().toString().isEmpty()) {
            message = "The phosphate quantity is not entered";
            txtCropFertilizerCalculatorPhosphate.requestFocus();
        } else if (txtCropFertilizerCalculatorPotassium.getText().toString().isEmpty()) {
            message = "The potassium quantity is not entered";
            txtCropFertilizerCalculatorPotassium.requestFocus();
        } else if (txtCropFertilizerCalculatorNpkPrice.getText().toString().isEmpty()) {
            message = getString(R.string.missing_unit_price);
            txtCropFertilizerCalculatorNpkPrice.requestFocus();
        } else if (txtCropFertilizerCalculatorPotassicPrice.getText().toString().isEmpty()) {
            message = getString(R.string.missing_unit_price);
            txtCropFertilizerCalculatorPotassicPrice.requestFocus();
        } else if (txtCropFertilizerCalculatorNitrogenousPrice.getText().toString().isEmpty()) {
            message = getString(R.string.missing_unit_price);
            txtCropFertilizerCalculatorNitrogenousPrice.requestFocus();
        }

        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}