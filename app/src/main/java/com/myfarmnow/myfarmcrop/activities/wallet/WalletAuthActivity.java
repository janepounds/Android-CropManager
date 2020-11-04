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
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.helpers.WalletLoginHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.TokenResponse;
import com.myfarmnow.myfarmcrop.models.user_model.UserData;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;
import com.venmo.android.pin.PinFragment;
import com.venmo.android.pin.PinFragmentConfiguration;
import com.venmo.android.pin.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WalletAuthActivity extends AppCompatActivity implements  PinFragment.Listener{
    static TextView errorTextView;
    Context context;
    public static String WALLET_ACCESS_TOKEN=null;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_authentication_manager);


        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);


        errorTextView =  findViewById(R.id.text_view_crop_user_error);
        context=WalletAuthActivity.this;


        if ( ConstantValues.CUSTOMER_HAS_WALLET && WalletAuthActivity.WALLET_ACCESS_TOKEN==null){

            PinFragmentConfiguration   pinConfig =new PinFragmentConfiguration(WalletAuthActivity.this)
                    .validator(new Validator(){
                        public boolean isValid(String submission){

                            final  ProgressDialog dialog = new ProgressDialog(context);
                            dialog.setIndeterminate(true);
                            dialog.setMessage("Please Wait..");
                            dialog.setCancelable(false);
                            String WalletPass=DashboardActivity.PREFERENCES_PREPIN_ENCRYPTION+submission;

                            if (submission.length() < 4) {
                                Toast.makeText(WalletAuthActivity.this, "Enter PIN!", Toast.LENGTH_SHORT).show();
                            }else {
                                //login and get token
                                Log.w("Wallet:","attempting user login "+WalletHomeActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, WalletAuthActivity.this ));

                                if (WalletHomeActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, WalletAuthActivity.this ) != null) {

                                    WalletLoginHelper.checkLogin( WalletPass, WalletAuthActivity.this, null, dialog);

                                    overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                                }

                            }
                            return WalletPass.equals(DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_PASSWORD, context));
                           // return submission.equals(DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_PIN, context)); // ...check against where you saved the pin

                        }
                    });

            PinFragment toShow =  PinFragment.newInstanceForVerification(pinConfig);
            getFragmentManager().beginTransaction()
                    .replace( R.id.container, toShow)
                    .commit();
        }
        else if( !ConstantValues.CUSTOMER_HAS_WALLET){
            PinFragmentConfiguration    pinConfig2 =new PinFragmentConfiguration(this)
                    .validator(new Validator(){
                        public boolean isValid(String submission){

                            final  ProgressDialog dialog = new ProgressDialog(context);
                            dialog.setIndeterminate(true);
                            dialog.setMessage("Please Wait..");
                            dialog.setCancelable(false);
                            String WalletPass=DashboardActivity.PREFERENCES_PREPIN_ENCRYPTION+submission;
                            if (submission.length() <= 0) {
                                Toast.makeText(WalletAuthActivity.this, "Enter PIN!", Toast.LENGTH_SHORT).show();
                            }else {
                                Log.w("Wallet: ","attempting user registration "+WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_EMAIL, WalletAuthActivity.this )+" : "+WalletHomeActivity.PREFERENCES_USER_EMAIL);

                                WalletLoginHelper.userRegister( dialog, context,WalletPass);
                            }

                            return WalletPass.equals(DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_PASSWORD, context)); // ...check against where you saved the pin

                        }
                    });

            PinFragment toShow =  PinFragment.newInstanceForCreation(pinConfig2);
            getFragmentManager().beginTransaction()
                    .replace( R.id.container, toShow)
                    .commit();
        }
        else{
            WalletHomeActivity.startHome(getApplicationContext());
            this.finish();
        }

    }

    public static void  getLoginToken( final String password, String email, String phoneNumber, final Context context) {

        /****RETROFIT IMPLEMENTATION*******/
        APIRequests apiRequests = APIClient.getWalletInstance();
        Call<TokenResponse> call = apiRequests.getToken(email,password);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.code()==200) {
                    Log.w("Get Token: ", "OnSuccess running");
                    TokenResponse tokenResponse = response.body();

                    String accessToken = tokenResponse.getData().getAccess_token();
                    Log.w("LoginToken: ",accessToken);
                        WALLET_ACCESS_TOKEN = accessToken;


                    WalletHomeActivity.startHome(context);
                    //now you can go to next wallet page
                }else {

                    if(response.code()==403) {
                        //Toast.makeText(context, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                        if (errorTextView != null){
                            errorTextView.setText(response.body().getMessage());
                            errorTextView.setVisibility(View.VISIBLE);
                            errorTextView.requestFocus();
                        }

                    }
                    if (response.errorBody() != null) {
                        Log.e("info", new String(response.message()));
                    } else {
                        Log.e("info", "Something got very very wrong");
                    }

                }


            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

                    Log.e("info : ", new String(String.valueOf(t.getMessage())));
                    Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();

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
