package com.myfarmnow.myfarmcrop.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropPayment;
import com.myfarmnow.myfarmcrop.models.PaymentDialogActivity;

public class CropPaymentDialog extends DialogFragment {
    MyFarmDbHandlerSingleton dbHandler;
    EditText dateTxt,amountTxt, paymentModeTxt,referenceNumberTxt,notesTxt;
    TextView paymentNumberTxt;
    Button saveBtn;
    Dialog dialog;
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
        paymentModeTxt = view.findViewById(R.id.txt_crop_record_payment_received_payment_mode);
        referenceNumberTxt = view.findViewById(R.id.txt_crop_record_payment_received_reference_number);
        notesTxt = view.findViewById(R.id.txt_crop_record_payment_received_notes);
        customerLayout = view.findViewById(R.id.layout_record_payment_received_customer);
        invoiceLayout = view.findViewById(R.id.layout_record_payment_received_invoice);
        saveBtn = view.findViewById(R.id.btn_save);
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

        builder.setView(view);
        dialog= builder.create();


        return dialog;

    }
    public void savePayment(){
        cropPayment = new CropPayment();
        cropPayment.setUserId(CropDashboardActivity.getPreferences("userId",getActivity()));
        cropPayment.setPaymentNumber(paymentNumberTxt.getText().toString());
        cropPayment.setReferenceNo(referenceNumberTxt.getText().toString());
        cropPayment.setMode(paymentModeTxt.getText().toString());
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
        else if(paymentModeTxt.getText().toString().isEmpty()){
            message =getString(R.string.payment_mode_not_entered);
            paymentModeTxt.requestFocus();
        }


        if(message != null){
            Toast.makeText(getActivity(), getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}
