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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropInvoiceManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropPaymentManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInvoice;

import java.util.ArrayList;

public class CropInvoicesListRecyclerAdapter extends RecyclerView.Adapter<CropInvoicesListRecyclerAdapter.InvoiceViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropInvoice> cropInvoicesList = new ArrayList<>();

    public CropInvoicesListRecyclerAdapter(Context context, ArrayList<CropInvoice> cropInvoices){
        cropInvoicesList.addAll(cropInvoices);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);

        Log.d("CROP FIELDS",cropInvoicesList.size()+" ");
    }
    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_estimate_list_card,parent,false);

        InvoiceViewHolder holder = new InvoiceViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropInvoice> cropInvoices){

        this.cropInvoicesList.addAll(cropInvoices);
        notifyDataSetChanged();
    }

    public void addCropInvoice(CropInvoice cropInvoice){
        this.cropInvoicesList.add(cropInvoice);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropInvoice> cropInvoices){

        this.cropInvoicesList.clear();
        this.cropInvoicesList.addAll(cropInvoices);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {

        CropInvoice estimate = cropInvoicesList.get(position);
        holder.nameTextView.setText(estimate.getCustomerName());
        holder.dateTextView.setText(estimate.getDate());
        holder.statusTextView.setText(estimate.determineStatus(mContext));
        holder.estimateNumberTextView.setText(estimate.getNumber());
        holder.orderNumberTxt.setText(estimate.getOrderNumber());
        holder.orderNumberLayout.setVisibility(View.VISIBLE);

        holder.amountTextView.setText(estimate.getBalance()+"");
    }



    @Override
    public int getItemCount() {
        return cropInvoicesList.size();
    }


    public class InvoiceViewHolder extends RecyclerView.ViewHolder{

        TextView amountTextView, orderNumberTxt, statusTextView, nameTextView, estimateNumberTextView, dateTextView;
        ImageView moreButton, deleteButton;
        LinearLayout orderNumberLayout;
        public InvoiceViewHolder(View itemView) {
            super(itemView);

            amountTextView = itemView.findViewById(R.id.txt_crop_estimate_card_amount);
            estimateNumberTextView = itemView.findViewById(R.id.txt_crop_estimate_card_estimate_number);
            dateTextView = itemView.findViewById(R.id.txt_crop_estimate_card_estimate_date);
            orderNumberTxt = itemView.findViewById(R.id.txt_crop_estimate_card_reference_number);
            statusTextView = itemView.findViewById(R.id.txt_crop_estimate_card_status);
          //  taxTextView = itemView.findViewById(R.id.txt_crop_estimate_card_phone);
            nameTextView = itemView.findViewById(R.id.txt_crop_estimate_card_customer_name);
            deleteButton = itemView.findViewById(R.id.img_crop_estimate_card_delete);
            moreButton = itemView.findViewById(R.id.img_crop_estimate_card_more);
            orderNumberLayout = itemView.findViewById(R.id.layout_crop_estimate_card_order_number);


            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))){
                                final CropInvoice cropInvoice = cropInvoicesList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle("Confirm")
                                        .setMessage("Do you really want to delete "+cropInvoice.getNumber()+" ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropInvoice(cropInvoice.getId());
                                                cropInvoicesList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))){
                                CropInvoice cropInvoice = cropInvoicesList.get(getAdapterPosition());
                                Intent editInvoice = new Intent(mContext, CropInvoiceManagerActivity.class);
                                editInvoice.putExtra("cropInvoice",cropInvoice);
                                mContext.startActivity(editInvoice);
                            }
                            else if (item.getTitle().toString().equals(mContext.getString(R.string.label_record_payment))){
                                CropInvoice cropInvoice = cropInvoicesList.get(getAdapterPosition());
                                Intent recordPayment = new Intent(mContext, CropPaymentManagerActivity.class);
                                recordPayment.putExtra("invoiceId",cropInvoice.getId());
                                mContext.startActivity(recordPayment);
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
