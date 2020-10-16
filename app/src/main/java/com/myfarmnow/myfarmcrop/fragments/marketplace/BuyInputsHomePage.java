package com.myfarmnow.myfarmcrop.fragments.marketplace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters.CategoryListAdapter_3;
import com.myfarmnow.myfarmcrop.app.CropManagerApp;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.customs.DialogLoader;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.database.User_Cart_BuyInputsDB;
import com.myfarmnow.myfarmcrop.fragments.AccountFragment;
import com.myfarmnow.myfarmcrop.fragments.HomeFragment;
import com.myfarmnow.myfarmcrop.fragments.OffersFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.BannerStyle1;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Categories_3;
import com.myfarmnow.myfarmcrop.models.banner_model.BannerDetails;
import com.myfarmnow.myfarmcrop.models.cart_model.CartProduct;
import com.myfarmnow.myfarmcrop.models.category_model.CategoryDetails;
import com.myfarmnow.myfarmcrop.network.StartAppRequests;
import com.myfarmnow.myfarmcrop.utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import am.appwise.components.ni.NoInternetDialog;

public class BuyInputsHomePage extends Fragment {
    private static final String TAG = "BuyInputsHomePage";
    StartAppRequests startAppRequests;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    FragmentManager fragmentManager;
    private Context context;
    private Toolbar toolbar;
    @SuppressLint("StaticFieldLeak")
    public static EditText searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buy_inputs_home, container, false);

        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getString(R.string.app_name));
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setElevation(0.5f);

        toolbar = view.findViewById(R.id.buy_inputs_home_toolbar);
        searchView = view.findViewById(R.id.buy_inputs_search_view);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.btm_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();
        startAppRequests = new StartAppRequests(requireContext());

        // Get BannersList from ApplicationContext
        bannerImages = ((CropManagerApp) requireContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((CropManagerApp) requireContext().getApplicationContext()).getCategoriesList();

        // Binding Layout View

        if (bannerImages.isEmpty() || allCategoriesList.isEmpty())
            new MyTask().execute();
        else
            continueSetup();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void continueSetup() {
        bannerImages = ((CropManagerApp) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((CropManagerApp) getContext().getApplicationContext()).getCategoriesList();
        // Setup BannerSlider
        setupBannerSlider(bannerImages);

        // Add corresponding ViewPagers to TabLayouts
        fragmentManager = getFragmentManager();

        Bundle categoryBundle = new Bundle();
        categoryBundle.putBoolean("isHeaderVisible", false);
        categoryBundle.putBoolean("isMenuItem", false);
        categoryBundle.putBoolean("home_9", true);
        Fragment categories = new Categories_3();
        categories.setArguments(categoryBundle);
        fragmentManager.beginTransaction().replace(R.id.home9_categories, categories).commit();

        Bundle bundleInfo = new Bundle();
        bundleInfo.putString("sortBy", "Newest");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_ic_search:
                Toast.makeText(context, "Search", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //*********** Setup the BannerSlider with the given List of BannerImages ********//

    private void setupBannerSlider(final List<BannerDetails> bannerImages) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment bannerStyle = null;

        switch (ConstantValues.DEFAULT_BANNER_STYLE) {
            case 0:
            case 1:
                bannerStyle = new BannerStyle1(bannerImages, allCategoriesList);
                break;
        }

        if (bannerStyle != null) {
            assert fragmentManager != null;
            fragmentManager.beginTransaction().replace(R.id.bannerFrameHome9, bannerStyle).commit();
        }
    }

    private class MyTask extends AsyncTask<String, Void, String> {

        DialogLoader dialogLoader = new DialogLoader(getContext());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoader.showProgressDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            // Check for Internet Connection from the static method of Helper class
            if (Utilities.hasActiveInternetConnection(getContext())) {
                // Call the method of StartAppRequests class to process App Startup Requests
                startAppRequests.RequestBanners();
                startAppRequests.RequestAllCategories();
                return "1";
            } else {
                return "0";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equalsIgnoreCase("1")) {
                continueSetup();
                dialogLoader.hideProgressDialog();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Bind Menu Items
        MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
        MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
        MenuItem profileItem = menu.findItem(R.id.toolbar_edit_profile);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);

        profileItem.setVisible(false);
        languageItem.setVisible(false);
        currencyItem.setVisible(false);
        searchItem.setVisible(true);
        cartItem.setVisible(true);

        //set badge value
        User_Cart_BuyInputsDB user_cart_BuyInputs_db = new User_Cart_BuyInputsDB();
        List<CartProduct> cartItemsList;
        cartItemsList = user_cart_BuyInputs_db.getCartItems();
        TextView badge = (TextView) cartItem.getActionView().findViewById(R.id.cart_badge);
        badge.setText(String.valueOf(cartItemsList.size()));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.page_1:
                selectedFragment = new HomeFragment(context, requireActivity().getSupportFragmentManager(), MyFarmDbHandlerSingleton.getHandlerInstance(context));
                break;
            case R.id.page_2:
                selectedFragment = new OffersFragment();
                break;
            case R.id.page_3:
                selectedFragment = new AccountFragment();
                break;
        }

        assert selectedFragment != null;
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, selectedFragment).commit();
        return true;
    };
}

