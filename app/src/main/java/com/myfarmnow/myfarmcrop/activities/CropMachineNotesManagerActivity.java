package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNote;

public class CropMachineNotesManagerActivity extends AppCompatActivity {

    //
    //
    LinearLayout categoryLayout, otherCategoryLayout;
    EditText otherCategoryTxt,dateTxt, descriptionTxt;
    Spinner categorySp;
    
    Button saveBtn;
    CropNote cropNote;
    MyFarmDbHandlerSingleton dbHandler;
    String machineId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_notes_manager);
         if(getIntent().hasExtra("cropNote")){
            cropNote = (CropNote)getIntent().getSerializableExtra("cropNote");

        }
        else if(getIntent().hasExtra("machineId")){
            machineId =getIntent().getStringExtra("machineId");
        }
        else{
            finish();
        }
        initializeForm();


    }

    public void initializeForm(){
        categoryLayout = findViewById(R.id.layout_crop_note_category);
        otherCategoryLayout = findViewById(R.id.layout_other_category);
        otherCategoryTxt = findViewById(R.id.txt_crop_note_category_other);
        categorySp = findViewById(R.id.sp_crop_note_category);
        dateTxt = findViewById(R.id.txt_crop_note_date);
        descriptionTxt = findViewById(R.id.txt_crop_note_description);
        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(dateTxt,this);
        categoryLayout.setVisibility(View.VISIBLE);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropNote ==null){
                        saveFields();
                    }
                    else{
                        updateField();
                    }

                    Intent toCropNotesList = new Intent(CropMachineNotesManagerActivity.this, CropMachineNotesListActivity.class);toCropNotesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropNotesList);
                }
            }
        });

        categorySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = categorySp.getSelectedItem().toString();
                if(category.toLowerCase().equals("other")){
                    otherCategoryLayout.setVisibility(View.VISIBLE);
                }else {
                    otherCategoryLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fillViews();
    }


    public void saveFields(){
        cropNote = new CropNote();
        cropNote.setParentId(machineId);
        cropNote.setNotes(descriptionTxt.getText().toString());
        cropNote.setDate(dateTxt.getText().toString());
        cropNote.setIsFor(CropNote.IS_FOR_MACHINE);


        if(categoryLayout.getVisibility()==View.VISIBLE){
            String category = categorySp.getSelectedItem().toString();
            if(category.toLowerCase().equals("other")){
                category = otherCategoryTxt.getText().toString();
            }
            cropNote.setCategory(category);
        }

        dbHandler.insertCropNote(cropNote);


    }

    public void updateField(){
        if(cropNote != null){
            cropNote = new CropNote();
            cropNote.setParentId(machineId);
            cropNote.setNotes(descriptionTxt.getText().toString());
            cropNote.setDate(dateTxt.getText().toString());
            cropNote.setIsFor(CropNote.IS_FOR_MACHINE);
            if(categoryLayout.getVisibility()==View.VISIBLE){
                String category = categorySp.getSelectedItem().toString();
                if(category.toLowerCase().equals("other")){

                    cropNote.setCategory(otherCategoryTxt.getText().toString());
                }
                else{
                    cropNote.setCategory(category);
                }

            }
            dbHandler.updateCropNote(cropNote);
        }

    }

    public void fillViews(){
        if(cropNote != null){

            CropDashboardActivity.selectSpinnerItemByValue(categorySp, cropNote.getCategory());
            dateTxt.setText(cropNote.getDate());

            if (categorySp.getSelectedItemPosition()==0 && cropNote.getCategory()
                     != null){
                CropDashboardActivity.selectSpinnerItemByValue(categorySp, "Other");
                otherCategoryTxt.setText(cropNote.getCategory());
            }

            descriptionTxt.setText(cropNote.getNotes());


        }


    }

    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }

        else if(descriptionTxt.getText().toString().isEmpty()){
            message = getString(R.string.description_not_entered_message);
            descriptionTxt.requestFocus();
        }
        else if(categoryLayout.getVisibility()==View.VISIBLE && categorySp.getSelectedItem().toString().toLowerCase().equals("other") &&  otherCategoryTxt.getText().toString().isEmpty()){
            message = "You must specify Category";
            otherCategoryTxt.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropMachineNotesManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

}
