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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropInvoiceManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropSalesOrderManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropSalesOrderPreviewActivity;
import com.myfarmnow.myfarmcrop.activities.CropPaymentManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropEstimate;
import com.myfarmnow.myfarmcrop.models.CropSalesOrder;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CropSalesOrdersListRecyclerAdapter extends RecyclerView.Adapter<CropSalesOrdersListRecyclerAdapter.SalesOrderViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropSalesOrder> cropSalesOrdersList = new ArrayList<>();

    public CropSalesOrdersListRecyclerAdapter(Context context, ArrayList<CropSalesOrder> cropSalesOrders){
        cropSalesOrdersList.addAll(cropSalesOrders);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public SalesOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_estimate_list_card,parent,false);

        SalesOrderViewHolder holder = new SalesOrderViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropSalesOrder> cropSalesOrders){

        this.cropSalesOrdersList.addAll(cropSalesOrders);
        notifyDataSetChanged();
    }

    public void addCropSalesOrder(CropSalesOrder cropSalesOrder){
        this.cropSalesOrdersList.add(cropSalesOrder);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropSalesOrder> cropSalesOrders){

        this.cropSalesOrdersList.clear();
        this.cropSalesOrdersList.addAll(cropSalesOrders);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull SalesOrderViewHolder holder, int position) {

        CropSalesOrder cropSalesOrder = cropSalesOrdersList.get(position);
        holder.nameTextView.setText(cropSalesOrder.getCustomerName());
        holder.dateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(cropSalesOrder.getDate()));
        holder.referenceNumberTxt.setText(cropSalesOrder.getReferenceNumber());
        holder.statusTextView.setText(cropSalesOrder.getStatus());
        holder.estimateNumberTextView.setText(cropSalesOrder.getNumber());
       // holder.referenceNumberTxtView.setVisibility(View.VISIBLE);

        holder.amountTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+NumberFormat.getInstance().format(cropSalesOrder.computeTotal()));
    }



    @Override
    public int getItemCount() {
        return cropSalesOrdersList.size();
    }


    public class SalesOrderViewHolder extends RecyclerView.ViewHolder{

        TextView amountTextView, referenceNumberTxt, statusTextView, nameTextView, estimateNumberTextView, dateTextView;
        ImageView moreButton;
        LinearLayout referenceNumberTxtView;
        public SalesOrderViewHolder(View itemView) {
            super(itemView);

            statusTextView = itemView.findViewById(R.id.txt_crop_estimate_card_status);
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
                                final CropSalesOrder cropSalesOrder = cropSalesOrdersList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete "+cropSalesOrder.getNumber()+" ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropSalesOrder(cropSalesOrder.getId());
                                                cropSalesOrdersList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropSalesOrder cropSalesOrder = cropSalesOrdersList.get(getAdapterPosition());
                                Intent editSalesOrder = new Intent(mContext, CropSalesOrderManagerActivity.class);
                                editSalesOrder.putExtra("cropSalesOrder",cropSalesOrder);
                                mContext.startActivity(editSalesOrder);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_convert_to_invoice))){
                                CropSalesOrder cropSalesOrder = cropSalesOrdersList.get(getAdapterPosition());
                                Intent recordPayment = new Intent(mContext, CropInvoiceManagerActivity.class);
                                recordPayment.putExtra("cropSalesOrder",cropSalesOrder);
                                mContext.startActivity(recordPayment);
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_preview_receipt))){
                                CropSalesOrder cropSalesOrder = cropSalesOrdersList.get(getAdapterPosition());
                                Intent editSalesOrder = new Intent(mContext, CropSalesOrderPreviewActivity.class);
                                editSalesOrder.putExtra("cropSalesOrder",cropSalesOrder);
                                editSalesOrder.putExtra("action",CropSalesOrderPreviewActivity.SALES_ORDER_ACTION_PREVIEW);
                                mContext.startActivity(editSalesOrder);
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_dowloand_pdf))){
                                CropSalesOrder cropSalesOrder = cropSalesOrdersList.get(getAdapterPosition());
                                Intent editSalesOrder = new Intent(mContext, CropSalesOrderPreviewActivity.class);
                                editSalesOrder.putExtra("cropSalesOrder",cropSalesOrder);
                                editSalesOrder.putExtra("action",CropSalesOrderPreviewActivity.SALES_ORDER_ACTION_DOWNLOAD);
                                mContext.startActivity(editSalesOrder);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_email))){
                                CropSalesOrder cropSalesOrder = cropSalesOrdersList.get(getAdapterPosition());
                                Intent editSalesOrder = new Intent(mContext, CropSalesOrderPreviewActivity.class);
                                editSalesOrder.putExtra("cropSalesOrder",cropSalesOrder);
                                editSalesOrder.putExtra("action",CropSalesOrderPreviewActivity.SALES_ORDER_ACTION_EMAIL);
                                mContext.startActivity(editSalesOrder);
                            }
                            return true;
                        }
                    });
                    popup.getMenu().add(R.string.label_preview_receipt);
                    popup.getMenu().add(R.string.label_dowloand_pdf);
                    popup.getMenu().add(R.string.label_email);
                  //  popup.getMenu().add(R.string.label_share_link);
                    if(!cropSalesOrdersList.get(getAdapterPosition()).getStatus().equals(mContext.getString(R.string.estimate_status_invoiced))){
                        popup.getMenu().add(R.string.label_convert_to_invoice);
                    }
                    popup.getMenu().add(R.string.label_edit);
                    popup.getMenu().add(R.string.label_delete);
                    popup.show();


                }
            });



            
        }

    }
}
