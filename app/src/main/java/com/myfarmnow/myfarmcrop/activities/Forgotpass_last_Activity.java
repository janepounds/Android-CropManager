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

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Forgotpass_last_Activity extends AppCompatActivity {


    EditText edt_otp;
    EditText edt_pass,edt_confirm_pass;
    Button btn_submit;

    String str_Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpass_last_);






        edt_otp = (EditText) findViewById(R.id.edtotp);
        edt_pass = (EditText) findViewById(R.id.edtpwd);
        edt_confirm_pass = (EditText) findViewById(R.id.edtconfirmpwd);

        btn_submit = (Button) findViewById(R.id.btnsubmit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_otp.getText().toString().equals("") || edt_otp.getText().toString().equals(null) ||edt_otp.getText().toString().equals(" ")||
                        edt_pass.getText().toString().equals("") || edt_pass.getText().toString().equals(null) ||edt_pass.getText().toString().equals(" ")||
                        edt_confirm_pass.getText().toString().equals("") || edt_confirm_pass.getText().toString().equals(null) ||edt_confirm_pass.getText().toString().equals(" ")){

                        Toast.makeText(Forgotpass_last_Activity.this, "Required field(s) missing.", Toast.LENGTH_SHORT).show();

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



        params.put("userid", getIntent().getStringExtra("userid"));
        params.put("otp", edt_otp.getText().toString());
        params.put("password", edt_pass.getText().toString());
        params.put("conf_pass", edt_confirm_pass.getText().toString());


        client.post("http://myfarmnow.info/new_password.php?", params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
                Log.e("params", "" + "http://myfarmnow.info/new_password.php?" + params);
                dialog = new ProgressDialog(Forgotpass_last_Activity.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");

                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    if (response.getInt("success") == 0) {
                        Toast.makeText(Forgotpass_last_Activity.this, "Required field(s) missing.", Toast.LENGTH_SHORT).show();

                    } else {


                        Log.e("response", "" + response.toString());

                        Toast.makeText(Forgotpass_last_Activity.this, "Password Change Successfully...", Toast.LENGTH_SHORT).show();

                        finish();
                        startActivity(new Intent(Forgotpass_last_Activity.this, CropLoginActivity.class));


                    }
                    dialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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