package com.myfarmnow.myfarmcrop.DailogFragments.wallet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.fragments.wallet.WalletLoansListFragment;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.LoanPayResponse;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;

import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PayLoan extends DialogFragment {

    EditText totalAmountTxt;
    TextView errorTextView;
    Button saveBtn;
    Context activity;
    FragmentManager fm;

    public PayLoan(Context context, FragmentManager supportFragmentManager){
        this.activity=context;
        this.fm=supportFragmentManager;
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
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view =inflater.inflate(R.layout.wallet_pay_loan, null);

        builder.setView(view);

        initializeForm( view);
        return builder.create();

    }

    public void initializeForm(View view) {

        totalAmountTxt = view.findViewById(R.id.txt_loan_bill_total);
        errorTextView = view.findViewById(R.id.error_message_txt);
        saveBtn = view.findViewById(R.id.btn_pay);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    public void processPayment(){
        ProgressDialog dialog;
        dialog = new ProgressDialog(activity);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();

            float amount = Float.parseFloat(totalAmountTxt.getText().toString());

            String access_token =WalletAuthActivity.WALLET_ACCESS_TOKEN;
            String userId = DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_WALLET_USER_ID, activity);
            

            /*********RETROFIT IMPLEMENTATION*************/
            APIRequests apiRequests = APIClient.getWalletInstance();
            Call<LoanPayResponse> call = apiRequests.loanPay(access_token,amount,userId);
            call.enqueue(new Callback<LoanPayResponse>() {
                @Override
                public void onResponse(Call<LoanPayResponse> call, Response<LoanPayResponse> response) {
                    if(response.code()== 200){
                        dialog.dismiss();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        Fragment fragment = new WalletLoansListFragment();

                        fragmentManager.beginTransaction()
                                .add(R.id.mainView, fragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                    }else if(response.errorBody()!=null){

                            errorTextView.setText("Error occured! Try again later");
                        }
                        else{
                            errorTextView.setText(response.body().getMessage());}
                            errorTextView.setVisibility(View.VISIBLE);
                             dialog.dismiss();


                }

                @Override
                public void onFailure(Call<LoanPayResponse> call, Throwable t) {
                    Log.e("info 1A: ", new String(String.valueOf(t.getMessage())));

                    Log.e("info 1A: ", "Something got very wrong");
                }
            });



    }


}