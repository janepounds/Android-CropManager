package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;

import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletHomeBinding;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.Buy;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.DepositMoneyMobile;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.DepositMoneyVisa;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.DepositMoneyVoucher;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.DepositPayments;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.TransferMoney;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class WalletHomeFragment extends Fragment {
    private static final String TAG = "WalletHomeFragment";
    private Context context;
    private FragmentWalletHomeBinding binding;
    private NavController navController = null;
    AppBarConfiguration appBarConfiguration;


    private TextView balanceTextView;
    static TabLayout tabs;
    public static double balance = 0;
    ActionBar actionBar;
    public static FragmentManager fm;

    private DisplayImageOptions options;
    //Imageucf
    private ProgressDialog dialog;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet_home, container, false);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getResources().getString(R.string.my_wallet));

        fm = getActivity().getSupportFragmentManager();

        binding.walletBalance.setText("UGX " + NumberFormat.getInstance().format(balance));

        updateBalance();

        //TextView walletUsername = view.findViewById(R.id.wallet_username);
        binding.usernameWalletHome.setText(ucf(WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_LAST_NAME, context)) + " " + ucf(WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_FIRST_NAME, context)));

        Log.d(TAG, "onCreateView: Name = " + ucf(WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_LAST_NAME, context)) + " " + ucf(WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_FIRST_NAME, context)));

        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);

        // return view;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

        binding.btnWalletDeposit.setOnClickListener(view19 -> openAddMoney());
        binding.layoutWalletTransfer.setOnClickListener(view110 -> openTransfer());
        binding.layoutWalletTransactions.setOnClickListener(view111 -> navController.navigate(R.id.action_walletHomeFragment_to_walletTransactionsListFragment));
        binding.layoutWalletLoans.setOnClickListener(view112 -> navController.navigate(R.id.action_walletHomeFragment_to_walletLoansListFragment));
        binding.layoutWalletCoupons.setOnClickListener(view18 -> comingSoon());
        binding.layoutWalletBuy.setOnClickListener(view17 -> openBuy());
        binding.btnWalletDeposit.setOnClickListener(view16 -> openAddMoney());
        binding.btnWalletTransfer.setOnClickListener(view15 -> openTransfer());
        binding.btnWalletTransactions.setOnClickListener(view14 -> navController.navigate(R.id.action_walletHomeFragment_to_walletTransactionsListFragment));
        binding.btnWalletLoans.setOnClickListener(view13 -> navController.navigate(R.id.action_walletHomeFragment_to_walletLoansListFragment));
        binding.btnWalletCoupons.setOnClickListener(view12 -> comingSoon());
        binding.btnWalletBuy.setOnClickListener(view1 -> openBuy());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private String ucf(String str) {
        if (str == null || str.length() < 2)
            return str;

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public void openAddMoney() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment depositDialog = new DepositPayments(context, balance);
        depositDialog.show(ft, "dialog");
    }

    public void openAddMobileMoney(View view) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment depositDailog = new DepositMoneyMobile(context, balance);
        depositDailog.show(ft, "dialog");
    }

    public void openAddMoneyVisa(View view) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment depositDailog = new DepositMoneyVisa(context, balance, getActivity().getSupportFragmentManager());
        depositDailog.show(ft, "dialog");
    }

    public void openAddMoneyVoucher(View view) {
        FragmentTransaction ft = this.fm.beginTransaction();
        Fragment prev = this.fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment depositDailog = new DepositMoneyVoucher(context, balance);
        depositDailog.show(ft, "dialog");
    }

    public void openTransfer() {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment transferDailog = new TransferMoney(context, balance, getActivity().getSupportFragmentManager());
        transferDailog.show(ft, "dialog");
    }

    public void updateBalance() {
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        Log.w("Token", WalletAuthActivity.WALLET_ACCESS_TOKEN);
        client.addHeader("Authorization", "Bearer " + WalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("userId", WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_ID, this.context));
        client.get(ApiPaths.WALLET_GET_BALANCE, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
                dialog = new ProgressDialog(context);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(true);
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    balance = response.getDouble("balance");

                    Log.d(TAG, "onSuccess: Balance = " + balance);

                    binding.walletBalance.setText("UGX " + NumberFormat.getInstance().format(balance));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                if (statusCode == 401) {
                    Toast.makeText(context, "Session Expired", Toast.LENGTH_LONG).show();
                    // WalletAuthActivity.startAuth(context, true);

                }
                if (errorResponse != null) {
                    Log.e("info", new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info", "Something got very very wrong");
                }

//                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : " + statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : " + statusCode, "Something got very very wrong");
                }
                Toast.makeText(context, "An error occurred Try again Later", Toast.LENGTH_LONG).show();
                WalletAuthActivity.startAuth(context, false);
            }
        });
    }


    public void openBuy() {
        FragmentTransaction ft = this.fm.beginTransaction();
        Fragment prev = this.fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment buyfoodDailog = new Buy(context, getActivity().getSupportFragmentManager());
        buyfoodDailog.show(ft, "dialog");
    }

    public void comingSoon() {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setIndeterminate(false);
        dialog.setMessage("Coming Soon ..!!");
        dialog.setCancelable(true);
        dialog.show();
    }
}