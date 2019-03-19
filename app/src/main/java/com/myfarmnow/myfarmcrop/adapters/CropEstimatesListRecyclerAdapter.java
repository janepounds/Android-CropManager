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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropEstimateManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropEstimateManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropEstimatePreviewActivity;
import com.myfarmnow.myfarmcrop.activities.CropPaymentManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropEstimate;
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
        ImageView moreButton;
        public EstimateViewHolder(View itemView) {
            super(itemView);

            amountTextView = itemView.findViewById(R.id.txt_crop_estimate_card_amount);
            estimateNumberTextView = itemView.findViewById(R.id.txt_crop_estimate_card_estimate_number);
            dateTextView = itemView.findViewById(R.id.txt_crop_estimate_card_estimate_date);
            referenceNumberTxt = itemView.findViewById(R.id.txt_crop_estimate_card_reference_number);
          //  taxTextView = itemView.findViewById(R.id.txt_crop_estimate_card_phone);
            nameTextView = itemView.findViewById(R.id.txt_crop_estimate_card_customer_name);
         
            moreButton = itemView.findViewById(R.id.img_crop_estimate_card_more);

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
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
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropEstimate cropEstimate = cropEstimatesList.get(getAdapterPosition());
                                Intent editEstimate = new Intent(mContext, CropEstimateManagerActivity.class);
                                editEstimate.putExtra("cropEstimate",cropEstimate);
                                mContext.startActivity(editEstimate);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_record_payment))){
                                CropEstimate cropEstimate = cropEstimatesList.get(getAdapterPosition());
                                Intent recordPayment = new Intent(mContext, CropPaymentManagerActivity.class);
                                recordPayment.putExtra("invoiceId",cropEstimate.getId());
                                mContext.startActivity(recordPayment);
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_preview_receipt))){
                                CropEstimate cropEstimate = cropEstimatesList.get(getAdapterPosition());
                                Intent editEstimate = new Intent(mContext, CropEstimatePreviewActivity.class);
                                editEstimate.putExtra("cropEstimate",cropEstimate);
                                editEstimate.putExtra("action",CropEstimatePreviewActivity.INVOICE_ACTION_PREVIEW);
                                mContext.startActivity(editEstimate);
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_dowloand_pdf))){
                                CropEstimate cropEstimate = cropEstimatesList.get(getAdapterPosition());
                                Intent editEstimate = new Intent(mContext, CropEstimatePreviewActivity.class);
                                editEstimate.putExtra("cropEstimate",cropEstimate);
                                editEstimate.putExtra("action",CropEstimatePreviewActivity.INVOICE_ACTION_DOWNLOAD);
                                mContext.startActivity(editEstimate);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_email))){
                                CropEstimate cropEstimate = cropEstimatesList.get(getAdapterPosition());
                                Intent editEstimate = new Intent(mContext, CropEstimatePreviewActivity.class);
                                editEstimate.putExtra("cropEstimate",cropEstimate);
                                editEstimate.putExtra("action",CropEstimatePreviewActivity.INVOICE_ACTION_EMAIL);
                                mContext.startActivity(editEstimate);
                            }
                            return true;
                        }
                    });
                    popup.getMenu().add(R.string.label_preview_receipt);
                    popup.getMenu().add(R.string.label_dowloand_pdf);
                    popup.getMenu().add(R.string.label_email);
                    popup.getMenu().add(R.string.label_share_link);
                    popup.getMenu().add(R.string.label_record_payment);
                    popup.getMenu().add(R.string.label_edit);
                    popup.getMenu().add(R.string.label_delete);
                    popup.show();


                }
            });



            
        }

    }
}
