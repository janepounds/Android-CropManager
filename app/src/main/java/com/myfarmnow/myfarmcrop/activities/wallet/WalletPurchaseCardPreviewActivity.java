package com.myfarmnow.myfarmcrop.activities.wallet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletTransactionReceiptResponse;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.models.wallet.WalletPurchase;
import com.myfarmnow.myfarmcrop.models.wallet.WalletTransaction;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;
import com.myfarmnow.myfarmcrop.singletons.WalletSettingsSingleton;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.myfarmnow.myfarmcrop.wallet.database.MyFarmDbHandlerSingleton;

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
        ProgressDialog dialog;
        dialog = new ProgressDialog(WalletPurchaseCardPreviewActivity.this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();
        /******************RETROFIT IMPLEMENTATION************************/
        APIRequests apiRequests = APIClient.getWalletInstance();
        Call<WalletTransactionReceiptResponse> call = apiRequests.
                getReceipt(WalletAuthActivity.WALLET_ACCESS_TOKEN,getIntent().getStringExtra("referenceNumber"));

        call.enqueue(new Callback<WalletTransactionReceiptResponse>() {
            @Override
            public void onResponse(Call<WalletTransactionReceiptResponse> call, Response<WalletTransactionReceiptResponse> response) {
                if(response.code()== 200){
                    try {
                        WalletTransactionReceiptResponse.ReceiptData.TransactionsData transaction = response.body().getData().getTransaction();

                        totalTextView.setText("UGX "+ NumberFormat.getInstance().format(transaction.getAmount()));
                        referenceNoTextView.setText(transaction.getReferenceNumber());
                        merchantNameTextView.setText(transaction.getReceiver());
                        SimpleDateFormat localFormat = new SimpleDateFormat(WalletSettingsSingleton.getInstance().getDateFormat()+" '|' HH:mm:ss a", Locale.ENGLISH);
                        localFormat.setTimeZone(TimeZone.getDefault());
                        String currentDateandTime = null;
                        SimpleDateFormat incomingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        incomingFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        currentDateandTime = localFormat.format(incomingFormat.parse(transaction.getDate()));
                        dateTextView.setText(currentDateandTime);

                        if(transaction.getStatus().equals("Completed"))
                            statusTextView.setText("Successful");
                        else
                            statusTextView.setText(transaction.getStatus());

                        receiptNumberTextView.setText(transaction.getReceiptNumber());

                        if(transaction.getType().equals("FoodPurchase"))
                            serviceTextView.setText("Food Purchase");
                        else
                            serviceTextView.setText("Money "+transaction.getType());

                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("Info2: ","-"+e.getMessage());
                    }


                    dialog.dismiss();
                }else
                    if(response.code()==401){
                        WalletAuthActivity.startAuth(WalletPurchaseCardPreviewActivity.this, true);
                    }
                    else{
                        errorTextView.setText("Error while loading receipt");
                        errorTextView.setVisibility(View.VISIBLE);
                    }
                    if (response.errorBody() != null) {
                        Log.e("info", new String(String.valueOf(response.errorBody())));
                    } else {
                        Log.e("info", "Something got very very wrong");
                    }
                    dialog.dismiss();


            }

            @Override
            public void onFailure(Call<WalletTransactionReceiptResponse> call, Throwable t) {

                    Log.e("info : ", new String(String.valueOf(t.getMessage())));

                    Log.e("info : " ,"Something got very very wrong");


                errorTextView.setText("Error while loading receipt");
                errorTextView.setVisibility(View.VISIBLE);
            }
        });


    }


}
