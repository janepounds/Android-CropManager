package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropActivitiesListActivity;
import com.myfarmnow.myfarmcrop.activities.CropCultivationManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropFertilizerApplicationManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropHarvestManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropIrrigationManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropScoutingManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropSprayingManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropTransplantingManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropsManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.Crop;


import java.util.ArrayList;

public class CropsListRecyclerAdapter extends RecyclerView.Adapter<CropsListRecyclerAdapter.CropCardViewHolder> {
    ArrayList<Crop> cropsList;
    LayoutInflater mInflater;
    Context mContext;

    public CropsListRecyclerAdapter(ArrayList<Crop> inventoryList, Context context) {
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
        holder.cropVarietyTextView.setText(""+""+"(" + curCrop.getVariety() + ")");
        holder.cropAge.setText("(" + curCrop.computeAge() + ")");
        holder.datePlantedTxt.setText(curCrop.getDateSown());
        holder.croppingYearTxt.setText(curCrop.getCroppingYear()+"");
        holder.seasonTxt.setText("(" + curCrop.getSeason() + ")");

        holder.rateTextView.setText(curCrop.getRate() + "");

    }

    @Override
    public int getItemCount() {
        return cropsList.size();
    }

    public void addList(ArrayList<Crop> inventoryListToAdd) {
        int size = getItemCount();
        this.cropsList.addAll(inventoryListToAdd);
        Log.d("INITIAL SIZE", size + " " + this.cropsList.size());
        notifyDataSetChanged();
    }

    public void addInventory(Crop sheepInventory) {
        this.cropsList.add(sheepInventory);
        notifyItemChanged(getItemCount());
    }

    public ArrayList<Crop> getCropsList() {

        return cropsList;
    }

    public void changeList(ArrayList<Crop> filteredList) {
        this.cropsList.clear();
        this.cropsList.addAll(filteredList);
        notifyDataSetChanged();
    }


    public class CropCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView editButton, moreButton, deleteButton, cultivateButton, fertilizerApplicationButton, soilAnalysisButton, sprayingButton;
        TextView cropNameTxtView, cropVarietyTextView, rateTextView, cropAge, croppingYearTxt, seasonTxt;

        TextView datePlantedTxt;

        Button activitiesBtn;

        public CropCardViewHolder(View itemView) {
            super(itemView);
            cropNameTxtView = itemView.findViewById(R.id.txt_crop_card_name);
            cropVarietyTextView = itemView.findViewById(R.id.txt_crop_card_variety);
            rateTextView = itemView.findViewById(R.id.txt_crop_card_rate);
            datePlantedTxt = itemView.findViewById(R.id.txt_crop_card_actual_date_planted);
            croppingYearTxt = itemView.findViewById(R.id.txt_crop_card_cropping_year);
            seasonTxt = itemView.findViewById(R.id.txt_crop_card_season);
            cropAge = itemView.findViewById(R.id.txt_crop_card_age);
            activitiesBtn = itemView.findViewById(R.id.btn_crop_activities);

            activitiesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Crop crop = cropsList.get(getAdapterPosition());
                    Intent showSpray = new Intent(mContext, CropActivitiesListActivity.class);
                    showSpray.putExtra("cropId", crop.getId());
                    mContext.startActivity(showSpray);
                }
            });

            moreButton = itemView.findViewById(R.id.img_crop_card_more);
            //layout_batch_number txt_view_batch_lbl
            moreButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))) {
                                final Crop crop = cropsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete " + crop.getName() + " crop?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCrop(crop.getId());
                                                cropsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null).show();
                            } else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))) {
                                Crop crop = cropsList.get(getAdapterPosition());
                                Intent editCrop = new Intent(mContext, CropsManagerActivity.class);
                                editCrop.putExtra("crop", crop);
                                mContext.startActivity(editCrop);
                            } else if (item.getTitle().toString().equals(mContext.getString(R.string.label_cultivate))) {
                                Crop crop = cropsList.get(getAdapterPosition());
                                Intent intent = new Intent(mContext, CropCultivationManagerActivity.class);
                                intent.putExtra("cropId", crop.getId());
                                mContext.startActivity(intent);
                            } else if (item.getTitle().toString().equals(mContext.getString(R.string.label_fertilizer))) {
                                Crop crop = cropsList.get(getAdapterPosition());
                                Intent showFertilize = new Intent(mContext, CropFertilizerApplicationManagerActivity.class);
                                showFertilize.putExtra("cropId", crop.getId());
                                mContext.startActivity(showFertilize);
                            } else if (item.getTitle().toString().equals(mContext.getString(R.string.label_spray))) {
                                Crop crop = cropsList.get(getAdapterPosition());
                                Intent showSpray = new Intent(mContext, CropSprayingManagerActivity.class);
                                showSpray.putExtra("cropId", crop.getId());
                                mContext.startActivity(showSpray);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_irrigation))){
                                Crop crop = cropsList.get(getAdapterPosition());
                                Intent showIrrigation = new Intent(mContext, CropIrrigationManagerActivity.class);
                                showIrrigation.putExtra("cropId",crop.getId());
                                mContext.startActivity(showIrrigation);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_transplanting))){
                                Crop crop = cropsList.get(getAdapterPosition());
                                Intent showTransplanting = new Intent(mContext, CropTransplantingManagerActivity.class);
                                showTransplanting.putExtra("cropId",crop.getId());
                                mContext.startActivity(showTransplanting);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_scouting))){
                                Crop crop = cropsList.get(getAdapterPosition());
                                Intent showScouting = new Intent(mContext, CropScoutingManagerActivity.class);
                                showScouting.putExtra("cropId",crop.getId());
                                mContext.startActivity(showScouting);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_harvest))){
                                Crop crop = cropsList.get(getAdapterPosition());
                                Intent showHarvest = new Intent(mContext, CropHarvestManagerActivity.class);
                                showHarvest.putExtra("cropId",crop.getId());
                                mContext.startActivity(showHarvest);

                            }

                            return true;
                        }
                    });

                    //
                    popup.getMenu().add(R.string.label_cultivate);
                    popup.getMenu().add(R.string.label_fertilizer);
                    popup.getMenu().add(R.string.label_spray);
                    popup.getMenu().add(R.string.label_harvest);
                    popup.getMenu().add(R.string.label_irrigation);
                    popup.getMenu().add(R.string.label_transplanting);
                    popup.getMenu().add(R.string.label_scouting);
                    popup.getMenu().add(R.string.label_edit);
                    popup.getMenu().add(R.string.label_delete);
                    popup.show();

                }
            });


        }

        @Override
        public void onClick(View view) {

        }
    }
}
