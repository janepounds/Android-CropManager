package com.myfarmnow.myfarmcrop.DailogFragments.wallet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.models.wallet.WalletPurchase;
import com.myfarmnow.myfarmcrop.singletons.WalletSettingsSingleton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class PurchasePreview extends DialogFragment {
    ScrollView summaryScrollView;
    Button confirmBtn;
    LinearLayout error_message_layout, discount_layout;
    TextView datetimeTextView, totalTextView,mechantIdTextView,
            mechantNameTextView,errorTextView, discountTextView;

    String businessName ="";
    Context activity;
    public PurchasePreview(Context context){
        this.activity=context;

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view =inflater.inflate(R.layout.wallet_purchase_preview, null);

        builder.setView(view);

        initializeView(view);
        return builder.create();

    }
    public void initializeView(View view){

        totalTextView = view.findViewById(R.id.txt_view_crop_bill_preview_total);
        errorTextView = view.findViewById(R.id.Comfirmation_Error_textview);
        discountTextView= view.findViewById(R.id.txt_view_discount_preview_total);
        discount_layout= view.findViewById(R.id.discount_layount);
        error_message_layout= view.findViewById(R.id.linearlayount_error);
        datetimeTextView = view.findViewById(R.id.text_view_purchase_date_time);

        mechantNameTextView = view.findViewById(R.id.text_view_purchase_preview_name);
        mechantIdTextView = view.findViewById(R.id.text_view_purchase_preview_mechant_id);
        confirmBtn = view.findViewById(R.id.btn_purchase_preview_confirm);

        totalTextView.setText( NumberFormat.getInstance().format(WalletPurchase.getInstance().getAmount()));

        SimpleDateFormat localFormat = new SimpleDateFormat(WalletSettingsSingleton.getInstance().getDateFormat(), Locale.ENGLISH);
        localFormat.setTimeZone(TimeZone.getDefault());
        String currentDateandTime = null;
        SimpleDateFormat incomingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        incomingFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            currentDateandTime = localFormat.format(new Date());

        datetimeTextView.setText(currentDateandTime);

        mechantIdTextView.setText(WalletPurchase.getInstance().getMechantId());
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
        //confirmBtn.setVisibility(View.GONE);

        getMechantName();
        comfirmPayment();
    }

    public void getMechantName(){
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization","Bearer "+ WalletAuthActivity.WALLET_ACCESS_TOKEN);
        Log.d("URL", ApiPaths.MERCHANT_GET_INFO+WalletPurchase.getInstance().getMechantId());
        client.get(ApiPaths.MERCHANT_GET_INFO+WalletPurchase.getInstance().getMechantId(), params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;
            @Override
            public void onStart() {
                dialog = new ProgressDialog(activity);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    businessName = response.getString("businessName");
                    mechantNameTextView.setText(businessName);
                    confirmBtn.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                if(statusCode==412) {
                    String businessName = null;
                    try {
                        businessName = errorResponse.getString("message");
                        mechantNameTextView.setText(businessName);
                        errorTextView.setText(businessName);
                        errorTextView.setVisibility(View.VISIBLE);
                        // confirmBtn.setEnabled(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(statusCode==401){
                    WalletAuthActivity.startAuth(activity, true);
                }
                if (errorResponse != null) {
                    Log.e("info", String.valueOf(errorResponse));
                } else {
                    Log.e("info", "Something got very very wrong");
                }

                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, errorResponse);
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                mechantNameTextView.setText("Unknown Merchant");

                errorTextView.setText("Error while checking for mechant occured");
                errorTextView.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });
    }

    public void processPayment(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        client.addHeader("Authorization","Bearer "+ WalletAuthActivity.WALLET_ACCESS_TOKEN);

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("merchantId",WalletPurchase.getInstance().getMechantId());
            requestObject.put("amount",WalletPurchase.getInstance().getAmount());
            requestObject.put("coupon",WalletPurchase.getInstance().getCoupon());

            HttpEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
                ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }


            client.post(activity, ApiPaths.WALLET_PAY_MERCHANT,params,"application/json", new JsonHttpResponseHandler() {
                ProgressDialog dialog;
                @Override
                public void onStart() {
                    dialog = new ProgressDialog(activity);
                    dialog.setIndeterminate(true);
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //logic to save the updated fields
                    dialog.dismiss();
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.wallet_dialog_one_button);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(false);
                    TextView text = dialog.findViewById(R.id.dlg_one_button_tv_message);
                    text.setText("You have purchased Food worth UGX "+ NumberFormat.getInstance().format(WalletPurchase.getInstance().getAmount())+" from "+businessName);
                    TextView title = dialog.findViewById(R.id.dlg_one_button_tv_title);
                    title.setText("SUCCESS!");
                    Button dialogButton = (Button) dialog.findViewById(R.id.dlg_one_button_btn_ok);
                    dialogButton.setText("OK");
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent goToWallet = new Intent(activity, WalletHomeActivity.class);
                            startActivity(goToWallet);
                        }
                    });
                    dialog.show();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    try {
                        errorTextView.setText(errorResponse.getString("message"));
                        error_message_layout.setVisibility(View.VISIBLE);
                        errorTextView.setVisibility(View.VISIBLE);
                        if(errorResponse != null){
                            Log.e("BACKUP RESPONSE 1A"+statusCode,errorResponse.toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        errorTextView.setText("Error occured! Try again later");
                    }

                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1A: "+statusCode, errorResponse);
                    } else {
                        Log.e("info 1A: "+statusCode, "Something got very very wrong");
                    }
                    errorTextView.setText("Error occured! Try again later");
                    error_message_layout.setVisibility(View.VISIBLE);
                    errorTextView.setVisibility(View.VISIBLE);
                    dialog.dismiss();

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void comfirmPayment(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        client.addHeader("Authorization","Bearer "+ WalletAuthActivity.WALLET_ACCESS_TOKEN);

        JSONObject requestObject = new JSONObject();

        try {

            requestObject.put("merchantId",WalletPurchase.getInstance().getMechantId());
            requestObject.put("amount",WalletPurchase.getInstance().getAmount());
            requestObject.put("coupon",WalletPurchase.getInstance().getCoupon());
            Log.e("Coupon Error",WalletPurchase.getInstance().getCoupon());
            HttpEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
                ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }


            client.post(activity, ApiPaths.WALLET_CPMFIRM_PAY_MERCHANT,params,"application/json", new JsonHttpResponseHandler() {
                ProgressDialog dialog;
                @Override
                public void onStart() {
                    dialog = new ProgressDialog(activity);
                    dialog.setIndeterminate(true);
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //logic to save the updated fields
                    dialog.dismiss();
                    discount_layout.setVisibility(View.VISIBLE);
                    discountTextView.setVisibility(View.VISIBLE);
                    try {
                        discountTextView.setText("UGX "+response.getString("discount"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("Success message Failure",e.getMessage());
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                    try {
                        errorTextView.setText(errorResponse.getString("message"));
                        error_message_layout.setVisibility(View.VISIBLE);
                        errorTextView.setVisibility(View.VISIBLE);
                        if(errorResponse != null){
                            Log.e("BACKUP RESPONSE 1A"+statusCode,errorResponse.toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        errorTextView.setText("Error occured! Try again later");
                    }

                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1A: "+statusCode, errorResponse);
                    } else {
                        Log.e("info 1A: "+statusCode, "Something got very very wrong");
                    }
                    errorTextView.setText("Error occured! Try again later");
                    error_message_layout.setVisibility(View.VISIBLE);
                    errorTextView.setVisibility(View.VISIBLE);
                    dialog.dismiss();

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
