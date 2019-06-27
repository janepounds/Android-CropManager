package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.myfarmnow.myfarmcrop.activities.CropInventoryFertilizerManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropInventorySeedsManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropInventorySprayManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventory;

import java.util.ArrayList;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;

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
        holder.consumptionProgressBar.setScaleY(0.5f);
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

        ImageView  moreButton;
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
            moreButton = itemView.findViewById(R.id.img_crop_inventory_more);
            //layout_batch_number txt_view_batch_lbl

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                consumptionProgressBar.getProgressDrawable().setColorFilter(
                        Color.parseColor("#8ABB2A"), PorterDuff.Mode.MULTIPLY);
            }





            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropInventory inventory = inventoryList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete "+inventory.getInventoryType()+" inventory?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {


                                                CropInventory inventory = inventoryList.get(getAdapterPosition());

                                                if(inventory.getInventoryType().equals(CropInventory.CONST_SEEDS_INVENTORY)){
                                                    MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropSeeds(inventory.getId());
                                                }else if(inventory.getInventoryType().equals(CropInventory.CONST_SPRAY_INVENTORY)){
                                                    MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropSpray(inventory.getId());
                                                }else if(inventory.getInventoryType().equals(CropInventory.CONST_FERTILIZER_INVENTORY)){
                                                    MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropFertilizerInventory(inventory.getId());
                                                }

                                                inventoryList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());



                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();

                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropInventory inventory = inventoryList.get(getAdapterPosition());
                                //
                                if(inventory.getInventoryType().equals(CropInventory.CONST_SEEDS_INVENTORY)){
                                    Intent editInventory = new Intent(mContext, CropInventorySeedsManagerActivity.class);
                                    editInventory.putExtra("seedsInventory", (CropInventorySeeds)inventory);
                                    mContext.startActivity(editInventory);
                                }else  if(inventory.getInventoryType().equals(CropInventory.CONST_FERTILIZER_INVENTORY)){
                                    Intent editInventory = new Intent(mContext, CropInventoryFertilizerManagerActivity.class);
                                    editInventory.putExtra("fertilizerInventory", (CropInventoryFertilizer)inventory);
                                    mContext.startActivity(editInventory);
                                }
                                else  if(inventory.getInventoryType().equals(CropInventory.CONST_SPRAY_INVENTORY)){
                                    Intent editInventory = new Intent(mContext, CropInventorySprayManagerActivity.class);
                                    editInventory.putExtra("sprayInventory", (CropInventorySpray)inventory);
                                    mContext.startActivity(editInventory);
                                }
                            }
                            return true;
                        }
                    });

                    popup.getMenu().add(R.string.label_edit);
                    popup.getMenu().add(R.string.label_delete);
                    popup.show();


                }
            });



        }

        @Override
        public void onClick(View v) {

        }
    }
}
