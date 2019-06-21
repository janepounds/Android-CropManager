package com.myfarmnow.myfarmcrop.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.ApiPaths;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import cz.msebera.android.httpclient.Header;

public class CropWalletActivity extends AppCompatActivity {

    TextView balanceTextView, pendingTransactionTextView;
    double balance=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_digital_wallet);
        if(CropWalletAuthActivity.WALLET_ACCESS_TOKEN==null){
            Intent authenticate = new Intent(this,CropWalletAuthActivity.class);
            startActivity(authenticate);
        }
        initializeWallet();
    }

    public void initializeWallet(){
        balanceTextView = findViewById(R.id.crop_digital_wallet_amount);
        pendingTransactionTextView = findViewById(R.id.txt_pending_transaction);

        if(CropWalletAddMoneyActivity.PENDING_DEPOSIT_REFERENCE_NUMBER != null){
            pendingTransactionTextView.setText("You have a pending Deposit .... Reference Number : "+CropWalletAddMoneyActivity.PENDING_DEPOSIT_REFERENCE_NUMBER);
            pendingTransactionTextView.setVisibility(View.VISIBLE);
            checkRecentDepositStatus(CropWalletAddMoneyActivity.PENDING_DEPOSIT_REFERENCE_NUMBER);
        }
        else  if(CropDigitalWalletWithdrawActivity.PENDING_WITHDRAW_REFERENCE_NUMBER != null){
            pendingTransactionTextView.setText("You have a pending Withdraw .... Reference Number : "+CropDigitalWalletWithdrawActivity.PENDING_WITHDRAW_REFERENCE_NUMBER);
            pendingTransactionTextView.setVisibility(View.VISIBLE);
            checkRecentWithdrawStatus(CropDigitalWalletWithdrawActivity.PENDING_WITHDRAW_REFERENCE_NUMBER);
        }
        else{
            pendingTransactionTextView.setVisibility(View.GONE);
        }
        updateBalance();


    }

    public void updateBalance(){
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization","Bearer "+CropWalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("userId",CropDashboardActivity.getPreferences(CropDashboardActivity.PREFERENCES_USER_ID,this));
        client.post(ApiPaths.CROP_WALLET_GET_BALANCE, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;
            @Override
            public void onStart() {

                dialog = new ProgressDialog(CropWalletActivity.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                     balance = response.getDouble("balance");
                    balanceTextView.setText(NumberFormat.getInstance().format(balance));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {


                if(statusCode==401){
                    Toast.makeText(CropWalletActivity.this, "Session Expired", Toast.LENGTH_LONG).show();
                    CropWalletAuthActivity.startAuth(CropWalletActivity.this, true);

                }
                if (errorResponse != null) {
                    Log.e("info", new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info", "Something got very very wrong");
                }

                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                Toast.makeText(CropWalletActivity.this, "An error occurred Try again Later", Toast.LENGTH_LONG).show();
                Intent authenticate = new Intent(CropWalletActivity.this,CropWalletAuthActivity.class);
                startActivity(authenticate);
            }
        });
    }
    public void openAddMoney(View view){

        Intent openAM = new Intent(this,CropWalletAddMoneyActivity.class);
        openAM.putExtra("balance", balance);
        startActivity(openAM);
    }
    public void openWithdraw(View view){

        Intent openW = new Intent(this, CropDigitalWalletWithdrawActivity.class);
        openW.putExtra("balance", balance);
        startActivity(openW);
    }
    public void openTransfer(View view){
        Intent openT = new Intent(this,CropDigitalWalletTransferActivity.class);
        openT.putExtra("balance", balance);
        startActivity(openT);
    }

    public void checkRecentDepositStatus(String reference){
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization","Bearer "+CropWalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("userId",CropDashboardActivity.getPreferences(CropDashboardActivity.PREFERENCES_USER_ID,this));
        client.setTimeout(20000);
        client.get(ApiPaths.CROP_WALLET_MOMO_CHECK_DEPOSIT_STATUS+reference, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;
            @Override
            public void onStart() {

                dialog = new ProgressDialog(CropWalletActivity.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {

                    if(response.getString("status").toLowerCase().equals("success")){
                        updateBalance();
                        pendingTransactionTextView.setVisibility(View.GONE);
                        //TODO put alert for success
                    }
                    else if(response.getString("status").toLowerCase().equals("failure")){
                        updateBalance();
                        pendingTransactionTextView.setVisibility(View.GONE);
                        //TODO put alert for failure
                    }
                    else if(response.getString("status").toLowerCase().equals("pending")){
                        //TODO Schedule next check
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {


                if(statusCode==401){
                    Toast.makeText(CropWalletActivity.this, "Session Expired", Toast.LENGTH_LONG).show();
                    CropWalletAuthActivity.startAuth(CropWalletActivity.this, true);

                }
                if (errorResponse != null) {
                    Log.e("info", new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info", "Something got very very wrong");
                }

                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                //TODO schedule next try
                dialog.dismiss();
            }
        });
    }


    public void checkRecentWithdrawStatus(String reference){
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization","Bearer "+CropWalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("userId",CropDashboardActivity.getPreferences(CropDashboardActivity.PREFERENCES_USER_ID,this));
        client.setTimeout(20000);
        client.get(ApiPaths.CROP_WALLET_MOMO_CHECK_WITHDRAW_STATUS+reference, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;
            @Override
            public void onStart() {

                dialog = new ProgressDialog(CropWalletActivity.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {

                    if(response.getString("status").toLowerCase().equals("success")){
                        updateBalance();
                        pendingTransactionTextView.setVisibility(View.GONE);
                        //TODO put alert for success
                    }
                    else if(response.getString("status").toLowerCase().equals("failure")){
                        updateBalance();
                        pendingTransactionTextView.setVisibility(View.GONE);
                        //TODO put alert for failure
                    }
                    else if(response.getString("status").toLowerCase().equals("pending")){
                        //TODO Schedule next check
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(statusCode==401){
                    Toast.makeText(CropWalletActivity.this, "Session Expired", Toast.LENGTH_LONG).show();
                    CropWalletAuthActivity.startAuth(CropWalletActivity.this, true);
                }
                if (errorResponse != null) {
                    Log.e("info", new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info", "Something got very very wrong");
                }

                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                //TODO schedule next try
                dialog.dismiss();
            }
        });
    }
}
