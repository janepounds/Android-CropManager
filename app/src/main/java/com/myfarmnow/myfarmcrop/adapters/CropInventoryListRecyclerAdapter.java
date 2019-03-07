package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.myfarmnow.myfarmcrop.models.CropInventory;

import java.util.ArrayList;

import com.myfarmnow.myfarmcrop.R;

public class CropInventoryListRecyclerAdapter extends RecyclerView.Adapter<CropInventoryListRecyclerAdapter.CropCardViewHolder>  {
    ArrayList<CropInventory> inventoryList;
    LayoutInflater mInflater;
    Context mContext;

    public CropInventoryListRecyclerAdapter(ArrayList<CropInventory> inventoryList, Context context){
        this.inventoryList = inventoryList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;

    }

    @NonNull
    @Override
    public CropCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.crop_inventory_list_card, parent, false);
        return new CropCardViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CropCardViewHolder holder, int position) {
        CropInventory curInventory = inventoryList.get(position);

        holder.inventoryNameTxtView.setText(curInventory.getInventoryType()+" ("+curInventory.getName()+")");
        holder.batchNumberTxtView.setText(curInventory.getBatchNumber());
        holder.consumptionProgressBar.setMax((int)curInventory.getInitialQuantity());
        holder.consumptionProgressBar.setProgress((int)curInventory.calculateAmountLeft());
        holder.curInventoryTxtView.setText(curInventory.calculateAmountLeft()+"/"+curInventory.getInitialQuantity()+" "+curInventory.getUsageUnits());

    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    public void addList(ArrayList<CropInventory> inventoryListToAdd){
        int size = getItemCount();
        this.inventoryList.addAll(inventoryListToAdd);
        Log.d("INITAL SIZE",size+" "+this.inventoryList.size());
        notifyDataSetChanged();
    }

    public void addInventory(CropInventory sheepInventory){
        this.inventoryList.add(sheepInventory);
        notifyItemChanged(getItemCount());
    }

    public ArrayList<CropInventory> getInventoryList(){

        return inventoryList;
    }

    public void changeList(ArrayList<CropInventory> filteredList) {
        this.inventoryList.clear();
        this.inventoryList.addAll(filteredList);
        notifyDataSetChanged();
    }


    public class CropCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView sheepPhotoImageView;
        ImageView editButton, deleteButton;
        TextView inventoryNameTxtView,batchNumberTxtView,curInventoryTxtView, batchNumberlblTxtView;
        ProgressBar consumptionProgressBar;
        LinearLayout batchNumberLayout;
        public CropCardViewHolder(View itemView) {
            super(itemView);
            inventoryNameTxtView = itemView.findViewById(R.id.txt_view_inventory_name);
            batchNumberTxtView = itemView.findViewById(R.id.txtView_batch_number);
            curInventoryTxtView = itemView.findViewById(R.id.txt_view_current_inventory);
            consumptionProgressBar = itemView.findViewById(R.id.progress_bar_consumption);
            batchNumberLayout = itemView.findViewById(R.id.layout_batch_number);
            batchNumberlblTxtView = itemView.findViewById(R.id.txt_view_batch_lbl);
            //layout_batch_number txt_view_batch_lbl


        }

        @Override
        public void onClick(View v) {

        }
    }
}
