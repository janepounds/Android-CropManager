package com.myfarmnow.myfarmcrop.activities.marketplace;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.WrappedDrawable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.Login;
import com.myfarmnow.myfarmcrop.app.MyAppPrefsManager;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Categories_3;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.CurrencyFrag;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.HomePage_9;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Languages;
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
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Update_Account;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.WishList;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.customs.NotificationBadger;
import com.myfarmnow.myfarmcrop.network.StartAppRequests;
import com.myfarmnow.myfarmcrop.receivers.AlarmReceiver;
import com.myfarmnow.myfarmcrop.utils.DrawerLocker;
import com.myfarmnow.myfarmcrop.utils.LocaleHelper;
import com.myfarmnow.myfarmcrop.utils.NotificationScheduler;
import com.myfarmnow.myfarmcrop.utils.Utilities;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import am.appwise.components.ni.NoInternetDialog;

import static android.provider.SettingsSlicesContract.KEY_LOCATION;

//import android.support.v4.graphics.drawable.DrawableWrapper;


/**
 * MainActivity of the App
 **/

public class BuyInputsActivity extends AppCompatActivity implements  DrawerLocker {

    int homeStyle, categoryStyle;

    Toolbar toolbar;
    ActionBar actionBar;

    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;

    SharedPreferences sharedPreferences;
    MyAppPrefsManager myAppPrefsManager;


    public static String mSelectedItem;
    private static final String SELECTED_ITEM_ID = "selected";


    private boolean FLAG_YOUTUBE, FLAG_FAVORITE;

    // Razor Pay callback is not for the fragment so we need to paas static data from main activity to sub fragmnet
    public static String paymentNonceToken;

    public Fragment defaultHomeFragment;
    public Fragment defaultCategoryFragment;
    public Fragment currentFragment;
    public HomePage_9 homePage_9;
    public Categories_3 categories_3;
    public Products newest;
    public Products topSellers;
    public Products superDeals;
    public Products mostLiked;
    public Update_Account update_account;
    public My_Orders myOrders;
    public My_Addresses myAddresses;
    public WishList myfavorites;
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
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        floatingActionButton = findViewById(R.id.settingsFabBtn);


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

                     enableBottomNavigation(true);
                }
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                setupTitle();

            }
        });

        setupBottomNavigation();


        // Select SELECTED_ITEM from SavedInstanceState
        mSelectedItem = savedInstanceState == null ? ConstantValues.DEFAULT_HOME_STYLE : savedInstanceState.getString(SELECTED_ITEM_ID);

        setupDefaultHomePage(mSelectedItem);
        //setupDefaultCategoryPage(getResources().getString(R.string.categoryStyle3));
        // Navigate to SelectedItem
        //drawerSelectedItemNavigation(mSelectedItem);

        if (ConstantValues.IS_CLIENT_ACTIVE)
            floatingActionButton.setVisibility(View.GONE);
    }

    private void setupTitle() {

        Fragment curruntFrag = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        if (curruntFrag instanceof My_Cart) {
            actionBar.setTitle(getString(R.string.actionCart));
        } else if (curruntFrag instanceof Shipping_Address) {
            actionBar.setTitle(getString(R.string.shipping_address));
        } else if (curruntFrag instanceof Nearby_Merchants) {
            actionBar.setTitle(getString(R.string.nearby_merchants));
        } else if (curruntFrag instanceof Update_Account) {
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


    private void setupBottomNavigation() {
        setDrawerEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem1) {
                switch (menuItem1.getItemId()) {
                    case R.id.navigation_home:
                        if (currentFragment == defaultHomeFragment)
                            return false;
                        BuyInputsActivity.this.drawerSelectedItemNavigation(ConstantValues.DEFAULT_HOME_STYLE);
                        actionBar.setTitle(BuyInputsActivity.this.getResources().getString(R.string.app_name));
                        return true;
                    case R.id.navigation_category:
                        BuyInputsActivity.this.drawerSelectedItemNavigation(ConstantValues.DEFAULT_CATEGORY_STYLE);
                        actionBar.setTitle(BuyInputsActivity.this.getResources().getString(R.string.categories));
                        return true;
                    case R.id.navigation_wishlit:
                        if (currentFragment == myfavorites)
                            return false;
                        if (ConstantValues.IS_USER_LOGGED_IN) {
                            BuyInputsActivity.this.drawerSelectedItemNavigation(BuyInputsActivity.this.getString(R.string.actionFavourites));
                            return true;
                        }
                        BuyInputsActivity.this.startActivity(new Intent(BuyInputsActivity.this, Login.class));
                        return false;
                    case R.id.navigation_me:
                        if (currentFragment == meFragment)
                            return false;
                        drawerSelectedItemNavigation(getString(R.string.actionMe));
                        return true;
                }
                return false;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                extraSettings = new ExtraSettings();
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.main_fragment, extraSettings)
//                        .addToBackStack(null)
//                        .commit();
            }
        });

    }

    //*********** Navigate to given Selected Item of NavigationDrawer ********//

    public void drawerSelectedItemNavigation(String selectedItem) {

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (selectedItem.equalsIgnoreCase(getString(R.string.homeStyle9))) {
            mSelectedItem = selectedItem;

            // Navigate to HomePage5 Fragment
            if (homePage_9 == null) {
                homePage_9 = new HomePage_9();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment, homePage_9)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, homePage_9)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(homePage_9).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = homePage_9;

            actionBar.setTitle(getString(R.string.app_name));

        }
        else if (selectedItem.equalsIgnoreCase(getString(R.string.actionCategories))) {
            mSelectedItem = selectedItem;
            Log.e("Selected Item:",selectedItem);
            if (categories_3 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                categories_3 = new Categories_3();
                categories_3.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, categories_3, getString(R.string.categoryStyle1))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(categories_3).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(getString(R.string.actionHome)).commit();
            }
            currentFragment = categories_3;

            actionBar.setTitle(getString(R.string.actionCategories));

        }  else if (selectedItem.equalsIgnoreCase(getString(R.string.categoryStyle3))) {
            mSelectedItem = selectedItem;

            if (categories_3 == null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isHeaderVisible", false);
                categories_3 = new Categories_3();
                categories_3.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, categories_3, getString(R.string.categoryStyle3))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(categories_3).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = categories_3;


            actionBar.setTitle(getString(R.string.actionCategories));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionShop))) {
            mSelectedItem = selectedItem;

            if (newest == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "Newest");
                bundle.putBoolean("isMenuItem", true);
                newest = new Products();
                newest.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, newest)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(newest).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = newest;

            actionBar.setTitle(getString(R.string.actionShop));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.Newest))) {
            mSelectedItem = selectedItem;

            if (newest == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "Newest");
                bundle.putBoolean("isMenuItem", true);
                newest = new Products();
                newest.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, newest)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(newest).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = newest;

            actionBar.setTitle(getString(R.string.actionShop));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.superDeals))) {
            mSelectedItem = selectedItem;

            if (superDeals == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "special");
                bundle.putBoolean("isMenuItem", true);
                superDeals = new Products();
                superDeals.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, superDeals)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(superDeals).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = superDeals;

            actionBar.setTitle(getString(R.string.actionShop));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.topSeller))) {
            mSelectedItem = selectedItem;

            if (topSellers == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "top seller");
                bundle.putBoolean("isMenuItem", true);
                topSellers = new Products();
                topSellers.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, topSellers)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(topSellers).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = topSellers;

            actionBar.setTitle(getString(R.string.actionShop));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.mostLiked))) {
            mSelectedItem = selectedItem;

            if (mostLiked == null) {
                Bundle bundle = new Bundle();
                bundle.putString("sortBy", "most liked");
                bundle.putBoolean("isMenuItem", true);
                mostLiked = new Products();
                mostLiked.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, mostLiked)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(mostLiked).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = mostLiked;

            actionBar.setTitle(getString(R.string.actionShop));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionAccount))) {
            if (ConstantValues.IS_USER_LOGGED_IN) {
                mSelectedItem = selectedItem;
                if (update_account == null) {
                    // Navigate to Update_Account Fragment
                    update_account = new Update_Account();
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, update_account)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    fragmentManager.beginTransaction().hide(currentFragment).show(update_account).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                }
                currentFragment = update_account;

                actionBar.setTitle(getString(R.string.actionAccount));

            } else {
                // Navigate to Login Activity
                startActivity(new Intent(BuyInputsActivity.this, Login.class));
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionOrders))) {
            if (ConstantValues.IS_USER_LOGGED_IN) {
                mSelectedItem = selectedItem;

                if (myOrders == null) {
                    myOrders = new My_Orders();
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, myOrders)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    fragmentManager.beginTransaction().hide(currentFragment).show(myOrders).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                }
                currentFragment = myOrders;

                actionBar.setTitle(getString(R.string.actionOrders));

            } else {
                // Navigate to Login Activity
                startActivity(new Intent(BuyInputsActivity.this, Login.class));
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }

        }
        else if (selectedItem.equalsIgnoreCase(getString(R.string.actionAddresses))) {
            if (ConstantValues.IS_USER_LOGGED_IN) {
                mSelectedItem = selectedItem;

                if (myAddresses == null) {
                    myAddresses = new My_Addresses(null);
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, myAddresses)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    fragmentManager.beginTransaction().hide(currentFragment).show(myAddresses).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                }
                currentFragment = myAddresses;

                actionBar.setTitle(getString(R.string.actionAddresses));

            } else {
                // Navigate to Login Activity
                startActivity(new Intent(BuyInputsActivity.this, Login.class));
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionFavourites))) {
            if (ConstantValues.IS_USER_LOGGED_IN) {
                mSelectedItem = selectedItem;

                if (myfavorites == null) {
                    myfavorites = new WishList();
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment, myfavorites)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    fragmentManager.beginTransaction().hide(currentFragment).show(myfavorites).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                }
                currentFragment = myfavorites;

                actionBar.setTitle(getString(R.string.actionFavourites));

            } else {
                // Navigate to Login Activity
                startActivity(new Intent(BuyInputsActivity.this, Login.class));
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionShareApp))) {
            mSelectedItem = selectedItem;

            // Share App with the help of static method of Utilities class
            Utilities.shareMyApp(BuyInputsActivity.this);



        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionRateApp))) {
            mSelectedItem = selectedItem;

            // Rate App with the help of static method of Utilities class
//            Utilities.rateMyApp(BuyInputsActivity.this);



        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionSettings))) {
            mSelectedItem = selectedItem;

            // Navigate to SettingsFragment Fragment
            if (settings == null) {
                settings = new SettingsFragment();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, settings)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(settings).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = settings;

            actionBar.setTitle(getString(R.string.actionSettings));

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionMe))) {
            mSelectedItem = selectedItem;

            // Navigate to SettingsFragment Fragment
            if (meFragment == null) {
                meFragment = new MeFragment();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment, meFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            } else {
                fragmentManager.beginTransaction().hide(currentFragment).show(meFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
            currentFragment = meFragment;


        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionLogin))) {
            mSelectedItem = selectedItem;

            // Navigate to Login Activity
            startActivity(new Intent(BuyInputsActivity.this, Login.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);

        } else if (selectedItem.equalsIgnoreCase(getString(R.string.actionLogout))) {
            mSelectedItem = selectedItem;

            // Edit UserID in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("userID", "");
            editor.apply();

            // Set UserLoggedIn in MyAppPrefsManager
            MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(this);
            myAppPrefsManager.setUserLoggedIn(false);

            // Set isLogged_in of ConstantValues
            ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();

            FirebaseAuth.getInstance().signOut();

            // Navigate to Login Activity
            startActivity(new Intent(BuyInputsActivity.this, Login.class));
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
        }

    }


    private void setupDefaultHomePage(String defaultHome) {
        homePage_9 = new HomePage_9();
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, homePage_9, getString(R.string.homeStyle9)).commit();
        currentFragment = homePage_9;
        defaultHomeFragment = homePage_9;

    }

    private void showHomePage() {
        getSupportFragmentManager().beginTransaction().hide(currentFragment).show(defaultHomeFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        currentFragment = defaultHomeFragment;
        if (ConstantValues.NAVIGATION_STYLE.equals("bottom")) {
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
        }
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
        MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
        MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);

        languageItem.setVisible(false);
        currencyItem.setVisible(false);


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
        Utilities.tintMenuIcon(BuyInputsActivity.this, languageItem, R.color.white);
        Utilities.tintMenuIcon(BuyInputsActivity.this, searchItem, R.color.white);
        Utilities.tintMenuIcon(BuyInputsActivity.this, cartItem, R.color.white);

        return super.onCreateOptionsMenu(menu);
    }


    //*********** Prepares the OptionsMenu of Toolbar ********//

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (FLAG_FAVORITE) {
            MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
            MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
            MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
            MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
            MenuItem youtubeItem = menu.findItem(R.id.toolbar_ic_favorite);

            youtubeItem.setVisible(true);
            languageItem.setVisible(false);
            searchItem.setVisible(false);
            cartItem.setVisible(false);
            currencyItem.setVisible(false);
            // FLAG_FAVORITE = false;
        }


        if (FLAG_YOUTUBE) {
            MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
            MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
            MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
            MenuItem youtubeItem = menu.findItem(R.id.toolbar_ic_favorite);
            MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
            youtubeItem.setVisible(true);
            languageItem.setVisible(false);
            currencyItem.setVisible(false);
            searchItem.setVisible(false);
            cartItem.setVisible(false);
            FLAG_YOUTUBE = false;
        } else {


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

            case R.id.toolbar_ic_language:
                // Navigate to Languages Fragment
                fragment = new Languages();
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                break;

            case R.id.toolbar_ic_currency:

                

                // Navigate to Currency Fragment
                fragment = new CurrencyFrag();
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();

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

            case R.id.toolbar_ic_favorite:
                // Navigate to My_Cart Fragment
                /*fragment = new FavFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();

                FLAG_FAVORITE = true;
                */

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


    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    @SuppressLint("RestrictedApi")
    public void enableBottomNavigation(boolean isEnabled) {
        if (isEnabled) {
            if (bottomNavigationView.getVisibility() == View.GONE)
                bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            if (bottomNavigationView.getVisibility() == View.VISIBLE)
                bottomNavigationView.setVisibility(View.GONE);
        }

        if (!ConstantValues.IS_CLIENT_ACTIVE)
            floatingActionButton.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
    }

    public void toggleNavigaiton(boolean isEnabled) {
        if (ConstantValues.NAVIGATION_STYLE.equals("side")) {
            setDrawerEnabled(isEnabled);
        } else {
            enableBottomNavigation(isEnabled);

        }
    }

    public void clearBackStackInclusive(String tag) {
        getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}

