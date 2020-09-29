package com.myfarmnow.myfarmcrop.fragments.buyInputsFragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.WrappedDrawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.app.CropManagerApp;
import com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters.ViewPagerCustomAdapter;
import com.myfarmnow.myfarmcrop.customs.NotificationBadger;
import com.myfarmnow.myfarmcrop.models.category_model.CategoryDetails;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Products extends Fragment {

    String sortBy = "Newest";
    boolean isMenuItem = false;
    boolean isSubFragment = false;

    int selectedTabIndex = 0;
    String selectedTabText = "";

    TabLayout product_tabs;
    ViewPager product_viewpager;
    TextView category_title;

    ViewPagerCustomAdapter viewPagerCustomAdapter;

    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    List<CategoryDetails> allSubCategoriesList = new ArrayList<>();
    List<CategoryDetails> finalCategoriesList = new ArrayList<>();

    public void invalidateProducts(){
        viewPagerCustomAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Get CategoriesList from AppContext
        allCategoriesList = ((CropManagerApp) getContext().getApplicationContext()).getCategoriesList();
        ((DashboardActivity)getActivity()).currentFragment=getActivity().getSupportFragmentManager().getPrimaryNavigationFragment();
        allSubCategoriesList = new ArrayList<>();

        // Get SubCategoriesList from AllCategoriesList
        for (int i=0;  i<allCategoriesList.size();  i++) {
            if (!allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
                allSubCategoriesList.add(allCategoriesList.get(i));
            }
        }

        finalCategoriesList = new ArrayList<>();
        finalCategoriesList.addAll(allCategoriesList);
        finalCategoriesList.addAll(allSubCategoriesList);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buy_inputs_products, container, false);
        category_title= rootView.findViewById(R.id.txt_category_title);

        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy", "Newest");
            }

            if (getArguments().containsKey("isMenuItem")) {
                isMenuItem = getArguments().getBoolean("isMenuItem", false);
            }

            if (getArguments().containsKey("isSubFragment")) {
                isSubFragment = getArguments().getBoolean("isSubFragment", false);
            }

            if (getArguments().containsKey("CategoryName")) {
                selectedTabText = getArguments().getString("CategoryName", "Category");
                category_title.setText(selectedTabText);
            }
        }


        // Toggle Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        if (!isSubFragment) {

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionShop));
        }


        // Binding Layout Views
        product_tabs = rootView.findViewById(R.id.product_tabs);
        product_viewpager = rootView.findViewById(R.id.product_viewpager);


        // Setup ViewPagerAdapter
        setupViewPagerAdapter();


        product_viewpager.setOffscreenPageLimit(0);
        product_viewpager.setAdapter(viewPagerCustomAdapter);

        // Add corresponding ViewPagers to TabLayouts
        product_tabs.setupWithViewPager(product_viewpager);

        
        try {
            product_tabs.getTabAt(selectedTabIndex).select();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        

        return rootView;

    }



    //*********** Setup the ViewPagerAdapter ********//

    private void setupViewPagerAdapter() {

        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        viewPagerCustomAdapter = new ViewPagerCustomAdapter(getChildFragmentManager());

        // Initialize All_Products Fragment with specified arguments
        Bundle bundleInfo = new Bundle();
        bundleInfo.putString("sortBy", sortBy);

        // Add the Fragments to the ViewPagerAdapter with TabHeader


        for (int i=0;  i < finalCategoriesList.size();  i++) {

            // Initialize Category_Products Fragment with specified arguments
            Fragment categoryProducts = new Category_Products();
            Bundle categoryInfo = new Bundle();
            categoryInfo.putString("sortBy", sortBy);
            categoryInfo.putInt("CategoryID", Integer.parseInt(finalCategoriesList.get(i).getId()));
            categoryProducts.setArguments(categoryInfo);

            // Add the Fragments to the ViewPagerAdapter with TabHeader
            viewPagerCustomAdapter.addFragment(categoryProducts, finalCategoriesList.get(i).getName());


            if (selectedTabText.equalsIgnoreCase(finalCategoriesList.get(i).getName())) {
                selectedTabIndex = i + 1;
            }
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Hide Cart Icon in the Toolbar
        MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
        MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
        MenuItem profileItem = menu.findItem(R.id.toolbar_edit_profile);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
        profileItem.setVisible(false);
        languageItem.setVisible(false);
        currencyItem.setVisible(false);
        searchItem.setVisible(false);
        cartItem.setVisible(true);
    }



}

