package com.myfarmnow.myfarmcrop.fragments.buyInputsFragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters.ProductAdapter;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.customs.DialogLoader;
import com.myfarmnow.myfarmcrop.customs.EndlessRecyclerViewScroll;
import com.myfarmnow.myfarmcrop.models.product_model.GetAllProducts;
import com.myfarmnow.myfarmcrop.models.product_model.ProductData;
import com.myfarmnow.myfarmcrop.models.product_model.ProductDetails;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.myfarmnow.myfarmcrop.network.BuyInputsAPIClient;

import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Callback;


public class All_Products extends Fragment {

    View rootView;
    
    int pageNo = 0;
    boolean isGridView;
    boolean isHideBottomBar;
    String customerID;
    String sortBy = "Newest";

    LinearLayout bottomBar;
    LinearLayout sortList;
    TextView emptyRecord;
    TextView sortListText;
    ProgressBar progressBar, mainProgress;
    ToggleButton filterButton;
    ToggleButton toggleLayoutView;
    RecyclerView all_products_recycler;


    ProductAdapter productAdapter;
    List<ProductDetails> productsList;
    
    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    DialogLoader dialogLoader;

    public void invalidateProducts(){
        productAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.buy_inputs_f_products_vertical, container, false);

        dialogLoader = new DialogLoader(getContext());

        if (getArguments() != null) {
            if (getArguments().containsKey("sortBy")) {
                sortBy = getArguments().getString("sortBy");
            }
            if (getArguments().containsKey("hideBottomBar")) {
                isHideBottomBar = getArguments().getBoolean("hideBottomBar");
            }
        }

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        // Get the Customer's ID from SharedPreferences
        customerID = getActivity().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");
        
        // Binding Layout Views
        bottomBar = rootView.findViewById(R.id.bottomBar);
        sortList = rootView.findViewById(R.id.sort_list);
        sortListText = rootView.findViewById(R.id.sort_text);
        emptyRecord = rootView.findViewById(R.id.empty_record);
        progressBar = rootView.findViewById(R.id.loading_bar);
        filterButton = rootView.findViewById(R.id.filterBtn);
        toggleLayoutView = rootView.findViewById(R.id.layout_toggleBtn);
        all_products_recycler = rootView.findViewById(R.id.products_recycler);
        mainProgress = rootView.findViewById(R.id.progressBar);

        // Hide some of the Views
        filterButton.setVisibility(View.GONE);
        emptyRecord.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        
        isGridView = true;
        toggleLayoutView.setChecked(isGridView);

        bottomBar.setVisibility(isHideBottomBar ? View.GONE : View.VISIBLE);

        // Set sortListText text
        if (sortBy.equalsIgnoreCase("top seller")) {
            sortListText.setText(getString(R.string.top_seller));
        } else if (sortBy.equalsIgnoreCase("special")) {
            sortListText.setText(getString(R.string.super_deals));
        } else if (sortBy.equalsIgnoreCase("most liked")) {
            sortListText.setText(getString(R.string.most_liked));
        } else {
            sortListText.setText(getString(R.string.newest));
        }

        
        productsList = new ArrayList<>();

        // Request for Products based on PageNo.
        RequestAllProducts(pageNo, sortBy);


        // Initialize GridLayoutManager and LinearLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        linearLayoutManager = new LinearLayoutManager(getContext());


        // Initialize the ProductAdapter for RecyclerView
        productAdapter = new ProductAdapter(getContext(), productsList, false,false);
        
        setRecyclerViewLayoutManager(isGridView);
        all_products_recycler.setAdapter(productAdapter);


        // Handle the Scroll event of Product's RecyclerView
        all_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
            @Override
            public void onLoadMore(int current_page) {
                progressBar.setVisibility(View.VISIBLE);
                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                new LoadMoreTask(current_page).execute();
            }
        });

        productAdapter.notifyDataSetChanged();
    
    
        // Toggle RecyclerView's LayoutManager
        toggleLayoutView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isGridView = isChecked;
                setRecyclerViewLayoutManager(isGridView);
            }
        });
        

        sortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get sortBy_array from String_Resources
                final String[] sortArray = getResources().getStringArray(R.array.sortBy_array);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setCancelable(true);
                
                dialog.setItems(sortArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        
                        String selectedText = sortArray[which];
                        sortListText.setText(selectedText);
                        
                        
                        if (selectedText.equalsIgnoreCase(sortArray[0])) {
                            sortBy = "Newest";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[1])) {
                            sortBy = "a to z";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[2])) {
                            sortBy = "z to a";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[3])) {
                            sortBy = "high to low";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[4])) {
                            sortBy = "low to high";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[5])) {
                            sortBy = "top seller";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[6])) {
                            sortBy = "special";
                        }
                        else if (selectedText.equalsIgnoreCase(sortArray[7])) {
                            sortBy = "most liked";
                        }
                        else {
                            sortBy = "Newest";
                        }
                        

                        productsList.clear();
                        // Request for Products based on sortBy
                        RequestAllProducts(pageNo, sortBy);
                        dialog.dismiss();


                        // Handle the Scroll event of Product's RecyclerView
                        all_products_recycler.addOnScrollListener(new EndlessRecyclerViewScroll(bottomBar) {
                            // Override abstract method onLoadMore() of EndlessRecyclerViewScroll class
                            @Override
                            public void onLoadMore(int current_page) {
//                                progressBar.setVisibility(View.VISIBLE);
                                // Execute AsyncTask LoadMoreTask to Load More Products from Server
                                new LoadMoreTask(current_page).execute();
                            }
                        });

                    }
                });
                dialog.show();
            }
        });
        
        return rootView;
        
    }
    
    //*********** Switch RecyclerView's LayoutManager ********//
    
    public void setRecyclerViewLayoutManager(Boolean isGridView) {
        int scrollPosition = 0;
        
        // If a LayoutManager has already been set, get current Scroll Position
        if (all_products_recycler.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) all_products_recycler.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }
        
        productAdapter.toggleLayout(isGridView);
        
        all_products_recycler.setLayoutManager(isGridView ? gridLayoutManager : linearLayoutManager);
        all_products_recycler.setAdapter(productAdapter);
        
        all_products_recycler.scrollToPosition(scrollPosition);
    }
    
    //*********** Adds Products returned from the Server to the ProductsList ********//

    private void addProducts(ProductData productData) {
        
        // Add Products to ProductsList from the List of ProductData
        for (int i = 0; i < productData.getProductData().size(); i++) {
            ProductDetails productDetails = productData.getProductData().get(i);
            productsList.add(productDetails);
        }

        productAdapter.notifyDataSetChanged();
        
        // Change the Visibility of emptyRecord Text based on ProductList's Size
        if (productAdapter.getItemCount() == 0) {
            emptyRecord.setVisibility(View.VISIBLE);
        } else {
            emptyRecord.setVisibility(View.GONE);
        }
        
    }
    
    //*********** Request Products from the Server based on PageNo. ********//

    public void RequestAllProducts(int pageNumber, String sortBy) {

        GetAllProducts getAllProducts = new GetAllProducts();
        getAllProducts.setPageNumber(pageNumber);
        getAllProducts.setLanguageId(ConstantValues.LANGUAGE_ID);
        getAllProducts.setCustomersId(customerID);
        getAllProducts.setType(sortBy);
        getAllProducts.setCurrencyCode(ConstantValues.CURRENCY_CODE);

        String str = new Gson().toJson(getAllProducts);
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
                        // Products have been returned. Add Products to the ProductsList
                       addProducts(response.body());

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Products haven't been returned. Call the method to process some implementations
                        addProducts(response.body());
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                    else {
                        // Unable to get Success status
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }

                    // Hide the ProgressBar
                    progressBar.setVisibility(View.GONE);
                    mainProgress.setVisibility(View.GONE);
                }
                else {
                   Toast.makeText(getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                mainProgress.setVisibility(View.GONE);
               // Toast.makeText(App.getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
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
            dialogLoader.showProgressDialog();

        }


        //*********** Performs some Processes on Background Thread and Returns a specified Result  ********//

        @Override
        protected String doInBackground(String... params) {

            // Request for Products based on PageNo.
            RequestAllProducts(page_number, sortBy);

            return "All Done!";
        }


        //*********** Runs on the UI thread after #doInBackground() ********//

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialogLoader.hideProgressDialog();
        }
    }
}