package com.myfarmnow.myfarmcrop.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.myfarmnow.myfarmcrop.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.models.ApiPaths;
import com.myfarmnow.myfarmcrop.services.CropNotificationsCreatorService;
import com.myfarmnow.myfarmcrop.services.CropNotificationsFireService;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CropLoginActivity extends AppCompatActivity {

    EditText edtusername, edtpwd;
    Button btnSignIn;
    TextView tvSignUp;
    TextView tvForgetpass, errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        tvForgetpass = (TextView) findViewById(R.id.tvForgetPass);
        edtusername = (EditText) findViewById(R.id.edtusername);
        edtpwd = (EditText) findViewById(R.id.edtpwd);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        errorTextView =  findViewById(R.id.text_view_crop_user_error);




        if (!CropDashboardActivity.getPreferences("userId",this).isEmpty() ) {
            startActivity(new Intent(CropLoginActivity.this, CropDashboardActivity.class));
            finish();
        }

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtusername.getText().toString().length() <= 0) {
                    Toast.makeText(CropLoginActivity.this, "Enter Email Address or PhoneNumber..", Toast.LENGTH_SHORT).show();
                } else if (edtpwd.getText().toString().length() <= 0) {
                    Toast.makeText(CropLoginActivity.this, "Enter password..", Toast.LENGTH_SHORT).show();
                } else {
                    String loginEntry =edtusername.getText().toString();
                    String email = null;
                    String phoneNumber =null;

                    if(loginEntry.matches(".*[a-zA-Z@].*")){
                        //maybe Email
                        email = loginEntry;
                    }
                    else if(loginEntry.matches(".*[0-9].*")){
                        //maybe phone Number
                        phoneNumber = loginEntry;

                    }

                    checkLogin( email, phoneNumber, edtpwd.getText().toString(), CropLoginActivity.this,errorTextView);
                }

            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(CropLoginActivity.this, CropRegisterActivity.class));
            }
        });

        tvForgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(CropLoginActivity.this, CropForgotPasswordRequestCode.class));
            }
        });
    }

    private String getpreferences(String key) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("pref",
                0);
        return sharedPreferences.getString(key, "0");

    }

    /**
     * @param email
     * @param phoneNumber
     * @param password
     * @param context
     */
    public static void checkLogin(String email, String phoneNumber, String password, final Context context, final TextView  errorTextView) {

        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
      
       // params.put("email", edtusername.getText().toString());
        //params.put("password", edtpwd.getText().toString());
        params.put("email", email);
        params.put("password", password);
        params.put("phoneNumber", phoneNumber);
        


        client.post(ApiPaths.CROP_LOGIN_GET_ALL, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {

                dialog = new ProgressDialog(context);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

                try {
                    if (response.getString("status").equals("failure")) {

                        Toast.makeText(context, "Invalid login detail", Toast.LENGTH_SHORT).show();

                    } else {
                        JSONObject user = response.getJSONObject("user");
                        Toast.makeText(context, "Successfully Logged in..", Toast.LENGTH_SHORT).show();
                        Log.e("response", response.toString());
                        CropDashboardActivity.saveUser(user,context);
                        ((AppCompatActivity)context).finish();

                        //start the notifications services
                        context.startService(new Intent(context, CropNotificationsCreatorService.class));
                        context.startService(new Intent(context, CropNotificationsFireService.class));

                        context.startActivity(new Intent(context, CropDashboardActivity.class));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try{
                    if(statusCode==403){
                        //Toast.makeText(context, errorResponse.getString("message"), Toast.LENGTH_LONG).show();

                        errorTextView.setText(errorResponse.getString("message"));
                        errorTextView.setVisibility(View.VISIBLE);
                        errorTextView.requestFocus();


                        JSONObject data = errorResponse.getJSONObject("data");
                        if( errorResponse.getString("message").contains("Verification Required")){
                            Intent verifyPhoneNumber = new Intent(context, CropVerifyPhoneNumberActivity.class);
                            verifyPhoneNumber.putExtra("userId",data.getString("id"));
                            verifyPhoneNumber.putExtra("phoneNumber",data.getString("phoneNumber"));
                            verifyPhoneNumber.putExtra("countryCode",data.getString("countryCode"));
                            verifyPhoneNumber.putExtra("resendCode","yes");
                            context.startActivity(verifyPhoneNumber);
                        }
                    }
                if (errorResponse != null) {
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
            }
        });
    }




}
