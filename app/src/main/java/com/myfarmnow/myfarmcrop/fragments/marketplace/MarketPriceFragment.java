package com.myfarmnow.myfarmcrop.fragments.marketplace;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.marketplace.MarketPriceItemAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentMarketPriceBinding;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPrice;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPriceItem;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPriceSubItem;
import com.myfarmnow.myfarmcrop.models.marketplace.MyProduce;

import java.util.ArrayList;


public class MarketPriceFragment extends Fragment {
    private static final String TAG = "MarketPriceFragment";
    private FragmentMarketPriceBinding binding;
    private Context context;

    private ArrayList<MarketPrice> marketPriceArrayList = new ArrayList<>();
    private ArrayList<MarketPriceItem> marketPriceItemArrayList = new ArrayList<>();
    private ArrayList<MarketPriceSubItem> marketPriceSubItemArrayList = new ArrayList<>();

    private MyFarmDbHandlerSingleton dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_price, container, false);

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);

        dbHandler.insertMarketPrice(new MarketPrice("Ginger", "Tororo", "1,000", "600"));
        dbHandler.insertMarketPrice(new MarketPrice("Millet", "Mbarara", "1,800", "1,500"));
        dbHandler.insertMarketPrice(new MarketPrice("Milk", "Gulu", "1,000", "800"));
        dbHandler.insertMarketPrice(new MarketPrice("Ginger", "Kampala", "2,000", "100"));

        marketPriceArrayList.clear();
        marketPriceArrayList = dbHandler.getAllMarketPrices();

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!adapterView.getSelectedItem().toString().equals("Select Crop")) {
//                    Toast.makeText(context, "Item = " + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Item = " + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "onCreateView: Filter = " + dbHandler.filterMarketPrices(adapterView.getSelectedItem().toString()));
                    Log.d(TAG, "onCreateView: Filter = " + dbHandler.filterMarketPrices(adapterView.getSelectedItem().toString()).toString());
                    marketPriceArrayList.clear();
                    marketPriceArrayList = dbHandler.filterMarketPrices(adapterView.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        for (int k = 0; k < marketPriceArrayList.size(); k++) {
            MarketPrice marketPrice = marketPriceArrayList.get(k);
            MarketPriceSubItem subItem = new MarketPriceSubItem(marketPrice.getMarket(), marketPrice.getRetail(), marketPrice.getWholesale());
            marketPriceSubItemArrayList.add(subItem);
            MarketPriceItem marketPriceItem = new MarketPriceItem(marketPrice.getCrop(), marketPriceSubItemArrayList);
            marketPriceItemArrayList.add(marketPriceItem);
            Log.d(TAG, "onCreateView: List = " + marketPrice.getCrop());
        }

//        MarketPriceSubItem subItem4 = new MarketPriceSubItem("Gulu", "2,000", "1,750");
//        MarketPriceSubItem subItem6 = new MarketPriceSubItem("Mbarara", "2,500", "2,200");
//        MarketPriceSubItem subItem5 = new MarketPriceSubItem("Kampala", "2,700", "2,400");
//        marketPriceSubItemArrayList1.add(subItem4);
//        marketPriceSubItemArrayList1.add(subItem5);
//        marketPriceSubItemArrayList1.add(subItem6);
//        MarketPriceItem marketPriceItem1 = new MarketPriceItem("Millet (Kg)", marketPriceSubItemArrayList1);
//        marketPriceItemArrayList.add(marketPriceItem1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        MarketPriceItemAdapter adapter = new MarketPriceItemAdapter(context, marketPriceItemArrayList);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}