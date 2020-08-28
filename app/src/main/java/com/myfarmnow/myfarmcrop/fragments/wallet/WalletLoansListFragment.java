package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletLoanAppInitiateActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletLoansListActivity;
import com.myfarmnow.myfarmcrop.adapters.wallet.LoansListAdapter;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.PayLoan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class WalletLoansListFragment extends Fragment {
    private static final String TAG = "WalletLoansListFragment";
    private Context context;

    RecyclerView statementRecyclerView;
    LoansListAdapter statementAdapter;
    RecyclerView.LayoutManager layoutManager;
    ActionBar actionBar;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    private List<LoanApplication> dataList = new ArrayList<>();
    private Button payLoanBtn, applyLoanBtn;
    private float interest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_loans_list, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        Objects.requireNonNull(actionBar).setTitle(getString(R.string.title_activity_wallet_loans_list));

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        statementRecyclerView = view.findViewById(R.id.loans_list_recycler_view);
        applyLoanBtn = view.findViewById(R.id.wallet_appyly_loan_btn);
        payLoanBtn = view.findViewById(R.id.wallet_pay_loan_btn);
        payLoanBtn.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(context);
        statementRecyclerView.setLayoutManager(layoutManager);


        statementAdapter = new LoansListAdapter(dataList);
        statementRecyclerView.setAdapter(statementAdapter);

        actualStatementData();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void actualStatementData() {

        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization", "Bearer " + WalletAuthActivity.WALLET_ACCESS_TOKEN);

        params.put("userId", WalletHomeFragment.getPreferences(WalletHomeFragment.PREFERENCES_USER_ID, context));
        client.get(ApiPaths.WALLET_LOANS_LIST, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
                dialog = new ProgressDialog(context);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    LoanApplication data = null;
                    JSONArray transactions = response.getJSONArray("loans");
                    interest = (float) response.getDouble("Interest");
                    for (int i = 0; i < transactions.length(); i++) {
                        JSONObject record = transactions.getJSONObject(i);
                        data = new LoanApplication(record);
                        dataList.add(data);
                        if (data.getStatus().equals("Approved") || data.getStatus().equals("Partially Paid")) {
                            applyLoanBtn.setVisibility(View.GONE);
                            payLoanBtn.setVisibility(View.VISIBLE);
                        }

                    }
                    statementAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("info : ", e.getMessage());
                }


                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == 401) {
                    WalletAuthActivity.startAuth(context, true);
                }
                if (errorResponse != null) {
                    Log.e("info", new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info", "Something got very very wrong");
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : " + statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : " + statusCode, "Something got very very wrong");
                }
                dialog.dismiss();
                WalletAuthActivity.startAuth(context, true);
            }
        });
    }

    public void newLoanApplication(View v) {
        Intent openW = new Intent(context, WalletLoanAppInitiateActivity.class);
        openW.putExtra("interest", interest);
        //interest
        startActivity(openW);
    }

    public void payLoan(View v) {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment payLoandialog = new PayLoan(context, fm);
        payLoandialog.show(ft, "dialog");
    }
}