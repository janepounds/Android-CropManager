package com.myfarmnow.myfarmcrop.fragments.buyInputsFragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.Login;
import com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters.CartItemsAdapter;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.customs.DialogLoader;
import com.myfarmnow.myfarmcrop.database.User_Cart_BuyInputsDB;
import com.myfarmnow.myfarmcrop.models.cart_model.CartProduct;
import com.myfarmnow.myfarmcrop.models.cart_model.CartProductAttributes;
import com.myfarmnow.myfarmcrop.models.product_model.GetAllProducts;
import com.myfarmnow.myfarmcrop.models.product_model.GetStock;
import com.myfarmnow.myfarmcrop.models.product_model.ProductData;
import com.myfarmnow.myfarmcrop.models.product_model.ProductDetails;
import com.myfarmnow.myfarmcrop.models.product_model.ProductStock;
import com.myfarmnow.myfarmcrop.network.BuyInputsAPIClient;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import am.appwise.components.ni.NoInternetDialog;
import retrofit2.Call;
import retrofit2.Response;

public class My_Cart extends Fragment {
    private static final String TAG = "My_Cart";
    String customerID;
    RecyclerView cart_items_recycler;
    LinearLayout cart_view, cart_view_empty;
    Button cart_checkout_btn, continue_shopping_btn, clear_cart;
    NestedScrollView mainRvLayout;
    public TextView cart_item_subtotal_price, cart_item_discount_price, cart_item_total_price;

    CartItemsAdapter cartItemsAdapter;
    User_Cart_BuyInputsDB user_cart_BuyInputs_db = new User_Cart_BuyInputsDB();

    List<CartProduct> cartItemsList = new ArrayList<>();
    List<CartProduct> finalCartItemsList = new ArrayList<>();
    List<ProductDetails> cartProducts = new ArrayList<>();
    List<String> stocks = new ArrayList<>();
    DialogLoader dialogLoader;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(getString(R.string.actionCart));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buy_inputs_my_cart, container, false);

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        setHasOptionsMenu(true);
        dialogLoader = new DialogLoader(getContext());

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        //MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(getString(R.string.actionCart));
        customerID = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString(DashboardActivity.PREFERENCES_USER_ID, "");
        // Get the List of Cart Items from the Local Databases User_Cart_DB
        finalCartItemsList = cartItemsList = user_cart_BuyInputs_db.getCartItems();

        // Binding Layout Views
        cart_view = rootView.findViewById(R.id.cart_view);
        cart_checkout_btn = rootView.findViewById(R.id.cart_checkout_btn);
        cart_items_recycler = rootView.findViewById(R.id.cart_items_recycler);
        cart_view_empty = rootView.findViewById(R.id.cart_view_empty);
        continue_shopping_btn = rootView.findViewById(R.id.continue_shopping_btn);
        mainRvLayout = rootView.findViewById(R.id.mainRvLayout);
//        cart_item_subtotal_price = rootView.findViewById(R.id.cart_item_subtotal_price);
        cart_item_total_price = rootView.findViewById(R.id.cart_item_total_price);
        clear_cart = rootView.findViewById(R.id.btn_clear_cart);
//        cart_item_discount_price = rootView.findViewById(R.id.cart_item_discount_price);

        // Change the Visibility of cart_view and cart_view_empty LinearLayout based on CartItemsList's Size
        if (cartItemsList.size() != 0) {
            cart_view.setVisibility(View.VISIBLE);

            cart_view_empty.setVisibility(View.GONE);
        } else {
            cart_view.setVisibility(View.GONE);
            cart_view_empty.setVisibility(View.VISIBLE);
        }

        // Initialize the AddressListAdapter for RecyclerView
        cartItemsAdapter = new CartItemsAdapter(getContext(), finalCartItemsList, My_Cart.this);

        // Set the Adapter and LayoutManager to the RecyclerView
        cart_items_recycler.setAdapter(cartItemsAdapter);
        cart_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        // Show the Cart's Total Price with the help of static method of CartItemsAdapter
        cartItemsAdapter.setCartTotal();

        cartItemsAdapter.notifyDataSetChanged();

        Log.d(TAG, "onCreateView: "+finalCartItemsList);

        clear_cart.setOnClickListener(view -> {
            // Delete CartItem from Local Database using static method of My_Cart
            ClearCart();


            // Calculate Cart's Total Price Again
            cartItemsAdapter.setCartTotal();

            // Remove CartItem from Cart List
            cartItemsList.clear();

            // Notify that item at position has been removed
            cartItemsAdapter.notifyDataSetChanged();

            // Update Cart View from Local Database using static method of My_Cart
            updateCartView(cartItemsList.size());

        });

        // Handle Click event of continue_shopping_btn Button
        continue_shopping_btn.setOnClickListener(view -> {
            // Go back to previous fragment
            requireActivity().onBackPressed();
        });

        // Handle Click event of cart_checkout_btn Button
        cart_checkout_btn.setOnClickListener(view -> {
            Log.e("CheckoutWarning: ", "checkout  " + ConstantValues.MAINTENANCE_MODE);

            if (ConstantValues.MAINTENANCE_MODE != null) {
                if (ConstantValues.MAINTENANCE_MODE.equalsIgnoreCase("Maintenance"))
                    showDialog(ConstantValues.MAINTENANCE_TEXT);
                else {
                    // Check if cartItemsList isn't empty
                    if (cartItemsList.size() != 0) {

                        // Check if User is Logged-In
                        if (ConstantValues.IS_USER_LOGGED_IN) {
                            Log.e("VC_Shop", "checkout executes  ");
                            new CheckStockTask().execute();
                        } else {
                            // Navigate to Login Activity
                            Intent i = new Intent(getContext(), Login.class);
                            getContext().startActivity(i);
                            ((DashboardActivity) getContext()).finish();
                            ((DashboardActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                        }
                    }

                }
            }
        });

//        cart_pair_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                        Log.e("CheckoutWarning: ", "checkout  "+ConstantValues.MAINTENANCE_MODE);
//
//                        if (ConstantValues.MAINTENANCE_MODE != null) {
//                            if (ConstantValues.MAINTENANCE_MODE.equalsIgnoreCase("Maintenance"))
//                                showDialog(ConstantValues.MAINTENANCE_TEXT);
//                            else {
//                                // Check if cartItemsList isn't empty
//                                if (cartItemsList.size() != 0) {
//
//                                    // Check if User is Logged-In
//                                    if (ConstantValues.IS_USER_LOGGED_IN) {
//
//                                        //Log.e("VC_Shop", "checkout executes  ");
//                                        //new CheckStockTask().execute();
//
//                                        FragmentManager fm = My_Cart.this.getFragmentManager();
//                                        FragmentTransaction ft = fm.beginTransaction();
//                                        Fragment prev =fm.findFragmentByTag("dialog");
//                                        if (prev != null) {
//                                            ft.remove(prev);
//                                        }
//                                        ft.addToBackStack(null);
//                                        // Create and show the dialog.
//                                        DialogFragment searchMerchantDailog =new SearchMerchantForPairing(getActivity(),My_Cart.this);
//                                        searchMerchantDailog.show( ft, "dialog");
//                                    } else {
//                                        // Navigate to Login Activity
//                                        Intent i = new Intent(getContext(), Login.class);
//                                        getContext().startActivity(i);
//                                        ((DashboardActivity) getContext()).finish();
//                                        ((DashboardActivity) getContext()).overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
//                                    }
//                                }
//
//                            }
//                        }
//            }
//        });

        return rootView;
    }

    //*********** Change the Layout View of My_Cart Fragment based on Cart Items ********//

    public void updateCartView(int cartListSize) {
        // Check if Cart has some Items
        if (cartListSize != 0) {
            cart_view.setVisibility(View.VISIBLE);
            cart_view_empty.setVisibility(View.GONE);
        } else {
            cart_view.setVisibility(View.GONE);
            cart_view_empty.setVisibility(View.VISIBLE);
        }
    }

    //*********** Used to Share the App with Others ********//

    private void showDialog(String str) {
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(getContext());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.buy_inputs_dialog_maintenance, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        final TextView dialog_message = dialogView.findViewById(R.id.maintenanceText);
        final Button dialog_button_positive = dialogView.findViewById(R.id.dialog_button);

        dialog_message.setText(str);

        final android.app.AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        dialog_button_positive.setOnClickListener(v -> alertDialog.dismiss());
    }

    //*********** Static method to Add the given Item to User's Cart ********//

    public static void AddCartItem(CartProduct cartProduct) {
        User_Cart_BuyInputsDB user_cart_BuyInputs_db = new User_Cart_BuyInputsDB();
        user_cart_BuyInputs_db.addCartItem
                (
                        cartProduct
                );
    }

    //*********** Static method to Get the Cart Product based on product_id ********//

    public static CartProduct GetCartProduct(int product_id) {
        User_Cart_BuyInputsDB user_cart_BuyInputs_db = new User_Cart_BuyInputsDB();
        CartProduct cartProduct = user_cart_BuyInputs_db.getCartProduct
                (
                        product_id
                );
        return cartProduct;
    }

    public void refreshCartProducts() {
        cartItemsList = user_cart_BuyInputs_db.getCartItems();
        cartItemsAdapter.notifyDataSetChanged();
    }

    //*********** Static method to Update the given Item in User's Cart ********//

    public static void UpdateCartItem(CartProduct cartProduct) {
        User_Cart_BuyInputsDB user_cart_BuyInputs_db = new User_Cart_BuyInputsDB();
        user_cart_BuyInputs_db.updateCartItem
                (
                        cartProduct
                );
    }

    //*********** Static method to Delete the given Item from User's Cart ********//

    public static void DeleteCartItem(int cart_item_id) {
        User_Cart_BuyInputsDB user_cart_BuyInputs_db = new User_Cart_BuyInputsDB();
        user_cart_BuyInputs_db.deleteCartItem
                (
                        cart_item_id
                );
    }

    //*********** Static method to Clear User's Cart ********//

    public static void ClearCart() {
        User_Cart_BuyInputsDB user_cart_BuyInputs_db = new User_Cart_BuyInputsDB();
        user_cart_BuyInputs_db.clearCart();
    }

    //*********** Static method to get total number of Items in User's Cart ********//

    public static int getCartSize() {
        int cartSize = 0;

        User_Cart_BuyInputsDB user_cart_BuyInputs_db = new User_Cart_BuyInputsDB();
        List<CartProduct> cartItems = user_cart_BuyInputs_db.getCartItems();

        for (int i = 0; i < cartItems.size(); i++) {
            cartSize += cartItems.get(i).getCustomersBasketProduct().getCustomersBasketQuantity();
        }

        return cartSize;
    }

    //*********** Static method to check if the given Product is already in User's Cart ********//

    public static boolean checkCartHasProduct(int cart_item_id) {
        User_Cart_BuyInputsDB user_cart_BuyInputs_db = new User_Cart_BuyInputsDB();
        return user_cart_BuyInputs_db.getCartItemsIDs().contains(cart_item_id);
    }

    public static boolean checkCartHasProductAndMeasure(int cart_item_id) {
        boolean result = false;

        ArrayList<CartProduct> user_cart_BuyInputs_db = new User_Cart_BuyInputsDB().checkCartHasProductAndMeasure(cart_item_id);
        for (CartProduct cartProduct : user_cart_BuyInputs_db) {
            if (cartProduct.getCustomersBasketProduct().getProductsId()==cart_item_id) {
                result = true;
            } else {
                result = false;
            }
        }

        return result;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NotNull MenuInflater inflater) {
        // Hide Cart Icon in the Toolbar
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        MenuItem profileItem = menu.findItem(R.id.toolbar_edit_profile);
        profileItem.setVisible(false);
        cartItem.setVisible(false);
        searchItem.setVisible(false);
    }

    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            dialogLoader.showProgressDialog();
            cartProducts.clear();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 0; i < cartItemsList.size(); i++) {
                try {
                    RequestProductDetails(i, cartItemsList.get(i).getCustomersBasketProduct().getProductsId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialogLoader.hideProgressDialog();
            for (int i = 0; i < finalCartItemsList.size(); i++) {
                finalCartItemsList.get(i).getCustomersBasketProduct().setProductsName(cartProducts.get(i).getProductsName());
                //finalCartItemsList.get(i).getCustomersBasketProduct().setCategoryNames(cartProducts.get(i).getCategoryNames());
                //finalCartItemsList.get(i).getCustomersBasketProduct().setCustomersBasketQuantity(cartProducts.get(i).getCustomersBasketQuantity());
                finalCartItemsList.get(i).getCustomersBasketProduct().setProductsPrice(cartProducts.get(i).getProductsPrice());
                //finalCartItemsList.get(i).getCustomersBasketProduct().setTotalPrice(cartProducts.get(i).getTotalPrice());
                finalCartItemsList.get(i).getCustomersBasketProduct().setProductsFinalPrice(cartProducts.get(i).getProductsFinalPrice());
                finalCartItemsList.get(i).getCustomersBasketProduct().setProductsQuantity(cartProducts.get(i).getProductsQuantity());

            }
            cartItemsAdapter.notifyDataSetChanged();
        }
    }

    public void RequestProductDetails(final int position, final int products_id) throws IOException {

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
        Response<ProductData> response = call.execute();

        if (response.isSuccessful()) {

            if (response.body().getSuccess().equalsIgnoreCase("1")) {
                cartProducts.add(response.body().getProductData().get(0));
            } else {
                Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
            }
            dialogLoader.hideProgressDialog();

        } else {
            dialogLoader.hideProgressDialog();
            Toast.makeText(getContext(), "Coud not get response.", Toast.LENGTH_SHORT).show();
        }

    }

    private void requestProductStock2(int productID, List<String> attributes, int position) {
        GetStock getStockParams = new GetStock();
        getStockParams.setProductsId(productID + "");
        getStockParams.setAttributes(attributes);
        Call<ProductStock> call = BuyInputsAPIClient.getInstance().getProductStock(getStockParams);
        try {
            Response<ProductStock> response = call.execute();
            if (response.isSuccessful()) {
                stocks.add(position, response.body().getStock());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getSelectedAttributesIds(List<CartProductAttributes> selectedAttributes) {
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < selectedAttributes.size(); i++) {
            ids.add(String.valueOf(selectedAttributes.get(i).getValues().get(0).getProducts_attributes_id()));
        }
        return ids;
    }

    private boolean isAllStockValid(List<String> stocks) {
        for (int i = 0; i < stocks.size(); i++) {
            if (Integer.parseInt(stocks.get(i)) <= 0)
                return false;
        }
        return true;
    }

    public class CheckStockTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoader.showProgressDialog();
            stocks.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < cartItemsList.size(); i++) {
                requestProductStock2(cartItemsList.get(i).getCustomersBasketProduct().getProductsId(), getSelectedAttributesIds(cartItemsList.get(i).getCustomersBasketProductAttributes()), i);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialogLoader.hideProgressDialog();
            if (isAllStockValid(stocks)) {
                Fragment fragment = new My_Addresses(My_Cart.this);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.main_fragment_container, fragment)
                        .addToBackStack(getString(R.string.actionAddresses)).commit();

            } else {
                Toast.makeText(getContext(), "Your Product in the cart is out of stock.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class pairingTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogLoader.showProgressDialog();
            stocks.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < cartItemsList.size(); i++) {
                requestProductStock2(cartItemsList.get(i).getCustomersBasketProduct().getProductsId(), getSelectedAttributesIds(cartItemsList.get(i).getCustomersBasketProductAttributes()), i);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialogLoader.hideProgressDialog();

            if (isAllStockValid(stocks)) {
                Fragment fragment = new My_Addresses(My_Cart.this);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().add(R.id.main_fragment_container, fragment)
                        .addToBackStack(getString(R.string.actionAddresses)).commit();

            } else {
                Toast.makeText(getContext(), "Your Product in the cart is out of stock.", Toast.LENGTH_SHORT).show();
            }

        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        cartItemsAdapter = new CartItemsAdapter(getContext(), finalCartItemsList, My_Cart.this);
//
//        // Set the Adapter and LayoutManager to the RecyclerView
//        cart_items_recycler.setAdapter(cartItemsAdapter);
//        cart_items_recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
//
//
//    }
}

