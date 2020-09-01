package com.myfarmnow.myfarmcrop.activities.wallet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.fragments.wallet.WalletHomeFragment;
import com.myfarmnow.myfarmcrop.fragments.wallet.WalletLoansListFragment;
import com.myfarmnow.myfarmcrop.fragments.wallet.WalletTransactionsListFragment;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.DepositMoneyMobile;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.DepositMoneyVisa;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.DepositMoneyVoucher;

import org.json.JSONException;
import org.json.JSONObject;


public class WalletHomeActivity extends AppCompatActivity {
    private static final String TAG = "WalletHomeActivity";
    private Context context;
    public WalletHomeFragment homeFragment;
    public WalletLoansListFragment loansListFragment;
    public WalletTransactionsListFragment transactionsListFragment;
    public static FragmentManager fm;


    public static final String PREFERENCES_FILE_NAME = "pref";
    public static final String STREET_PREFERENCES_ID = "addressStreet";
    public static final String CITY_PREFERENCES_ID = "addressCityOrTown";
    public static final String COUNTRY_PREFERENCES_ID = "addressCountry";
    public static final String PREFERENCES_FIRST_NAME = "firstname";
    public static final String PREFERENCES_LAST_NAME = "lastname";
    public static final String PREFERENCES_USER_ID = "userId";
    public static final String PREFERENCES_USER_EMAIL = "email";
    public static final String PREFERENCES_PHONE_NUMBER = "phoneNumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_home);

        context = getApplicationContext();
        fm = getSupportFragmentManager();
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
        editor.commit();
    }

    public static void saveUser(JSONObject user, Context context) throws JSONException {
        WalletHomeActivity.savePreferences(WalletHomeActivity.PREFERENCES_FIRST_NAME, user.getString("firstname"), context);
        WalletHomeActivity.savePreferences("email", user.getString("email"), context);
        WalletHomeActivity.savePreferences(WalletHomeActivity.PREFERENCES_USER_ID, user.getString("id"), context);
        WalletHomeActivity.savePreferences(WalletHomeActivity.PREFERENCES_LAST_NAME, user.getString("lastname"), context);
        WalletHomeActivity.savePreferences(WalletHomeActivity.PREFERENCES_USER_EMAIL, user.getString("email"), context);
        WalletHomeActivity.savePreferences(WalletHomeActivity.STREET_PREFERENCES_ID, user.getString("addressStreet"), context);
        WalletHomeActivity.savePreferences(WalletHomeActivity.CITY_PREFERENCES_ID, user.getString("addressCityOrTown"), context);
        WalletHomeActivity.savePreferences("phoneNumber", user.getString("phoneNumber"), context);
//        WalletHomeActivity.savePreferences("latitude", user.getString("latitude"), context);
//        WalletHomeActivity.savePreferences("longitude", user.getString("longitude"), context);
//        WalletHomeActivity.savePreferences("userimage", user.getString("userimage"), context);
    }

    public static void startHome(Context context) {
        try {
            Intent home = new Intent(context, WalletHomeActivity.class);
            context.startActivity(home);
        } catch (Exception e) {
            Log.e("Intent start Error: ", e.getMessage());
        }
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
        DialogFragment depositDialog = new DepositMoneyMobile(this, WalletHomeFragment.balance);
        depositDialog.show(ft, "dialog");
    }

    public void openAddMoneyVisa(View view) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment depositDialog = new DepositMoneyVisa(this, WalletHomeFragment.balance, getSupportFragmentManager());
        depositDialog.show(ft, "dialog");
    }

    public void openAddMoneyVoucher(View view) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment depositDialog = new DepositMoneyVoucher(this, WalletHomeFragment.balance);
        depositDialog.show(ft, "dialog");
    }
}
