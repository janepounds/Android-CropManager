package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.TransactionsList;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.adapters.wallet.WalletTransactionsListAdapter;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.models.wallet.WalletTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;

public class TransactionsListFragment extends Fragment {
    private static final String TAG = "TransactionsListFragment";
    private Context appContext;

    RecyclerView statementRecyclerView;
    RecyclerView.Adapter statementAdapter;
    RecyclerView.LayoutManager layoutManager;
    ActionBar actionBar;
    private List<WalletTransaction> dataList = new ArrayList<>();

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_statement, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getString(R.string.title_activity_transactions));

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        statementRecyclerView = view.findViewById(R.id.statement_recycler_view);
        layoutManager = new LinearLayoutManager(appContext);
        statementRecyclerView.setLayoutManager(layoutManager);

        statementAdapter = new WalletTransactionsListAdapter(dataList, getActivity().getSupportFragmentManager());
        statementRecyclerView.setAdapter(statementAdapter);

        actualStatementData();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appContext = context;
    }

    private void actualStatementData() {
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization", "Bearer " + WalletAuthActivity.WALLET_ACCESS_TOKEN);

        client.get(ApiPaths.WALLET_TRANSACTIONS_LIST, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
                dialog = new ProgressDialog(appContext);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    WalletTransaction data = null;
                    JSONArray transactions = response.getJSONArray("transactions");
                    for (int i = 0; i < transactions.length(); i++) {
                        JSONObject record = transactions.getJSONObject(i);
                        //type
                        if (record.getString("type").equals("Charge")) {
                            data = new WalletTransaction(record.getString("date"), record.getString("receiver"), "debit", record.getDouble("amount"), record.getString("referenceNumber"));
                        } else if (record.getString("type").equals("FoodPurchase")) {
                            data = new WalletTransaction(record.getString("date"), record.getString("receiver"), "debit", record.getDouble("amount"), record.getString("referenceNumber"));
                            data.setIsPurchase(true);
                        } else if (record.getString("type").equals("Deposit")) {
                            data = new WalletTransaction(record.getString("date"), record.getString("receiver"), "credit", record.getDouble("amount"), record.getString("referenceNumber"));
                        } else if (record.getString("type").equals("Transfer")) {
                            String userName = WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_FIRST_NAME, appContext) + " " + WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_LAST_NAME, appContext);

                            if (userName.equals(record.getString("sender"))) {
                                data = new WalletTransaction(record.getString("date"), record.getString("receiver"), "debit", record.getDouble("amount"), record.getString("referenceNumber"));
                            } else {
                                data = new WalletTransaction(record.getString("date"), record.getString("sender"), "credit", record.getDouble("amount"), record.getString("referenceNumber"));
                            }
                        } else if (record.getString("type").equals("Withdraw")) {
                            data = new WalletTransaction(record.getString("date"), record.getString("receiver"), "debit", record.getDouble("amount"), record.getString("referenceNumber"));
                        }
                        if (data != null) {
                            dataList.add(data);
                        }
                    }
                    statementAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == 401) {
                    WalletAuthActivity.startAuth(appContext, true);
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

                WalletAuthActivity.startAuth(appContext, true);
            }
        });

    }
}
