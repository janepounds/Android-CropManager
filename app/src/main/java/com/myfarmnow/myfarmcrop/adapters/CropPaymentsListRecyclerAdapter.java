package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

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
import com.myfarmnow.myfarmcrop.models.CropInvoicePayment;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CropPaymentsListRecyclerAdapter extends RecyclerView.Adapter<CropPaymentsListRecyclerAdapter.PaymentViewHolder> {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropInvoicePayment> cropInvoicePaymentsList = new ArrayList<>();

    public CropPaymentsListRecyclerAdapter(Context context, ArrayList<CropInvoicePayment> cropInvoicePayments){
        cropInvoicePaymentsList.addAll(cropInvoicePayments);
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

    public void appendList(ArrayList<CropInvoicePayment> cropInvoicePayments){

        this.cropInvoicePaymentsList.addAll(cropInvoicePayments);
        notifyDataSetChanged();
    }

    public void addCropPayment(CropInvoicePayment cropInvoicePayment){
        this.cropInvoicePaymentsList.add(cropInvoicePayment);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropInvoicePayment> cropInvoicePayments){

        this.cropInvoicePaymentsList.clear();
        this.cropInvoicePaymentsList.addAll(cropInvoicePayments);

        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {

        CropInvoicePayment payment = cropInvoicePaymentsList.get(position);
        holder.nameTextView.setText(payment.getCustomerName());
        holder.dateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(payment.getDate()));
        holder.modeTextView.setText(payment.getMode());
        holder.referenceNumberTxt.setText(payment.getInvoiceNumber());
        holder.paymentNumberTextView.setText(payment.getPaymentNumber());
        holder.amountTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+NumberFormat.getInstance().format(payment.getAmount()));
        if (payment.getInvoiceId()==null){
            holder.invoicePaymentLayout.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return cropInvoicePaymentsList.size();
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
                                final CropInvoicePayment cropInvoicePayment = cropInvoicePaymentsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle(R.string.label_confirm)
                                        .setMessage(wrapper.getString(R.string.delete_prompt_message)+ cropInvoicePayment.getPaymentNumber()+" ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropPayment(cropInvoicePayment.getId());
                                                cropInvoicePaymentsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropInvoicePayment cropInvoicePayment = cropInvoicePaymentsList.get(getAdapterPosition());
                                Intent editPayment = new Intent(mContext, CropPaymentManagerActivity.class);
                                editPayment.putExtra("cropInvoicePayment", cropInvoicePayment);
                                mContext.startActivity(editPayment);
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
