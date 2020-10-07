package com.myfarmnow.myfarmcrop.fragments.marketplace;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.marketplace.MarketPriceItemAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPrice;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPriceItem;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPriceSubItem;

import java.util.ArrayList;

public class MarketPriceFragment extends Fragment {
    private static final String TAG = "MarketPriceFragment";
    private Context context;

    private ArrayList<MarketPrice> marketPriceArrayList = new ArrayList<>();
    private ArrayList<MarketPriceItem> marketPriceItemArrayList = new ArrayList<>();
    private ArrayList<MarketPriceSubItem> marketPriceSubItemArrayList = new ArrayList<>();

    private MyFarmDbHandlerSingleton dbHandler;

    private RecyclerView recyclerView;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_market_price, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_market_price_fragment);
        spinner = view.findViewById(R.id.spinner_market_price_fragment);

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        MarketPriceItemAdapter adapter = new MarketPriceItemAdapter(context, marketPriceItemArrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

//        dbHandler.insertMarketPrice(new MarketPrice("Ginger", "Tororo", "1,000", "600"));
//        dbHandler.insertMarketPrice(new MarketPrice("Millet", "Mbarara", "1,800", "1,500"));
//        dbHandler.insertMarketPrice(new MarketPrice("Milk", "Gulu", "1,000", "800"));

//        marketPriceArrayList.clear();
//        marketPriceArrayList = dbHandler.getAllMarketPrices();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                marketPriceArrayList.clear();
                marketPriceItemArrayList.clear();
                marketPriceSubItemArrayList.clear();

                if (adapterView.getSelectedItem().toString().equals("Select Crop")) {
                    Toast.makeText(context, "Select Crop", Toast.LENGTH_SHORT).show();
                } else if (adapterView.getSelectedItem().toString().equals("Ginger")) {
                    Toast.makeText(context, "Ginger", Toast.LENGTH_SHORT).show();
                    marketPriceArrayList = dbHandler.filterMarketPrices("Ginger");

                    for (int k = 0; k < marketPriceArrayList.size(); k++) {
                        Log.d(TAG, "onItemSelected: Array = " + marketPriceArrayList.get(k));
                        MarketPrice marketPrice = marketPriceArrayList.get(k);
                        Log.d(TAG, "onItemSelected: Name = " + marketPrice.getCrop());

                        MarketPriceSubItem subItem = new MarketPriceSubItem(marketPrice.getMarket(), marketPrice.getRetail(), marketPrice.getWholesale());
                        marketPriceSubItemArrayList.add(subItem);
                    }

                    MarketPriceItem marketPriceItem = new MarketPriceItem("Ginger", marketPriceSubItemArrayList);
                    marketPriceItemArrayList.add(marketPriceItem);

                } else if (adapterView.getSelectedItem().toString().equals("Millet")) {
                    Toast.makeText(context, "Millet", Toast.LENGTH_SHORT).show();
                    marketPriceArrayList = dbHandler.filterMarketPrices("Millet");

                    for (int k = 0; k < marketPriceArrayList.size(); k++) {
                        Log.d(TAG, "onItemSelected: Array = " + marketPriceArrayList.get(k));
                        MarketPrice marketPrice = marketPriceArrayList.get(k);
                        Log.d(TAG, "onItemSelected: Name = " + marketPrice.getCrop());

                        MarketPriceSubItem subItem = new MarketPriceSubItem(marketPrice.getMarket(), marketPrice.getRetail(), marketPrice.getWholesale());
                        marketPriceSubItemArrayList.add(subItem);
                    }

                    MarketPriceItem marketPriceItem = new MarketPriceItem("Millet", marketPriceSubItemArrayList);
                    marketPriceItemArrayList.add(marketPriceItem);

                } else if (adapterView.getSelectedItem().toString().equals("Milk")) {
                    Toast.makeText(context, "Milk", Toast.LENGTH_SHORT).show();
                    marketPriceArrayList = dbHandler.filterMarketPrices("Milk");

                    for (int k = 0; k < marketPriceArrayList.size(); k++) {
                        Log.d(TAG, "onItemSelected: Array = " + marketPriceArrayList.get(k));
                        MarketPrice marketPrice = marketPriceArrayList.get(k);
                        Log.d(TAG, "onItemSelected: Name = " + marketPrice.getCrop());

                        MarketPriceSubItem subItem = new MarketPriceSubItem(marketPrice.getMarket(), marketPrice.getRetail(), marketPrice.getWholesale());
                        marketPriceSubItemArrayList.add(subItem);
                    }

                    MarketPriceItem marketPriceItem = new MarketPriceItem("Milk", marketPriceSubItemArrayList);
                    marketPriceItemArrayList.add(marketPriceItem);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        for (int k = 0; k < marketPriceArrayList.size(); k++) {
//            MarketPrice marketPrice = marketPriceArrayList.get(k);
//            MarketPriceSubItem subItem = new MarketPriceSubItem(marketPrice.getMarket(), marketPrice.getRetail(), marketPrice.getWholesale());
//            marketPriceSubItemArrayList.add(subItem);
//            MarketPriceItem marketPriceItem = new MarketPriceItem(marketPrice.getCrop(), marketPriceSubItemArrayList);
//            marketPriceItemArrayList.add(marketPriceItem);
//            Log.d(TAG, "onCreateView: List = " + marketPrice.getCrop());
//        }

//        MarketPriceSubItem subItem4 = new MarketPriceSubItem("Gulu", "2,000", "1,750");
//        MarketPriceSubItem subItem6 = new MarketPriceSubItem("Mbarara", "2,500", "2,200");
//        MarketPriceSubItem subItem5 = new MarketPriceSubItem("Kampala", "2,700", "2,400");
//        marketPriceSubItemArrayList1.add(subItem4);
//        marketPriceSubItemArrayList1.add(subItem5);
//        marketPriceSubItemArrayList1.add(subItem6);
//        MarketPriceItem marketPriceItem1 = new MarketPriceItem("Millet (Kg)", marketPriceSubItemArrayList1);
//        marketPriceItemArrayList.add(marketPriceItem1);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        MarketPriceItemAdapter adapter = new MarketPriceItemAdapter(context, marketPriceItemArrayList);
//        binding.recyclerView.setAdapter(adapter);
//        binding.recyclerView.setLayoutManager(linearLayoutManager);
//        adapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}