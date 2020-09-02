package com.myfarmnow.myfarmcrop.activities.wallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.models.user_model.UserDetails;
import com.myfarmnow.myfarmcrop.helpers.WalletLoginHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.venmo.android.pin.PinFragment;
import com.venmo.android.pin.PinFragmentConfiguration;
import com.venmo.android.pin.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class WalletAuthActivity extends AppCompatActivity implements  PinFragment.Listener{
    TextView tvForgetpass;
    static TextView errorTextView;
    Context context;
    public static String WALLET_ACCESS_TOKEN=null;
    SharedPreferences sharedPreferences;
    PinFragmentConfiguration pinConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_authentication_manager);


        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);


        errorTextView =  findViewById(R.id.text_view_crop_user_error);
        context=WalletAuthActivity.this;


        if (WalletAuthActivity.WALLET_ACCESS_TOKEN==null){

            pinConfig =new PinFragmentConfiguration(this)
                    .validator(new Validator(){
                        public boolean isValid(String submission){


                            final  ProgressDialog dialog = new ProgressDialog(context);
                            dialog.setIndeterminate(true);
                            dialog.setMessage("Please Wait..");
                            dialog.setCancelable(false);

                            if (submission.length() <= 0) {
                                Toast.makeText(WalletAuthActivity.this, "Enter PIN!", Toast.LENGTH_SHORT).show();
                            }else if( WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_EMAIL, WalletAuthActivity.this )==null && WALLET_ACCESS_TOKEN==null ) {
                                Log.w("Wallet:","attempting user login "+WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_EMAIL, WalletAuthActivity.this ));

                                if (sharedPreferences.getString("userEmail", null) != null) {

                                    WalletLoginHelper.checkLogin( submission, WalletAuthActivity.this, null, dialog);

                                    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                                }

                            }else if( !WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_EMAIL, WalletAuthActivity.this ).isEmpty() &&  WALLET_ACCESS_TOKEN == null  ) {

                                Log.w("Wallet:","getting login token "+WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_EMAIL, WalletAuthActivity.this ));
                                WalletAuthActivity.getLoginToken(  submission,WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_EMAIL, WalletAuthActivity.this ), null,context);
                            }else {
                                Log.w("Wallet: ","attempting user registration "+WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_EMAIL, WalletAuthActivity.this )+" : "+WalletHomeActivity.PREFERENCES_USER_EMAIL);

                                WalletLoginHelper.userRegister( dialog, context,submission);
                            }

                            return submission.equals(DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_PASSWORD, context)); // ...check against where you saved the pin

                        }
                    });

            PinFragment toShow =  PinFragment.newInstanceForVerification(pinConfig);
            getFragmentManager().beginTransaction()
                    .replace( R.id.container, toShow)
                    .commit();
        }else{
            WalletHomeActivity.startHome(getApplicationContext());
        }



    }
    private String getpreferences(String key) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("pref",
                0);
        return sharedPreferences.getString(key, "0");

    }

    public static void  getLoginToken( final String password, String email, String phoneNumber, final Context context) {

        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        params.put("email", email);
        params.put("password", password);
        params.put("phoneNumber", phoneNumber);

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);


        client.post(ApiPaths.WALLET_GET_TOKEN, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                dialog.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.w("Get Token: ", "OnSuccess running");
                try {

                    String accessToken = response.getString("access_token");
                    Log.w("LoginToken: ",accessToken);
                    WALLET_ACCESS_TOKEN = accessToken;

                    WalletHomeActivity.startHome(context);
                    //now you can go to next wallet page


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error: ",e.getMessage());
                }

                if(dialog!=null && dialog.isShowing())
                    dialog.dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try{
                    if(statusCode==403) {
                        //Toast.makeText(context, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                        if (errorTextView != null){
                            errorTextView.setText(errorResponse.getString("message"));
                            errorTextView.setVisibility(View.VISIBLE);
                            errorTextView.requestFocus();
                        }

                    }else if(statusCode==404){
                        Log.e("info", new String(String.valueOf(errorResponse)));
                        WalletLoginHelper.userRegister( dialog, context,password);
                    }
                    if (errorResponse != null) {
                        Log.e("info", new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info", "Something got very very wrong");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(dialog!=null && dialog.isShowing())
                    dialog.dismiss();

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                if(dialog!=null && dialog.isShowing())
                    dialog.dismiss();
            }
        });
    }

    public static void startAuth(Context context, boolean sessionExpired){
        Intent authenticate = new Intent(context, WalletAuthActivity.class);
        authenticate.putExtra("sessionExpired",sessionExpired);
        context.startActivity(authenticate);

    }


    @Override
    public void onValidated() {
        Log.w("PIN", "Pin validated");
    }

    @Override
    public void onPinCreated() {

        Log.w("PIN", "Pin created");
    }
}
