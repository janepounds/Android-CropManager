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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.InitiateTransferResponse;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;
import com.myfarmnow.myfarmcrop.singletons.WalletSettingsSingleton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmTransfer extends DialogFragment {

    Button confirmBtn;
    TextView serviceTextView, datetimeTextView, totalTextView, receiverPhoneNumber,
            receiverNameTextView, errorTextView, transactionLabelTextView, receiverLabel;
    String phoneNumber, businessName = "";
    Context activity;
    float amount;

    public ConfirmTransfer(Context context, String phoneNumber, float amount) {
        this.activity = context;
        this.phoneNumber = phoneNumber;
        this.amount = amount;

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
        View view = inflater.inflate(R.layout.wallet_purchase_preview, null);

        builder.setView(view);

        initializeView(view);
        return builder.create();

    }

    public void initializeView(View view) {

        totalTextView = view.findViewById(R.id.txt_view_crop_bill_preview_total);
        errorTextView = view.findViewById(R.id.text_view_purchase_preview_error);
        datetimeTextView = view.findViewById(R.id.text_view_purchase_date_time);
        transactionLabelTextView = view.findViewById(R.id.transaction_receiver_label);
        transactionLabelTextView.setText("Transferring to");

        receiverLabel = view.findViewById(R.id.txt_wallet_purchase_mechant_label);
        receiverLabel.setText("Receiver's Phone Number");

        receiverNameTextView = view.findViewById(R.id.text_view_purchase_preview_name);
        receiverPhoneNumber = view.findViewById(R.id.text_view_purchase_preview_mechant_id);
        confirmBtn = view.findViewById(R.id.btn_purchase_preview_confirm);
        confirmBtn.setText("Confirm Transfer");
        serviceTextView = view.findViewById(R.id.text_view_purchase_service);
        serviceTextView.setText("Money Transfer");

        totalTextView.setText(NumberFormat.getInstance().format(this.amount));

        SimpleDateFormat localFormat = new SimpleDateFormat(WalletSettingsSingleton.getInstance().getDateFormat(), Locale.ENGLISH);
        localFormat.setTimeZone(TimeZone.getDefault());
        String currentDateandTime = null;
        SimpleDateFormat incomingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        incomingFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        currentDateandTime = localFormat.format(new Date());

        datetimeTextView.setText(currentDateandTime);

        receiverPhoneNumber.setText(this.phoneNumber);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateDeposit(phoneNumber, amount);
            }
        });

        getReceiverName();
    }

    public void getReceiverName() {
        /*************RETROFIT IMPLEMENTATION*******************/
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization", "Bearer " + WalletAuthActivity.WALLET_ACCESS_TOKEN);
        Log.e("URL", ApiPaths.USER_GET_INFO_BY_PHONE + this.phoneNumber);

        client.get(ApiPaths.USER_GET_INFO_BY_PHONE + this.phoneNumber, params, new JsonHttpResponseHandler() {
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
                    receiverNameTextView.setText(businessName);
                    confirmBtn.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error", e.getMessage());
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                if (statusCode == 412) {
                    String businessName = null;
                    try {
                        businessName = errorResponse.getString("message");
                        receiverNameTextView.setText(businessName);
                        errorTextView.setText(businessName);
                        errorTextView.setVisibility(View.VISIBLE);
                        // confirmBtn.setEnabled(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (statusCode == 401) {
                    WalletAuthActivity.startAuth(activity, true);
                }
                if (errorResponse != null) {
                    Log.e("info " + statusCode, String.valueOf(errorResponse));
                } else {
                    Log.e("info", "Something got very very wrong");
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info2 : " + statusCode, errorResponse);
                } else {
                    Log.e("info2 : " + statusCode, "Something got very very wrong");
                }
                receiverNameTextView.setText("Unknown Merchant");

                errorTextView.setText("Error while checking for merchant occurred");
                errorTextView.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });
    }

    public void initiateDeposit(final String phoneNumber, final float amount) {
        ProgressDialog dialog;
        String countryCode = "+256";
        String access_token =WalletAuthActivity.WALLET_ACCESS_TOKEN;
        Toast.makeText(activity, phoneNumber, Toast.LENGTH_LONG).show();
        dialog = new ProgressDialog(activity);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();
        /*****RETROFIT IMPLEMENTATION*****/
        APIRequests apiRequests = APIClient.getWalletInstance();
        Call<InitiateTransferResponse> call = apiRequests.initiateTransfer(access_token,Double.parseDouble(new Float(amount).toString()),phoneNumber);
        call.enqueue(new Callback<InitiateTransferResponse>() {
            @Override
            public void onResponse(Call<InitiateTransferResponse> call, Response<InitiateTransferResponse> response) {
                if(response.code() ==200){
                    dialog.dismiss();
                    final Dialog dialog = new Dialog(activity);
                    dialog.setContentView(R.layout.wallet_dialog_one_button);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(false);
                    TextView text = dialog.findViewById(R.id.dlg_one_button_tv_message);
                    text.setText("You have transferred UGX " + NumberFormat.getInstance().format(amount) + " to " + businessName);
                    TextView title = dialog.findViewById(R.id.dlg_one_button_tv_title);
                    title.setText("SUCCESS!");
                    Button dialogButton = (Button) dialog.findViewById(R.id.dlg_one_button_btn_ok);
                    dialogButton.setText("OK");
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            //finish();
                            Intent goToWallet = new Intent(activity, WalletHomeActivity.class);
                            startActivity(goToWallet);
                        }
                    });
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();

                    //0703993246
                }else if(response.code() == 401) {
                    WalletAuthActivity.startAuth(activity, true);
                }
                    else if (response.code() == 500) {
                        errorTextView.setText("Error Occurred Try again later");
                        Log.e("info 500", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                    } else if (response.code() == 400) {
                        errorTextView.setText("Check your input details");
                        Log.e("info 500", new String(String.valueOf(response.errorBody()) + ", code: " + response.code()));
                    } else if (response.code() == 406) {


                            Log.d("reespnse message",response.message());
//                            errorTextView.setText(message);

                        Log.e("info 406", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                    } else {
                        errorTextView.setText("Error Occurred Try again later");

                            Log.e("info", "Something got very very wrong, code: " + response.code());

                    }
                    errorTextView.setVisibility(View.VISIBLE);
                    dialog.dismiss();


            }

            @Override
            public void onFailure(Call<InitiateTransferResponse> call, Throwable t) {
                Log.e("info : " , new String(String.valueOf(t.getMessage())));
                errorTextView.setText("Error Occurred Try again later");
                errorTextView.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });
//phone number = +256780598708


    }
}
