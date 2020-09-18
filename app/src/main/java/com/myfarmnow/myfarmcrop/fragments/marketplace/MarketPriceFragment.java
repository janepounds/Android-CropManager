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

import com.google.gson.Gson;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.marketplace.MarketPriceItemAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentMarketPriceBinding;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPriceItem;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPriceSubItem;
import com.myfarmnow.myfarmcrop.models.marketplace.MyProduce;

import java.util.ArrayList;


public class MarketPriceFragment extends Fragment {
    private static final String TAG = "MarketPriceFragment";
    private FragmentMarketPriceBinding binding;
    private Context context;

    private ArrayList<MarketPriceItem> marketPriceItemArrayList = new ArrayList<>();
    private ArrayList<MarketPriceSubItem> marketPriceSubItemArrayList = new ArrayList<>();
    private ArrayList<MarketPriceSubItem> marketPriceSubItemArrayList1 = new ArrayList<>();

    private MyFarmDbHandlerSingleton dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_market_price, container, false);

        marketPriceItemArrayList.clear();

        MarketPriceSubItem subItem = new MarketPriceSubItem("Tororo", "1,000", "600");
        MarketPriceSubItem subItem1 = new MarketPriceSubItem("Mbarara", "1,800", "1,500");
        MarketPriceSubItem subItem2 = new MarketPriceSubItem("Gulu", "1,000", "800");
        MarketPriceSubItem subItem3 = new MarketPriceSubItem("Kampala", "2,000", "100");
        marketPriceSubItemArrayList.add(subItem);
        marketPriceSubItemArrayList.add(subItem1);
        marketPriceSubItemArrayList.add(subItem2);
        marketPriceSubItemArrayList.add(subItem3);
        MarketPriceItem marketPriceItem = new MarketPriceItem("Ginger (Kg)", marketPriceSubItemArrayList);
        marketPriceItemArrayList.add(marketPriceItem);

        MarketPriceSubItem subItem4 = new MarketPriceSubItem("Gulu", "2,000", "1,750");
        MarketPriceSubItem subItem6 = new MarketPriceSubItem("Mbarara", "2,500", "2,200");
        MarketPriceSubItem subItem5 = new MarketPriceSubItem("Kampala", "2,700", "2,400");
        marketPriceSubItemArrayList1.add(subItem4);
        marketPriceSubItemArrayList1.add(subItem5);
        marketPriceSubItemArrayList1.add(subItem6);
        MarketPriceItem marketPriceItem1 = new MarketPriceItem("Millet (Kg)", marketPriceSubItemArrayList1);
        marketPriceItemArrayList.add(marketPriceItem1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        MarketPriceItemAdapter adapter = new MarketPriceItemAdapter(context, marketPriceItemArrayList);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}