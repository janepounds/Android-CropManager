package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropField;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropFieldManagerActivity extends AppCompatActivity {


    CropField cropField =null;
    EditText  fieldNameTxt,totalAreaTxt,croppableAreaTxt ;
    Spinner soilCategorySpinner,soilTypeSpinner,watercourseSpinner,unitsSpinner, typeSp, layoutTypeSp, statusSp;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_field_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("cropField")){
            cropField = (CropField)getIntent().getSerializableExtra("cropField");
        }
        initializeForm();
    }

    public void initializeForm(){

        fieldNameTxt = findViewById(R.id.txt_crop_field_name);
        soilCategorySpinner = findViewById(R.id.sp_crop_field_soil_category);
        soilTypeSpinner = findViewById(R.id.sp_crop_field_soil_type);
        watercourseSpinner = findViewById(R.id.sp_crop_field_watercourse);
        totalAreaTxt = findViewById(R.id.txt_crop_field_total_area);
        croppableAreaTxt = findViewById(R.id.txt_crop_field_croppable_area);
        unitsSpinner = findViewById(R.id.sp_crop_field_units);
        typeSp = findViewById(R.id.sp_crop_field_type);
        layoutTypeSp = findViewById(R.id.sp_crop_field_layout_type);
        statusSp = findViewById(R.id.sp_crop_field_status);

        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ((ArrayAdapter)soilCategorySpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)soilTypeSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)watercourseSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)unitsSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)layoutTypeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)statusSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)typeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               try{
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                       ((TextView) view).setTextColor(getColor(R.color.colorPrimary));

                   }
                   else {
                       ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                   }
                   ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
               }catch (Exception e){

               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        soilCategorySpinner.setOnItemSelectedListener(onItemSelectedListener);
        soilTypeSpinner.setOnItemSelectedListener(onItemSelectedListener);
        watercourseSpinner.setOnItemSelectedListener(onItemSelectedListener);
        unitsSpinner.setOnItemSelectedListener(onItemSelectedListener);
        statusSp.setOnItemSelectedListener(onItemSelectedListener);
        layoutTypeSp.setOnItemSelectedListener(onItemSelectedListener);
        typeSp.setOnItemSelectedListener(onItemSelectedListener);
//TODO SETTING DEFAULT UNITS
        //CropDashboardActivity.selectSpinnerItemByValue(unitsSpinner,CropSettingsSingleton.getInstance().getAreaUnits());

        fillViews();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropField ==null){
                        saveFields();
                    }
                    else{
                        updateField();
                    }

                    Intent toCropFieldsList = new Intent(CropFieldManagerActivity.this, CropFieldsListActivity.class);
                    toCropFieldsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropFieldsList);
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }
    public void saveFields(){
        cropField = new CropField();
        cropField.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropField.setFieldName(fieldNameTxt.getText().toString());
        cropField.setSoilCategory( soilCategorySpinner.getSelectedItem().toString());
        cropField.setSoilType( soilTypeSpinner.getSelectedItem().toString());
        cropField.setWatercourse( watercourseSpinner.getSelectedItem().toString());
        cropField.setLayoutType( layoutTypeSp.getSelectedItem().toString());
        cropField.setFieldType( typeSp.getSelectedItem().toString());
        cropField.setStatus( statusSp.getSelectedItem().toString());
        cropField.setTotalArea(Float.parseFloat(totalAreaTxt.getText().toString()));
        cropField.setCroppableArea(Float.parseFloat(croppableAreaTxt.getText().toString()));
        cropField.setUnits(unitsSpinner.getSelectedItem().toString());

        dbHandler.insertCropField(cropField);



    }
    public void updateField(){
        if(cropField !=null){

            cropField.setFieldName(fieldNameTxt.getText().toString());
            cropField.setSoilCategory( soilCategorySpinner.getSelectedItem().toString());
            cropField.setSoilType( soilTypeSpinner.getSelectedItem().toString());
            cropField.setWatercourse( watercourseSpinner.getSelectedItem().toString());
            cropField.setLayoutType( layoutTypeSp.getSelectedItem().toString());
            cropField.setFieldType( typeSp.getSelectedItem().toString());
            cropField.setStatus( statusSp.getSelectedItem().toString());
            cropField.setTotalArea(Float.parseFloat(totalAreaTxt.getText().toString()));
            cropField.setCroppableArea(Float.parseFloat(croppableAreaTxt.getText().toString()));
            cropField.setUnits(unitsSpinner.getSelectedItem().toString());
            dbHandler.updateCropField(cropField);

        }
    }

    public void fillViews(){
        if(cropField !=null){
            fieldNameTxt.setText(cropField.getFieldName());
            CropDashboardActivity.selectSpinnerItemByValue(soilCategorySpinner,cropField.getSoilCategory());
            CropDashboardActivity.selectSpinnerItemByValue(soilTypeSpinner,cropField.getSoilType());
            CropDashboardActivity.selectSpinnerItemByValue(watercourseSpinner,cropField.getWatercourse());
            CropDashboardActivity.selectSpinnerItemByValue(unitsSpinner,cropField.getUnits());
            CropDashboardActivity.selectSpinnerItemByValue(typeSp,cropField.getFieldType());
            CropDashboardActivity.selectSpinnerItemByValue(statusSp,cropField.getStatus());
            CropDashboardActivity.selectSpinnerItemByValue(layoutTypeSp,cropField.getLayoutType());

            totalAreaTxt.setText(cropField.getTotalArea()+"");
            croppableAreaTxt.setText(cropField.getCroppableArea()+"");
            saveBtn.setText(R.string.btn_update_label);
        }
    }

    public boolean validateEntries(){
        String message = null;
        if(fieldNameTxt.getText().toString().isEmpty()){
            message = getString(R.string.field_name_not_entered_message);
            fieldNameTxt.requestFocus();
        }
        else if(totalAreaTxt.getText().toString().isEmpty()){
            message = getString(R.string.total_area_not_entered_message);
            totalAreaTxt.requestFocus();
        }
        else if(unitsSpinner.getSelectedItemPosition()==0){
            message = getString(R.string.units_not_selected);
            unitsSpinner.requestFocus();
        }
        else if(statusSp.getSelectedItemPosition()==0){
            message = getString(R.string.field_status_is_not_selected);
            statusSp.requestFocus();
        }
        else if(typeSp.getSelectedItemPosition()==0){
            message = getString(R.string.field_type_not_selected);
            typeSp.requestFocus();
        }


        if(message != null){
            Toast.makeText(CropFieldManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


}
