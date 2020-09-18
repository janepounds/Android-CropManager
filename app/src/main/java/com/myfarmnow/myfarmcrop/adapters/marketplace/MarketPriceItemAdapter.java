package com.myfarmnow.myfarmcrop.adapters.marketplace;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPriceItem;
import com.myfarmnow.myfarmcrop.models.marketplace.MyProduce;

import java.util.ArrayList;

public class MarketPriceItemAdapter extends RecyclerView.Adapter<MarketPriceItemAdapter.MarketPriceItemViewHolder> {
    private Context context;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<MarketPriceItem> marketPriceItemArrayList;

    public static class MarketPriceItemViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public RecyclerView recyclerView;

        public MarketPriceItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.market_name);
            recyclerView = itemView.findViewById(R.id.market_price_recyclerView);
        }
    }

    public MarketPriceItemAdapter(Context context, ArrayList<MarketPriceItem> marketPriceItemArrayList) {
        this.context = context;
        this.marketPriceItemArrayList = marketPriceItemArrayList;
    }

    @NonNull
    @Override
    public MarketPriceItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_price_list_item, parent, false);

        return new MarketPriceItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketPriceItemViewHolder holder, int position) {
        MarketPriceItem marketPriceItem = marketPriceItemArrayList.get(position);

        holder.name.setText(marketPriceItem.getTitle());

        // Create a layout manager with initial
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.recyclerView.getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        linearLayoutManager.setInitialPrefetchItemCount(marketPriceItem.getMarketPriceSubItemList().size());

        // Create sub item view adapter
        MarketPriceSubItemAdapter marketPriceSubItemAdapter = new MarketPriceSubItemAdapter(marketPriceItem.getMarketPriceSubItemList());

        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(marketPriceSubItemAdapter);
        holder.recyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return marketPriceItemArrayList.size();
    }
}