package com.myfarmnow.myfarmcrop.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

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

        View view = layoutInflater.inflate(R.layout.crop_income_expense_list_card, parent, false);

        IncomeExpenseViewHolder holder = new IncomeExpenseViewHolder(view);
        return holder;
    }

    public void changeList(ArrayList<CropIncomeExpense> cropIncomeExpenses) {
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
        holder.departmentTextView.setText(incomeExpense.getDepartment());
        holder.statusTextView.setText(incomeExpense.getPaymentStatus());
        holder.amountTextView.setText("UGX" + "  " + " " + NumberFormat.getInstance().format(incomeExpense.computeAmount()));
        holder.dateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(incomeExpense.getDate()));
    }

    @Override
    public int getItemCount() {
        return cropIncomeExpensesList.size();
    }

    public class IncomeExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView, transactionTextView, categoryTextView, departmentTextView, statusTextView, amountTextView, dateTextView;
        ImageView moreButton;

        public IncomeExpenseViewHolder(View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_item);
            transactionTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_transaction);
            categoryTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_category);
            departmentTextView= itemView.findViewById(R.id.txt_crop_income_expense_card_department);
            statusTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_status);
            amountTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_gross_amount);
            dateTextView = itemView.findViewById(R.id.txt_crop_income_expense_card_date);
            moreButton = itemView.findViewById(R.id.img_crop_income_expense_card_more);

            moreButton.setOnClickListener(v -> {
                final Context wrapper = new ContextThemeWrapper(mContext, R.style.MyPopupMenu);
                PopupMenu popup = new PopupMenu(wrapper, v);
                popup.setOnMenuItemClickListener(item -> {
                    NavController navController = Navigation.findNavController(v);

                    if (item.getTitle().toString().equals(mContext.getString(R.string.label_delete))) {

                        final CropIncomeExpense cropIncomeExpense = cropIncomeExpensesList.get(getAdapterPosition());

                        new AlertDialog.Builder(mContext)
                                .setTitle("Confirm")
                                .setMessage("Do you really want to delete " + cropIncomeExpense.getItem() + " item?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                                    MyFarmDbHandlerSingleton.getHandlerInstance(mContext).deleteCropIncomeExpense(cropIncomeExpense.getId());
                                    cropIncomeExpensesList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                })
                                .setNegativeButton(android.R.string.no, null).show();

                    } else if (item.getTitle().toString().equals(mContext.getString(R.string.label_edit))) {

                        CropIncomeExpense cropIncomeExpense = cropIncomeExpensesList.get(getAdapterPosition());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("cropIncomeExpense", cropIncomeExpense);
                        navController.navigate(R.id.action_financialRecordsFragment_to_addFinancialRecordFragment, bundle);

                    }
                    return true;
                });

                popup.getMenu().add(R.string.label_edit);
                popup.getMenu().add(R.string.label_delete);
                popup.show();
            });
        }
    }
}