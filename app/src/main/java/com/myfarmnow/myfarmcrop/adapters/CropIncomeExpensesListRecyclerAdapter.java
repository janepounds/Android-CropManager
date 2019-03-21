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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropCultivationsListActivity;
import com.myfarmnow.myfarmcrop.activities.CropFertilizerApplicationListActivity;
import com.myfarmnow.myfarmcrop.activities.CropIncomeExpenseManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropIncomeExpensesListActivity;
import com.myfarmnow.myfarmcrop.activities.CropSprayingListActivity;
import com.myfarmnow.myfarmcrop.activities.CropsManagerActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.Crop;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CropIncomeExpensesListRecyclerAdapter extends RecyclerView.Adapter<CropIncomeExpensesListRecyclerAdapter.IncomeExpenseViewHolder> {
    LayoutInflater layoutInflater;
    Context mContext;
    ArrayList<CropIncomeExpense> cropIncomeExpensesList = new ArrayList<>();

    public CropIncomeExpensesListRecyclerAdapter(Context context, ArrayList<CropIncomeExpense> cropIncomeExpenses) {
        cropIncomeExpensesList.addAll(cropIncomeExpenses);
        mContext = context;
        layoutInflater = LayoutInflater.from(mContext);

    }


    @NonNull
    @Override
    public IncomeExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.crop_income_expense_list_card,parent,false);

        IncomeExpenseViewHolder holder = new IncomeExpenseViewHolder(view);
        return holder;
    }


    public void appendList(ArrayList<CropIncomeExpense> cropIncomeExpenses){

        this.cropIncomeExpensesList.addAll(cropIncomeExpenses);
        notifyDataSetChanged();
    }

    public void addCropIncomeExpense(CropIncomeExpense cropIncomeExpenses){
        this.cropIncomeExpensesList.add(cropIncomeExpenses);
        notifyItemChanged(getItemCount());
    }
    public void changeList(ArrayList<CropIncomeExpense> cropIncomeExpenses){

        this.cropIncomeExpensesList.clear();
        this.cropIncomeExpensesList.addAll(cropIncomeExpenses);

        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeExpenseViewHolder holder, int position) {

        CropIncomeExpense incomeExpense = cropIncomeExpensesList.get(position);
        holder.itemTextView.setText(incomeExpense.getItem());
        holder.transactionTextView.setText(incomeExpense.getTransaction());
        holder.categoryTextView.setText(incomeExpense.getCategory());
        holder.statusTextView.setText(incomeExpense.getPaymentStatus());
        holder.amountTextView.setText(NumberFormat.getInstance().format(incomeExpense.getGrossAmount()));
        holder.dateTextView.setText(incomeExpense.getDate());

    }
    @Override
    public int getItemCount() {
        return cropIncomeExpensesList.size();    }


    public class IncomeExpenseViewHolder extends RecyclerView.ViewHolder {

    TextView itemTextView, transactionTextView, categoryTextView,statusTextView,amountTextView,dateTextView;
    ImageView deleteButton, editButton, moreButton;
        public IncomeExpenseViewHolder(View itemView) {
            super(itemView);

            itemTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_item);
            transactionTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_transaction);
            categoryTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_category);
            statusTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_status);
            amountTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_gross_amount);
            dateTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_date);
            moreButton = itemView.findViewById(R.id.img_crop_income_expense_card_more);



            moreButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, v);
                     popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                                          @Override
                                                          public boolean onMenuItemClick(MenuItem item) {

                                                              if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))) {
                                                                  final CropIncomeExpense cropIncomeExpense = cropIncomeExpensesList.get(getAdapterPosition());
                                                                  new AlertDialog.Builder(mContext)
                                                                          .setTitle("Confirm")
                                                                          .setMessage("Do you really want to delete " + cropIncomeExpense.getItem() + " item?")
                                                                          .setIcon(android.R.drawable.ic_dialog_alert)
                                                                          .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                                                              public void onClick(DialogInterface dialog, int whichButton) {

                                                                                  MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropIncomeExpense(cropIncomeExpense.getId());
                                                                                  cropIncomeExpensesList.remove(getAdapterPosition());
                                                                                  notifyItemRemoved(getAdapterPosition());

                                                                              }
                                                                          })
                                                                          .setNegativeButton(android.R.string.no, null).show();
                                                              } else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))) {
                                                                  CropIncomeExpense cropIncomeExpense = cropIncomeExpensesList.get(getAdapterPosition());
                                                                  Intent editCropIncomeExpense = new Intent(mContext, CropIncomeExpenseManagerActivity.class);
                                                                  editCropIncomeExpense.putExtra("cropIncomeExpense", cropIncomeExpense);
                                                                  mContext.startActivity(editCropIncomeExpense);

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