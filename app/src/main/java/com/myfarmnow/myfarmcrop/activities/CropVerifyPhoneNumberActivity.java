package com.myfarmnow.myfarmcrop.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.ApiPaths;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CropVerifyPhoneNumberActivity extends AppCompatActivity {

    TextView messageTextView, resendCodeTextView;
    EditText codeTxt;
    Button verifyButton;
    String phoneNumber, countryCode, userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_verify_phone_number);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        if(getIntent().hasExtra("userId") && getIntent().hasExtra("phoneNumber")&& getIntent().hasExtra("countryCode")){
            userId = getIntent().getStringExtra("userId");
            phoneNumber = getIntent().getStringExtra("phoneNumber");
            countryCode = getIntent().getStringExtra("countryCode");
            
        }else{
            finish();
        }



        messageTextView = findViewById(R.id.text_view_crop_verify_phone_message);
        resendCodeTextView = findViewById(R.id.text_view_crop_verify_resend_code);
        codeTxt = findViewById(R.id.txt_crop_verify_phone_code);
        verifyButton = findViewById(R.id.btn_crop_verify_phone);


        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                final RequestParams params = new RequestParams();

                //   String tokens = SharedPreferences.(this).gettoken();

                params.put("code",codeTxt.getText().toString());
                params.put("userId",userId);
                params.put("phoneNumber",phoneNumber);
                params.put("countryCode",countryCode);


                client.post(ApiPaths.CROP_USER_VERIFY_PHONE, params, new JsonHttpResponseHandler() {
                    ProgressDialog dialog;

                    @Override
                    public void onStart() {

                        dialog = new ProgressDialog(CropVerifyPhoneNumberActivity.this);
                        dialog.setIndeterminate(true);
                        dialog.setMessage("Please Wait..");

                        dialog.setCancelable(false);
                        dialog.show();
                    }


                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // If the response is JSONObject instead of expected JSONArray
                        try {
                            JSONObject user = response.getJSONObject("user");
                            Toast.makeText(CropVerifyPhoneNumberActivity.this, "Phone Number Verified", Toast.LENGTH_SHORT).show();
                            Log.e("response", response.toString());
                            CropDashboardActivity.saveUser(user,CropVerifyPhoneNumberActivity.this);
                            Intent verifyPhoneNumber = new Intent(CropVerifyPhoneNumberActivity.this,  CropLoadBackUpActivity.class);
                            startActivity(verifyPhoneNumber);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {


                            if (errorResponse != null) {
                                String message = errorResponse.getString("message");
                                //
                                if(message.contains("No pending verifications")){
                                    Toast.makeText(CropVerifyPhoneNumberActivity.this, "The Code has expired. Request new code", Toast.LENGTH_SHORT).show();
                                }
                                Log.e("info", new String(String.valueOf(errorResponse)));
                            } else {
                                Log.e("info", "Something got very very wrong");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                        dialog.dismiss();
                    }
                });
            }
        });



        resendCodeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendCode();


            }
        });
        messageTextView.setText("A code has been sent to " +phoneNumber+". Enter it below to verify your account");

        if(getIntent().hasExtra("resendCode")){
            resendCode();
        }



    }


    public void resendCode(){




        AlertDialog.Builder builder = new AlertDialog.Builder(CropVerifyPhoneNumberActivity.this);

        View dialogView = LayoutInflater.from(this).inflate(R.layout.activity_popup_edittext,null);


        builder.setMessage("Please confirm your phone number!");

        final EditText codeTxt = dialogView.findViewById(R.id.edtCountryCode);
        final EditText numberTxt = dialogView.findViewById(R.id.edtContact);

        builder.setView(dialogView);
        builder.setPositiveButton("RE-SEND", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                    AsyncHttpClient client = new AsyncHttpClient();
                    final RequestParams params = new RequestParams();
                    phoneNumber ="+" +codeTxt.getText().toString()+ numberTxt.getText().toString();
                    countryCode = codeTxt.getText().toString();
                    params.put("verificationMethod","sms");
                    params.put("userId",userId);
                    params.put("phoneNumber",phoneNumber);
                    params.put("countryCode",countryCode);
                    client.post(ApiPaths.CROP_USER_RESEND_CODE, params, new JsonHttpResponseHandler() {
                        ProgressDialog dialog;

                        @Override
                        public void onStart() {

                            dialog = new ProgressDialog(CropVerifyPhoneNumberActivity.this);
                            dialog.setIndeterminate(true);
                            dialog.setMessage("Please Wait..");

                            dialog.setCancelable(false);
                            dialog.show();
                        }


                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                            CropDashboardActivity.savePreferences("phoneNumber",phoneNumber,CropVerifyPhoneNumberActivity.this);
                            CropDashboardActivity.savePreferences("countryCode",countryCode,CropVerifyPhoneNumberActivity.this);

                            // If the response is JSONObject instead of expected JSONArray
                            try {

                                messageTextView.setText(response.getString("message")+ " Enter the code below to verify your account");
                                Log.d("response", response.toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            try {


                                if (errorResponse != null) {
                                    String message = errorResponse.getString("message");
                                    //

                                    Toast.makeText(CropVerifyPhoneNumberActivity.this, message, Toast.LENGTH_SHORT).show();

                                    Log.e("info", new String(String.valueOf(errorResponse)));
                                } else {
                                    Log.e("info", "Something got very very wrong");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                            dialog.dismiss();
                        }
                    });
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
