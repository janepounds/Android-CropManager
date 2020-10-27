package com.myfarmnow.myfarmcrop.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.Login;
import com.myfarmnow.myfarmcrop.app.MyAppPrefsManager;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.customs.DialogLoader;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Addresses;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Cart;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Orders;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.SettingsFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.WishList;
import com.myfarmnow.myfarmcrop.models.user_model.UserData;
import com.myfarmnow.myfarmcrop.network.BuyInputsAPIClient;
import com.myfarmnow.myfarmcrop.utils.Utilities;
import com.myfarmnow.myfarmcrop.utils.ValidateInputs;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {
    private static final String TAG = "AccountFragment";
    private Context context;
    Fragment fragment;
    public Fragment currentFragment;
    FragmentManager fragmentManager;

    public My_Orders myOrders;
    public My_Addresses myAddresses;
    public WishList myFavorites;
    public SettingsFragment settings;
    public My_Cart my_cart;
    public News news;

    DialogLoader dialogLoader;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getString(R.string.actionAccount));

        RelativeLayout layoutNews = view.findViewById(R.id.layout_news);
        RelativeLayout layoutMyAddresses = view.findViewById(R.id.layout_my_addresses);
        RelativeLayout layoutMyOrders = view.findViewById(R.id.layout_my_orders);
        RelativeLayout layoutMyCart = view.findViewById(R.id.layout_my_cart);
        RelativeLayout layoutFavourites = view.findViewById(R.id.layout_favourites);
        RelativeLayout layoutRateMyApp = view.findViewById(R.id.layout_rate_my_app);
        RelativeLayout layoutShareApp = view.findViewById(R.id.layout_share_app);
        RelativeLayout layoutPrivacyPolicy = view.findViewById(R.id.layout_privacy_policy);
        RelativeLayout layoutRefundPolicy = view.findViewById(R.id.layout_refund_policy);
        RelativeLayout layoutLogout = view.findViewById(R.id.layout_logout);
        RelativeLayout layoutChangePassword = view.findViewById(R.id.layout_change_password);
        TextView accountUsername = view.findViewById(R.id.text_username_account_fragment);
        TextView accountUserEmail = view.findViewById(R.id.text_email_account_fragment);
        TextView userAddress = view.findViewById(R.id.txt_user_address);

        // Edit UserID in SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);

        Log.d(TAG, "onCreateView: First Name = " + sharedPreferences.getString(DashboardActivity.PREFERENCES_USER_EMAIL, null));
        Log.d(TAG, "onCreateView: First Name = " + sharedPreferences.getString(DashboardActivity.PREFERENCES_FIRST_NAME, ""));
        Log.d(TAG, "onCreateView: Last Name = " + sharedPreferences.getString(DashboardActivity.PREFERENCES_LAST_NAME, ""));

        Log.d(TAG, "onCreateView: addressStreet = " + sharedPreferences.getString("addressStreet", ""));
        Log.d(TAG, "onCreateView: addressCityOrTown = " + sharedPreferences.getString("addressCityOrTown", ""));
        Log.d(TAG, "onCreateView: address_district = " + sharedPreferences.getString("address_district", ""));
        Log.d(TAG, "onCreateView: addressCountry = " + sharedPreferences.getString("addressCountry", ""));

        String name = sharedPreferences.getString(DashboardActivity.PREFERENCES_FIRST_NAME, "") + " " + sharedPreferences.getString(DashboardActivity.PREFERENCES_LAST_NAME, "");
        accountUsername.setText(name);
        accountUserEmail.setText(sharedPreferences.getString(DashboardActivity.PREFERENCES_USER_EMAIL, null));

        String address = sharedPreferences.getString("addressStreet", "") + ", " + sharedPreferences.getString("addressCityOrTown", "") + ", " + sharedPreferences.getString("addressCountry", "");
        userAddress.setText(address);

        fragmentManager = requireActivity().getSupportFragmentManager();
        currentFragment = fragmentManager.getPrimaryNavigationFragment();

        layoutNews.setOnClickListener(v -> getNews());

        layoutMyAddresses.setOnClickListener(v -> getMyAddresses());

        layoutMyOrders.setOnClickListener(v -> getMyOrders());

        layoutMyCart.setOnClickListener(v -> goToMyCart());

        layoutFavourites.setOnClickListener(v -> getMyFavourites());

        layoutRateMyApp.setOnClickListener(v -> rateApp());

        layoutShareApp.setOnClickListener(v -> shareApp());

        layoutPrivacyPolicy.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext(), android.R.style.Theme_NoTitleBar);
            View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
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
                Objects.requireNonNull(alertDialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
            }

            dialog_button.setOnClickListener(v1 -> alertDialog.dismiss());

            alertDialog.show();
        });

        layoutRefundPolicy.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext(), android.R.style.Theme_NoTitleBar);
            View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_webview_fullscreen, null);
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
                alertDialog.getWindow().setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
            }

            dialog_button.setOnClickListener(v12 -> alertDialog.dismiss());

            alertDialog.show();
        });

        layoutLogout.setOnClickListener(v -> logout());

        layoutChangePassword.setOnClickListener(v -> changePassword());

        dialogLoader = new DialogLoader(context);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void getMyAddresses() {

        if (myAddresses == null) {
            myAddresses = new My_Addresses(null);
            if (currentFragment == null)
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment_container, myAddresses)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            else
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment_container, myAddresses)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
        } else {
            fragmentManager.beginTransaction().show(myAddresses).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        }
        currentFragment = myAddresses;
        DashboardActivity.actionBar.setTitle(getString(R.string.actionAddresses));
    }

    public void getMyOrders() {

        if (myOrders == null) {
            myOrders = new My_Orders();
            if (currentFragment == null)
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment_container, myOrders)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            else
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment_container, myOrders)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
        } else {
            fragmentManager.beginTransaction().show(myOrders).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        }
        currentFragment = myOrders;
        DashboardActivity.actionBar.setTitle(getString(R.string.actionOrders));
    }

    public void goToMyCart() {
        // Navigate to My_Cart Fragment
        fragment = new My_Cart();
        if (currentFragment == null)
            fragmentManager.beginTransaction()
                    .add(R.id.main_fragment_container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();
        else
            fragmentManager.beginTransaction()
                    .hide(currentFragment)
                    .add(R.id.main_fragment_container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();

        currentFragment = my_cart;
        DashboardActivity.actionBar.setTitle(getString(R.string.actionCart));
    }

    public void getMyFavourites() {
        if (myFavorites == null) {
            myFavorites = new WishList();
            if (currentFragment == null)
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment_container, myFavorites)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            else
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment_container, myFavorites)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
        } else {
            fragmentManager.beginTransaction().show(myFavorites).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        }
        currentFragment = myFavorites;
        DashboardActivity.actionBar.setTitle(getString(R.string.actionFavourites));
    }

    public void getNews() {
        if (news == null) {
            news = new News();
            if (currentFragment == null)
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment_container, news)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            else
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment_container, news)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
        } else {
            fragmentManager.beginTransaction().show(news).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        }
        currentFragment = news;
        DashboardActivity.actionBar.setTitle(getString(R.string.actionNews));
    }

    public void shareApp() {
        // Share App with the help of static method of Utilities class
        Utilities.shareMyApp(requireContext());
    }

    public void rateApp() {
        // Rate App with the help of static method of Utilities class
        Utilities.rateMyApp(getContext());
    }

    public void logout() {
        // Edit UserID in SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(DashboardActivity.PREFERENCES_FILE_NAME,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DashboardActivity.PREFERENCES_USER_ID, "");
        editor.apply();

        // Set UserLoggedIn in MyAppPrefsManager
        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(context);
        myAppPrefsManager.logOutUser();

        // Set isLogged_in of ConstantValues
        ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();

        FirebaseAuth.getInstance().signOut();

        // Navigate to Login Activity
        startActivity(new Intent(getActivity(), Login.class));
        requireActivity().finish();
        requireActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
    }

    public void changePassword() {
        // Handle on Forgot Password Click
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context, R.style.DialogFullscreen);
        View dialogView = getLayoutInflater().inflate(R.layout.buy_inputs_dialog_input, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        final Button dialog_button = dialogView.findViewById(R.id.dialog_button);
        final TextView dialog_forgotText = dialogView.findViewById(R.id.forgot_password_text);
        final EditText dialog_input = dialogView.findViewById(R.id.dialog_input);
        final TextView dialog_title = dialogView.findViewById(R.id.dialog_title);
        final ImageView dismiss_button = dialogView.findViewById(R.id.dismissButton);
        dialog_forgotText.setVisibility(View.VISIBLE);
        dialog_button.setText(getString(R.string.sendemail));
        dialog_title.setText(getString(R.string.forgot_your_password));


        final android.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        dismiss_button.setOnClickListener(v -> alertDialog.dismiss());

        dialog_button.setOnClickListener(v -> {

            if (ValidateInputs.isValidEmail(dialog_input.getText().toString().trim())) {
                // Request for Password Reset
                processForgotPassword(dialog_input.getText().toString());

            } else {
                Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.invalid_email), Snackbar.LENGTH_LONG).show();
            }

            alertDialog.dismiss();
        });
    }

    private void processForgotPassword(String email) {

        dialogLoader.showProgressDialog();

        Call<UserData> call = BuyInputsAPIClient.getInstance()
                .processForgotPassword
                        (
                                email
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {
                    // Show the Response Message
                    String message = response.body().getMessage();
                    Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();

                } else {
                    // Show the Error Message
                    Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(context, "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NotNull MenuInflater inflater) {
        // Hide Cart Icon in the Toolbar
        MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
        MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
        MenuItem profileItem = menu.findItem(R.id.toolbar_edit_profile);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
        profileItem.setVisible(true);
        languageItem.setVisible(false);
        currencyItem.setVisible(false);
        searchItem.setVisible(false);
        cartItem.setVisible(false);
    }

}
