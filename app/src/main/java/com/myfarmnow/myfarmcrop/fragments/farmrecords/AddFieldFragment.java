package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import com.myfarmnow.myfarmcrop.activities.farmrecords.CropFieldsListActivity;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.database.MyFarmRoomDatabase;
import com.myfarmnow.myfarmcrop.databinding.FragmentAddFieldBinding;

import java.lang.ref.WeakReference;


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


//        soilCategorySpinner.setOnItemSelectedListener(onItemSelectedListener);
//        soilTypeSpinner.setOnItemSelectedListener(onItemSelectedListener);
//        watercourseSpinner.setOnItemSelectedListener(onItemSelectedListener);
        binding.spCropFieldUnits.setOnItemSelectedListener(onItemSelectedListener);
        binding.spCropFieldStatus.setOnItemSelectedListener(onItemSelectedListener);
//        layoutTypeSp.setOnItemSelectedListener(onItemSelectedListener);
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

                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }

    public void saveFields(){
            // fetch data and create produce object
        cropField = new CropField(binding.txtCropFieldName.getText().toString(),binding.spCropFieldType.getSelectedItem().toString(),binding.spCropFieldStatus.getSelectedItem().toString(),
                    binding.spCropFieldUnits.getSelectedItem().toString(), binding.txtCropFieldTotalArea.getText().toString(),binding.txtCropFieldCroppableArea.getText().toString(),12);


            // create worker thread to insert data into database
        new InsertFieldTask(AddFieldFragment.this, cropField).execute();

    }
    public void updateField(){
        if(cropField !=null){

            cropField.setField_name(binding.txtCropFieldName.getText().toString());

            cropField.setField_type( binding.spCropFieldType.getSelectedItem().toString());
            cropField.setStatus(binding.spCropFieldStatus.getSelectedItem().toString());
            cropField.setField_size(binding.txtCropFieldTotalArea.getText().toString());
            cropField.setCroppable_area(binding.txtCropFieldCroppableArea.getText().toString());
            cropField.setUnit(binding.spCropFieldUnits.getSelectedItem().toString());


            new UpdateFieldTask(AddFieldFragment.this, cropField).execute();

        }
    }

    public void fillViews(){
        if(cropField !=null){
            binding.txtCropFieldName.setText(cropField.getField_name());
            DashboardActivity.selectSpinnerItemByValue(binding.spCropFieldUnits,cropField.getUnit());
            DashboardActivity.selectSpinnerItemByValue(binding.spCropFieldType,cropField.getField_type());
            DashboardActivity.selectSpinnerItemByValue(binding.spCropFieldStatus,cropField.getStatus());
            binding.txtCropFieldTotalArea.setText(cropField.getField_size()+"");
            binding.txtCropFieldCroppableArea.setText(cropField.getCroppable_area()+"");
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

    //Room implementation
    private class InsertFieldTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddFieldFragment> fragmentReference;
        private CropField fieldsTable;
       // private ProgressDialog dialog;
        private Context context;

        // only retain a weak reference to the activity
        InsertFieldTask(AddFieldFragment context, CropField fieldsTable) {
            fragmentReference = new WeakReference<>(context);
            this.fieldsTable =fieldsTable;
            this.context = context.context;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            fragmentReference.get().myFarmRoomDatabase.fieldsDao().insert(fieldsTable);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                fragmentReference.get().requireActivity().runOnUiThread(() -> {
                    Toast.makeText(context, "Field Added", Toast.LENGTH_SHORT).show();

                });
                navController.navigate(R.id.action_addFieldFragment_to_fieldsListFragment);

            }
        }
    }
    
    private class UpdateFieldTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddFieldFragment> fragmentReference;
        private CropField fieldsTable;
        // private ProgressDialog dialog;
        private Context context;

        // only retain a weak reference to the activity
        UpdateFieldTask(AddFieldFragment context, CropField fieldsTable) {
            fragmentReference = new WeakReference<>(context);
            this.fieldsTable =fieldsTable;
            this.context = context.context;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            fragmentReference.get().myFarmRoomDatabase.fieldsDao().UpdateField(fieldsTable);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                fragmentReference.get().requireActivity().runOnUiThread(() -> {
                    Toast.makeText(context, "Field Added", Toast.LENGTH_SHORT).show();

                });
                navController.navigate(R.id.action_addFieldFragment_to_fieldsListFragment);

            }
        }
    }


}