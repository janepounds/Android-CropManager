package com.myfarmnow.myfarmcrop.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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



public class CropWalletAuthActivity extends AppCompatActivity {
    EditText edtusername, edtpwd;
    Button btnSignIn, btnAuthenticate;
    TextView tvSignUp, tvSignIn;
    TextView tvForgetpass, errorTextView;
    public static String WALLET_ACCESS_TOKEN=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_digital_wallet_authentication_manager);



        edtpwd = findViewById(R.id.edtpwd);
        btnAuthenticate = findViewById(R.id.btnAuthenticate);
        errorTextView =  findViewById(R.id.text_view_crop_user_error);


        btnAuthenticate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               if (edtpwd.getText().toString().length() <= 0) {
                    Toast.makeText(CropWalletAuthActivity.this, "Enter password..", Toast.LENGTH_SHORT).show();
                } else {


                    checkLogin( CropDashboardActivity.getPreferences(CropDashboardActivity.PREFERENCES_USER_EMAIL, CropWalletAuthActivity.this ), null, edtpwd.getText().toString(),errorTextView);
                }

            }
        });


    }
    private String getpreferences(String key) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("pref",
                0);
        return sharedPreferences.getString(key, "0");

    }

    public void checkLogin(String email, String phoneNumber, String password, final TextView  errorTextView) {

        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        // params.put("email", edtusername.getText().toString());
        //params.put("password", edtpwd.getText().toString());
        params.put("email", email);
        params.put("password", password);
        params.put("phoneNumber", phoneNumber);



        client.post(ApiPaths.CROP_WALLET_GET_TOKEN, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {

                dialog = new ProgressDialog(CropWalletAuthActivity.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

                try {

                    String accessToken = response.getString("access_token");
                    WALLET_ACCESS_TOKEN = accessToken;

                    finish();
                    Intent goToWallet = new Intent(CropWalletAuthActivity.this,CropWalletActivity.class);
                    startActivity(goToWallet);
                    //now you can go to next wallet page


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

    public static void startAuth(Context context, boolean sessionExpired){
        Intent authenticate = new Intent(context,CropWalletAuthActivity.class);
        authenticate.putExtra("sessionExpired",sessionExpired);
        context.startActivity(authenticate);
    }


}
