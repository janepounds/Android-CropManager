package com.myfarmnow.myfarmcrop.popupDailogs.farmrecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;

public class AddSpray extends DialogFragment {

    public EditText purchaseDatTxt,seedNameTxt,costTxt,
            quantityTxt,batchTxt,supplierTxt,activeIngredientsTxt,harvestIntervalTxt,expiryDateTxt;
    TextView currencyTxt, cropsprayunitsTxt;
    Button saveBtn;
    Spinner usageUnitSpinner,typeSp;
    MyFarmDbHandlerSingleton dbHandler;
    CropInventorySpray sprayInventory;
    Context activity;
    FragmentManager fm;

    public AddSpray(Context context, FragmentManager supportFragmentManager){
        this.activity=context;
        this.fm=supportFragmentManager;

    }






    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_RIGHT_ICON);
        getDialog().setFeatureDrawableResource(Window.FEATURE_RIGHT_ICON,R.drawable.ic_close);
        getDialog().setContentView(R.layout.activity_crop_inventory_fragment);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);


    }


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view =inflater.inflate(R.layout.activity_crop_inventory_fragment, null);

        builder.setView(view);
        builder.setCancelable(true);

        initializeView(view);
        return builder.create();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title", "ADD SPRAY");
        getDialog().setCancelable(true);
        getDialog().setTitle(title);




    }


    public void initializeView(View view){
        purchaseDatTxt = view.findViewById(R.id.txt_crop_spray_date_of_purchase);
        seedNameTxt = view.findViewById(R.id.txt_crop_spray_spray_name);
        typeSp = view.findViewById(R.id.sp_crop_spray_type);
//        harvestIntervalTxt = findViewById(R.id.txt_crop_spray_harvest_interval);
//        activeIngredientsTxt = findViewById(R.id.txt_crop_spray_active_ingreds);
        expiryDateTxt = view.findViewById(R.id.txt_crop_pray_exp_date);
        usageUnitSpinner = view.findViewById(R.id.spinner_crop_spray_unit);
        cropsprayunitsTxt = view.findViewById(R.id.txt_crop_spray_units);
        quantityTxt = view.findViewById(R.id.txt_crop_spray_qty);
        costTxt = view.findViewById(R.id.txt_crop_spray_cost);
        currencyTxt = view.findViewById(R.id.txt_crop_spray_currency);

        batchTxt = view.findViewById(R.id.txt_crop_spray_batch_no);
        supplierTxt = view.findViewById(R.id.txt_crop_spray_supplier);
        saveBtn = view.findViewById(R.id.btn_save);

        usageUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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



    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }
}
