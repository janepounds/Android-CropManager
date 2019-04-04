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
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropTransplantingManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropTransplanting;

import java.util.ArrayList;

public class CropTransplantingListRecyclerAdapter extends RecyclerView.Adapter<CropTransplantingListRecyclerAdapter.TransplantingViewHolder>   {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropTransplanting> cropTransplantingList = new ArrayList<>();
    public CropTransplantingListRecyclerAdapter(Context context, ArrayList<CropTransplanting> cropTransplantings){
        cropTransplantingList.addAll(cropTransplantings);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

    }


    @NonNull
    @Override
    public TransplantingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_transplanting_list_card,parent,false);
        TransplantingViewHolder holder = new TransplantingViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropTransplanting> cropTransplantings){

        this.cropTransplantingList.addAll(cropTransplantings);
        notifyDataSetChanged();
    }
    public void addCropTransplanting(CropTransplanting cropTransplanting){
        this.cropTransplantingList.add(cropTransplanting);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropTransplanting> cropTransplantings){

        this.cropTransplantingList.clear();
        this.cropTransplantingList.addAll(cropTransplantings);

        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull TransplantingViewHolder holder, int position) {
        CropTransplanting transplanting = cropTransplantingList.get(position);
        holder.operationDateTxt.setText(transplanting.getOperationDate());
        holder.totalSeedlingsTxt.setText(transplanting.getTotalSeedling()+"");
        holder.seedlingsPerHaTxt.setText(transplanting.getSeedlingPerHa()+"");
        holder.varietyEarlinessTxt.setText(transplanting.getVarietyEarliness());
        holder.harvestDueDateTxt.setText(transplanting.getOperationDate());
        holder.expectedYieldTxt.setText(transplanting.getExpectedYield()+"");
        holder.totalCostTxt.setText(transplanting.getTotalCost()+"");

    }

    @Override
    public int getItemCount() {
        return cropTransplantingList.size();
    }

    public class TransplantingViewHolder extends RecyclerView.ViewHolder {
        TextView operationDateTxt,totalSeedlingsTxt,seedlingsPerHaTxt,expectedYieldTxt,totalCostTxt,varietyEarlinessTxt,harvestDueDateTxt;
        ImageView moreButton ;

        public TransplantingViewHolder(View itemView) {
            super(itemView);
            operationDateTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_operation_date);
            totalSeedlingsTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_total_seedlings);
            seedlingsPerHaTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_seedlings_per_ha);
            varietyEarlinessTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_variety_earliness);
            harvestDueDateTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_operation_date);
            expectedYieldTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_expected_yield);
            totalCostTxt = itemView.findViewById(R.id.txt_view_crop_transplanting_card_total_cost);
            moreButton = itemView.findViewById(R.id.img_crop_irrigation_card_more);



            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropTransplanting cropTransplanting = cropTransplantingList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this Transplanting card?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropTransplanting(cropTransplanting.getId());
                                                cropTransplantingList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();

                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropTransplanting cropTransplanting = cropTransplantingList.get(getAdapterPosition());
                                Intent editTransplanting = new Intent(mContext, CropTransplantingManagerActivity.class);
                                editTransplanting.putExtra("cropTransplanting",cropTransplanting);
                                mContext.startActivity(editTransplanting);
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
