package com.myfarmnow.myfarmcrop.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.fragments.wallet.WalletHomeFragment;
import com.myfarmnow.myfarmcrop.models.user_model.UserData;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class WalletLoginHelper {



    public static void checkLogin(final String rawpassword, final Context context, final TextView errorTextView, final ProgressDialog dialog) {
        final RequestParams params = new RequestParams();
        /*****RETROFIT IMPLEMENTATION*******/
        final String email = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, context);
        final String phoneNumber = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_PHONE_NUMBER, context);
        params.put("email", email);
        params.put("password", rawpassword);
        params.put("phoneNumber", phoneNumber);
        APIRequests apiRequests = APIClient.getWalletInstance();
        Call<UserData> call = apiRequests.authenticate(email,rawpassword);
        Log.w("email_log",email);
        Log.e("Info ", ApiPaths.Emaisha_Wallet_LOGIN_GET_ALL + "?" + params);
        dialog.show();
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.isSuccessful()) {
                    String status = response.body().getSuccess();
                    try {
                        if (status.equals("failure")) {

                            Toast.makeText(context, "Invalid login detail", Toast.LENGTH_SHORT).show();

                        } else {
                            Gson gson = new Gson();
                            String user = gson.toJson(response.body().getData());
                            JSONObject object =new JSONObject(user);
                            Toast.makeText(context, "Successfully Logged in..", Toast.LENGTH_SHORT).show();
                            Log.e("response", response.toString());
                            WalletHomeActivity.saveUser(object, context);
                            ((AppCompatActivity) context).finish();
                            dialog.dismiss();
                            WalletAuthActivity.getLoginToken(rawpassword, email, phoneNumber, context);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(response.errorBody()!=null){
                    Log.e("info1 " , String.valueOf(response.errorBody().toString()));
                }

                if (response.code() == 403) {
                    //Toast.makeText(context, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                    if (errorTextView != null) {
                        errorTextView.setText(response.body().getMessage());
                        errorTextView.setVisibility(View.VISIBLE);
                        errorTextView.requestFocus();
                    }


                } else if (response.code() == 404) {

                    WalletLoginHelper.userRegister(dialog, context, rawpassword);
                }
                dialog.dismiss();


            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.e("info1 " , String.valueOf(t.getMessage()));

                Log.e("info1", "Something got very  wrong 0001");

                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public static void userRegister(final ProgressDialog dialog, final Context context, final String rawPassword) {
        /******RETROFIT IMPLEMENTATION*********/
        String email = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, context);
        String firstname = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_FIRST_NAME, context);
        String lastname = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_LAST_NAME, context);
        String addressStreet = DashboardActivity.getPreferences(DashboardActivity.STREET_PREFERENCES_ID, context);
        String addressCityOrTown = DashboardActivity.getPreferences(DashboardActivity.CITY_PREFERENCES_ID, context);
        String password = rawPassword;
        String phoneNumber =DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_PHONE_NUMBER, context);
        final RequestParams params = new RequestParams();

        params.put("firstname", "" + firstname);
        params.put("lastname", "" + lastname);
        params.put("addressStreet", "" + addressStreet);
        params.put("addressCityOrTown", "" + addressCityOrTown);
        params.put("email", "" + email);
        params.put("password", "" + rawPassword);
        params.put("phoneNumber", phoneNumber);
        Log.e("response", ApiPaths.Wallet_CREATE_USER + params);


        APIRequests apiRequests = APIClient.getWalletInstance();
        Call<UserData> call = apiRequests.create(firstname,lastname,email,password,phoneNumber,addressStreet,addressCityOrTown);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.isSuccessful()){
                    try {
                        Gson gson = new Gson();
                        String user = gson.toJson(response.body().getData());
                        JSONObject object =new JSONObject(user);
                        Toast.makeText(context, "Successfully Logged in..", Toast.LENGTH_SHORT).show();
                        Log.e("response", response.toString());
                        WalletHomeActivity.saveUser(object, context);

                        dialog.dismiss();

                        WalletAuthActivity.getLoginToken(rawPassword, email, null, context);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("Error:", e.getMessage());
                    }
                }else{
                    if (response.code() == 403) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();

                    } else if (response.code() == 400 ) {
                        //user has account
                        Log.w("Account Exists", "Attempting  User Login");
                        WalletLoginHelper.checkLogin(rawPassword, context, null, dialog);

                    } else {

                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {

                    Log.e("info : " , t.getMessage());

                dialog.dismiss();
            }

        });



    }


}
