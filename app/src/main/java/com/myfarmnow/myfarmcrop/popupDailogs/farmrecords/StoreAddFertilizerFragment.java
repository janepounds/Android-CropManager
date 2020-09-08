package com.myfarmnow.myfarmcrop.popupDailogs.farmrecords;

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
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;


public class StoreAddFertilizerFragment extends DialogFragment {
    private Context context;
    private MyFarmDbHandlerSingleton dbHandler;
    private NavController navController;
    CropInventoryFertilizer fertilizerInventory =null;
    EditText dateTxt,nameTxt,batchNumberTxt,npkNTxt, npkPTxt, npkKTxt,qtyTxt,serialNumberTxt,supplierTxt,costTxt;
    EditText macrosCa,macrosMg,macrosS,microsB,microsMn,microsCl,microsMo,microsCu,microsZn,microsFe,microsNa;
    TextView currencyTxt,txtCropFertilizerUnit;
    Spinner typeSp,usageUnitSp;
    Button saveBtn;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_RIGHT_ICON);
        getDialog().setFeatureDrawableResource(Window.FEATURE_RIGHT_ICON,R.drawable.ic_close);
        if(requireActivity().getIntent().hasExtra("fertilizerInventory")){
            fertilizerInventory =(CropInventoryFertilizer)requireActivity().getIntent().getSerializableExtra("fertilizerInventory");
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
        View view =inflater.inflate(R.layout.fragment_store_add_fertilizer, null);
        builder.setView(view);
        initializeForm( view);

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
        dateTxt = view.findViewById(R.id.txt_crop_fertilizer_date_of_purchase);
        nameTxt = view.findViewById(R.id.txt_crop_fertilizer_fertilizer_name);
        typeSp = view.findViewById(R.id.spinner_crop_fertilizer_fertilizer_type);
        batchNumberTxt = view.findViewById(R.id.txt_crop_fertilizer_batch_number);
        usageUnitSp = view.findViewById(R.id.spinner_crop_fertilizer_usage_unit);
        supplierTxt = view.findViewById(R.id.txt_crop_fertilizer_supplier);
        costTxt = view.findViewById(R.id.txt_crop_fertilizer_unit_cost);
        qtyTxt = view.findViewById(R.id.txt_crop_fertilizer_qty);
        npkNTxt = view.findViewById(R.id.txt_crop_fertilizer_npk_n);
        npkKTxt = view.findViewById(R.id.txt_crop_fertilizer_npk_k);
        npkPTxt = view.findViewById(R.id.txt_crop_fertilizer_npk_p);
        txtCropFertilizerUnit = view.findViewById(R.id.txt_crop_fertilizer_unit);
        saveBtn = view.findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        DashboardActivity.addDatePicker(dateTxt,context);
        ((ArrayAdapter)typeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)usageUnitSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        usageUnitSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                if(selection.toLowerCase().equals("g")){

                    txtCropFertilizerUnit.setText(" G");

                }
                else if(selection.toLowerCase().equals("kg")){

                    txtCropFertilizerUnit.setText("KG");


                }
                else if(selection.toLowerCase().equals("cwt")){
                    txtCropFertilizerUnit.setText("CWT");




                }
                else if(selection.toLowerCase().equals("tonne")){
                    txtCropFertilizerUnit.setText("TN");




                }
                else if(selection.toLowerCase().equals("lt")){

                    txtCropFertilizerUnit.setText("LT");


                }
                else if(selection.toLowerCase().equals("ml")){

                    txtCropFertilizerUnit.setText("ML");


                }
                else if(selection.toLowerCase().equals("m3")){


                    txtCropFertilizerUnit.setText("M3");


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typeSp.setOnItemSelectedListener(onItemSelectedListener);




//        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());


        fillViews();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(fertilizerInventory==null){
                        saveSeeds();
                    }
                    else{
                        updateSeeds();
                    }
                   //redirect to list
                    getDialog().dismiss();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }

    public void fillViews(){
        if(fertilizerInventory != null){

            DashboardActivity.selectSpinnerItemByValue(usageUnitSp,fertilizerInventory.getUsageUnits());
            Log.d("USAGE UNITS", fertilizerInventory.getUsageUnits());
            DashboardActivity.selectSpinnerItemByValue(typeSp,fertilizerInventory.getType());
            dateTxt.setText(fertilizerInventory.getPurchaseDate());
            nameTxt.setText(fertilizerInventory.getName());
            npkNTxt.setText(fertilizerInventory.getnPercentage()+"");
            npkKTxt.setText(fertilizerInventory.getkPercentage()+"");
            npkPTxt.setText(fertilizerInventory.getpPercentage()+"");
            qtyTxt.setText(fertilizerInventory.getQuantity()+"");
            costTxt.setText(fertilizerInventory.getCost()+"");
            serialNumberTxt.setText(fertilizerInventory.getSerialNumber());
            batchNumberTxt.setText(fertilizerInventory.getBatchNumber());
            supplierTxt.setText(fertilizerInventory.getSupplier());





        }
    }
    public void saveSeeds(){
        fertilizerInventory = new CropInventoryFertilizer();
        fertilizerInventory.setUserId(DashboardActivity.getPreferences("userId",context));
        fertilizerInventory.setPurchaseDate(dateTxt.getText().toString());
        fertilizerInventory.setFertilizerName(nameTxt.getText().toString());
        fertilizerInventory.setType(typeSp.getSelectedItem().toString());
        fertilizerInventory.setnPercentage(Float.parseFloat(npkNTxt.getText().toString()));
        fertilizerInventory.setpPercentage(Float.parseFloat(npkPTxt.getText().toString()));
        fertilizerInventory.setkPercentage(Float.parseFloat(npkKTxt.getText().toString()));
        fertilizerInventory.setQuantity(Float.parseFloat(qtyTxt.getText().toString()));
        fertilizerInventory.setBatchNumber(batchNumberTxt.getText().toString());
        fertilizerInventory.setSupplier(supplierTxt.getText().toString());
        fertilizerInventory.setUsageUnits(usageUnitSp.getSelectedItem().toString());
        fertilizerInventory.setCost(Float.parseFloat(costTxt.getText().toString()));

        Log.d("USAGE UNITS", fertilizerInventory.getUsageUnits());

        dbHandler.insertCropFertilizerInventory(fertilizerInventory);



    }
    public void updateSeeds(){

        if(fertilizerInventory != null) {

            fertilizerInventory.setUserId(DashboardActivity.getPreferences("userId",context));
            fertilizerInventory.setPurchaseDate(dateTxt.getText().toString());
            fertilizerInventory.setFertilizerName(nameTxt.getText().toString());
            fertilizerInventory.setType(typeSp.getSelectedItem().toString());
            fertilizerInventory.setnPercentage(Float.parseFloat(npkNTxt.getText().toString()));
            fertilizerInventory.setpPercentage(Float.parseFloat(npkPTxt.getText().toString()));
            fertilizerInventory.setkPercentage(Float.parseFloat(npkKTxt.getText().toString()));
            fertilizerInventory.setQuantity(Float.parseFloat(qtyTxt.getText().toString()));
            fertilizerInventory.setBatchNumber(batchNumberTxt.getText().toString());
            fertilizerInventory.setSerialNumber(serialNumberTxt.getText().toString());
            fertilizerInventory.setSupplier(supplierTxt.getText().toString());
            fertilizerInventory.setUsageUnits(usageUnitSp.getSelectedItem().toString());
            fertilizerInventory.setCost(Float.parseFloat(costTxt.getText().toString()));
            dbHandler.updateCropFertilizerInventory(fertilizerInventory);
        }
    }
    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }
        else if(nameTxt.getText().toString().isEmpty()){
            message = getString(R.string.fertilizer_name_not_entered);
            nameTxt.requestFocus();
        }
        else if(qtyTxt.getText().toString().isEmpty()){
            message = getString(R.string.quantity_not_entered_message);
            qtyTxt.requestFocus();
        }else if(batchNumberTxt.getText().toString().isEmpty()){
            message = getString(R.string.batch_number_entered_message);
            batchNumberTxt.requestFocus();
        }
        else if(usageUnitSp.getSelectedItemPosition()==0){
            message = getString(R.string.usage_units_not_selected);
            usageUnitSp.requestFocus();
        }else if(typeSp.getSelectedItemPosition()==0){
            message = getString(R.string.fertilizer_type_not_selected);
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