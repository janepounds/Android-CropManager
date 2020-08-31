package com.myfarmnow.myfarmcrop.activities.wallet;

import android.os.Bundle;
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


public class WalletHomeActivity extends AppCompatActivity {

    public Fragment currentFragment;
    public WalletHomeFragment homeFragment;
    public WalletLoansListFragment loansListFragment;
    public WalletTransactionsListFragment transactionsListFragment;
    public static FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_home);

        fm = getSupportFragmentManager();
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
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
