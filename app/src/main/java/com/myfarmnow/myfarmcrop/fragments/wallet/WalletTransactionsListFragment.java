package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.adapters.wallet.WalletTransactionsListAdapter;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletLoansListBinding;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletTransactionsListBinding;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.LoanListResponse;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletTransactionResponse;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.models.wallet.WalletTransaction;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletTransactionsListFragment extends Fragment {
    private static final String TAG = "WalletTransactionsList";
    private Context context;

    private FragmentWalletTransactionsListBinding binding;
    AppBarConfiguration appBarConfiguration;

    RecyclerView.Adapter statementAdapter;
    ActionBar actionBar;
    private List<WalletTransaction> dataList = new ArrayList<>();

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet_transactions_list, container, false);

        binding.statementRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        statementAdapter = new WalletTransactionsListAdapter(dataList, requireActivity().getSupportFragmentManager());
        binding.statementRecyclerView.setAdapter(statementAdapter);

        actualStatementData();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void actualStatementData() {
        ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();
        String access_token = WalletAuthActivity.WALLET_ACCESS_TOKEN;

        /**********RETROFIT IMPLEMENTATION************/
        APIRequests apiRequests = APIClient.getWalletInstance();
        Call<WalletTransactionResponse> call  = apiRequests.transactionList(access_token);
        call.enqueue(new Callback<WalletTransactionResponse>() {
            @Override
            public void onResponse(Call<WalletTransactionResponse> call, Response<WalletTransactionResponse> response) {
                if(response.code()== 200){
                        try {
                            WalletTransaction data = null;
                            WalletTransactionResponse.TransactionData walletTransactionResponseData = response.body().getData();
                            List<WalletTransactionResponse.TransactionData.Transactions> transactions = walletTransactionResponseData.getTransactions();
                            for (int i = 0; i < transactions.size(); i++) {
                                WalletTransactionResponse.TransactionData.Transactions res = transactions.get(i);
                                Gson gson = new Gson();
                                String ress = gson.toJson(res);
                                JSONObject record = new JSONObject(ress);
                                //type
                                if (record.getString("type").equals("Charge")) {
                                    data = new WalletTransaction(record.getString("date"), record.getString("receiver"), "debit", record.getDouble("amount"), record.getString("referenceNumber"));
                                } else if (record.getString("type").equals("FoodPurchase")) {
                                    data = new WalletTransaction(record.getString("date"), record.getString("receiver"), "debit", record.getDouble("amount"), record.getString("referenceNumber"));
                                    data.setIsPurchase(true);
                                } else if (record.getString("type").equals("Deposit")) {
                                    data = new WalletTransaction(record.getString("date"), record.getString("receiver"), "credit", record.getDouble("amount"), record.getString("referenceNumber"));
                                } else if (record.getString("type").equals("Transfer")) {
                                    String userName = WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_FIRST_NAME, context) + " " + WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_LAST_NAME, context);

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
                }else if(response.code() == 401){

                        WalletAuthActivity.startAuth(context, true);
                    if (response.errorBody() != null) {
                        Log.e("info", new String(String.valueOf(response.errorBody())));
                    } else {
                        Log.e("info", "Something got very very wrong");
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<WalletTransactionResponse> call, Throwable t) {

            }
        });


    }
}