package com.myfarmnow.myfarmcrop.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
//import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropBillManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropPurchaseOrderManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropPurchaseOrderPreviewActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropPurchaseOrder;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CropPurchaseOrdersListRecyclerAdapter extends RecyclerView.Adapter<CropPurchaseOrdersListRecyclerAdapter.PurchaseOrderViewHolder>  {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropPurchaseOrder> cropPurchaseOrdersList = new ArrayList<>();

    public CropPurchaseOrdersListRecyclerAdapter(Context context, ArrayList<CropPurchaseOrder> cropPurchaseOrders){
        cropPurchaseOrdersList.addAll(cropPurchaseOrders);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public PurchaseOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_purchase_order_list_card,parent,false);

        PurchaseOrderViewHolder holder = new PurchaseOrderViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropPurchaseOrder> cropPurchaseOrders){

        this.cropPurchaseOrdersList.addAll(cropPurchaseOrders);
        notifyDataSetChanged();
    }

    public void addCropPurchaseOrder(CropPurchaseOrder cropPurchaseOrder){
        this.cropPurchaseOrdersList.add(cropPurchaseOrder);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropPurchaseOrder> cropPurchaseOrders){

        this.cropPurchaseOrdersList.clear();
        this.cropPurchaseOrdersList.addAll(cropPurchaseOrders);

        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull PurchaseOrderViewHolder holder, int position) {

        CropPurchaseOrder estimate = cropPurchaseOrdersList.get(position);
        holder.nameTextView.setText(estimate.getSupplierName());
        holder.dateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(estimate.getDeliveryDate()));
        holder.purchaseOrderNumberTxt.setText(estimate.getNumber());
        holder.statusTextView.setText(estimate.getStatus());
        holder.amountTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+NumberFormat.getInstance().format(estimate.computeTotal()));
    }



    @Override
    public int getItemCount() {
        return cropPurchaseOrdersList.size();
    }


    public class PurchaseOrderViewHolder extends RecyclerView.ViewHolder{
        TextView amountTextView, statusTextView, nameTextView, purchaseOrderNumberTxt, dateTextView;
        ImageView moreButton;
        //LinearLayout referenceNumberTxtView;

        public PurchaseOrderViewHolder(View itemView) {
            super(itemView);
            statusTextView = itemView.findViewById(R.id.txt_crop_purchase_order_card_status);
            amountTextView = itemView.findViewById(R.id.txt_crop_purchase_order_card_amount);
            purchaseOrderNumberTxt = itemView.findViewById(R.id.txt_crop_purchase_order_card_number);
            dateTextView = itemView.findViewById(R.id.txt_crop_purchase_order_card_delivery_date);
            nameTextView = itemView.findViewById(R.id.txt_crop_purchase_order_card_supplier_name);
            moreButton = itemView.findViewById(R.id.img_crop_purchase_order_card_more);

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final CropPurchaseOrder cropPurchaseOrder = cropPurchaseOrdersList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete "+cropPurchaseOrder.getNumber()+" ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropPurchaseOrder(cropPurchaseOrder.getId());
                                                cropPurchaseOrdersList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropPurchaseOrder cropPurchaseOrder = cropPurchaseOrdersList.get(getAdapterPosition());
                                Intent editPurchaseOrder = new Intent(mContext, CropPurchaseOrderManagerActivity.class);
                                editPurchaseOrder.putExtra("cropPurchaseOrder",cropPurchaseOrder);
                                mContext.startActivity(editPurchaseOrder);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_convert_to_bill))){
                                CropPurchaseOrder cropPurchaseOrder = cropPurchaseOrdersList.get(getAdapterPosition());
                                Intent recordPayment = new Intent(mContext, CropBillManagerActivity.class);
                                recordPayment.putExtra("cropPurchaseOrder",cropPurchaseOrder);
                                mContext.startActivity(recordPayment);
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_preview_receipt))){
                                CropPurchaseOrder cropPurchaseOrder = cropPurchaseOrdersList.get(getAdapterPosition());
                                Intent editPurchaseOrder = new Intent(mContext, CropPurchaseOrderPreviewActivity.class);
                                editPurchaseOrder.putExtra("cropPurchaseOrder",cropPurchaseOrder);
                                editPurchaseOrder.putExtra("action",CropPurchaseOrderPreviewActivity.BILL_ACTION_PREVIEW);
                                mContext.startActivity(editPurchaseOrder);
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_dowloand_pdf))){
                                CropPurchaseOrder cropPurchaseOrder = cropPurchaseOrdersList.get(getAdapterPosition());
                                Intent editPurchaseOrder = new Intent(mContext, CropPurchaseOrderPreviewActivity.class);
                                editPurchaseOrder.putExtra("cropPurchaseOrder",cropPurchaseOrder);
                                editPurchaseOrder.putExtra("action",CropPurchaseOrderPreviewActivity.BILL_ACTION_DOWNLOAD);
                                mContext.startActivity(editPurchaseOrder);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_email))){
                                CropPurchaseOrder cropPurchaseOrder = cropPurchaseOrdersList.get(getAdapterPosition());
                                Intent editPurchaseOrder = new Intent(mContext, CropPurchaseOrderPreviewActivity.class);
                                editPurchaseOrder.putExtra("cropPurchaseOrder",cropPurchaseOrder);
                                editPurchaseOrder.putExtra("action",CropPurchaseOrderPreviewActivity.BILL_ACTION_EMAIL);
                                mContext.startActivity(editPurchaseOrder);
                            }
                            return true;
                        }
                    });
                    popup.getMenu().add(R.string.label_preview_receipt);
                    popup.getMenu().add(R.string.label_dowloand_pdf);
                    popup.getMenu().add(R.string.label_email);
                    //popup.getMenu().add(R.string.label_share_link);
                    if(!cropPurchaseOrdersList.get(getAdapterPosition()).getStatus().equals(mContext.getString(R.string.purchase_order_status_billed))){
                        popup.getMenu().add(R.string.label_convert_to_bill);
                    }

                    popup.getMenu().add(R.string.label_edit);
                    popup.getMenu().add(R.string.label_delete);
                    popup.show();


                }
            });


        }
    }
}
