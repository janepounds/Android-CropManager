package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropScouting;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;

public class CropScoutingManagerActivity extends AppCompatActivity {



    EditText scoutingDateTxt,scoutingMethodTxt,costTxt,remarksTxt;
    Spinner infestedSpinner,infestationTypeSpinner,infestationSpinner,infestationLevelSpinner;
    LinearLayout infestationShowHideLayout;
    Button saveBtn;
    CropSpinnerAdapter infestationAdapter;
    MyFarmDbHandlerSingleton dbHandler;
    CropScouting cropScouting=null;
    String cropId;
    boolean infestationSet = false;

    ArrayList<CropSpinnerItem> diseaseArrayList= new ArrayList<>();
    ArrayList<CropSpinnerItem> nematodeArrayList= new ArrayList<>();
    ArrayList<CropSpinnerItem>  pestArrayList = new ArrayList<>();
    ArrayList<CropSpinnerItem>  rodentArrayList = new ArrayList<>();
    ArrayList<CropSpinnerItem>  weedArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_scouting_manager);
        if(getIntent().hasExtra("cropScouting")){
            cropScouting = (CropScouting) getIntent().getSerializableExtra("cropScouting");
        }
        if(getIntent().hasExtra("cropId")){
            cropId =getIntent().getStringExtra("cropId");
        }
        else{
            finish();
        }
        initializeForm();
    }

    public void initializeForm(){

        scoutingDateTxt = findViewById(R.id.txt_crop_scouting_date);
        scoutingMethodTxt = findViewById(R.id.txt_crop_scouting_method);
        infestedSpinner = findViewById(R.id.sp_crop_scouting_infested);
        infestationTypeSpinner = findViewById(R.id.sp_crop_scouting_infestation_type);
        infestationSpinner = findViewById(R.id.sp_crop_scouting_infestation);
        infestationLevelSpinner = findViewById(R.id.sp_crop_scouting_infestation_level);
        costTxt = findViewById(R.id.txt_crop_scouting_cost);
        remarksTxt = findViewById(R.id.txt_crop_scouting_remarks);
        infestationShowHideLayout = findViewById(R.id.crop_scouting_show_hide_infestation);


        infestedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==1){
                    infestationShowHideLayout.setVisibility(View.VISIBLE);
                }
                else if(position==2){
                    infestationShowHideLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        infestationAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Infestation",this);
        infestationSpinner.setAdapter(infestationAdapter);
        String disease [] = getResources().getStringArray(R.array.sp_crop_scouting_infestation_disease);
        String nematode [] = getResources().getStringArray(R.array.sp_crop_scouting_infestation_nematode);
        String pest [] = getResources().getStringArray(R.array.sp_crop_scouting_infestation_pest);
        String rodent [] = getResources().getStringArray(R.array.sp_crop_scouting_infestation_rodent);
        String weed [] = getResources().getStringArray(R.array.sp_crop_scouting_infestation_weed);

        for(String x: disease){
            diseaseArrayList.add(new InfestationTypeSpinnerItem(x));
        }
        for(String x: nematode){
            nematodeArrayList.add(new InfestationTypeSpinnerItem(x));
        }
        for(String x: pest){
            pestArrayList.add(new InfestationTypeSpinnerItem(x));
        }
        for(String x: rodent){
            rodentArrayList.add(new InfestationTypeSpinnerItem(x));
        }
        for(String x: weed){
            weedArrayList.add(new InfestationTypeSpinnerItem(x));
        }
        infestationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getItemAtPosition(position).toString();
                if(selection.toLowerCase().equals("disease")){
                    infestationSpinner.setEnabled(true);
                    infestationAdapter.changeItems(diseaseArrayList);

                }
                else if(selection.toLowerCase().equals("nematode")){
                    infestationSpinner.setEnabled(true);
                    infestationAdapter.changeItems(nematodeArrayList);
                }
                else if(selection.toLowerCase().equals("pest")){
                    infestationSpinner.setEnabled(true);
                    infestationAdapter.changeItems(pestArrayList);
                }
                else if(selection.toLowerCase().equals("rodent")){
                    infestationSpinner.setEnabled(true);
                    infestationAdapter.changeItems(rodentArrayList);
                }
                else if(selection.toLowerCase().equals("weed")){
                    infestationSpinner.setEnabled(true);
                    infestationAdapter.changeItems(weedArrayList);
                }
                else{
                    infestationSpinner.setEnabled(false);
                }
                if(!infestationSet && cropScouting != null){
                    CropDashboardActivity.selectSpinnerItemById(infestationSpinner, cropScouting.getInfestation());
                    infestationSet =true;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(scoutingDateTxt,this);

        ((ArrayAdapter)infestedSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)infestationTypeSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)infestationSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)infestationLevelSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropScouting==null){
                        saveScouting();
                    }
                    else{
                        updateScouting();
                    }

                    Intent cropScouting = new Intent(CropScoutingManagerActivity.this, CropScoutingListActivity.class);
                    cropScouting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    cropScouting.putExtra("cropId",cropId);
                    startActivity(cropScouting);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });


        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        fillViews();
    }
    public void saveScouting() {
        cropScouting = new CropScouting();
        cropScouting.setUserId(CropDashboardActivity.getPreferences("userId", this));
        cropScouting.setCropId(cropId);
        cropScouting.setDate(scoutingDateTxt.getText().toString());
        cropScouting.setMethod(scoutingMethodTxt.getText().toString());
        cropScouting.setInfested(infestedSpinner.getSelectedItem().toString());
        cropScouting.setInfestationType(infestationTypeSpinner.getSelectedItem().toString());
        cropScouting.setInfestation(infestationSpinner.getSelectedItem().toString());
        cropScouting.setInfestationLevel(infestationLevelSpinner.getSelectedItem().toString());
        cropScouting.setCost(Float.parseFloat(costTxt.getText().toString()));
        cropScouting.setRemarks(remarksTxt.getText().toString());

        dbHandler.insertCropScouting(cropScouting);
    }

    public void updateScouting(){
        if(cropScouting != null){
            cropScouting.setUserId(CropDashboardActivity.getPreferences("userId", this));
            cropScouting.setCropId(cropId);
            cropScouting.setDate(scoutingDateTxt.getText().toString());
            cropScouting.setMethod(scoutingMethodTxt.getText().toString());
            cropScouting.setInfested(infestedSpinner.getSelectedItem().toString());
            cropScouting.setInfestationType(infestationTypeSpinner.getSelectedItem().toString());
            cropScouting.setInfestation(infestationSpinner.getSelectedItem().toString());
            cropScouting.setInfestationLevel(infestationLevelSpinner.getSelectedItem().toString());
            cropScouting.setCost(Float.parseFloat(costTxt.getText().toString()));
            cropScouting.setRemarks(remarksTxt.getText().toString());

            dbHandler.updateCropScouting(cropScouting);
        }
    }
    public void fillViews(){
        if(cropScouting != null){
            CropDashboardActivity.selectSpinnerItemByValue(infestedSpinner, cropScouting.getInfested());
            CropDashboardActivity.selectSpinnerItemByValue(infestationTypeSpinner, cropScouting.getInfestationType());
           // CropDashboardActivity.selectSpinnerItemById(infestationSpinner, cropScouting.getInfestation());
            CropDashboardActivity.selectSpinnerItemByValue(infestationLevelSpinner, cropScouting.getInfestationLevel());
            scoutingDateTxt.setText(cropScouting.getDate());
            scoutingMethodTxt.setText(cropScouting.getMethod());
            costTxt.setText(cropScouting.getCost()+"");
            remarksTxt.setText(cropScouting.getRemarks());

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateEntries(){
        String message = null;
        if(scoutingDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.scounting_date_not_selected_message);
            scoutingDateTxt.requestFocus();
        }
        else if(costTxt.getText().toString().isEmpty()){
            message = getString(R.string.cost_not_entered);
            costTxt.requestFocus();
        }
        else if(infestedSpinner.getSelectedItemPosition()==0) {
            message = getString(R.string.infested_not_selected);
            infestedSpinner.requestFocus();
        }


        if(message != null){
            Toast.makeText(CropScoutingManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }





    public class InfestationTypeSpinnerItem implements CropSpinnerItem {

        String value;
        public InfestationTypeSpinnerItem(String  value){
            this.value =value;
        }

        @Override
        public String getId() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }


    }


}
