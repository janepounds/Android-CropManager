package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.database.MyFarmRoomDatabase;
import com.myfarmnow.myfarmcrop.databinding.FragmentAddFieldBinding;


public class AddFieldFragment extends Fragment {
    private FragmentAddFieldBinding binding;
    private Context context;
    com.myfarmnow.myfarmcrop.models.farmrecords.CropField cropField =null;
    MyFarmDbHandlerSingleton dbHandler;
    private MyFarmRoomDatabase myFarmRoomDatabase;
    NavController navController;
    private CropField fieldsTable;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_field,container,false);
        myFarmRoomDatabase = MyFarmRoomDatabase.getInstance(context);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
        if(requireActivity().getIntent().hasExtra("cropField")){
            cropField = (com.myfarmnow.myfarmcrop.models.farmrecords.CropField)requireActivity().getIntent().getSerializableExtra("cropField");
        }
        initializeForm();




    }

    public void initializeForm(){

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        ((ArrayAdapter)binding.spCropFieldUnits.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)binding.spCropFieldStatus.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)binding.spCropFieldType.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position == 0){
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }
                  else  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.black)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }



        };


        binding.spCropFieldUnits.setOnItemSelectedListener(onItemSelectedListener);
        binding.spCropFieldStatus.setOnItemSelectedListener(onItemSelectedListener);
        binding.spCropFieldType.setOnItemSelectedListener(onItemSelectedListener);
        binding.spCropFieldUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                String selection = parent.getItemAtPosition(position).toString();
                if(selection.toLowerCase().equals("acres")){

                    binding.txtCropsEstimatedRevenueUnits.setText("Acres");
                    binding.txtCropsCroppableAreaUnits.setText("Acres");

                }
                else if(selection.toLowerCase().equals("hectares")){

                    binding.txtCropsEstimatedRevenueUnits.setText("Hectares");
                    binding.txtCropsCroppableAreaUnits.setText("Hectares");


                }
                else if(selection.toLowerCase().equals("square miles")){
                    binding.txtCropsEstimatedRevenueUnits.setText("Square Miles");
                    binding.txtCropsCroppableAreaUnits.setText("Square Miles");




                }


            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


        //DashboardActivity.selectSpinnerItemByValue(unitsSpinner,CropSettingsSingleton.getInstance().getAreaUnits());

        fillViews();
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
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
                    //navigate to field list
                    navController.navigate(R.id.action_addFieldFragment_to_fieldsListFragment);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }

    public void saveFields(){
        cropField = new CropField();
        cropField.setUserId(DashboardActivity.getPreferences("userId",context));
        cropField.setFieldName(binding.txtCropFieldName.getText().toString());
        cropField.setFieldType( binding.spCropFieldType.getSelectedItem().toString());
        cropField.setStatus(binding.spCropFieldStatus.getSelectedItem().toString());
        cropField.setTotalArea(Float.parseFloat(binding.txtCropFieldTotalArea.getText().toString()) );
        cropField.setCroppableArea(Float.parseFloat(binding.txtCropFieldCroppableArea.getText().toString()));
        cropField.setUnits(binding.spCropFieldUnits.getSelectedItem().toString());


        dbHandler.insertCropField(cropField);
        Log.w("userId",DashboardActivity.getPreferences("userId",context));

    }
    public void updateField(){
        if(cropField !=null){

            cropField.setFieldName(binding.txtCropFieldName.getText().toString());
            cropField.setFieldType( binding.spCropFieldType.getSelectedItem().toString());
            cropField.setStatus(binding.spCropFieldStatus.getSelectedItem().toString());
            cropField.setTotalArea(Float.parseFloat(binding.txtCropFieldTotalArea.getText().toString()));
            cropField.setCroppableArea(Float.parseFloat(binding.txtCropFieldCroppableArea.getText().toString()));
            cropField.setUnits(binding.spCropFieldUnits.getSelectedItem().toString());


            dbHandler.updateCropField(cropField);

        }
    }

    public void fillViews(){
        if(cropField !=null){
            binding.txtCropFieldName.setText(cropField.getFieldName());
            DashboardActivity.selectSpinnerItemByValue(binding.spCropFieldUnits,cropField.getUnits());
            DashboardActivity.selectSpinnerItemByValue(binding.spCropFieldType,cropField.getFieldType());
            DashboardActivity.selectSpinnerItemByValue(binding.spCropFieldStatus,cropField.getStatus());
            binding.txtCropFieldTotalArea.setText(cropField.getTotalArea()+"");
            binding.txtCropFieldCroppableArea.setText(cropField.getCroppableArea()+"");
            binding.btnSave.setText(R.string.btn_update_label);
        }
    }

    public boolean validateEntries(){
        String message = null;
        if(binding.txtCropFieldName.getText().toString().isEmpty()){
            message = getString(R.string.field_name_not_entered_message);
            binding.txtCropFieldName.requestFocus();
        }
        else if(binding.txtCropFieldTotalArea.getText().toString().isEmpty()){
            message = getString(R.string.total_area_not_entered_message);
            binding.txtCropFieldTotalArea.requestFocus();
        }
        else if(binding.spCropFieldUnits.getSelectedItemPosition()==0){
            message = getString(R.string.units_not_selected);
            binding.spCropFieldUnits.requestFocus();
        }
        else if( binding.spCropFieldStatus.getSelectedItemPosition()==0){
            message = getString(R.string.field_status_is_not_selected);
            binding.spCropFieldStatus.requestFocus();
        }
        else if( binding.spCropFieldStatus.getSelectedItemPosition()==0){
            message = getString(R.string.field_type_not_selected);
            binding.spCropFieldType.requestFocus();
        }


        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }




}