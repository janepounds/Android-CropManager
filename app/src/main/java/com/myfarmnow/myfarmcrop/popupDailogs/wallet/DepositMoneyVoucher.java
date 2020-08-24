package com.myfarmnow.myfarmcrop.popupDailogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cabral.emaisha.R;
import com.cabral.emaisha.wallet.activities.WalletAuthActivity;
import com.cabral.emaisha.wallet.activities.WalletHomeActivity;
import com.cabral.emaisha.wallet.models.ApiPaths;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class DepositMoneyVoucher extends DialogFragment {

    Button addMoneyImg;
    TextView errorMsgTxt;
    EditText voucherTxt;

    static String PENDING_DEPOSIT_REFERENCE_NUMBER;

    double balance;
    ProgressDialog dialog;
    Context activity;
    public DepositMoneyVoucher(Context context,double balance){
        this.activity=context;   this.balance=balance;

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        View view =inflater.inflate(R.layout.wallet_add_money_voucher, null);

        builder.setView(view);

        initializeForm( view);
        return builder.create();

    }
    public void initializeForm(View view) {
        addMoneyImg = view.findViewById(R.id.button_add_money);

        voucherTxt= view.findViewById(R.id.wallet_add_money_amount);

        errorMsgTxt = view.findViewById(R.id.text_view_error_message);


        addMoneyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateDeposit();
            }
        });

        dialog = new ProgressDialog(this.activity);
        dialog.setIndeterminate(true);
        dialog.setMessage("Processing the transaction..");
        dialog.setCancelable(false);
    }

    public void initiateDeposit(){

        String codeEntered = voucherTxt.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization","Bearer "+ WalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("email", WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_EMAIL,this.activity));
        params.put("phoneNumber", WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_PHONE_NUMBER,this.activity));
        params.put("codeEntered",codeEntered);

        client.setTimeout(30000);
        client.post(ApiPaths.WALLET_INITIATE_VOUCHER_DEPOSIT, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                refreshActivity();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(statusCode==401){
                    WalletAuthActivity.startAuth(activity, true);
                }else if(statusCode == 500){
                    if (errorResponse != null) {
                        try {
                            errorMsgTxt.setText(errorResponse.getString("message"));
                        } catch (JSONException e) {
                            Log.e("info Error: ", e.getMessage());
                            errorMsgTxt.setText("Error Occurred Please Check and Try again");
                        }
                    } else {

                        Log.e("info", "Something got very very wrong, code: "+statusCode);
                    }
                    Log.e("info 500", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                }
                else if(statusCode == 400){
                    if (errorResponse != null) {
                        try {
                            errorMsgTxt.setText(errorResponse.getString("message"));
                        } catch (JSONException e) {
                            Log.e("info", e.getMessage());
                            errorMsgTxt.setText("Error Occurred Please Check and Try again");
                        }
                    } else {

                        Log.e("info", "Something got very very wrong, code: "+statusCode);
                    }
                    Log.e("info 500", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                }
                else if(statusCode == 406){
                    if (errorResponse != null) {
                        try {
                            errorMsgTxt.setText(errorResponse.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("info", e.getMessage());
                            errorMsgTxt.setText("Error Occurred Please Check and Try again");
                        }
                    }else {

                        Log.e("info", "Something got very very wrong, code: "+statusCode);
                    }
                    Log.e("info 406", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                }
                else {
                    errorMsgTxt.setText("Error Occurred Try again later");
                    if (errorResponse != null) {
                        try {
                            errorMsgTxt.setText(errorResponse.getString("message"));
                        } catch (JSONException e) {
                            Log.e("info", e.getMessage());
                            errorMsgTxt.setText("Error Occurred Please Check and Try again");
                        }
                        Log.e("info", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                    } else {

                        Log.e("info", "Something got very very wrong, code: "+statusCode);
                    }
                }
                errorMsgTxt.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {

                        errorMsgTxt.setText(errorResponse);

                    Log.e("info : "+statusCode,errorResponse );
                } else {

                    Log.e("info : "+statusCode, "Something got very wrong");
                }
                errorMsgTxt.setText("Error Occurred Try again later");
                errorMsgTxt.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });


    }

    public void refreshActivity(){
        this.dismiss();
        Intent goToWallet = new Intent(activity, WalletHomeActivity.class);
        startActivity(goToWallet);
    }

}
