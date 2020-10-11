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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;

public class AddCropFragment extends Fragment {
    private Context context;
    private MyFarmDbHandlerSingleton dbHandler;
    private Crop crop;
    public CropSpinnerAdapter fieldsSpinnerAdapter;
    private NavController navController;

    private Toolbar toolbar;
    private EditText txtCropsDateSown, txtCropsVariety, txtCropsArea, txtCropsEstimatedRevenue, txtCropsEstimatedYield;
    private AutoCompleteTextView autocompleteCropName;
    private Spinner spCropsField, spCropsSeason, spCropsHarvestUnits;
    private TextView cropsCurrencyB, txtCropsEstimatedRevenueUnits;
    private ImageView datePickerImage;
    private Button btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_crop, container, false);

        toolbar = view.findViewById(R.id.toolbar_add_crop_fragment);
        txtCropsDateSown = view.findViewById(R.id.txt_crops_date_sown);
        autocompleteCropName = view.findViewById(R.id.autoComplete_crop_crop);
        spCropsField = view.findViewById(R.id.sp_crops_field);
        spCropsSeason = view.findViewById(R.id.sp_crops_season);
        spCropsHarvestUnits = view.findViewById(R.id.sp_crops_harvest_units);
        cropsCurrencyB = view.findViewById(R.id.crops_currency_b);
        txtCropsEstimatedRevenueUnits = view.findViewById(R.id.txt_crops_estimated_revenue_units);
        txtCropsVariety = view.findViewById(R.id.txt_crops_variety);
        txtCropsArea = view.findViewById(R.id.txt_crops_area);
        txtCropsEstimatedRevenue = view.findViewById(R.id.txt_crops_estimated_revenue);
        txtCropsEstimatedYield = view.findViewById(R.id.txt_crops_estimated_yield);
        datePickerImage = view.findViewById(R.id.date_picker_image);
        btnSave = view.findViewById(R.id.btn_save);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(getString(R.string.actionAddCrop));

        if (getArguments() != null) {
            crop = (Crop) getArguments().getSerializable("crop");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionEditCrop));
            btnSave.setText(getString(R.string.update));
        }

        initializeForm();

        toolbar.setNavigationOnClickListener(view1 -> navController.popBackStack());

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
    }

    public void initializeForm() {
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        DashboardActivity.addDatePicker(txtCropsDateSown, context);
        DashboardActivity.addDatePickerImageView(datePickerImage, txtCropsDateSown, context);

        btnSave.setOnClickListener(v -> {
            if (validateEntries()) {
                if (crop == null) {
                    saveCrop();
                } else {
                    updateCrop();
                }
                //go back to list
                navController.popBackStack();
            }

        });

        ArrayList<String> cropsList = new ArrayList<>();
        ArrayList<CropInventorySeeds> seedsList=  dbHandler.getCropSeeds(DashboardActivity.RETRIEVED_USER_ID);
        for (CropInventorySeeds seed :  seedsList) {
            cropsList.add(seed.getName());
        }

        autocompleteCropName.setThreshold(1);
        autocompleteCropName.setAdapter(new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, cropsList));
        autocompleteCropName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                autocompleteCropName.showDropDown();
            }
        });

        autocompleteCropName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtCropsVariety.setText( seedsList.get(position).getVariety()+"");
            }
        });

        ArrayList<CropSpinnerItem> fieldsItems = new ArrayList<>();
        for (CropField x : dbHandler.getCropFields(DashboardActivity.RETRIEVED_USER_ID)) {
            fieldsItems.add(x);
        }

        fieldsSpinnerAdapter = new CropSpinnerAdapter(fieldsItems, "Field", context);

        spCropsField.setAdapter(fieldsSpinnerAdapter);

        // ((ArrayAdapter)cropSP.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) spCropsSeason.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) spCropsHarvestUnits.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
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
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//Change selected text size
                } catch (Exception e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };



        spCropsSeason.setOnItemSelectedListener(onItemSelectedListener);

        cropsCurrencyB.setText(CropSettingsSingleton.getInstance().getCurrency());

        spCropsHarvestUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    if (position == 0) {
                        ((TextView) view).setTextColor(Color.GRAY);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//Change selected text size
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String selection = parent.getItemAtPosition(position).toString();
                if (selection.toLowerCase().equals("boxes")) {
                    txtCropsEstimatedRevenueUnits.setText("/ Box");
                } else if (selection.toLowerCase().equals("kg")) {
                    txtCropsEstimatedRevenueUnits.setText("/ Kg");
                } else if (selection.toLowerCase().equals("tonnes")) {
                    txtCropsEstimatedRevenueUnits.setText("/ Tonne");
                } else if (selection.toLowerCase().equals("bushels")) {
                    txtCropsEstimatedRevenueUnits.setText("/ Bushel");
                } else if (selection.toLowerCase().equals("bags")) {
                    txtCropsEstimatedRevenueUnits.setText("/ Bag");
                } else if (selection.toLowerCase().equals("bunches")) {
                    txtCropsEstimatedRevenueUnits.setText("/ Bunch");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fillViews();
    }

    public void saveCrop() {
        crop = new Crop();
        crop.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        crop.setName(autocompleteCropName.getText().toString());
        crop.setVariety(txtCropsVariety.getText().toString());
        crop.setFieldId(((com.myfarmnow.myfarmcrop.models.farmrecords.CropField) spCropsField.getSelectedItem()).getId());
        crop.setSeason(spCropsSeason.getSelectedItem().toString());
        crop.setDateSown(txtCropsDateSown.getText().toString());
        crop.setArea(Float.parseFloat(txtCropsArea.getText().toString()));
        crop.setEstimatedRevenue(Float.parseFloat(txtCropsEstimatedRevenue.getText().toString()));
        crop.setEstimatedYield(Float.parseFloat(txtCropsEstimatedYield.getText().toString()));
        if (spCropsHarvestUnits.getSelectedItemPosition() != 0) {
            crop.setHarvestUnits(spCropsHarvestUnits.getSelectedItem().toString());
        }
        dbHandler.insertCrop(crop);
    }

    public void updateCrop() {
        if (crop != null) {
            crop.setName(autocompleteCropName.getText().toString());
            crop.setVariety(txtCropsVariety.getText().toString());
            crop.setFieldId(((com.myfarmnow.myfarmcrop.models.farmrecords.CropField) spCropsField.getSelectedItem()).getId());
            crop.setSeason(spCropsSeason.getSelectedItem().toString());
            crop.setDateSown(txtCropsDateSown.getText().toString());
            crop.setArea(Float.parseFloat(txtCropsArea.getText().toString()));
            crop.setEstimatedRevenue(Float.parseFloat(txtCropsEstimatedRevenue.getText().toString()));
            crop.setEstimatedYield(Float.parseFloat(txtCropsEstimatedYield.getText().toString()));
            if (spCropsHarvestUnits.getSelectedItemPosition() != 0) {
                crop.setHarvestUnits(spCropsHarvestUnits.getSelectedItem().toString());
            }
            dbHandler.updateCrop(crop);
        }
    }

    public void fillViews() {
        if (crop != null) {
            // DashboardActivity.selectSpinnerItemByValue(cropSP,crop.getName());
            txtCropsVariety.setText(crop.getVariety());
            autocompleteCropName.setText(crop.getName());
            DashboardActivity.selectSpinnerItemByValue(spCropsSeason, crop.getSeason());
            DashboardActivity.selectSpinnerItemByValue(spCropsHarvestUnits, crop.getHarvestUnits());
            txtCropsDateSown.setText(crop.getDateSown());
            txtCropsArea.setText(crop.getArea() + "");
            txtCropsEstimatedYield.setText(crop.getEstimatedYield() + "");
            txtCropsEstimatedRevenue.setText(crop.getEstimatedRevenue() + "");
            DashboardActivity.selectSpinnerItemById(spCropsField, crop.getFieldId());
        }
    }

    public boolean validateEntries() {
        String message = null;
        if (txtCropsDateSown.getText().toString().isEmpty()) {
            message = getString(R.string.date_not_entered_message);
            txtCropsDateSown.requestFocus();
        } else if (txtCropsArea.getText().toString().isEmpty()) {
            message = getString(R.string.area_not_entered);
            txtCropsArea.requestFocus();
        } else if (autocompleteCropName.getText().toString().isEmpty()) {
            message = "Crop must be entered";
            autocompleteCropName.requestFocus();
        } else if (spCropsField.getSelectedItemPosition() == 0) {
            message = getString(R.string.field_not_selected);
            spCropsField.requestFocus();
        } else if (txtCropsEstimatedRevenue.getText().toString().isEmpty()) {
            txtCropsEstimatedRevenue.setText(getString(R.string.default_numeric_value));
        } else if (txtCropsEstimatedYield.getText().toString().isEmpty()) {
            txtCropsEstimatedYield.setText(getString(R.string.default_numeric_value));
        }

        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}