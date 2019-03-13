package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropCultivation;

public class CropCultivationManagerActivity extends AppCompatActivity {

    EditText dateTxt, notesTxt,costTxt, operatorTxt;
    Button btn_save;
    CropCultivation cultivation;
    String cropId;
    Spinner operationTxt;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_cultivation_manager);
        if(getIntent().hasExtra("cultivation")){
            cultivation =(CropCultivation) getIntent().getSerializableExtra("cultivation");
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
        dateTxt =findViewById(R.id.txt_crop_cultivate_date);
        notesTxt =findViewById(R.id.txt_crop_cultivate_notes);

        operationTxt =findViewById(R.id.sp_crop_cultivate_operation);
        costTxt =findViewById(R.id.txt_crop_cultivate_fixed_cost);
        operatorTxt =findViewById(R.id.txt_crop_cultivate_operator);
        btn_save = findViewById(R.id.btn_save);
        CropDashboardActivity.addDatePicker(dateTxt,this);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cultivation==null){
                        saveCultivation();
                    }
                    else{
                        updateCultivation();
                    }
                    Intent toCropsList = new Intent(CropCultivationManagerActivity.this, CropCultivationsListActivity.class);
                    toCropsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    toCropsList.putExtra("fieldId",cropId);
                    startActivity(toCropsList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        fillViews();
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
    }

    public void saveCultivation(){
        cultivation = new CropCultivation();
        cultivation.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cultivation.setDate(dateTxt.getText().toString());
        cultivation.setOperator(operatorTxt.getText().toString());
        cultivation.setOperation(operationTxt.getSelectedItem().toString());
        cultivation.setCropId(cropId);
        cultivation.setNotes(notesTxt.getText().toString());
        cultivation.setCost(Float.parseFloat(costTxt.getText().toString()));
      
        dbHandler.insertCropCultivate(cultivation);

    }
    public void updateCultivation(){
        if(cultivation != null){

            cultivation.setDate(dateTxt.getText().toString());
            cultivation.setOperator(operatorTxt.getText().toString());
            cultivation.setOperation(operationTxt.getSelectedItem().toString());
            cultivation.setCropId(cropId);
            cultivation.setNotes(notesTxt.getText().toString());
            cultivation.setCost(Float.parseFloat(costTxt.getText().toString()));

            dbHandler.updateCropCultivate(cultivation);
        }
    }

    public void fillViews(){
        if(cultivation != null){
            dateTxt.setText(cultivation.getDate());
            operatorTxt.setText(cultivation.getOperator());
            CropDashboardActivity.selectSpinnerItemByValue(operationTxt, cultivation.getOperation());
            notesTxt.setText(cultivation.getNotes());
            costTxt.setText(cultivation.getCost()+"");
        }
    }

    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }
        else if(operationTxt.getSelectedItemPosition()==0){
            message = getString(R.string.operation_not_selected);
            operationTxt.requestFocus();
        }
        else if(operatorTxt.getText().toString().isEmpty()){
            message = getString(R.string.operator_not_entered);
            operatorTxt.requestFocus();
        }

        if(costTxt.getText().toString().isEmpty()){
            costTxt.setText("0");
        }



        if(message != null){
            Toast.makeText(CropCultivationManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}
