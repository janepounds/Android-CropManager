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
import com.myfarmnow.myfarmcrop.activities.CropPaymentManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropPayment;

import java.util.ArrayList;

public class CropPaymentsListRecyclerAdapter extends RecyclerView.Adapter<CropPaymentsListRecyclerAdapter.PaymentViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropPayment> cropPaymentsList = new ArrayList<>();

    public CropPaymentsListRecyclerAdapter(Context context, ArrayList<CropPayment> cropPayments){
        cropPaymentsList.addAll(cropPayments);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);
    }
    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_payments_list_card,parent,false);
        PaymentViewHolder holder = new PaymentViewHolder(view);
        return holder;
    }

    public void appendList(ArrayList<CropPayment> cropPayments){

        this.cropPaymentsList.addAll(cropPayments);
        notifyDataSetChanged();
    }

    public void addCropPayment(CropPayment cropPayment){
        this.cropPaymentsList.add(cropPayment);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropPayment> cropPayments){

        this.cropPaymentsList.clear();
        this.cropPaymentsList.addAll(cropPayments);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {

        CropPayment payment = cropPaymentsList.get(position);
        holder.nameTextView.setText(payment.getCustomerName());
        holder.dateTextView.setText(payment.getDate());
        holder.modeTextView.setText(payment.getMode());
        holder.referenceNumberTxt.setText(payment.getInvoiceNumber());
        holder.paymentNumberTextView.setText(payment.getPaymentNumber());
        holder.amountTextView.setText(payment.getAmount()+"");
        if (payment.getInvoiceId()==null){
            holder.invoicePaymentLayout.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return cropPaymentsList.size();
    }


    public class PaymentViewHolder extends RecyclerView.ViewHolder{

        TextView amountTextView, referenceNumberTxt, modeTextView, nameTextView, paymentNumberTextView, dateTextView;
        ImageView moreButton, deleteButton;
        LinearLayout invoicePaymentLayout;
        public PaymentViewHolder(View itemView) {
            super(itemView);

            amountTextView = itemView.findViewById(R.id.txt_crop_payment_card_amount);
            paymentNumberTextView = itemView.findViewById(R.id.txt_crop_payment_card_payment_number);
            dateTextView = itemView.findViewById(R.id.txt_crop_payment_card_date);
            modeTextView = itemView.findViewById(R.id.txt_crop_payment_card_mode);
            referenceNumberTxt = itemView.findViewById(R.id.txt_crop_payment_card_invoice_number);

            nameTextView = itemView.findViewById(R.id.txt_crop_payment_card_customer_name);
            moreButton = itemView.findViewById(R.id.img_crop_payment_card_more);
            invoicePaymentLayout = itemView.findViewById(R.id.layout_crop_payment_card_invoice);

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropPayment cropPayment = cropPaymentsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle(R.string.label_confirm)
                                        .setMessage(wrapper.getString(R.string.delete_prompt_message)+cropPayment.getPaymentNumber()+" ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropPayment(cropPayment.getId());
                                                cropPaymentsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropPayment cropPayment = cropPaymentsList.get(getAdapterPosition());
                                Intent editPayment = new Intent(mContext, CropPaymentManagerActivity.class);
                                editPayment.putExtra("cropPayment",cropPayment);
                                mContext.startActivity(editPayment);
                            }
                            return true;
                        }
                    });
                    popup.getMenu().add(R.string.label_preview_receipt);
                    popup.getMenu().add(R.string.label_dowloand_pdf);
                    popup.getMenu().add(R.string.label_email);
                    popup.getMenu().add(R.string.label_share_link);
                    popup.getMenu().add(R.string.label_edit);
                    popup.getMenu().add(R.string.label_delete);
                    popup.show();


                }
            });
        }

    }
}
