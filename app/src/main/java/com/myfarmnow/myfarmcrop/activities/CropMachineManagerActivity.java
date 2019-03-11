package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropField;
import com.myfarmnow.myfarmcrop.models.CropMachine;

public class CropMachineManagerActivity extends AppCompatActivity {

    CropMachine cropMachineToEdit=null;
    EditText machineNameTxt,brandTxt,manufacturerTxt, modelTxt,regNumberTxt,quantityTxt,dateAcquiredTxt,purchasedFromTxt,storageLocationTxt,purchasePriceTxt ;
    Spinner categorySpinner;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_machine_manager);

        if(getIntent().hasExtra("cropMachine")){
            cropMachineToEdit = (CropMachine) getIntent().getSerializableExtra("cropMachine");
        }
        initializeForm();



    }

    public void initializeForm(){

        machineNameTxt = findViewById(R.id.txt_crop_machine_name);
        brandTxt = findViewById(R.id.txt_crop_machine_brand);
        categorySpinner = findViewById(R.id.sp_crop_machine_category);
        manufacturerTxt = findViewById(R.id.txt_crop_machine_manufacturer);
        modelTxt = findViewById(R.id.txt_crop_machine_model);
        regNumberTxt = findViewById(R.id.txt_crop_machine_reg);
       quantityTxt = findViewById(R.id.txt_crop_machine_quantity);
        dateAcquiredTxt = findViewById(R.id.txt_crop_machine_date_acquired);
        purchasedFromTxt = findViewById(R.id.txt_crop_machine_purchased_from);
        storageLocationTxt = findViewById(R.id.txt_crop_machine_storage_location);
        purchasePriceTxt = findViewById(R.id.txt_crop_machine_purchase_price);


        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(dateAcquiredTxt,this);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropMachineToEdit==null){
                        saveMachines();
                    }
                    else{
                        updateMachines();
                    }

                    Intent toCropMachinesList = new Intent(CropMachineManagerActivity.this, CropMachinesListActivity.class);
                    toCropMachinesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropMachinesList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        fillViews();
    }
    public void saveMachines(){
        cropMachineToEdit = new CropMachine();
        Log.d("ERROR", "SAVE MACHINES METHOD");
        cropMachineToEdit.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropMachineToEdit.setName(machineNameTxt.getText().toString());
        cropMachineToEdit.setBrand(brandTxt.getText().toString());
        cropMachineToEdit.setCategory(categorySpinner.getSelectedItem().toString());
        cropMachineToEdit.setManufacturer(manufacturerTxt.getText().toString());
        cropMachineToEdit.setModel(modelTxt.getText().toString());
        cropMachineToEdit.setRegistrationNumber(Integer.parseInt(regNumberTxt.getText().toString()));
        cropMachineToEdit.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
        cropMachineToEdit.setDate(dateAcquiredTxt.getText().toString());
        cropMachineToEdit.setPurchasedFrom(purchasedFromTxt.getText().toString());
        cropMachineToEdit.setStorageLocation(storageLocationTxt.getText().toString());
        cropMachineToEdit.setPurchasePrice(Float.parseFloat(purchasePriceTxt.getText().toString()));
        dbHandler.insertCropMachine(cropMachineToEdit);



    }

    public void updateMachines(){
        if(cropMachineToEdit != null){
            cropMachineToEdit.setUserId(CropDashboardActivity.getPreferences("userId",this));
            cropMachineToEdit.setName(machineNameTxt.getText().toString());
            cropMachineToEdit.setBrand(brandTxt.getText().toString());
            cropMachineToEdit.setCategory(categorySpinner.getSelectedItem().toString());
            cropMachineToEdit.setManufacturer(manufacturerTxt.getText().toString());
            cropMachineToEdit.setModel(modelTxt.getText().toString());
            cropMachineToEdit.setRegistrationNumber(Integer.parseInt(regNumberTxt.getText().toString()));
            cropMachineToEdit.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
            cropMachineToEdit.setDate(dateAcquiredTxt.getText().toString());
            cropMachineToEdit.setPurchasedFrom(purchasedFromTxt.getText().toString());
            cropMachineToEdit.setStorageLocation(storageLocationTxt.getText().toString());
            cropMachineToEdit.setPurchasePrice(Float.parseFloat(purchasePriceTxt.getText().toString()));
            dbHandler.updateCropMachine(cropMachineToEdit);
        }
    }
    public void fillViews(){
        if(cropMachineToEdit != null){
            CropDashboardActivity.selectSpinnerItemByValue(categorySpinner, cropMachineToEdit.getCategory());

            machineNameTxt.setText(cropMachineToEdit.getName());
            brandTxt.setText(cropMachineToEdit.getBrand());
            manufacturerTxt.setText(cropMachineToEdit.getManufacturer());
            modelTxt.setText(cropMachineToEdit.getModel());
            regNumberTxt.setText(cropMachineToEdit.getRegistrationNumber()+"");
            quantityTxt.setText(cropMachineToEdit.getQuantity()+"");
            dateAcquiredTxt.setText(cropMachineToEdit.getDate());
            purchasedFromTxt.setText(cropMachineToEdit.getPurchasedFrom());
            storageLocationTxt.setText(cropMachineToEdit.getStorageLocation());
            purchasePriceTxt.setText(cropMachineToEdit.getPurchasePrice()+"");
}

    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateEntries(){
        String message = null;
        if(machineNameTxt.getText().toString().isEmpty()){
            message = getString(R.string.machine_name_not_entered_message);
            machineNameTxt.requestFocus();
        }
        else if(quantityTxt.getText().toString().isEmpty()){
            message = getString(R.string.quantity_not_entered_message);
            quantityTxt.requestFocus();
        }
        else if(purchasedFromTxt.getText().toString().isEmpty()){
            message = getString(R.string.purchaed_from_not_entered_message);
            purchasedFromTxt.requestFocus();
        }
        else if(purchasePriceTxt.getText().toString().isEmpty()){
            message = getString(R.string.purchase_price_not_entered_message);
            purchasePriceTxt.requestFocus();
        }
        else if(categorySpinner.getSelectedItemPosition()==0){
            message = getString(R.string.category_not_selected);
            categorySpinner.requestFocus();
        }


        if(message != null){
            Toast.makeText(CropMachineManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

}
