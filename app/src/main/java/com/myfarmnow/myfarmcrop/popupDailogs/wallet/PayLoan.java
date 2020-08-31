package com.myfarmnow.myfarmcrop.popupDailogs.wallet;

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
import androidx.fragment.app.FragmentManager;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletLoansListActivity;
import com.myfarmnow.myfarmcrop.fragments.wallet.WalletHomeFragment;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


public class PayLoan extends DialogFragment {

    EditText totalAmountTxt;
    TextView errorTextView;
    Button saveBtn;
    Context activity;
    FragmentManager fm;

    public PayLoan(Context context, FragmentManager supportFragmentManager){
        this.activity=context;
        this.fm=supportFragmentManager;
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
        View view =inflater.inflate(R.layout.wallet_pay_loan, null);

        builder.setView(view);

        initializeForm( view);
        return builder.create();

    }

    public void initializeForm(View view) {

        totalAmountTxt = view.findViewById(R.id.txt_loan_bill_total);
        errorTextView = view.findViewById(R.id.error_message_txt);
        saveBtn = view.findViewById(R.id.btn_pay);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    public void processPayment(){

        float amount = Float.parseFloat(totalAmountTxt.getText().toString());

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        client.addHeader("Authorization","Bearer "+ WalletAuthActivity.WALLET_ACCESS_TOKEN);

        JSONObject requestObject = new JSONObject();

        try {

            requestObject.put("amount",amount);
            requestObject.put("userId", WalletHomeFragment.getPreferences(WalletHomeFragment.PREFERENCES_USER_ID, activity));

            HttpEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
                ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }


            client.post(activity, ApiPaths.WALLET_PAY_LOAN,params,"application/json", new JsonHttpResponseHandler() {

                ProgressDialog dialog;
                @Override
                public void onStart() {
                    dialog = new ProgressDialog(activity);
                    dialog.setIndeterminate(true);
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //logic to save the updated fields
                    dialog.dismiss();

                    Intent openW = new Intent(activity, WalletLoansListActivity.class);
                    startActivity(openW);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    try {
                        errorTextView.setText(errorResponse.getString("message"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        errorTextView.setText("Error occured! Try again later");
                    }
                    errorTextView.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1A: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1A: "+statusCode, "Something got very wrong");
                    }


                    dialog.dismiss();

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}