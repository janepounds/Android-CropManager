package com.myfarmnow.myfarmcrop.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.ApiPaths;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import cz.msebera.android.httpclient.Header;

public class CropWalletAddMoneyActivity extends AppCompatActivity {

    LinearLayout  layoutAddMoney;
    Button addMoneyImg;
    TextView addMoneyTxt,phoneNumberTxt,errorMsgTxt;
    Spinner countryCodeSp;
    static String PENDING_DEPOSIT_REFERENCE_NUMBER;
    TextView balanceTextView, titleTextView;
    double balance=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_digital_wallet_add_money);

        initializeForm();
    }

    public void initializeForm() {
        layoutAddMoney = findViewById(R.id.layout_add_money);
        phoneNumberTxt = findViewById(R.id.crop_add_money_mobile_no);
        addMoneyImg = findViewById(R.id.button_add_money);
        addMoneyTxt = findViewById(R.id.crop_add_money_amount);
        countryCodeSp = findViewById(R.id.sp_crop_digital_wallet_country_code);
        balanceTextView = findViewById(R.id.crop_add_money_balance);
        titleTextView = findViewById(R.id.crop_digital_wallet_label);
        errorMsgTxt = findViewById(R.id.text_view_error_message);
        if(getIntent().hasExtra("balance")){
            balance=getIntent().getDoubleExtra("balance",0);
        }

        balanceTextView.setText(NumberFormat.getInstance().format(balance));
        titleTextView.setText("Add Money");
        ((ArrayAdapter)countryCodeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,12);//Change selected text size
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        countryCodeSp.setOnItemSelectedListener(onItemSelectedListener);

        addMoneyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateDeposit();
            }
        });

    }

    public void initiateDeposit(){

        String countryCode = countryCodeSp.getSelectedItem().toString();
        String phoneNumber = phoneNumberTxt.getText().toString();
        String amountEntered = addMoneyTxt.getText().toString();
        
        float amount = Float.parseFloat(amountEntered);

        countryCode =countryCode.replace("+","");
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization","Bearer "+CropWalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("userId",CropDashboardActivity.getPreferences(CropDashboardActivity.PREFERENCES_USER_ID,this));
        params.put("countryCode",countryCode);
        params.put("phoneNumber",countryCode+phoneNumber);
        params.put("amount",amount);
        params.put("currency","UGX");
        client.setTimeout(20000);
        client.post(ApiPaths.CROP_WALLET_INITIATE_MOMO_DEPOSIT, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;
            @Override
            public void onStart() {

                dialog = new ProgressDialog(CropWalletAddMoneyActivity.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    PENDING_DEPOSIT_REFERENCE_NUMBER = response.getString("referenceNumber");
                    finish();
                    Intent goToWallet = new Intent(CropWalletAddMoneyActivity.this,CropWalletActivity.class);
                    startActivity(goToWallet);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(statusCode==401){
                    CropWalletAuthActivity.startAuth(CropWalletAddMoneyActivity.this, true);
                }else if(statusCode == 500){
                    errorMsgTxt.setText("Error Occurred Try again later");
                    Log.e("info 500", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                }
                else if(statusCode == 400){
                    errorMsgTxt.setText("Check your input details");
                    Log.e("info 500", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                }
                else if(statusCode == 406){
                    try {
                        errorMsgTxt.setText(errorResponse.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("info 406", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                }
                else {
                    errorMsgTxt.setText("Error Occurred Try again later");
                    if (errorResponse != null) {
                        Log.e("info", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                    } else {
                        Log.e("info", "Something got very very wrong, code: "+statusCode);
                    }
                }
                errorMsgTxt.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                errorMsgTxt.setText("Error Occurred Try again later");
                errorMsgTxt.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });
        
        


    }




}
