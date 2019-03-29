package com.myfarmnow.myfarmcrop.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

    Spinner cropSp, npkSp,potassicSp, nitrogenousSp,unitsSp;
    EditText nitrogenTxt, phosphateTxt, potassiumTxt,npkPriceTxt, potassicPriceTxt, nitrogenousPriceTxt,areaTxt;
    TextView npkCompoTxt, potassicCompoTxt, nitrogenousCompoTxt;
    CropSpinnerAdapter cropsSpinnerAdapter,npkSpinnerAdapter,potassicSpinnerAdapter,nitrogenousSpinnerAdapter;
    MyFarmDbHandlerSingleton dbHandler;
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
        unitsSp = findViewById(R.id.sp_crop_fertililizer_calculator_units);
        nitrogenTxt = findViewById(R.id.txt_crop_fertililizer_calculator_nitrogen);
        phosphateTxt = findViewById(R.id.txt_crop_fertililizer_calculator_phosphate);
        potassiumTxt = findViewById(R.id.txt_crop_fertililizer_calculator_potassium);
        nitrogenousSp = findViewById(R.id.sp_crop_fertililizer_calculator_nitrogenous_fert);
        potassicSp = findViewById(R.id.sp_crop_fertililizer_calculator_potassic_fert);
        potassicSp = findViewById(R.id.sp_crop_fertililizer_calculator_npk_fert);
        potassicCompoTxt = findViewById(R.id.txt_crop_fertililizer_calculator_potassic_compo);
        npkCompoTxt = findViewById(R.id.txt_crop_fertililizer_calculator_npk_compo);
        areaTxt = findViewById(R.id.txt_crop_fertililizer_calculator_area);
        nitrogenousCompoTxt = findViewById(R.id.txt_crop_fertililizer_calculator_nitrogenous_compo);
        npkPriceTxt = findViewById(R.id.txt_crop_fertililizer_calculator_npk_price);
        potassicPriceTxt = findViewById(R.id.txt_crop_fertililizer_calculator_potassic_price);
        nitrogenousPriceTxt = findViewById(R.id.txt_crop_fertililizer_calculator_nitrogenous_price);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        cropsSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Crop",this);
        ArrayList<CropItem> cropItems = dbHandler.getCropItems();
        for(CropItem cropItem: cropItems){
            cropsSpinnerAdapter.add(cropItem);
        }
        cropSp.setAdapter(cropsSpinnerAdapter);
        npkSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",this);
        ArrayList<CropFertilizer> npkCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NPK);
        for(CropFertilizer cropFertilizer: npkCropFertilizers){
            npkSpinnerAdapter.add(cropFertilizer);
        }
        npkSp.setAdapter(npkSpinnerAdapter);
        potassicSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",this);
        ArrayList<CropFertilizer> potassicCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_POTASSIC);
        for(CropFertilizer cropFertilizer: potassicCropFertilizers){
            npkSpinnerAdapter.add(cropFertilizer);
        }
        potassicSp.setAdapter(potassicSpinnerAdapter);
        nitrogenousSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Fertilizer",this);
        ArrayList<CropFertilizer> nitrogenousCropFertilizers = dbHandler.getCropFertilizers(CropDatabaseInitializerSingleton.FERTILIZER_TYPE_NITROGENOUS);
        for(CropFertilizer cropFertilizer: nitrogenousCropFertilizers){
            npkSpinnerAdapter.add(cropFertilizer);
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
                    CropFertilizerCalculator.getInstance().setNpkFertilizer(cropFertilizer);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
