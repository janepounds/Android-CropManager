package com.myfarmnow.myfarmcrop.fragments.buyInputsFragments;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters.ProductAdapterRemovable;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.database.User_Recents_BuyInputsDB;
import com.myfarmnow.myfarmcrop.models.product_model.GetAllProducts;
import com.myfarmnow.myfarmcrop.models.product_model.ProductData;
import com.myfarmnow.myfarmcrop.models.product_model.ProductDetails;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.myfarmnow.myfarmcrop.network.BuyInputsAPIClient;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class RecentlyViewed extends Fragment {

    String customerID;

    TextView emptyRecord, headerText;
    RecyclerView recents_recycler;

    ProductAdapterRemovable productAdapter;
    User_Recents_BuyInputsDB recents_db = new User_Recents_BuyInputsDB();
    ShimmerFrameLayout shimmerFrameLayout;
    ArrayList<Integer> recents;
    List<ProductDetails> recentViewedList;
    ProgressBar progressBar;

    public void invalidateProducts(){

        recents = recents_db.getUserRecents();

        if (recents.size() < 1 ) {
            headerText.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
        else {
            headerText.setVisibility(View.VISIBLE);
            recentViewedList.clear();
            for (int i=0;  i<recents.size();  i++) {
                // Request current Product's Details
                RequestProductDetails(recents.get(i));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buy_inputs_f_products_horizontal, container, false);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
       // noInternetDialog.show();

        recents = new ArrayList<>();
        recentViewedList  = new ArrayList<>();

        // Get the List of RecentlyViewed Product's IDs from the Local Databases User_Recents_DB
        recents = recents_db.getUserRecents();

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString(DashboardActivity.PREFERENCES_USER_ID, "");


        // Binding Layout Views
        emptyRecord = rootView.findViewById(R.id.empty_record_text);
        headerText = rootView.findViewById(R.id.products_horizontal_header);
        recents_recycler = rootView.findViewById(R.id.products_horizontal_recycler);
        progressBar = rootView.findViewById(R.id.progressBar);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerFrame);

        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        // Set text of Header
        headerText.setText(getString(R.string.recentlyViewed));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            headerText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_recents, 0, 0, 0);
        } else {
            headerText.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_recents), null, null, null);
        }
    
    
        // Initialize the ProductAdapterRemovable and LayoutManager for RecyclerView
        productAdapter = new ProductAdapterRemovable(getContext(), recentViewedList, true, true, headerText);
        recents_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Set the Adapter and LayoutManager to the RecyclerView
        recents_recycler.setAdapter(productAdapter);
        

        // Check if the recents List isn't empty
        if (recents.size() < 1 ) {
            headerText.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
        else {
            headerText.setVisibility(View.VISIBLE);
            recentViewedList.clear();
            for (int i=0;  i<recents.size();  i++) {
                // Request current Product's Details
                RequestProductDetails(recents.get(i));
            }
        }


        return rootView;
    }



    //*********** Adds Products returned from the Server to the RecentViewedList ********//

    private void addRecentProducts(ProductData productData) {

        // Add Products to recentViewedList
        if (productData.getProductData().size() > 0 ) {
            recentViewedList.add(productData.getProductData().get(0));
        }

        // Notify the Adapter
        productAdapter.notifyDataSetChanged();
    }



    //*********** Request the Product's Details from the Server based on Product's ID ********//

    public void RequestProductDetails(final int products_id) {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(0);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setProductsId(String.valueOf(products_id));
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);


        Call<ProductData> call = BuyInputsAPIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );

        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                
                if (response.isSuccessful()) {
                    
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        // Product's Details has been returned.
                        // Add Product to the recentViewedList
                        addRecentProducts(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Product's Details haven't been returned.
                        // Call the method to process some implementations
                        addRecentProducts(response.body());

                    }
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                Toast.makeText(getActivity(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
        
    }

}

