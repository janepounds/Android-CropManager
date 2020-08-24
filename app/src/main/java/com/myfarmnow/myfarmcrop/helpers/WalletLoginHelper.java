package com.cabral.emaisha.wallet.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cabral.emaisha.models.user_model.UserDetails;
import com.cabral.emaisha.wallet.activities.WalletAuthActivity;
import com.cabral.emaisha.wallet.activities.WalletHomeActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.cabral.emaisha.wallet.models.ApiPaths;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WalletLoginHelper {


    public static void checkLogin(final UserDetails userInfo,final String rawpassword, final Context context, final TextView errorTextView, final ProgressDialog dialog) {

        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        dialog.show();
        final String email=userInfo.getEmail();
        final String phoneNumber=userInfo.getPhone();

        params.put("email", email);
        params.put("password", rawpassword);
        params.put("phoneNumber", phoneNumber);

        client.post(ApiPaths.Emaisha_Wallet_LOGIN_GET_ALL, params, new JsonHttpResponseHandler() {


            @Override
            public void onStart() {
                Log.e("Info ",ApiPaths.Emaisha_Wallet_LOGIN_GET_ALL+"?"+params);
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
                             WalletHomeActivity.saveUser(user,context);
                           ((AppCompatActivity)context).finish();
                        dialog.dismiss();
                        WalletAuthActivity.getLoginToken(userInfo, email, phoneNumber, rawpassword,errorTextView,context);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try{
                    if (errorResponse != null) {
                        Log.e("info1 "+statusCode, String.valueOf(errorResponse) );
                    } else {
                        Log.e("info1", "Something got very  wrong 0001");
                    }

                    if(statusCode==403){
                        //Toast.makeText(context, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                        if(errorTextView!=null){
                            errorTextView.setText(errorResponse.getString("message"));
                            errorTextView.setVisibility(View.VISIBLE);
                            errorTextView.requestFocus();
                        }


                    }else if(statusCode==404){

                        WalletLoginHelper.userRegister( dialog, context,userInfo,rawpassword);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Exception : ",e.getMessage());
                }

                try{
                    dialog.dismiss();
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info2 : "+statusCode, errorResponse);
                } else {
                    Log.e("info2 : "+statusCode, "Something got very very wrong");
                }
                //dialog.dismiss();
            }
        });
    }

    public static void userRegister(final ProgressDialog dialog, final Context context, final UserDetails userInfo, final String rawPassword) {
        AsyncHttpClient client = new AsyncHttpClient();

        final RequestParams params = new RequestParams();

        params.put("firstname", "" +  userInfo.getFirstName());
        params.put("lastname", "" + userInfo.getLastName());
        params.put("addressStreet", "" + userInfo.getDefaultAddressId());
        params.put("addressCityOrTown", "" + userInfo.getDefaultAddressId());
        params.put("email", "" + userInfo.getEmail());
        params.put("password", "" + userInfo.getPassword());
        params.put("phoneNumber", userInfo.getPhone());

        client.post(ApiPaths.Wallet_CREATE_USER, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                dialog.show();
                Log.e("response", ApiPaths.Wallet_CREATE_USER+params);
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONObject user = response.getJSONObject("user");
                    Toast.makeText(context, "Successfully Logged in..", Toast.LENGTH_SHORT).show();
                    Log.e("response", response.toString());
                    WalletHomeActivity.saveUser(user,context);

                    dialog.dismiss();

                    WalletAuthActivity.getLoginToken(userInfo, userInfo.getEmail(), userInfo.getPhone(), rawPassword,null,context);

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
                        if(statusCode==403){
                            Toast.makeText(context, errorResponse.getString("message"), Toast.LENGTH_LONG).show();

                        }else if(statusCode == 400 &&  ( errorResponse.getJSONObject("errors").getString("email").equals("[\"The email has already been taken.\"]") || errorResponse.getJSONObject("errors").getString("phoneNumber").equals("[\"The phone number has already been taken.\"]") ) ){//user has account
                            Log.w("Account Exists", "Attempting  User Login" );
                            WalletLoginHelper.checkLogin(userInfo, rawPassword, context, null, dialog);

                        }else{

                            Toast.makeText(context, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("info response", e.getMessage() );
                    }


                } else {
                    Log.e("info", "Something got very very wrong");
                }


                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, errorResponse);
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                dialog.dismiss();
            }
        });
    }



}
