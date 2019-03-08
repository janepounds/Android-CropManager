package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.Crop;


import java.util.ArrayList;

public class CropsListRecyclerAdapter extends RecyclerView.Adapter<CropsListRecyclerAdapter.CropCardViewHolder>  {
    ArrayList<Crop> inventoryList;
    LayoutInflater mInflater;
    Context mContext;

    public CropsListRecyclerAdapter(ArrayList<Crop> inventoryList, Context context){
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
        Crop curInventory = inventoryList.get(position);

      /*  holder.inventoryNameTxtView.setText(curInventory.getInventoryType()+" ("+curInventory.getName()+")");
        holder.batchNumberTxtView.setText(curInventory.getBatchNumber());
        holder.consumptionProgressBar.setMax((int)curInventory.getInitialQuantity());
        holder.consumptionProgressBar.setProgress((int)curInventory.calculateAmountLeft());
        holder.curInventoryTxtView.setText(curInventory.calculateAmountLeft()+"/"+curInventory.getInitialQuantity()+" "+curInventory.getUsageUnits());
      */
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    public void addList(ArrayList<Crop> inventoryListToAdd){
        int size = getItemCount();
        this.inventoryList.addAll(inventoryListToAdd);
        Log.d("INITAL SIZE",size+" "+this.inventoryList.size());
        notifyDataSetChanged();
    }

    public void addInventory(Crop sheepInventory){
        this.inventoryList.add(sheepInventory);
        notifyItemChanged(getItemCount());
    }

    public ArrayList<Crop> getInventoryList(){

        return inventoryList;
    }

    public void changeList(ArrayList<Crop> filteredList) {
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
            editButton = itemView.findViewById(R.id.img_crop_inventory_edit);
            deleteButton = itemView.findViewById(R.id.img_crop_inventory_delete);
            //layout_batch_number txt_view_batch_lbl
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Crop inventory = inventoryList.get(getAdapterPosition());
                    new AlertDialog.Builder(mContext)
                            .setTitle("Confirm")
                            .setMessage("Do you really want to delete the crop "+inventory.getName()+" ("+inventory.getVariety()+") ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {


                                    Crop inventory = inventoryList.get(getAdapterPosition());

                                    inventoryList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());



                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });


            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Crop inventory = inventoryList.get(getAdapterPosition());
                    //


                }
            });


        }

        @Override
        public void onClick(View v) {

        }
    }
}
