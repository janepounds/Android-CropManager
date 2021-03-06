package com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.models.product_model.Value;

import java.util.List;


/**
 * ProductAttributeValuesAdapter is the adapter class of RecyclerView holding List of Product Attribute Values in CartItemsAdapter
 **/

public class ProductAttributeValuesAdapter extends RecyclerView.Adapter<ProductAttributeValuesAdapter.MyViewHolder> {

    Context context;
    private List<Value> attributeValues;


    public ProductAttributeValuesAdapter(Context context, List<Value> attributeValues) {
        this.context = context;
        this.attributeValues = attributeValues;
    }



    //********** Called to Inflate a Layout from XML and then return the Holder *********//

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_inputs_attribute_values, parent, false);

        return new MyViewHolder(itemView);
    }



    //********** Called by RecyclerView to display the Data at the specified Position *********//

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Get the data model based on Position
        Value value = attributeValues.get(position);
    
        holder.attribute_value_name.setText(value.getValue());
        holder.attribute_value_prefix.setText(value.getPricePrefix());
        holder.attribute_value_price.setText(ConstantValues.CURRENCY_SYMBOL + value.getPrice());
    }



    //********** Returns the total number of items in the data set *********//

    @Override
    public int getItemCount() {
        return attributeValues.size();
    }



    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView attribute_value_name;
        TextView attribute_value_prefix;
        TextView attribute_value_price;


        public MyViewHolder(final View itemView) {
            super(itemView);
            attribute_value_name = itemView.findViewById(R.id.attribute_value_name);
            attribute_value_prefix = itemView.findViewById(R.id.attribute_value_prefix);
            attribute_value_price = itemView.findViewById(R.id.attribute_value_price);
        }
    }

}

