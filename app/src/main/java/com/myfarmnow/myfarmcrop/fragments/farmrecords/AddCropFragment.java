package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.adapters.farmrecords.CropFieldsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.database.MyFarmRoomDatabase;
import com.myfarmnow.myfarmcrop.databinding.FragmentAddCropBinding;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddCropFragment extends Fragment {

    public FragmentAddCropBinding binding;
    private Context context;
    private MyFarmDbHandlerSingleton dbHandler;
    private Crop crop;
    public  CropSpinnerAdapter fieldsSpinnerAdapter, seedsSpinnerAdapter;
    private NavController navController;
    private MyFarmRoomDatabase myFarmRoomDatabase;
    Crop cropsTable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_crop, container, false);
        Toolbar toolbar = binding.toolbar;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Crops");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (requireActivity().getIntent().hasExtra("crop")) {
            crop = (Crop) requireActivity().getIntent().getSerializableExtra("crop");
        }
        initializeForm();

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


    }

    public void initializeForm() {


        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        DashboardActivity.addDatePicker(binding.txtCropsDateSown, context);


        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEntries()) {
                    if (crop == null) {
                        saveCrop();
                    } else {
                        updateCrop();
                    }
                    navController.popBackStack();
                } else {
                    Log.d("ERROR", "Testing");
                }
            }
        });

        ArrayList<String> cropsList = new ArrayList<>();
        cropsList.add("Select Crop");
        for (CropItem cropItem : dbHandler.getCropItems()) {
            cropsList.add(cropItem.getName());
        }
        binding.spCropCrop.setAdapter(new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, cropsList));




        ArrayList<CropSpinnerItem> fieldsItems = new ArrayList<>();
        for(CropField x: dbHandler.getCropFields(DashboardActivity.getPreferences("userId",context))){
            fieldsItems.add(x);
        }
        fieldsSpinnerAdapter = new CropSpinnerAdapter(fieldsItems,"Field",context);
        binding.spCropsField.setAdapter(fieldsSpinnerAdapter);


        ArrayList<CropSpinnerItem> seedItems = new ArrayList<>();
        for (CropInventorySeeds x : dbHandler.getCropSeeds(DashboardActivity.getPreferences("userId", context))) {
            seedItems.add(x);
        }


        ((ArrayAdapter) binding.spCropsSeason.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) binding.spCropsHarvestUnits.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

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
        binding.spCropCrop.setOnItemSelectedListener(onItemSelectedListener);

        binding.spCropsSeason.setOnItemSelectedListener(onItemSelectedListener);


        binding.cropsCurrencyB.setText(CropSettingsSingleton.getInstance().getCurrency());

        binding.spCropsHarvestUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                }
                String selection = parent.getItemAtPosition(position).toString();
                if (selection.toLowerCase().equals("boxes")) {

                    binding.txtCropsEstimatedRevenueUnits.setText("/ Box");

                } else if (selection.toLowerCase().equals("kg")) {

                    binding.txtCropsEstimatedRevenueUnits.setText("/ Kg");


                } else if (selection.toLowerCase().equals("tonnes")) {
                    binding.txtCropsEstimatedRevenueUnits.setText("/ Tonne");


                } else if (selection.toLowerCase().equals("bushels")) {

                    binding.txtCropsEstimatedRevenueUnits.setText("/ Bushel");


                } else if (selection.toLowerCase().equals("bags")) {


                    binding.txtCropsEstimatedRevenueUnits.setText("/ Bag");


                } else if (selection.toLowerCase().equals("bunches")) {

                    binding.txtCropsEstimatedRevenueUnits.setText("/ Bunch");


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
        crop.setUserId(Integer.parseInt(DashboardActivity.getPreferences("userId",context)));
        crop.setCrop(binding.spCropCrop.getText().toString());
        crop.setVariety(binding.txtCropsVariety.getText().toString());
        crop.setField_id(((com.myfarmnow.myfarmcrop.models.farmrecords.CropField) binding.spCropsField.getSelectedItem()).getId());
        crop.setSeason(binding.spCropsSeason.getSelectedItem().toString());
        crop.setPlanting_date(binding.txtCropsDateSown.getText().toString());
        crop.setFiels_size( binding.txtCropsArea.getText().toString() );
        crop.setEstimated_revenue( binding.txtCropsEstimatedRevenue.getText().toString() );
        crop.setEstimated_yield( binding.txtCropsEstimatedYield.getText().toString() );
        if (binding.spCropsHarvestUnits.getSelectedItemPosition() != 0) {
            crop.setUnits(binding.spCropsHarvestUnits.getSelectedItem().toString());
        }
        dbHandler.insertCrop(crop);

    }

    public void updateCrop() {
        if (crop != null) {
            crop.setCrop(binding.spCropCrop.getText().toString());
            crop.setVariety(binding.txtCropsVariety.getText().toString());
            crop.setField_id(((com.myfarmnow.myfarmcrop.models.farmrecords.CropField) binding.spCropsField.getSelectedItem()).getId());
            crop.setSeason(binding.spCropsSeason.getSelectedItem().toString());
            crop.setPlanting_date(binding.txtCropsDateSown.getText().toString());
            crop.setFiels_size( binding.txtCropsArea.getText().toString() );
            crop.setEstimated_revenue( binding.txtCropsEstimatedRevenue.getText().toString() );
            crop.setEstimated_yield( binding.txtCropsEstimatedYield.getText().toString() );
            if (binding.spCropsHarvestUnits.getSelectedItemPosition() != 0) {
                crop.setUnits(binding.spCropsHarvestUnits.getSelectedItem().toString());
            }

            dbHandler.updateCrop(crop);
        }

    }

    public void fillViews() {
        if (crop != null) {
            // DashboardActivity.selectSpinnerItemByValue(cropSP,crop.getName());
            binding.txtCropsVariety.setText(crop.getVariety());
            binding.spCropCrop.setText(crop.getCrop());
            DashboardActivity.selectSpinnerItemByValue(binding.spCropsSeason, crop.getSeason());
            DashboardActivity.selectSpinnerItemByValue(binding.spCropsHarvestUnits, crop.getUnits());
            binding.txtCropsDateSown.setText(crop.getPlanting_date());
            binding.txtCropsArea.setText(crop.getFiels_size() + "");
            binding.txtCropsEstimatedYield.setText(crop.getEstimated_yield() + "");
            binding.txtCropsEstimatedRevenue.setText(crop.getEstimated_revenue() + "");
            DashboardActivity.selectSpinnerItemById(binding.spCropsField, crop.getField_id());
        }

    }



    public boolean validateEntries() {
        String message = null;
        if (binding.txtCropsDateSown.getText().toString().isEmpty()) {
            message = getString(R.string.date_not_entered_message);
            binding.txtCropsDateSown.requestFocus();
        } else if (binding.txtCropsArea.getText().toString().isEmpty()) {
            message = getString(R.string.area_not_entered);
            binding.txtCropsArea.requestFocus();
        } else if (binding.spCropCrop.getText().toString().isEmpty()) {
            message = "Crop must be entered";
            binding.spCropCrop.requestFocus();
        } else if (binding.spCropsField.getSelectedItemPosition() == 0) {
            message = getString(R.string.field_not_selected);
            binding.spCropsField.requestFocus();
        } else if (binding.txtCropsEstimatedRevenue.getText().toString().isEmpty()) {
            binding.txtCropsEstimatedRevenue.setText(getString(R.string.default_numeric_value));
        } else if (binding.txtCropsEstimatedYield.getText().toString().isEmpty()) {
            binding.txtCropsEstimatedYield.setText(getString(R.string.default_numeric_value));
        }


        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


//    public void  showfieldlist(List<CropSpinnerItem> fieldlist) {
//        ArrayList<CropSpinnerItem> fieldsItems = new ArrayList<>();
//        for (CropSpinnerItem x : fieldlist) {
//            fieldsItems.add(x);
//        }
//    }


}