package com.myfarmnow.myfarmcrop.DailogFragments.wallet;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.myfarmnow.myfarmcrop.BuildConfig;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.flutterwave.raveandroid.rave_presentation.RaveNonUIManager;
import com.flutterwave.raveandroid.rave_presentation.ugmobilemoney.UgandaMobileMoneyPaymentCallback;
import com.flutterwave.raveandroid.rave_presentation.ugmobilemoney.UgandaMobileMoneyPaymentManager;
import com.flutterwave.raveutils.verification.RaveVerificationUtils;

import com.myfarmnow.myfarmcrop.models.wallet.WalletTransaction;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Date;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DepositMoneyMobile extends DialogFragment {
    LinearLayout layoutAddMoney;
    Button addMoneyImg;
    TextView addMoneyTxt, phoneNumberTxt, errorMsgTxt;
    static String PENDING_DEPOSIT_REFERENCE_NUMBER;
    TextView balanceTextView;
    double balance;
    private String txRef;
    ProgressDialog dialog;
    Context activity;
    private RaveVerificationUtils verificationUtils;

    public DepositMoneyMobile(Context context, double balance) {
        this.activity = context;
        this.balance = balance;

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
        View view = inflater.inflate(R.layout.wallet_add_money, null);

        builder.setView(view);

        initializeForm(view);

        ImageView close = view.findViewById(R.id.wallet_transfer_money_close);
        close.setOnClickListener(v -> dismiss());

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
        this.txRef = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_WALLET_USER_ID, this.activity) + (new Date().getTime());
        addMoneyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateDeposit();
            }
        });

        verificationUtils = new RaveVerificationUtils(this, false, BuildConfig.PUBLIC_KEY);

    }


    public void initiateDeposit() {
      
        dialog = new ProgressDialog(this.activity);
        dialog.setIndeterminate(true);
        dialog.setMessage("Processing transaction..");
        dialog.setCancelable(false);

        String phoneNumber = phoneNumberTxt.getText().toString();
        String amountEntered = addMoneyTxt.getText().toString();

        double amount = Float.parseFloat(amountEntered);
        txRef = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_WALLET_USER_ID, this.activity) + (new Date().getTime());

        RaveNonUIManager raveNonUIManager = new RaveNonUIManager().setAmount(amount)
                .setCurrency("UGX")
                .setEmail(DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, this.activity))
                .setfName(DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_FIRST_NAME, this.activity))
                .setlName(DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_LAST_NAME, this.activity))
                .setPhoneNumber("0" + phoneNumber)
                .setNarration("Cabral Tech Ltd")
                .setPublicKey(BuildConfig.PUBLIC_KEY)
                .setEncryptionKey(BuildConfig.ENCRYPTION_KEY)
                .setTxRef(txRef)
                .onStagingEnv(false)
                .isPreAuth(true)
                .initialize();

        //cardPayManager = new CardPaymentManager(( raveManager), this, this);
        UgandaMobileMoneyPaymentCallback mobileMoneyPaymentCallback = new UgandaMobileMoneyPaymentCallback() {
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
                Log.e("MobileMoneypaymentError", errorMessage);
            }

            @Override
            public void onSuccessful(String flwRef) {
                dialog.dismiss();
                Log.e("Success code :", flwRef);
                Toast.makeText(activity, "Transaction Successful", Toast.LENGTH_LONG).show();
                creditAfterDeposit(flwRef);
            }

            @Override
            public void showAuthenticationWebPage(String authenticationUrl) {
                Log.e("Loading auth web page: ", authenticationUrl);
                verificationUtils.showWebpageVerificationScreen(authenticationUrl);
            }
        };
        UgandaMobileMoneyPaymentManager mobilePayManager = new UgandaMobileMoneyPaymentManager(raveNonUIManager, (UgandaMobileMoneyPaymentCallback) mobileMoneyPaymentCallback);

        mobilePayManager.charge();
        //mobilePayManager.fetchTransactionFee();


    }


    public void creditAfterDeposit(String txRef) {
        /******************RETROFIT IMPLEMENTATION**************************/
        dialog = new ProgressDialog(this.activity);
        dialog.setIndeterminate(true);
        dialog.setMessage("Crediting Account..");
        dialog.show();

        String amountEntered = addMoneyTxt.getText().toString();
        double amount = Float.parseFloat(amountEntered);
        String referenceNumber = txRef;
        String email = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, this.activity);
        String access_token = WalletAuthActivity.WALLET_ACCESS_TOKEN;
        APIRequests apiRequests = APIClient.getWalletInstance();
        Call<WalletTransaction> call = apiRequests.creditUser(access_token,email,amount,referenceNumber);
        call.enqueue(new Callback<WalletTransaction>() {
            @Override
            public void onResponse(Call<WalletTransaction> call, Response<WalletTransaction> response) {
                if(response.code() == 200){
                    dialog.cancel();

                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    refreshActivity();
                }else if(response.code() == 401){

                        WalletAuthActivity.startAuth(activity, true);
                    } else if (response.code() == 500) {
                        if (response.errorBody() != null) {
                            Toast.makeText(activity,response.body().getRecepient(), Toast.LENGTH_LONG).show();
                        } else {

                            Log.e("info", "Something got very very wrong, code: " + response.code());
                        }
                        Log.e("info 500", String.valueOf(response.errorBody()) + ", code: " + response.code());
                    } else if (response.code() == 400) {
                        if (response.errorBody() != null) {
                            Toast.makeText(activity, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        } else {

                            Log.e("info", "Something got very very wrong, code: " + response.code());
                        }
                        Log.e("info 500", String.valueOf(response.errorBody()) + ", code: " + response.code());
                    } else if (response.code() == 406) {
                        if (response.errorBody() != null) {

                            Toast.makeText(activity, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        } else {

                            Log.e("info", "Something got very very wrong, code: " + response.code());
                        }
                        Log.e("info 406", String.valueOf(response.errorBody()) + ", code: " + response.code());
                    } else {

                        if (response.errorBody() != null) {

                            Toast.makeText(activity, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                            Log.e("info", String.valueOf(response.errorBody()) + ", code: " + response.code());
                        } else {

                            Log.e("info", "Something got very very wrong, code: " + response.code());
                        }
                    }
                    dialog.dismiss();
                }


            @Override
            public void onFailure(Call<WalletTransaction> call, Throwable t) {

            }
        });


    }


    public void refreshActivity() {
        Intent goToWallet = new Intent(activity, WalletHomeActivity.class);
        startActivity(goToWallet);
    }


}
