package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.myfarmnow.myfarmcrop.activities.CropBillManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropBillPreviewActivity;
import com.myfarmnow.myfarmcrop.activities.CropBillPaymentManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropBill;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CropBillsListRecyclerAdapter extends RecyclerView.Adapter<CropBillsListRecyclerAdapter.BillViewHolder> {


    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropBill> cropBillsList = new ArrayList<>();

    public CropBillsListRecyclerAdapter(Context context, ArrayList<CropBill> cropBills){
        cropBillsList.addAll(cropBills);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_bill_list_card,parent,false);
        BillViewHolder holder = new BillViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropBill> cropBills){

        this.cropBillsList.addAll(cropBills);
        notifyDataSetChanged();
    }

    public void addCropBill(CropBill cropBill){
        this.cropBillsList.add(cropBill);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropBill> cropBills){

        this.cropBillsList.clear();
        this.cropBillsList.addAll(cropBills);

        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        CropBill estimate = cropBillsList.get(position);
        holder.supplierTextView.setText(estimate.getSupplierName());
        holder.dueDateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(estimate.getDueDate()));
        holder.billDateTextView.setText(
                        CropSettingsSingleton.getInstance().convertToUserFormat(estimate.getBillDate()));

        holder.statusTextView.setText(estimate.determineStatus(mContext));
        holder.billNumberTextView.setText(estimate.getNumber());

        holder.amountTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+" " +NumberFormat.getInstance().format(estimate.computeBalance()));

    }

    @Override
    public int getItemCount() {
        return cropBillsList.size();
    }
    public class BillViewHolder extends RecyclerView.ViewHolder{

        TextView amountTextView,  statusTextView, supplierTextView, billNumberTextView, dueDateTextView,billDateTextView;
        ImageView moreButton;

        public BillViewHolder(View itemView) {
            super(itemView);

            amountTextView = itemView.findViewById(R.id.txt_crop_bill_card_balance_due);
            billNumberTextView = itemView.findViewById(R.id.txt_crop_bill_card_number);
            dueDateTextView = itemView.findViewById(R.id.txt_crop_bill_card_bill_due);
            billDateTextView = itemView.findViewById(R.id.txt_crop_bill_card_bill_date);
            statusTextView = itemView.findViewById(R.id.txt_crop_bill_card_status);

            supplierTextView = itemView.findViewById(R.id.txt_crop_bill_card_supplier_name);


            moreButton = itemView.findViewById(R.id.img_crop_bill_card_more);


            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final CropBill cropBill = cropBillsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete "+cropBill.getNumber()+" ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropBill(cropBill.getId());
                                                cropBillsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropBill cropBill = cropBillsList.get(getAdapterPosition());
                                Intent editBill = new Intent(mContext, CropBillManagerActivity.class);
                                editBill.putExtra("cropBill",cropBill);
                                mContext.startActivity(editBill);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_record_payment))){
                                CropBill cropBill = cropBillsList.get(getAdapterPosition());
                                Intent recordPayment = new Intent(mContext, CropBillPaymentManagerActivity.class);
                                recordPayment.putExtra("billId",cropBill.getId());
                                mContext.startActivity(recordPayment);
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_preview_receipt))){
                                CropBill cropBill = cropBillsList.get(getAdapterPosition());
                                Intent editBill = new Intent(mContext, CropBillPreviewActivity.class);
                                editBill.putExtra("cropBill",cropBill);
                                editBill.putExtra("action",CropBillPreviewActivity.BILL_ACTION_PREVIEW);
                                mContext.startActivity(editBill);
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_dowloand_pdf))){
                                CropBill cropBill = cropBillsList.get(getAdapterPosition());
                                Intent editBill = new Intent(mContext, CropBillPreviewActivity.class);
                                editBill.putExtra("cropBill",cropBill);
                                editBill.putExtra("action",CropBillPreviewActivity.BILL_ACTION_DOWNLOAD);
                                mContext.startActivity(editBill);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.preview_bill))){
                                CropBill cropBill = cropBillsList.get(getAdapterPosition());
                                Intent editBill = new Intent(mContext, CropBillPreviewActivity.class);
                                editBill.putExtra("cropBill",cropBill);
                                editBill.putExtra("action",CropBillPreviewActivity.BILL_ACTION_PREVIEW);
                                mContext.startActivity(editBill);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_email))){
                                CropBill cropBill = cropBillsList.get(getAdapterPosition());
                                Intent editBill = new Intent(mContext, CropBillPreviewActivity.class);
                                editBill.putExtra("cropBill",cropBill);
                                editBill.putExtra("action",CropBillPreviewActivity.BILL_ACTION_EMAIL);
                                mContext.startActivity(editBill);
                            }
                            return true;
                        }
                    });
                    popup.getMenu().add(R.string.preview_bill);
                    popup.getMenu().add(R.string.label_dowloand_pdf);
                    popup.getMenu().add(R.string.label_email);
                   // popup.getMenu().add(R.string.label_share_link);
                    popup.getMenu().add(R.string.label_record_payment);
                    popup.getMenu().add(R.string.label_edit);
                    popup.getMenu().add(R.string.label_delete);
                    popup.show();


                }
            });


        }

    }


}
