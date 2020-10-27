package com.myfarmnow.myfarmcrop.adapters.buyInputsAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.product_model.ProductMeasure;

import java.util.List;

public class ProductMeasureAdapter extends RecyclerView.Adapter<ProductMeasureAdapter.ProductMeasureViewHolder> {
    private static final String TAG = "ProductMeasureAdapter";
    private Context context;
    private List<ProductMeasure>productMeasures;

   public ProductMeasureAdapter(Context context, List<ProductMeasure> productMeasureList){
       this.context = context;
       this.productMeasures = productMeasureList;

   }

    @NonNull
    @Override
    public ProductMeasureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_inputs_product_measure, parent, false);

        return new ProductMeasureAdapter.ProductMeasureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductMeasureViewHolder holder, int position) {
        final ProductMeasure productMeasure = productMeasures.get(position);
        Log.d(TAG, "onBindViewHolder: "+productMeasure);
        for(int i=0; i<productMeasures.size();i++){
            String measure =productMeasure.getProducts_weight() + " " + productMeasure.getProducts_weight_unit();

            holder.weight.setText(measure);



        }
    }

    @Override
    public int getItemCount() {
        return productMeasures.size();
    }

    public class ProductMeasureViewHolder extends RecyclerView.ViewHolder {
       private TextView weight;
        public ProductMeasureViewHolder(View itemView) {
            super(itemView);
            weight = itemView.findViewById(R.id.weight1);
        }

    }
}
