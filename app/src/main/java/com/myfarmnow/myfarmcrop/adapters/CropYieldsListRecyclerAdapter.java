package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.CropYieldRecord;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CropYieldsListRecyclerAdapter extends RecyclerView.Adapter<CropYieldsListRecyclerAdapter.CropYieldViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropYieldRecord> cropItemsList = new ArrayList<>();




    ArrayList<String> deleteItemsId= new ArrayList<>();



    public CropYieldsListRecyclerAdapter(Context context, ArrayList<CropYieldRecord> cropItems){
        cropItemsList.addAll(cropItems);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);
        cropItemsList.addAll(cropItems);


    }
    @NonNull
    @Override
    public CropYieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_yield_item_row,parent,false);

        CropYieldViewHolder holder = new CropYieldViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropYieldRecord> cropItems){

        this.cropItemsList.addAll(cropItems);
        notifyDataSetChanged();
    }

    public void addCropYieldRecord(CropYieldRecord cropItem){
        this.cropItemsList.add(cropItem);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropYieldRecord> cropItems){

        this.cropItemsList.clear();
        this.cropItemsList.addAll(cropItems);

        notifyDataSetChanged();
    }

    public ArrayList<String> getDeleteItemsId() {
        return deleteItemsId;
    }

    @Override
    public void onBindViewHolder(@NonNull final CropYieldViewHolder holder, int position) {

        CropYieldRecord record = cropItemsList.get(position);

        holder.cropNameTextView.setText(record.getCropName());
        holder.fieldTextView.setText(record.getFieldName());
        holder.varietyTextView.setText(record.getVariety());
        holder.croppingYearTextView.setText(record.getCroppingYear());
        holder.seasonTextView.setText(record.getSeason());
        holder.totalCostTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+NumberFormat.getInstance().format(record.getTotalCost()));
        holder.revenueTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+NumberFormat.getInstance().format(record.getTotalCost()));
        holder.marginTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+NumberFormat.getInstance().format(record.getMargin()));
        holder.returnOnInvestmentTextView.setText(record.getReturnOnInvestment()+"%");

    }



    @Override
    public int getItemCount() {
        return cropItemsList.size();
    }




    public class CropYieldViewHolder extends RecyclerView.ViewHolder{

        TextView cropNameTextView,fieldTextView,varietyTextView,croppingYearTextView,seasonTextView,
                totalCostTextView,revenueTextView,marginTextView,returnOnInvestmentTextView;
     
        public CropYieldViewHolder(View itemView) {
            super(itemView);
            cropNameTextView = itemView.findViewById(R.id.text_view_crop_yield_crop_name);
            fieldTextView = itemView.findViewById(R.id.text_view_crop_yield_field);
            varietyTextView = itemView.findViewById(R.id.text_view_crop_yield_variety);
            croppingYearTextView = itemView.findViewById(R.id.text_view_crop_yield_cropping_year);
            seasonTextView = itemView.findViewById(R.id.text_view_crop_yield_season);
            totalCostTextView = itemView.findViewById(R.id.text_view_view_crop_yield_total_cost);
            revenueTextView = itemView.findViewById(R.id.text_view_view_crop_yield_revenue);
            marginTextView = itemView.findViewById(R.id.text_view_crop_yield_margin);
            returnOnInvestmentTextView = itemView.findViewById(R.id.text_view_view_crop_yield_roi);

        }



    }

}
