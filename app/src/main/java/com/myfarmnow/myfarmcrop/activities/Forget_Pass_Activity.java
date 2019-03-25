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

public class Forget_Pass_Activity extends AppCompatActivity {

    EditText edt_Emailid;
    Button btn_send_otp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__pass_);

        edt_Emailid = (EditText) findViewById(R.id.edt_usearemail);
        btn_send_otp = (Button) findViewById(R.id.btnsendotp);

        btn_send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_Emailid.getText().toString().equals("") || edt_Emailid.getText().toString().equals(null) ||edt_Emailid.getText().toString().equals(" ")){

                    Toast.makeText(Forget_Pass_Activity.this, "Required field(s) missing.", Toast.LENGTH_SHORT).show();

                }else {
                    sendotp();
                }



            }
        });

    }

    public void   sendotp() {



        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        params.put("email", edt_Emailid.getText().toString());


        client.post("http://myfarmnow.info/forgot_password.php?", params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
                Log.e("params", "" + "http://myfarmnow.info/add_logo.php?" + params);
                dialog = new ProgressDialog(Forget_Pass_Activity.this);
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
                        Toast.makeText(Forget_Pass_Activity.this, "Required field(s) missing.", Toast.LENGTH_SHORT).show();

                    } else {
                      //  JSONObject jbn = response.getJSONObject("1");


                        Log.e("response", "" + response.toString());

                        Toast.makeText(Forget_Pass_Activity.this, "Send OTP Successfully", Toast.LENGTH_SHORT).show();

                        Toast.makeText(Forget_Pass_Activity.this, "Mail Send Successfully....", Toast.LENGTH_SHORT).show();

                        String ikd=response.getString("userid");
                        Intent intent=new Intent(Forget_Pass_Activity.this, Forgotpass_last_Activity.class);
                        intent.putExtra("userid",response.getString("userid"));
                        startActivity(intent);
                        finish();

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
