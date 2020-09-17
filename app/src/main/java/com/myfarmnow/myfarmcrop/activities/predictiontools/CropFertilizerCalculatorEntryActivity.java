package com.myfarmnow.myfarmcrop.activities.predictiontools;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
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

public class CropFertilizerCalculatorEntryActivity extends AppCompatActivity {

    Spinner cropSp, npkSp,potassicSp, nitrogenousSp;
    EditText nitrogenTxt, phosphateTxt, potassiumTxt,npkPriceTxt, potassicPriceTxt, nitrogenousPriceTxt,areaTxt;
    TextView npkCompoTxt, potassicCompoTxt, nitrogenousCompoTxt;
    CropSpinnerAdapter cropsSpinnerAdapter,npkSpinnerAdapter,potassicSpinnerAdapter,nitrogenousSpinnerAdapter;
    MyFarmDbHandlerSingleton dbHandler;
    Button calculateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_fertilizer_calculator_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();


    }

    public void initializeViews(){
        cropSp = findViewById(R.id.sp_crop_fertililizer_calculator_crop);
        nitrogenTxt = findViewById(R.id.txt_crop_fertililizer_calculator_nitrogen);
        phosphateTxt = findViewById(R.id.txt_crop_fertililizer_calculator_phosphate);
        potassiumTxt = findViewById(R.id.txt_crop_fertililizer_calculator_potassium);
        nitrogenousSp = findViewById(R.id.sp_crop_fertililizer_calculator_nitrogenous_fert);
        potassicSp = findViewById(R.id.sp_crop_fertililizer_calculator_potassic_fert);
        npkSp = findViewById(R.id.sp_crop_fertililizer_calculator_npk_fert);
        potassicCompoTxt = findViewById(R.id.txt_crop_fertililizer_calculator_potassic_compo);
        npkCompoTxt = findViewById(R.id.txt_crop_fertililizer_calculator_npk_compo);
        areaTxt = findViewById(R.id.txt_crop_fertililizer_calculator_area);
        nitrogenousCompoTxt = findViewById(R.id.txt_crop_fertililizer_calculator_nitrogenous_compo);
        npkPriceTxt = findViewById(R.id.txt_crop_fertililizer_calculator_npk_price);
        potassicPriceTxt = findViewById(R.id.txt_crop_fertililizer_calculator_potassic_price);
        nitrogenousPriceTxt = findViewById(R.id.txt_crop_fertililizer_calculator_nitrogenous_price);
        calculateBtn = findViewById(R.id.btn_crop_fertililizer_calculator_calculate);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        cropsSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Crop",this);
        cropSp.setAdapter(cropsSpinnerAdapter);
        ArrayList<CropItem> cropItems = dbHandler.getCropItems();
        for(CropItem cropItem: cropItems){
            cropsSpinnerAdapter.add(cropItem);
        }

        npkSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",this);
        ArrayList<CropFertilizer> npkCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NPK);
        for(CropFertilizer cropFertilizer: npkCropFertilizers){
            npkSpinnerAdapter.add(cropFertilizer);
        }
        npkSp.setAdapter(npkSpinnerAdapter);
        potassicSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",this);
        ArrayList<CropFertilizer> potassicCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_POTASSIC);
        for(CropFertilizer cropFertilizer: potassicCropFertilizers){
            potassicSpinnerAdapter.add(cropFertilizer);
        }
        potassicSp.setAdapter(potassicSpinnerAdapter);
        nitrogenousSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",this);
        ArrayList<CropFertilizer> nitrogenousCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NITROGENOUS);
        for(CropFertilizer cropFertilizer: nitrogenousCropFertilizers){
            nitrogenousSpinnerAdapter.add(cropFertilizer);
        }
        nitrogenousSp.setAdapter(nitrogenousSpinnerAdapter);

        cropSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CropItem cropItem = (CropItem)((CropSpinnerItem)cropSp.getSelectedItem());
                    nitrogenTxt.setText(cropItem.getnPercentage()+"");
                    potassiumTxt.setText(cropItem.getkPercentage()+"");
                    phosphateTxt.setText(cropItem.getpPercentage()+"");
                    CropFertilizerCalculator.getInstance().setCrop(cropItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        nitrogenousSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CropFertilizer cropFertilizer = (CropFertilizer)((CropSpinnerItem)nitrogenousSp.getSelectedItem());
                    nitrogenousCompoTxt.setText(cropFertilizer.getComposition());
                    CropFertilizerCalculator.getInstance().setNitrogenousFertilizer(cropFertilizer);
                    nitrogenousCompoTxt.setVisibility(View.VISIBLE);
                }else{
                    nitrogenousCompoTxt.setVisibility(View.GONE);
                    CropFertilizerCalculator.getInstance().setNitrogenousFertilizer(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        potassicSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CropFertilizer cropFertilizer = (CropFertilizer)((CropSpinnerItem)potassicSp.getSelectedItem());
                    potassicCompoTxt.setText(cropFertilizer.getComposition());
                    CropFertilizerCalculator.getInstance().setPotassicFertilizer(cropFertilizer);
                    potassicCompoTxt.setVisibility(View.VISIBLE);
                } else{
                    potassicCompoTxt.setVisibility(View.GONE);
                    CropFertilizerCalculator.getInstance().setPotassicFertilizer(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        npkSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CropFertilizer cropFertilizer = (CropFertilizer)((CropSpinnerItem)npkSp.getSelectedItem());
                    npkCompoTxt.setText(cropFertilizer.getComposition());
                    npkCompoTxt.setVisibility(View.VISIBLE);
                    CropFertilizerCalculator.getInstance().setNpkFertilizer(cropFertilizer);
                }
                else{
                    npkCompoTxt.setVisibility(View.GONE);
                    CropFertilizerCalculator.getInstance().setNpkFertilizer(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    try {
                        CropFertilizerCalculator.getInstance().setNitrogenComposition(Float.parseFloat(nitrogenTxt.getText().toString()));
                        CropFertilizerCalculator.getInstance().setPhosphateComposition(Float.parseFloat(phosphateTxt.getText().toString()));
                        CropFertilizerCalculator.getInstance().setPotassiumComposition(Float.parseFloat(potassiumTxt.getText().toString()));
                        CropFertilizerCalculator.getInstance().setPotassicPrice(Float.parseFloat(potassicPriceTxt.getText().toString()));
                        CropFertilizerCalculator.getInstance().setNitrogenousPrice(Float.parseFloat(nitrogenousPriceTxt.getText().toString()));
                        CropFertilizerCalculator.getInstance().setNpkPrice(Float.parseFloat(npkPriceTxt.getText().toString()));
                        CropFertilizerCalculator.getInstance().setArea(Float.parseFloat(areaTxt.getText().toString()));
                        CropFertilizerCalculator.getInstance().setUnits(getString(R.string.acres));

                        if(CropFertilizerCalculator.getInstance().isCalculationPossible()){
                            startActivity(new Intent(CropFertilizerCalculatorEntryActivity.this,CropFertilizerCalculatorResults.class));
                        }else{
                            Toast.makeText(CropFertilizerCalculatorEntryActivity.this, getString(CropFertilizerCalculator.getInstance().determineMissingNutrient()), Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){

                    }
                }
            }
        });
    }

    public boolean validateEntries(){
        String message = null;
        if(nitrogenTxt.getText().toString().isEmpty()){
            message = "The nitrogen quantity is not entered";
            nitrogenTxt.requestFocus();
        }
        else if(phosphateTxt.getText().toString().isEmpty()){
            message = "The phosphate quantity is not entered";
            phosphateTxt.requestFocus();
        }
        else if(potassiumTxt.getText().toString().isEmpty()){
            message = "The potassium quantity is not entered";
            potassiumTxt.requestFocus();
        }
        else if(npkPriceTxt.getText().toString().isEmpty()){
            message = getString(R.string.missing_unit_price);
            npkPriceTxt.requestFocus();
        }
        else if(potassicPriceTxt.getText().toString().isEmpty()){
            message = getString(R.string.missing_unit_price);
            potassicPriceTxt.requestFocus();
        }
        else if(nitrogenousPriceTxt.getText().toString().isEmpty()){
            message = getString(R.string.missing_unit_price);
            nitrogenousPriceTxt.requestFocus();
        }




        if(message != null){
            Toast.makeText(CropFertilizerCalculatorEntryActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

}
