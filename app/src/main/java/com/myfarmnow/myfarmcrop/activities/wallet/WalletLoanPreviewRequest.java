package com.cabral.emaisha.wallet.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.cabral.emaisha.R;
import com.cabral.emaisha.wallet.models.ApiPaths;
import com.cabral.emaisha.wallet.models.LoanApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import cz.msebera.android.httpclient.Header;

public class WalletLoanPreviewRequest extends AppCompatActivity {
    String[] descriptionData = {"Amount", "Confirm","Photo", "Referees"};
    TextView errorMsgTxt, amountTxt, dueAmountTxt, dueDateTxt, interestRateTxt,durationTxt;
    Button nextStepBtn;
    LoanApplication loanApplication;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_loan_preview_request);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StateProgressBar stateProgressBar =  findViewById(R.id.loan_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);


        if(getIntent().hasExtra("loanApplication") ){
            loanApplication = (LoanApplication)getIntent().getSerializableExtra("loanApplication");
            loanApplication.setInterestRate( (float) getIntent().getFloatExtra("interest", 0) );
        }
        else{
            finish();
        }
        initializeActivity();

    }



    public void initializeActivity(){
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        nextStepBtn = findViewById(R.id.btn_loan_next_step);
        amountTxt = findViewById(R.id.text_view_loan_preview_amount);
        dueAmountTxt = findViewById(R.id.text_view_loan_preview_due_amount);
        dueDateTxt = findViewById(R.id.text_view_loan_preview_due_date);
        interestRateTxt = findViewById(R.id.text_view_loan_preview_interest_rate);
        durationTxt = findViewById(R.id.text_view_loan_preview_duration);
        errorMsgTxt = findViewById(R.id.text_view_error_message);

        durationTxt.setText(loanApplication.getDuration()+" "+loanApplication.getDurationLabel());
        dueDateTxt.setText(loanApplication.computeDueDate());
        amountTxt.setText("UGX "+ NumberFormat.getInstance().format(loanApplication.getAmount()));
        interestRateTxt.setText( NumberFormat.getInstance().format(loanApplication.getInterestRate())+"%");
        dueAmountTxt.setText("UGX "+ NumberFormat.getInstance().format(loanApplication.computeDueAmount()) );
        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateApplication();
            }
        });
    }

    public void initiateApplication(){
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization","Bearer "+ WalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("userId", WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_ID,this));
        params.put("amount",loanApplication.getAmount());
        params.put("duration",loanApplication.getDuration());
        params.put("loanType",loanApplication.getLoanType());
        params.put("interest",loanApplication.getInterestRate());
        client.setTimeout(20000);
        client.post(ApiPaths.WALLET_LOAN_APPLICATION_INITIATE, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray

                Intent startNext = new Intent(WalletLoanPreviewRequest.this,WalletLoanAppPhotos.class);

                try {
                    startNext.putExtra("loanApplicationId",response.getString("loanApplicationId"));
                    startActivity(startNext);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(statusCode==401){
                    WalletAuthActivity.startAuth(WalletLoanPreviewRequest.this, true);
                }else if(statusCode == 500){
                    errorMsgTxt.setText("Error Occurred Try again later");
                    Log.e("info 500", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                }
                else if(statusCode == 400){
                    try {
                        errorMsgTxt.setText(errorResponse.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("info 500", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                }
                else if(statusCode == 406){
                    try {
                        errorMsgTxt.setText(errorResponse.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("info 406", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                }
                else {
                    errorMsgTxt.setText("Error Occurred Try again later");
                    if (errorResponse != null) {
                        Log.e("info", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                    } else {
                        Log.e("info", "Something got very very wrong, code: "+statusCode);
                    }
                }
                errorMsgTxt.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                errorMsgTxt.setText("Error Occurred Try again later");
                errorMsgTxt.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }


        });
    }

}
