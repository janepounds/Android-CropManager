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
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropBillPaymentManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropPaymentBill;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

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
        CropPaymentBill payment = cropPaymentBillsList.get(position);

        holder.nameTextView.setText(payment.getSupplierName());
        holder.dateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(payment.getDate()));
        holder.modeTextView.setText(payment.getMode());
        holder.billNumberTextView.setText(payment.getBillNumber()+"");
    //    holder.paymentNumberTextView.setText(payment.get());
        holder.amountTextView.setText(CropSettingsSingleton.getInstance().getCurrency()+" "+NumberFormat.getInstance().format(payment.getAmount()));

        if (payment.getBillId()==null){
            holder.billPaymentLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return cropPaymentBillsList.size();
    }

    public class PaymentBillViewHolder  extends RecyclerView.ViewHolder {

        TextView amountTextView, billNumberTextView, modeTextView, nameTextView, paymentNumberTextView, dateTextView;
        ImageView moreButton, deleteButton;
        LinearLayout billPaymentLayout;

        public PaymentBillViewHolder(View itemView) {
            super(itemView);

            amountTextView = itemView.findViewById(R.id.txt_crop_payment_card_amount);
          //  paymentNumberTextView = itemView.findViewById(R.id.txt_crop_payment_card_payment_number);
            dateTextView = itemView.findViewById(R.id.txt_crop_payment_card_date);
            modeTextView = itemView.findViewById(R.id.txt_crop_payment_card_mode);
            billNumberTextView = itemView.findViewById(R.id.txt_crop_payment_card_invoice_number);

            nameTextView = itemView.findViewById(R.id.txt_crop_payment_card_customer_name);
            moreButton = itemView.findViewById(R.id.img_crop_payment_card_more);
            billPaymentLayout = itemView.findViewById(R.id.layout_crop_payment_card_invoice);

            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                    PopupMenu popup = new PopupMenu(wrapper, v);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getTitle().equals(mContext.getString(R.string.label_delete))) {
                                final CropPaymentBill cropPaymentBill = cropPaymentBillsList.get(getAdapterPosition());
                                new AlertDialog.Builder(mContext)
                                        .setTitle(R.string.label_confirm)
                                        .setMessage(wrapper.getString(R.string.delete_prompt_message) + " payment for bill " + cropPaymentBill.getBillId() + " ?")
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropPaymentBill(cropPaymentBill.getId());
                                                cropPaymentBillsList.remove(getAdapterPosition());
                                                notifyItemRemoved(getAdapterPosition());

                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, null).show();
                            } else if (item.getTitle().equals(mContext.getString(R.string.label_edit))) {
                                CropPaymentBill cropPaymentBill = cropPaymentBillsList.get(getAdapterPosition());
                                Intent editPaymentBill = new Intent(mContext, CropBillPaymentManagerActivity.class);
                                editPaymentBill.putExtra("cropPaymentBill", cropPaymentBill);
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
