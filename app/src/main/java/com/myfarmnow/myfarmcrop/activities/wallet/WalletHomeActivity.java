package com.cabral.emaisha.wallet.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cabral.emaisha.R;

import com.cabral.emaisha.activities.MainActivity;
import com.cabral.emaisha.app.MyAppPrefsManager;
import com.cabral.emaisha.constant.ConstantValues;
import com.cabral.emaisha.network.StartAppRequests;
import com.cabral.emaisha.wallet.models.ApiPaths;
import com.cabral.emaisha.wallet.popupDailogs.Buy;
import com.cabral.emaisha.wallet.popupDailogs.DepositMoneyMobile;
import com.cabral.emaisha.wallet.popupDailogs.DepositMoneyVisa;
import com.cabral.emaisha.wallet.popupDailogs.DepositMoneyVoucher;
import com.cabral.emaisha.wallet.popupDailogs.DepositPayments;
import com.cabral.emaisha.wallet.popupDailogs.TransferMoney;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import cz.msebera.android.httpclient.Header;



public class WalletHomeActivity extends AppCompatActivity  {

    public static final String PREFERENCES_FILE_NAME ="pref";
    public static final String STREET_PREFERENCES_ID ="addressStreet";
    public static final String CITY_PREFERENCES_ID ="addressCityOrTown";
    public static final String COUNTRY_PREFERENCES_ID ="addressCountry";
    public static final String PREFERENCES_FIRST_NAME ="firstname";
    public static final String PREFERENCES_LAST_NAME ="lastname";
    public static final String PREFERENCES_USER_ID ="userId";
    public static final String PREFERENCES_USER_EMAIL ="email";
    public static final String PREFERENCES_PHONE_NUMBER = "phoneNumber";

    public static  TextView balanceTextView;
    public static  Context context;
    static TabLayout tabs;   private Toolbar toolbar;
    static double balance=0;
    ActionBar actionBar;
    public static FragmentManager fm;

    private DisplayImageOptions options;
    //Imageucf
    private ProgressDialog dialog;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.wallet_home);
        context=WalletHomeActivity.this;
        toolbar = findViewById(R.id.myToolbar);
        // Get ActionBar and Set the Title and HomeButton of Toolbar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.my_wallet));

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.fm=getSupportFragmentManager();
        balanceTextView = findViewById(R.id.crop_digital_wallet_amount);

        balanceTextView.setText("UGX "+ NumberFormat.getInstance().format(balance));

        updateBalance();

        TextView username_view= findViewById(R.id.username_wallet_home);

        username_view.setText(ucf(WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_LAST_NAME,this))+" "+ucf(WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_FIRST_NAME,this)) );

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                // Set UserLoggedIn in MyAppPrefsManager

                MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(WalletHomeActivity.this);
                myAppPrefsManager.setUserLoggedIn(true);

                // Set isLogged_in of ConstantValues
                ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
                Log.e("USERID CHECK:",ConstantValues.IS_USER_LOGGED_IN+"" );
                StartAppRequests.RegisterDeviceForFCM(WalletHomeActivity.this);

                startActivity(new Intent(WalletHomeActivity.this, MainActivity.class));
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private  String ucf(String str){
         return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String getPreferences(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref",
                0);
        return sharedPreferences.getString(key, "");

    }

    public static void  savePreferences(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME ,
                0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveUser(JSONObject user, Context context) throws JSONException{

        WalletHomeActivity.savePreferences(PREFERENCES_FIRST_NAME, user.getString("firstname"), context);
        WalletHomeActivity.savePreferences("email", user.getString("email"), context);
        WalletHomeActivity.savePreferences(PREFERENCES_USER_ID, user.getString("id"), context);
        WalletHomeActivity.savePreferences(PREFERENCES_LAST_NAME, user.getString("lastname"), context);
        WalletHomeActivity.savePreferences(PREFERENCES_USER_EMAIL, user.getString("email"), context);
        WalletHomeActivity.savePreferences(STREET_PREFERENCES_ID, user.getString("addressStreet"), context);
        WalletHomeActivity.savePreferences(CITY_PREFERENCES_ID, user.getString("addressCityOrTown"), context);
        WalletHomeActivity.savePreferences("phoneNumber", user.getString("phoneNumber"), context);
       // WalletHomeActivity.savePreferences("latitude", user.getString("latitude"), context);
        //WalletHomeActivity.savePreferences("longitude", user.getString("longitude"), context);
        // WalletHomeActivity.savePreferences("userimage", user.getString("userimage"), this);

    }



    public void openAddMoney(View view){
        FragmentTransaction ft = this.fm.beginTransaction();
        Fragment prev = this.fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment depositDailog =new DepositPayments(WalletHomeActivity.this,balance);
        depositDailog.show( ft, "dialog");
    }

    public void openAddMobileMoney(View view){
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = this.fm.beginTransaction();
        Fragment prev = this.fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment depositDailog =new DepositMoneyMobile(WalletHomeActivity.this,balance);
        depositDailog.show( ft, "dialog");
    }

    public void openAddMoneyVisa(View view){
        FragmentTransaction ft = this.fm.beginTransaction();
        Fragment prev = this.fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment depositDailog =new DepositMoneyVisa(WalletHomeActivity.this,balance,getSupportFragmentManager());
        depositDailog.show( ft, "dialog");
    }

    public void openAddMoneyVoucher(View view){
        FragmentTransaction ft = this.fm.beginTransaction();
        Fragment prev = this.fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment depositDailog =new DepositMoneyVoucher(WalletHomeActivity.this,balance);
        depositDailog.show( ft, "dialog");
    }

    public void openTransactions(View view){
        Intent transactionActivity = new Intent(WalletHomeActivity.this, TransactionsList.class);
        startActivity(transactionActivity);

    }
    public void openBuy(View view){
        FragmentTransaction ft = this.fm.beginTransaction();
        Fragment prev = this.fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment buyfoodDailog =new Buy(WalletHomeActivity.this,getSupportFragmentManager());
        buyfoodDailog.show( ft, "dialog");
    }
    public void comingsoon(View view){

        ProgressDialog dialog = new ProgressDialog(WalletHomeActivity.this);
        dialog.setIndeterminate(false);
        dialog.setMessage("Coming Soon ..!!");
        dialog.setCancelable(true);
        dialog.show();

    }

    public void openLoans(View view){

        Intent openW = new Intent(WalletHomeActivity.this, WalletLoansListActivity.class);
        openW.putExtra("balance", balance);
        startActivity(openW);
    }

    public void openTransfer(View view){
        FragmentTransaction ft = this.fm.beginTransaction();
        Fragment prev = this.fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment transferDailog =new TransferMoney(WalletHomeActivity.this,this.balance,getSupportFragmentManager());
        transferDailog.show( ft, "dialog");
    }

    public static void updateBalance(){
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization","Bearer "+ WalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("userId", WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_ID,context));
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

                   balanceTextView.setText("UGX "+ NumberFormat.getInstance().format(balance));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                if(statusCode==401){
                    Toast.makeText(context, "Session Expired", Toast.LENGTH_LONG).show();
                   // WalletAuthActivity.startAuth(context, true);

                }
                if(errorResponse != null) {
                    Log.e("info", new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info", "Something got very very wrong");
                }

//                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                Toast.makeText(context, "An error occurred Try again Later", Toast.LENGTH_LONG).show();
                //WalletAuthActivity.startAuth(context, false);
            }
        });
    }



    public static void startHome(Context context){
        try {

            Intent home = new Intent(context, WalletHomeActivity.class);
            context.startActivity(home);
        } catch (Exception e){
            Log.e("Intent start Error: ", e.getMessage());
        }
    }
}
