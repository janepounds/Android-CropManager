package com.myfarmnow.myfarmcrop.fragments.farmrecords.crop;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class AddFieldFragment extends Fragment {
    private Context context;
    com.myfarmnow.myfarmcrop.models.farmrecords.CropField cropField = null;
    MyFarmDbHandlerSingleton dbHandler;
    NavController navController;
    private CropField fieldsTable;

    private Toolbar toolbar;
    private Spinner spCropFieldUnits, spCropFieldStatus, spCropFieldType;
    private TextView txtCropsEstimatedRevenueUnits, txtCropsCroppableAreaUnits;
    private EditText txtCropFieldName, txtCropFieldTotalArea, txtCropFieldCroppableArea;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_field, container, false);

        toolbar = view.findViewById(R.id.toolbar_add_field_fragment);
        spCropFieldUnits = view.findViewById(R.id.sp_crop_field_units);
        spCropFieldStatus = view.findViewById(R.id.sp_crop_field_status);
        spCropFieldType = view.findViewById(R.id.sp_crop_field_type);
        txtCropsEstimatedRevenueUnits = view.findViewById(R.id.txt_crops_estimated_revenue_units);
        txtCropsCroppableAreaUnits = view.findViewById(R.id.txt_crops_croppable_area_units);
        txtCropFieldName = view.findViewById(R.id.txt_crop_field_name);
        txtCropFieldTotalArea = view.findViewById(R.id.txt_crop_field_total_area);
        txtCropFieldCroppableArea = view.findViewById(R.id.txt_crop_field_croppable_area);
        btnSave = view.findViewById(R.id.btn_save);

        return view;
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
        setHasOptionsMenu(true);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Fields");

        if (getArguments() != null) {
            cropField = (com.myfarmnow.myfarmcrop.models.farmrecords.CropField) getArguments().getSerializable("cropField");
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Edit Field");
        }

        initializeForm();
    }

    public void initializeForm() {
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        ((ArrayAdapter) spCropFieldUnits.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) spCropFieldStatus.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) spCropFieldType.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position == 0) {
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                    } else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.black)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//Change selected text size
                } catch (Exception e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        };

        spCropFieldUnits.setOnItemSelectedListener(onItemSelectedListener);
        spCropFieldStatus.setOnItemSelectedListener(onItemSelectedListener);
        spCropFieldType.setOnItemSelectedListener(onItemSelectedListener);

        spCropFieldUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                    } else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//Change selected text size
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String selection = parent.getItemAtPosition(position).toString();
                if (selection.toLowerCase().equals("acres")) {
                    txtCropsEstimatedRevenueUnits.setText("Acres");
                    txtCropsCroppableAreaUnits.setText("Acres");
                } else if (selection.toLowerCase().equals("hectares")) {
                    txtCropsEstimatedRevenueUnits.setText("Hectares");
                    txtCropsCroppableAreaUnits.setText("Hectares");
                } else if (selection.toLowerCase().equals("square miles")) {
                    txtCropsEstimatedRevenueUnits.setText("Square Miles");
                    txtCropsCroppableAreaUnits.setText("Square Miles");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //DashboardActivity.selectSpinnerItemByValue(unitsSpinner,CropSettingsSingleton.getInstance().getAreaUnits());

        fillViews();

        btnSave.setOnClickListener((View.OnClickListener) v -> {
            if (validateEntries()) {
                if (cropField == null) {
                    saveFields();
                } else {
                    updateField();
                }
                //navigate to field list
                navController.popBackStack();
            } else {
                Log.d("ERROR", "Testing");
            }
        });
    }

    public void saveFields() {
        cropField = new CropField();
        cropField.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        cropField.setFieldName(txtCropFieldName.getText().toString());
        cropField.setFieldType(spCropFieldType.getSelectedItem().toString());
        cropField.setStatus(spCropFieldStatus.getSelectedItem().toString());
        cropField.setTotalArea(Float.parseFloat(txtCropFieldTotalArea.getText().toString()));
        cropField.setCroppableArea(Float.parseFloat(txtCropFieldCroppableArea.getText().toString()));
        cropField.setUnits(spCropFieldUnits.getSelectedItem().toString());


        dbHandler.insertCropField(cropField);
        Log.w("userId", DashboardActivity.RETRIEVED_USER_ID);

    }

    public void updateField() {
        if (cropField != null) {
            cropField.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            cropField.setFieldName(txtCropFieldName.getText().toString());
            cropField.setFieldType(spCropFieldType.getSelectedItem().toString());
            cropField.setStatus(spCropFieldStatus.getSelectedItem().toString());
            cropField.setTotalArea(Float.parseFloat(txtCropFieldTotalArea.getText().toString()));
            cropField.setCroppableArea(Float.parseFloat(txtCropFieldCroppableArea.getText().toString()));
            cropField.setUnits(spCropFieldUnits.getSelectedItem().toString());

            dbHandler.updateCropField(cropField);
        }
    }

    public void fillViews() {
        if (cropField != null) {
            txtCropFieldName.setText(cropField.getFieldName());
            DashboardActivity.selectSpinnerItemByValue(spCropFieldUnits, cropField.getUnits());
            DashboardActivity.selectSpinnerItemByValue(spCropFieldType, cropField.getFieldType());
            DashboardActivity.selectSpinnerItemByValue(spCropFieldStatus, cropField.getStatus());
            txtCropFieldTotalArea.setText(cropField.getTotalArea() + "");
            txtCropFieldCroppableArea.setText(cropField.getCroppableArea() + "");
            btnSave.setText(R.string.btn_update_label);
        }
    }

    public boolean validateEntries() {
        String message = null;
        if (txtCropFieldName.getText().toString().isEmpty()) {
            message = getString(R.string.field_name_not_entered_message);
            txtCropFieldName.requestFocus();
        } else if (txtCropFieldTotalArea.getText().toString().isEmpty()) {
            message = getString(R.string.total_area_not_entered_message);
            txtCropFieldTotalArea.requestFocus();
        } else if (spCropFieldUnits.getSelectedItemPosition() == 0) {
            message = getString(R.string.units_not_selected);
            spCropFieldUnits.requestFocus();
        } else if (spCropFieldStatus.getSelectedItemPosition() == 0) {
            message = getString(R.string.field_status_is_not_selected);
            spCropFieldStatus.requestFocus();
        } else if (spCropFieldStatus.getSelectedItemPosition() == 0) {
            message = getString(R.string.field_type_not_selected);
            spCropFieldType.requestFocus();
        }

        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController.popBackStack();
        return super.onOptionsItemSelected(item);
    }
}