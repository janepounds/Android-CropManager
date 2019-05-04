package com.myfarmnow.myfarmcrop.fragments;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropPayment;
import com.myfarmnow.myfarmcrop.models.PaymentDialogActivity;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropInvoicePaymentDialog extends DialogFragment {
    MyFarmDbHandlerSingleton dbHandler;
    EditText dateTxt,amountTxt,referenceNumberTxt,notesTxt;
    TextView paymentNumberTxt;
    Button saveBtn;
    Dialog dialog;
    Spinner paymentModeSp;
    CropPayment cropPayment;
    LinearLayout customerLayout, invoiceLayout;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.crop_record_payment_received, null);
        amountTxt = view.findViewById(R.id.txt_crop_record_payment_received_amount);
        dateTxt = view.findViewById(R.id.txt_crop_record_payment_received_date);
        paymentNumberTxt = view.findViewById(R.id.txt_crop_record_payment_received_payment_number);
        paymentModeSp = view.findViewById(R.id.txt_crop_record_payment_received_payment_mode);
        referenceNumberTxt = view.findViewById(R.id.txt_crop_record_payment_received_reference_number);
        notesTxt = view.findViewById(R.id.txt_crop_record_payment_received_notes);
        customerLayout = view.findViewById(R.id.layout_record_payment_received_customer);
        invoiceLayout = view.findViewById(R.id.layout_record_payment_received_invoice);
        saveBtn = view.findViewById(R.id.btn_save);

        ((TextView)view.findViewById(R.id.txt_crop_record_payment_currency)).setText(CropSettingsSingleton.getInstance().getCurrency());
        CropDashboardActivity.addDatePicker(dateTxt,getActivity());
        customerLayout.setVisibility(View.GONE);
        invoiceLayout.setVisibility(View.GONE);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    savePayment();
                    dialog.dismiss();
                    PaymentDialogActivity activity = (PaymentDialogActivity) getActivity();
                    activity.savePayment(cropPayment);
                }
            }
        });
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(getActivity());
        paymentNumberTxt.setText(dbHandler.getNextPaymentNumber());
        //setCancelable(false);

        ((ArrayAdapter)paymentModeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getActivity().getColor(R.color.colorPrimary));
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
        paymentModeSp.setOnItemSelectedListener(onItemSelectedListener);

        builder.setView(view);
        dialog= builder.create();


        return dialog;

    }
    public void savePayment(){
        cropPayment = new CropPayment();
        cropPayment.setUserId(CropDashboardActivity.getPreferences("userId",getActivity()));
        cropPayment.setPaymentNumber(paymentNumberTxt.getText().toString());
        cropPayment.setReferenceNo(referenceNumberTxt.getText().toString());
        cropPayment.setMode(paymentModeSp.getSelectedItem().toString());
        cropPayment.setDate(dateTxt.getText().toString());
        cropPayment.setAmount(Float.parseFloat(amountTxt.getText().toString()));
        cropPayment.setNotes(notesTxt.getText().toString());
        dbHandler.insertCropPayment(cropPayment);
    }

    public boolean validateEntries(){
        String message = null;
       if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }
        else if(paymentModeSp.getSelectedItemPosition()==0){
            message =getString(R.string.payment_mode_not_entered);
            paymentModeSp.requestFocus();
        }


        if(message != null){
            Toast.makeText(getActivity(), getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}
