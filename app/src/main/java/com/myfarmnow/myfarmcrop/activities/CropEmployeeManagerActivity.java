package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import com.myfarmnow.myfarmcrop.models.CropEmployee;

public class CropEmployeeManagerActivity extends AppCompatActivity {

    CropEmployee cropEmployee= null;
    EditText titleTxt,firstNameTxt,lastNameTxt,payRateTxt,reportsToTxt,phoneTxt,mobileTxt,employeeIdTxt,addressTxt,emailTxt,date0fBirthTxt,hireDateTxt ;
    Spinner genderTxt, employeeStatusTxt, perTxt,payTypeTxt;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_employee_manager);


        if(getIntent().hasExtra("cropEmployee")){
            cropEmployee = (CropEmployee)getIntent().getSerializableExtra("cropEmployee");
        }
        initializeForm();
    }
    public void initializeForm() {

        titleTxt = findViewById(R.id.txt_crop_employee_title);
        firstNameTxt = findViewById(R.id.txt_crop_employee_first_name);
        lastNameTxt = findViewById(R.id.txt_crop_employee_last_name);
        phoneTxt = findViewById(R.id.txt_crop_employee_phone);
        mobileTxt = findViewById(R.id.txt_crop_employee_mobile);
        employeeIdTxt = findViewById(R.id.txt_crop_employee_id);
        genderTxt = findViewById(R.id.txt_crop_employee_gender);
        addressTxt = findViewById(R.id.txt_crop_employee_address);
        emailTxt = findViewById(R.id.txt_crop_employee_email);
        date0fBirthTxt = findViewById(R.id.txt_crop_employee_date_of_birth);
        hireDateTxt = findViewById(R.id.txt_crop_employee_hire_date);
        employeeStatusTxt = findViewById(R.id.txt_crop_employee_employment_status);
        payRateTxt = findViewById(R.id.txt_crop_employee_pay_rate);
       perTxt= findViewById(R.id.txt_crop_employee_per);
        payTypeTxt= findViewById(R.id.txt_crop_employee_pay_type);
        reportsToTxt= findViewById(R.id.txt_crop_employee_reports_to);
        saveBtn = findViewById(R.id.btn_save);


        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        DashboardActivity.addDatePicker(date0fBirthTxt,this);
        DashboardActivity.addDatePicker(hireDateTxt,this);

        ((ArrayAdapter)genderTxt.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)employeeStatusTxt.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)perTxt.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)payTypeTxt.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


        fillViews();
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropEmployee ==null){
                        saveEmployee();
                    }
                    else{
                        updateEmployee();
                    }

                    Intent toCropEmployeesList = new Intent(CropEmployeeManagerActivity.this, CropEmployeesListActivity.class);
                    toCropEmployeesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropEmployeesList);
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
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
        genderTxt.setOnItemSelectedListener(onItemSelectedListener);
        employeeStatusTxt.setOnItemSelectedListener(onItemSelectedListener);
        perTxt.setOnItemSelectedListener(onItemSelectedListener);
        payTypeTxt.setOnItemSelectedListener(onItemSelectedListener);

    }
    public void saveEmployee(){
        cropEmployee = new CropEmployee();
        cropEmployee.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        cropEmployee.setTitle(titleTxt.getText().toString());
        cropEmployee.setPhone( phoneTxt.getText().toString());
        cropEmployee.setMobile( mobileTxt.getText().toString());
        cropEmployee.setEmail( emailTxt.getText().toString());
        cropEmployee.setFirstName(firstNameTxt.getText().toString());
        cropEmployee.setLastName(lastNameTxt.getText().toString());
        cropEmployee.setEmployeeId(employeeIdTxt.getText().toString());
        cropEmployee.setGender(genderTxt.getSelectedItem().toString());
        cropEmployee.setAddress(addressTxt.getText().toString());
        cropEmployee.setEmail(emailTxt.getText().toString());
        cropEmployee.setDateOfBirth(date0fBirthTxt.getText().toString());
        cropEmployee.setHireDate(hireDateTxt.getText().toString());
        cropEmployee.setEmploymentStatus(employeeStatusTxt.getSelectedItem().toString());
        cropEmployee.setPayAmount(Float.parseFloat(payRateTxt.getText().toString()));
        cropEmployee.setPayRate(perTxt.getSelectedItem().toString());
        cropEmployee.setPayType(payTypeTxt.getSelectedItem().toString());
        cropEmployee.setSupervisor(reportsToTxt.getText().toString());




        dbHandler.insertCropEmployee(cropEmployee);


    }

    public void updateEmployee(){
        if(cropEmployee !=null){

            cropEmployee.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            cropEmployee.setTitle(titleTxt.getText().toString());
            cropEmployee.setPhone( phoneTxt.getText().toString());
            cropEmployee.setMobile( mobileTxt.getText().toString());
            cropEmployee.setEmail( emailTxt.getText().toString());
            cropEmployee.setFirstName(firstNameTxt.getText().toString());
            cropEmployee.setLastName(lastNameTxt.getText().toString());
            cropEmployee.setEmployeeId(employeeIdTxt.getText().toString());
            cropEmployee.setGender(genderTxt.getSelectedItem().toString());
            cropEmployee.setAddress(addressTxt.getText().toString());
            cropEmployee.setEmail(emailTxt.getText().toString());
            cropEmployee.setDateOfBirth(date0fBirthTxt.getText().toString());
            cropEmployee.setHireDate(hireDateTxt.getText().toString());
            cropEmployee.setEmploymentStatus(employeeStatusTxt.getSelectedItem().toString());
            cropEmployee.setPayAmount(Float.parseFloat(payRateTxt.getText().toString()));
            cropEmployee.setPayRate(perTxt.getSelectedItem().toString());
            cropEmployee.setPayType(payTypeTxt.getSelectedItem().toString());
            cropEmployee.setSupervisor(reportsToTxt.getText().toString());
            dbHandler.updateCropEmployee(cropEmployee);

        }
    }

    public void fillViews(){
        if(cropEmployee !=null){
            titleTxt.setText(cropEmployee.getTitle());
            DashboardActivity.selectSpinnerItemByValue(genderTxt,cropEmployee.getGender());
            DashboardActivity.selectSpinnerItemByValue(employeeStatusTxt,cropEmployee.getEmploymentStatus());
            DashboardActivity.selectSpinnerItemByValue(perTxt,cropEmployee.getPayRate());
            DashboardActivity.selectSpinnerItemByValue(payTypeTxt,cropEmployee.getPayType());

            firstNameTxt.setText(cropEmployee.getFirstName());
            lastNameTxt.setText(cropEmployee.getLastName());
            phoneTxt.setText(cropEmployee.getPhone());
            mobileTxt.setText(cropEmployee.getMobile());
            emailTxt.setText(cropEmployee.getEmail());
            employeeIdTxt.setText(cropEmployee.getEmployeeId());
            addressTxt.setText(cropEmployee.getAddress());
           date0fBirthTxt.setText(cropEmployee.getDateOfBirth());
            hireDateTxt.setText(cropEmployee.getHireDate());
            payRateTxt.setText(cropEmployee.getPayAmount()+"");
            reportsToTxt.setText(cropEmployee.getSupervisor());


            saveBtn.setText(R.string.btn_update_label);
        }
    }
    public boolean validateEntries(){
        String message = null;
        if(titleTxt.getText().toString().isEmpty()){
            message = getString(R.string.title_not_entered_message);
            titleTxt.requestFocus();
        }
        else if(lastNameTxt.getText().toString().isEmpty()){
            message = getString(R.string.last_name_not_entered_message);
            lastNameTxt.requestFocus();
        }
        else if(firstNameTxt.getText().toString().isEmpty()){
            message = getString(R.string.first_name_not_entered);
            firstNameTxt.requestFocus();
        }
        else if(genderTxt.getSelectedItemPosition()==0){
            message =getString(R.string.gender_not_selected);
            genderTxt.requestFocus();
        }

        else if(employeeStatusTxt.getSelectedItemPosition()==0){
            message = getString(R.string.employment_status_not_selected);
            employeeStatusTxt.requestFocus();
        }
        else if(perTxt.getSelectedItemPosition()==0){
            message = getString(R.string.per_not_selected);
            perTxt.requestFocus();
        }
        else if(payTypeTxt.getSelectedItemPosition()==0){
            message = getString(R.string.pay_type_not_selected);
            payTypeTxt.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropEmployeeManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


}
