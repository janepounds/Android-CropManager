package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.myfarmnow.myfarmcrop.models.CropField;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;

public class CropFieldManagerActivity extends AppCompatActivity {


    CropField cropFieldToEdit=null;
    EditText  fieldNameTxt,totalAreaTxt,croppableAreaTxt ;
    Spinner soilCategorySpinner,soilTypeSpinner,watercourseSpinner,unitsSpinner;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_field_manager);
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

        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropFieldToEdit==null){
                        saveFields();
                    }
                    else{
                        //updateFields();
                    }

                    Intent toCropFieldsList = new Intent(CropFieldManagerActivity.this, CropFieldsListActivity.class);
                    toCropFieldsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropFieldsList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }
    public void saveFields(){
        cropFieldToEdit = new CropField();
        cropFieldToEdit.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropFieldToEdit.setFieldName(fieldNameTxt.getText().toString());
        cropFieldToEdit.setSoilCategory( soilCategorySpinner.getSelectedItem().toString());
        cropFieldToEdit.setSoilType( soilTypeSpinner.getSelectedItem().toString());
        cropFieldToEdit.setWatercourse( watercourseSpinner.getSelectedItem().toString());
        cropFieldToEdit.setTotalArea(Float.parseFloat(totalAreaTxt.getText().toString()));
        cropFieldToEdit.setCroppableArea(Float.parseFloat(croppableAreaTxt.getText().toString()));
        cropFieldToEdit.setUnits(unitsSpinner.getSelectedItem().toString());


        dbHandler.insertCropField(cropFieldToEdit);



    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateEntries(){
        String message = null;
        if(fieldNameTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            fieldNameTxt.requestFocus();
        }
        else if(totalAreaTxt.getText().toString().isEmpty()){
            message = getString(R.string.seed_name_not_entered_message);
            totalAreaTxt.requestFocus();
        }
        else if(unitsSpinner.getSelectedItemPosition()==0){
            message = getString(R.string.usage_units_not_selected);
            unitsSpinner.requestFocus();
        }


        if(message != null){
            Toast.makeText(CropFieldManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


}
