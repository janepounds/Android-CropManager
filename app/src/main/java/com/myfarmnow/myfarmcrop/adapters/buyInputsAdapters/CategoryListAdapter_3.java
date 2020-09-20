package com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.marketplace.BuyInputsActivity;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Products;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.SubCategories_3;
import com.myfarmnow.myfarmcrop.models.category_model.CategoryDetails;

import java.util.List;


/**
 * CategoryListAdapter is the adapter class of RecyclerView holding List of Categories in MainCategories
 **/

public class CategoryListAdapter_3 extends RecyclerView.Adapter<CategoryListAdapter_3.MyViewHolder> {

    boolean isSubCategory;

    Context context;
    List<CategoryDetails> categoriesList;


    public CategoryListAdapter_3(Context context, List<CategoryDetails> categoriesList, boolean isSubCategory) {
        this.context = context;
        this.isSubCategory = isSubCategory;
        this.categoriesList = categoriesList;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_inputs_categories_3, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        final CategoryDetails categoryDetails = categoriesList.get(position);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);
        // Set OrderProductCategory Image on ImageView with Glide Library
        Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(ConstantValues.ECOMMERCE_URL+categoryDetails.getImage())
                .into(holder.category_icon);


        holder.category_title.setText(categoryDetails.getName());
        holder.category_products.setText("("+categoryDetails.getTotalProducts() + ")");
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    
        LinearLayout category_card;
        ImageView category_icon;
        TextView category_title, category_products;


        public MyViewHolder(final View itemView) {
            super(itemView);
            category_card = itemView.findViewById(R.id.category_card);
            category_icon = itemView.findViewById(R.id.category_icon);
            category_title = itemView.findViewById(R.id.category_title);
            category_products = itemView.findViewById(R.id.category_products);

            category_card.setOnClickListener(this);
        }


        // Handle Click Listener on OrderProductCategory item
        @Override
        public void onClick(View view) {
            // Get OrderProductCategory Info
            Bundle categoryInfo = new Bundle();
            categoryInfo.putInt("CategoryID", Integer.parseInt(categoriesList.get(getAdapterPosition()).getId()));
            categoryInfo.putString("CategoryName", categoriesList.get(getAdapterPosition()).getName());

            Fragment fragment;

            if (!isSubCategory) {
                // Navigate to Products Fragment
                fragment = new Products();
            } else {
                // Navigate to SubCategories Fragment
                fragment = new SubCategories_3();
            }

            fragment.setArguments(categoryInfo);
            FragmentManager fragmentManager = ((BuyInputsActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null).commit();
        }
    }

}

