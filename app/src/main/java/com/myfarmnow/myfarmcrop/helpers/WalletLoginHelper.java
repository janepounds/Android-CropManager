package com.myfarmnow.myfarmcrop.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletAuthentication;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletUserRegistration;
import com.myfarmnow.myfarmcrop.models.user_model.UserData;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class WalletLoginHelper extends AppCompatActivity {


    public static void checkLogin(final String rawpassword, final Context context, final TextView errorTextView, final ProgressDialog dialog) {

        final RequestParams params = new RequestParams();

        /*****RETROFIT IMPLEMENTATION*******/
        final String email = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, context);
        final String phoneNumber = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_PHONE_NUMBER, context);
        params.put("email", email);
        params.put("password", rawpassword);
        params.put("phoneNumber", phoneNumber);
        APIRequests apiRequests = APIClient.getWalletInstance();
        Call<WalletAuthentication> call = apiRequests.authenticate(email, rawpassword);
        Log.w("email_log", email);
        Log.w("Info ", ApiPaths.Emaisha_Wallet_LOGIN_GET_ALL + "?" + params);
        dialog.show();
        call.enqueue(new Callback<WalletAuthentication>() {
            @Override
            public void onResponse(@NotNull Call<WalletAuthentication> call, @NotNull Response<WalletAuthentication> response) {
                if (response.code() == 200) {
                    try {
//                        Gson gson = new Gson();
//                        String user = gson.toJson(response.body().getData());
//                        JSONObject object = new JSONObject(user);
                        WalletAuthActivity.getLoginToken(rawpassword, email, phoneNumber, context);
                    } catch (Exception e) {
                        Log.e("response", response.toString());
                        e.printStackTrace();
                    } finally {

                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                    if (errorTextView != null) {
                        errorTextView.setText(response.body().getMessage());
                        errorTextView.setVisibility(View.VISIBLE);
                        errorTextView.requestFocus();
                    } else if (response.body() != null) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {

                        Log.e("response", response.toString());
                    }


                }


            }

            @Override
            public void onFailure(Call<WalletAuthentication> call, Throwable t) {
                Log.e("info2 : ", t.getMessage());
                dialog.dismiss();
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
        String phoneNumber = "+"+DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_PHONE_NUMBER, context);
        dialog.show();
        APIRequests apiRequests = APIClient.getWalletInstance();
        Call<WalletUserRegistration> call = apiRequests.create(firstname, lastname, email, rawPassword, phoneNumber, addressStreet, addressCityOrTown);
        call.enqueue(new Callback<WalletUserRegistration>() {
            @Override
            public void onResponse(Call<WalletUserRegistration> call, Response<WalletUserRegistration> response) {
                if (response.code() == 200) {
                    try {
                        //get user data
                        WalletUserRegistration.ResponseData userdata = response.body().getData();
                        Gson gson = new Gson();
                        String user = gson.toJson(userdata);
                        JSONObject object = new JSONObject(user);
                        Toast.makeText(context, "Successfully Logged in..", Toast.LENGTH_SHORT).show();

                         DashboardActivity.savePreferences(DashboardActivity.PREFERENCES_WALLET_USER_ID,object.getString("id"),context);
                        WalletAuthActivity.getLoginToken(rawPassword, email, null, context);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("WalletREgResponseError:", e.getMessage());
                    } finally {
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

            }


            @Override
            public void onFailure(Call<WalletUserRegistration> call, Throwable t) {
                Log.e("info : ", t.getMessage());
                dialog.dismiss();
            }

        });


    }


}
