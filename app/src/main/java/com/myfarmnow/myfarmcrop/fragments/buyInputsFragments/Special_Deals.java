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
import com.myfarmnow.myfarmcrop.app.CropManagerApp;
import com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters.ProductAdapter;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.models.product_model.GetAllProducts;
import com.myfarmnow.myfarmcrop.models.product_model.ProductData;
import com.myfarmnow.myfarmcrop.models.product_model.ProductDetails;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.myfarmnow.myfarmcrop.network.BuyInputsAPIClient;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class Special_Deals extends Fragment {

    String customerID;
    Boolean isHeaderVisible;
    Call<ProductData> networkCall;

    TextView emptyRecord, headerText;
    RecyclerView super_deals_recycler;
    ShimmerFrameLayout shimmerFrameLayout;

    ProductAdapter productAdapter;

    List<ProductDetails> dealProductsList;
    ProgressBar progressBar;


    public void invalidateProducts(){
        productAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buy_inputs_f_products_horizontal, container, false);

        // Get the Boolean from Bundle Arguments
        isHeaderVisible = getArguments().getBoolean("isHeaderVisible");

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();


        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        emptyRecord = rootView.findViewById(R.id.empty_record_text);
        headerText = rootView.findViewById(R.id.products_horizontal_header);
        super_deals_recycler = rootView.findViewById(R.id.products_horizontal_recycler);
        progressBar = rootView.findViewById(R.id.progressBar);
        shimmerFrameLayout = rootView.findViewById(R.id.shimmerFrame);
    
        // Hide some of the Views
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    
        // Check if Header must be Invisible or not
        if (!isHeaderVisible) {
            headerText.setVisibility(View.GONE);
        } else {
            headerText.setText(getString(R.string.super_deals));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                headerText.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_star_circle, 0, 0, 0);
            } else {
                headerText.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_star_circle), null, null, null);
            }
        }
    
    
        dealProductsList = new ArrayList<>();
    
    
        // RecyclerView has fixed Size
        super_deals_recycler.setHasFixedSize(true);
        super_deals_recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    
        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), dealProductsList, true,false);
    
        // Set the Adapter and LayoutManager to the RecyclerView
        super_deals_recycler.setAdapter(productAdapter);
    
    
        // Request for Most Sold Products
        RequestSpecialDeals();


        return rootView;

    }



    //*********** Adds Products returned from the Server to the DealProductsList ********//

    private void addProducts(ProductData productData) {

        // Add Products to dealProductsList
        if (productData.getProductData().size() > 0) {
            dealProductsList.addAll(productData.getProductData());
        } else {
            headerText.setVisibility(View.GONE);
        }
        productAdapter.notifyDataSetChanged();
    }



    //*********** Request all the Products from the Server based on Product's Deals ********//

    public void RequestSpecialDeals() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(0);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setType("special");
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);


        networkCall = BuyInputsAPIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );

        networkCall.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {
                
                if (response.isSuccessful()) {
                    
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        // Products have been returned. Add Products to the dealProductsList
                        addProducts(response.body());
                        emptyRecord.setVisibility(View.GONE);

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Products haven't been returned
                        emptyRecord.setVisibility(View.VISIBLE);
                        if (isHeaderVisible){
                            emptyRecord.setVisibility(View.GONE);
                            headerText.setVisibility(View.GONE);
                        }

                    }
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                if (!networkCall.isCanceled()) {
                    Toast.makeText(CropManagerApp.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                }
            }
        });
    }



    @Override
    public void onPause() {

        // Check if NetworkCall is being executed
        if (networkCall.isExecuted()){
            // Cancel the NetworkCall
            networkCall.cancel();
        }

        super.onPause();
    }
}
