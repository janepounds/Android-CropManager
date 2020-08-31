package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.adapters.wallet.LoansListAdapter;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletLoansListBinding;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.PayLoan;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class WalletLoansListFragment extends Fragment {
    private static final String TAG = "WalletLoansListFragment";
    private Context context;

    private FragmentWalletLoansListBinding binding;
    AppBarConfiguration appBarConfiguration;

    LoansListAdapter statementAdapter;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    private List<LoanApplication> dataList = new ArrayList<>();
    private float interest;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet_loans_list, container, false);

        // binding.walletPayLoanBtn.setVisibility(View.GONE);
        binding.loansListRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        statementAdapter = new LoansListAdapter(dataList);
        binding.loansListRecyclerView.setAdapter(statementAdapter);

        actualStatementData();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

        Bundle bundle = new Bundle();
        bundle.putFloat("interest", interest);
        binding.walletApplyLoanBtn.setOnClickListener(view1 -> navController.navigate(R.id.action_walletLoansListFragment_to_walletLoanAppInitiateFragment, bundle));
        binding.walletPayLoanBtn.setOnClickListener(view2 -> payLoan());
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
                            binding.walletApplyLoanBtn.setVisibility(View.GONE);
                            binding.walletPayLoanBtn.setVisibility(View.VISIBLE);
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

    public void payLoan() {
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