package com.myfarmnow.myfarmcrop.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropPaymentBillManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropPaymentBill;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CropPaymentBillsListRecyclerAdapter extends RecyclerView.Adapter<CropPaymentBillsListRecyclerAdapter.PaymentBillViewHolder>  {

    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropPaymentBill> cropPaymentBillsList = new ArrayList<>();

    public CropPaymentBillsListRecyclerAdapter(Context context, ArrayList<CropPaymentBill> cropPaymentBills){
        cropPaymentBillsList.addAll(cropPaymentBills);
        mContext =context;
        layoutInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public PaymentBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.crop_payment_bill_list_card,parent,false);
        PaymentBillViewHolder holder = new PaymentBillViewHolder(view);
        return holder;
    }
    public void appendList(ArrayList<CropPaymentBill> cropPaymentBills){

        this.cropPaymentBillsList.addAll(cropPaymentBills);
        notifyDataSetChanged();
    }

    public void addCropPaymentBill(CropPaymentBill cropPaymentBill){
        this.cropPaymentBillsList.add(cropPaymentBill);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropPaymentBill> cropPaymentBills){

        this.cropPaymentBillsList.clear();
        this.cropPaymentBillsList.addAll(cropPaymentBills);

        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull PaymentBillViewHolder holder, int position) {
        CropPaymentBill paymentBill = cropPaymentBillsList.get(position);

        holder.paymentDateTextView.setText(paymentBill.getDate());
        holder.modeTextView.setText(paymentBill.getMode());
        holder.billIdTextView.setText(paymentBill.getBillId());

        holder.amountTextView.setText(NumberFormat.getInstance().format(paymentBill.getAmount()));

    }


    @Override
    public int getItemCount() {
        return cropPaymentBillsList.size();
    }

    public class PaymentBillViewHolder  extends RecyclerView.ViewHolder{
        TextView paymentDateTextView,modeTextView,billIdTextView,amountTextView;
        ImageView moreButton;


        public PaymentBillViewHolder(View itemView) {
            super(itemView);

            amountTextView = itemView.findViewById(R.id.txt_crop_payment_bill_card_payment_made);
            paymentDateTextView = itemView.findViewById(R.id.txt_crop_payment_bill_card_payment_date);
            modeTextView = itemView.findViewById(R.id.txt_crop_payment_bill_card_mode);
            billIdTextView = itemView.findViewById(R.id.txt_crop_payment_bill_card_bill_number);


            moreButton = itemView.findViewById(R.id.img_crop_payment_card_more);

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))){
                                final CropPaymentBill cropPaymentBill = cropPaymentBillsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle(R.string.label_confirm)
                                        .setMessage(wrapper.getString(R.string.delete_prompt_message)+" payment for bill "+cropPaymentBill.getBillId()+" ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropPaymentBill(cropPaymentBill.getId());
                                                cropPaymentBillsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }})
                                        .setNegativeButton(android.R.string.no, null).show();
                            }else if (item.getTitle().equals(mContext.getString(R.string.label_edit))){
                                CropPaymentBill cropPaymentBill = cropPaymentBillsList.get(getAdapterPosition());
                                Intent editPaymentBill = new Intent(mContext, CropPaymentBillManagerActivity.class);
                                editPaymentBill.putExtra("cropPaymentBill",cropPaymentBill);
                                mContext.startActivity(editPaymentBill);
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
