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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;



public class StoreAddSprayFragment extends DialogFragment {
    public EditText purchaseDatTxt,seedNameTxt,costTxt,
            quantityTxt,batchTxt,supplierTxt,activeIngredientsTxt,harvestIntervalTxt,expiryDateTxt;
    TextView currencyTxt,cropsprayunitsTxt;
    Button saveBtn;
    Spinner usageUnitSpinner,typeSp;
    MyFarmDbHandlerSingleton dbHandler;
    CropInventorySpray sprayInventory;
    private Context context;
    private NavController navController;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_RIGHT_ICON);
        getDialog().setFeatureDrawableResource(Window.FEATURE_RIGHT_ICON,R.drawable.ic_close);
        if(requireActivity().getIntent().hasExtra("sprayInventory")){
            sprayInventory =(CropInventorySpray)requireActivity().getIntent().getSerializableExtra("sprayInventory");
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view =inflater.inflate(R.layout.fragment_store_add_spray, null);
        builder.setView(view);
        initializeForm(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context =context;
    }

    public void initializeForm(View view){
        purchaseDatTxt = view.findViewById(R.id.txt_crop_spray_date_of_purchase);
        typeSp = view.findViewById(R.id.sp_crop_spray_type);
        seedNameTxt = view.findViewById(R.id.txt_crop_spray_spray_name);
        expiryDateTxt = view.findViewById(R.id.txt_crop_pray_exp_date);
        usageUnitSpinner = view.findViewById(R.id.spinner_crop_spray_unit);
        quantityTxt = view.findViewById(R.id.txt_crop_spray_qty);
        costTxt = view.findViewById(R.id.txt_crop_spray_cost);
        currencyTxt = view.findViewById(R.id.txt_crop_spray_currency);
        cropsprayunitsTxt = view.findViewById(R.id.txt_crop_spray_units);
        batchTxt = view.findViewById(R.id.txt_crop_spray_batch_no);
        supplierTxt = view.findViewById(R.id.txt_crop_spray_supplier);
        saveBtn = view.findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        DashboardActivity.addDatePicker(purchaseDatTxt,context);
        DashboardActivity.addDatePicker(expiryDateTxt,context);
        ((ArrayAdapter)usageUnitSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)typeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position == 0){
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }
                   else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

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
        usageUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                String selection = parent.getItemAtPosition(position).toString();
                if(selection.toLowerCase().equals("ml")){

                    cropsprayunitsTxt.setText(" ML");

                }
                else if(selection.toLowerCase().equals("cwt")){

                    cropsprayunitsTxt.setText("CWT");


                }
                else if(selection.toLowerCase().equals("flo3")){
                    cropsprayunitsTxt.setText("FLO3");




                }
                else if(selection.toLowerCase().equals("lt")){

                    cropsprayunitsTxt.setText("LT");


                }
                else if(selection.toLowerCase().equals("m3")){


                    cropsprayunitsTxt.setText("M3");


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeSp.setOnItemSelectedListener(onItemSelectedListener);


        fillViews();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(sprayInventory==null){
                        saveSeeds();
                    }
                    else{
                        updateSeeds();
                    }
                    //redirect to list fragment
                    //navController.popBackStack();
                    getDialog().dismiss();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }
    public void fillViews(){
        if(sprayInventory != null){

            expiryDateTxt.setText(sprayInventory.getExpiryDate());
            purchaseDatTxt.setText(sprayInventory.getPurchaseDate());
            seedNameTxt.setText(sprayInventory.getName());
            DashboardActivity.selectSpinnerItemByValue(usageUnitSpinner,sprayInventory.getUsageUnits());
            DashboardActivity.selectSpinnerItemByValue(typeSp,sprayInventory.getType());
            quantityTxt.setText(sprayInventory.getQuantity()+"");
            costTxt.setText(sprayInventory.getCost()+"");
            batchTxt.setText(sprayInventory.getBatchNumber()+"");
            supplierTxt.setText(sprayInventory.getSupplier()+"");

        }
    }
    public void saveSeeds(){
        sprayInventory = new CropInventorySpray();
        sprayInventory.setUserId(DashboardActivity.getPreferences("userId",context));
        sprayInventory.setUsageUnits(usageUnitSpinner.getSelectedItem().toString());
        sprayInventory.setName(seedNameTxt.getText().toString());
        sprayInventory.setPurchaseDate(purchaseDatTxt.getText().toString());
        sprayInventory.setType(typeSp.getSelectedItem().toString());
        sprayInventory.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
        sprayInventory.setCost(Float.parseFloat(costTxt.getText().toString()));
        sprayInventory.setBatchNumber(batchTxt.getText().toString());
        sprayInventory.setSupplier(supplierTxt.getText().toString());


        sprayInventory.setExpiryDate(expiryDateTxt.getText().toString());

        // sprayInventory.setTgw(tgwTxt.getText().toString());


        dbHandler.insertCropSpray(sprayInventory);



    }
    public void updateSeeds(){

        if(sprayInventory != null) {

            sprayInventory.setUserId(DashboardActivity.getPreferences("userId",context));
            sprayInventory.setUsageUnits(usageUnitSpinner.getSelectedItem().toString());
            sprayInventory.setPurchaseDate(purchaseDatTxt.getText().toString());
            sprayInventory.setName(seedNameTxt.getText().toString());
            sprayInventory.setType(typeSp.getSelectedItem().toString());
            sprayInventory.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
            sprayInventory.setCost(Float.parseFloat(costTxt.getText().toString()));
            sprayInventory.setBatchNumber(batchTxt.getText().toString());
            sprayInventory.setSupplier(supplierTxt.getText().toString());
            sprayInventory.setExpiryDate(expiryDateTxt.getText().toString());
            dbHandler.updateCropSpray(sprayInventory);
        }
    }

    public boolean validateEntries(){
        String message = null;
        if(purchaseDatTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            purchaseDatTxt.requestFocus();
        }
        else if(seedNameTxt.getText().toString().isEmpty()){
            message = getString(R.string.seed_name_not_entered_message);
            seedNameTxt.requestFocus();
        }

        else if(quantityTxt.getText().toString().isEmpty()){
            message = getString(R.string.quantity_not_entered_message);
            quantityTxt.requestFocus();
        }else if(batchTxt.getText().toString().isEmpty()){
            message = getString(R.string.batch_number_entered_message);
            batchTxt.requestFocus();
        }
        else if(usageUnitSpinner.getSelectedItemPosition()==0){
            message = getString(R.string.usage_units_not_selected);
            usageUnitSpinner.requestFocus();
        }else if(typeSp.getSelectedItemPosition() ==0){
            message = getString(R.string.type_not_selected);
            typeSp.requestFocus();
        }

        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


}