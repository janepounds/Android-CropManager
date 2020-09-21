package com.myfarmnow.myfarmcrop.fragments.buyInputsFragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.app.CropManagerApp;
import com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters.ProductAdapterRemovable;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.customs.DialogLoader;
import com.myfarmnow.myfarmcrop.customs.EndlessRecyclerViewScroll;
import com.myfarmnow.myfarmcrop.models.product_model.GetAllProducts;
import com.myfarmnow.myfarmcrop.models.product_model.ProductData;
import com.myfarmnow.myfarmcrop.models.product_model.ProductDetails;
import com.google.android.material.snackbar.Snackbar;
import com.myfarmnow.myfarmcrop.network.BuyInputsAPIClient;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class WishList extends Fragment {

    View rootView;
    int pageNo = 0;
    String customerID;

    LinearLayout bottomBar;
    TextView emptyRecord;
    TextView sortListText;
    ProgressBar progressBar, mainProgress;
    ToggleButton filterButton;
    ToggleButton toggleLayoutView;
    RecyclerView favourites_recycler;

    GridLayoutManager gridLayoutManager;
    ProductAdapterRemovable productAdapter;

    List<ProductDetails> favouriteProductsList = new ArrayList<>();

    DialogLoader dialogLoader;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            pageNo = 0;
            favouriteProductsList.clear();
            dialogLoader.showProgressDialog();
            RequestWishList(pageNo);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.buy_inputs_f_products_vertical, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionFavourites));

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
       // noInternetDialog.show();

        // Get the CustomerID from SharedPreferences
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        bottomBar = rootView.findViewById(R.id .bottomBar);
        sortListText = rootView.findViewById(R.id.sort_text);
        emptyRecord = rootView.findViewById(R.id.empty_record);
        progressBar = rootView.findViewById(R.id.loading_bar);
        filterButton = rootView.findViewById(R.id.filterBtn);
        toggleLayoutView = rootView.findViewById(R.id.layout_toggleBtn);
        favourites_recycler = rootView.findViewById(R.id.products_recycler);
        mainProgress = rootView.findViewById(R.id.progressBar);


        // Hide some of the Views
        bottomBar.setVisibility(View.GONE);
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        filterButton.setVisibility(View.GONE);
        mainProgress.setVisibility(View.GONE);


        favouriteProductsList = new ArrayList<>();


        dialogLoader = new DialogLoader(getContext());
        dialogLoader.showProgressDialog();

        favouriteProductsList.clear();
        // Request for User's Favourite Products
        RequestWishList(pageNo);


        // Initialize the ProductAdapter and GridLayoutManager for RecyclerView
        productAdapter = new ProductAdapterRemovable(getContext(), favouriteProductsList, false, false, emptyRecord);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        
        // Set the Adapter and LayoutManager to the RecyclerView
        favourites_recycler.setAdapter(productAdapter);
        favourites_recycler.setLayoutManager(gridLayoutManager);


        // Handle Scroll event of the RecyclerView
        favourites_recycler.addOnScrollListener(new EndlessRecyclerViewScroll() {
            @Override
            public void onLoadMore(int current_page) {
                progressBar.setVisibility(View.VISIBLE);

                // Execute LoadMoreTask
                new LoadMoreTask(current_page).execute();
            }

        });


        return rootView;
    }



    //*********** Adds Products returned from the Server to the FavouriteProductsList ********//

    private void addProducts(ProductData productData) {

        // Add Products to favouriteProductsList from the List of ProductData
        for (int i = 0; i < productData.getProductData().size(); i++) {
            favouriteProductsList.add(productData.getProductData().get(i));
        }

        productAdapter.notifyDataSetChanged();


        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (productAdapter.getItemCount() == 0) {
            emptyRecord.setVisibility(View.VISIBLE);
        }
        else {
            emptyRecord.setVisibility(View.GONE);
        }
    }



    //*********** Request User's Favorited Products from the Server based on PageNo. ********//

    public void RequestWishList(int pageNumber) {

        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(pageNumber);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setType("wishlist");
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);


        Call<ProductData> call = BuyInputsAPIClient.getInstance()
                .getAllProducts
                        (
                                getAllProducts
                        );

        call.enqueue(new Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, retrofit2.Response<ProductData> response) {

                dialogLoader.hideProgressDialog();

                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        
                        // Products have been returned. Add Products to the favouriteProductsList
                        addProducts(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        addProducts(response.body());
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();
    
                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
    
                    progressBar.setVisibility(View.GONE);
    
                }
                else {
                    Toast.makeText(CropManagerApp.getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(CropManagerApp.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    /*********** LoadMoreTask Used to Load more Products from the Server in the Background Thread using AsyncTask ********/

    private class LoadMoreTask extends AsyncTask<String, Void, String> {

        int page_number;

        private LoadMoreTask(int page_number) {
            this.page_number = page_number;
        }


        //*********** Runs on the UI thread before #doInBackground() ********//

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//

        @Override
        protected String doInBackground(String... params) {

            // Request for User's Favourite Products
            RequestWishList(page_number);

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}