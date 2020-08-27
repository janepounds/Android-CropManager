package com.myfarmnow.myfarmcrop.popupDailogs.wallet;


import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.myfarmnow.myfarmcrop.BuildConfig;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.flutterwave.raveandroid.rave_presentation.RaveNonUIManager;
import com.flutterwave.raveandroid.rave_presentation.ugmobilemoney.UgandaMobileMoneyPaymentCallback;
import com.flutterwave.raveandroid.rave_presentation.ugmobilemoney.UgandaMobileMoneyPaymentManager;
import com.flutterwave.raveutils.verification.RaveVerificationUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


public class DepositMoneyMobile extends DialogFragment {
    LinearLayout layoutAddMoney;
    Button addMoneyImg;
    TextView addMoneyTxt,phoneNumberTxt,errorMsgTxt;
    static String PENDING_DEPOSIT_REFERENCE_NUMBER;
    TextView balanceTextView;
   double balance;
    private String txRef;
    ProgressDialog dialog;
    Context activity;
    private RaveVerificationUtils verificationUtils;
    public DepositMoneyMobile(Context context, double balance){
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
        View view =inflater.inflate(R.layout.wallet_add_money, null);

        builder.setView(view);

        initializeForm( view);
        return builder.create();

    }

    public void initializeForm(View view) {
        layoutAddMoney = view.findViewById(R.id.layout_add_money);
        phoneNumberTxt = view.findViewById(R.id.crop_add_money_mobile_no);
        addMoneyImg = view.findViewById(R.id.button_add_money);
        addMoneyTxt = view.findViewById(R.id.crop_add_money_amount);
        balanceTextView = view.findViewById(R.id.crop_add_money_balance);
        errorMsgTxt = view.findViewById(R.id.text_view_error_message);

        balanceTextView.setText(NumberFormat.getInstance().format(balance));
        this.txRef=WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_ID,this.activity)+(new Date().getTime());
        addMoneyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateDeposit();
            }
        });

        verificationUtils = new RaveVerificationUtils(this, false, BuildConfig.PUBLIC_KEY);

    }



    public void initiateDeposit(){

        dialog = new ProgressDialog(this.activity);
        dialog.setIndeterminate(true);
        dialog.setMessage("Processing transaction..");
        dialog.setCancelable(false);

        String phoneNumber = phoneNumberTxt.getText().toString();
        String amountEntered = addMoneyTxt.getText().toString();

        double amount = Float.parseFloat(amountEntered);
        txRef=WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_ID,this.activity)+(new Date().getTime());

        RaveNonUIManager raveNonUIManager = new RaveNonUIManager().setAmount(amount)
                .setCurrency("UGX")
                .setEmail( WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_EMAIL,this.activity) )
                .setfName( WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_FIRST_NAME,this.activity) )
                .setlName( WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_LAST_NAME,this.activity) )
                .setPhoneNumber("0"+phoneNumber)
                .setNarration("Cabral Tech Ltd")
                .setPublicKey(BuildConfig.PUBLIC_KEY)
                .setEncryptionKey(BuildConfig.ENCRYPTION_KEY)
                .setTxRef(txRef)
                .onStagingEnv(false)
                .isPreAuth(true)
                .initialize();

        //cardPayManager = new CardPaymentManager(( raveManager), this, this);
        UgandaMobileMoneyPaymentCallback mobileMoneyPaymentCallback= new UgandaMobileMoneyPaymentCallback() {
            @Override
            public void showProgressIndicator(boolean active) {
                try {

                    if (dialog == null) {
                        dialog = new ProgressDialog(activity);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setMessage("Please wait...");
                    }

                    if (active && !dialog.isShowing()) {
                        dialog.show();
                    } else {
                        //dialog.dismiss();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorMessage, @Nullable String flwRef) {
                dialog.dismiss();
                Log.e("MobileMoneypaymentError",errorMessage);
            }

            @Override
            public void onSuccessful(String flwRef) {
                dialog.dismiss();
                Log.e("Success code :",flwRef);
                Toast.makeText(activity, "Transaction Successful", Toast.LENGTH_LONG).show();
                creditAfterDeposit(flwRef);
            }

            @Override
            public void showAuthenticationWebPage(String authenticationUrl) {
                Log.e("Loading auth web page: ",authenticationUrl);
                verificationUtils.showWebpageVerificationScreen(authenticationUrl);
            }
        };
        UgandaMobileMoneyPaymentManager mobilePayManager = new UgandaMobileMoneyPaymentManager( raveNonUIManager, (UgandaMobileMoneyPaymentCallback) mobileMoneyPaymentCallback);

        mobilePayManager.charge();
        //mobilePayManager.fetchTransactionFee();


    }


    public void creditAfterDeposit(String txRef){
        dialog = new ProgressDialog(this.activity);
        dialog.setIndeterminate(true);
        dialog.setMessage("Crediting Account..");



        String amountEntered = addMoneyTxt.getText().toString();
        double amount = Float.parseFloat(amountEntered);

        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization","Bearer "+ WalletAuthActivity.WALLET_ACCESS_TOKEN);

        params.put("email", WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_EMAIL,this.activity));
        params.put("referenceNumber", txRef);
        params.put("amount", amount);

        client.setTimeout(30000);
        client.post(ApiPaths.WALLET_CREDIT_USER_DEPOSIT, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                dialog.cancel();

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                refreshActivity();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(statusCode==401){
                    WalletAuthActivity.startAuth(activity, true);
                }else if(statusCode == 500){
                    if (errorResponse != null) {
                        try {
                            Toast.makeText(activity, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("info Error: ", e.getMessage());

                        }
                    } else {

                        Log.e("info", "Something got very very wrong, code: "+statusCode);
                    }
                    Log.e("info 500", String.valueOf(errorResponse) +", code: "+statusCode);
                }
                else if(statusCode == 400){
                    if (errorResponse != null) {
                        try {
                            Toast.makeText(activity, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("info", e.getMessage());
                        }
                    } else {

                        Log.e("info", "Something got very very wrong, code: "+statusCode);
                    }
                    Log.e("info 500", String.valueOf(errorResponse) +", code: "+statusCode);
                }
                else if(statusCode == 406){
                    if (errorResponse != null) {
                        try {

                            Toast.makeText(activity, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("info", e.getMessage());
                        }
                    }else {

                        Log.e("info", "Something got very very wrong, code: "+statusCode);
                    }
                    Log.e("info 406", String.valueOf(errorResponse) +", code: "+statusCode);
                }
                else {

                    if (errorResponse != null) {
                        try {

                            Toast.makeText(activity, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("info", e.getMessage());
                        }
                        Log.e("info", String.valueOf(errorResponse) +", code: "+statusCode);
                    } else {

                        Log.e("info", "Something got very very wrong, code: "+statusCode);
                    }
                }
                dialog.dismiss();

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {

                    Toast.makeText(activity, errorResponse, Toast.LENGTH_LONG).show();

                    Log.e("info : "+statusCode,errorResponse );
                } else {

                    Log.e("info : "+statusCode, "Something got very wrong");
                }
                dialog.dismiss();
            }
        });


    }



    public void refreshActivity(){
        Intent goToWallet = new Intent(activity, WalletHomeActivity.class);
        startActivity(goToWallet);
    }


}
