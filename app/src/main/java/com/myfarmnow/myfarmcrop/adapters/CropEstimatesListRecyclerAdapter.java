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
import com.myfarmnow.myfarmcrop.activities.CropEstimateManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropEstimate;

import java.util.ArrayList;

public class CropEstimatesListRecyclerAdapter extends RecyclerView.Adapter<CropEstimatesListRecyclerAdapter.EstimateViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropEstimate> cropEstimatesList = new ArrayList<>();

    public CropEstimatesListRecyclerAdapter(Context context, ArrayList<CropEstimate> cropEstimates){
        cropEstimatesList.addAll(cropEstimates);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP FIELDS",cropEstimatesList.size()+" ");
    }
    @NonNull
    @Override
    public EstimateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_estimate_list_card,parent,false);

        EstimateViewHolder holder = new EstimateViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropEstimate> cropEstimates){

        this.cropEstimatesList.addAll(cropEstimates);
        notifyDataSetChanged();
    }

    public void addCropEstimate(CropEstimate cropEstimate){
        this.cropEstimatesList.add(cropEstimate);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropEstimate> cropEstimates){

        this.cropEstimatesList.clear();
        this.cropEstimatesList.addAll(cropEstimates);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull EstimateViewHolder holder, int position) {

        CropEstimate estimate = cropEstimatesList.get(position);
        holder.nameTextView.setText(estimate.getCustomerName());
        holder.dateTextView.setText(estimate.getDate());
        //holder.orderNumberTxt.setText(estimate.getCompany());
        holder.estimateNumberTextView.setText(estimate.getNumber());
        //holder.dateTextView.setText(estimate.getD());

        holder.amountTextView.setText(estimate.computeTotal()+"");
    }



    @Override
    public int getItemCount() {
        return cropEstimatesList.size();
    }


    public class EstimateViewHolder extends RecyclerView.ViewHolder{

        TextView amountTextView, referenceNumberTxt, taxTextView, nameTextView, estimateNumberTextView, dateTextView;
        ImageView editButton, deleteButton;
        public EstimateViewHolder(View itemView) {
            super(itemView);

            amountTextView = itemView.findViewById(R.id.txt_crop_estimate_card_amount);
            estimateNumberTextView = itemView.findViewById(R.id.txt_crop_estimate_card_estimate_number);
            dateTextView = itemView.findViewById(R.id.txt_crop_estimate_card_estimate_date);
            referenceNumberTxt = itemView.findViewById(R.id.txt_crop_estimate_card_reference_number);
          //  taxTextView = itemView.findViewById(R.id.txt_crop_estimate_card_phone);
            nameTextView = itemView.findViewById(R.id.txt_crop_estimate_card_customer_name);
            deleteButton = itemView.findViewById(R.id.img_crop_estimate_card_delete);
            editButton = itemView.findViewById(R.id.img_crop_estimate_card_edit);


            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CropEstimate cropEstimate = cropEstimatesList.get(getAdapterPosition());
                    Intent editEstimate = new Intent(mContext, CropEstimateManagerActivity.class);
                    editEstimate.putExtra("cropEstimate",cropEstimate);
                    mContext.startActivity(editEstimate);
                }
            });
       
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CropEstimate cropEstimate = cropEstimatesList.get(getAdapterPosition());
                    new AlertDialog.Builder(mContext)
                            .setTitle("Confirm")
                            .setMessage("Do you really want to delete "+cropEstimate.getNumber()+" ?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {

                                    MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropEstimate(cropEstimate.getId());
                                    cropEstimatesList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());

                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
        }

    }
}
