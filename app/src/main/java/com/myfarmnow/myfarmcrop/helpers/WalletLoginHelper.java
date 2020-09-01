package com.myfarmnow.myfarmcrop.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.fragments.wallet.WalletHomeFragment;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WalletLoginHelper {


    public static void checkLogin(final String rawpassword, final Context context, final TextView errorTextView, final ProgressDialog dialog) {

        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        dialog.show();
        final String email = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, context);
        final String phoneNumber = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_PHONE_NUMBER, context);

        params.put("email", email);
        params.put("password", rawpassword);
        params.put("phoneNumber", phoneNumber);

        client.post(ApiPaths.Emaisha_Wallet_LOGIN_GET_ALL, params, new JsonHttpResponseHandler() {


            @Override
            public void onStart() {
                Log.e("Info ", ApiPaths.Emaisha_Wallet_LOGIN_GET_ALL + "?" + params);
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
                        WalletHomeActivity.saveUser(user, context);
                        ((AppCompatActivity) context).finish();
                        dialog.dismiss();
                        WalletAuthActivity.getLoginToken(rawpassword, email, phoneNumber, context);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    if (errorResponse != null) {
                        Log.e("info1 " + statusCode, String.valueOf(errorResponse));
                    } else {
                        Log.e("info1", "Something got very  wrong 0001");
                    }

                    if (statusCode == 403) {
                        //Toast.makeText(context, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                        if (errorTextView != null) {
                            errorTextView.setText(errorResponse.getString("message"));
                            errorTextView.setVisibility(View.VISIBLE);
                            errorTextView.requestFocus();
                        }


                    } else if (statusCode == 404) {

                        WalletLoginHelper.userRegister(dialog, context, rawpassword);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Exception : ", e.getMessage());
                }

                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info2 : " + statusCode, errorResponse);
                } else {
                    Log.e("info2 : " + statusCode, "Something got very very wrong");
                }
                //dialog.dismiss();
            }
        });
    }

    public static void userRegister(final ProgressDialog dialog, final Context context, final String rawPassword) {
        AsyncHttpClient client = new AsyncHttpClient();

        final RequestParams params = new RequestParams();

        params.put("firstname", "" + DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_FIRST_NAME, context));
        params.put("lastname", "" + DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_LAST_NAME, context));
        params.put("addressStreet", "" + DashboardActivity.getPreferences(DashboardActivity.STREET_PREFERENCES_ID, context));
        params.put("addressCityOrTown", "" + DashboardActivity.getPreferences(DashboardActivity.CITY_PREFERENCES_ID, context));
        params.put("email", "" + DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, context));
        params.put("password", "" + rawPassword);
        params.put("phoneNumber", DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_PHONE_NUMBER, context));
        String email = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, context);


        client.post(ApiPaths.Wallet_CREATE_USER, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                dialog.show();
                Log.e("response", ApiPaths.Wallet_CREATE_USER + params);
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONObject user = response.getJSONObject("user");
                    Toast.makeText(context, "Successfully Logged in..", Toast.LENGTH_SHORT).show();
                    Log.e("response", response.toString());
                    WalletHomeActivity.saveUser(user, context);

                    dialog.dismiss();

                    WalletAuthActivity.getLoginToken(rawPassword, email, null, context);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error:", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    Log.e("info response", String.valueOf(errorResponse));

                    try {
                        if (statusCode == 403) {
                            Toast.makeText(context, errorResponse.getString("message"), Toast.LENGTH_LONG).show();

                        } else if (statusCode == 400 && (errorResponse.getJSONObject("errors").getString("email").equals("[\"The email has already been taken.\"]") || errorResponse.getJSONObject("errors").getString("phoneNumber").equals("[\"The phone number has already been taken.\"]"))) {//user has account
                            Log.w("Account Exists", "Attempting  User Login");
                            WalletLoginHelper.checkLogin(rawPassword, context, null, dialog);

                        } else {

                            Toast.makeText(context, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("info response", e.getMessage());
                    }


                } else {
                    Log.e("info", "Something got very very wrong");
                }


                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : " + statusCode, errorResponse);
                } else {
                    Log.e("info : " + statusCode, "Something got very very wrong");
                }
                dialog.dismiss();
            }
        });
    }


}
