package com.myfarmnow.myfarmcrop.activities.wallet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.wallet.LoansListAdapter;
import com.myfarmnow.myfarmcrop.fragments.wallet.WalletHomeFragment;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.PayLoan;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class WalletLoansListActivity extends AppCompatActivity {
    RecyclerView statementRecyclerView;
    LoansListAdapter statementAdapter;
    RecyclerView.LayoutManager layoutManager;
    ActionBar actionBar;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    private List<LoanApplication> dataList = new ArrayList<>();
    private Button payloanBtn, applyloanBtn;
    private  float interest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_loans_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.title_activity_wallet_loans_list));

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        statementRecyclerView = findViewById(R.id.loans_list_recycler_view);
        applyloanBtn= findViewById(R.id.wallet_apply_loan_btn);
        payloanBtn= findViewById(R.id.wallet_pay_loan_btn);
        payloanBtn.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(this);
        statementRecyclerView.setLayoutManager(layoutManager);


        statementAdapter = new LoansListAdapter(dataList);
        statementRecyclerView.setAdapter(statementAdapter);

        actualStatementData();
    }

    private void actualStatementData() {

        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization","Bearer "+ WalletAuthActivity.WALLET_ACCESS_TOKEN);

        params.put("userId", WalletHomeFragment.getPreferences(WalletHomeFragment.PREFERENCES_USER_ID,this));
        client.get(ApiPaths.WALLET_LOANS_LIST, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;
            @Override
            public void onStart() {
                dialog = new ProgressDialog(WalletLoansListActivity.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    LoanApplication data=null;
                    JSONArray transactions = response.getJSONArray("loans");
                     interest = (float) response.getDouble("Interest");
                    for (int i=0; i<transactions.length();i++){
                        JSONObject record =transactions.getJSONObject(i);
                        data = new LoanApplication(record);
                        dataList.add(data);
                        if(data.getStatus().equals("Approved") || data.getStatus().equals("Partially Paid")){
                            applyloanBtn.setVisibility(View.GONE);
                            payloanBtn.setVisibility(View.VISIBLE);
                        }

                    }
                    statementAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("info : ",e.getMessage());
                }


                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if(statusCode==401){
                    WalletAuthActivity.startAuth(WalletLoansListActivity.this, true);
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
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                dialog.dismiss();
                WalletAuthActivity.startAuth(WalletLoansListActivity.this, true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent whatEverIntentName = new Intent(this, WalletHomeActivity.class);
        startActivity( whatEverIntentName );

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here


                startActivity(new Intent(WalletLoansListActivity.this, WalletHomeActivity.class));
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void newLoanApplication(View v){
        Intent openW = new Intent(this, WalletLoanAppInitiateActivity.class);
        openW.putExtra("interest",interest);
        //interest
        startActivity(openW);
    }

    public void  payLoan(View v){
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev =fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment payLoandialog =new PayLoan(WalletLoansListActivity.this, fm);
        payLoandialog.show( ft, "dialog");
    }

}
