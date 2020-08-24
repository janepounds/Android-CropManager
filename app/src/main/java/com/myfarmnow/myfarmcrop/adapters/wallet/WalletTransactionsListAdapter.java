package com.cabral.emaisha.wallet.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cabral.emaisha.R;
import com.cabral.emaisha.wallet.activities.WalletPurchaseCardPreviewActivity;
import com.cabral.emaisha.wallet.models.WalletTransaction;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class WalletTransactionsListAdapter  extends RecyclerView.Adapter<WalletTransactionsListAdapter.MyViewHolder> {
     private List<WalletTransaction> dataList;
    LayoutInflater layoutInflater;
    private FragmentManager fm;

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textDate, textTime, textCreditAmount, textDebitAmount, textPaidTo,textPaidTo_label, textReceivedFrom, referenceNumberTxtView,receiptTextView;
        LinearLayout debitLayout, creditLayout;

        public MyViewHolder(View v, FragmentManager fm) {
            super(v);
            textDate = v.findViewById(R.id.text_view_date_transaction);
            textTime = v.findViewById(R.id.text_view_time_transaction);
            textCreditAmount = v.findViewById(R.id.text_view_credit_amount);
            textDebitAmount = v.findViewById(R.id.text_view_debit_amount);
            textPaidTo = v.findViewById(R.id.text_view_debit_paid_to);
            textReceivedFrom = v.findViewById(R.id.text_view_credit_received_from);
            debitLayout = v.findViewById(R.id.layout_debit_activity);
            creditLayout = v.findViewById(R.id.layout_home_credit_activity);
            referenceNumberTxtView = v.findViewById(R.id.text_view_reference_number);
            textPaidTo_label= v.findViewById(R.id.paid_to_bought_from);
            receiptTextView = v.findViewById(R.id.text_view_receipt);
            receiptTextView.setOnClickListener(this);
      }

        @Override
        public void onClick(View v) {
            WalletTransaction transaction = dataList.get(getAdapterPosition());
            Log.e("Reference Number", transaction.getReferenceNumber()+" is "+transaction.isPurchase());
            if (fm!=null ){
                Intent intent = new Intent(v.getContext(), WalletPurchaseCardPreviewActivity.class);
                intent.putExtra("referenceNumber",transaction.getReferenceNumber());
                v.getContext().startActivity(intent);

            }
        }
    }


    public WalletTransactionsListAdapter(List<WalletTransaction> dataList, FragmentManager supportFragmentManager) {
        this.dataList = dataList;
        fm=supportFragmentManager;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_statement_card,parent,false);

        MyViewHolder holder = new MyViewHolder( view,fm);
        return holder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        WalletTransaction data = dataList.get(position);

        SimpleDateFormat localFormat1 = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        SimpleDateFormat localFormat2 = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);
        localFormat1.setTimeZone(TimeZone.getDefault());
        localFormat2.setTimeZone(TimeZone.getDefault());

        String currentDate = null, currentTime = null, prevDate=null;
        try {
            SimpleDateFormat incomingFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            incomingFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            currentDate = localFormat1.format(incomingFormat.parse(data.getDate()));
            currentTime = localFormat2.format(incomingFormat.parse(data.getDate()));

            if(position!=0)
            prevDate = localFormat1.format(incomingFormat.parse(dataList.get(position-1).getDate()));

            holder.textDate.setText(currentDate);
            holder.textTime.setText(currentTime);

            if(currentDate.equals(prevDate+"")  )
                holder.textDate.setVisibility(View.GONE);
            else
                holder.textDate.setVisibility(View.VISIBLE);

            holder.textPaidTo.setText(data.getRecepient());
            holder.referenceNumberTxtView.setText(data.getReferenceNumber());
            holder.textReceivedFrom.setText(data.getRecepient());

            if(data.getType().equals("credit")) {
                holder.debitLayout.setVisibility(View.GONE);
                holder.textCreditAmount.setText("+ UGX "+ NumberFormat.getInstance().format(data.getAmount())+"");
                holder.creditLayout.setVisibility(View.VISIBLE);
            }
            else if(data.getType().equals("debit")) {
                holder.creditLayout.setVisibility(View.GONE);
                holder.debitLayout.setVisibility(View.VISIBLE);
                holder.textDebitAmount.setText("- UGX "+ NumberFormat.getInstance().format(data.getAmount())+"");
            }

            holder.receiptTextView.setVisibility(View.VISIBLE);
            if(data.isPurchase()){
                holder.textPaidTo_label.setText("Food From");
            }else{
                holder.textPaidTo_label.setText("Transferred To");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("Error",e.getMessage());
        }



    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
