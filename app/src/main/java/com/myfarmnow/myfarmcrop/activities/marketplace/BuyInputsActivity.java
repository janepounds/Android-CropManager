package com.myfarmnow.myfarmcrop.activities.marketplace;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.WrappedDrawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.app.MyAppPrefsManager;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.fragments.AccountFragment;
import com.myfarmnow.myfarmcrop.fragments.HomeFragment;
import com.myfarmnow.myfarmcrop.fragments.OffersFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Categories_3;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.HomePage;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.MeFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Addresses;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Cart;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Orders;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Nearby_Merchants;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Products;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.SearchFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.SettingsFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Shipping_Address;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Thank_You;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.UpdateAccountFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.WishList;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.customs.NotificationBadger;
import com.myfarmnow.myfarmcrop.utils.LocaleHelper;
import com.myfarmnow.myfarmcrop.utils.Utilities;

import am.appwise.components.ni.NoInternetDialog;

import static android.provider.SettingsSlicesContract.KEY_LOCATION;

public class BuyInputsActivity extends AppCompatActivity{

    Toolbar toolbar;
    ActionBar actionBar;


    SharedPreferences sharedPreferences;
    MyAppPrefsManager myAppPrefsManager;


    public static String mSelectedItem;
    private static final String SELECTED_ITEM_ID = "selected";
    // Razor Pay callback is not for the fragment so we need to paas static data from main activity to sub fragmnet
    public static String paymentNonceToken;

    public Fragment defaultHomeFragment;
    public Fragment currentFragment;
    public HomePage homePage_9;
    public SettingsFragment settings;
    public MeFragment meFragment;

    public Fragment getCurrentFragment() {
        return currentFragment;
    }


    //*********** Called when the Activity is becoming Visible to the User ********//



    //*********** Called when the Activity is first Created ********//

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_inputs_activity);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(BuyInputsActivity.this).build();
        //noInternetDialog.show();

        // Get MyAppPrefsManager
        myAppPrefsManager = new MyAppPrefsManager(BuyInputsActivity.this);
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);


        // Binding Layout Views
        toolbar = findViewById(R.id.myToolbar);


        // Get ActionBar and Set the Title and HomeButton of Toolbar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(ConstantValues.APP_HEADER);

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



        // Handle ToolbarNavigationClickListener with OnBackStackChangedListener
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                // Check BackStackEntryCount of FragmentManager
                if (getSupportFragmentManager().getBackStackEntryCount() <= 0)  {
                    // Set DrawerToggle Indicator and default ToolbarNavigationClickListener
                    actionBar.setTitle(ConstantValues.APP_HEADER);
                    actionBar.setHomeButtonEnabled(false);
                    actionBar.setDisplayHomeAsUpEnabled(false);
                }
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                setupTitle();

            }
        });


        // Select SELECTED_ITEM from SavedInstanceState
        mSelectedItem = savedInstanceState == null ? ConstantValues.DEFAULT_HOME_STYLE : savedInstanceState.getString(SELECTED_ITEM_ID);
        setupDefaultHomePage(mSelectedItem);

        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.page_1:
                selectedFragment = new HomeFragment(BuyInputsActivity.this, getSupportFragmentManager(),  MyFarmDbHandlerSingleton.getHandlerInstance(this));
                break;
            case R.id.page_2:
                selectedFragment = new OffersFragment();
                break;
            case R.id.page_3:
                selectedFragment = new AccountFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, selectedFragment).commit();
        return true;
    };

    private void setupTitle() {

        Fragment curruntFrag = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        if (curruntFrag instanceof My_Cart) {
            actionBar.setTitle(getString(R.string.actionCart));
        } else if (curruntFrag instanceof Shipping_Address) {
            actionBar.setTitle(getString(R.string.shipping_address));
        } else if (curruntFrag instanceof Nearby_Merchants) {
            actionBar.setTitle(getString(R.string.nearby_merchants));
        } else if (curruntFrag instanceof UpdateAccountFragment) {
            actionBar.setTitle(getString(R.string.actionAccount));
        } else if (curruntFrag instanceof My_Orders) {
            actionBar.setTitle(getString(R.string.actionOrders));
        } else if (curruntFrag instanceof My_Addresses) {
            actionBar.setTitle(getString(R.string.actionAddresses));
        } else if (curruntFrag instanceof WishList) {
            actionBar.setTitle(getString(R.string.actionFavourites));
        } else if (curruntFrag instanceof SettingsFragment) {
            actionBar.setTitle(getString(R.string.actionSettings));
        }

    }

    //*********** Navigate to given Selected Item of NavigationDrawer ********//

    private void setupDefaultHomePage(String defaultHome) {
        homePage_9 = new HomePage();
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_9, getString(R.string.homeStyle9)).commit();
        currentFragment = homePage_9;
        defaultHomeFragment = homePage_9;

    }

    private void showHomePage() {
        getSupportFragmentManager().beginTransaction().hide(currentFragment).show(defaultHomeFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        currentFragment = defaultHomeFragment;
        actionBar.setTitle(getString(R.string.app_name));
    }

    //*********** Called by the System when the Device's Configuration changes while Activity is Running ********//

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Configure ActionBarDrawerToggle with new Configuration
    }


    //*********** Invoked to Save the Instance's State when the Activity may be Temporarily Destroyed ********//

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (Shipping_Address.map != null) {
            outState.putParcelable(Shipping_Address.KEY_CAMERA_POSITION,Shipping_Address.map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, Shipping_Address.lastKnownLocation);
        }
        super.onSaveInstanceState(outState);

        super.onSaveInstanceState(outState);
        // Save the Selected NavigationDrawer Item
        outState.putString(SELECTED_ITEM_ID, mSelectedItem);
    }


    //*********** Set the Base Context for the ContextWrapper ********//

    @Override
    protected void attachBaseContext(Context newBase) {

        String languageCode = ConstantValues.LANGUAGE_CODE;
        if ("".equalsIgnoreCase(languageCode) || languageCode == null )
            languageCode = ConstantValues.LANGUAGE_CODE = "en";

        super.attachBaseContext(LocaleHelper.wrapLocale(newBase, languageCode));
    }


    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.checkout));
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }


    //*********** Creates the Activity's OptionsMenu ********//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate toolbar_menu Menu
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        // Bind Menu Items
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);



        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView image = (ImageView) inflater.inflate(R.layout.buy_inputs_animated_ic_cart, null);

        Drawable itemIcon = cartItem.getIcon().getCurrent();
        image.setImageDrawable(itemIcon);

        cartItem.setActionView(image);


        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to My_Cart Fragment
                Fragment fragment = new My_Cart();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            }
        });


        // Tint Menu Icons with the help of static method of Utilities class
        Utilities.tintMenuIcon(BuyInputsActivity.this, searchItem, R.color.white);
        Utilities.tintMenuIcon(BuyInputsActivity.this, cartItem, R.color.white);

        return super.onCreateOptionsMenu(menu);
    }


    //*********** Prepares the OptionsMenu of Toolbar ********//

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

                MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);

                // Get No. of Cart Items with the static method of My_Cart Fragment
                int cartSize = My_Cart.getCartSize();


                // if Cart has some Items
                if (cartSize > 0) {

                    // Animation for cart_menuItem
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_icon);
                    animation.setRepeatMode(Animation.REVERSE);
                    animation.setRepeatCount(1);

                    cartItem.getActionView().startAnimation(animation);
                    cartItem.getActionView().setAnimation(null);


                    LayerDrawable icon = null;
                    Drawable drawable = cartItem.getIcon();

                    if (drawable instanceof DrawableWrapper) {
                        drawable = ((DrawableWrapper) drawable).getWrappedDrawable();
                    } else if (drawable instanceof WrappedDrawable) {
                        drawable = ((WrappedDrawable) drawable).getWrappedDrawable();
                    }


                    if (drawable instanceof LayerDrawable) {
                        icon = (LayerDrawable) drawable;
                    } else if (drawable instanceof DrawableWrapper) {
                        DrawableWrapper wrapper = (DrawableWrapper) drawable;
                        if (wrapper.getWrappedDrawable() instanceof LayerDrawable) {
                            icon = (LayerDrawable) wrapper.getWrappedDrawable();
                        }
                    }

//                icon = (LayerDrawable) drawable;


                    // Set BadgeCount on Cart_Icon with the static method of NotificationBadger class
                    if (icon != null)
                        NotificationBadger.setBadgeCount(this, icon, String.valueOf(cartSize));


                } else {
                    // Set the Icon for Empty Cart
                    cartItem.setIcon(R.drawable.ic_cart_empty);
                }



        return super.onPrepareOptionsMenu(menu);
    }


    //*********** Called whenever an Item in OptionsMenu is Selected ********//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (item.getItemId()) {

            case android.R.id.home:
                if (getSupportFragmentManager().findFragmentById(R.id.main_fragment) instanceof Thank_You) {
                    getFragmentManager().popBackStack(getString(R.string.actionHome), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else {
                    BuyInputsActivity.super.onBackPressed();
                }
                break;


            case R.id.toolbar_ic_search:

                // Navigate to SearchFragment Fragment
                fragment = new SearchFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                break;

            case R.id.toolbar_ic_cart:

                // Navigate to My_Cart Fragment
                fragment = new My_Cart();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //*********** Called when the Activity has detected the User pressed the Back key ********//

    @Override
    public void onBackPressed() {

        // Get FragmentManager
        FragmentManager fm = getSupportFragmentManager();


         if (fm.getBackStackEntryCount() > 0) {

            // Pop previous Fragment
            fm.popBackStack();

        }
       /* // Check if doubleBackToExitPressed is true
        else if (doublePressedBackToExit) {
            super.onBackPressed();

        }*/
        else {

            if (currentFragment == defaultHomeFragment)
                BuyInputsActivity.super.onBackPressed();
            else
                showHomePage();

        }
    }



    public void clearBackStackInclusive(String tag) {
        getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}

