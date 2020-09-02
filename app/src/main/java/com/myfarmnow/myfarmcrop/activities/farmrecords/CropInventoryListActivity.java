package com.myfarmnow.myfarmcrop.activities.farmrecords;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;


import com.loopj.android.http.AsyncHttpClient;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropInventoryListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.fragments.wallet.WalletHomeFragment;
import com.myfarmnow.myfarmcrop.models.CropInventory;

import java.util.ArrayList;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.popupDailogs.farmrecords.AddFertilizer;
import com.myfarmnow.myfarmcrop.popupDailogs.farmrecords.AddSeed;
import com.myfarmnow.myfarmcrop.popupDailogs.farmrecords.AddSpray;
import com.myfarmnow.myfarmcrop.popupDailogs.wallet.DepositMoneyVoucher;

public class CropInventoryListActivity extends AppCompatActivity {

    RecyclerView cropInventoryListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    CropInventoryListRecyclerAdapter cropListRecyclerAdapter;
    Spinner selectInventorySpinner;
    ArrayList<CropInventory> cropInventoryList = new ArrayList();
    ArrayList<CropInventory> cropListBackUp = new ArrayList();
    MyFarmDbHandlerSingleton dbHandler;

    public static FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_inventory_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        cropInventoryListRecyclerView = findViewById(R.id.inventory_recyc_view);
        selectInventorySpinner = findViewById(R.id.select_inventory_spinner);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        cropInventoryListRecyclerView.setLayoutManager(linearLayoutManager);
        cropListRecyclerAdapter = new CropInventoryListRecyclerAdapter(CropInventoryListActivity.this.cropInventoryList,CropInventoryListActivity.this);
        cropInventoryListRecyclerView.setAdapter(cropListRecyclerAdapter);
        loadCropInventories();

        selectInventorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                if(position !=0){
                    String selection = parent.getSelectedItem().toString();
                    ArrayList<CropInventory> filteredList = new ArrayList<>();
                    if(cropListBackUp.size() ==0 ){
                       // cropArrayList.clear();
                        for(CropInventory x :cropListRecyclerAdapter.getInventoryList()){
                            cropListBackUp.add(x);
                        }
                    }

                    //filteredList.clear();
                    for(CropInventory x :cropListBackUp){
                        if(selection.toLowerCase().contains(x.getInventoryType().toLowerCase())||position==1){
                            filteredList.add(x);
                        }

                    }

                    cropListRecyclerAdapter.changeList(filteredList);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadCropInventories(){
        AsyncHttpClient client = new AsyncHttpClient();
        for(CropInventorySeeds seedsInventory:dbHandler.getCropSeeds(DashboardActivity.getPreferences("userId",this))){
            cropListRecyclerAdapter.addInventory(seedsInventory);
        }
        for(CropInventoryFertilizer fertilizerInventory:dbHandler.getCropFertilizerInventorys(DashboardActivity.getPreferences("userId",this))){
            cropListRecyclerAdapter.addInventory(fertilizerInventory);
        }
        for(CropInventorySpray spraysInventory:dbHandler.getCropSpray(DashboardActivity.getPreferences("userId",this))){
            cropListRecyclerAdapter.addInventory(spraysInventory);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_menu_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {


            case R.id.addFertilizer:
                FragmentManager fm = getSupportFragmentManager();
                AddFertilizer fertilizerFragment =  AddFertilizer.newInstance("FERTILIZER");

               fertilizerFragment.show(fm, "Add Fertilizer");
                return true;

            case R.id.addSeed:
                FragmentManager manager = getSupportFragmentManager();

                AddSeed dialogFragment =  AddSeed.newInstance("SEED");

                dialogFragment.show(manager, "Add Spray");

                return true;
            case R.id.addSpray:


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
