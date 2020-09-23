package com.myfarmnow.myfarmcrop.fragments;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.Login;
import com.myfarmnow.myfarmcrop.app.MyAppPrefsManager;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.databinding.FragmentAccountBinding;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.MeFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Addresses;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Cart;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Orders;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.SettingsFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Update_Account;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.WishList;
import com.myfarmnow.myfarmcrop.utils.Utilities;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {
    private static final String TAG = "AccountFragment";
    private FragmentAccountBinding binding;
    private Context context;
    Fragment fragment;
    public Fragment currentFragment;
    FragmentManager fragmentManager;


    public Update_Account update_account;
    public My_Orders myOrders;
    public My_Addresses myAddresses;
    public WishList myfavorites;
    public SettingsFragment settings;
    public My_Cart my_cart;

    ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        fragmentManager=getActivity().getSupportFragmentManager();
        currentFragment=fragmentManager.getPrimaryNavigationFragment();

        binding.layoutMyAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyAddresses();
            }
        });
        binding.layoutMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyOrders();
            }
        });
        binding.layoutMyCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMycart();
            }
        });
        binding.layoutFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmyFavourites();
            }
        });

        binding.layoutRateMyApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reteApp();
            }
        });
        binding.layoutShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });
        binding.layoutPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_NoTitleBar);
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);

                final ImageButton dialog_button = dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = dialogView.findViewById(R.id.dialog_webView);

                dialog_title.setText(getString(R.string.privacy_policy));


                String description = ConstantValues.PRIVACY_POLICY;
                String styleSheet = "<style> " +
                        "body{background:#ffffff; margin:0; padding:0} " +
                        "p{color:#757575;} " +
                        "img{display:inline; height:auto; max-width:100%;}" +
                        "</style>";

                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


                final AlertDialog alertDialog = dialog.create();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }

                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();

            }
        });

        binding.layoutRefundPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_NoTitleBar);
                View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);

                final ImageButton dialog_button = dialogView.findViewById(R.id.dialog_button);
                final TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
                final WebView dialog_webView = dialogView.findViewById(R.id.dialog_webView);

                dialog_title.setText(getString(R.string.refund_policy));


                String description = ConstantValues.REFUND_POLICY;
                String styleSheet = "<style> " +
                        "body{background:#ffffff; margin:0; padding:0} " +
                        "p{color:#757575;} " +
                        "img{display:inline; height:auto; max-width:100%;}" +
                        "</style>";

                dialog_webView.setHorizontalScrollBarEnabled(false);
                dialog_webView.loadDataWithBaseURL(null, styleSheet + description, "text/html", "utf-8", null);


                final AlertDialog alertDialog = dialog.create();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }

                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });
        binding.layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logout();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public void getMyAddresses(){

        if (myAddresses == null) {
            myAddresses = new My_Addresses(null);
            if(currentFragment==null)
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, myAddresses)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            else
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.fragment_container, myAddresses)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
        } else {
            fragmentManager.beginTransaction().show(myAddresses).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        }
        currentFragment = myAddresses;
    }
    public void getMyOrders(){

        if (myOrders == null) {
            myOrders = new My_Orders();
            if(currentFragment==null)
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, myOrders)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            else
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.fragment_container, myOrders)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
        } else {
            fragmentManager.beginTransaction().show(myOrders).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        }
        currentFragment = myOrders;
    }

    public void goToMycart(){
        // Navigate to My_Cart Fragment
        fragment = new My_Cart();
        if(currentFragment==null)
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();
        else
            fragmentManager.beginTransaction()
                    .hide(currentFragment)
                    .add(R.id.fragment_container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

        currentFragment = my_cart;
    }


    public void getmyFavourites(){
        if (myfavorites == null) {
            myfavorites = new WishList();
            if(currentFragment==null)
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, myfavorites)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            else
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.fragment_container, myfavorites)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
        } else {
            fragmentManager.beginTransaction().show(myfavorites).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        }
        currentFragment = myfavorites;
    }

    public void  shareApp(){
        // Share App with the help of static method of Utilities class
        Utilities.shareMyApp(getContext());
    }

    public  void reteApp(){
        // Rate App with the help of static method of Utilities class
        Utilities.rateMyApp(getContext());

    }
    public void logout(){
        // Edit UserID in SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(DashboardActivity.PREFERENCES_FILE_NAME,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userID", "");
        editor.apply();

        // Set UserLoggedIn in MyAppPrefsManager
        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(context);
        myAppPrefsManager.setUserLoggedIn(false);

        // Set isLogged_in of ConstantValues
        ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();

        FirebaseAuth.getInstance().signOut();

        // Navigate to Login Activity
        startActivity(new Intent( getActivity(), Login.class));
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
    }


}
