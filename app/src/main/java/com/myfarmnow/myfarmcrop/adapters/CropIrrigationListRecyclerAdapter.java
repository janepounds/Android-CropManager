package com.myfarmnow.myfarmcrop.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropIrrigationManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropIrrigation;

import java.util.ArrayList;

public class CropIrrigationListRecyclerAdapter  extends RecyclerView.Adapter<CropIrrigationListRecyclerAdapter.IrrigationViewHolder>  {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropIrrigation> cropIrrigationList = new ArrayList<>();
    public CropIrrigationListRecyclerAdapter(Context context, ArrayList<CropIrrigation> cropIrrigations){
        cropIrrigationList.addAll(cropIrrigations);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        }


    @NonNull
    @Override
    public IrrigationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_irrigation_list_card,parent,false);
        IrrigationViewHolder holder = new IrrigationViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropIrrigation> cropIrrigations){

        this.cropIrrigationList.addAll(cropIrrigations);
        notifyDataSetChanged();
    }
    public void addCropIrrigation(CropIrrigation cropIrrigation){
        this.cropIrrigationList.add(cropIrrigation);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropIrrigation> cropIrrigations){

        this.cropIrrigationList.clear();
        this.cropIrrigationList.addAll(cropIrrigations);

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull IrrigationViewHolder holder, int position) {
        CropIrrigation irrigation = cropIrrigationList.get(position);
        holder.operationDateTxt.setText(irrigation.getOperationDate());
        holder.areaIrrigationTxt.setText(irrigation.getAreaIrrigated()+"");
        holder.systemRateTxt.setText(irrigation.getSystemRate()+" l/hr");
        holder.durationTxt.setText(irrigation.getAreaIrrigated()+" hrs");
        holder.totalWaterQuantity.setText(irrigation.getTotalWaterQuantity()+" ltrs");
        holder.recurrenceTxt.setText(irrigation.getRecurrence());
        holder.totalCostTxt.setText(irrigation.getTotalCost()+"");

    }

    //TODO ADJUSTING THE SIDE LINE FOR THE CARD
    @Override
    public int getItemCount() {
        return cropIrrigationList.size();
    }

    public class IrrigationViewHolder extends RecyclerView.ViewHolder {
        TextView operationDateTxt,areaIrrigationTxt,systemRateTxt,durationTxt,totalWaterQuantity,recurrenceTxt,totalCostTxt;
        ImageView moreButton ;

        public IrrigationViewHolder(View itemView) {
            super(itemView);
            operationDateTxt = itemView.findViewById(R.id.txt_view_crop_irrigation_card_operation_date);
            areaIrrigationTxt = itemView.findViewById(R.id.txt_crop_irrigation_card_irrigated_area);
            systemRateTxt = itemView.findViewById(R.id.txt_view_crop_irrigation_card_system_rate);
            durationTxt = itemView.findViewById(R.id.txt_view_crop_irrigation_card_duration);
            totalWaterQuantity = itemView.findViewById(R.id.txt_view_crop_irrigation_card_total_water_quantity);
            recurrenceTxt = itemView.findViewById(R.id.txt_view_crop_irrigation_card_recurrence);
            totalCostTxt = itemView.findViewById(R.id.txt_view_crop_irrigation_card_total_cost);


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
                                final CropIrrigation cropIrrigation = cropIrrigationList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete this irrigation card?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropIrrigation(cropIrrigation.getId());
                                                cropIrrigationList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();

                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropIrrigation cropIrrigation = cropIrrigationList.get(getAdapterPosition());
                                Intent editIrrigation = new Intent(mContext, CropIrrigationManagerActivity.class);
                                editIrrigation.putExtra("cropIrrigation",cropIrrigation);
                                mContext.startActivity(editIrrigation);
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
