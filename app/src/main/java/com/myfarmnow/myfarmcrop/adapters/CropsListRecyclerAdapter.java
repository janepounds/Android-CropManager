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
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropCultivateListActivity;
import com.myfarmnow.myfarmcrop.activities.CropsManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.Crop;


import java.util.ArrayList;

public class CropsListRecyclerAdapter extends RecyclerView.Adapter<CropsListRecyclerAdapter.CropCardViewHolder>  {
    ArrayList<Crop> cropsList;
    LayoutInflater mInflater;
    Context mContext;

    public CropsListRecyclerAdapter(ArrayList<Crop> inventoryList, Context context){
        this.cropsList = inventoryList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;

    }

    @NonNull
    @Override
    public CropCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.crops_list_card, parent, false);
        return new CropCardViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CropCardViewHolder holder, int position) {
        Crop curCrop = cropsList.get(position);
        holder.cropNameTxtView.setText(curCrop.getName());
        holder.cropVarietyTextView.setText("("+curCrop.getVariety()+")");
        holder.cropAge.setText("("+curCrop.computeAge()+")");
        holder.datePlantedTxt.setText(curCrop.getDateSown());
        holder.croppableAreaTxtView.setText(curCrop.getArea()+"ha");

    }

    @Override
    public int getItemCount() {
        return cropsList.size();
    }

    public void addList(ArrayList<Crop> inventoryListToAdd){
        int size = getItemCount();
        this.cropsList.addAll(inventoryListToAdd);
        Log.d("INITIAL SIZE",size+" "+this.cropsList.size());
        notifyDataSetChanged();
    }

    public void addInventory(Crop sheepInventory){
        this.cropsList.add(sheepInventory);
        notifyItemChanged(getItemCount());
    }

    public ArrayList<Crop> getCropsList(){

        return cropsList;
    }

    public void changeList(ArrayList<Crop> filteredList) {
        this.cropsList.clear();
        this.cropsList.addAll(filteredList);
        notifyDataSetChanged();
    }


    public class CropCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView editButton, deleteButton,cultivateButton;
        TextView cropNameTxtView, cropVarietyTextView, croppableAreaTxtView, cropAge;

        TextView datePlantedTxt;
        public CropCardViewHolder(View itemView) {
            super(itemView);
            cropNameTxtView = itemView.findViewById(R.id.txt_crop_card_name);
            cropVarietyTextView = itemView.findViewById(R.id.txt_crop_card_variety);
            croppableAreaTxtView = itemView.findViewById(R.id.txt_crop_card_area);
            datePlantedTxt = itemView.findViewById(R.id.txt_crop_card_actual_date_planted);
            cropAge = itemView.findViewById(R.id.txt_crop_card_age);
            editButton = itemView.findViewById(R.id.img_crop_card_edit);
            deleteButton = itemView.findViewById(R.id.img_crop_card_delete);
            cultivateButton = itemView.findViewById(R.id.img_crop_card_cultivate);
            //layout_batch_number txt_view_batch_lbl
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Crop inventory = cropsList.get(getAdapterPosition());
                    new AlertDialog.Builder(mContext)
                            .setTitle("Confirm")
                            .setMessage("Do you really want to delete the crop "+inventory.getName()+" ("+inventory.getVariety()+") ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    Crop crop = cropsList.get(getAdapterPosition());
                                    MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCrop(crop.getId());
                                    cropsList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());

                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });


            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Crop crop = cropsList.get(getAdapterPosition());
                    Intent editCrop = new Intent(mContext, CropsManagerActivity.class);
                    editCrop.putExtra("crop", crop);
                    mContext.startActivity(editCrop);

                }
            });
            cultivateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Crop crop = cropsList.get(getAdapterPosition());
                    Intent showCultivates = new Intent(mContext, CropCultivateListActivity.class);
                    showCultivates.putExtra("cropId", crop.getId());
                    mContext.startActivity(showCultivates);

                }
            });


        }

        @Override
        public void onClick(View v) {

        }
    }
}
