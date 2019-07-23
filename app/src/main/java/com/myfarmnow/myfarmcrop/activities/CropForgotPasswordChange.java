package com.myfarmnow.myfarmcrop.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
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

public class CropForgotPasswordChange extends AppCompatActivity {


    EditText codeTxt;
    EditText edt_pass,edt_confirm_pass;
    Button btn_submit;

    String str_Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass_last_);



        codeTxt = (EditText) findViewById(R.id.edtotp);
        edt_pass = (EditText) findViewById(R.id.edtpwd);
        edt_confirm_pass = (EditText) findViewById(R.id.edtconfirmpwd);


        btn_submit = (Button) findViewById(R.id.btnsubmit);
        if(getIntent().hasExtra("phoneNumber")&& getIntent().hasExtra("countryCode")){

        }else{
            finish();
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (codeTxt.getText().toString().equals("") || codeTxt.getText().toString().equals(null) || codeTxt.getText().toString().equals(" ")||
                        edt_pass.getText().toString().equals("") || edt_pass.getText().toString().equals(null) ||edt_pass.getText().toString().equals(" ")||
                        edt_confirm_pass.getText().toString().equals("") || edt_confirm_pass.getText().toString().equals(null) ||edt_confirm_pass.getText().toString().equals(" ")){

                        Toast.makeText(CropForgotPasswordChange.this, "Required field(s) missing.", Toast.LENGTH_SHORT).show();
                }
                else if(!edt_pass.getText().toString().equals(edt_confirm_pass.getText().toString())){
                    edt_confirm_pass.requestFocus();
                    Toast.makeText(CropForgotPasswordChange.this, "Password Mismatch", Toast.LENGTH_SHORT).show();

                }
                else {
                    changePassword();
                }

            }
        });

    }

    public void changePassword() {



        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();



        params.put("phoneNumber", getIntent().getStringExtra("phoneNumber"));
        params.put("countryCode", getIntent().getStringExtra("countryCode"));
        params.put("code", codeTxt.getText().toString());
        params.put("newPassword", edt_pass.getText().toString());



        client.post(ApiPaths.CROP_FORGOT_PASSWORD_CHANGE, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
                dialog = new ProgressDialog(CropForgotPasswordChange.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");

                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    Log.d("USER",response.toString());
                    JSONObject user = response.getJSONObject("user");

                    CropDashboardActivity.saveUser(user,CropForgotPasswordChange.this);
                    Intent verifyPhoneNumber = new Intent(CropForgotPasswordChange.this, CropLoadBackUpActivity.class);
                    startActivity(verifyPhoneNumber);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    Log.e("info", new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info", "Something got very very wrong");
                }
            }
        });
    }
}