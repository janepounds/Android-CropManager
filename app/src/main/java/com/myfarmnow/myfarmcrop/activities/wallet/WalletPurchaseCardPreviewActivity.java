package com.myfarmnow.myfarmcrop.activities.wallet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cabral.emaisha.R;
import com.cabral.emaisha.wallet.models.ApiPaths;
import com.cabral.emaisha.wallet.models.WalletTransaction;
import com.cabral.emaisha.wallet.singletons.WalletSettingsSingleton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;

//import com.cabral.emaisha.wallet.database.MyFarmDbHandlerSingleton;

public class WalletPurchaseCardPreviewActivity extends AppCompatActivity {

    TextView serviceTextView, receiptNumberTextView, statusTextView,totalTextView,
            merchantNameTextView,errorTextView,dateTextView,referenceNoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_purchase_card_preview);
        initializeView();
    }

    public void initializeView(){
        serviceTextView = findViewById(R.id.text_view_purchase_service);
        receiptNumberTextView = findViewById(R.id.text_view_purchase_receipt_number);
        statusTextView = findViewById(R.id.text_view_purchase_status);
        totalTextView = findViewById(R.id.txt_view_bill_preview_total);
        errorTextView = findViewById(R.id.text_view_purchase_preview_error);
        merchantNameTextView = findViewById(R.id.text_view_purchase_preview_name);
        dateTextView = findViewById(R.id.text_view_purchase_date_time);
        referenceNoTextView = findViewById(R.id.text_view_purchase_reference_number);

        totalTextView.setText( NumberFormat.getInstance().format(WalletTransaction.getInstance().getAmount()));
        dateTextView.setText(WalletTransaction.getInstance().getDate());
        referenceNoTextView.setText(WalletTransaction.getInstance().getReferenceNumber());
        actualStatementData();
    }

    private void actualStatementData() {

        if(!getIntent().hasExtra("referenceNumber")){
            finish();
        }
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization","Bearer "+ WalletAuthActivity.WALLET_ACCESS_TOKEN);

        client.get(ApiPaths.WALLET_PAYMENTS_MERCHANT_RECEIPT +getIntent().getStringExtra("referenceNumber"), params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;
            @Override
            public void onStart() {
                dialog = new ProgressDialog(WalletPurchaseCardPreviewActivity.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONObject transaction = response.getJSONObject("transaction");
                    totalTextView.setText("UGX "+ NumberFormat.getInstance().format(transaction.getDouble("amount")));
                    referenceNoTextView.setText(transaction.getString("referenceNumber"));
                    merchantNameTextView.setText(transaction.getString("receiver"));
                    SimpleDateFormat localFormat = new SimpleDateFormat(WalletSettingsSingleton.getInstance().getDateFormat()+" '|' HH:mm:ss a", Locale.ENGLISH);
                    localFormat.setTimeZone(TimeZone.getDefault());
                    String currentDateandTime = null;
                    SimpleDateFormat incomingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    incomingFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    currentDateandTime = localFormat.format(incomingFormat.parse(transaction.getString("date")));
                    dateTextView.setText(currentDateandTime);

                    if(transaction.getString("status").equals("Completed"))
                        statusTextView.setText("Successful");
                    else
                    statusTextView.setText(transaction.getString("status"));

                    receiptNumberTextView.setText(transaction.getString("receiptNumber"));

                    if(transaction.getString("type").equals("FoodPurchase"))
                        serviceTextView.setText("Food Purchase");
                    else
                        serviceTextView.setText("Money "+transaction.getString("type"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Info1: ","-"+e.getMessage());
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("Info2: ","-"+e.getMessage());
                }


                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(statusCode==401){
                    WalletAuthActivity.startAuth(WalletPurchaseCardPreviewActivity.this, true);
                }
                else{
                    errorTextView.setText("Error while loading receipt");
                    errorTextView.setVisibility(View.VISIBLE);
                }
                if (errorResponse != null) {
                    Log.e("info", new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info", "Something got very very wrong");
                }
                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }

                errorTextView.setText("Error while loading receipt");
                errorTextView.setVisibility(View.VISIBLE);
                //WalletAuthActivity.startAuth(WalletPurchaseCardPreviewActivity.this, true);
            }
        });




    }




}
