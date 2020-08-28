package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.models.ApiPaths;
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

import cz.msebera.android.httpclient.Header;

public class WalletHomeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "WalletHomeFragment";
    private Context context;

    private NavController navController = null;
    private LinearLayout walletTransactions, walletLoans, walletDeposit, walletTransfer, walletCoupons, walletBuy;
    private ImageButton btnWalletTransactions, btnWalletLoans, btnWalletDeposit, btnWalletTransfer, btnWalletCoupons, btnWalletBuy;

    public static final String PREFERENCES_FILE_NAME = "pref";
    public static final String STREET_PREFERENCES_ID = "addressStreet";
    public static final String CITY_PREFERENCES_ID = "addressCityOrTown";
    public static final String COUNTRY_PREFERENCES_ID = "addressCountry";
    public static final String PREFERENCES_FIRST_NAME = "firstname";
    public static final String PREFERENCES_LAST_NAME = "lastname";
    public static final String PREFERENCES_USER_ID = "userId";
    public static final String PREFERENCES_USER_EMAIL = "email";
    public static final String PREFERENCES_PHONE_NUMBER = "phoneNumber";

    public TextView balanceTextView;
    static TabLayout tabs;
    private Toolbar toolbar;
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
        View view = inflater.inflate(R.layout.fragment_wallet_home, container, false);

        // Set the toolbar as Actionbar
        toolbar = view.findViewById(R.id.myToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getString(R.string.my_wallet));

        // Enable home button on the toolbar
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        walletTransactions = view.findViewById(R.id.wallet_transactions);
        walletLoans = view.findViewById(R.id.wallet_loans);
        walletDeposit = view.findViewById(R.id.wallet_deposit);
        walletTransfer = view.findViewById(R.id.wallet_transfer);
        walletCoupons = view.findViewById(R.id.wallet_coupons);
        walletBuy = view.findViewById(R.id.wallet_buy);

        btnWalletTransactions = view.findViewById(R.id.btn_wallet_transactions);
        btnWalletLoans = view.findViewById(R.id.btn_wallet_loans);
        btnWalletDeposit = view.findViewById(R.id.btn_wallet_deposit);
        btnWalletTransfer = view.findViewById(R.id.btn_wallet_transfer);
        btnWalletCoupons = view.findViewById(R.id.btn_wallet_coupons);
        btnWalletBuy = view.findViewById(R.id.btn_wallet_buy);

        fm = getActivity().getSupportFragmentManager();
        balanceTextView = view.findViewById(R.id.crop_digital_wallet_amount);

        balanceTextView.setText("UGX " + NumberFormat.getInstance().format(balance));

        updateBalance();

        TextView walletUsername = view.findViewById(R.id.wallet_username);
        walletUsername.setText(ucf(WalletHomeFragment.getPreferences(WalletHomeFragment.PREFERENCES_LAST_NAME, context)) + " " + ucf(WalletHomeFragment.getPreferences(WalletHomeFragment.PREFERENCES_FIRST_NAME, context)));

        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        walletTransactions.setOnClickListener(this);
        walletLoans.setOnClickListener(this);
        walletDeposit.setOnClickListener(this);
        walletTransfer.setOnClickListener(this);
        walletCoupons.setOnClickListener(this);
        walletBuy.setOnClickListener(this);

        btnWalletTransactions.setOnClickListener(this);
        btnWalletLoans.setOnClickListener(this);
        btnWalletDeposit.setOnClickListener(this);
        btnWalletTransfer.setOnClickListener(this);
        btnWalletCoupons.setOnClickListener(this);
        btnWalletBuy.setOnClickListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wallet_transactions:
            case R.id.btn_wallet_transactions:
                navController.navigate(R.id.action_walletHomeFragment_to_walletTransactionsListFragment);
                break;
            case R.id.wallet_loans:
            case R.id.btn_wallet_loans:
                navController.navigate(R.id.action_walletHomeFragment_to_walletLoansListFragment);
                break;
            case R.id.wallet_deposit:
            case R.id.btn_wallet_deposit:
                openAddMoney();
                break;
            case R.id.wallet_transfer:
            case R.id.btn_wallet_transfer:
                openTransfer();
                break;
            case R.id.wallet_coupons:
            case R.id.btn_wallet_coupons:
                comingSoon();
                break;
            case R.id.wallet_buy:
            case R.id.btn_wallet_buy:
                openBuy();
                break;
            default:
                Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show();
        }

    }

    private String ucf(String str) {
        if(str==null || str.length()<2 )
            return str;

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String getPreferences(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref",
                0);
        return sharedPreferences.getString(key, "");
    }

    public static void savePreferences(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME,
                0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveUser(JSONObject user, Context context) throws JSONException {
        WalletHomeFragment.savePreferences(PREFERENCES_FIRST_NAME, user.getString("firstname"), context);
        WalletHomeFragment.savePreferences("email", user.getString("email"), context);
        WalletHomeFragment.savePreferences(PREFERENCES_USER_ID, user.getString("id"), context);
        WalletHomeFragment.savePreferences(PREFERENCES_LAST_NAME, user.getString("lastname"), context);
        WalletHomeFragment.savePreferences(PREFERENCES_USER_EMAIL, user.getString("email"), context);
        WalletHomeFragment.savePreferences(STREET_PREFERENCES_ID, user.getString("addressStreet"), context);
        WalletHomeFragment.savePreferences(CITY_PREFERENCES_ID, user.getString("addressCityOrTown"), context);
        WalletHomeFragment.savePreferences("phoneNumber", user.getString("phoneNumber"), context);
        // WalletHomeFragment.savePreferences("latitude", user.getString("latitude"), context);
        //WalletHomeFragment.savePreferences("longitude", user.getString("longitude"), context);
        // WalletHomeFragment.savePreferences("userimage", user.getString("userimage"), this);
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
        client.addHeader("Authorization", "Bearer " + WalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("userId", WalletHomeFragment.getPreferences(WalletHomeFragment.PREFERENCES_USER_ID, this.context));
        client.get(ApiPaths.CROP_WALLET_GET_BALANCE, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
//                dialog = new ProgressDialog(WalletHomeActivity.this);
//                dialog.setIndeterminate(true);
//                dialog.setMessage("Please Wait..");
//                dialog.setCancelable(false);
//                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    balance = response.getDouble("balance");

                    balanceTextView.setText("UGX " + NumberFormat.getInstance().format(balance));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                //WalletAuthActivity.startAuth(context, false);
            }
        });
    }

    public static void startHome(Context context) {
        try {
            Intent home = new Intent(context, WalletHomeActivity.class);
            context.startActivity(home);
        } catch (Exception e) {
            Log.e("Intent start Error: ", e.getMessage());
        }
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