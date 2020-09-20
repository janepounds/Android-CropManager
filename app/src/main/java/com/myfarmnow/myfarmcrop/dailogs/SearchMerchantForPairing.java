package com.myfarmnow.myfarmcrop.dailogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.customs.DialogLoader;
import com.myfarmnow.myfarmcrop.database.User_Cart_BuyInputsDB;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Cart;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Nearby_Merchants;
import com.myfarmnow.myfarmcrop.models.cart_model.CartProduct;
import com.myfarmnow.myfarmcrop.models.merchants_model.MerchantData;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.myfarmnow.myfarmcrop.network.BuyInputsAPIClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchMerchantForPairing extends DialogFragment {

    Context activity;
    EditText shop_edit;
    My_Cart parentFragment;
    Button continue_pair_btn;

    public SearchMerchantForPairing(Context context, My_Cart my_cart){
        this.activity=context;
        this.parentFragment=my_cart;

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View view =inflater.inflate(R.layout.buy_inputs_search_merchant_layout, null);

        shop_edit=(EditText) view.findViewById(R.id.edt_shop_id);
        continue_pair_btn=(Button) view.findViewById(R.id.continue_pair_btn);

        continue_pair_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMerchantDataById(view);
            }
        });

        builder.setView(view);
        return builder.create();

    }


    public void getMerchantDataById(final View rootView ){

        final DialogLoader dialogLoader = new DialogLoader(getContext());
        User_Cart_BuyInputsDB user_cart_BuyInputs_db = new User_Cart_BuyInputsDB();
        // Get checkoutItems from Local Databases User_Cart_DB
        List<CartProduct> checkoutItemsList = user_cart_BuyInputs_db.getCartItems();
        ArrayList<String> product_names = new ArrayList <String>();
        for (CartProduct product:checkoutItemsList) {
            product_names.add(product.getCustomersBasketProduct().getProductsId()+"::"+product.getCustomersBasketProduct().getProductsName()+"::"+product.getCustomersBasketProduct().getCustomersBasketQuantity());
        }

        String product_names_str = new Gson().toJson(product_names);
        Log.w( "CartData",product_names_str);

        String shopID=shop_edit.getText().toString();
        dialogLoader.showProgressDialog();
        Call<MerchantData> call = BuyInputsAPIClient.getInstance()
                .getMerchantsProductData
                        (
                                shopID,product_names_str
                        );

        call.enqueue(new Callback<MerchantData>() {
            @Override
            public void onResponse(Call<MerchantData> call, retrofit2.Response<MerchantData> response) {

                String str = new Gson().toJson(response.body());

                Log.w("Response",str+".***********");
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        // merchants have been returned. Add merchants to the merchantsList
                        if(response.body().getData().size()>0){
                            try {
                                FragmentManager fragmentManager = getFragmentManager();

                                Fragment nearby_merchants = new Nearby_Merchants(response.body(), parentFragment);

                                fragmentManager.popBackStack(getString(R.string.select_merchants_fragment), FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                dismiss();
                                fragmentManager.beginTransaction().add(R.id.main_fragment, nearby_merchants)
                                        .addToBackStack(getString(R.string.select_merchants_fragment)).commit();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        else
                            Snackbar.make(rootView, "Couldn't find Nearby Merchants!", Snackbar.LENGTH_LONG).show();

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        //emptyRecord.setVisibility(View.VISIBLE);
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_LONG).show();

                    }
                    else {
                        // Unable to get Success status
                        //emptyRecord.setVisibility(View.VISIBLE);
                        Snackbar.make(rootView, getString(R.string.unexpected_response), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                    Log.w("Response",response.message()+"");
                }
                dialogLoader.hideProgressDialog();

            }

            @Override
            public void onFailure(Call<MerchantData> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
                Log.w("Response",t.toString()+"");
                dialogLoader.hideProgressDialog();
            }
        });


    }
}
