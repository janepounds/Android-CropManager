package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;


public class StoreAddSeedFragment extends DialogFragment {
    private Context context;
    private CropInventorySeeds seedsInventoryToEdit = null;
    private MyFarmDbHandlerSingleton dbHandler;
    private NavController navController;
    EditText purchaseDatTxt, seedNameTxt, varietyTxt, dressingTxt, tgwTxt, quantityTxt, costTxt, batchTxt, manufacturerTxt, supplierTxt;
    ImageView close;
    Spinner usageUnitSpinner, typeSp;
    Button saveBtn;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


//                    (CropInventorySeeds)requireActivity().getIntent().getSerializableExtra("seedsInventory");


//        if(getIntent().hasExtra("seedsInventory")){
//            seedsInventoryToEdit =(CropInventorySeeds)getIntent().getSerializableExtra("seedsInventory");
//        }


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        if(getArguments()!=null) {
            seedsInventoryToEdit = (CropInventorySeeds) getArguments().getSerializable("seedsInventory");
            Log.w("edit", seedsInventoryToEdit.getBatchNumber());
        }


        View view = getLayoutInflater().inflate(R.layout.fragment_store_add_seed, null);
        builder.setView(view);
        initializeForm(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        navController = Navigation.findNavController(view);

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public void initializeForm(View view) {
        close = view.findViewById(R.id.seed_close);
        purchaseDatTxt = view.findViewById(R.id.txt_crop_purchase_date);
        seedNameTxt = view.findViewById(R.id.txt_crop_seed_name);
        varietyTxt = view.findViewById(R.id.txt_crop_variety);
        usageUnitSpinner = view.findViewById(R.id.txt_crop_usage_unit);
        quantityTxt = view.findViewById(R.id.txt_crop_quantity);
        costTxt = view.findViewById(R.id.txt_crop_cost);
        batchTxt = view.findViewById(R.id.txt_crop_batch_number);
        supplierTxt = view.findViewById(R.id.txt_crop_supplier);
        saveBtn = view.findViewById(R.id.btn_save);
        typeSp = view.findViewById(R.id.sp_crop_seed_type);
        manufacturerTxt = view.findViewById(R.id.txt_crop_manufacturer);

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        DashboardActivity.addDatePicker(purchaseDatTxt, context);
        close.setOnClickListener(view1 -> getDialog().dismiss());

        ((ArrayAdapter) usageUnitSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) typeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

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
        usageUnitSpinner.setOnItemSelectedListener(onItemSelectedListener);
        typeSp.setOnItemSelectedListener(onItemSelectedListener);


//        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());


        fillViews();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEntries()) {
                    if (seedsInventoryToEdit == null) {
                        saveSeeds();
                    } else {
                        updateSeeds();
                    }
                    //go back to list
                    navController = Navigation.findNavController(getParentFragment().getView());
                    navController.navigate(R.id.action_storeAddSeedFragment_to_storeFragment);

                } else {
                    Log.d("ERROR", "Testing");
                }
            }
        });
    }

    public void fillViews() {
        if (seedsInventoryToEdit != null) {

            DashboardActivity.selectSpinnerItemByValue(usageUnitSpinner, seedsInventoryToEdit.getUsageUnits());
            DashboardActivity.selectSpinnerItemByValue(typeSp, seedsInventoryToEdit.getType());
            purchaseDatTxt.setText(seedsInventoryToEdit.getPurchaseDate());
            seedNameTxt.setText(seedsInventoryToEdit.getName());
            varietyTxt.setText(seedsInventoryToEdit.getVariety());
            quantityTxt.setText(seedsInventoryToEdit.getQuantity() + "");
            costTxt.setText(seedsInventoryToEdit.getCost() + "");
            batchTxt.setText(seedsInventoryToEdit.getBatchNumber() + "");
            supplierTxt.setText(seedsInventoryToEdit.getSupplier() + "");
            manufacturerTxt.setText(seedsInventoryToEdit.getManufacturer() + "");
//            tgwTxt.setText(seedsInventoryToEdit.getTgw()+"");


        }
    }

    public void saveSeeds() {
        seedsInventoryToEdit = new CropInventorySeeds();
        seedsInventoryToEdit.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        seedsInventoryToEdit.setUsageUnits(usageUnitSpinner.getSelectedItem().toString());
        seedsInventoryToEdit.setPurchaseDate(purchaseDatTxt.getText().toString());
        seedsInventoryToEdit.setName(seedNameTxt.getText().toString());
        seedsInventoryToEdit.setVariety(varietyTxt.getText().toString());
        seedsInventoryToEdit.setCost(Float.parseFloat(costTxt.getText().toString()));
        seedsInventoryToEdit.setBatchNumber(batchTxt.getText().toString());
        seedsInventoryToEdit.setSupplier(supplierTxt.getText().toString());
        seedsInventoryToEdit.setType(typeSp.getSelectedItem().toString());
        seedsInventoryToEdit.setManufacturer(manufacturerTxt.getText().toString());
        dbHandler.insertCropSeeds(seedsInventoryToEdit);


    }

    public void updateSeeds() {

        if (seedsInventoryToEdit != null) {

            seedsInventoryToEdit.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            seedsInventoryToEdit.setUsageUnits(usageUnitSpinner.getSelectedItem().toString());
            seedsInventoryToEdit.setPurchaseDate(purchaseDatTxt.getText().toString());
            seedsInventoryToEdit.setName(seedNameTxt.getText().toString());
            seedsInventoryToEdit.setVariety(varietyTxt.getText().toString());
            seedsInventoryToEdit.setCost(Float.parseFloat(costTxt.getText().toString()));
            seedsInventoryToEdit.setBatchNumber(batchTxt.getText().toString());
            seedsInventoryToEdit.setSupplier(supplierTxt.getText().toString());
            seedsInventoryToEdit.setType(typeSp.getSelectedItem().toString());

            dbHandler.updateCropSeeds(seedsInventoryToEdit);
        }
    }

    public boolean validateEntries() {
        String message = null;
        if (purchaseDatTxt.getText().toString().isEmpty()) {
            message = getString(R.string.date_not_entered_message);
            purchaseDatTxt.requestFocus();
        } else if (seedNameTxt.getText().toString().isEmpty()) {
            message = getString(R.string.seed_name_not_entered_message);
            seedNameTxt.requestFocus();
        } else if (batchTxt.getText().toString().isEmpty()) {
            message = getString(R.string.batch_number_entered_message);
            batchTxt.requestFocus();
        } else if (usageUnitSpinner.getSelectedItemPosition() == 0) {
            message = getString(R.string.usage_units_not_selected);
            usageUnitSpinner.requestFocus();
        } else if (typeSp.getSelectedItemPosition() == 0) {
            message = getString(R.string.seed_type_not_selected);
            typeSp.requestFocus();
        }

        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}