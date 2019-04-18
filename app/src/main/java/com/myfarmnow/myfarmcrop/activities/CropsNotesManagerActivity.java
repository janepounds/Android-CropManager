package com.myfarmnow.myfarmcrop.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CropsNotesManagerActivity extends AppCompatActivity {

    EditText dateTxt, descriptionTxt;


    Button saveBtn;
    CropNote cropNote;
    MyFarmDbHandlerSingleton dbHandler;
    String cropId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_notes_manager);
        if(getIntent().hasExtra("cropNote")){
            cropNote = (CropNote)getIntent().getSerializableExtra("cropNote");

        }
        else if(getIntent().hasExtra("cropId")){
            cropId =getIntent().getStringExtra("cropId");
        }
        else{
            finish();
        }
        initializeForm();


    }

    public void initializeForm(){

        dateTxt = findViewById(R.id.txt_crop_note_date);
        descriptionTxt = findViewById(R.id.txt_crop_note_description);
        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(dateTxt,this);

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

                    Intent toCropNotesList = new Intent(CropsNotesManagerActivity.this, CropsNotesListActivity.class);
                    // toCropNotesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    toCropNotesList.putExtra("cropId",cropNote.getParentId());
                    startActivity(toCropNotesList);
                }
            }
        });



        fillViews();
    }


    public void saveFields(){
        cropNote = new CropNote();
        cropNote.setParentId(cropId);
        cropNote.setNotes(descriptionTxt.getText().toString());
        cropNote.setDate(dateTxt.getText().toString());
        cropNote.setIsFor(CropNote.IS_FOR_CROP);


        dbHandler.insertCropNote(cropNote);


    }

    public void updateField(){
        if(cropNote != null){


            cropNote.setNotes(descriptionTxt.getText().toString());
            cropNote.setDate(dateTxt.getText().toString());
            cropNote.setIsFor(CropNote.IS_FOR_CROP);
            dbHandler.updateCropNote(cropNote);
        }

    }

    public void fillViews(){
        if(cropNote != null){


            dateTxt.setText(cropNote.getDate());
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

        if(message != null){
            Toast.makeText(CropsNotesManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

}
