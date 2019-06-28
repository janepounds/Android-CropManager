package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
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

public class CropNutrientsCalculatorEntryActivity extends AppCompatActivity {

    Spinner cropSp,nSourceSp,kSourceSp, pSourceSp;
    EditText yieldTxt,nSourcePriceTxt, kSourcePriceTxt, pSourcePriceTxt;
    CropSpinnerAdapter cropsSpinnerAdapter,nSourceSpinnerAdapter,kSourceSpinnerAdapter,pSourceSpinnerAdapter;
    MyFarmDbHandlerSingleton dbHandler;
    CheckBox economicImpactCheck;
    Button calculateBtn;
    LinearLayout econImpactLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_nutrients_calculator_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeViews();



    }

    public void initializeViews(){
        cropSp = findViewById(R.id.sp_crop_nutrients_calculator_crop);

        pSourceSp = findViewById(R.id.sp_crop_nutrients_calculator_pSource_fert);
        kSourceSp = findViewById(R.id.sp_crop_nutrients_calculator_kSource_fert);
        nSourceSp = findViewById(R.id.sp_crop_nutrients_calculator_nSource_fert);
        yieldTxt = findViewById(R.id.txt_crop_nutrients_calculator_yield);

        nSourcePriceTxt = findViewById(R.id.txt_crop_nutrients_calculator_nSource_price);
        kSourcePriceTxt = findViewById(R.id.txt_crop_nutrients_calculator_kSource_price);
        pSourcePriceTxt = findViewById(R.id.txt_crop_nutrients_calculator_pSource_price);
        economicImpactCheck = findViewById(R.id.check_crop_nutrients_calculator_econ_impact);
        econImpactLayout = findViewById(R.id.layout_crop_nutrients_calculator_econ_impact);
        calculateBtn = findViewById(R.id.btn_crop_nutrients_calculator_calculate);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        cropsSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Crop",this);
        cropSp.setAdapter(cropsSpinnerAdapter);
        ArrayList<CropItem> cropItems = dbHandler.getCropItemsForNutrientRemoval();
        for(CropItem cropItem: cropItems){
            cropsSpinnerAdapter.add(cropItem);
        }

        nSourceSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",this);
        pSourceSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",this);
        kSourceSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",this);

        ArrayList<CropFertilizer> npkCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NPK);
        for(CropFertilizer cropFertilizer: npkCropFertilizers){
            nSourceSpinnerAdapter.add(cropFertilizer);
            kSourceSpinnerAdapter.add(cropFertilizer);
            pSourceSpinnerAdapter.add(cropFertilizer);
        }
        nSourceSp.setAdapter(nSourceSpinnerAdapter);

        ArrayList<CropFertilizer> kSourceCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_POTASSIC);
        for(CropFertilizer cropFertilizer: kSourceCropFertilizers){
            kSourceSpinnerAdapter.add(cropFertilizer);
        }
        kSourceSp.setAdapter(kSourceSpinnerAdapter);

        ArrayList<CropFertilizer> nitrogenousCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NITROGENOUS);

        for(CropFertilizer cropFertilizer: nitrogenousCropFertilizers){
            pSourceSpinnerAdapter.add(cropFertilizer);
            nSourceSpinnerAdapter.add(cropFertilizer);
        }
        pSourceSp.setAdapter(pSourceSpinnerAdapter);

        cropSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    CropItem cropItem = (CropItem)((CropSpinnerItem)cropSp.getSelectedItem());
                    CropNutrientsCalculator.getInstance().setCrop(cropItem);
                    System.out.println("REMOVED PER TONNES: N="+cropItem.getnRemoved()+" P = "+cropItem.getpRemoved()+" K ="+cropItem.getkRemoved());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        economicImpactCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    econImpactLayout.setVisibility(View.VISIBLE);
                }else{
                    econImpactLayout.setVisibility(View.GONE);
                }
            }
        });
        
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    try {
                        CropItem cropItem = (CropItem)((CropSpinnerItem)cropSp.getSelectedItem());
                        CropNutrientsCalculator.getInstance().setCrop(cropItem);
                        CropNutrientsCalculator.getInstance().setEstimatedYield(Float.parseFloat(yieldTxt.getText().toString()));
                        CropNutrientsCalculator.getInstance().setEconomicImpactRequired(economicImpactCheck.isChecked());
                        if(economicImpactCheck.isChecked()){
                            CropNutrientsCalculator.getInstance().setNitrogenSourceFertilizer((CropFertilizer)((CropSpinnerItem) nSourceSp.getSelectedItem()));
                            CropNutrientsCalculator.getInstance().setPotassiumSourceFertilizer((CropFertilizer)((CropSpinnerItem) kSourceSp.getSelectedItem()));
                            CropNutrientsCalculator.getInstance().setPhosphorusSourceFertilizer((CropFertilizer)((CropSpinnerItem) pSourceSp.getSelectedItem()));
                            CropNutrientsCalculator.getInstance().setNitrogenSourcePrice(Float.parseFloat(nSourcePriceTxt.getText().toString()));
                            CropNutrientsCalculator.getInstance().setPhosphorusSourcePrice(Float.parseFloat(pSourcePriceTxt.getText().toString()));
                            CropNutrientsCalculator.getInstance().setPotassiumSourcePrice(Float.parseFloat(kSourcePriceTxt.getText().toString()));

                        }
                        startActivity(new Intent(CropNutrientsCalculatorEntryActivity.this,CropNutrientsCalculatorResults.class));


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public boolean validateEntries(){
        String message = null;
        if(yieldTxt.getText().toString().isEmpty()){
            message = getString(R.string.estimated_yield_not_entered);
            yieldTxt.requestFocus();
        }
        else if(cropSp.getSelectedItemPosition()==0){
            message = getString(R.string.crop_not_selected);
            cropSp.requestFocus();
        }

        else if(economicImpactCheck.isChecked()){
           if(nSourcePriceTxt.getText().toString().isEmpty()){
                message = getString(R.string.missing_unit_price);
               nSourcePriceTxt.requestFocus();
            }
            else if(kSourcePriceTxt.getText().toString().isEmpty()){
                message = getString(R.string.missing_unit_price);
               kSourcePriceTxt.requestFocus();
            }
            else if(pSourcePriceTxt.getText().toString().isEmpty()){
                message = getString(R.string.missing_unit_price);
               pSourcePriceTxt.requestFocus();
            }
        }
        if(message != null){
            Toast.makeText(CropNutrientsCalculatorEntryActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
