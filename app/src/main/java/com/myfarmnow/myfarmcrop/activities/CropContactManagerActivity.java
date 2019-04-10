package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropContact;

public class CropContactManagerActivity extends AppCompatActivity {

    CropContact cropContact=null;
    EditText nameTxt,businessNameTxt,addressTxt,phoneNumberTxt,emailTxt,websiteTxt;
    Spinner typeSpinner;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_contact_manager);

        if(getIntent().hasExtra("cropContact")){
            cropContact = (CropContact) getIntent().getSerializableExtra("cropContact");
        }
        initializeForm();

    }


    public void initializeForm(){

        typeSpinner = findViewById(R.id.sp_crop_contact_type);
        nameTxt =findViewById(R.id.txt_crop_contact_name);
        businessNameTxt =findViewById(R.id.txt_crop_contact_business_name);
        addressTxt =findViewById(R.id.txt_crop_contact_address);
        phoneNumberTxt =findViewById(R.id.txt_crop_contact_phone_number);
        emailTxt =findViewById(R.id.txt_crop_contact_email);
        websiteTxt =findViewById(R.id.txt_crop_contact_website);


        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ((ArrayAdapter)typeSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropContact==null){
                        saveContacts();
                    }
                    else{
                        updateContacts();
                    }

                    Intent toCropContactsList = new Intent(CropContactManagerActivity.this, CropContactsListActivity.class);
                    toCropContactsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropContactsList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        fillViews();
    }


    public void saveContacts(){
        cropContact = new CropContact();
        cropContact.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropContact.setType(typeSpinner.getSelectedItem().toString());
        cropContact.setName(nameTxt.getText().toString());
        cropContact.setBusinessName(businessNameTxt.getText().toString());
        cropContact.setAddress(addressTxt.getText().toString());
        cropContact.setPhoneNumber(phoneNumberTxt.getText().toString());
        cropContact.setEmail(emailTxt.getText().toString());
        cropContact.setWebsite(websiteTxt.getText().toString());

        dbHandler.insertCropContact(cropContact);



    }

    public void updateContacts(){
        if(cropContact != null){
            cropContact.setUserId(CropDashboardActivity.getPreferences("userId",this));
            cropContact.setType(typeSpinner.getSelectedItem().toString());
            cropContact.setName(nameTxt.getText().toString());
            cropContact.setBusinessName(businessNameTxt.getText().toString());
            cropContact.setAddress(addressTxt.getText().toString());
            cropContact.setPhoneNumber(phoneNumberTxt.getText().toString());
            cropContact.setEmail(emailTxt.getText().toString());
            cropContact.setWebsite(websiteTxt.getText().toString());

            dbHandler.updateCropContact(cropContact);
        }
    }
    public void fillViews(){
        if(cropContact != null){
            CropDashboardActivity.selectSpinnerItemByValue(typeSpinner, cropContact.getType());
            nameTxt.setText(cropContact.getName());
            businessNameTxt.setText(cropContact.getBusinessName());
            addressTxt.setText(cropContact.getAddress());
            phoneNumberTxt.setText(cropContact.getPhoneNumber()+"");
            emailTxt.setText(cropContact.getEmail());
            websiteTxt.setText(cropContact.getWebsite());

        }

    }

    public boolean validateEntries(){
        String message = null;
        if(typeSpinner.getSelectedItemPosition()==0){
            message = getString(R.string.contact_type_not_selected_message);
            typeSpinner.requestFocus();
        }
        else if(nameTxt.getText().toString().isEmpty()){
            message = getString(R.string.name_not_entered);
            nameTxt.requestFocus();
        }
        else if(addressTxt.getText().toString().isEmpty()){
            message = getString(R.string.address_not_entered_message);
            addressTxt.requestFocus();
        }
        else if(phoneNumberTxt.getText().toString().isEmpty()){
            message = getString(R.string.phone_number_not_entered);
            phoneNumberTxt.requestFocus();
        }



        if(message != null){
            Toast.makeText(CropContactManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }



}
