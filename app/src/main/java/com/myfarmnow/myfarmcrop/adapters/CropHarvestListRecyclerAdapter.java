package com.myfarmnow.myfarmcrop.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropHarvestManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropHarvest;

import java.util.ArrayList;

public class CropHarvestListRecyclerAdapter extends RecyclerView.Adapter<CropHarvestListRecyclerAdapter.HarvestViewHolder> {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropHarvest> cropHarvestList = new ArrayList<>();


    public CropHarvestListRecyclerAdapter(Context context, ArrayList<CropHarvest> cropHarvests){
        cropHarvestList.addAll(cropHarvests);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

    }
    @NonNull
    @Override
    public HarvestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_harvest_list_card,parent,false);

        HarvestViewHolder holder = new HarvestViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropHarvest> cropHarvests){

        this.cropHarvestList.addAll(cropHarvests);
        notifyDataSetChanged();
    }
    public void addCropHarvest(CropHarvest cropHarvest){
        this.cropHarvestList.add(cropHarvest);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropHarvest> cropHarvests){

        this.cropHarvestList.clear();
        this.cropHarvestList.addAll(cropHarvests);

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull HarvestViewHolder harvestViewHolder, int position) {
        CropHarvest harvest = cropHarvestList.get(position);
        harvestViewHolder.harvestDateTxt.setText(harvest.getDate());
        harvestViewHolder.harvestMethodTxt.setText("("+harvest.getMethod()+")");
        harvestViewHolder.quantityTxt.setText(harvest.getQuantity()+" ");
        harvestViewHolder.unitsTxt.setText(harvest.getUnits());
        harvestViewHolder.statusTxt.setText(harvest.getStatus());
        harvestViewHolder.costTxt.setText(harvest.getCost()+"");

    }

    @Override
    public int getItemCount() {
        return cropHarvestList.size();
    }


    public class HarvestViewHolder extends RecyclerView.ViewHolder{
        TextView harvestDateTxt,harvestMethodTxt,quantityTxt,statusTxt,costTxt,unitsTxt;
        LinearLayout hideShowLayout,expandContentLayout;
        ImageView moreButton,showHideRemarksButton;

        public HarvestViewHolder(View itemView) {
            super(itemView);
            harvestDateTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_date);
            harvestMethodTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_method);
            quantityTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_quantity);
            statusTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_status);
            unitsTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_units);

            costTxt = itemView.findViewById(R.id.txt_view_crop_harvest_card_cost);

            hideShowLayout = itemView.findViewById(R.id.layout_crop_harvest_card_show_hide);
            expandContentLayout = itemView.findViewById(R.id.layout_crop_harvest_expand);

            moreButton = itemView.findViewById(R.id.img_crop_harvest_card_more);
            showHideRemarksButton = itemView.findViewById(R.id.img_crop_harvest_card_show_crops);
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final CropHarvest cropHarvest = cropHarvestList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this Harvest Record ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropHarvest(cropHarvest.getId());
                                                cropHarvestList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropHarvest cropHarvest = cropHarvestList.get(getAdapterPosition());
                                Intent editHarvest = new Intent(mContext, CropHarvestManagerActivity.class);
                                editHarvest.putExtra("cropHarvest",cropHarvest);
                                editHarvest.putExtra("cropId",cropHarvest.getCropId());
                                mContext.startActivity(editHarvest);
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


        }
    }

