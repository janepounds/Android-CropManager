package com.myfarmnow.myfarmcrop.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.myfarmnow.myfarmcrop.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.models.ApiPaths;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CropForgotPasswordRequestCode extends AppCompatActivity {

    EditText countryCodeTxt,phoneNumberTxt;
    Button btn_send_otp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_forget_password_request_code);

        countryCodeTxt = (EditText) findViewById(R.id.txt_crop_forgot_password_country_code);
        phoneNumberTxt = (EditText) findViewById(R.id.txt_crop_forgot_password_phone_number);
        btn_send_otp = (Button) findViewById(R.id.btnsendotp);

        btn_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (countryCodeTxt.getText().toString().isEmpty()){
                    countryCodeTxt.requestFocus();
                    Toast.makeText(CropForgotPasswordRequestCode.this, "Required field(s) missing.", Toast.LENGTH_SHORT).show();

                }else if (phoneNumberTxt.getText().toString().isEmpty()){

                    phoneNumberTxt.requestFocus();
                    Toast.makeText(CropForgotPasswordRequestCode.this, "Required field(s) missing.", Toast.LENGTH_SHORT).show();

                }
                else {
                    sendotp();
                }



            }
        });

    }

    public void   sendotp() {



        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        final String phoneNumber =  "+"+countryCodeTxt.getText().toString()+phoneNumberTxt.getText().toString();
        params.put("phoneNumber",phoneNumber);
        params.put("countryCode",countryCodeTxt.getText().toString());

        client.post(ApiPaths.CROP_FORGOT_PASSWORD_REQUEST_CODE, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
                dialog = new ProgressDialog(CropForgotPasswordRequestCode.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");

                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Intent intent=new Intent(CropForgotPasswordRequestCode.this, CropForgotPasswordChange.class);
                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("countryCode",countryCodeTxt.getText().toString());
                startActivity(intent);
                finish();
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    try {
                        Toast.makeText(CropForgotPasswordRequestCode.this, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                dialog.dismiss();
            }
        });
    }
}
